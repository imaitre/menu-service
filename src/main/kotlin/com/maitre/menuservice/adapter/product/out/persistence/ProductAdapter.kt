package com.maitre.menuservice.adapter.product.out.persistence

import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.DeleteProductPort
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductByGroupPort
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductPort
import com.maitre.menuservice.domain.product.port.out.persistence.SaveProductPort
import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toEntity
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ProductAdapter(private val productRepository: ProductRepository) :
    SaveProductPort, GetProductPort, GetProductByGroupPort, DeleteProductPort {

    override fun save(product: Product): Mono<Product> {
        return productRepository.save(product.toEntity())
            .map { it.toDomain() }
    }

    override fun get(id: String): Mono<Product> {
        return productRepository.findById(id)
            .map { it.toDomain() }
    }

    override fun getByGroup(id: String): Flux<Product> {
        return productRepository.findByGroupId(id)
            .map { it.toDomain() }
    }

    override fun delete(id: String): Mono<Void> {
        return productRepository.deleteById(id)
    }

}