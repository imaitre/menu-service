package com.maitre.menuservice.domain.group.port.out.persistence

import reactor.core.publisher.Mono

interface DeleteGroupByMenuIdPort {
    fun deleteByMenuId(id: String): Mono<Void>
}