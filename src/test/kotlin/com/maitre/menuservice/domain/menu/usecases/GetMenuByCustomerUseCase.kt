package com.maitre.menuservice.domain.menu.usecases

import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenusByCustomerPort
import com.maitre.menuservice.exception.MenuNotFoundException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class GetMenuByCustomerUseCase {

    private val logger: Logger = mock()
    private val getMenusByCustomerPort: GetMenusByCustomerPort = mock()
    private val getMenusByCustomerUseCase = GetMenusByCustomerUseCase(logger, getMenusByCustomerPort)

    @Test
    fun `Test Get Menus by Customer Use Case - verify if get port is being called`() {
        givenSomeCorrectPort()

        whenUseCaseIsExecuted()

        verify(getMenusByCustomerPort, times(1)).getByCustomerId(any<String>())
    }

    @Test
    fun `Test Get Menus by Customer Use Case - verify if get ports is being called`() {
        givenSomeCorrectPort()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectNextCount(2)
            .verifyComplete()

    }

    @Test
    fun `Test Get Products by group Use Case  - verify if exception is throw when product is not found`() {
        givenSomeNonExistentMenu()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectError(MenuNotFoundException::class.java)
            .verify()
    }

    private fun givenSomeCorrectPort() {
        whenever(getMenusByCustomerPort.getByCustomerId(any<String>())).thenReturn(Flux.just(menu1, menu2))
    }

    private fun whenUseCaseIsExecuted() = getMenusByCustomerUseCase.execute("CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63")

    private fun givenSomeNonExistentMenu() {
        whenever(getMenusByCustomerPort.getByCustomerId(any<String>())).thenReturn(Flux.empty())
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