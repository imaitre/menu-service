package com.maitre.menuservice.adapter.utils

import com.maitre.menuservice.adapter.`in`.dto.ProductRequestDTO
import com.maitre.menuservice.adapter.`in`.dto.ProductResponseDTO
import com.maitre.menuservice.adapter.out.persistense.entity.ProductEntity
import com.maitre.menuservice.domain.entity.Product

fun ProductRequestDTO.toDomain() =
    Product(
        name = name,
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
        name = name,
        description = description,
        amount = amount,
        adultsOnly = adultsOnly,
        available = available,
        addons = addons,
        imageUrls = imageUrls
    )