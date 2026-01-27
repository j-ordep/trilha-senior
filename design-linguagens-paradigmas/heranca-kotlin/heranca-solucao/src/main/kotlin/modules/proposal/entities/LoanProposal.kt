package org.example.modules.proposal.entities

import java.math.BigDecimal

data class LoanProposal (
    val creditAmount: BigDecimal,
    val creditScore: Int,
    val income: Int
)