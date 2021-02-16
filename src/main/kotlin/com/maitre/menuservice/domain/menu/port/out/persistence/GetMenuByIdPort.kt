package com.maitre.menuservice.domain.menu.port.out.persistence

import com.maitre.menuservice.domain.menu.entity.Menu
import reactor.core.publisher.Mono

interface GetMenuByIdPort {
    fun getById(id: String) : Mono<Menu>
}