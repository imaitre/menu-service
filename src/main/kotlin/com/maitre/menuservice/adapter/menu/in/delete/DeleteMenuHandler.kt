package com.maitre.menuservice.adapter.menu.`in`.delete

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface DeleteMenuHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}