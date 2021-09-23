package com.crg.learn.persistence.account;

import com.crg.learn.domain.account.*;
import org.javamoney.moneta.Money;

import java.time.Instant;

class PersistentAccountMapper implements AccountExporter {
    private final Account account;
    private final PersistentAccount persistentAccount = new PersistentAccount();

    public PersistentAccountMapper(Account account) {
        this.account = account;
    }

    public PersistentAccount map() {
        account.export(this);
        return persistentAccount;
    }

    @Override
    public void accountNumber(String value) {
        persistentAccount.setAccountNumber(value);
    }

    @Override
    public void ownerFirstName(String value) {
        persistentAccount.setHolderFirstName(value);
    }

    @Override
    public void ownerLastName(String value) {
        persistentAccount.setHolderLastName(value);
    }

    @Override
    public void balance(Money value) {
        // no-op
    }

    @Override
    public void addEntry(TransactionId transactionId, Instant whenBooked, Money amount) {
        var persistentEntry = new PersistentEntry();
        persistentEntry.setTransactionId(transactionId.value());
        persistentEntry.setAmount(amount);
        persistentEntry.setWhenBooked(whenBooked);
        persistentAccount.add(persistentEntry);
    }
}
