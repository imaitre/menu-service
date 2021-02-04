package com.maitre.menuservice.adapter.product.`in`

import com.maitre.menuservice.adapter.product.`in`.create.CreateProductHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class ProductRouter(private val createProductHandler: CreateProductHandler) {

    @Bean
    fun productRoutes() = router {
        POST("/product", createProductHandler::create)
//        GET("/product/{id}", y)
//        GET("/product?groupId={id}", z)
    }

}