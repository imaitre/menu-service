package com.maitre.menuservice.adapter.product.out.persistence.entity

import java.math.BigDecimal
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("product")
data class ProductEntity(
    @Id
    val id: String,
    val groupId: String,
    val name: String,
    val description: String?,
    val amount: BigDecimal,
    val adultsOnly: Boolean,
    val available: Boolean,
    val addons: Map<String, BigDecimal>?,
    val imageUrls: List<String>?
)