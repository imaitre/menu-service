package com.maitre.menuservice.domain.usecases

import com.maitre.menuservice.adapter.utils.toJson
import com.maitre.menuservice.domain.entity.Menu
import com.maitre.menuservice.domain.port.out.persistence.GetMenuPort
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