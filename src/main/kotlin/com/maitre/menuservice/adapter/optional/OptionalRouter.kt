package com.maitre.menuservice.adapter.optional

import com.maitre.menuservice.adapter.optional.`in`.create.CreateOptonalHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class OptionalRouter(private val createOptonalHandler: CreateOptonalHandler)
{

  @Bean
  fun menuRoutes() = router {
    POST("/optional", createOptonalHandler::execute)
  }
}