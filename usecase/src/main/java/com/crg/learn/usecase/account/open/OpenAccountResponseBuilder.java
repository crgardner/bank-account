package com.crg.learn.usecase.account.open;

import com.crg.learn.domain.account.Account;
import org.javamoney.moneta.Money;

class OpenAccountResponseBuilder implements Account.AccountReader {
    private String accountNumberValue;
    private String firstNameValue;
    private String lastNameValue;
    private Money balanceValue;

    @Override
    public void accountNumber(String accountNumberValue) {
        this.accountNumberValue = accountNumberValue;

    }

    @Override
    public void ownerFirstName(String firstNameValue) {
        this.firstNameValue = firstNameValue;
    }

    @Override
    public void ownerLastName(String lastNameValue) {
        this.lastNameValue = lastNameValue;
    }

    @Override
    public void balance(Money balanceValue) {
        this.balanceValue = balanceValue;
    }

    public OpenAccountResponse build() {
        return new OpenAccountResponse(accountNumberValue, firstNameValue, lastNameValue, balanceValue);
    }
}
