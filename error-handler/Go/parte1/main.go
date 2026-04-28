package main

import (
	"fmt"
	"log"
	"os"
)

func ReadFile(path string) ([]byte, error) {
	file, err := os.ReadFile(path)
	if err != nil {
		/*
		Contexto:
		- Esta função só tenta ler o arquivo e devolve a falha ao chamador.

		Decisão de design:
		- O erro é retornado como valor, sem fazer logging aqui, para manter a função reutilizável e evitar logs duplicados.

		Aprendizado:
		- Em Go, o ponto de logging costuma ficar na borda do sistema, onde existe contexto suficiente para decidir severidade e formato.
		*/
		return nil, err
	}

	return file, nil
}

func main() {
	file, err := ReadFile("a.txt")
	if err != nil {
		// Aqui o programa está na borda da aplicação, então já faz sentido registrar a falha observável.
		log.Println(err)
	}

	fmt.Println(string(file))
}
