package com.maitre.menuservice.adapter.out.persistense

import com.maitre.menuservice.adapter.out.persistense.entity.MenuEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuRepository : ReactiveMongoRepository<MenuEntity, String>