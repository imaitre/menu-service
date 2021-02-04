package com.maitre.menuservice.adapter.group.`in`.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.maitre.menuservice.domain.group.entity.GroupType

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class GroupResponseDTO (
    val id: String,
    val menuId: String,
    val name: String,
    val description: String?,
    val type: GroupType,
    val available: Boolean
)
