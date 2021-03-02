package com.maitre.menuservice.adapter.optional.`in`.create

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface CreateOptonalHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}