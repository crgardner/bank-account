package com.crg.learn.usecase.account;

import com.crg.learn.domain.account.*;
import com.crg.learn.domain.person.Person;
import com.crg.learn.usecase.concept.UseCase;

class OpenAccount implements UseCase<OpenAccountRequest, OpenAccountResponder> {
    private final Bank bank;

    public OpenAccount(Bank bank) {
        this.bank = bank;
    }

    public void execute(OpenAccountRequest request, OpenAccountResponder responder) {
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
