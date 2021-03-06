package com.crg.learn.controller.account.open;

import com.crg.learn.controller.account.shared.BasicMoneyFormatter;
import com.crg.learn.controller.presenter.BasePresenter;
import com.crg.learn.usecase.account.open.OpenAccountResponder;
import com.crg.learn.usecase.shared.AccountResponse;
import org.springframework.http.ResponseEntity;

import java.net.URI;

class OpenAccountPresenter extends BasePresenter implements OpenAccountResponder {

    @Override
    public void accept(AccountResponse response) {
        responseOf(ResponseEntity.created(uriFrom(response))
                                 .body(viewModelFrom(response)));
    }

    private URI uriFrom(AccountResponse response) {
        return URI.create("/banking/accounts/v1/%s".formatted(response.accountNumber()));
    }

    private OpenAccountViewModel viewModelFrom(AccountResponse response) {
        return new OpenAccountViewModel(response.accountNumber(),
                                    response.ownerFirstName(),
                                    response.ownerLastName(),
                                    BasicMoneyFormatter.format(response.balance()),
                                    response.balance().getCurrency().getCurrencyCode());
    }

}
