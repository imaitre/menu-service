package com.maitre.menuservice.adapter.menu.`in`.create

import com.maitre.menuservice.adapter.optional.`in`.delete.DeleteOptionalHandler
import com.maitre.menuservice.domain.optional.usecases.DeleteOptionalUseCase
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class DeleteOptionalHandlerImpl(
        private val logger: Logger,
        private val deleteOptionalUseCase: DeleteOptionalUseCase
) : DeleteOptionalHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

        logger.info(
                "delete optional by id request initiated. " +
                        "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                        "headers=${serverRequest.headers()}")

        return deleteOptionalUseCase.execute(serverRequest.pathVariable("id"))
                 .then(
                         ServerResponse.noContent().build()
                 ).doOnSuccess {
                     logger.info(
                             "delete optional finished. " +
                                     "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                     )
                 }
    }
}