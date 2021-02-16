package com.maitre.menuservice.domain.group.usecases

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupsByMenuIdPort
import com.maitre.menuservice.exception.GroupNotFoundException
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class GetGroupsByMenuIdUseCase(private val logger: Logger,
                               private val getGroupsByMenuIdPort: GetGroupsByMenuIdPort) {

    fun execute(menuId: String): Flux<Group> {
        logger.info("Get groups by menu_id use case initiated. id=$menuId")
        return getGroupsByMenuIdPort.getByMenuId(menuId)
                .switchIfEmpty(Mono.error(GroupNotFoundException(menuId)))
                .doOnNext { logger.info("id=${menuId}, group=${it.toJson()}") }
    }
}