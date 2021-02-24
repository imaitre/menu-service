package com.maitre.menuservice.domain.menu.usecases

import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenuPort
import com.maitre.menuservice.domain.menu.port.out.persistence.SaveMenuPort
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

class UpdateMenuUseCaseTest {

    private val logger: Logger = mock()
    private val saveMenuPort: SaveMenuPort = mock()
    private val getMenuPort: GetMenuPort = mock()
    private val updateMenuUseCase = UpdateMenuUseCase(logger,saveMenuPort, getMenuPort)

    @Test
    fun `Test Update Menu Use Case - verify if ports are being called`() {
        givenSomeCorrectPorts()

        whenUseCaseIsExecuted()

        verify(getMenuPort, times(1)).get(any<String>())
        verify(saveMenuPort, times(1)).save(any<Menu>())

    }

    @Test
    fun `Test Update Menu Use Case - verify if exception is throw when group is not found`() {
        givenSomeNonExistentMenu()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectError(MenuNotFoundException::class.java)
            .verify()
    }

    private fun givenSomeCorrectPorts() {
        whenever(getMenuPort.get(any<String>())).thenReturn(Mono.just(menu))
        whenever(saveMenuPort.save(any<Menu>())).thenReturn(Mono.just(menu))
    }

    private fun givenSomeNonExistentMenu() {
        whenever(getMenuPort.get(any<String>())).thenReturn(Mono.empty())
        whenever(saveMenuPort.save(any<Menu>())).thenReturn(Mono.empty())

    }

    private fun whenUseCaseIsExecuted() =
        updateMenuUseCase.execute(menu,"GROU_3145fe83-72a1-4ae8-9183-51f31e0213f9")

    private val menu = Menu(
        "MENU_bc4743a8-1130-4127-a057-0aacc950a1e3",
        "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
        "Menu de segunda-feira",
        "Menu da segunda-feira pra iniciar a semana fininho.",
        true
    )

}