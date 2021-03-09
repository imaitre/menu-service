package com.maitre.menuservice.adapter.product.`in`.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class ProductRequestDTO(
    val groupId: String,
    val name: String,
    val description: String?,
    val amount: BigDecimal,
    val adultsOnly: Boolean,
    val available: Boolean,
    val addons: List<String>?,
    val imageUrls: List<String>?
    )
