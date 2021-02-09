package com.maitre.menuservice.adapter.menu.`in`.create

import com.maitre.menuservice.adapter.menu.`in`.dto.MenuRequestDTO
import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.usecases.CreateMenuUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier

class CreateMenuHandlerTest {

    private val logger: Logger = mock()
    private val createMenuUseCase: CreateMenuUseCase = mock()
    private val createMenuHandler = CreateMenuHandlerImpl(logger, createMenuUseCase)
    private val serverRequest: ServerRequest = mock()

    @Test
    fun `Test Create Menu Handler - verify status code and header`() {

        givenSomeCorrectRequest()

        whenHandlerIsExecuted()
            .map {
                assertEquals(
                    201, it.rawStatusCode()
                )
                assertEquals(MediaType.APPLICATION_JSON, it.headers().contentType)
            }
            .block()

    }

    @Test
    fun `Test Create Menu Handler - verify if use case is being called`() {
        givenSomeCorrectRequest()

        whenHandlerIsExecuted().block()

        verify(createMenuUseCase, times(1)).execute(any<Menu>())
    }

    @Test
    fun `Test Create Menu Handler - verify if an event is emitted`() {
        givenSomeCorrectRequest()

        StepVerifier.create(whenHandlerIsExecuted())
            .expectNextCount(1)
            .verifyComplete()

    }

    private fun givenSomeCorrectRequest() {
        whenever(serverRequest.bodyToMono(MenuRequestDTO::class.java)).thenReturn(menuRequestDTO)
        whenever(createMenuUseCase.execute(any<Menu>())).thenReturn(menu)
    }

    private fun whenHandlerIsExecuted() =
        createMenuHandler.execute(serverRequest)

    private val menuRequestDTO = MenuRequestDTO(
        "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
        "Menu de segunda-feira",
        "Menu da segunda-feira pra iniciar a semana fininho.",
        true
    ).toMono()

    private val menu = Menu(
        "MENU_bc4743a8-1130-4127-a057-0aacc950a1e3",
        "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
        "Menu de segunda-feira",
        "Menu da segunda-feira pra iniciar a semana fininho.",
        true
    ).toMono()
}