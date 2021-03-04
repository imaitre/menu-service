package com.maitre.menuservice.utils

import com.maitre.menuservice.adapter.optional.dto.OptionalRequestDTO
import com.maitre.menuservice.adapter.optional.dto.OptionalResponseDTO
import com.maitre.menuservice.adapter.optional.out.persistence.entity.OptionalEntity
import com.maitre.menuservice.domain.optional.entity.Optional

fun OptionalRequestDTO.toDomain() =
    Optional(
        menuId=menuId,
        name= name,
        description = description,
        available= available,
        minimum = minimum,
        maximum = maximum,
        repeat= repeat,
        addons = addOnsDTOtoDomain(addons)
    )

fun Optional.toResponseDTO() =
    OptionalResponseDTO(
        id= id,
        menuId=menuId,
        name= name,
        description = description,
        available= available,
        minimum = minimum,
        maximum = maximum,
        repeat= repeat,
        addons = addOnToDTO(addons)
    )
fun Optional.toEntity() =
    OptionalEntity(
        id= id,
        menuId=menuId,
        name= name,
        description = description,
        available= available,
        minimum = minimum,
        maximum = maximum,
        repeat= repeat,
        addons = addOnToEntity(addons)
    )

fun OptionalEntity.toDomain() =
    Optional(
        id= id,
        menuId=menuId,
        name= name,
        description = description,
        available= available,
        minimum = minimum,
        maximum = maximum,
        repeat= repeat,
        addons = addOnEntityToDomain(addons)
    )


fun addOnsDTOtoDomain(addons: List<OptionalRequestDTO.AddOnsDTO>): List<Optional.AddOns>{
    return addons.map {
        Optional.AddOns(
            id = it.id,
            name= it.name,
            description= it.description,
            price = it.price,
            available= it.available
        )
    }.toMutableList()
}


fun addOnToDTO(addons: List<Optional.AddOns>): List<OptionalResponseDTO.AddOnsDTO>{
    return addons.map {
        OptionalResponseDTO.AddOnsDTO(
            id = it.id,
            name= it.name,
            description= it.description,
            price = it.price,
            available= it.available
        )
    }.toMutableList()
}

fun addOnToEntity(addons: List<Optional.AddOns>): List<OptionalEntity.AddOns>{
    return addons.map {
        OptionalEntity.AddOns(
            id = it.id,
            name= it.name,
            description= it.description,
            price = it.price,
            available= it.available
        )
    }.toMutableList()
}

fun addOnEntityToDomain(addons: List<OptionalEntity.AddOns>): List<Optional.AddOns>{
    return addons.map {
        Optional.AddOns(
            id = it.id,
            name= it.name,
            description= it.description,
            price = it.price,
            available= it.available
        )
    }.toMutableList()
}