package com.maitre.menuservice.adapter.out.persistense.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("menu")
data class MenuEntity(
    @Id
    val id: String,
    val customerId: String,
    val name: String,
    val description: String?,
    val available: Boolean,
    val groups: List<GroupEntity>?
)