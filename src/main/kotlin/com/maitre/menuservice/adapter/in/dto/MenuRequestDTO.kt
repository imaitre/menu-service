package com.maitre.menuservice.adapter.`in`.dto

data class MenuRequestDTO(
    val customer_id: String,
    val name: String,
    val description: String,
    val available: Boolean
)