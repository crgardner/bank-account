package com.crg.learn.usecase.account;

import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;

class OpenAccount {
    private final Bank bank;
    private final OpenAccountRequest request;
    private final OpenAccountResponder responder;

    public OpenAccount(Bank bank, OpenAccountRequest request, OpenAccountResponder responder) {
        this.bank = bank;
        this.request = request;
        this.responder = responder;
    }

    public void execute() {
        var accountNumber = new AccountNumber("011234567X");
        var account = new Account(accountNumber, new Person(request.firstName(), request.lastName()));
        bank.open(account);

        var writer = new AccountNumberWriter();
        accountNumber.writeTo(writer);
        responder.accept(new OpenAccountResponse(writer.value));
    }

    private static class AccountNumberWriter implements AccountNumber.AccountNumberReader {

        private String value;

        @Override
        public void numberValue(String value) {
            this.value = value;
        }
    }
}
