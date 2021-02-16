package com.maitre.menuservice.domain.menu.usecases

import com.maitre.menuservice.domain.group.port.out.persistence.DeleteGroupByMenuIdPort
import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.port.out.persistence.DeleteMenuPort
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

class DeleteMenuUseCaseTest {

    private val logger: Logger = mock()
    private val getMenuByIdPort: GetMenuByIdPort= mock()
    private val deleteMenuPort: DeleteMenuPort= mock()
    private val deleteGroupByMenuIdPort: DeleteGroupByMenuIdPort = mock()
    private val deleteMenuUseCase = DeleteMenuUseCase(logger, getMenuByIdPort, deleteMenuPort, deleteGroupByMenuIdPort)

    @Test
    fun `Test Delete Menu Use Case - verify if ports are being called`() {
        givenSomeCorrectPorts()

        whenUseCaseIsExecuted()

        verify(getMenuByIdPort, times(1)).getById(any<String>())
        verify(deleteGroupByMenuIdPort, times(1)).deleteByMenuId(any<String>())
        verify(deleteMenuPort, times(1)).delete(any<String>())

    }

    @Test
    fun `Test Delete Menu Use Case - verify if event is emitted`() {
        givenSomeCorrectPorts()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun `Test Delete Menu Use Case - verify if exception is throw when menu is not found`() {
        givenSomeNonExistentProduct()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectError(MenuNotFoundException::class.java)
            .verify()
    }

    private fun givenSomeCorrectPorts() {
        whenever(getMenuByIdPort.getById(any<String>())).thenReturn(Mono.just(menu))
        whenever(deleteMenuPort.delete(any<String>())).thenReturn(Mono.empty())
        whenever(deleteGroupByMenuIdPort.deleteByMenuId(any<String>())).thenReturn(Mono.empty())

    }

    private fun givenSomeNonExistentProduct() {
        whenever(getMenuByIdPort.getById(any<String>())).thenReturn(Mono.empty())
        whenever(deleteMenuPort.delete(any<String>())).thenReturn(Mono.empty())
        whenever(deleteGroupByMenuIdPort.deleteByMenuId(any<String>())).thenReturn(Mono.empty())
    }

    private fun whenUseCaseIsExecuted() =
        deleteMenuUseCase.execute("MENU_62469e0a-fee6-4f3b-8892-8c9cc4901808")

    private val menu = Menu(
        id = "MENU_62469e0a-fee6-4f3b-8892-8c9cc4901808",
        customerId = "CUST_62469e0a-fee6-4f3b-8892-8c9cc4901808",
        name = "Menu de segunda-feira",
        description = "Menu da segunda-feira pra iniciar a semana fininho.",
        available = true
    )
}