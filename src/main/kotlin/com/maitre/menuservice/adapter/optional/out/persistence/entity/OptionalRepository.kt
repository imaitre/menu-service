package com.maitre.menuservice.adapter.optional.out.persistence.entity

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface OptionalRepository: ReactiveMongoRepository<OptionalEntity, String> {

  fun findAllByMenuId(id: String): Flux<OptionalEntity>

}