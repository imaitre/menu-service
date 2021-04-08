package com.maitre.menuservice.domain.optional.usecases

import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupsByMenuIdPort
import com.maitre.menuservice.domain.optional.entity.Optional
import com.maitre.menuservice.domain.optional.port.out.persistence.DeleteOptionalPort
import com.maitre.menuservice.domain.optional.port.out.persistence.GetOptionalByIdPort
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductsByGroupPort
import com.maitre.menuservice.domain.product.port.out.persistence.SaveProductPort
import com.maitre.menuservice.exception.OptionalNotFoundException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class DeleteOptionalUseCaseTest{
   private val logger: Logger = mock()
    private val getOptionalByIdPort: GetOptionalByIdPort = mock()
    private val deleteOptionalPort: DeleteOptionalPort = mock()
    private val getGroupsByMenuIdPort: GetGroupsByMenuIdPort = mock()
    private val getProductsByGroupPort: GetProductsByGroupPort = mock()
    private val saveProductPort: SaveProductPort = mock()

    private val deleteOptionalUseCase =
        DeleteOptionalUseCase(logger, getOptionalByIdPort, deleteOptionalPort,
            getGroupsByMenuIdPort, getProductsByGroupPort, saveProductPort)

    @Test
    fun `Test Delete Optional Use Case - verify if ports is being called`() {
        givenSomeCorrectPorts()

        whenUseCaseIsExecuted()

        verify(getOptionalByIdPort, times(1)).getById(any<String>())
        verify(deleteOptionalPort, times(1)).delete(any<String>())

    }

    @Test
    fun `Test Delete Optional Use Case - verify if no event is emitted`() {
        givenSomeCorrectPorts()
        StepVerifier.create(whenUseCaseIsExecuted())
                .expectNextCount(0)
                .verifyComplete()
    }
    @Test
    fun `Test Delete Optional Use Case - verify if exception is throw when Optional is not found`() {
        givenSomeNonExistentOptional()

        StepVerifier.create(whenUseCaseIsExecuted())
                .expectError(OptionalNotFoundException::class.java)
                .verify()
    }

    private fun givenSomeCorrectPorts() {
        whenever(getOptionalByIdPort.getById(any<String>())).thenReturn(Mono.just(optional))
        whenever(deleteOptionalPort.delete(any<String>())).thenReturn(Mono.empty())

    }

    private fun givenSomeNonExistentOptional() {
        whenever(getOptionalByIdPort.getById(any<String>())).thenReturn(Mono.empty())
        whenever(deleteOptionalPort.delete(any<String>())).thenReturn(Mono.empty())
    }

    private fun whenUseCaseIsExecuted() =
            deleteOptionalUseCase.execute("PROD_62469e0a-fee6-4f3b-8892-8c9cc4901808")

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