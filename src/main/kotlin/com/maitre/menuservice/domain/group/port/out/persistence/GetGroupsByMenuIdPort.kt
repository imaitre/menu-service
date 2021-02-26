package com.maitre.menuservice.domain.group.port.out.persistence

import com.maitre.menuservice.domain.group.entity.Group
import reactor.core.publisher.Flux

interface GetGroupsByMenuIdPort {
    fun getByMenuId(menuId: String) : Flux<Group>
}