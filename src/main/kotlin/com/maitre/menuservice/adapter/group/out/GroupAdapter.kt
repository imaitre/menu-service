package com.maitre.menuservice.adapter.group.out

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.port.out.persistence.DeleteGroupPort
import com.maitre.menuservice.domain.group.port.out.persistence.DeleteGroupsByMenuIdPort
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupByIdPort
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupsByMenuIdPort
import com.maitre.menuservice.domain.group.port.out.persistence.SaveGroupPort
import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toEntity
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class GroupAdapter(private val groupRepository: GroupRepository) : SaveGroupPort, GetGroupsByMenuIdPort,
    DeleteGroupPort, GetGroupByIdPort,
    DeleteGroupsByMenuIdPort {

    override fun save(group: Group): Mono<Group> {
        return groupRepository.save(group.toEntity())
            .map { it.toDomain() }
    }

    override fun getByMenuId(menuId: String): Flux<Group> {
        return groupRepository.findAllByMenuId(menuId)
            .map { it.toDomain() }
    }

    override fun getById(groupId: String): Mono<Group> {
        return groupRepository.findById(groupId)
                .map { it.toDomain() }
    }

    override fun delete(groupId: String): Mono<Void>{
        return groupRepository.deleteById(groupId)
    }

    override fun deleteByMenuId(id: String): Mono<Void> {
        return groupRepository.deleteByMenuId(id)
    }
}