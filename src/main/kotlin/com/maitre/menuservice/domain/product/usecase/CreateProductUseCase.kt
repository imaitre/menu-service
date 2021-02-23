package com.maitre.menuservice.domain.product.usecase

import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupByIdPort
import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.SaveProductPort
import com.maitre.menuservice.exception.GroupNotFoundException
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CreateProductUseCase(
    private val logger: Logger,
    private val saveProductPort: SaveProductPort,
    private val getGroupByIdPort: GetGroupByIdPort
) {

    fun execute(product: Product): Mono<Product> {
        logger.info("Create product use case initiated. menu=${product.toJson()}")
        return getGroupByIdPort.getById(product.groupId)
            .switchIfEmpty(Mono.error(GroupNotFoundException(product.groupId)))
            .then(saveProductPort.save(product))
            .doOnNext { logger.info("Create product use case done.") }
    }
}