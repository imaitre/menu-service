package com.maitre.menuservice.adapter.`in`

import com.maitre.menuservice.adapter.`in`.create.CreateMenuHandler
import com.maitre.menuservice.adapter.`in`.get.GetMenuHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class MenuRouter(
    private val createMenuHandler: CreateMenuHandler,
    private val getMenuHandler: GetMenuHandler
) {

    @Bean
    fun router() = router {
        POST("/menu", createMenuHandler::create)
        GET("/menu/{id}", getMenuHandler::get)
    }
}