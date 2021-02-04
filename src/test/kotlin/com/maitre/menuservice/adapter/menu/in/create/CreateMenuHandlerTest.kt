package com.maitre.menuservice.adapter.menu.`in`.create

import com.maitre.menuservice.adapter.menu.`in`.dto.MenuRequestDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CreateMenuHandlerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `Should create a menu`() {
        val menuRequestDTO = MenuRequestDTO(
            "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62",
            "Menu de segunda-feira",
            "menu da sengundinha pra inicia a semana fininho.",
            true
        )

        webTestClient
            .post()
            .uri("/menu")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(menuRequestDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").exists()
            .jsonPath("$.customer_id").isEqualTo("CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62")
            .jsonPath("$.name").isEqualTo("Menu de segunda-feira")
            .jsonPath("$.description").isEqualTo("menu da sengundinha pra inicia a semana fininho.")
            .jsonPath("$.available").isEqualTo(true)

    }
}