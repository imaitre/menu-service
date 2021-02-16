package com.maitre.menuservice.domain.group.usecases

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.entity.GroupType
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupByIdPort
import com.maitre.menuservice.domain.group.port.out.persistence.SaveGroupPort
import com.maitre.menuservice.exception.GroupNotFoundException
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class UpdateGroupUseCaseTest{

    private val logger: Logger = mock()
    private val saveGroupPort: SaveGroupPort = mock()
    private val getGroupByIdPort: GetGroupByIdPort = mock()
    private val updateGroupUseCase = UpdateGroupUseCase(logger,saveGroupPort, getGroupByIdPort)

    @Test
    fun `Test Update Group Use Case - verify if ports are being called`() {
        givenSomeCorrectPorts()

        whenUseCaseIsExecuted()

        verify(getGroupByIdPort, times(1)).getById(any<String>())
        verify(saveGroupPort, times(1)).save(any<Group>())

    }

    @Test
    fun `Test Update Group Use Case - verify if exception is throw when group is not found`() {
        givenSomeNonExistentGroup()

        StepVerifier.create(whenUseCaseIsExecuted())
                .expectError(GroupNotFoundException::class.java)
                .verify()
    }

    private fun givenSomeCorrectPorts() {
        whenever(getGroupByIdPort.getById(any<String>())).thenReturn(Mono.just(group))
        whenever(saveGroupPort.save(any<Group>())).thenReturn(Mono.just(group))
    }

    private fun givenSomeNonExistentGroup() {
        whenever(getGroupByIdPort.getById(any<String>())).thenReturn(Mono.empty())
        whenever(saveGroupPort.save(any<Group>())).thenReturn(Mono.empty())

    }

    private fun whenUseCaseIsExecuted() =
            updateGroupUseCase.execute(group,"GROU_3145fe83-72a1-4ae8-9183-51f31e0213f9")

    private val group = Group(
            "GROU_3145fe83-72a1-4ae8-9183-51f31e0213f9",
            "MENU_6c7bd72b-4728-4c6f-9a64-c76371deb7aa",
            "Sorvetinhos do fininho",
            "Sorvetes delicia pro fininho viva a coca-cola!",
            GroupType.ICE_CREAM,
            true
    )
}