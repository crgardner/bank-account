package com.crg.learn.usecase.account.open;

import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;
import com.crg.learn.usecase.account.shared.AccountResponseBuilder;

public class OpenAccountInteractor implements OpenAccountUseCase {
    private final Bank bank;

    public OpenAccountInteractor(Bank bank) {
        this.bank = bank;
    }

    public void execute(OpenAccountRequest request, OpenAccountResponder responder) {
        var account = createAccountFrom(request);
        open(account);
        respond(responder, account);
    }

    private Account createAccountFrom(OpenAccountRequest request) {
        var accountNumber = new AccountNumber("011234567X");
        var accountHolder = new Person(request.firstName(), request.lastName());

        return new Account(accountNumber, accountHolder);
    }

    private void open(Account account) {
        bank.open(account);
    }

    private void respond(OpenAccountResponder responder, Account account) {
        var exporter = new AccountResponseBuilder();

        account.export(exporter);
        responder.accept(exporter.build());
    }

}
