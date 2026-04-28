package repository

import (
	"error-handler/internal/domain"
	"sync"
	"time"

	"github.com/google/uuid"
)

type InMemoryOrderRepository struct {
	// O mutex protege o mapa contra acesso concorrente. Mapas nativos de Go nao
	// sao seguros para escrita concorrente sem sincronizacao explicita.
	mu sync.RWMutex
	orders map[uuid.UUID]*domain.Order
}

func NewInMemroyOrderRepository() *InMemoryOrderRepository {
	// O construtor inicializa o mapa para que o repositorio possa receber escritas
	// imediatamente, sem depender de preparo externo.
	return &InMemoryOrderRepository{
		orders: make(map[uuid.UUID]*domain.Order),
	}
}

func (r *InMemoryOrderRepository) Create(order *domain.Order) error {
	// Contexto:
	// - Este repositorio em memoria encapsula detalhes operacionais simples para
	//   manter o exemplo focado em contrato e tratamento de erro.
	r.mu.Lock()
	defer r.mu.Unlock()

	if order.ID == uuid.Nil {
		// Gerar o ID aqui simplifica o uso do repositorio, embora em sistemas
		// maiores essa responsabilidade tambem possa ficar em uma camada de servico.
		order.ID = uuid.New()
	}

	if _, ok := r.orders[order.ID]; ok {
		return domain.ErrInvalidOrder
	}

	if order.CreatedAt.IsZero() {
		order.CreatedAt = time.Now()
	}
	// No primeiro salvamento, UpdatedAt nasce igual a CreatedAt porque ainda nao
	// houve alteracao posterior ao registro inicial.
	order.UpdatedAt = order.CreatedAt

	// Ponto de atencao:
	// - O mapa armazena o mesmo ponteiro recebido. Isso evita copia da struct, mas
	//   qualquer mutacao externa nesse objeto afeta o estado interno do repositorio.
	r.orders[order.ID] = order

	return nil
}
