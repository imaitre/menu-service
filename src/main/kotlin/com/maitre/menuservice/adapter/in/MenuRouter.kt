package com.maitre.menuservice.adapter.`in`

import com.maitre.menuservice.adapter.`in`.create.CreateMenuHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class MenuRouter(private val createMenuHandler: CreateMenuHandler) {

    @Bean
    fun router() = router {
        POST("/menu", createMenuHandler::create)
    }
}