package com.maitre.menuservice.domain.product.entity

import java.math.BigDecimal
import java.util.UUID

data class Product(
    val id: String = "PROD_" + UUID.randomUUID(),
    val groupId: String,
    val name: String,
    val description: String?,
    val amount: BigDecimal,
    val adultsOnly: Boolean,
    val available: Boolean,
    val addons: Map<String, BigDecimal>?,
    val imageUrls: List<String>
)