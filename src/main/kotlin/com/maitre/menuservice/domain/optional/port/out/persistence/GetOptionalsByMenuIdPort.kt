package com.maitre.menuservice.domain.optional.port.out.persistence

import com.maitre.menuservice.domain.optional.entity.Optional
import reactor.core.publisher.Flux

interface GetOptionalsByMenuIdPort {
    fun getByMenuId(menuId: String) : Flux<Optional>
}