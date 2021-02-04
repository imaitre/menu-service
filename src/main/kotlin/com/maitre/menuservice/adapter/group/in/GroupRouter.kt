package com.maitre.menuservice.adapter.group.`in`

import com.maitre.menuservice.adapter.group.`in`.create.CreateGroupHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class GroupRouter(
        private val createGroupHandler: CreateGroupHandler,
) {

    @Bean
    fun routerGroup() = router {
        POST("/group", createGroupHandler::execute)
    }
}