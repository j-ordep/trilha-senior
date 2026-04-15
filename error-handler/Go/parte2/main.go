package main

import (
	"fmt"

	"github.com/google/uuid"
)

type OrderItem struct {
	ProductID string
	Price     float64
	Quantity  int
}

type Order struct {
	ID          string
	CustomerID  string
	TotalAmount float64
	Status      string
	Items       []OrderItem
}

/*
	O que acontece exatamente quando retorna erro dentro do go, no ponto de vista de alocação, de heap, da stack?
*/

func findOrder(id string) (*Order, error) {
	if _, err := uuid.Parse(id); err != nil {
		return nil, err
	}
	// ...
	return nil, fmt.Errorf("failed to find order %q", id)
}

func main() {
	_, err := findOrder("123e4567-e89b-12d3-a456-426614174000")
	if err != nil {
		fmt.Println("Error:", err)
	}
}
