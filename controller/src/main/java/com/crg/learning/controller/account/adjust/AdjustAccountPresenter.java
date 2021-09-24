package com.crg.learning.controller.account.adjust;

import com.crg.learn.usecase.account.adjust.AdjustAccountResponder;
import com.crg.learn.usecase.shared.*;
import com.crg.learning.controller.account.shared.AccountResourceMapper;
import org.javamoney.moneta.Money;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class AdjustAccountPresenter implements AdjustAccountResponder {

    private final AccountResourceMapper accountResourceMapper = new AccountResourceMapper();
    private ResponseEntity<Object> entity;

    @Override
    public void accept(AccountResponse response) {
        var entry = response.entryResponses().get(0);
        var viewModel = new AdjustmentViewModel(response.accountNumber(),
                                                formatted(response.balance()),
                                                response.balance().getCurrency().getCurrencyCode(),
                                                entry.transactionId(), formatted(entry.amount())
        );
        entity = ResponseEntity.created(uriFrom(response, entry)).body(viewModel);
    }

    private String formatted(Money amount) {
        return "%.2f".formatted(amount.getNumber().doubleValueExact());
    }

    private URI uriFrom(AccountResponse response, EntryResponse entry) {
        return URI.create("/banking/v1/accounts/%s/adjustments/%s".formatted(response.accountNumber(), entry.transactionId()));
    }

    @Override
    public void onNotFound() {
        entity = ResponseEntity.notFound().build();
    }

    public ResponseEntity<Object> responseEntity() {
        return entity;
    }
}
