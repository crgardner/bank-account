package com.crg.learn.presenter.http;

import java.net.URI;

public interface ResponseWriter {
    void ok(Object resource);

    void created(URI uri, Object resource);

    void notFound();
}
