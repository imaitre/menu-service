package com.maitre.menuservice.adapter.`in`.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class MenuRequestDTO(
    val customerId: String,
    val name: String,
    val description: String?,
    val available: Boolean
)