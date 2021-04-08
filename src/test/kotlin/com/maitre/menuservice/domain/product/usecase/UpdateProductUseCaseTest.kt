package com.maitre.Productservice.domain.product.usecase


import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductByIdPort
import com.maitre.menuservice.domain.product.port.out.persistence.SaveProductPort
import com.maitre.menuservice.domain.product.usecase.UpdateProductUseCase
import com.maitre.menuservice.exception.ProductNotFoundException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.math.BigDecimal

class UpdateProductUseCaseTest {

    private val logger: Logger = mock()
    private val saveProductPort: SaveProductPort = mock()
    private val getProductByIdPort: GetProductByIdPort = mock()
    private val updateProductUseCase = UpdateProductUseCase(logger,saveProductPort, getProductByIdPort)

    @Test
    fun `Test Update Product Use Case - verify if ports are being called`() {
        givenSomeCorrectPorts()

        whenUseCaseIsExecuted()

        verify(getProductByIdPort, times(1)).getById(any<String>())
        verify(saveProductPort, times(1)).save(any<Product>())

    }

    @Test
    fun `Test Update Product Use Case - verify if exception is throw when group is not found`() {
        givenSomeNonExistentProduct()

        StepVerifier.create(whenUseCaseIsExecuted())
            .expectError(ProductNotFoundException::class.java)
            .verify()
    }

    private fun givenSomeCorrectPorts() {
        whenever(getProductByIdPort.getById(any<String>())).thenReturn(Mono.just(product))
        whenever(saveProductPort.save(any<Product>())).thenReturn(Mono.just(product))
    }

    private fun givenSomeNonExistentProduct() {
        whenever(getProductByIdPort.getById(any<String>())).thenReturn(Mono.empty())
        whenever(saveProductPort.save(any<Product>())).thenReturn(Mono.empty())

    }

    private fun whenUseCaseIsExecuted() =
        updateProductUseCase.execute(product,"PROD_bc4743a8-1130-4127-a057-0aacc950a1e3")

    private val product = Product(
        "PROD_bc4743a8-1130-4127-a057-0aacc950a1e3",
        "GROU_bc4743a8-1130-4127-a057-0aacc950a1e3",
        "Virada paulista",
        "Arroz, tutu de feijão, couve, bisteca, linguiça e banana.",
        BigDecimal(29.99),
        false,
        true,
        mutableListOf(
            "OPTI_bc4743a8-1130-4127-a057-0aacc950a1e3",
            "OPTI_bc4743a8-1130-4127-a057-0aacc950a1e3"
        ),
        listOf(
            "https://amp.receitadevovo.com.br/wp-content/uploads/2020/10/virado-paulista.jpg",
            "https://f.i.uol.com.br/fotografia/2018/02/05/15178692315a78d8af8c8db_1517869231_3x2_rt.jpg"
        )
    )

}