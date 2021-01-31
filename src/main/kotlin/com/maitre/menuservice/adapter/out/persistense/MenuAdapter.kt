package com.maitre.menuservice.adapter.out.persistense

import com.maitre.menuservice.adapter.utils.toDomain
import com.maitre.menuservice.adapter.utils.toEntity
import com.maitre.menuservice.domain.entity.Menu
import com.maitre.menuservice.domain.port.out.persistence.SaveMenuPort
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class MenuAdapter(private val menuRepository: MenuRepository) : SaveMenuPort {

    override fun save(menu: Menu): Mono<Menu> {
        return menuRepository.save(menu.toEntity())
            .map { it.toDomain() }
    }

}