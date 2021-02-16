package com.maitre.menuservice.domain.group.usecases

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.entity.GroupType
import com.maitre.menuservice.domain.group.port.out.persistence.SaveGroupPort
import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenuByIdPort
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class CreateMenuUseCaseTest {

    private val logger: Logger = mock()
    private val saveGroupPort: SaveGroupPort = mock()
    private val getMenuByIdPort: GetMenuByIdPort = mock()
    private val createGroupUseCase = CreateGroupUseCase(logger, saveGroupPort, getMenuByIdPort)
    @Test
    fun `Test Create Group Use Case - verify if save port is being called`() {
        givenSomeCorrectMenuPort()
        givenSomeCorrectSavePort()

        whenUseCaseIsExecuted()

        verify(getMenuByIdPort, times(1)).getById(any<String>())
        verify(saveGroupPort, times(1)).save(any<Group>())
    }

    @Test
    fun `Test Create Group Use Case - verify if an event is emitted`() {
        givenSomeCorrectMenuPort()
        givenSomeCorrectSavePort()

        StepVerifier.create(whenUseCaseIsExecuted())
                .expectNextCount(1)
                .verifyComplete()
    }

    private fun givenSomeCorrectSavePort() {
        whenever(saveGroupPort.save(any<Group>())).thenReturn(Mono.just(group))
    }

    private fun givenSomeCorrectMenuPort() {
        whenever(getMenuByIdPort.getById(any<String>())).thenReturn(Mono.just(menu))
    }

    private fun whenUseCaseIsExecuted() = createGroupUseCase.execute(group)
    private val group = Group(
            "GROU_3145fe83-72a1-4ae8-9183-51f31e0213f9",
            "MENU_6c7bd72b-4728-4c6f-9a64-c76371deb7aa",
            "Sorvetinhos do fininho",
            "Sorvetes delicia pro fininho viva a coca-cola!",
            GroupType.ICE_CREAM,
            true
    )
    private val menu = Menu(
            "MENU_bc4743a8-1130-4127-a057-0aacc950a1e3",
            "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
            "Menu de segunda-feira",
            "Menu da segunda-feira pra iniciar a semana fininho.",
            true
    )
}