package com.maitre.menuservice.adapter.product.`in`.get

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface GetProductByGroupHandler {
    fun getByGroup(serverRequest: ServerRequest): Mono<ServerResponse>
}