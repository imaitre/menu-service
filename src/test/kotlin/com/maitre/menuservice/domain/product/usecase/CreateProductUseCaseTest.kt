package com.maitre.menuservice.domain.product.usecase

import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.SaveProductPort
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.math.BigDecimal

class CreateProductUseCaseTest{

    private val logger: Logger = mock()
    private val saveProductPort: SaveProductPort = mock()
    private val createProductUseCase = CreateProductUseCase(logger, saveProductPort)

    @Test
    fun `Test Create Product Use Case - verify if save port is being called`() {
        givenSomeCorrectSavePort()

        whenUseCaseIsExecuted()

        verify(saveProductPort, times(1)).save(any<Product>())
    }

    @Test
    fun `Test Create Product Use Case - verify if an event is emitted`() {
        givenSomeCorrectSavePort()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectNextCount(1)
            .verifyComplete()
    }


    private fun whenUseCaseIsExecuted() = createProductUseCase.execute(product)


    private fun givenSomeCorrectSavePort() {
        whenever(saveProductPort.save(any<Product>())).thenReturn(Mono.just(product))
    }


    private val product = Product(
         "PRO_bc4743a8-1130-4127-a057-0aacc950a1e3",
        "GRO_bc4743a8-1130-4127-a057-0aacc950a1e3",
     "Virada paulista",
    "Arroz, tutu de feijão, couve, bisteca, linguiça e banana.",
        BigDecimal(29.99),
     false,
    true,
        mapOf("Bisteca extra" to BigDecimal(4.99),
            "Linguiça extra" to BigDecimal(2.99),
            "Ovo frito" to BigDecimal(1.99)
        ),
        listOf("https://amp.receitadevovo.com.br/wp-content/uploads/2020/10/virado-paulista.jpg",
            "https://f.i.uol.com.br/fotografia/2018/02/05/15178692315a78d8af8c8db_1517869231_3x2_rt.jpg")
    )




}