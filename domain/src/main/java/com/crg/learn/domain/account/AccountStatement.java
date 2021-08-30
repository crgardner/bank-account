package com.crg.learn.domain.account;

import com.crg.learn.domain.person.Person;

public class AccountStatement {
    private final AccountStatementLines statementLines;
    private final AccountNumber accountNumber;
    private final Person accountHolder;

    public AccountStatement(AccountNumber accountNumber, Person accountHolder, AccountStatementLines statementLines) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.statementLines = statementLines;
    }

    public void export(AccountStatementExporter reader) {
        statementLines.export(reader);
    }
}
