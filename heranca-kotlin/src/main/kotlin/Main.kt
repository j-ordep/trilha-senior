package org.example

import org.example.modules.proposal.entities.LoanProposalimport org.example.modules.proposal.validator.PremiumLoanValidator

fun main() {
    val validator = PremiumLoanValidator()
    val proposal = LoanProposal(income = 1000, creditScore = 500, creditAmount = 1000.toBigDecimal())
    val errors = validator.validate(proposal)
    println(errors)


}