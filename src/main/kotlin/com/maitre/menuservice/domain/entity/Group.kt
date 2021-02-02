package com.maitre.menuservice.domain.entity

import java.util.UUID

data class Group(
    val id: String = "GROU_" + UUID.randomUUID(),
    val name: String,
    val description: String?,
    val type: GroupType,
    val available: Boolean,
    val products: List<Product>?
)

enum class GroupType {
    ORDINARY_FOOD, DESSERT, CANDY, FRUIT, PIZZA, HAMBURGER, VEGETARIAN, VEGAN, ICE_CREAM, PASTA,
    ORDINARY_DRINK, SOFT_DRINK, ALCOHOLIC_DRINK, JUICE, MILKSHAKE,
    OTHER
}
