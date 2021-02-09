package com.maitre.menuservice.adapter.product.delete

import com.maitre.menuservice.adapter.product.`in`.delete.DeleteProductHandlerImpl
import com.maitre.menuservice.domain.product.usecase.DeleteProductUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class DeleteProductHandlerTest {

    private val logger: Logger = mock()
    private val deleteProductUseCase: DeleteProductUseCase = mock()
    private val deleteMenuHandler = DeleteProductHandlerImpl(logger, deleteProductUseCase)
    private val serverRequest: ServerRequest = mock()

    @Test
    fun `Test Delete Menu Handler - verify status code and header`() {
        givenSomeCorrectRequest()

        whenHandlerIsExecuted()
            .map {
                Assertions.assertEquals(
                    204, it.rawStatusCode()
                )
            }
            .block()

    }

    @Test
    fun `Test Delete Menu Handler - verify if use case is being called`() {
        givenSomeCorrectRequest()

        whenHandlerIsExecuted().block()

        verify(deleteProductUseCase, times(1)).execute(any<String>())
    }

    @Test
    fun `Test Delete Menu Handler - verify if an event is emitted`() {
        givenSomeCorrectRequest()

        StepVerifier.create(whenHandlerIsExecuted())
            .expectNextCount(1)
            .verifyComplete()
    }

    private fun givenSomeCorrectRequest() {
        whenever(serverRequest.pathVariable("id")).thenReturn("MENU_bc4743a8-1130-4127-a057-0aacc950a1e3")
        whenever(deleteProductUseCase.execute(any<String>())).thenReturn(Mono.empty())
    }

    private fun whenHandlerIsExecuted() =
        deleteMenuHandler.execute(serverRequest)
}