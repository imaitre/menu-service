package com.maitre.menuservice.domain.group.port.out.persistence

import com.maitre.menuservice.domain.group.entity.Group
import reactor.core.publisher.Mono

interface GetGroupPort {
    fun get(id: String) : Mono<Group>
}