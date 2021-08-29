package com.crg.learn.usecase.account.statement;

import com.crg.learn.domain.account.*;

public class PrepareAccountStatementInteractor implements PrepareAccountStatementUseCase {
    private final Bank bank;

    public PrepareAccountStatementInteractor(Bank bank) {
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
