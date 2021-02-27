package com.maitre.menuservice.adapter.menu.`in`.get

import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.usecases.GetMenusByCustomerUseCase
import com.maitre.menuservice.utils.Constants.CUSTOMER_ID
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import java.util.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class GetMenuByCustomerHandlerTest {

    private val logger: Logger = mock()
    private val getMenusByCustomerUseCase: GetMenusByCustomerUseCase = mock()
    private val getMenusByCustomerHandler = GetMenusByCustomerHandlerImpl(logger, getMenusByCustomerUseCase)
    private val serverRequest: ServerRequest = mock()

    @Test
    fun `Test Get Menu By Customer Handler - verify status code and header`() {
        givenSomeCorrectRequest()

        whenHandlerIsExecuted()
            .map {
                Assertions.assertEquals(
                    200, it.rawStatusCode()
                )
                Assertions.assertEquals(MediaType.TEXT_EVENT_STREAM, it.headers().contentType)
            }
            .block()
    }

    @Test
    fun `Test Get Menu By Customer Handler - verify if use case is being called`() {
        givenSomeCorrectRequest()

        whenHandlerIsExecuted().block()

        verify(getMenusByCustomerUseCase, times(1)).execute(any<String>())
    }

    @Test
    fun `Test Get Menu By Customer Handle - verify if an event is emitted`() {
        givenSomeCorrectRequest()

        StepVerifier.create(whenHandlerIsExecuted())
            .expectNextCount(1)
            .verifyComplete()
    }

    private fun whenHandlerIsExecuted() = getMenusByCustomerHandler.execute(serverRequest)

    private fun givenSomeCorrectRequest() {
        whenever(serverRequest.queryParam(CUSTOMER_ID)).thenReturn(Optional.of("CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63"))
        whenever(getMenusByCustomerUseCase.execute(any<String>())).thenReturn(Flux.just(menu1, menu2))
    }

    private val menu1 = Menu(
        "MENU_bc4743a8-1130-4127-a057-0aacc950a1e3",
        "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
        "Menu de segunda-feira",
        "Menu da segunda-feira pra iniciar a semana fininho.",
        true
    )

    private val menu2 = Menu(
        "MENU_bc4743a8-1130-4127-a057-0aacc950a1e4",
        "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
        "Menu de segunda-feira",
        "Menu da segunda-feira pra iniciar a semana fininho.",
        true
    )
}