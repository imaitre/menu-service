package com.maitre.menuservice.domain.product.port.out.persistence

import reactor.core.publisher.Mono

interface DeleteProductsByGroupIdPort {
    fun deleteByGroupId(id: String) : Mono<Void>
}
