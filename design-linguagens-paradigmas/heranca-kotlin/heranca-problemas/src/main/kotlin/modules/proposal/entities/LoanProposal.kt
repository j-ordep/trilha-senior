package org.example.modules.proposal.entities

import java.math.BigDecimal

// Modelo simples de entrada para a validacao.
// Como e uma `data class`, o `copy(...)` gera outra instancia com alteracoes pontuais.
//
// Isso ajuda a destacar um detalhe importante do problema: o vazamento de erro nao acontece
// porque a proposta foi mutada, e sim porque o validador preserva estado entre chamadas.
data class LoanProposal (
    val creditAmount: BigDecimal,
    val creditScore: Int,
    val income: Int
)
