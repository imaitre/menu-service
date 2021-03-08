package com.maitre.menuservice.domain.optional.usecases


import com.maitre.menuservice.domain.optional.entity.Optional
import com.maitre.menuservice.domain.optional.port.out.persistence.GetOptionalByIdPort
import com.maitre.menuservice.exception.OptionalNotFoundException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import reactor.test.StepVerifier

class GetOptionalByIdUseCaseTest {

    private val logger: Logger = mock()
    private val getOptionalByIdPort: GetOptionalByIdPort = mock()
    private val getOptionalUseCase = GetOptionalByIdUseCase(logger, getOptionalByIdPort)

    @Test
    fun `Test Get Optional Use Case - verify if get port is being called`() {
        givenSomeCorrectGetPort()

        whenUseCaseIsExecuted()

        verify(getOptionalByIdPort, times(1)).getById(any<String>())
    }

    @Test
    fun `Test Get Optional Use Case - verify if an event is emitted`() {
        givenSomeCorrectGetPort()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectNextCount(1)
            .verifyComplete()
    }

    @Test
    fun `Test Get Optional Use Case - verify if exception is throw when menu is not found`() {
        givenSomeNonExistentMenu()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectError(OptionalNotFoundException::class.java)
            .verify()

    }

    private fun givenSomeCorrectGetPort() {
        whenever(getOptionalByIdPort.getById(any<String>())).thenReturn(optional)
    }

    private fun givenSomeNonExistentMenu() {
        whenever(getOptionalByIdPort.getById(any<String>())).thenReturn(Mono.empty())
    }

    private fun whenUseCaseIsExecuted() = getOptionalUseCase.execute("GROU_f31415c2-87eb-41aa-8f7f-9344949cbd20")

    private val optional = Optional(
        "OPTI_f174849d-419e-4bed-842f-22a5bd018f3a",
        "MENU_f174849d-419e-4bed-842f-22a5bd018f3a",
        "Cobertura extra",
        "Escolha duas coberturas extra no seu sorvetinho",
        true,
        0,
        1,
        true,
        mutableListOf(
            Optional.AddOns(
                "'12",
                "Caramelo",
                2.0,
                "Caramelo fino",
                true,
            )
        )
    ).toMono()
}