package com.maitre.menuservice.adapter.group.out.persistence.entity

import com.maitre.menuservice.domain.group.entity.GroupType
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