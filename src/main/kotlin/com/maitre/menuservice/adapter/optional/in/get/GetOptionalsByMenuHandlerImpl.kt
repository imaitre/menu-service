package com.maitre.menuservice.adapter.optional.`in`.get

import com.maitre.menuservice.domain.optional.entity.Optional
import com.maitre.menuservice.domain.optional.usecases.GetOptionalsByMenuIdUseCase
import com.maitre.menuservice.exception.MissingParameterException
import com.maitre.menuservice.utils.Constants.MENU_ID
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class GetOptionalsByMenuHandlerImpl(
        private val logger: Logger,
        private val getOptionalsByMenuIdUseCase: GetOptionalsByMenuIdUseCase
) : GetOptionalsByMenuHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

        logger.info(
                "Get optionals by menu_id Request initiated. " +
                        "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                        "headers=${serverRequest.headers()}, menu_id=${serverRequest.queryParam(MENU_ID)}")

        val optionals = getOptionalsByMenuIdUseCase.execute(serverRequest.queryParam(MENU_ID)
                .orElseThrow{ MissingParameterException(MENU_ID) })

        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(optionals, Optional::class.java)
                .doOnNext{
                    logger.info(
                            "Get optionals by menu_id finished"+
                                    "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                    )
                }
    }
}