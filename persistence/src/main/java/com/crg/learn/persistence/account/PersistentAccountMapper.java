package com.crg.learn.persistence.account;

import com.crg.learn.domain.account.*;
import org.javamoney.moneta.Money;

class PersistentAccountMapper implements AccountExporter {
    private final Account account;
    private final PersistentAccount accountEntity = new PersistentAccount();

    public PersistentAccountMapper(Account account) {
        this.account = account;
    }

    public PersistentAccount map() {
        account.export(this);
        return accountEntity;
    }

    @Override
    public void accountNumber(String value) {
        accountEntity.setAccountNumber(value);
    }

    @Override
    public void ownerFirstName(String value) {
        accountEntity.setHolderFirstName(value);
    }

    @Override
    public void ownerLastName(String value) {
        accountEntity.setHolderLastName(value);
    }

    @Override
    public void balance(Money value) {
        accountEntity.setBalance(value);
    }
}
