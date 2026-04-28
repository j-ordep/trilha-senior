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
            /*
            Contexto:
            - O chamador recebeu uma checked exception porque o método `copy` expõe `throws IOException`.

            Aprendizado:
            - Se este ponto da aplicação não tem estratégia de recuperação, o `catch` tende a virar apenas log local ou falha da execução.
            - Isso evidencia o custo de deixar a exceção vazar: a responsabilidade de tratamento sobe na pilha mesmo quando o chamador não agrega contexto útil.
            */
            // throw new RuntimeException(e);
        }

        // Aqui a lambda encaixa naturalmente porque `println` não declara checked exception.
        Arrays.asList(args).forEach(System.out::println);

        /*
        Contexto:
        - Este trecho ilustra o atrito entre checked exception e APIs funcionais como `forEach`.

        Conceito principal:
        - A lambda precisa obedecer à assinatura da interface funcional consumida por `forEach`.

        Aprendizado:
        - Como `Consumer<T>` não declara `throws`, uma checked exception de `FileReader` não pode simplesmente "vazar" pela lambda.
        - O efeito prático é que o fluxo funcional perde fluidez quando a operação interna depende de tratamento obrigatório.
        */
        Arrays.asList("a.txt", "b.txt").forEach(file -> {
            var reader = new FileReader(file);
            System.out.println(reader);
        });

        /*
        Decisão de design:
        - O `try/catch` dentro da lambda adapta a checked exception para o contrato exigido pela interface funcional.

        Trade-off:
        - Converter para `RuntimeException` reduz o atrito sintático e permite continuar no estilo funcional.
        - Em contrapartida, a causa passa a ser tratada como unchecked exception, deslocando a responsabilidade para níveis mais altos e escondendo o contrato original da API.
        */
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

    /*
    Contexto:
    - O método delega a cópia para `Files.copy` e preserva a `IOException` na assinatura.

    Aprendizado:
    - Declarar `throws` mantém explícito que a falha é uma checked exception de I/O.
    - O benefício é transparência do contrato; o custo é acoplar o chamador ao detalhe de tratamento exigido pela biblioteca padrão.
    */
    private static void copy(final Path source, final Path target) throws IOException {
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }
}
