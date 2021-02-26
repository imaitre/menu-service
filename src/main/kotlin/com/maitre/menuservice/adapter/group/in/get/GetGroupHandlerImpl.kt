package com.maitre.menuservice.adapter.group.`in`.get

import com.maitre.menuservice.domain.group.usecases.GetGroupUseCase
import com.maitre.menuservice.utils.toJson
import com.maitre.menuservice.utils.toResponseDTO
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class GetGroupHandlerImpl(
    private val logger: Logger,
    private val getGroupUseCase: GetGroupUseCase
) : GetGroupHandler{

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {
        logger.info(
            "Get Group Request initiated. " +
                    "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                    "headers=${serverRequest.headers()}")

        return getGroupUseCase.execute(serverRequest.pathVariable("id"))
            .map { it.toResponseDTO() }
            .flatMap {
                logger.info("ResponseBody=${it.toJson()}")
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(it)
            }.doOnNext {
                logger.info(
                    "Get Group finished. " +
                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                )
            }
    }
}