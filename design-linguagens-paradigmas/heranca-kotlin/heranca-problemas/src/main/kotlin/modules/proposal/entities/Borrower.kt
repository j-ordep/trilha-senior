package org.example.modules.proposal.entities

import java.time.LocalDate

// Entidade de dominio mantida aqui apenas para dar contexto ao modulo.
// Ela nao participa diretamente do problema de heranca demonstrado no validador.
//
// Ponto didatico: usar `val` nas propriedades impede reatribuir cada campo,
// mas isso nao torna automaticamente todo o grafo de objetos "imutavel" em sentido amplo.
// O problema principal deste exemplo esta no estado mutavel guardado pelo validador, nao nesta entidade.
data class Borrower(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val cpf: String,
    val birthDate: LocalDate,
    val address: Address,
    val isActive: Boolean = true
)

// `Address` tambem e apenas um value object de apoio.
// A presenca de data classes no modelo nao impede, por si so, que outro ponto do design
// concentre estado compartilhado e produza efeito colateral entre execucoes.
data class Address(
    val street: String,
    val number: String,
    val complement: String? = null,
    val neighborhood: String,
    val city: String,
    val state: String,
    val zipCode: String
)
