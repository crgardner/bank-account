package com.crg.learn.usecase.account.statement;

import com.crg.learn.domain.account.*;

import java.util.Optional;

public class PrepareAccountStatementInteractor implements PrepareAccountStatementUseCase {
    private final Bank bank;

    public PrepareAccountStatementInteractor(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void execute(PrepareAccountStatementRequest request, PrepareAccountStatementResponder responder) {
        var possibleAccount = lookupAccount(request);
        var statement = createStatementFrom(possibleAccount.orElseThrow());

        respond(responder, statement);
    }

    private Optional<Account> lookupAccount(PrepareAccountStatementRequest request) {
        return bank.lookup(new AccountNumber(request.accountNumber()));
    }

    private AccountStatement createStatementFrom(Account account) {
        return account.createStatement();
    }

    private void respond(PrepareAccountStatementResponder responder, AccountStatement statement) {
        var builder = new PrepareStatementResponseBuilder();
        statement.export(builder);

        responder.accept(builder.build());
    }

}
