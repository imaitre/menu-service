package com.maitre.menuservice.adapter.product.`in`.create

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface CreateProductHandler {
    fun execute(serverRequest: ServerRequest): Mono<ServerResponse>
}