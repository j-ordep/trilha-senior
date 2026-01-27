package tilha.senior.ex03//  Programação Funcional

data class Message(
    val Sender: String,
    val Body: String,
    val IsRead: Boolean = false
)

val messages = listOf(
    Message("Victor", "mensagem 1"),
    Message("Alisson", "mensagem 2"),
    Message("Victor", "mensagem 3"),
    Message("Victor", "mensagem 4")
)

fun main() {

    // conta quantas mensagens cada rementete enviou e tambem o remetente que mais enviou mensagem
    val frequentSender = messages
        // .groupBy { message -> message.Sender } dado esse parametro(message), me retorne agrupado por (message.Sender)
        .groupBy { it.Sender } // o map fica como uma key e uma lista de mensagens => "Victor": ["mensagem 1", "mensagem 2"], "Alisson" -> [Message2]
        .maxByOrNull { (key, message) -> message.size } // compara os maps, e retorna o map<key, message> da que tiver mais valores(message)
        ?.key ?: "Unknown sender" // se existir resultado retorne somente a key em vez do map inteiro

    println("-> $frequentSender")

    // ordena a lista, trazendo somente os senders
    val senders = messages
        .filter { it.Body.isNotBlank() && !it.IsRead }
        .map { it.Sender } // transforma List<Message> em List<String> - map tem uma ideia de TRANSFORMAÇÃO IMUTAVEL
        .distinct() // remove duplicatas
        .sorted()

    println("-> $senders")
}
