package org.example.modules.proposal.validator

import org.example.modules.proposal.entities.LoanProposal
import org.example.modules.proposal.validator.errors.BusinesError

// Em Kotlin, classes e metodos sao `final` por padrao.
// Aqui a classe foi marcada como `open` para permitir extensao por heranca.
//
// Este arquivo representa justamente a versao "problematica":
// a variacao de comportamento foi empurrada para subclasses, enquanto a lista de erros
// ficou como estado mutavel compartilhado dentro da instancia base.
open class LoanValidator {
    // `protected` expoe o mesmo estado para a classe base e para subclasses.
    // Isso facilita o override, mas tambem acopla todas as regras a mesma lista mutavel.
    //
    // Observacao de linguagem: `var` significa que a referencia pode ser reatribuida;
    // alem disso, `mutableListOf(...)` produz uma colecao mutavel. Ou seja, ha estado alteravel
    // suficiente para que uma execucao deixe residuos para a proxima.
    protected var errors = mutableListOf<BusinesError>()

    fun validate(proposal: LoanProposal): List<BusinesError> {
        // O metodo executa as regras na mesma instancia e devolve a propria lista interna.
        // Como a lista nao e recriada nem limpa aqui, o contrato fica sensivel a ordem e a
        // quantidade de chamadas feitas no mesmo objeto.
        validateCreditScore(proposal)
        validateIncome(proposal)
        return errors
    }

    private fun validateCreditScore(proposal: LoanProposal) {
        if (proposal.creditScore < 500) {
            errors.add(BusinesError("CREDIT_SCORE_TOO_LOW", "Credit score is too low"))
        }
    }

    // `open` no metodo permite que subclasses troquem apenas esta parte da validacao.
    // O trade-off e que a variacao da regra passa a depender de heranca, mas o armazenamento
    // do resultado continua centralizado e compartilhado na superclasse.
    open fun validateIncome(proposal: LoanProposal) {
        if (proposal.income < 1000) {
            errors.add(BusinesError("INCOME_TOO_LOW", "Income is too low"))
        }
    }

}

// A subclasse muda somente o criterio de renda, o que mostra a intencao original de especializar
// comportamento com `override`.
//
// A limitacao desta abordagem e que a regra especializada nao recebe um resultado isolado para preencher;
// ela continua escrevendo na mesma lista herdada da superclasse. O problema, portanto, nao e o `override`
// em si, e sim o fato de comportamento variavel e estado compartilhado estarem acoplados.
class PremiumLoanValidator: LoanValidator() {
    override fun validateIncome(proposal: LoanProposal) {
        if (proposal.income < 3000) {
            errors.add(BusinesError("INCOME_TOO_LOW", "Income is too low"))
        }
    }
}
