package com.crg.learn.controller.account.open;

import com.crg.learn.controller.presenter.BasePresenter;
import com.crg.learn.usecase.account.open.OpenAccountResponder;
import com.crg.learn.usecase.shared.AccountResponse;
import com.crg.learn.controller.account.shared.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class OpenAccountPresenter extends BasePresenter implements OpenAccountResponder {

    private final AccountResourceMapper accountResourceMapper = new AccountResourceMapper();

    @Override
    public void accept(AccountResponse response) {
        responseOf(ResponseEntity.created(uriFrom(response))
                               .body(accountResourceFrom(response)));
    }

    private URI uriFrom(AccountResponse response) {
        return URI.create("/banking/accounts/v1/%s".formatted(response.accountNumber()));
    }

    private AccountViewModel accountResourceFrom(AccountResponse response) {
        return accountResourceMapper.accountResourceFrom(response);
    }

}
