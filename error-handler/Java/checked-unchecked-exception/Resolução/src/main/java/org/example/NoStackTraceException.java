package org.example;

public class NoStackTraceException extends RuntimeException {

    public NoStackTraceException(String message) {
        this(message, null);
    }

    public NoStackTraceException(String message, Throwable cause) {
        /*
        Contexto:
        - Esta unchecked exception existe para encapsular falhas sem carregar o custo padrão de diagnóstico do `Throwable`.

        Conceito principal:
        - O construtor protegido de `RuntimeException` permite desligar suppression e stack trace.

        Trade-off:
        - Isso reduz alocação e trabalho de montagem da pilha, o que pode ser útil quando a exceção representa um fluxo esperado e muito frequente.
        - Em contrapartida, a ausência de stack trace e de causa encadeada reduz bastante a capacidade de investigação quando o erro foge do cenário previsto.
        */
        super(message, null, false, false);
    }


}
