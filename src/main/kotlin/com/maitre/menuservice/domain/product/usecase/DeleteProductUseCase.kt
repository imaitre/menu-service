package com.maitre.menuservice.domain.product.usecase

import com.maitre.menuservice.domain.product.port.out.persistence.DeleteProductPort
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductByIdPort
import com.maitre.menuservice.exception.ProductNotFoundException
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DeleteProductUseCase(private val logger: Logger,
                           private val getProductByIdPort: GetProductByIdPort,
                           private val deleteProductPort: DeleteProductPort
) {

    fun execute(id: String): Mono<Void> {

        return getProductByIdPort.getById(id)
            .switchIfEmpty(Mono.error(ProductNotFoundException(id)))
            .then(deleteProductPort.delete(id))
            .doOnSuccess { logger.info("Delete product use case done. id=${id}}") }
    }
}
