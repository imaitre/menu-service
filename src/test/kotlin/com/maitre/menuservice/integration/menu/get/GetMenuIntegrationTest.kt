package com.maitre.menuservice.integration.menu.get

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GetMenuIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `Should get a menu`() {
        webTestClient
            .get()
            .uri("/menu/MENU_af60830b-d190-43bf-afcb-f5cc2656ea25")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo("MENU_af60830b-d190-43bf-afcb-f5cc2656ea25")
            .jsonPath("$.customer_id").isEqualTo("CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62")
            .jsonPath("$.name").isEqualTo("Menu de segunda")
            .jsonPath("$.description").isEqualTo("abcd e rf g h hjj dddd")
            .jsonPath("$.available").isEqualTo(true)
    }

    @Test
    fun `Should respond 404 NOT_FOUND if menu is not found`() {
        webTestClient
            .get()
            .uri("/menu/MENU_af60830b-d190-43bf-afcb-f5cc2656ea26")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.error_messages").isArray
            .jsonPath("$.error_messages.length()").isEqualTo(1)
            .jsonPath("$.error_messages[*].description").isEqualTo("No menu found. id=MENU_af60830b-d190-43bf-afcb-f5cc2656ea26")

    }
}