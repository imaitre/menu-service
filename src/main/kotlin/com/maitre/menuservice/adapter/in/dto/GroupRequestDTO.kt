package com.maitre.menuservice.adapter.`in`.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class GroupRequestDTO(
    val name: String,
    val description: String?,
    val type: GroupType,
    val available: Boolean,
    val products: List<ProductRequestDTO>?
    )

enum class GroupType {
    ORDINARY_FOOD, DESSERT, CANDY, FRUIT, PIZZA, HAMBURGER, VEGETARIAN, VEGAN, ICE_CREAM, PASTA,
    ORDINARY_DRINK, SOFT_DRINK, ALCOHOLIC_DRINK, JUICE, MILKSHAKE,
    OTHER
}
