package com.maitre.menuservice.domain.optional.usecases


import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenuByIdPort
import com.maitre.menuservice.domain.optional.entity.Optional
import com.maitre.menuservice.domain.optional.port.out.persistence.SaveOptionalPort
import com.maitre.menuservice.exception.MenuNotFoundException
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CreateOptionalUseCase(private val logger: Logger,
                            private val saveOptionalPort: SaveOptionalPort,
                            private val getMenuByIdPort: GetMenuByIdPort) {

    fun execute(optional: Optional): Mono<Optional> {
        logger.info("Create optional use case initiated. group=${optional.toJson()}")
        return getMenuByIdPort.getById(optional.menuId)
                .switchIfEmpty(Mono.error(MenuNotFoundException(optional.menuId)))
                .then(saveOptionalPort.save(optional))
                .doOnNext { logger.info("Create optional use case done.") }
    }
}