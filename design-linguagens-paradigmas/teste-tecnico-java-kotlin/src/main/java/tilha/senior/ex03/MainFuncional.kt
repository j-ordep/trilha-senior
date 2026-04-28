package tilha.senior.ex03// Programação funcional com coleções de Kotlin.

data class Message(
    val Sender: String,
    val Body: String,
    val IsRead: Boolean = false
)

// `val` garante imutabilidade de referência para a lista; o exemplo explora transformação de dados, não reatribuição.
val messages = listOf(
    Message("Victor", "mensagem 1"),
    Message("Alisson", "mensagem 2"),
    Message("Victor", "mensagem 3"),
    Message("Victor", "mensagem 4")
)

fun main() {

    /*
    Contexto:
    - A pipeline agrupa as mensagens por remetente e escolhe o grupo com maior quantidade.

    Conceito principal:
    - Em Kotlin, funções de coleção como groupBy e maxByOrNull permitem compor a regra sem loops explícitos.
    */
    val frequentSender = messages
        .groupBy { it.Sender } // O resultado é um Map<String, List<Message>> em que cada chave aponta para as mensagens do remetente.
        .maxByOrNull { (_, message) -> message.size } // O lambda usa apenas o tamanho do grupo; a chave não participa da comparação.
        ?.key ?: "Unknown sender" // O operador Elvis define um fallback explícito caso a coleção esteja vazia.

    println("-> $frequentSender")

    /*
    Aprendizado:
    - Esta segunda pipeline evidencia o estilo funcional: cada etapa produz uma nova visão da coleção anterior.

    Ponto de atenção:
    - `map`, `distinct` e `sorted` descrevem transformação e não mutação in-place; isso ajuda a comparar esta solução com a versão imperativa.
    */
    val senders = messages
        .filter { it.Body.isNotBlank() && !it.IsRead }
        .map { it.Sender } // `map` projeta cada Message para o campo relevante nesta etapa.
        .distinct() // remove duplicatas sem precisar de estrutura auxiliar explícita
        .sorted()

    println("-> $senders")
}
