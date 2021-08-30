package com.crg.learn.domain.account;

import com.crg.learn.domain.person.Person;
import org.javamoney.moneta.Money;

import javax.money.*;
import java.util.*;

public class Account {
    private static final CurrencyUnit DEFAULT_CURRENCY = Monetary.getCurrency("EUR");

    private final AccountNumber accountNumber;
    private final Person accountHolder;
    private final AccountEntries entries = new AccountEntries();

    public Account(AccountNumber accountNumber, Person accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
    }

    public Account(AccountImporter importer) {
        this(importer.accountNumber(), importer.accountHolder());
    }

    public void add(Entry entry) {
        entries.add(entry);
    }

    public boolean hasBalanceOf(Money amount) {
        return currentBalance().equals(amount);
    }

    private Money currentBalance() {
        return entries.computeBalance(DEFAULT_CURRENCY);
    }

    public void export(AccountExporter exporter) {
        exporter.balance(currentBalance());
        accountNumber.writeTo(exporter::accountNumber);
        accountHolder.writeTo((first, last) -> {
            exporter.ownerFirstName(first);
            exporter.ownerLastName(last);
        });
    }

    public AccountStatement createStatement() {
        return new AccountStatement(accountNumber, accountHolder, entries.createStatement(DEFAULT_CURRENCY));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (!(o instanceof Account account)) {
            return false;
        }

        return accountNumber.equals(account.accountNumber) && accountHolder.equals(account.accountHolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, accountHolder);
    }

}
