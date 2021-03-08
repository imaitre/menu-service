package com.maitre.menuservice.adapter.optional.out.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("optional")
data class OptionalEntity(
  @Id
  val id: String,
  val menuId: String,
  val name: String,
  val description: String?,
  val available: Boolean,
  val minimum: Int,
  val maximum: Int,
  val repeat: Boolean,
  val addons: List<AddOns>) {

  data class AddOns(
    val id: String?,
    val name: String,
    val price: Double,
    val description: String?,
    val available: Boolean,
  )
}