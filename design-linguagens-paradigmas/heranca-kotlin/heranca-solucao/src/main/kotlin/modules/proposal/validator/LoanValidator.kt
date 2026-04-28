package org.example.modules.proposal.validator

import org.example.modules.proposal.entities.LoanProposal
import org.example.modules.proposal.validator.errors.BusinesError

/*
    Fatos observaveis neste arquivo:
    - `LoanValidator` compoe uma lista de `Validator`; ele nao especializa outro validador via heranca.
    - Cada implementacao concentra uma regra e devolve seus proprios erros.
    - Nao ha lista interna de erros sendo reutilizada entre chamadas.

    Consequencia pratica:
    - Para adicionar uma nova regra, basta criar outro `Validator`
      e inclui-lo na lista recebida pelo orquestrador.
*/
class LoanValidator(
    private val validators: List<Validator>
) {
    // Responsabilidade unica: coordenar os componentes recebidos
    // e agregar os erros produzidos por cada contrato.
    fun validate(proposal: LoanProposal): List<BusinesError> =
        validators.flatMap { it.validate(proposal) }
}

class CreditScoreValidator : Validator {
    // Regra isolada e sem estado mutavel.
    // Cada execucao depende apenas da proposta recebida.
    override fun validate(proposal: LoanProposal) =
        if (proposal.creditScore < 500)
            listOf(BusinesError("CREDIT_SCORE_TOO_LOW", "Credit score is too low"))
        else emptyList()
}

// `minIncome` representa a configuracao da regra.
// A variacao acontece por parametro, nao por subclasses ou por estado compartilhado.
class IncomeValidator(private val minIncome: Int) : Validator {
    // A proposta muda a cada chamada; a configuracao do validador continua a mesma.
    // Isso deixa explicita a separacao entre contrato, parametro e dado validado.
    override fun validate(proposal: LoanProposal) =
        if (proposal.income < minIncome)
            listOf(BusinesError("INCOME_TOO_LOW", "Income is too low"))
        else emptyList()
}
