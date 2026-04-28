package org.example.modules.proposal.entities

import java.math.BigDecimal

// A proposta e um dado de entrada simples.
// Como a modelagem fica em uma `data class`, as regras podem viver em componentes externos
// e ser compostas sem acoplar validacao ao proprio modelo.
data class LoanProposal (
    val creditAmount: BigDecimal,
    val creditScore: Int,
    val income: Int
)
