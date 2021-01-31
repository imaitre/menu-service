package com.maitre.menuservice.domain.port.out.persistence

import com.maitre.menuservice.domain.entity.Menu
import reactor.core.publisher.Mono

interface SaveMenuPort {
    fun save(menu: Menu) : Mono<Menu>
}