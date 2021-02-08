package com.maitre.menuservice.utils

import com.maitre.menuservice.adapter.group.`in`.dto.GroupRequestDTO
import com.maitre.menuservice.adapter.group.`in`.dto.GroupResponseDTO
import com.maitre.menuservice.adapter.group.out.persistence.entity.GroupEntity
import com.maitre.menuservice.domain.group.entity.Group

fun GroupRequestDTO.toDomain() =
    Group(
        name = name,
        menuId = menuId,
        description = description,
        type = type,
        available = available
    )

fun Group.toEntity() =
    GroupEntity(
        id = id,
        menuId = menuId,
        name = name,
        description = description,
        type = type,
        available = available
    )

fun GroupEntity.toDomain() =
    Group(
        id = id,
        name = name,
        menuId = menuId,
        description = description,
        type = type,
        available = available
    )

fun Group.toResponseDTO() =
    GroupResponseDTO(
        id = id,
        menuId = menuId,
        name = name,
        description = description,
        type = type, available = available
    )