package com.maitre.menuservice.adapter.product.`in`.create

import com.maitre.menuservice.adapter.product.`in`.dto.ProductRequestDTO
import com.maitre.menuservice.domain.product.usecase.CreateProductUseCase
import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toJson
import com.maitre.menuservice.utils.toResponseDTO
import java.net.URI
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.created
import reactor.core.publisher.Mono

@Component
class CreateProductHandlerImpl(
    private val logger: Logger,
    private val createProductUseCase: CreateProductUseCase,
    @Value("\${server.port}")
    private var port: Int,
    @Value("\${server.uri}")
    private val uri: String
) : CreateProductHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

        return serverRequest.bodyToMono(ProductRequestDTO::class.java)
            .doOnNext {
                logger.info(
                    "Create Product Request initiated. " +
                            "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                            "body=${it.toJson()}, headers=${serverRequest.headers()}"
                )
            }
            .flatMap {
                createProductUseCase.execute(it.toDomain())
            }
            .map {
                it.toResponseDTO()
            }.flatMap {
                logger.info("ResponseBody=${it.toJson()}")
                created(URI("$uri:$port${serverRequest.path()}/${it.id}"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(it)
            }.doOnNext {
                logger.info(
                    "Create Product finished. " +
                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                )
            }

    }

}