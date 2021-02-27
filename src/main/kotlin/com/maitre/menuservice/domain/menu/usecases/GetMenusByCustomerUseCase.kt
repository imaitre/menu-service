package com.maitre.menuservice.domain.menu.usecases

import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenusByCustomerPort
import com.maitre.menuservice.exception.MenuNotFoundException
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class GetMenusByCustomerUseCase(
    private val logger: Logger,
    private val getMenusByCustomerPort: GetMenusByCustomerPort
) {

    fun execute(id: String): Flux<Menu> {
        logger.info("Get menus by customer use case initiated. id=$id")
        return getMenusByCustomerPort.getByCustomerId(id)
            .switchIfEmpty(Mono.error(MenuNotFoundException(id)))
            .doOnNext { logger.info("Get menus by customer use case done. id=${id}, menu=${it}") }
    }

}
