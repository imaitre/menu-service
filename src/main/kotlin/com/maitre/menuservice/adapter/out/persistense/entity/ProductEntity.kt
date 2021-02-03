package com.maitre.menuservice.adapter.out.persistense.entity

import java.math.BigDecimal

data class ProductEntity(
    val id: String,
    val name: String,
    val description: String?,
    val amount: BigDecimal,
    val adultsOnly: Boolean,
    val available: Boolean,
    val addons: Map<String, BigDecimal>?,
    val imageUrls: List<String>
)