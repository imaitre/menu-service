package com.maitre.menuservice.adapter.menu.`in`.create

import com.maitre.menuservice.adapter.menu.`in`.dto.MenuRequestDTO
import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toJson
import com.maitre.menuservice.utils.toResponseDTO
import com.maitre.menuservice.domain.menu.usecases.CreateMenuUseCase
import java.net.URI
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.created
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
                created(URI("IMPLEMENTAR_HATEOS_SELF${serverRequest.path()}/${it.id}"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(it)
            }.doOnNext {
                logger.info(
                    "Create Menu finished. " +
                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                )
            }

    }
}