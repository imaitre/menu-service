package com.maitre.menuservice.adapter.optional.`in`.create

import com.maitre.menuservice.adapter.optional.dto.OptionalRequestDTO
import com.maitre.menuservice.domain.optional.usecases.CreateOptionalUseCase
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
import java.net.URI

@Component
class CreateOptionalHandlerImpl(
  private val logger: Logger,
  private val createOptionalUseCase: CreateOptionalUseCase,
  @Value("\${server.port}")
  private var port: Int,
  @Value("\${server.uri}")
  private val uri: String
) : CreateOptonalHandler {

  override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

    return serverRequest.bodyToMono(OptionalRequestDTO::class.java)
      .doOnNext {
        logger.info(
          "Create Optional Request initiated. " +
                  "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                  "body=${it.toJson()}, headers=${serverRequest.headers()}"
        )
      }
      .flatMap {
        createOptionalUseCase.execute(it.toDomain())
      }
      .map {
        it.toResponseDTO()
      }.flatMap {
        logger.info("ResponseBody=${it.toJson()}")
        ServerResponse.created(URI("$uri:$port${serverRequest.path()}/${it.id}"))
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(it)
      }.doOnNext {
        logger.info(
          "Create Optional finished. " +
                  "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
        )
      }

  }
}