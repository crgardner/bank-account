package com.crg.learn.usecase.account.shared;

import com.crg.learn.domain.account.*;
import org.javamoney.moneta.Money;

public class AccountResponseBuilder implements AccountReader {
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

    public AccountResponse build() {
        return new AccountResponse(accountNumberValue, firstNameValue, lastNameValue, balanceValue);
    }
}
