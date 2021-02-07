package com.maitre.menuservice.adapter.menu.`in`.create

import com.maitre.menuservice.adapter.group.`in`.delete.DeleteGroupHandler
import com.maitre.menuservice.domain.group.usecases.DeleteGroupUseCase
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class DeleteGroupHandlerImpl(
        private val logger: Logger,
        private val deleteGroupUseCase: DeleteGroupUseCase
) : DeleteGroupHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

        logger.info(
                "delete group by id request initiated. " +
                        "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                        "headers=${serverRequest.headers()}")

         deleteGroupUseCase.execute(serverRequest.pathVariable("id"))

        return ServerResponse.noContent().build()
                .doOnNext{
                    logger.info(
                            "Delete group by id finished"+
                                    "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                    )
                }
    }
}