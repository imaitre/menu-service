package com.maitre.menuservice.adapter.group.out

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupsByMenuIdPort
import com.maitre.menuservice.domain.group.port.out.persistence.SaveGroupPort
import com.maitre.menuservice.utils.toEntity
import com.maitre.menuservice.utils.toDomain
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class GroupsByMenuIdAdapter(private val groupRepository: GroupRepository) : SaveGroupPort, GetGroupsByMenuIdPort {

    override fun save(group: Group): Mono<Group> {
        return groupRepository.save(group.toEntity())
            .map { it.toDomain() }
    }

    override fun getByMenuId(menuId: String): Flux<Group> {
        return groupRepository.findAllByMenuId(menuId)
            .map { it.toDomain() }
    }
}