package com.maitre.menuservice.adapter.menu.`in`.update

import com.maitre.menuservice.adapter.menu.`in`.dto.MenuRequestDTO
import com.maitre.menuservice.domain.menu.usecases.UpdateMenuUseCase
import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toJson
import com.maitre.menuservice.utils.toResponseDTO
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class UpdateMenuHandlerImpl(
    private val logger: Logger,
    private val updateMenuUseCase: UpdateMenuUseCase
) : UpdateMenuHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.bodyToMono(MenuRequestDTO::class.java)
            .doOnNext {
                logger.info(
                    "Update Menu Request initiated. " +
                            "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                            "body=${it.toJson()}, headers=${serverRequest.headers()}"
                )
            }
            .flatMap {
                updateMenuUseCase.execute(it.toDomain(), serverRequest.pathVariable("id"))
            }
            .map {
                it.toResponseDTO()
            }.flatMap {
                logger.info("ResponseBody=${it.toJson()}")
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(it)
            }.doOnNext {
                logger.info(
                    "Update Menu finished. " +
                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                )
            }

    }


}