package com.maitre.menuservice.domain.group.usecases

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.port.out.persistence.SaveGroupPort
import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenuByIdPort
import com.maitre.menuservice.exception.MenuNotFoundException
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CreateGroupUseCase(private val logger: Logger,
                         private val saveGroupPort: SaveGroupPort,
                         private val getMenuByIdPort: GetMenuByIdPort) {

    fun execute(group: Group): Mono<Group> {
        logger.info("Create group use case initiated. group=${group.toJson()}")
        return getMenuByIdPort.getById(group.menuId)
                .switchIfEmpty(Mono.error(MenuNotFoundException(group.menuId)))
                .then(saveGroupPort.save(group))
                .doOnNext { logger.info("Create group use case done.") }
    }
}