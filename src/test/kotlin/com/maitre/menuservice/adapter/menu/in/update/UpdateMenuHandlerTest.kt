package com.maitre.menuservice.adapter.menu.`in`.update

import com.maitre.menuservice.adapter.menu.`in`.dto.MenuRequestDTO
import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.usecases.UpdateMenuUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier

class UpdateMenuHandlerTest {

    private val logger: Logger = mock()
    private val updateMenuUseCase: UpdateMenuUseCase = mock()
    private val updateMenuHandler = UpdateMenuHandlerImpl(logger, updateMenuUseCase)
    private val serverRequest: ServerRequest = mock()


    @Test
    fun `Test Update Menu Handler - verify status code and header`() {

        givenSomeCorrectRequest()

        whenHandlerIsExecuted()
            .map {
                Assertions.assertEquals(
                    200, it.rawStatusCode()
                )
                Assertions.assertEquals(MediaType.APPLICATION_JSON, it.headers().contentType)
            }
            .block()
    }

    @Test
    fun `Test Update Menu Handler - verify if use case is being called`() {
        givenSomeCorrectRequest()

        whenHandlerIsExecuted().block()

        verify(updateMenuUseCase, times(1)).execute(any<Menu>(), any<String>())
    }

    @Test
    fun `Test Update Menu Handler - verify if an event is emitted`() {
        givenSomeCorrectRequest()

        StepVerifier.create(whenHandlerIsExecuted())
            .expectNextCount(1)
            .verifyComplete()
    }

    private fun whenHandlerIsExecuted() = updateMenuHandler.execute(serverRequest)

    private fun givenSomeCorrectRequest() {
        whenever(serverRequest.pathVariable("id")).thenReturn("MENU_bc4743a8-1130-4127-a057-0aacc950a1e3")
        whenever(serverRequest.bodyToMono(MenuRequestDTO::class.java)).thenReturn(menuRequestDTO)
        whenever(updateMenuUseCase.execute(any<Menu>(), any<String>())).thenReturn(menu)
    }

    private val menuRequestDTO = MenuRequestDTO(
        "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
        "Menu de segunda-feira",
        "Menu da segunda-feira pra iniciar a semana fininho.",
        true).toMono()

    private val menu = Menu(
        "MENU_bc4743a8-1130-4127-a057-0aacc950a1e3",
        "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
        "Menu de segunda-feira",
        "Menu da segunda-feira pra iniciar a semana fininho.",
        true).toMono()
}