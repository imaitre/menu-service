package com.maitre.menuservice.domain.group.port.out.persistence

import com.maitre.menuservice.domain.group.entity.Group
import reactor.core.publisher.Mono

interface SaveGroupPort {

    fun save(group: Group): Mono<Group>
}