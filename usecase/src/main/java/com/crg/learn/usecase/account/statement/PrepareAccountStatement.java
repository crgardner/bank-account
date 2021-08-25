package com.crg.learn.usecase.account.statement;

import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.concept.UseCase;

public class PrepareAccountStatement implements UseCase<PrepareAccountStatementRequest, PrepareAccountStatementResponder> {
    private final Bank bank;

    public PrepareAccountStatement(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void execute(PrepareAccountStatementRequest request, PrepareAccountStatementResponder responder) {
        var possibleAccount = bank.lookup(new AccountNumber(request.accountNumber()));
        var account = possibleAccount.orElseThrow();

        var statement = account.createStatement();

        var builder = new PrepareStatementResponseBuilder();
        statement.writeTo(builder);

        responder.accept(builder.build());
    }

}
