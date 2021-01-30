package com.maitre.menuservice.adapter.`in`.create

import com.maitre.menuservice.adapter.`in`.dto.MenuRequestDTO
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class CreateMenuHandlerImpl : CreateMenuHandler {
    override fun create(serverRequest: ServerRequest): Mono<ServerResponse> {
        val menuRequestDTO = MenuRequestDTO("CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62", "Pasta", "pasta menu", true)
        return ok().contentType(MediaType.APPLICATION_JSON).body(menuRequestDTO.toMono(), MenuRequestDTO::class.java)
    }
}