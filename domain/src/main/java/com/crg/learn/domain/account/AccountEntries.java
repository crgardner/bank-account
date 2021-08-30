package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import javax.money.*;
import java.util.*;
import java.util.stream.Collectors;

class AccountEntries {
    private final List<Entry> entries = new ArrayList<>();

    void add(Entry entry) {
        entries.add(entry);
    }

    AccountStatementLines createStatement(CurrencyUnit currency) {
        var runningBalance = new MonetaryRunningBalance(currency);
        var lines = entries.stream().map(entry -> accountStatementLine(entry, runningBalance))
                                    .collect(Collectors.toList());
        return new AccountStatementLines(lines);
    }

    private AccountStatementLine accountStatementLine(Entry entry, MonetaryRunningBalance runningBalance) {
        return entry.createStatementLine(runningBalance);
    }

    Money computeBalance(CurrencyUnit currency) {
        var runningBalance = new MonetaryRunningBalance(currency);
        entries.forEach(entry -> entry.adjust(runningBalance));
        return runningBalance.current();
    }
}
