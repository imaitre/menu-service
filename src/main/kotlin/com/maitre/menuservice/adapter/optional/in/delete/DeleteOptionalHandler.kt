package com.maitre.menuservice.adapter.optional.`in`.delete

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface DeleteOptionalHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}