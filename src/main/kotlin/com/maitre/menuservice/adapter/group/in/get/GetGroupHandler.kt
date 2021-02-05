package com.maitre.menuservice.adapter.group.`in`.get

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface GetGroupHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}