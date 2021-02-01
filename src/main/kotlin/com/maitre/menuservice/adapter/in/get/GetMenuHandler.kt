package com.maitre.menuservice.adapter.`in`.get

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface GetMenuHandler {
    fun get(serverRequest: ServerRequest): Mono<ServerResponse>
}