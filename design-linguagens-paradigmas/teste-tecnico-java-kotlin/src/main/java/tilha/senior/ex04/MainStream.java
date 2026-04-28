public record Message(String sender, String body, Boolean isRead) {
    public Message(String sender, String body) {
        this(sender, body, false);
    }
}

void main() {
    /*
    Contexto:
    - Esta versão resolve o mesmo problema do exemplo imperativo usando Stream.

    Aprendizado:
    - O ganho aqui não é um "loop bonito", mas a composição de operações de agregação e transformação em uma pipeline declarativa.
    */
    final var messages = List.of(
            new Message("Victor", "mensagem 1"),
            new Message("Alisson", "mensagem 2"),
            new Message("Victor", "mensagem 3"),
            new Message("Victor", "mensagem 4")
    );

    var frequentSender = messages.stream()
            .collect(Collectors.groupingBy(Message::sender)) // groupingBy materializa um Map<String, List<Message>> para a próxima etapa da agregação.
            .entrySet() // entrySet() expõe pares chave/valor como Map.Entry, o que permite comparar remetente e frequência no mesmo fluxo.
            .stream()
            .max(Comparator.comparingInt(entry -> entry.getValue().size()))
            .map(Map.Entry::getKey)
            .orElse("Unknown sender");

    /*
    Conceito principal:
    - A segunda pipeline filtra, projeta, remove duplicatas e ordena antes de materializar o resultado.

    Ponto de atenção:
    - Streams ficam mais legíveis quando cada etapa mantém uma responsabilidade única; se a cadeia crescer demais, vale extrair operações nomeadas.
    */
    List<String> senders =
            messages.stream()
                    .filter(m -> !m.body().isBlank() && !m.isRead())
                    .map(Message::sender)
                    .distinct()
                    .sorted()
                    .toList();

    IO.println("->" + frequentSender);
    IO.println("->" + senders);
}
