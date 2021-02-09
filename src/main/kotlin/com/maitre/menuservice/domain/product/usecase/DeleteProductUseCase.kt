package com.maitre.menuservice.domain.product.usecase

import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.DeleteProductPort
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductPort
import com.maitre.menuservice.exception.ProductNotFoundException
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DeleteProductUseCase(private val logger: Logger,
                           private val getProductPort: GetProductPort,
                           private val deleteProductPort: DeleteProductPort
) {

    fun execute(id: String): Mono<Void> {

        return getProductPort.get(id)
            .switchIfEmpty(Mono.error(ProductNotFoundException(id)))
            .then(deleteProductPort.delete(id))
            .doOnNext { logger.info("Delete product use case done. id=${id}}") }

    }

}
