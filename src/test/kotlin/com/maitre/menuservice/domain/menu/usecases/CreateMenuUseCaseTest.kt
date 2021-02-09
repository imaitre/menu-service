package com.maitre.menuservice.domain.menu.usecases

import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.port.out.persistence.SaveMenuPort
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class CreateMenuUseCaseTest {

    private val logger: Logger = mock()
    private val saveMenuPort: SaveMenuPort = mock()
    private val createMenuUseCase = CreateMenuUseCase(logger, saveMenuPort)

    @Test
    fun `Test Create Menu Use Case - verify if save port is being called`() {
        givenSomeCorrectSavePort()

        whenUseCaseIsExecuted()

        verify(saveMenuPort, times(1)).save(any<Menu>())
    }

    @Test
    fun `Test Create Menu Use Case - verify if an event is emitted`() {
        givenSomeCorrectSavePort()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectNextCount(1)
            .verifyComplete()
    }

    private fun givenSomeCorrectSavePort() {
        whenever(saveMenuPort.save(any<Menu>())).thenReturn(Mono.just(menu))
    }

    private fun whenUseCaseIsExecuted() =
        createMenuUseCase.execute(menu)

    private val menu = Menu(
        "MENU_bc4743a8-1130-4127-a057-0aacc950a1e3",
        "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
        "Menu de segunda-feira",
        "Menu da segunda-feira pra iniciar a semana fininho.",
        true
    )
}