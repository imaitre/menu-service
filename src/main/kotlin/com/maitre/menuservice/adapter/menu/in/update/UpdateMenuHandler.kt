package com.maitre.menuservice.adapter.menu.`in`.update

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface UpdateMenuHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}