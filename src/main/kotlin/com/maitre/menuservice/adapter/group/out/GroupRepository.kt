package com.maitre.menuservice.adapter.group.out

import com.maitre.menuservice.adapter.group.out.persistence.entity.GroupEntity
import com.maitre.menuservice.adapter.menu.out.persistence.entity.MenuEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface GroupRepository : ReactiveMongoRepository<GroupEntity, String>{
    fun findAllByMenuId(menuId: String): Flux<GroupEntity>
}