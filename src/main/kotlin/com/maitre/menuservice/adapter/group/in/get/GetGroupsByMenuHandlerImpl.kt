package com.maitre.menuservice.adapter.group.`in`.get

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.usecases.GetGroupsByMenuIdUseCase
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
class GetGroupsByMenuHandlerImpl(
        private val logger: Logger,
        private val getGroupsByMenuIdUseCase: GetGroupsByMenuIdUseCase
) : GetGroupsByMenuHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

        logger.info(
                "Get groups by menu_id Request initiated. " +
                        "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                        "headers=${serverRequest.headers()}, menu_id=${serverRequest.queryParam(MENU_ID)}")

        val groups = getGroupsByMenuIdUseCase.execute(serverRequest.queryParam(MENU_ID)
                .orElseThrow{ MissingParameterException(MENU_ID) })

        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(groups, Group::class.java)
                .doOnNext{
                    logger.info(
                            "Get Groups by menu_id finished"+
                                    "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                    )
                }
    }
}