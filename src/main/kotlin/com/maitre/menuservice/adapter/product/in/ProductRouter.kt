package com.maitre.menuservice.adapter.product.`in`

import com.maitre.menuservice.adapter.product.`in`.create.CreateProductHandler
import com.maitre.menuservice.adapter.product.`in`.delete.DeleteProductHandler
import com.maitre.menuservice.adapter.product.`in`.get.GetProductHandler
import com.maitre.menuservice.adapter.product.`in`.get.GetProductsByGroupHandler
import com.maitre.menuservice.adapter.product.`in`.update.UpdateProductHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class ProductRouter(
    private val createProductHandler: CreateProductHandler,
    private val getProductHandler: GetProductHandler,
    private val getProductsByGroupHandler: GetProductsByGroupHandler,
    private val deleteProductHandler: DeleteProductHandler,
    private val updateProductHandler: UpdateProductHandler
) {

    @Bean
    fun productRoutes() = router {
        GET("/product/{id}", getProductHandler::execute)
        GET("/product", getProductsByGroupHandler::execute)
        POST("/product", createProductHandler::execute)
        PUT("/product/{id}", updateProductHandler::execute)
        DELETE("/product/{id}", deleteProductHandler::execute)
    }

}