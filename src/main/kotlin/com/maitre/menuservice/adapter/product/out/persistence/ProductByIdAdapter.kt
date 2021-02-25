package com.maitre.menuservice.adapter.product.out.persistence

import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.DeleteProductPort
import com.maitre.menuservice.domain.product.port.out.persistence.DeleteProductsByGroupIdPort
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductByIdPort
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductsByGroupPort
import com.maitre.menuservice.domain.product.port.out.persistence.SaveProductPort
import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toEntity
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ProductByIdAdapter(private val productRepository: ProductRepository) :
    SaveProductPort, GetProductByIdPort, GetProductsByGroupPort, DeleteProductPort, DeleteProductsByGroupIdPort {

    override fun save(product: Product): Mono<Product> {
        return productRepository.save(product.toEntity())
            .map { it.toDomain() }
    }

    override fun getById(id: String): Mono<Product> {
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

    override fun deleteByGroupId(id: String): Mono<Void> {
        return productRepository.deleteByGroupId(id)
    }

}