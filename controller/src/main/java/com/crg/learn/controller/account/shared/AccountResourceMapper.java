package com.crg.learn.controller.account.shared;

import com.crg.learn.usecase.shared.AccountResponse;

public class AccountResourceMapper {
    public AccountResource accountResourceFrom(AccountResponse response) {
        return new AccountResource(response.accountNumber(), response.ownerFirstName(), response.ownerLastName(),
                formattedBalance(response), response.balance().getCurrency().getCurrencyCode());
    }

    private String formattedBalance(AccountResponse response) {
        return "%.2f".formatted(response.balance().getNumber().doubleValueExact());
    }
}