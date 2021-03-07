package com.maitre.menuservice.domain.optional.usecases

import com.maitre.menuservice.domain.optional.entity.Optional
import com.maitre.menuservice.domain.optional.port.out.persistence.GetOptionalsByMenuIdPort
import com.maitre.menuservice.exception.OptionalNotFoundException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

internal class GetOptionalsByMenuIdUseCaseTest{

    private val logger: Logger = mock()
    private val getOptionalsByMenuIdPort: GetOptionalsByMenuIdPort = mock()
    private val getOptionalsUseCase = GetOptionalsByMenuIdUseCase(logger, getOptionalsByMenuIdPort)


    @Test
    fun `Test Get Optional Use Case - verify if get port is being called`() {
        givenSomeCorrectGetPort()

        whenUseCaseIsExecuted()

        verify(getOptionalsByMenuIdPort, times(1)).getByMenuId(any<String>())
    }

    @Test
    fun `Test Get Optional Use Case - verify if an event is emitted`() {
        givenSomeCorrectGetPort()

        StepVerifier.create(whenUseCaseIsExecuted())
                .expectNextCount(1)
                .verifyComplete()
    }

    @Test
    fun `Test Get Optional Use Case - verify if exception is throw when group is not found`() {
        givenSomeNonExistentGroup()

        StepVerifier.create(whenUseCaseIsExecuted())
                .expectError(OptionalNotFoundException::class.java)
                .verify()
    }

    private fun givenSomeCorrectGetPort() {
        whenever(getOptionalsByMenuIdPort.getByMenuId(any<String>())).thenReturn(Flux.just(optional))
    }

    private fun givenSomeNonExistentGroup() {
        whenever(getOptionalsByMenuIdPort.getByMenuId(any<String>())).thenReturn(Flux.empty())
    }

    private fun whenUseCaseIsExecuted() =
        getOptionalsUseCase.execute("MENU_bc4743a8-1130-4127-a057-0aacc950a1e3")

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
  )
}