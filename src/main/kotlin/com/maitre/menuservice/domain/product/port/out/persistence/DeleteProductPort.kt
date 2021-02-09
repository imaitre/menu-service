package com.maitre.menuservice.domain.product.port.out.persistence

import com.maitre.menuservice.domain.product.entity.Product
import reactor.core.publisher.Mono

interface DeleteProductPort {
    fun delete(id: String) : Mono<Void>
}
