package com.maitre.menuservice.domain.optional.usecases

import com.maitre.menuservice.domain.menu.entity.Menu
import com.maitre.menuservice.domain.menu.port.out.persistence.GetMenuByIdPort
import com.maitre.menuservice.domain.optional.entity.Optional
import com.maitre.menuservice.domain.optional.port.out.persistence.SaveOptionalPort
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class UpdateOptionalUseCaseTest{

  private val logger: Logger = mock()
  private val saveOptionalPort: SaveOptionalPort = mock()
  private val getMenuByIdPort: GetMenuByIdPort = mock()
  private val updateOptionalUseCase = UpdateOptionalUseCase(logger, saveOptionalPort, getMenuByIdPort)

  @Test
  fun `Test Update Optional Use Case - verify if save port is being called`() {
    givenSomeCorrectSavePort()

    whenUseCaseIsExecuted()

    verify(getMenuByIdPort, times(1)).getById(any<String>())
    verify(saveOptionalPort, times(1)).save(any<Optional>())
  }

  @Test
  fun `Test Update Optional Use Case - verify if an event is emitted`() {
    givenSomeCorrectSavePort()

    StepVerifier.create(whenUseCaseIsExecuted())
      .expectNextCount(1)
      .verifyComplete()
  }

  private fun givenSomeCorrectSavePort() {
    whenever(getMenuByIdPort.getById(any<String>())).thenReturn(Mono.just(menu))
    whenever(saveOptionalPort.save(any<Optional>())).thenReturn(Mono.just(optional))
  }

  private fun whenUseCaseIsExecuted() =
    updateOptionalUseCase.execute(optional, "OPTI_f174849d-419e-4bed-842f-22a5bd018f3a")

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
  private val menu = Menu(
    "MENU_bc4743a8-1130-4127-a057-0aacc950a1e3",
    "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
    "Menu de segunda-feira",
    "Menu da segunda-feira pra iniciar a semana fininho.",
    true
  )

}