package main

import (
	"errors"
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

var (
	// Sentinel errors permitem que o chamador teste categorias de falha com errors.Is,
	// mesmo quando a mensagem final for enriquecida com contexto por wrapping.
	ErrOrderNotFound      = errors.New("order not found")
	ErrInvalidOrderID     = errors.New("invalid order ID")
	ErrOrderNil           = errors.New("order is nil")
	ErrInvalidCustomerID  = errors.New("invalid customer ID")
	ErrInvalidTotalAmount = errors.New("invalid total amount")
)

func FindOrder(id string) (*Order, error) {
	if _, err := uuid.Parse(id); err != nil {
		// O wrapping com %w preserva a identidade da causa para inspeção posterior com errors.Is.
		return nil, fmt.Errorf("invalid order id %w", ErrInvalidOrderID)
	}

	// ...
	return nil, fmt.Errorf("failed to find order %q: %w", id, ErrOrderNotFound)
}

func ValidateOrder(order *Order) error {
	if order == nil {
		// O nil check é importante porque acessar campos de um ponteiro nulo causaria panic em runtime.
		return ErrOrderNil
	}

	var errs []error

	if _, err := uuid.Parse(order.CustomerID); err != nil {
		// Cada validação adiciona seu próprio erro para que o chamador veja o conjunto de problemas de uma vez.
		errs = append(errs, fmt.Errorf("invalid customer id %w", ErrInvalidCustomerID))
	}

	if order.TotalAmount <= 0 {
		// Neste exemplo, valor inválido é tratado como erro de validação porque impede o pedido de seguir no fluxo.
		errs = append(errs, fmt.Errorf("total amount must be greater than zero %w", ErrInvalidTotalAmount))
	}

	if len(errs) > 0 {
		// errors.Join agrega múltiplas falhas sem perder a possibilidade de testar cada sentinel individualmente.
		return errors.Join(errs...)
	}

	return nil
}

// APIError carrega detalhes estruturados de uma falha externa.
// O ponto principal não é substituir sentinel errors, mas permitir inspeção de metadados via errors.As.
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
	if errors.Is(err, ErrInvalidTotalAmount) {
		// errors.Is consulta a cadeia de erros sem depender da mensagem textual, o que deixa a checagem mais estável.
		fmt.Println(err)
		return
	}

	err = fetchAPI()
	var apiErr *APIError

	if errors.As(err, &apiErr) {
		// errors.As é útil quando o chamador precisa extrair um tipo concreto e ler campos além da mensagem.
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
