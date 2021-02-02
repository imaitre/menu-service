package com.maitre.menuservice.adapter.utils

import com.maitre.menuservice.adapter.`in`.dto.GroupRequestDTO
import com.maitre.menuservice.adapter.`in`.dto.GroupResponseDTO
import com.maitre.menuservice.adapter.out.persistense.entity.GroupEntity
import com.maitre.menuservice.domain.entity.Group

fun GroupRequestDTO.toDomain() =
    Group(
        name = name,
        description = description,
        type = type,
        available = available,
        products = null
    )

fun Group.toEntity() =
    GroupEntity(
        id = id,
        name = name,
        description = description,
        type = type,
        available = available
    )

fun GroupEntity.toDomain() =
    Group(
        name = name,
        description = description,
        type = type,
        available = available,
        products = null
    )

fun Group.toResponseDTO() =
    GroupResponseDTO(
        id = id,
        name = name,
        description = description,
        type = type, available = available,
        null
    )