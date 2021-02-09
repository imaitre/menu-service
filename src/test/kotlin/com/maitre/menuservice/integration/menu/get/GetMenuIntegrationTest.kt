package com.maitre.menuservice.integration.menu.get

import com.maitre.menuservice.adapter.menu.out.MenuRepository
import com.maitre.menuservice.adapter.menu.out.persistence.entity.MenuEntity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.EnabledIf
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.kotlin.core.publisher.toMono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'test'}")
class GetMenuIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Mock
    private lateinit var menuRepository: MenuRepository

    @Test
    fun `Should get a menu`() {

        whenever(menuRepository.findById(any<String>())).thenReturn(menuEntity.toMono())

        webTestClient
            .get()
            .uri("/menu/MENU_af60830b-d190-43bf-afcb-f5cc2656ea25")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo("MENU_af60830b-d190-43bf-afcb-f5cc2656ea25")
            .jsonPath("$.customer_id").isEqualTo("CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62")
            .jsonPath("$.name").isEqualTo("Monday menu")
            .jsonPath("$.description").isEqualTo("some description.")
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
            .jsonPath("$.error_messages[*].description")
            .isEqualTo("No menu found. id=MENU_af60830b-d190-43bf-afcb-f5cc2656ea26")

    }

    private val menuEntity = MenuEntity(
        "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
        "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62",
        "Monday menu",
        "some description.",
        true
    )

}