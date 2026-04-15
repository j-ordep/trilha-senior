package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        final var source = Paths.get("gopher.png");
        final var target = Paths.get("out.png");

        try {
            copy(source, target);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    private static void copy(final Path source, final Path target) throws IOException {
        Files.copy(source, target);
    }
}
