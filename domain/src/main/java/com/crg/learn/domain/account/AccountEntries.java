package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.util.*;
import java.util.stream.Collectors;

class AccountEntries {
    private final List<Entry> entries;

    public AccountEntries() {
        this.entries = new ArrayList<>();
    }

    public AccountEntries(List<EntryImporter> entryImporters) {
        this.entries = entryImporters.stream().map(Entry::new).collect(Collectors.toList());
    }

    void add(Entry entry) {
        entries.add(entry);
    }

    AccountStatementLines createStatementLines(CurrencyUnit currency) {
        return new AccountStatementLines(entryLines(currency));
    }

    private List<AccountStatementLine> entryLines(CurrencyUnit currency) {
        var runningBalance = new MonetaryRunningBalance(currency);

        return entries.stream().map(entry -> entry.createStatementLine(runningBalance))
                               .collect(Collectors.toList());
    }

    Money computeBalance(CurrencyUnit currency) {
        var runningBalance = new MonetaryRunningBalance(currency);
        entries.forEach(entry -> entry.adjust(runningBalance));

        return runningBalance.current();
    }

    public void export(AccountExporter exporter) {
        entries.forEach(entry -> entry.export(exporter));
    }
}
