package main

import (
	"fmt"
	"slices"
	"sort"
	"strings"
)

type Message struct {
	Sender string
	Body   string
	IsRead bool
}

// NewMessage centraliza a inicialização do valor e deixa explícitos os campos
// usados no exercício. Em Go isso é opcional, mas um construtor pequeno ajuda
// a manter a criação consistente quando o tipo começa a ganhar regras.
func NewMessage(sender, body string, isRead bool) Message {
	return Message{
		Sender: sender,
		Body:   body,
		IsRead: isRead,
	}
}

// CountBySender agrega as mensagens por remetente em um map.
// Quando a chave ainda não existe, Go retorna o valor zero do tipo ao ler o
// map; por isso o incremento funciona sem inicialização manual.
func CountBySender(messages []Message) map[string]int {
	messageCountBySender := make(map[string]int)

	for _, message := range messages {
		messageCountBySender[message.Sender]++
	}

	return messageCountBySender
}

// TopSender percorre a contagem agregada e mantém o maior valor encontrado.
// Como maps não preservam ordem de iteração em Go, empates não têm desempate
// estável e podem retornar remetentes diferentes entre execuções.
func TopSender(messages map[string]int) string {
	var topSender string
	maxMessages := -1

	for sender, count := range messages {
		if count > maxMessages {
			topSender = sender
			maxMessages = count
		}
	}

	return topSender
}

// UniqueSenders filtra remetentes com mensagem não vazia e ainda não lida,
// evitando duplicatas antes de ordenar o resultado final.
//
// Ponto de atenção:
// - slices.Contains simplifica o exemplo, mas faz busca linear. Para coleções
//   maiores, um map auxiliar seria mais eficiente para controlar unicidade.
func UniqueSenders(messages []Message) []string {
	var senderList []string

	for _, message := range messages {
		isMessageValid := strings.TrimSpace(message.Body) != "" && !message.IsRead

		if isMessageValid && !slices.Contains(senderList, message.Sender) {
			senderList = append(senderList, message.Sender)
		}
	}
	sort.Strings(senderList)
	return senderList
}

func main() {
	// O fluxo principal prepara dados em memória e exercita as funções sem I/O
	// além da saída padrão, mantendo o foco do exemplo em slices, maps e
	// transformações simples sobre coleções.
	message1 := NewMessage("Victor", "mensagem1", false)
	message2 := NewMessage("Victor", "mensagem2", false)
	message3 := NewMessage("Alisson", "mensagem3", false)
	message4 := NewMessage("Victor", "mensagem4", false)

	messages := make([]Message, 0)
	messages = append(messages, message1, message2, message3, message4)

	counts := CountBySender(messages)
	fmt.Println(counts)
	topSender := TopSender(counts)
	fmt.Println(topSender)
	uniqueSender := UniqueSenders(messages)
	fmt.Println(uniqueSender)
}
