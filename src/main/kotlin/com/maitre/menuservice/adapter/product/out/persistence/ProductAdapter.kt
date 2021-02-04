package com.maitre.menuservice.adapter.product.out.persistence

import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.SaveProductPort
import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toEntity
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ProductAdapter(private val productRepository: ProductRepository) : SaveProductPort {

    override fun save(product: Product): Mono<Product> {
        return productRepository.save(product.toEntity())
            .map { it.toDomain() }
    }

}