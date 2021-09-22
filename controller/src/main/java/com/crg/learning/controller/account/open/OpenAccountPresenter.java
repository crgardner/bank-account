package com.crg.learning.controller.account.open;

import com.crg.learn.usecase.account.open.OpenAccountResponder;
import com.crg.learn.usecase.shared.AccountResponse;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class OpenAccountPresenter implements OpenAccountResponder {

    private final AccountResourceMapper accountResourceMapper = new AccountResourceMapper();
    private ResponseEntity<Object> entity;

    @Override
    public void accept(AccountResponse response) {
        entity = ResponseEntity.created(uriFrom(response))
                               .body(accountResourceFrom(response));
    }

    private URI uriFrom(AccountResponse response) {
        return URI.create("/banking/accounts/v1/%s".formatted(response.accountNumber()));
    }

    private AccountResource accountResourceFrom(AccountResponse response) {
        return accountResourceMapper.accountResourceFrom(response);
    }

    public ResponseEntity<Object> responseEntity() {
        return entity;
    }
}
