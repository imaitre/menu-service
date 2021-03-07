package com.maitre.menuservice.adapter.optional

import com.maitre.menuservice.adapter.optional.`in`.create.CreateOptonalHandler
import com.maitre.menuservice.adapter.optional.`in`.get.GetOptionalsByMenuHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class OptionalRouter(
  private val createOptionalHandler: CreateOptonalHandler,
  private val getOptionalsByMenuHandler: GetOptionalsByMenuHandler)
{

  @Bean
  fun optionalRoutes() = router {
    POST("/optional", createOptionalHandler::execute)
    GET("/optional", getOptionalsByMenuHandler::execute)
  }
}