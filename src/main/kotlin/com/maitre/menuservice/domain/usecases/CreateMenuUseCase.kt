package com.maitre.menuservice.domain.usecases

import com.maitre.menuservice.adapter.utils.toJson
import com.maitre.menuservice.domain.entity.Menu
import com.maitre.menuservice.domain.port.out.persistence.SaveMenuPort
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class CreateMenuUseCase(private val logger: Logger,
                        private val saveMenuPort: SaveMenuPort) {

    fun execute(menu: Menu) : Mono<Menu> {
        logger.info("Create menu use case initiated. menu=${menu.toJson()}")
        return Menu("uuid", "custid", "nmomee", "dsfdd", true, null).toMono()//saveMenuPort.save(menu)
            .doOnNext { logger.info("Create menu use case done.") }
    }
}