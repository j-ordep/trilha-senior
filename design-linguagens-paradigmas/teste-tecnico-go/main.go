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

func NewMessage(sender, body string, isRead bool) Message {
	return Message{
		Sender: sender,
		Body:   body,
		IsRead: isRead,
	}
}

func CountBySender(messages []Message) map[string]int {
	messageCountBySender := make(map[string]int)

	for _, message := range messages {
		messageCountBySender[message.Sender]++ // se o sender não tá no map ele já faz um put automaticamente, além de somar +1 o Value
	}

	return messageCountBySender
}

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

func UniqueSenders(messages []Message) []string {
	var senderList []string

	for _, message := range messages {
		isMessageValid := strings.TrimSpace(message.Body) != "" && !message.IsRead

		if isMessageValid && !slices.Contains(senderList, message.Sender) { // slices.contains pois devem ser remetentes unicos
			senderList = append(senderList, message.Sender)
		}
	}
	sort.Strings(senderList)
	return senderList
}

func main() {
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
