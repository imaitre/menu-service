package com.maitre.menuservice.adapter.menu.`in`.create

import com.maitre.menuservice.adapter.menu.`in`.delete.DeleteMenuHandler
import com.maitre.menuservice.domain.menu.usecases.DeleteMenuUseCase
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class DeleteMenuHandlerImpl(
        private val logger: Logger,
        private val deleteMenuUseCase: DeleteMenuUseCase
) : DeleteMenuHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

        logger.info(
                "delete menu by id request initiated. " +
                        "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                        "headers=${serverRequest.headers()}")

        return deleteMenuUseCase.execute(serverRequest.pathVariable("id"))
                 .then(
                         ServerResponse.noContent().build()
                 ).doOnSuccess {
                     logger.info(
                             "Delete menu finished. " +
                                     "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                     )
                 }
    }
}