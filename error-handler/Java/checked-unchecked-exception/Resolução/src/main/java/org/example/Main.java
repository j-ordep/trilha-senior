package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        final var source = Paths.get("gopher.png");
        final var target = Paths.get("out.png");

        /*
        Contexto:
        - A versão comentada abaixo preserva a causa original ao encapsular a checked exception em `RuntimeException`.

        Aprendizado:
        - Essa conversão elimina a obrigação de propagar `throws` no restante da aplicação e mantém a stack trace para diagnóstico.
        - O trade-off é transformar um contrato explícito de I/O em uma unchecked exception, o que simplifica a assinatura, mas reduz a previsibilidade do ponto de falha para quem chama.
        */
        /* try {
            copy(source, target);
        } catch (Exception e) {
            // System.err.println(e); // só loga sem saber de fato o que aconteceu (usando Exception e)
            throw new RuntimeException(e); // com isso vemos toda a StackTrace, gera pressão no GC para limpar toda a stack, com alta carga gera gargalo
        } */

        /*
        Contexto:
        - A implementação ativa também converte a falha para unchecked exception, mas troca a causa original por uma exceção de domínio simplificada.

        Decisão de design:
        - `NoStackTraceException` comunica uma mensagem de alto nível e evita o custo de montar stack trace.

        Trade-off:
        - O ganho é reduzir ruído e overhead em cenários muito frequentes.
        - O custo é perder contexto de diagnóstico: sem causa encadeada e sem stack trace, fica mais difícil investigar onde a falha realmente ocorreu.
        */
        try {
            copy(source, target);
        } catch (Exception e) {
            throw new NoStackTraceException("Error: File Already Exists");
        }

    }

    /*
    Conceito principal:
    - O método continua usando `throws IOException` internamente, mas a responsabilidade de converter essa checked exception foi trazida para a borda do `main`.

    Aprendizado:
    - O exercício evidencia duas camadas possíveis: a API técnica pode expor `IOException`, enquanto a aplicação decide se deixa a exceção vazar ou se a encapsula em uma unchecked exception.
    */
    private static void copy(final Path source, final Path target) throws IOException {
        Files.copy(source, target);
    }
}
