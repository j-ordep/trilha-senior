import tilha.senior.ex02.Message;

void main() {
    /*
    Contexto:
    - A versão imperativa do exercício começa com uma lista literal de mensagens.

    Aprendizado:
    - List.of deixa explícito que a coleção de entrada não será modificada. Isso reduz ruído no exemplo e destaca o processamento imperativo feito nos métodos auxiliares.
    */

    final var messages = List.of(
            new Message("Victor", "texto 1"),
            new Message("Alisson", "texto 2"),
            new Message("Victor", "texto 3"),
            new Message("Victor", "texto 4")
    );

    // O main apenas orquestra as etapas; a mutabilidade e os detalhes de iteração ficam encapsulados na classe Message.
    final var senderCounts = Message.countMessageBySender(messages);
    final var topSender = Message.getMostFrequentSender(senderCounts);
    final var uniqueSenders = Message.getSortedUniqueSenders(messages);

    IO.println(senderCounts);
    IO.println(topSender);
    IO.println(uniqueSenders);

}
