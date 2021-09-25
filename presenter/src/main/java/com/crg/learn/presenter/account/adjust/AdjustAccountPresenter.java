package com.crg.learn.presenter.account.adjust;

import com.crg.learn.presenter.http.ResponseWriter;
import com.crg.learn.usecase.account.adjust.AdjustAccountResponder;
import com.crg.learn.usecase.shared.*;
import org.javamoney.moneta.Money;

import java.net.URI;

public class AdjustAccountPresenter implements AdjustAccountResponder {

    private final ResponseWriter entity;

    public AdjustAccountPresenter(ResponseWriter entity) {
        this.entity = entity;
    }

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
        entity.created(uriFrom(response, entry), viewModel);
    }

    private String formatted(Money amount) {
        return "%.2f".formatted(amount.getNumber().doubleValueExact());
    }

    private URI uriFrom(AccountResponse response, EntryResponse entry) {
        return URI.create("/banking/v1/accounts/%s/adjustments/%s".formatted(response.accountNumber(), entry.transactionId()));
    }

    @Override
    public void onNotFound() {
        entity.notFound();
    }

}
