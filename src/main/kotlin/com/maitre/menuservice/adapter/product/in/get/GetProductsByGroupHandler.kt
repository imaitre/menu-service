package com.maitre.menuservice.adapter.product.`in`.get

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface GetProductsByGroupHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}