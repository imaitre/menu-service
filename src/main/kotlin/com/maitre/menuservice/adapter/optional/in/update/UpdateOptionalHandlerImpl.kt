package com.maitre.menuservice.adapter.optional.`in`.update

import com.maitre.menuservice.adapter.optional.dto.OptionalRequestDTO
import com.maitre.menuservice.domain.optional.usecases.UpdateOptionalUseCase
import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toJson
import com.maitre.menuservice.utils.toResponseDTO
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class UpdateOptionalHandlerImpl(
  private val logger: Logger,
  private val updateOptionalUseCase: UpdateOptionalUseCase,
  @Value("\${server.port}")
  private var port: Int,
  @Value("\${server.uri}")
  private val uri: String
) : UpdateOptionalHandler {

  override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

    return serverRequest.bodyToMono(OptionalRequestDTO::class.java)
      .doOnNext {
        logger.info(
          "Update Optional Request initiated. " +
                  "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                  "body=${it.toJson()}, headers=${serverRequest.headers()}"
        )
      }
      .flatMap {
        updateOptionalUseCase.execute(it.toDomain(), serverRequest.pathVariable("id"))
      }
      .map {
        it.toResponseDTO()
      }.flatMap {
        logger.info("ResponseBody=${it.toJson()}")
        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(it)
      }.doOnNext {
        logger.info(
          "Update Optional finished. " +
                  "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
        )
      }
  }
}