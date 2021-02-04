package com.maitre.menuservice.adapter.`in`.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.maitre.menuservice.domain.entity.GroupType

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class GroupRequestDTO(
    val menuId: String,
    val name: String,
    val description: String?,
    val type: GroupType,
    val available: Boolean
    )