package com.maitre.menuservice.domain.group.usecases

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.entity.GroupType
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupByIdPort
import com.maitre.menuservice.exception.GroupNotFoundException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier

class GetGroupUseCaseTest {

    private val logger: Logger = mock()
    private val getGroupByIdPort: GetGroupByIdPort = mock()
    private val getGroupUseCase = GetGroupUseCase(logger, getGroupByIdPort)

    @Test
    fun `Test Get Group Use Case - verify if get port is being called`() {
        givenSomeCorrectGetPort()

        whenUseCaseIsExecuted()

        verify(getGroupByIdPort, times(1)).getById(any<String>())
    }

    @Test
    fun `Test Get Group Use Case - verify if an event is emitted`() {
        givenSomeCorrectGetPort()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectNextCount(1)
            .verifyComplete()
    }

    @Test
    fun `Test Get Group Use Case - verify if exception is throw when menu is not found`() {
        givenSomeNonExistentMenu()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectError(GroupNotFoundException::class.java)
            .verify()

    }

    private fun givenSomeCorrectGetPort() {
        whenever(getGroupByIdPort.getById(any<String>())).thenReturn(group)
    }

    private fun givenSomeNonExistentMenu() {
        whenever(getGroupByIdPort.getById(any<String>())).thenReturn(Mono.empty())
    }

    private fun whenUseCaseIsExecuted() = getGroupUseCase.execute("GROU_f31415c2-87eb-41aa-8f7f-9344949cbd20")

    private val group = Group(
        "GRUO_bc4743a8-1130-4127-a057-0aacc950a1e3",
        "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
        "Ice Cream",
        "delicious ice cream",
        GroupType.ICE_CREAM,
        true).toMono()
}