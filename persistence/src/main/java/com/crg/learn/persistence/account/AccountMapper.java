package com.crg.learn.persistence.account;

import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;

import java.util.*;

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
    public Person accountHolder() {
        return new Person(persistentAccount.getHolderFirstName(),
                          persistentAccount.getHolderLastName());
    }

    @Override
    public List<EntryImporter> entryImporters() {
        return Collections.emptyList();
    }
}
