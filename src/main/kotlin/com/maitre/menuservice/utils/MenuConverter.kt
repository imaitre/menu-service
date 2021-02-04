package com.maitre.menuservice.utils

import com.maitre.menuservice.adapter.menu.`in`.dto.MenuRequestDTO
import com.maitre.menuservice.adapter.menu.`in`.dto.MenuResponseDTO
import com.maitre.menuservice.adapter.menu.out.persistence.entity.MenuEntity
import com.maitre.menuservice.domain.menu.entity.Menu

fun MenuRequestDTO.toDomain() =
    Menu(
        customerId = customerId,
        name = name,
        description = description,
        available = available
    )

fun Menu.toResponseDTO() =
    MenuResponseDTO(
        id = id,
        customerId = customerId,
        name = name,
        description = description,
        available = available
    )

fun Menu.toEntity() =
    MenuEntity(
        id = id,
        customerId = customerId,
        name = name,
        description = description,
        available = available
    )

fun MenuEntity.toDomain() =
    Menu(
        id = id,
        customerId = customerId,
        name = name,
        description = description,
        available = available
    )