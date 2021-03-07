package com.maitre.menuservice.domain.optional.port.out.persistence

import reactor.core.publisher.Mono

interface DeleteOptionalPort {
    fun delete(id: String): Mono<Void>
}