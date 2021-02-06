package com.maitre.menuservice.domain.product.port.out.persistence

import com.maitre.menuservice.domain.product.entity.Product
import reactor.core.publisher.Mono

interface GetProductPort {
    fun get(id: String) : Mono<Product>
}