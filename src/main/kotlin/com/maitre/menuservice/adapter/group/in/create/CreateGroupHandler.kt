package com.maitre.menuservice.adapter.group.`in`.create

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface CreateGroupHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}