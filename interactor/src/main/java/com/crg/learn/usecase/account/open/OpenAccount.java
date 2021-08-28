package com.crg.learn.usecase.account.open;

import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;
import com.crg.learn.usecase.account.shared.AccountResponseBuilder;
import com.crg.learn.usecase.concept.UseCase;

class OpenAccount implements UseCase<OpenAccountRequest, OpenAccountResponder> {
    private final Bank bank;

    OpenAccount(Bank bank) {
        this.bank = bank;
    }

    public void execute(OpenAccountRequest request, OpenAccountResponder responder) {
        var account = createAccountFrom(request);
        open(account);

        respond(responder, account);
    }

    private Account createAccountFrom(OpenAccountRequest request) {
        var accountNumber = new AccountNumber("011234567X");

        return new Account(accountNumber, new Person(request.firstName(), request.lastName()));
    }

    private void open(Account account) {
        bank.open(account);
    }

    private void respond(OpenAccountResponder responder, Account account) {
        var builder = new AccountResponseBuilder();

        account.writeTo(builder);
        responder.accept(builder.build());
    }

}
