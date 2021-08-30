package com.crg.learn.domain.account;

import java.util.List;

public class AccountStatementLines {
    private final List<AccountStatementLine> lines;

    public AccountStatementLines(List<AccountStatementLine> lines) {
        this.lines = lines;
    }

    public void export(AccountStatementExporter exporter) {
        lines.forEach(line -> line.export(exporter));
    }
}
