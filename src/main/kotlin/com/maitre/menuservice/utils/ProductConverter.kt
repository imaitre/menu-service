package com.maitre.menuservice.utils

import com.maitre.menuservice.adapter.product.`in`.dto.ProductRequestDTO
import com.maitre.menuservice.adapter.product.`in`.dto.ProductResponseDTO
import com.maitre.menuservice.adapter.product.out.persistence.entity.ProductEntity
import com.maitre.menuservice.domain.product.entity.Product

fun ProductRequestDTO.toDomain() =
    Product(
        name = name,
        groupId = groupId,
        description = description,
        amount = amount,
        adultsOnly = adultsOnly,
        available = available,
        addons = addons,
        imageUrls = imageUrls
    )

fun Product.toEntity() =
    ProductEntity(
        id = id,
        groupId = groupId,
        name = name,
        description = description,
        amount = amount,
        adultsOnly = adultsOnly,
        available = available,
        addons = addons,
        imageUrls = imageUrls
    )

fun ProductEntity.toDomain() =
    Product(
        id = id,
        groupId = groupId,
        name = name,
        description = description,
        amount = amount,
        adultsOnly = adultsOnly,
        available = available,
        addons = addons,
        imageUrls = imageUrls
    )

fun Product.toResponseDTO() =
    ProductResponseDTO(
        id = id,
        groupId = groupId,
        name = name,
        description = description,
        amount = amount,
        adultsOnly = adultsOnly,
        available = available,
        addons = addons,
        imageUrls = imageUrls
    )