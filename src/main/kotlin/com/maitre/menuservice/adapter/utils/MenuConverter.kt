package com.maitre.menuservice.adapter.utils

import com.maitre.menuservice.adapter.`in`.dto.MenuRequestDTO
import com.maitre.menuservice.adapter.`in`.dto.MenuResponseDTO
import com.maitre.menuservice.adapter.out.persistense.entity.MenuEntity
import com.maitre.menuservice.domain.entity.Menu

fun MenuRequestDTO.toDomain() =
    Menu(
        customerId = customerId,
        name = name,
        description = description,
        available = available,
        groups = groups?.map { it.toDomain() }?.toList()
    )

fun Menu.toResponseDTO() =
    MenuResponseDTO(
        id = id,
        customerId = customerId,
        name = name,
        description = description,
        available = available,
        groups = groups?.map { it.toResponseDTO() }?.toList()
    )

fun Menu.toEntity() =
    MenuEntity(
        id = id,
        customerId = customerId,
        name = name,
        description = description,
        available = available,
        groups = groups?.map { it.toEntity() }?.toList()
    )

fun MenuEntity.toDomain() =
    Menu(
        id = id,
        customerId = customerId,
        name = name,
        description = description,
        available = available,
        groups = groups?.map { it.toDomain() }?.toList()
    )