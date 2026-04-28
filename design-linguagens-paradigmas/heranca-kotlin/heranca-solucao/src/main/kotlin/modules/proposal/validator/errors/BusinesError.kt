package org.example.modules.proposal.validator.errors

// O erro de negocio e um valor simples.
// Isso facilita combinar resultados de validadores independentes
// sem manter estado compartilhado entre eles.
data class BusinesError (
    val code: String,
    val message: String
)
