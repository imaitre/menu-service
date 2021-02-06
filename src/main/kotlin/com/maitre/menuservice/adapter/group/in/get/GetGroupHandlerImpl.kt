package com.maitre.menuservice.adapter.menu.`in`.create

import com.maitre.menuservice.adapter.group.`in`.get.GetGroupHandler
import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.usecases.GetGroupsByMenuIdUseCase
import com.maitre.menuservice.exception.MissingParameterException
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.RuntimeException

@Component
class GetGroupHandlerImpl(
        private val logger: Logger,
        private val getGroupsByMenuIdUseCase: GetGroupsByMenuIdUseCase
) : GetGroupHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

        logger.info(
                "Get groups by menu_id Request initiated. " +
                        "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                        "headers=${serverRequest.headers()}, menu_id=${serverRequest.queryParam("menu_id")}")

        val groups = getGroupsByMenuIdUseCase.execute(serverRequest.queryParam("menu_id")
                .orElseThrow{ MissingParameterException("menu_id") })

        return ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(groups, Group::class.java)
                .doOnNext{
                    logger.info(
                            "Get Groups by menu_id finished"+
                                    "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                    )
                }
    }
}