package com.maitre.menuservice.domain.optional.usecases

import com.maitre.menuservice.domain.optional.port.out.persistence.DeleteOptionalPort
import com.maitre.menuservice.domain.optional.port.out.persistence.GetOptionalByIdPort
import com.maitre.menuservice.exception.OptionalNotFoundException
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DeleteUpdateUseCase(private val logger: Logger,
                          private val getOptionalByIdPort: GetOptionalByIdPort,
                          private val deleteOptionalPort: DeleteOptionalPort
) {

    fun execute(id: String): Mono<Void>{
        logger.info("Delete optional use case initiated. groupId=${id}")
        return getOptionalByIdPort.getById(id)
                .switchIfEmpty(Mono.error(OptionalNotFoundException(id)))
                .then(deleteOptionalPort.delete(id))
                .doOnSuccess { logger.info("Delete optional use case done. id=${id}}") }
    }
}