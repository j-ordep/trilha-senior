package main

import (
	"errors"
	"fmt"

	"github.com/google/uuid"
)

var (
	// Sentinel errors representam categorias estaveis de falha e permitem
	// comparacao com errors.Is sem depender do texto da mensagem.
	ErrOrderNotFound      = errors.New("order not found")
	ErrInvalidOrderID     = errors.New("invalid order ID")
	ErrOrderNil           = errors.New("order is nil")
	ErrInvalidCustomerID  = errors.New("invalid customer ID")
	ErrInvalidTotalAmount = errors.New("invalid total amount")
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

func FindOrder(id string) (*Order, error) {
	// Contexto:
	// - A validacao do UUID acontece antes da busca para interromper cedo entradas
	//   claramente invalidas.
	//
	// Conceito principal:
	// - O retorno explicito de erro usa %w para encapsular a causa sem perder a
	//   possibilidade de comparacao por errors.Is.
	if _, err := uuid.Parse(id); err != nil {
		return nil, fmt.Errorf("invalid order id %w", ErrInvalidOrderID)
	}

	// Inferencia:
	// - O acesso ao armazenamento foi omitido; o exemplo concentra a atencao na
	//   modelagem do erro de "nao encontrado".
	return nil, fmt.Errorf("failed to find order %q: %w", id, ErrOrderNotFound)
}

func ValidateOrder(order *Order) error {
	// Como a funcao recebe um ponteiro, a checagem de nil evita panic ao acessar
	// campos de um pedido ausente.
	if order == nil {
		return ErrOrderNil
	}

	var errs []error

	if _, err := uuid.Parse(order.CustomerID); err != nil {
		errs = append(errs, fmt.Errorf("invalid customer id %w", ErrInvalidCustomerID))
	}

	// Decisao de design:
	// - As validacoes acumulam erros independentes para que o chamador consiga
	//   enxergar mais de um problema na mesma rodada.
	if order.TotalAmount <= 0 {
		errs = append(errs, fmt.Errorf("total amount must be greater than zero %w", ErrInvalidTotalAmount))
	}

	if len(errs) > 0 {
		// errors.Join preserva cada causa individual, o que permite continuar usando
		// errors.Is mesmo com varias falhas agregadas.
		return errors.Join(errs...)
	}

	return nil
}

// APIError representa um erro tipado de integracao com metadados extras.
//
// Conceito principal:
// - Em Go, a interface error e implicita: basta expor Error() string para o tipo
//   passar a satisfaze-la, sem declaracao adicional.
type APIError struct {
	Status      string
	Message     string
	IsRetryable bool
}

func (e *APIError) Error() string {
	return fmt.Sprintf("%s: %s", e.Status, e.Message)
}

func fetchAPI() error {
	return &APIError{Status: "500", Message: "Internal server error", IsRetryable: true}
}

func main() {
	_, err := FindOrder("xpto")

	err = ValidateOrder(&Order{})
	// Mesmo com errors.Join, errors.Is continua sendo a forma idiomatica de checar
	// se uma sentinel error especifica participa da cadeia de falhas.
	if errors.Is(err, ErrInvalidTotalAmount) {
		fmt.Println(err)
		return
	}

	err = fetchAPI()
	var apiErr *APIError

	// errors.As e apropriado quando o chamador precisa recuperar um tipo concreto
	// para acessar campos adicionais, e nao apenas classificar a falha.
	if errors.As(err, &apiErr) {
		fmt.Println(apiErr.Error())
		fmt.Printf("%s: %s", apiErr.Status, apiErr.Message)
		return
	}

	if errors.Is(err, ErrInvalidCustomerID) {
		// ...
		fmt.Println("Error:", err)
		return
	}

	if errors.Is(err, ErrOrderNotFound) {
		// ...
		fmt.Println("Error:", err)
	}

}
