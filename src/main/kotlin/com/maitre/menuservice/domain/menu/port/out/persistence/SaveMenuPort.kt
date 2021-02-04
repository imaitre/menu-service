package com.maitre.menuservice.domain.menu.port.out.persistence

import com.maitre.menuservice.domain.menu.entity.Menu
import reactor.core.publisher.Mono

interface SaveMenuPort {
    fun save(menu: Menu) : Mono<Menu>
}