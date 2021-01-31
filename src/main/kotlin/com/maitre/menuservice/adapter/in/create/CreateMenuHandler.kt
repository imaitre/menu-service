package com.maitre.menuservice.adapter.`in`.create

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface CreateMenuHandler {
    fun create(serverRequest: ServerRequest): Mono<ServerResponse>
}