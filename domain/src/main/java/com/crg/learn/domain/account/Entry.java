package com.crg.learn.domain.account;

import com.crg.learn.domain.money.MonetaryRunningBalance;
import org.javamoney.moneta.Money;

import java.time.Instant;

public class Entry {
    private final Money amount;
    private final Instant whenBooked;
    private TransactionId transactionId;

    public Entry(TransactionId transactionId, Money amount, Instant whenBooked) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.whenBooked = whenBooked;
    }

    public Entry(TransactionId transactionId, Money amount) {
        this(transactionId, amount, Instant.now());
    }

    Entry(EntryImporter importer) {
        this(importer.transactionId(), importer.amount(), importer.whenBooked());
    }


    public void adjust(MonetaryRunningBalance runningBalance) {
        runningBalance.adjust(amount);
    }

    public AccountStatementLine createStatementLine(MonetaryRunningBalance runningBalance) {
        return new AccountStatementLine(amount, whenBooked, runningBalance.adjust(amount));
    }

    public void export(AccountExporter exporter) {
        exporter.addEntry(transactionId, whenBooked, amount);
    }
}
