package com.maitre.menuservice.domain.product.port.out.persistence

import reactor.core.publisher.Mono

interface DeleteProductPort {
    fun delete(id: String) : Mono<Void>
}
