package com.maitre.menuservice.adapter.group.`in`.create

import com.maitre.menuservice.adapter.group.`in`.dto.GroupRequestDTO
import com.maitre.menuservice.adapter.menu.`in`.create.CreateGroupHandlerImpl
import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.entity.GroupType
import com.maitre.menuservice.domain.group.usecases.CreateGroupUseCase
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
 class CreateGroupHandlerTest {

    private val logger: Logger = mock()
    private val createGroupUseCase: CreateGroupUseCase = mock()
    private val createGroupHandler = CreateGroupHandlerImpl(logger, createGroupUseCase)
    private val serverRequest: ServerRequest = mock()

    @Test
    fun `Test Create Group Handler - verify status code and header`() {

        givenSomeCorrectRequest()

        whenHandlerIsExecuted()
                .map {
                    Assertions.assertEquals(
                            201, it.rawStatusCode()
                    )
                    Assertions.assertEquals(MediaType.APPLICATION_JSON, it.headers().contentType)
                }
                .block()

    }
    @Test
    fun `Test Create Group Handler - verify if use case is being called`() {
        givenSomeCorrectRequest()

        whenHandlerIsExecuted().block()

        verify(createGroupUseCase, times(1)).execute(any<Group>())
    }

    @Test
    fun `Test Create Group Handler - verify if an event is emitted`() {
        givenSomeCorrectRequest()

        StepVerifier.create(whenHandlerIsExecuted())
                .expectNextCount(1)
                .verifyComplete()

    }
    private fun whenHandlerIsExecuted() = createGroupHandler.execute(serverRequest)

    private fun givenSomeCorrectRequest() {
        whenever(serverRequest.bodyToMono(GroupRequestDTO::class.java)).thenReturn(groupRequestDTO)
        whenever(createGroupUseCase.execute(any<Group>())).thenReturn(group)
    }
    private val groupRequestDTO = GroupRequestDTO(
            "MENU_bc4743a8-1130-4127-a057-0aacc950a1e3",
            "Ice Cream",
            "delicious ice cream",
            GroupType.ICE_CREAM,
            true).toMono()

    private val group = Group(
            "GRUO_bc4743a8-1130-4127-a057-0aacc950a1e3",
            "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
            "Ice Cream",
            "delicious ice cream",
            GroupType.ICE_CREAM,
            true).toMono()
}
