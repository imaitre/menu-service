package com.maitre.menuservice.domain.menu.port.out.persistence

import reactor.core.publisher.Mono

interface DeleteMenuPort {
    fun delete(id: String): Mono<Void>
}