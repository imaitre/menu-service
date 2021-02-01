package com.maitre.menuservice.adapter.`in`.get

import com.maitre.menuservice.adapter.utils.toJson
import com.maitre.menuservice.adapter.utils.toResponseDTO
import com.maitre.menuservice.domain.usecases.GetMenuUseCase
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class GetMenuHandlerImpl(
    private val logger: Logger,
    private val getMenuUseCase: GetMenuUseCase
) : GetMenuHandler {

    override fun get(serverRequest: ServerRequest): Mono<ServerResponse> {

        logger.info(
            "Get Menu Request initiated. " +
                    "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                    "headers=${serverRequest.headers()}")

        return getMenuUseCase.execute(serverRequest.pathVariable("id"))
            .map { it.toResponseDTO() }
            .flatMap {
                logger.info("ResponseBody=${it.toJson()}")
                ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(it)
            }.doOnNext {
                logger.info(
                    "Get Menu finished. " +
                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                )
            }

    }

}