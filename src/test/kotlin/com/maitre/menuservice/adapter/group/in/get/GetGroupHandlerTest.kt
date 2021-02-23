package com.maitre.menuservice.adapter.group.`in`.get

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.entity.GroupType
import com.maitre.menuservice.domain.group.usecases.GetGroupsByMenuIdUseCase
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.*

class GetGroupHandlerTest{

    private val logger: Logger = mock()
    private val getGroupsByMenuIdUseCase: GetGroupsByMenuIdUseCase = mock()
    private val getGroupHandler = GetGroupHandlerImpl(logger, getGroupsByMenuIdUseCase)
    private val serverRequest: ServerRequest = mock()


    @Test
    fun `Test Get Group Handler - verify status code and header`() {
        givenSomeCorrectRequest()

        whenHandlerIsExecuted()
                .map {
                    Assertions.assertEquals(
                            200, it.rawStatusCode()
                    )
                    Assertions.assertEquals(MediaType.TEXT_EVENT_STREAM, it.headers().contentType)
                }
                .block()
    }

    @Test
    fun `Test Get Group Handler - verify if use case is being called`() {
        givenSomeCorrectRequest()

        whenHandlerIsExecuted().block()

        verify(getGroupsByMenuIdUseCase, times(1)).execute(any<String>())
    }

    @Test
    fun `Test Get Group Handler - verify if an event is emitted`() {
        givenSomeCorrectRequest()

        StepVerifier.create(whenHandlerIsExecuted())
                .expectNextCount(1)
                .verifyComplete()
    }


    private fun whenHandlerIsExecuted() = getGroupHandler.execute(serverRequest)

    private fun givenSomeCorrectRequest() {
        whenever(serverRequest.queryParam("menu_id")).thenReturn(Optional.of("MENU_af60830b-d190-43bf-afcb-f5cc2656ea25"))
        whenever(getGroupsByMenuIdUseCase.execute(any<String>())).thenReturn(Flux.just(group))
    }

    private val group = Group(
            "GRUO_bc4743a8-1130-4127-a057-0aacc950a1e3",
            "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
            "Ice Cream",
            "delicious ice cream",
            GroupType.ICE_CREAM,
            true)
}