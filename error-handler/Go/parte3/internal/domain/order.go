package domain

import (
	"errors"
	"time"

	"github.com/google/uuid"
)

var (
	// O pacote domain concentra sentinel errors reutilizaveis para manter a
	// semantica das falhas consistente entre camadas.
	ErrOrderNotFound = errors.New("order not found")
	ErrInvalidOrder  = errors.New("invalid order")
)

// Order representa a entidade principal manipulada pelo dominio deste exercicio.
//
// Ponto de atencao:
// - A struct guarda apenas estado. Regras de validacao ou persistencia precisam
//   ser tratadas por outras partes do sistema.
type Order struct {
	ID        uuid.UUID
	Customer  string
	Amount    float64
	CreatedAt time.Time
	UpdatedAt time.Time
}

// OrderRepository descreve as operacoes esperadas de persistencia.
//
// Conceito principal:
// - Trata-se de uma interface implicita de Go: qualquer tipo com esses metodos e
//   mesmas assinaturas passa a satisfazer o contrato automaticamente.
//
// Decisao de design:
// - Manter a interface no dominio reduz acoplamento com um repositorio concreto,
//   como a implementacao em memoria deste modulo.
type OrderRepository interface {
	Create(order *Order) error
	GetByID(id uuid.UUID) (*Order, error)
	Update(id uuid.UUID) error
	Delete(id uuid.UUID) error
	List() ([]*Order, error)
}
