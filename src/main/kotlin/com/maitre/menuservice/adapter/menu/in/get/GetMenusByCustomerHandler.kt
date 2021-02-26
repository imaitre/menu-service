package com.maitre.menuservice.adapter.menu.`in`.get

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface GetMenusByCustomerHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}
