package com.maitre.menuservice.adapter.group.out

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupPort
import com.maitre.menuservice.domain.group.port.out.persistence.SaveGroupPort
import com.maitre.menuservice.utils.toEntity
import com.maitre.menuservice.utils.toDomain
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class GroupAdapter(private val groupRepository: GroupRepository) : SaveGroupPort, GetGroupPort {

    override fun save(group: Group): Mono<Group> {
        return groupRepository.save(group.toEntity())
            .map { it.toDomain() }
    }

    override fun get(id: String): Mono<Group> {
        return groupRepository.findById(id)
            .map { it.toDomain() }
    }


}