package com.maitre.menuservice.adapter.product.out.persistence

import com.maitre.menuservice.adapter.product.out.persistence.entity.ProductEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ProductRepository : ReactiveMongoRepository<ProductEntity, String>{
    fun findByGroupId(groupId: String) : Flux<ProductEntity>
}