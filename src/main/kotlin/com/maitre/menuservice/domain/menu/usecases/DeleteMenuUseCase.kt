package com.maitre.menuservice.domain.menu.usecases

import com.maitre.menuservice.domain.group.port.out.persistence.DeleteGroupByMenuIdPort
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupsByMenuIdPort
import com.maitre.menuservice.domain.menu.port.out.persistence.DeleteMenuPort
import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenuByIdPort
import com.maitre.menuservice.domain.product.port.out.persistence.DeleteProductByGroupIdPort
import com.maitre.menuservice.exception.MenuNotFoundException
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import reactor.kotlin.core.publisher.zip

@Service
class DeleteMenuUseCase(
    private val logger: Logger,
    private val getMenuByIdPort: GetMenuByIdPort,
    private val deleteMenuPort: DeleteMenuPort,
    private val deleteGroupByMenuIdPort: DeleteGroupByMenuIdPort,
    private val getGroupsByMenuIdPort: GetGroupsByMenuIdPort,
    private val deleteProductByGroupIdPort: DeleteProductByGroupIdPort
) {

    fun execute(id: String): Mono<Void> {
        logger.info("Delete menu use case initiated. groupId=${id}")

        return getMenuByIdPort.getById(id)
            .switchIfEmpty(Mono.error(MenuNotFoundException(id)))
            .toFlux()
            .flatMap { getGroupsByMenuIdPort.getByMenuId(id) }
            .flatMap { deleteProductByGroupIdPort.deleteByGroupId(it.id) }
            .then(deleteGroupByMenuIdPort.deleteByMenuId(id))
            .then(deleteMenuPort.delete(id))
            .doOnSuccess { logger.info("Delete menu use case done. id=${id}}") }

    }
}