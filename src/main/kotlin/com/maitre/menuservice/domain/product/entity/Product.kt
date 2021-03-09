package com.maitre.menuservice.domain.product.entity

import java.math.BigDecimal
import java.util.*

data class Product(
  var id: String = "PROD_" + UUID.randomUUID(),
  val groupId: String,
  val name: String,
  val description: String?,
  val amount: BigDecimal,
  val adultsOnly: Boolean,
  val available: Boolean,
  val addons: List<String>?,
  val imageUrls: List<String>?
)