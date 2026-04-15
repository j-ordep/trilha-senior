package main

import (
	"fmt"
	"log"
	"os"
)

func ReadFile(path string) ([]byte, error) {
	file, err := os.ReadFile(path)
	if err != nil {
		// log.Println(err) - logs devem ser criados sempre na borda do sistema
		return nil, err
	}
	
	return file, nil
}

func main() {
	file, err := ReadFile("a.txt")
	if err != nil {
		log.Println(err)
	}

	fmt.Println(string(file))
}