package com.crg.learning.controller.account.adjust;

import com.crg.learn.usecase.account.adjust.AdjustAccountResponder;
import com.crg.learn.usecase.shared.AccountResponse;
import com.crg.learning.controller.account.shared.AccountResourceMapper;
import org.springframework.http.ResponseEntity;

public class AdjustAccountPresenter implements AdjustAccountResponder {

    private final AccountResourceMapper accountResourceMapper = new AccountResourceMapper();
    private ResponseEntity<Object> entity;

    @Override
    public void accept(AccountResponse response) {
        entity = ResponseEntity.ok(accountResourceMapper.accountResourceFrom(response));
    }

    @Override
    public void onNotFound() {
        entity = ResponseEntity.notFound().build();
    }

    public ResponseEntity<Object> responseEntity() {
        return entity;
    }
}
