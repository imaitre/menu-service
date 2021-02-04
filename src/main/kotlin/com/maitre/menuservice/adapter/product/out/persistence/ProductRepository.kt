package com.maitre.menuservice.adapter.product.out.persistence

import com.maitre.menuservice.adapter.product.out.persistence.entity.ProductEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : ReactiveMongoRepository<ProductEntity, String>