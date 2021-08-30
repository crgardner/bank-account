package com.crg.learn.persistence.account;

import static org.assertj.core.api.Assertions.*;
import static com.crg.learn.domain.testsupport.AccountMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.*;

import com.crg.learn.domain.person.Person;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;

import javax.money.Monetary;

@DisplayName("AccountEntityMapper")
class PersistentAccountMapperTest {

    @Test
    void mapsAccountToAccountEntity() {
        var account = make(an(Account, with(numberValue, "9999999999"),
                                              with(accountHolder, new Person("Hank", "Hill"))));

        var mapper = new PersistentAccountMapper(account);
        var accountEntity = mapper.map();

        assertThat(accountEntity.getAccountNumber()).isEqualTo("9999999999");
        assertThat(accountEntity.getBalance()).isEqualTo(Money.zero(Monetary.getCurrency("EUR")));
        assertThat(accountEntity.getHolderFirstName()).isEqualTo("Hank");
        assertThat(accountEntity.getHolderLastName()).isEqualTo("Hill");
    }
}