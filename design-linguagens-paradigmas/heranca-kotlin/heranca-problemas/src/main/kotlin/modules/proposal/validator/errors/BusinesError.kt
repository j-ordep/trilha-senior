package org.example.modules.proposal.validator.errors

// Value object simples para representar uma violacao encontrada.
// O nome da classe e o payload foram preservados porque o foco deste worker e documentar
// o aprendizado do modulo, nao corrigir inconsistencias de modelagem ou nomenclatura.
data class BusinesError (
    val code: String,
    val message: String
)
