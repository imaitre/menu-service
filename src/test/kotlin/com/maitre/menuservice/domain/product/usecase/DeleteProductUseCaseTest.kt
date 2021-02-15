package com.maitre.menuservice.domain.product.usecase

import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.DeleteProductPort
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductPort
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.math.BigDecimal

class DeleteProductUseCaseTest{
    private val logger: Logger = mock()
    private val getProductPort: GetProductPort = mock()
    private val deleteProductPort: DeleteProductPort = mock()

    private val deleteProductUseCase = DeleteProductUseCase(logger, getProductPort, deleteProductPort)


    @Test
    fun `Test Delete Product Use Case - vverify if get ports is being called`() {
        givenSomeCorrectPorts()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectNextCount(0)
            .verifyComplete()

        verify(getProductPort, times(1)).get(any<String>())
        verify(deleteProductPort, times(1)).delete(any<String>())
    }

    @Test
    fun `Test Delete Product Use Case - verify if no event is emitted`() {
        givenSomeCorrectPorts()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectNextCount(0)
            .verifyComplete()
    }

    private fun whenUseCaseIsExecuted() = deleteProductUseCase.execute("PROD_62469e0a-fee6-4f3b-8892-8c9cc4901808")


    private fun givenSomeCorrectPorts() {
        whenever(getProductPort.get(any<String>())).thenReturn(Mono.just(product))
        whenever(deleteProductPort.delete(any<String>())).thenReturn(Mono.empty())
    }


    private val product = Product(
        id = "PROD_62469e0a-fee6-4f3b-8892-8c9cc4901808",
        groupId = "GROU_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b63",
        name = "Gnocchi",
        description = "gnocchi pra quem quer ficar fininho.",
        amount = BigDecimal.valueOf(49.99),
        adultsOnly = false,
        available = true,
        addons = null,
        imageUrls = null
    )

}