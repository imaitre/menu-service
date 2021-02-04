package com.maitre.menuservice.domain.menu.usecases

import com.maitre.menuservice.utils.toJson
import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.port.out.persistence.SaveMenuPort
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CreateMenuUseCase(private val logger: Logger,
                        private val saveMenuPort: SaveMenuPort) {

    fun execute(menu: Menu) : Mono<Menu> {
        logger.info("Create menu use case initiated. menu=${menu.toJson()}")
        return saveMenuPort.save(menu)
            .doOnNext { logger.info("Create menu use case done.") }
    }
}