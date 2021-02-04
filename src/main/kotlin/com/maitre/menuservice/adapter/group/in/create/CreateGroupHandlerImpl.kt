package com.maitre.menuservice.adapter.menu.`in`.create

import com.maitre.menuservice.adapter.group.`in`.create.CreateGroupHandler
import com.maitre.menuservice.adapter.group.`in`.dto.GroupRequestDTO
import com.maitre.menuservice.domain.group.usecases.CreateGroupUseCase
import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toJson
import com.maitre.menuservice.utils.toResponseDTO
import java.net.URI
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.created
import reactor.core.publisher.Mono

@Component
class CreateGroupHandlerImpl(
        private val logger: Logger,
        private val createGroupUseCase: CreateGroupUseCase
) : CreateGroupHandler {

    override fun execute(serverRequest: ServerRequest): Mono<ServerResponse> {

        return serverRequest.bodyToMono(GroupRequestDTO::class.java)
                .doOnNext {
                    logger.info(
                            "Create Group Request initiated. " +
                                    "method=${serverRequest.method()}, path=${serverRequest.path()}, " +
                                    "body=${it.toJson()}, headers=${serverRequest.headers()}"
                    )
                }
                .flatMap {
                    createGroupUseCase.execute(it.toDomain())
                }
                .map {
                    it.toResponseDTO()
                }.flatMap {
                    logger.info("ResponseBody=${it.toJson()}")
                    created(URI("IMPLEMENTAR_HATEOS_SELF${serverRequest.path()}/${it.id}"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(it)
                }.doOnNext {
                    logger.info(
                            "Create Group finished. " +
                                    "ResponseCode=${it.statusCode()}, headers=${it.headers()}"
                    )
                }

    }
}