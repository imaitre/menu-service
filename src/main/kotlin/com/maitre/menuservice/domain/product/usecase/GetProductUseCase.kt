package com.maitre.menuservice.domain.product.usecase

import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductPort
import com.maitre.menuservice.exception.ProductNotFoundException
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetProductUseCase(
    private val logger: Logger,
    private val getProductPort: GetProductPort
) {

    fun execute(id: String): Mono<Product> {
        logger.info("Get product use case initiated. id=$id")
        return getProductPort.get(id)
            .switchIfEmpty(Mono.error(ProductNotFoundException(id)))
            .doOnNext { logger.info("Get product use case done. id=${id}, menu=${it.toJson()}") }
    }

}