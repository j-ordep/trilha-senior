public record Message(String sender, String body, Boolean isRead) {
    public Message(String sender, String body) {
        this(sender, body, false);
    }
}

void main() {
    final var messages = List.of(
            new Message("Victor", "mensagem 1"),
            new Message("Alisson", "mensagem 2"),
            new Message("Victor", "mensagem 3"),
            new Message("Victor", "mensagem 4")
    );

    var frequentSender = messages.stream()
            .collect(Collectors.groupingBy(Message::sender)) // "Victor" -> [Message1, Message2, Message3], "Alisson" -> [Message3] == Map<String, List<Message>>
            .entrySet() // Um Map não é iterável diretamente sobre chave + valor juntos, com o entreySet() conseguimos pegar Key e Value juntos. Set<Map.Entry<String, List<Message>>>
            .stream()
            .max(Comparator.comparingInt(entry -> entry.getValue().size()))
            .map(Map.Entry::getKey)
            .orElse("Unknown sender");

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