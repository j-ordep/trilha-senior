package org.example.modules.proposal.validator

import org.example.modules.proposal.entities.LoanProposal
import org.example.modules.proposal.validator.errors.BusinesError

// por padão classes em Kotlin são final (sem herança)
// open = “essa classe pode ser herdada”
// val = imutavel - var = mutavel
// protected = só a classe e suas subclasses podem acessar

open class LoanValidator {
    protected var errors = mutableListOf<BusinesError>()

    fun validate(proposal: LoanProposal): List<BusinesError> {
        validateCreditScore(proposal)
        validateIncome(proposal)
        return errors
    }

    private fun validateCreditScore(proposal: LoanProposal) {
        if (proposal.creditScore < 500) {
            errors.add(BusinesError("CREDIT_SCORE_TOO_LOW", "Credit score is too low"))
        }
    }

    open fun validateIncome(proposal: LoanProposal) {
        if (proposal.income < 1000) {
            errors.add(BusinesError("INCOME_TOO_LOW", "Income is too low"))
        }
    }

}

class PremiumLoanValidator: LoanValidator() {
    override fun validateIncome(proposal: LoanProposal) {
        if (proposal.income < 3000) {
            errors.add(BusinesError("INCOME_TOO_LOW", "Income is too low"))
        }
    }
}