package com.crg.learn.controller.presenter;

import org.springframework.http.ResponseEntity;

public abstract class BasePresenter {
    private ResponseEntity<Object> entity;

    public void onNotFound() {
        entity = ResponseEntity.notFound().build();
    }

    public ResponseEntity<Object> responseEntity() {
        return entity;
    }

    protected void responseOf(ResponseEntity<Object> entity) {
        this.entity = entity;
    }
}
