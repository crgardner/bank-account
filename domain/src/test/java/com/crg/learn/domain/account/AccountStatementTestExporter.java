package com.crg.learn.domain.account;

import org.javamoney.moneta.Money;

import java.time.Instant;
import java.util.*;

class AccountStatementTestExporter implements AccountStatementExporter {
    private final List<AccountStatementEntryExporter> statementEntries = new ArrayList<>();

    @Override
    public void addLine(Instant whenBooked, Money amount, Money balance) {
        statementEntries.add(new AccountStatementEntryExporter(whenBooked, amount, balance));
    }

    List<AccountStatementEntryExporter> statementEntries() {
        return statementEntries;
    }


}
