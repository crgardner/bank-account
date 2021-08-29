package com.crg.learning.controller.account.open;

import com.crg.learn.usecase.account.open.OpenAccountResponder;
import com.crg.learn.usecase.shared.AccountResponse;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class OpenAccountPresenter implements OpenAccountResponder {

    private ResponseEntity<Object> entity;

    @Override
    public void accept(AccountResponse response) {
        entity = ResponseEntity.created(uriFrom(response)).body(accountResourceFrom(response));
    }

    private URI uriFrom(AccountResponse response) {
        return URI.create(String.format("/banking/accounts/v1/%s", response.accountNumber()));
    }

    private AccountResource accountResourceFrom(AccountResponse response) {
        return new AccountResource(response.accountNumber(),
                                   response.ownerFirstName(),
                                   response.ownerLastName(),
                                   formattedBalance(response),
                                   response.balance().getCurrency().getCurrencyCode());
    }

    private String formattedBalance(AccountResponse response) {
        return String.format("%.2f", response.balance().getNumber().doubleValueExact());
    }

    public ResponseEntity<Object> responseEntity() {
        return entity;
    }
}
