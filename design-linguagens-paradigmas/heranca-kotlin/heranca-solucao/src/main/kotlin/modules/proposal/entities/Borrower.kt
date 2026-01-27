package org.example.modules.proposal.entities

import java.time.LocalDate

data class Borrower(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val cpf: String,
    val birthDate: LocalDate,
    val address: Address,
    val isActive: Boolean = true
)

data class Address(
    val street: String,
    val number: String,
    val complement: String? = null,
    val neighborhood: String,
    val city: String,
    val state: String,
    val zipCode: String
)