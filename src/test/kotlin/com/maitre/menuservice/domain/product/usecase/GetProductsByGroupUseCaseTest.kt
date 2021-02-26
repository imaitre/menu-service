package com.maitre.menuservice.domain.product.usecase

import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductsByGroupPort
import com.maitre.menuservice.exception.ProductNotFoundException
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.math.BigDecimal

class GetProductsByGroupUseCaseTest{
    private val logger: Logger = mock()
    private val getProductsByGroupPort: GetProductsByGroupPort = mock()


    private val getProductsByGroupUseCase = GetProductsByGroupUseCase(logger, getProductsByGroupPort)

    @Test
    fun `Test Get Products by group Use Case - verify if get port is being called`() {
        givenSomeCorrectPort()

        whenUseCaseIsExecuted()

        verify(getProductsByGroupPort, times(1)).getByGroup(any<String>())
    }


    @Test
    fun `Test Get Products by group Use Case - verify if get ports is being called`() {
        givenSomeCorrectPort()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectNextCount(1)
            .verifyComplete()

    }

    @Test
    fun `Test Get Products by group Use Case  - verify if exception is throw when product is not found`() {
        givenSomeNonExistentProduct()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectError(ProductNotFoundException::class.java)
            .verify()
    }


    private fun whenUseCaseIsExecuted() = getProductsByGroupUseCase.execute("PROD_62469e0a-fee6-4f3b-8892-8c9cc4901808")

    private fun givenSomeCorrectPort() {
        whenever(getProductsByGroupPort.getByGroup(any<String>())).thenReturn(Flux.just(product))
    }
    private fun givenSomeNonExistentProduct() {
        whenever(getProductsByGroupPort.getByGroup(any<String>())).thenReturn(Flux.empty())
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