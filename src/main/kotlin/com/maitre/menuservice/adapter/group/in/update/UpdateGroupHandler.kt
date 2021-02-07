package com.maitre.menuservice.adapter.group.`in`.update

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface UpdateGroupHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}