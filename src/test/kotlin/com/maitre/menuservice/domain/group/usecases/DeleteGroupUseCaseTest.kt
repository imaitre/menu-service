package com.maitre.menuservice.domain.group.usecases

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.entity.GroupType
import com.maitre.menuservice.domain.group.port.out.persistence.DeleteGroupPort
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupByIdPort
import com.maitre.menuservice.exception.GroupNotFoundException
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class DeleteGroupUseCaseTest{
   private val logger: Logger = mock()
    private val getGroupByIdPort: GetGroupByIdPort = mock()
    private val deleteGroupPort: DeleteGroupPort = mock()
    private val deleteGroupUseCase =  DeleteGroupUseCase(logger,getGroupByIdPort, deleteGroupPort)

    @Test
    fun `Test Delete Group Use Case - verify if ports is being called`() {
        givenSomeCorrectPorts()

        whenUseCaseIsExecuted()

        verify(getGroupByIdPort, times(1)).getById(any<String>())
        verify(deleteGroupPort, times(1)).delete(any<String>())
    }

    @Test
    fun `Test Delete Group Use Case - verify if no event is emitted`() {
        givenSomeCorrectPorts()
        StepVerifier.create(whenUseCaseIsExecuted())
                .expectNextCount(0)
                .verifyComplete()
    }
    @Test
    fun `Test Delete Group Use Case - verify if exception is throw when group is not found`() {
        givenSomeNonExistentGroup()

        StepVerifier.create(whenUseCaseIsExecuted())
                .expectError(GroupNotFoundException::class.java)
                .verify()
    }

    private fun givenSomeCorrectPorts() {
        whenever(getGroupByIdPort.getById(any<String>())).thenReturn(Mono.just(group))
        whenever(deleteGroupPort.delete(any<String>())).thenReturn(Mono.empty())
    }

    private fun givenSomeNonExistentGroup() {
        whenever(getGroupByIdPort.getById(any<String>())).thenReturn(Mono.empty())
        whenever(deleteGroupPort.delete(any<String>())).thenReturn(Mono.empty())
    }

    private fun whenUseCaseIsExecuted() =
            deleteGroupUseCase.execute("PROD_62469e0a-fee6-4f3b-8892-8c9cc4901808")

    private val group = Group(
            "GROU_3145fe83-72a1-4ae8-9183-51f31e0213f9",
            "MENU_6c7bd72b-4728-4c6f-9a64-c76371deb7aa",
            "Sorvetinhos do fininho",
            "Sorvetes delicia pro fininho viva a coca-cola!",
            GroupType.ICE_CREAM,
            true
    )

}