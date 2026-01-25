import tilha.senior.ex02.Message;

void main() {
/* Diferenças de Arrays.asList para List.of:

    Imutabilidade: List.of(...) retorna uma lista verdadeiramente imutável (não permite add, remove nem set).
    Arrays.asList(...) retorna uma lista de tamanho fixo backed por um array — permite set mas lança UnsupportedOperationException em add/remove.

    null: List.of lança NullPointerException se algum elemento for null; Arrays.asList aceita null.
    Array de primitivos: Arrays.asList(intArray) cria uma List<int[]> (comportamento inesperado); List.of com varargs evita esse problema.

    Uso recomendado: prefira List.of para listas pequenas e imutáveis (literal de dados). Se precisa de mutabilidade, crie new ArrayList<>(List.of(...))
    ou new ArrayList<>(Arrays.asList(...)).
*/

    final var messages = List.of(
            new Message("Victor", "texto 1"),
            new Message("Alisson", "texto 2"),
            new Message("Victor", "texto 3"),
            new Message("Victor", "texto 4")
    );

    final var senderCounts = Message.countMessageBySender(messages);
    final var topSender = Message.getMostFrequentSender(senderCounts);
    final var uniqueSenders = Message.getSortedUniqueSenders(messages);

    IO.println(senderCounts);
    IO.println(topSender);
    IO.println(uniqueSenders);

}
