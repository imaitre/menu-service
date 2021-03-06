package com.maitre.menuservice.adapter.optional

import com.maitre.menuservice.adapter.optional.`in`.create.CreateOptonalHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class OptionalRouter(private val createOptionalHandler: CreateOptonalHandler)
{

  @Bean
  fun optionalRoutes() = router {
    POST("/optional", createOptionalHandler::execute)
  }
}