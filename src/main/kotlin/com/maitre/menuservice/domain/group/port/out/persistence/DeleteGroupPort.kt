package com.maitre.menuservice.domain.group.port.out.persistence

import reactor.core.publisher.Mono

interface DeleteGroupPort {
    fun delete(id: String): Mono<Void>
}