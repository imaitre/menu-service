package com.maitre.menuservice.adapter.product.`in`

import com.maitre.menuservice.adapter.product.`in`.create.CreateProductHandler
import com.maitre.menuservice.adapter.product.`in`.get.GetProductsByGroupHandler
import com.maitre.menuservice.adapter.product.`in`.get.GetProductHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class ProductRouter(
    private val createProductHandler: CreateProductHandler,
    private val getProductHandler: GetProductHandler,
    private val getProductsByGroupHandler: GetProductsByGroupHandler
) {

    @Bean
    fun productRoutes() = router {
        POST("/product", createProductHandler::execute)
        GET("/product/{id}", getProductHandler::execute)
        GET("/product", getProductsByGroupHandler::execute)
    }

}