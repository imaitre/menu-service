package com.maitre.menuservice.adapter.group.`in`.delete

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface DeleteGroupHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}