package tilha.senior.ex02;

import java.util.*;

// POO e Imperativa (Procedural)

public record Message(String sender, String body, Boolean isRead){

    public Message(String sender, String body) {
        this(sender, body, false);
    }

    // somente conta a quantidade de mensagens por usuario(sender)
    public static Map<String, Integer> countMessageBySender(final List<Message> messages) {
        final var sendCounts = new HashMap<String, Integer>();

        // aqui ocorre mutabilidade quando alteramos o valor no map(sendCounts.put()), mas somente dentro da função
        for (Message message : messages) { // O(N)
            final var updatedCount = sendCounts.getOrDefault(message.sender(), 0) + 1; // "vitor ta ai? se não, o valor é 0 + 1 ou seja <Victor, 1>
            sendCounts.put(message.sender(), updatedCount); // put substitui o valor associado a chave
        }

        // evita o vazamento de sendCounts, imutabilidade, tornando função PURA, sem vazamentos, evitando Race Condition
        return Map.copyOf(sendCounts);
    };


    /*
        entrySet() retorna a chave e o valor do map
        - entry.getKey()
        - entry.getValue()

        poderia usar:
            for (String key : senderCounts.keySet()) {
                Integer value = senderCounts.get(key);
            }

        mas isso faria 2 operações, senderCounts.entrySet() resolve
    */
    public static String getMostFrequentSender(Map<String, Integer> senderCounts) {
        String topSender = null;
        var topCount = -1;

        for(final var entry : senderCounts.entrySet()) {
            if (entry.getValue() > topCount) {
                topSender = entry.getKey();
                topCount = entry.getValue();
            }
        }
        return topSender;
    }

    public static List<String> getSortedUniqueSenders(List<Message> messages) {
        final var senders = new TreeSet<String>(); // TreeSet ja garante ordem
        for (final var message : messages) {
            if (message.body() != null && !message.body().trim().isEmpty() && !message.isRead()) {
                senders.add(message.sender());
            }
        }
        return new ArrayList<>(senders); // copia o senders(que é um TreeSet) para um ArrayList
    }

}
