package com.maitre.menuservice.domain.group.port.out.persistence

import reactor.core.publisher.Mono

interface DeleteGroupsByMenuIdPort {
    fun deleteByMenuId(id: String): Mono<Void>
}