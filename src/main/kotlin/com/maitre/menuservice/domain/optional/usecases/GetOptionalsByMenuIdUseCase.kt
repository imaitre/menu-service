package com.maitre.menuservice.domain.optional.usecases

import com.maitre.menuservice.domain.optional.entity.Optional
import com.maitre.menuservice.domain.optional.port.out.persistence.GetOptionalsByMenuIdPort
import com.maitre.menuservice.exception.OptionalNotFoundException
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class GetOptionalsByMenuIdUseCase(private val logger: Logger,
                                  private val getOptionalsByMenuIdPort: GetOptionalsByMenuIdPort
) {

    fun execute(menuId: String): Flux<Optional> {
        logger.info("Get optionals by menu_id use case initiated. id=$menuId")
        return getOptionalsByMenuIdPort.getByMenuId(menuId)
                .switchIfEmpty(Mono.error(OptionalNotFoundException(menuId)))
                .doOnNext { logger.info("id=${menuId}, group=${it.toJson()}") }
    }
}