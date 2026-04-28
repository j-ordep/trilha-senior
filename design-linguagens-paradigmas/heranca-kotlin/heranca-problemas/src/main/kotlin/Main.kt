package org.example

import org.example.modules.proposal.entities.LoanProposal
import org.example.modules.proposal.validator.PremiumLoanValidator

/*
    Este exemplo existe para evidenciar o problema desta versao do modulo.

    Fato observavel:
    - as duas listas impressas acumulam erro, mesmo quando a segunda proposta ja deveria
      passar na regra de renda do validador premium.

    O ponto de aprendizagem nao e "a regra premium esta errada", e sim que a abordagem por
    heranca deixa um estado mutavel (`errors`) preso a instancia do validador. Quando a mesma
    instancia e reutilizada, o resultado de uma validacao vaza para a seguinte.
*/

fun main() {
    val validator = PremiumLoanValidator()
    val proposal = LoanProposal(income = 1000, creditScore = 500, creditAmount = 1000.toBigDecimal())

    // A primeira chamada registra erros na lista interna do validador.
    val errors = validator.validate(proposal)
    println(errors)

    // Mesmo mudando apenas a renda para um valor que deveria passar na regra sobrescrita,
    // o historico anterior continua presente porque o estado interno nao foi isolado por validacao.
    val newErrors = validator.validate(proposal.copy(income = 3000))
    println(newErrors)

}
