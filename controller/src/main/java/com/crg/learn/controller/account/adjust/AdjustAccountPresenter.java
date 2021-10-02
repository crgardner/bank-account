package com.crg.learn.controller.account.adjust;

import com.crg.learn.controller.presenter.BasePresenter;
import com.crg.learn.usecase.account.adjust.AdjustAccountResponder;
import com.crg.learn.usecase.shared.*;
import com.crg.learn.controller.account.shared.BasicMoneyFormatter;
import org.javamoney.moneta.Money;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class AdjustAccountPresenter extends BasePresenter implements AdjustAccountResponder {

    @Override
    public void accept(AccountResponse response) {
        response.firstEntry().ifPresentOrElse(entry -> map(entry, response), this::onNotFound);
    }

    private void map(EntryResponse entry, AccountResponse response) {
        var viewModel = new AdjustmentViewModel(response.accountNumber(),
                                                formatted(response.balance()),
                                                response.balance().getCurrency().getCurrencyCode(),
                                                entry.transactionId(), formatted(entry.amount())
        );

        responseOf(ResponseEntity.created(uriFrom(response, entry)).body(viewModel));
    }

    private String formatted(Money amount) {
        return BasicMoneyFormatter.format(amount);
    }

    private URI uriFrom(AccountResponse response, EntryResponse entry) {
        return URI.create("/banking/v1/accounts/%s/adjustments/%s".formatted(response.accountNumber(),
                                                                             entry.transactionId()));
    }

}
