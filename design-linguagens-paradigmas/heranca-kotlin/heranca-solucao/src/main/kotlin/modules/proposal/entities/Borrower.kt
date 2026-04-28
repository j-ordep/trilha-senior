package org.example.modules.proposal.entities

import java.time.LocalDate

// `data class` representa um agregado de dados com baixo acoplamento:
// as propriedades sao `val`, o estado nasce completo no construtor
// e nao depende de hierarquia para compartilhar comportamento.
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

// `complement` usa null safety (`String?`) para deixar explicito
// que esse campo pode nao existir.
data class Address(
    val street: String,
    val number: String,
    val complement: String? = null,
    val neighborhood: String,
    val city: String,
    val state: String,
    val zipCode: String
)
