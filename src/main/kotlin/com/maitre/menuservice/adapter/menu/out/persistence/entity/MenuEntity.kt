package com.maitre.menuservice.adapter.menu.out.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("menu")
data class MenuEntity(
    @Id
    val id: String,
    val customerId: String,
    val name: String,
    val description: String?,
    val available: Boolean
)