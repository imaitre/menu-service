package com.maitre.menuservice.adapter.product.out.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

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
    val addons: MutableList<String>?,
    val imageUrls: List<String>?
)