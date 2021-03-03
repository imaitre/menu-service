package com.maitre.menuservice.domain.optional.port.out.persistence

import com.maitre.menuservice.domain.optional.entity.Optional
import reactor.core.publisher.Mono

interface SaveOptionalPort {
    fun save(optional: Optional): Mono<Optional>
}