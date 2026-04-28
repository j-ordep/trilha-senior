package tilha.senior.ex02;

import java.util.*;

// Exemplo em Java que combina modelagem simples por record com processamento imperativo.

public record Message(String sender, String body, Boolean isRead){

    public Message(String sender, String body) {
        this(sender, body, false);
    }

    /*
    Contexto:
    - Este método acumula a quantidade de mensagens por remetente.

    Decisão de design:
    - O HashMap é mutável apenas dentro do método, o que simplifica a contagem sem expor estado compartilhado.
    */
    public static Map<String, Integer> countMessageBySender(final List<Message> messages) {
        final var sendCounts = new HashMap<String, Integer>();

        // A mutabilidade fica restrita ao escopo local, o que é comum em soluções imperativas que acumulam resultado passo a passo.
        for (Message message : messages) { // O(N)
            final var updatedCount = sendCounts.getOrDefault(message.sender(), 0) + 1; // getOrDefault evita uma verificação manual para a primeira ocorrência da chave.
            sendCounts.put(message.sender(), updatedCount); // put atualiza a entrada existente ou cria uma nova.
        }

        // Map.copyOf entrega uma visão imutável do resultado e evita que o chamador altere o acumulador interno.
        return Map.copyOf(sendCounts);
    };


    /*
    Conceito principal:
    - entrySet() expõe cada par chave/valor como Map.Entry, o que é apropriado quando a decisão depende das duas informações.

    Aprendizado:
    - Em comparação com iterar sobre keySet() e depois chamar get(key), entrySet() evita uma busca adicional e deixa a intenção mais clara.
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
        /*
        Contexto:
        - Este método precisa devolver remetentes únicos já ordenados.

        Decisão de design:
        - TreeSet combina deduplicação e ordenação natural na mesma estrutura, o que reduz código auxiliar em relação a List + sort.
        */
        final var senders = new TreeSet<String>(); // TreeSet garante unicidade e ordem durante a inserção.
        for (final var message : messages) {
            if (message.body() != null && !message.body().trim().isEmpty() && !message.isRead()) {
                senders.add(message.sender());
            }
        }
        return new ArrayList<>(senders); // A cópia final preserva a assinatura baseada em List sem expor a implementação interna.
    }

}
