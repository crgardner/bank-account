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

    AccountStatementLines createStatementLines(CurrencyUnit currency) {
        var runningBalance = new MonetaryRunningBalance(currency);

        return new AccountStatementLines(entryLines(runningBalance));
    }

    private List<AccountStatementLine> entryLines(MonetaryRunningBalance runningBalance) {
        return entries.stream().map(entry -> entry.createStatementLine(runningBalance))
                               .collect(Collectors.toList());
    }

    Money computeBalance(CurrencyUnit currency) {
        var runningBalance = new MonetaryRunningBalance(currency);
        entries.forEach(entry -> entry.adjust(runningBalance));

        return runningBalance.current();
    }
}
