package com.maitre.menuservice.domain.group.usecases

import com.maitre.menuservice.domain.group.port.out.persistence.DeleteGroupPort
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupByIdPort
import com.maitre.menuservice.exception.GroupNotFoundException
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DeleteGroupUseCase(private val logger: Logger,
                         private val getGroupByIdPort: GetGroupByIdPort,
                         private val deleteGroupPort: DeleteGroupPort) {

    fun execute(id: String): Mono<Void>{
        logger.info("Delete group use case initiated. groupId=${id}")
        return getGroupByIdPort.getById(id)
                .switchIfEmpty(Mono.error(GroupNotFoundException(id)))
                .then(deleteGroupPort.delete(id))
                .doOnNext { logger.info("Delete group use case done.") }
    }
}