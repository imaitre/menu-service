package com.maitre.menuservice.domain.group.usecases

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.entity.GroupType
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupsByMenuIdPort
import com.maitre.menuservice.exception.GroupNotFoundException
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

internal class GetGroupsByMenuIdUseCaseTest{

    private val logger: Logger = mock()
    private val getGroupsByMenuIdPort: GetGroupsByMenuIdPort = mock()
    private val getMenuUseCase = GetGroupsByMenuIdUseCase(logger, getGroupsByMenuIdPort)


    @Test
    fun `Test Get Group Use Case - verify if get port is being called`() {
        givenSomeCorrectGetPort()

        whenUseCaseIsExecuted()

        verify(getGroupsByMenuIdPort, times(1)).getByMenuId(any<String>())
    }

    @Test
    fun `Test Get Group Use Case - verify if an event is emitted`() {
        givenSomeCorrectGetPort()

        StepVerifier.create(whenUseCaseIsExecuted())
                .expectNextCount(1)
                .verifyComplete()
    }

    @Test
    fun `Test Get Group Use Case - verify if exception is throw when group is not found`() {
        givenSomeNonExistentGroup()

        StepVerifier.create(whenUseCaseIsExecuted())
                .expectError(GroupNotFoundException::class.java)
                .verify()

    }
    private fun givenSomeCorrectGetPort() {
        whenever(getGroupsByMenuIdPort.getByMenuId(any<String>())).thenReturn(Flux.just(group))
    }

    private fun givenSomeNonExistentGroup() {
        whenever(getGroupsByMenuIdPort.getByMenuId(any<String>())).thenReturn(Flux.empty())
    }

    private fun whenUseCaseIsExecuted() =
            getMenuUseCase.execute("MENU_bc4743a8-1130-4127-a057-0aacc950a1e3")

    private val group = Group(
            "GROU_3145fe83-72a1-4ae8-9183-51f31e0213f9",
            "MENU_6c7bd72b-4728-4c6f-9a64-c76371deb7aa",
            "Sorvetinhos do fininho",
            "Sorvetes delicia pro fininho viva a coca-cola!",
            GroupType.ICE_CREAM,
            true
    )
}