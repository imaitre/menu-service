package com.maitre.menuservice.domain.product.port.out.persistence

import com.maitre.menuservice.domain.product.entity.Product
import reactor.core.publisher.Flux

interface GetProductByGroupPort {
    fun getByGroup(id: String) : Flux<Product>
}