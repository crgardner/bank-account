package com.crg.learn.persistence.account;

import static org.assertj.core.api.Assertions.*;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.money.Monetary;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AccountJpaRepositoryTest {

    @Autowired
    @SuppressWarnings("all")
    private AccountJpaRepository repository;

    private PersistentAccount account;

    @BeforeEach
    void init() {
        account = new PersistentAccount();
        account.setBalance(Money.of(100, Monetary.getCurrency("USD")));
        account.setAccountNumber("9999");
        account.setHolderLastName("First");
        account.setHolderLastName("last");
    }

    @Test
    void savesAccount() {
        var savedAccount = repository.save(account);

        assertThat(savedAccount.getId()).isNotNull();
    }

    @Test
    void findsAccountByAccountNumber() {
    }
}