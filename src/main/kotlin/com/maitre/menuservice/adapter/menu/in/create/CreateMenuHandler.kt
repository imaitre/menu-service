package com.maitre.menuservice.adapter.menu.`in`.create

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface CreateMenuHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}