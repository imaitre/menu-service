package com.maitre.menuservice.adapter.`in`.create

import com.maitre.menuservice.adapter.`in`.dto.MenuRequestDTO
import com.maitre.menuservice.adapter.`in`.dto.MenuResponseDTO
import com.maitre.menuservice.adapter.utils.toDomain
import com.maitre.menuservice.adapter.utils.toJson
import com.maitre.menuservice.adapter.utils.toResponseDTO
import com.maitre.menuservice.domain.usecases.CreateMenuUseCase
import java.util.logging.Level
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromPublisher

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class CreateMenuHandlerImpl(
    private val logger: Logger,
    private val createMenuUseCase: CreateMenuUseCase
) : CreateMenuHandler {
    override fun create(serverRequest: ServerRequest): Mono<ServerResponse> {

        return serverRequest.bodyToMono(MenuRequestDTO::class.java)
            .doOnNext {
                logger.info(
                    "Create Menu Request initiated. " +
                            "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                            "body=${it.toJson()}, headers=${serverRequest.headers()}"
                )
            }
            .flatMap {
                createMenuUseCase.execute(it.toDomain())
            }
            .map {
                it.toResponseDTO()
            }.flatMap {
                logger.info("ResponseBody=${it.toJson()}")
                ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(it)
            }.doOnNext {
                logger.info(
                    "Create Menu finished. " +
                            "ResponseCode=${it.statusCode()}, path=${it.headers()}"
                )
            }

    }
}