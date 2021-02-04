package com.maitre.menuservice.adapter.menu.out

import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toEntity
import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenuPort
import com.maitre.menuservice.domain.menu.port.out.persistence.SaveMenuPort
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class MenuAdapter(private val menuRepository: MenuRepository) : SaveMenuPort, GetMenuPort {

    override fun save(menu: Menu): Mono<Menu> {
        return menuRepository.save(menu.toEntity())
            .map { it.toDomain() }
    }

    override fun get(id: String): Mono<Menu> {
        return menuRepository.findById(id)
            .map { it.toDomain() }
    }


}