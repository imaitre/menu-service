package com.maitre.menuservice.integration.menu.get

import com.maitre.menuservice.adapter.menu.out.MenuRepository
import com.maitre.menuservice.adapter.menu.out.persistence.entity.MenuEntity
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.EnabledIf
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'test'}")
class GetMenuByCustomerIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var menuRepository: MenuRepository

    @Test
    fun `Should get menus by customer_id`() {

        givenMenusSavedInDatabase()

        webTestClient
            .get()
            .uri("/menu?customer_id=CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62")
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueMatches("Content-Type", "text/event-stream;charset=UTF-8")
            .expectBody()

    }

    @Test
    fun `Should respond 404 NOT_FOUND if menu is not found`() {
        webTestClient
            .get()
            .uri("/menu?customer_id=123a")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.error_messages").isArray
            .jsonPath("$.error_messages.length()").isEqualTo(1)
            .jsonPath("$.error_messages[*].description").isEqualTo("Menu not found. id=123a")
    }

    private fun givenMenusSavedInDatabase(){
        val menuEntity1 = MenuEntity(
            "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
            "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62",
            "Monday menu",
            "some description.",
            true
        )

        val menuEntity2 = MenuEntity(
            "MENU_af60830b-d190-43bf-afcb-f5cc2656ea26",
            "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62",
            "Monday menu",
            "some description.",
            true
        )

        menuRepository.save(menuEntity1).block()
        menuRepository.save(menuEntity2).block()
    }


}