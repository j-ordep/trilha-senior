package org.example

import org.example.modules.proposal.entities.LoanProposal
import org.example.modules.proposal.validator.CreditScoreValidator
import org.example.modules.proposal.validator.IncomeValidator
import org.example.modules.proposal.validator.LoanValidator

// Este modulo exemplifica a solucao por composicao:
// `LoanValidator` recebe validadores prontos e combina seus resultados,
// em vez de herdar comportamento de uma classe base cheia de regras.
fun main() {
    val validator = LoanValidator(
        listOf(
            // Cada validador implementa o mesmo contrato (`Validator`),
            // mas cuida de uma regra especifica.
            CreditScoreValidator(),
            // A renda minima entra como parametro de configuracao.
            // Assim, a regra varia sem depender de estado mutavel compartilhado.
            IncomeValidator(minIncome = 3000)
        )
    )

    val proposal = LoanProposal(income = 1000, creditScore = 500, creditAmount = 1000.toBigDecimal())
    val errors = validator.validate(proposal)
    println(errors)

    val newErrors = validator.validate(proposal.copy(income = 3000))
    println(newErrors)

}
