package com.maitre.menuservice.domain.product.usecase

import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductByIdPort
import com.maitre.menuservice.domain.product.port.out.persistence.SaveProductPort
import com.maitre.menuservice.exception.ProductNotFoundException
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UpdateProductUseCase(private val logger: Logger,
                           private val saveProductPort: SaveProductPort,
                           private val getProductByIdPort: GetProductByIdPort
) {

    fun execute(product: Product, id: String): Mono<Product> {
        logger.info("Update product use case initiated. menu=${product.toJson()}")
        product.id = id
        return getProductByIdPort.getById(id)
            .switchIfEmpty(Mono.error(ProductNotFoundException(id)))
            .then(saveProductPort.save(product))
            .doOnNext { logger.info("Update product use case done.") }
    }

}
