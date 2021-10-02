package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import java.time.Instant;
import java.util.*;

class AccountTestExporter implements AccountExporter {

    private String accountNumber;
    private String ownerFirstName;
    private String ownerLastName;
    private Money balance;
    private List<EntryTestExporter> entries = new ArrayList<>();

    @Override
    public void accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    String accountNumber() {
        return accountNumber;
    }

    @Override
    public void ownerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    String ownerFirstName() {
        return ownerFirstName;
    }

    @Override
    public void ownerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    String ownerLastName() {
        return ownerLastName;
    }

    @Override
    public void balance(Money balance) {
        this.balance = balance;
    }

    Money balance() {
        return balance;
    }

    @Override
    public void addEntry(String transactionId, Instant whenBooked, Money amount) {
        entries.add(new EntryTestExporter(transactionId, whenBooked, amount));
    }

    List<EntryTestExporter> entries() {
        return entries;
    }
}
