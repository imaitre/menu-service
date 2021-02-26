package com.maitre.menuservice.domain.group.usecases

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupByIdPort
import com.maitre.menuservice.exception.GroupNotFoundException
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetGroupUseCase(
    private val logger: Logger,
    private val getGroupByIdPort: GetGroupByIdPort
) {

    fun execute(id: String) : Mono<Group> {
        logger.info("Get group use case initiated. id=$id")
        return getGroupByIdPort.getById(id)
            .switchIfEmpty(Mono.error(GroupNotFoundException(id)))
            .doOnNext { logger.info("Get group use case done. id=${id}, menu=${it.toJson()}") }

    }

}
