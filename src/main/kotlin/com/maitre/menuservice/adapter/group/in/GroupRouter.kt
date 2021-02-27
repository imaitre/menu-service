package com.maitre.menuservice.adapter.group.`in`

import com.maitre.menuservice.adapter.group.`in`.create.CreateGroupHandler
import com.maitre.menuservice.adapter.group.`in`.delete.DeleteGroupHandler
import com.maitre.menuservice.adapter.group.`in`.get.GetGroupsByMenuHandler
import com.maitre.menuservice.adapter.group.`in`.get.GetGroupHandler
import com.maitre.menuservice.adapter.group.`in`.update.UpdateGroupHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class GroupRouter(
    private val getGroupHandler: GetGroupHandler,
    private val getGroupsByMenuHandler: GetGroupsByMenuHandler,
    private val createGroupHandler: CreateGroupHandler,
    private val updateGroupHandler: UpdateGroupHandler,
    private val deleteGroupHandler: DeleteGroupHandler
) {

    @Bean
    fun groupRoutes() = router {
        GET("/group/{id}", getGroupHandler::execute)
        GET("/group", getGroupsByMenuHandler::execute)
        POST("/group", createGroupHandler::execute)
        PUT("/group/{id}", updateGroupHandler::execute)
        DELETE("/group/{id}", deleteGroupHandler::execute)
    }
}