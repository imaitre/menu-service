package com.maitre.menuservice.adapter.optional.`in`.get

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface GetOptionalHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}
