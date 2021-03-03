package com.maitre.menuservice.domain.optional.entity

import java.util.*


data class Optional(
  val id: String = "OPTI_" + UUID.randomUUID(),
  val menuId: String,
  val name: String,
  val description: String?,
  val available: Boolean,
  val minimum: Int,
  val maximum: Int,
  val repeat: Boolean,
  val addons: List<AddOns>)
  {
  data class AddOns(
    val id: String?,
    val name: String,
    val description: String?,
    val available: Boolean,
  )
}