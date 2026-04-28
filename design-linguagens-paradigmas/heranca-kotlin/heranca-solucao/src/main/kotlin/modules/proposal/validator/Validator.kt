package org.example.modules.proposal.validator

import org.example.modules.proposal.entities.LoanProposal
import org.example.modules.proposal.validator.errors.BusinesError

// A interface define o contrato minimo para entrar na composicao.
// `LoanValidator` depende dessa abstracao, o que reduz acoplamento
// e permite trocar implementacoes sem alterar o orquestrador.
interface Validator  {
    fun validate(proposal: LoanProposal): List<BusinesError>
}
