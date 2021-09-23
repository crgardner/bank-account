package com.crg.learn.persistence.account;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.money.Monetary;
import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

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
        account.setAccountNumber("9999");
        account.setHolderLastName("First");
        account.setHolderLastName("last");

        var entry = new PersistentEntry();
        entry.setTransactionId("888");
        entry.setWhenBooked(Instant.now());
        entry.setAmount(Money.zero(Monetary.getCurrency("EUR")));

        account.add(entry);
    }

    @Test
    void savesAccount() {
        var savedAccount = repository.save(account);
        var possibleAccount = repository.findByAccountNumber("9999");

        assertThat(possibleAccount).contains(savedAccount);
    }
}