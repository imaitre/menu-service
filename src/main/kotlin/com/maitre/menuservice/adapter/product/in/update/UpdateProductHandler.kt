package com.maitre.menuservice.adapter.product.`in`.update

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface UpdateProductHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}