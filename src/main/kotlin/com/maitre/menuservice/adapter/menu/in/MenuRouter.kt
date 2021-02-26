package com.maitre.menuservice.adapter.menu.`in`

import com.maitre.menuservice.adapter.menu.`in`.create.CreateMenuHandler
import com.maitre.menuservice.adapter.menu.`in`.delete.DeleteMenuHandler
import com.maitre.menuservice.adapter.menu.`in`.get.GetMenusByCustomerHandler
import com.maitre.menuservice.adapter.menu.`in`.get.GetMenuHandler
import com.maitre.menuservice.adapter.menu.`in`.update.UpdateMenuHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class MenuRouter(
    private val createMenuHandler: CreateMenuHandler,
    private val getMenuHandler: GetMenuHandler,
    private val getMenusByCustomerHandler: GetMenusByCustomerHandler,
    private val updateMenuHandler: UpdateMenuHandler,
    private val deleteMenuHandler: DeleteMenuHandler
) {

    @Bean
    fun menuRoutes() = router {
        GET("/menu/{id}", getMenuHandler::execute)
        GET("/menu", getMenusByCustomerHandler::execute)
        POST("/menu", createMenuHandler::execute)
        PUT("/menu/{id}", updateMenuHandler::execute)
        DELETE("/menu/{id}", deleteMenuHandler::execute)
    }
}