package com.maitre.menuservice.adapter.product.`in`.update

import com.maitre.menuservice.adapter.product.`in`.dto.ProductRequestDTO
import com.maitre.menuservice.domain.product.usecase.UpdateProductUseCase
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
class UpdateProductHandlerImpl(
    private val logger: Logger,
    private val updateProductUseCase: UpdateProductUseCase
) : UpdateProductHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.bodyToMono(ProductRequestDTO::class.java)
            .doOnNext {
                logger.info(
                    "Update Product Request initiated. " +
                            "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                            "body=${it.toJson()}, headers=${serverRequest.headers()}"
                )
            }
            .flatMap {
                updateProductUseCase.execute(it.toDomain(), serverRequest.pathVariable("id"))
            }
            .map {
                it.toResponseDTO()
            }.flatMap {
                logger.info("ResponseBody=${it.toJson()}")
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(it)
            }.doOnNext {
                logger.info(
                    "Update Product finished. " +
                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                )
            }

    }


}