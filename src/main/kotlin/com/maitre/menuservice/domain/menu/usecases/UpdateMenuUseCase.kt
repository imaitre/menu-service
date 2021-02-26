package com.maitre.menuservice.domain.menu.usecases

import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenuByIdPort
import com.maitre.menuservice.domain.menu.port.out.persistence.SaveMenuPort
import com.maitre.menuservice.exception.MenuNotFoundException
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UpdateMenuUseCase(private val logger: Logger,
                        private val saveMenuPort: SaveMenuPort,
                        private val getMenuByIdPort: GetMenuByIdPort
) {

    fun execute(menu: Menu, id: String): Mono<Menu> {
        logger.info("Update menu use case initiated. menu=${menu.toJson()}")
        menu.id = id
        return getMenuByIdPort.getById(id)
            .switchIfEmpty(Mono.error(MenuNotFoundException(id)))
            .then(saveMenuPort.save(menu))
            .doOnNext { logger.info("Update menu use case done.") }
    }

}
