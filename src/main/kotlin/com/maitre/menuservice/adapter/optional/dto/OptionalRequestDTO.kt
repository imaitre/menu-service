package com.maitre.menuservice.adapter.optional.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class OptionalRequestDTO(
        val menuId: String,
        val name: String,
        val description: String?,
        val available: Boolean,
        val minimum: Int,
        val maximum: Int,
        val repeat: Boolean,
        val addons: List<AddOnsDTO>
) {
        data class AddOnsDTO(
                var id: String?,
                val name: String,
                val description: String?,
                val available: Boolean,
        )
}