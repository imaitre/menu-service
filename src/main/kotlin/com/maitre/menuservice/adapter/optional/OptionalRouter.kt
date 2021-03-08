package com.maitre.menuservice.adapter.optional

import com.maitre.menuservice.adapter.optional.`in`.create.CreateOptonalHandler
import com.maitre.menuservice.adapter.optional.`in`.delete.DeleteOptionalHandler
import com.maitre.menuservice.adapter.optional.`in`.get.GetOptionalHandler
import com.maitre.menuservice.adapter.optional.`in`.get.GetOptionalsByMenuHandler
import com.maitre.menuservice.adapter.optional.`in`.update.UpdateOptionalHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class OptionalRouter(
  private val createOptionalHandler: CreateOptonalHandler,
  private val getOptionalsByMenuHandler: GetOptionalsByMenuHandler,
  private val getOptionalHandler: GetOptionalHandler,
  private val updateOptionalHandler: UpdateOptionalHandler,
 private  val deleteOptionalHandler: DeleteOptionalHandler)
{

  @Bean
  fun optionalRoutes() = router {
    POST("/optional", createOptionalHandler::execute)
    GET("/optional", getOptionalsByMenuHandler::execute)
    GET("/optional/{id}", getOptionalHandler::execute)
    PUT("/optional/{id}", updateOptionalHandler::execute)
    DELETE("/optional/{id}", deleteOptionalHandler::execute)

  }
}