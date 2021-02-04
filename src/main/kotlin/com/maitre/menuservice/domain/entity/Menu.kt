package com.maitre.menuservice.domain.entity

import java.util.UUID

data class Menu(
    val id: String = "MENU_" + UUID.randomUUID(),
    val customerId: String,
    val name: String,
    val description: String?,
    val available: Boolean
)