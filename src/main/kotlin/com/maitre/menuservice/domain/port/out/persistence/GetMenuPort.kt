package com.maitre.menuservice.domain.port.out.persistence

import com.maitre.menuservice.domain.entity.Menu
import reactor.core.publisher.Mono

interface GetMenuPort {
    fun get(id: String) : Mono<Menu>
}