package com.maitre.menuservice.adapter.group.`in`.update

import com.maitre.menuservice.adapter.group.`in`.dto.GroupRequestDTO
import com.maitre.menuservice.domain.group.usecases.UpdateGroupUseCase
import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toJson
import com.maitre.menuservice.utils.toResponseDTO
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class UpdateGroupHandlerImpl(
        private val logger: Logger,
        private val updateGroupUseCase: UpdateGroupUseCase
) : UpdateGroupHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

        return serverRequest.bodyToMono(GroupRequestDTO::class.java)
                .doOnNext {
                    logger.info(
                            "Update Group Request initiated. " +
                                    "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                                    "body=${it.toJson()}, headers=${serverRequest.headers()}"
                    )
                }
                .flatMap {
                    updateGroupUseCase.execute(it.toDomain(), serverRequest.pathVariable("id"))
                }
                .map {
                    it.toResponseDTO()
                }.flatMap {
                    logger.info("ResponseBody=${it.toJson()}")
                    ok().contentType(MediaType.APPLICATION_JSON).bodyValue(it)
                }.doOnNext {
                    logger.info(
                            "Update Group finished. " +
                                    "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                    )
                }
    }
}