package org.example

import org.example.modules.proposal.entities.LoanProposal
import org.example.modules.proposal.validator.CreditScoreValidator
import org.example.modules.proposal.validator.IncomeValidator
import org.example.modules.proposal.validator.LoanValidator

// TODO transformar em uma solução de composição e/ou interfaces

/*
    as duas listas de erros (errors e newErrors) terão erros, mas deveriam estar vazias, pois o income deveria passar pela validação
*/

fun main() {
    val validator = LoanValidator(
        listOf(
            CreditScoreValidator(),
            IncomeValidator(minIncome = 3000)
        )
    )

    val proposal = LoanProposal(income = 1000, creditScore = 500, creditAmount = 1000.toBigDecimal())
    val errors = validator.validate(proposal)
    println(errors)

    val newErrors = validator.validate(proposal.copy(income = 3000))
    println(newErrors)

}