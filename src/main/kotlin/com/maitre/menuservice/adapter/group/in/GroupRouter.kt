package com.maitre.menuservice.adapter.group.`in`

import com.maitre.menuservice.adapter.group.`in`.create.CreateGroupHandler
import com.maitre.menuservice.adapter.group.`in`.get.GetGroupHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class GroupRouter(
        private val createGroupHandler: CreateGroupHandler,
        private val getGroupHandler: GetGroupHandler,
) {

    @Bean
    fun groupRoutes() = router {
        POST("/group", createGroupHandler::execute)
        GET("/group", getGroupHandler::execute)
    }

}