package com.maitre.menuservice.adapter.group.out

import com.maitre.menuservice.adapter.group.out.persistence.entity.GroupEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface GroupRepository : ReactiveMongoRepository<GroupEntity, String>{
    fun findAllByMenuId(id: String): Flux<GroupEntity>

    fun deleteByMenuId(id: String): Mono<Void>
}