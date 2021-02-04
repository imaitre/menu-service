package com.maitre.menuservice.adapter.product.`in`.create

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface CreateProductHandler {
    fun create(serverRequest: ServerRequest): Mono<ServerResponse>
}