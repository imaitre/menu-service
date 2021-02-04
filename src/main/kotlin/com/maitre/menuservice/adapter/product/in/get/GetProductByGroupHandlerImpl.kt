package com.maitre.menuservice.adapter.product.`in`.get

import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.usecase.GetProductByGroupUseCase
import com.maitre.menuservice.utils.toJson
import com.maitre.menuservice.utils.toResponseDTO
import java.lang.RuntimeException
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class GetProductByGroupHandlerImpl(
    private val logger: Logger,
    private val getProductByGroupUseCase: GetProductByGroupUseCase
) : GetProductByGroupHandler {

    override fun getByGroup(serverRequest: ServerRequest): Mono<ServerResponse> {
        logger.info(
            "Get Product By Group Request initiated. " +
                    "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                    "headers=${serverRequest.headers()}, groupId=${serverRequest.queryParam("groupId")}"
        )

        val execute = getProductByGroupUseCase.execute(
            serverRequest.queryParam("groupId").orElseThrow { RuntimeException("groupId parameter is missing.") })

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(execute, Product::class.java)
            .doOnNext {
                logger.info(
                    "Get Product By Group Request finished. " +
                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                )
            }


//        return ok()
//            .contentType(MediaType.APPLICATION_JSON)
//            .body(clubService.getAllClubs(), Club::class.java)

//        return getProductByGroupUseCase.execute(serverRequest.queryParam("groupId"))
//            .map { it.toResponseDTO() }
//            .flatMap {
//                logger.info("ResponseBody=${it.toJson()}")
//                ServerResponse.ok()
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .bodyValue(it)
//            }.doOnNext {
//                logger.info(
//                    "Get Product By Group finished. " +
//                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
//                )
//            }
    }

}