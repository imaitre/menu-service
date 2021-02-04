package com.maitre.menuservice.exception

import org.slf4j.Logger
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.HttpMessageWriter
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.result.view.ViewResolver
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono

@Component
@Order(-1)
class GlobalExceptionHandler(private val logger: Logger) : WebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {

        return handle(ex)
            .flatMap {
                it.writeTo(exchange, HandlerStrategiesResponseContext(HandlerStrategies.withDefaults()))
            }
            .flatMap {
                Mono.empty<Void>()
            }
    }

    fun handle(throwable: Throwable): Mono<ServerResponse> {
        return when (throwable) {
            is MenuNotFoundException -> {
                getDefaultResponseMessage(throwable.message, HttpStatus.NOT_FOUND)
            } is ProductNotFoundException -> {
                getDefaultResponseMessage(throwable.message, HttpStatus.NOT_FOUND)
            } is MissingParameterException -> {
                getDefaultResponseMessage(throwable.message, HttpStatus.BAD_REQUEST)
            }
            else -> {
                logger.error(throwable.message, throwable)
                ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build()
            }
        }
    }

    private fun getDefaultResponseMessage(message: String?, httpStatus: HttpStatus) : Mono<ServerResponse> {
        val errorMessage = message ?: "Error message not specified."
        logger.warn(errorMessage)
        return ServerResponse.status(httpStatus).bodyValue(ErrorResponse(description = errorMessage))
    }
}

private class HandlerStrategiesResponseContext(val strategies: HandlerStrategies) : ServerResponse.Context {

    override fun messageWriters(): MutableList<HttpMessageWriter<*>> {
        return this.strategies.messageWriters()
    }

    override fun viewResolvers(): List<ViewResolver> {
        return this.strategies.viewResolvers()
    }
}