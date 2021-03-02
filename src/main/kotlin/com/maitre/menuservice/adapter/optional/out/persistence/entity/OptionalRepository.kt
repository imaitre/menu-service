package com.maitre.menuservice.adapter.optional.out.persistence.entity

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OptionalRepository: ReactiveMongoRepository<OptionalEntity, String> {
}