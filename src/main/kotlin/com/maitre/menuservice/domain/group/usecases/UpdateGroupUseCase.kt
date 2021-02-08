package com.maitre.menuservice.domain.group.usecases

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupByIdPort
import com.maitre.menuservice.domain.group.port.out.persistence.SaveGroupPort
import com.maitre.menuservice.exception.GroupNotFoundException
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UpdateGroupUseCase(private val logger: Logger,
                         private val saveGroupPort: SaveGroupPort, private val getGroupByIdPort: GetGroupByIdPort) {

    fun execute(group: Group, id: String): Mono<Group> {
        logger.info("Update group use case initiated. group=${group.toJson()}")
        group.id= id
        return getGroupByIdPort.getById(id)
                .switchIfEmpty(Mono.error(GroupNotFoundException(id)))
                .then(saveGroupPort.save(group))
                .doOnNext { logger.info("Create group use case done.") }
    }
}