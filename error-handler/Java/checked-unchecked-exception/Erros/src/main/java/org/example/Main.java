package org.example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final var source = Paths.get("img.png");
        final var target = Paths.get("out.png");

        try {
            copy(source, target);
        } catch (IOException e) {
            System.err.println(e);
            // não sabe como tratar e crash a aplicaçãoz
            // throw new RuntimeException(e);
        }

        // jeito bom e bonito em java
        Arrays.asList(args).forEach(System.out::println);

        /* RUIM
        - FileReader precisa de try-cath, não pode usar throw num bloco que usa lambda
        - No java labda usa interface funcional, interface por definição não tem throws */
        Arrays.asList("a.txt", "b.txt").forEach(file -> {
            var reader = new FileReader(file);
            System.out.println(reader);
        });

        // com isso teria que fazer assim PIOROU
        Arrays.asList("a.txt", "b.txt").forEach(file -> {
            FileReader reader = null;
            try {
                reader = new FileReader(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println(reader);
        });

    }

    // throws joga o erro pro próximo, afetando solid expondo erro interno de lib padrão
    private static void copy(final Path source, final Path target) throws IOException {
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
