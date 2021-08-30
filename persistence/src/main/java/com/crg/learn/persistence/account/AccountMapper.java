package com.crg.learn.persistence.account;

import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;
import org.javamoney.moneta.Money;

public class AccountMapper implements AccountImporter {
    private final PersistentAccount persistentAccount;

    public AccountMapper(PersistentAccount persistentAccount) {
        this.persistentAccount = persistentAccount;
    }

    @Override
    public AccountNumber accountNumber() {
        return new AccountNumber(persistentAccount.getAccountNumber());
    }

    @Override
    public Money accountBalance() {
        return persistentAccount.getBalance();
    }

    @Override
    public Person accountHolder() {
        return new Person(persistentAccount.getHolderFirstName(),
                          persistentAccount.getHolderLastName());
    }
}
