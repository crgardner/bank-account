package com.crg.learn.domain.account;

import java.util.List;

public class AccountStatementLines {
    private final List<AccountStatementLine> lines;

    public AccountStatementLines(List<AccountStatementLine> lines) {
        this.lines = lines;
    }
}
