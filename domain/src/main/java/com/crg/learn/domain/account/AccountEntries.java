package com.crg.learn.domain.account;

import javax.money.CurrencyUnit;
import java.util.*;
import java.util.stream.Collectors;

public class AccountEntries {
    private final List<Entry> entries = new ArrayList<>();

    public void add(Entry entry) {
        entries.add(entry);
    }

    public AccountStatementLines createStatement(CurrencyUnit currency) {
        var runningBalance = new MonetaryRunningBalance(currency);
        var lines = entries.stream().map(entry -> accountStatementLine(entry, runningBalance)).collect(Collectors.toList());
        return new AccountStatementLines(lines);
    }

    private AccountStatementLine accountStatementLine(Entry entry, MonetaryRunningBalance runningBalance) {
        return entry.createStatementLine(runningBalance);
    }
}
