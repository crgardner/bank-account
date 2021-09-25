package com.crg.learn.controller.http;

import com.crg.learn.presenter.http.ResponseWriter;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class ResponseEntityResponseWriter implements ResponseWriter {

    private ResponseEntity<Object> responseEntity;

    @Override
    public void ok(Object resource) {
        responseEntity = ResponseEntity.ok(resource);
    }

    @Override
    public void created(URI uri, Object resource) {
        responseEntity = ResponseEntity.created(uri).body(resource);
    }

    @Override
    public void notFound() {
        responseEntity = ResponseEntity.notFound().build();
    }

    public ResponseEntity<Object> entity() {
        return responseEntity;
    }

}
