package com.crg.learn.controller.account.shared;

import com.crg.learn.usecase.shared.AccountResponse;

public class AccountResourceMapper {
    public AccountResource accountResourceFrom(AccountResponse response) {
        return new AccountResource(response.accountNumber(),
                                   response.ownerFirstName(),
                                   response.ownerLastName(),
                                   BasicMoneyFormatter.format(response.balance()),
                                   response.balance().getCurrency().getCurrencyCode());
    }

}