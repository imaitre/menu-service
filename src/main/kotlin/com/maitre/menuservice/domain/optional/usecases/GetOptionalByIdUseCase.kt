package com.maitre.menuservice.domain.optional.usecases

import com.maitre.menuservice.domain.optional.entity.Optional
import com.maitre.menuservice.domain.optional.port.out.persistence.GetOptionalByIdPort
import com.maitre.menuservice.exception.OptionalNotFoundException
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetOptionalByIdUseCase(private val logger: Logger,
                             private val getOptionalsByIdPort: GetOptionalByIdPort
) {

    fun execute(id: String): Mono<Optional> {
        logger.info("Get optional by id use case initiated. id=$id")
        return getOptionalsByIdPort.getById(id)
          .switchIfEmpty(Mono.error(OptionalNotFoundException(id)))
          .doOnNext { logger.info("id=${id}, group=${it.toJson()}") }
    }
}