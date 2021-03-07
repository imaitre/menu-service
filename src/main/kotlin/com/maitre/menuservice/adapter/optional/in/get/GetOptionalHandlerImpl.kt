package com.maitre.menuservice.adapter.optional.`in`.get

import com.maitre.menuservice.domain.optional.usecases.GetOptionalByIdUseCase
import com.maitre.menuservice.utils.toJson
import com.maitre.menuservice.utils.toResponseDTO
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class GetOptionalHandlerImpl(
    private val logger: Logger,
    private val getOptionalByIdUseCase: GetOptionalByIdUseCase
) : GetOptionalHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {
        logger.info(
            "Get Optional Request initiated. " +
                    "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                    "headers=${serverRequest.headers()}")

        return getOptionalByIdUseCase.execute(serverRequest.pathVariable("id"))
            .map { it.toResponseDTO() }
            .flatMap {
                logger.info("ResponseBody=${it.toJson()}")
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(it)
            }.doOnNext {
                logger.info(
                    "Get Optional finished. " +
                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                )
            }
    }
}