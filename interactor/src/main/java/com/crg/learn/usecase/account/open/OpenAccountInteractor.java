package com.crg.learn.usecase.account.open;

import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;
import com.crg.learn.usecase.account.shared.AccountResponseBuilder;

public class OpenAccountInteractor implements OpenAccountUseCase {
    private final Bank bank;
    private final AccountNumberProvider accountNumberProvider;

    public OpenAccountInteractor(Bank bank, AccountNumberProvider accountNumberProvider) {
        this.bank = bank;
        this.accountNumberProvider = accountNumberProvider;
    }

    public void execute(OpenAccountRequest request, OpenAccountResponder responder) {
        var account = createAccountFrom(request);
        open(account);
        respond(responder, account);
    }

    private Account createAccountFrom(OpenAccountRequest request) {
        var accountNumber = accountNumberProvider.nextAccountNumber();
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
