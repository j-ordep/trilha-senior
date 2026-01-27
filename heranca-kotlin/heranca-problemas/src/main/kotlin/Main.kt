package org.example

import org.example.modules.proposal.entities.LoanProposal
import org.example.modules.proposal.validator.PremiumLoanValidator

/*
    as duas listas de erros (errors e newErrors) terão erros, mas deveriam estar vazias, pois o income deveria passar pela validação
*/

fun main() {
    val validator = PremiumLoanValidator()
    val proposal = LoanProposal(income = 1000, creditScore = 500, creditAmount = 1000.toBigDecimal())
    val errors = validator.validate(proposal)
    println(errors)

    val newErrors = validator.validate(proposal.copy(income = 3000))
    println(newErrors)

}