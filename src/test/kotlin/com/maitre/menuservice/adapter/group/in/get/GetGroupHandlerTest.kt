package com.maitre.menuservice.adapter.group.`in`.get

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.entity.GroupType
import com.maitre.menuservice.domain.group.usecases.GetGroupUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.kotlin.core.publisher.toMono
import reactor.test.StepVerifier

class GetGroupHandlerTest {

    private val logger: Logger = mock()
    private val getGroupUseCase: GetGroupUseCase = mock()
    private val getGroupHandler = GetGroupHandlerImpl(logger, getGroupUseCase)
    private val serverRequest: ServerRequest = mock()

    @Test
    fun `Test Get Group Handler - verify status code and header`() {

        givenSomeCorrectRequest()

        whenHandlerIsExecuted()
            .map {
                Assertions.assertEquals(
                    200, it.rawStatusCode()
                )
                Assertions.assertEquals(MediaType.APPLICATION_JSON, it.headers().contentType)
            }
            .block()
    }

    @Test
    fun `Test Get Group Handler - verify if use case is being called`() {
        givenSomeCorrectRequest()

        whenHandlerIsExecuted().block()

        verify(getGroupUseCase, times(1)).execute(any<String>())
    }

    @Test
    fun `Test Get Group Handler - verify if an event is emitted`() {
        givenSomeCorrectRequest()

        StepVerifier.create(whenHandlerIsExecuted())
            .expectNextCount(1)
            .verifyComplete()
    }

    private fun givenSomeCorrectRequest() {
        whenever(serverRequest.pathVariable("id")).thenReturn("GROU_f31415c2-87eb-41aa-8f7f-9344949cbd20")
        whenever(getGroupUseCase.execute(any<String>())).thenReturn(group)
    }

    private fun whenHandlerIsExecuted() = getGroupHandler.execute(serverRequest)

    private val group = Group(
        "GRUO_bc4743a8-1130-4127-a057-0aacc950a1e3",
        "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
        "Ice Cream",
        "delicious ice cream",
        GroupType.ICE_CREAM,
        true).toMono()
}