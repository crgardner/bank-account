package com.crg.learn.presenter.account.open;

import com.crg.learn.presenter.account.shared.*;
import com.crg.learn.presenter.http.ResponseWriter;
import com.crg.learn.usecase.account.open.OpenAccountResponder;
import com.crg.learn.usecase.shared.AccountResponse;

import java.net.URI;

public class OpenAccountPresenter implements OpenAccountResponder {

    private final AccountResourceMapper accountResourceMapper = new AccountResourceMapper();
    private final ResponseWriter entity;

    public OpenAccountPresenter(ResponseWriter entity) {
        this.entity = entity;
    }

    @Override
    public void accept(AccountResponse response) {
       entity.created(uriFrom(response), accountResourceFrom(response));
    }

    private URI uriFrom(AccountResponse response) {
        return URI.create("/banking/accounts/v1/%s".formatted(response.accountNumber()));
    }

    private AccountResource accountResourceFrom(AccountResponse response) {
        return accountResourceMapper.accountResourceFrom(response);
    }

}
