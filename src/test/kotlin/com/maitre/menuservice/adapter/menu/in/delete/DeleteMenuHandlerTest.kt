package com.maitre.menuservice.adapter.menu.`in`.delete

import com.maitre.menuservice.adapter.menu.`in`.create.DeleteMenuHandlerImpl
import com.maitre.menuservice.domain.menu.usecases.DeleteMenuUseCase
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class DeleteMenuHandlerTest {

    private val logger: Logger = mock()
    private val deleteMenuUseCase: DeleteMenuUseCase = mock()
    private val deleteMenuHandler = DeleteMenuHandlerImpl(logger, deleteMenuUseCase)
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

        verify(deleteMenuUseCase, times(1)).execute(any<String>())
    }

    @Test
    fun `Test Delete Menu Handler - verify if an event is emitted`() {
        givenSomeCorrectRequest()

        StepVerifier.create(whenHandlerIsExecuted())
            .expectNextCount(1)
            .verifyComplete()
    }


    private fun whenHandlerIsExecuted() = deleteMenuHandler.execute(serverRequest)

    private fun givenSomeCorrectRequest() {
        whenever(serverRequest.pathVariable("id")).thenReturn("MENU_bc4743a8-1130-4127-a057-0aacc950a1e3")
        whenever(deleteMenuUseCase.execute(any<String>())).thenReturn(Mono.empty())
    }
}