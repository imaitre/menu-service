package com.maitre.menuservice.adapter.menu.`in`.get

import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.usecases.GetMenusByCustomerUseCase
import com.maitre.menuservice.exception.MissingParameterException
import com.maitre.menuservice.utils.Constants
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class GetMenusByCustomerHandlerImpl(
    private val logger: Logger,
    private val getMenusByCustomerUseCase: GetMenusByCustomerUseCase
) : GetMenusByCustomerHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {
        logger.info(
            "Get Menus By Customer Request initiated. " +
                    "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                    "headers=${serverRequest.headers()}, customerId=${serverRequest.queryParam(Constants.CUSTOMER_ID)}"
        )

        val products = getMenusByCustomerUseCase.execute(serverRequest.queryParam(Constants.CUSTOMER_ID)
            .orElseThrow { MissingParameterException(Constants.CUSTOMER_ID) })

        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(products, Menu::class.java)
            .doOnNext {
                logger.info(
                    "Get Menus By Customer Request finished. " +
                            "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                )
            }
    }

}