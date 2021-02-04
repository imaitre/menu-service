package com.maitre.menuservice.adapter.out.persistense.entity

import com.maitre.menuservice.domain.entity.GroupType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("group")
data class GroupEntity(
    @Id
    val id: String,
    val menuId: String,
    val name: String,
    val description: String?,
    val type: GroupType,
    val available: Boolean
)