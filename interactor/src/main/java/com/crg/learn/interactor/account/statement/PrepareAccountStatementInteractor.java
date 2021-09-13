package com.crg.learn.interactor.account.statement;

import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.account.statement.*;

import java.util.Optional;

public class PrepareAccountStatementInteractor implements PrepareAccountStatementUseCase {
    private final AccountRepository accountRepository;

    public PrepareAccountStatementInteractor(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void execute(PrepareAccountStatementRequest request, PrepareAccountStatementResponder responder) {
        var possibleAccount = lookupAccount(request);
        var statement = createStatementFrom(possibleAccount.orElseThrow());

        respond(responder, statement);
    }

    private Optional<Account> lookupAccount(PrepareAccountStatementRequest request) {
        return accountRepository.lookup(new AccountNumber(request.accountNumber()));
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
