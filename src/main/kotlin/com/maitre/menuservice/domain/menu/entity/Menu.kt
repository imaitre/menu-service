package com.maitre.menuservice.domain.menu.entity

import java.util.UUID

data class Menu(
    var id: String = "MENU_" + UUID.randomUUID(),
    val customerId: String,
    val name: String,
    val description: String?,
    val available: Boolean
)