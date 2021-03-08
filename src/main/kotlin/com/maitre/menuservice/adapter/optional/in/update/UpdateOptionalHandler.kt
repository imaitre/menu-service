package com.maitre.menuservice.adapter.optional.`in`.update

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface UpdateOptionalHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}