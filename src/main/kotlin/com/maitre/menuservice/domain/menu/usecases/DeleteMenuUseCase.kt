package com.maitre.menuservice.domain.menu.usecases

import com.maitre.menuservice.domain.group.port.out.persistence.DeleteGroupsByMenuIdPort
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupsByMenuIdPort
import com.maitre.menuservice.domain.menu.port.out.persistence.DeleteMenuPort
import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenuByIdPort
import com.maitre.menuservice.domain.product.port.out.persistence.DeleteProductsByGroupIdPort
import com.maitre.menuservice.exception.MenuNotFoundException
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class DeleteMenuUseCase(
  private val logger: Logger,
  private val getMenuByIdPort: GetMenuByIdPort,
  private val deleteMenuPort: DeleteMenuPort,
  private val deleteGroupsByMenuIdPort: DeleteGroupsByMenuIdPort,
  private val getGroupsByMenuIdPort: GetGroupsByMenuIdPort,
  private val deleteProductsByGroupIdPort: DeleteProductsByGroupIdPort
) {

    fun execute(id: String): Mono<Void> {
        logger.info("Delete menu use case initiated. groupId=${id}")

        return getMenuByIdPort.getById(id)
            .switchIfEmpty(Mono.error(MenuNotFoundException(id)))
            .toFlux()
            .flatMap { getGroupsByMenuIdPort.getByMenuId(id) }
            .flatMap { deleteProductsByGroupIdPort.deleteByGroupId(it.id) }
            .then(deleteGroupsByMenuIdPort.deleteByMenuId(id))
            .then(deleteMenuPort.delete(id))
            .doOnSuccess { logger.info("Delete menu use case done. id=${id}}") }

    }
}