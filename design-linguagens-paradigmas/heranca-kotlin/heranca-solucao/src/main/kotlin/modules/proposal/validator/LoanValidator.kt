package org.example.modules.proposal.validator

import org.example.modules.proposal.entities.LoanProposal
import org.example.modules.proposal.validator.errors.BusinesError

/*
    - Elimina estado compartilhado
    - Cada classe tem uma única responsabilidade (SRP)
    - Aberto para extensão, fechado para modificação (OCP)
*/
class LoanValidator(
    private val validators: List<Validator>
) {
    fun validate(proposal: LoanProposal): List<BusinesError> =
        validators.flatMap { it.validate(proposal) }
}

class CreditScoreValidator : Validator {
    override fun validate(proposal: LoanProposal) =
        if (proposal.creditScore < 500)
            listOf(BusinesError("CREDIT_SCORE_TOO_LOW", "Credit score is too low"))
        else emptyList()
}

// parametro que muda menos
class IncomeValidator(private val minIncome: Int) : Validator {
    // parametro que muda mais
    override fun validate(proposal: LoanProposal) =
        if (proposal.income < minIncome)
            listOf(BusinesError("INCOME_TOO_LOW", "Income is too low"))
        else emptyList()
}
