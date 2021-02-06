package com.maitre.menuservice.adapter.product.`in`.get

import com.maitre.menuservice.domain.product.usecase.GetProductUseCase
import com.maitre.menuservice.utils.toJson
import com.maitre.menuservice.utils.toResponseDTO
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class GetProductHandlerImpl(
    private val logger: Logger,
    private val getProductUseCase: GetProductUseCase
) : GetProductHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

        logger.info(
            "Get Product Request initiated. " +
                    "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                    "headers=${serverRequest.headers()}"
        )

        return getProductUseCase.execute(serverRequest.pathVariable("id"))
            .map { it.toResponseDTO() }
            .flatMap {
                logger.info("ResponseBody=${it.toJson()}")
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(it)
            }.doOnNext {
                logger.info(
                    "Get Product finished. " +
                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                )
            }

    }
}