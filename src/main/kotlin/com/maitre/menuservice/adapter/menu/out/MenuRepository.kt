package com.maitre.menuservice.adapter.menu.out

import com.maitre.menuservice.adapter.menu.out.persistence.entity.MenuEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuRepository : ReactiveMongoRepository<MenuEntity, String>