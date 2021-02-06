package com.maitre.menuservice.domain.product.usecase

import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductByGroupPort
import com.maitre.menuservice.exception.ProductNotFoundException
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class GetProductsByGroupUseCase(
    private val logger: Logger,
    private val getProductByGroupPort: GetProductByGroupPort
) {

    fun execute(id: String): Flux<Product> {
        logger.info("Get products by group use case initiated. id=$id")
        return getProductByGroupPort.getByGroup(id)
            .switchIfEmpty(Mono.error(ProductNotFoundException(id)))
            .doOnNext { logger.info("Get product by group use case done. id=${id}, menu=${it}") }
    }

}