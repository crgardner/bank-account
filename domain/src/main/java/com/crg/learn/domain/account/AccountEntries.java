package com.crg.learn.domain.account;

import com.crg.learn.domain.money.MonetaryRunningBalance;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.util.*;
import java.util.stream.Collectors;

class AccountEntries {
    private final List<Entry> entries;

    AccountEntries() {
        this.entries = new ArrayList<>();
    }

    AccountEntries(List<EntryImporter> entryImporters) {
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

    void export(EntrySelectionRange range, AccountExporter exporter) {
        range.select(entries).forEach(entry -> entry.export(exporter));
    }
}
