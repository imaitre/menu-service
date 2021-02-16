package com.maitre.menuservice.adapter.product.`in`.get

import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.usecase.GetProductsByGroupUseCase
import com.maitre.menuservice.exception.MissingParameterException
import com.maitre.menuservice.utils.Constants.GROUP_ID
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class GetProductsByGroupHandlerImpl(
    private val logger: Logger,
    private val getProductsByGroupUseCase: GetProductsByGroupUseCase
) : GetProductsByGroupHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {
        logger.info(
            "Get Products By Group Request initiated. " +
                    "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                    "headers=${serverRequest.headers()}, groupId=${serverRequest.queryParam(GROUP_ID)}"
        )

        val products = getProductsByGroupUseCase.execute(serverRequest.queryParam(GROUP_ID)
            .orElseThrow { MissingParameterException(GROUP_ID) })

        return ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(products, Product::class.java)
            .doOnNext {
                logger.info(
                    "Get Products By Group Request finished. " +
                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                )
            }

    }

}