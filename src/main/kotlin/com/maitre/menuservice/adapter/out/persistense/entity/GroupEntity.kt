package com.maitre.menuservice.adapter.out.persistense.entity

import com.maitre.menuservice.domain.entity.GroupType

data class GroupEntity(
    val id: String,
    val name: String,
    val description: String?,
    val type: GroupType,
    val available: Boolean,
    val products: List<ProductEntity>?
)