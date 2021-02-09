package com.maitre.menuservice.adapter.product.`in`.delete

import com.maitre.menuservice.domain.product.usecase.DeleteProductUseCase
import com.maitre.menuservice.utils.toJson
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class DeleteProductHandlerImpl(private val logger: Logger,
private val deleteProductUseCase: DeleteProductUseCase
) : DeleteProductHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

        logger.info(
            "Delete Product Request initiated. " +
                    "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                    "headers=${serverRequest.headers()}"
        )

        return deleteProductUseCase.execute(serverRequest.pathVariable("id"))
            .then (
                ServerResponse.noContent().build()
            ).doOnSuccess {
                logger.info(
                    "Delete Product finished. " +
                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                )
            }

    }

}