package com.crg.learn.domain.account;

import com.crg.learn.domain.money.MonetaryRunningBalance;
import org.javamoney.moneta.Money;

import java.time.Instant;

public class Entry {
    private final Money amount;
    private final Instant whenBooked;

    public Entry(Money amount) {
        this(amount, Instant.now());
    }

    public Entry(Money amount, Instant whenBooked) {
        this.amount = amount;
        this.whenBooked = whenBooked;
    }

    Entry(EntryImporter importer) {
        this(importer.amount(), importer.whenBooked());
    }

    public void adjust(MonetaryRunningBalance runningBalance) {
        runningBalance.adjust(amount);
    }

    public AccountStatementLine createStatementLine(MonetaryRunningBalance runningBalance) {
        return new AccountStatementLine(amount, whenBooked, runningBalance.adjust(amount));
    }

    public void export(AccountExporter exporter) {
        exporter.addEntry(whenBooked, amount);
    }
}
