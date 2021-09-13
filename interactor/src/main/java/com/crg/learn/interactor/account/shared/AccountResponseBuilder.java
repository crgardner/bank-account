package com.crg.learn.interactor.account.shared;

import com.crg.learn.domain.account.*;
import com.crg.learn.usecase.shared.AccountResponse;
import org.javamoney.moneta.Money;

import java.time.Instant;

public class AccountResponseBuilder implements AccountExporter {
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

    @Override
    public void addEntry(Instant whenBooked, Money amount) {

    }

    public AccountResponse build() {
        return new AccountResponse(accountNumberValue, firstNameValue, lastNameValue, balanceValue);
    }
}
