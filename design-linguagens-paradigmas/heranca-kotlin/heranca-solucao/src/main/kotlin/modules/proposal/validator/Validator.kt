package org.example.modules.proposal.validator

import org.example.modules.proposal.entities.LoanProposal
import org.example.modules.proposal.validator.errors.BusinesError

interface Validator  {
    fun validate(proposal: LoanProposal): List<BusinesError>
}