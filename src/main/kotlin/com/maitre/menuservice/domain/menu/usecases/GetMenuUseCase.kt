package com.maitre.menuservice.domain.menu.usecases

import com.maitre.menuservice.utils.toJson
import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenuPort
import com.maitre.menuservice.exception.MenuNotFoundException
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetMenuUseCase(
    private val logger: Logger,
    private val getMenuPort: GetMenuPort
) {

    fun execute(id: String) : Mono<Menu> {
        logger.info("Get menu use case initiated. id=$id")
        return getMenuPort.get(id)
            .switchIfEmpty(Mono.error(MenuNotFoundException(id)))
            .doOnNext { logger.info("Get menu use case done. id=${id}, menu=${it.toJson()}") }

    }

}