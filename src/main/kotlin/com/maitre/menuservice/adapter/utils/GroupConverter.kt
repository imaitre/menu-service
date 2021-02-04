package com.maitre.menuservice.adapter.utils

import com.maitre.menuservice.adapter.`in`.dto.GroupRequestDTO
import com.maitre.menuservice.adapter.`in`.dto.GroupResponseDTO
import com.maitre.menuservice.adapter.out.persistense.entity.GroupEntity
import com.maitre.menuservice.domain.entity.Group

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