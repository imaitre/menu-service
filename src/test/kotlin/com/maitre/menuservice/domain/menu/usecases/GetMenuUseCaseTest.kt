package com.maitre.menuservice.domain.menu.usecases

import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenuByIdPort
import com.maitre.menuservice.exception.MenuNotFoundException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class GetMenuUseCaseTest {

    private val logger: Logger = mock()
    private val getMenuByIdPort: GetMenuByIdPort = mock()
    private val getMenuUseCase = GetMenuUseCase(logger, getMenuByIdPort)

    @Test
    fun `Test Get Menu Use Case - verify if get port is being called`() {
        givenSomeCorrectGetPort()

        whenUseCaseIsExecuted()

        verify(getMenuByIdPort, times(1)).getById(any<String>())
    }

    @Test
    fun `Test Get Menu Use Case - verify if an event is emitted`() {
        givenSomeCorrectGetPort()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectNextCount(1)
            .verifyComplete()
    }

    @Test
    fun `Test Get Menu Use Case - verify if exception is throw when menu is not found`() {
        givenSomeNonExistentMenu()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectError(MenuNotFoundException::class.java)
            .verify()

    }

    private fun givenSomeCorrectGetPort() {
        whenever(getMenuByIdPort.getById(any<String>())).thenReturn(Mono.just(menu))
    }

    private fun givenSomeNonExistentMenu() {
        whenever(getMenuByIdPort.getById(any<String>())).thenReturn(Mono.empty())
    }

    private fun whenUseCaseIsExecuted() =
        getMenuUseCase.execute("MENU_bc4743a8-1130-4127-a057-0aacc950a1e3")

    private val menu = Menu(
        "MENU_bc4743a8-1130-4127-a057-0aacc950a1e3",
        "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
        "Menu de segunda-feira",
        "Menu da segunda-feira pra iniciar a semana fininho.",
        true
    )
}