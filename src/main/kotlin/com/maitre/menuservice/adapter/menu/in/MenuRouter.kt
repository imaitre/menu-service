package com.maitre.menuservice.adapter.menu.`in`

import com.maitre.menuservice.adapter.menu.`in`.create.CreateMenuHandler
import com.maitre.menuservice.adapter.menu.`in`.get.GetMenuHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class MenuRouter(
    private val createMenuHandler: CreateMenuHandler,
    private val getMenuHandler: GetMenuHandler
) {

    @Bean
    fun menuRoutes() = router {
        POST("/menu", createMenuHandler::execute)
        GET("/menu/{id}", getMenuHandler::get)
    }
}