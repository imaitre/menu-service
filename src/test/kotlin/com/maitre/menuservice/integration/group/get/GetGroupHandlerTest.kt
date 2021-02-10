package com.maitre.menuservice.integration.group.get

import com.maitre.menuservice.domain.group.entity.GroupType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient

class GetGroupHandlerTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `Should get a groups by menu_id`() {
        webTestClient
                .get()
                .uri("/group?menu_id=MENU_af60830b-d190-43bf-afcb-f5cc2656ea25")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.menu_id").isEqualTo("GROU_6e2d194f-a31b-4767-926a-cd9b28f3bfaa")
                .jsonPath("$.name").isEqualTo("Ice Cream")
                .jsonPath("$.description").isEqualTo("delicious ice cream")
                .jsonPath("$.type").isEqualTo(GroupType.ICE_CREAM.name)
                .jsonPath("$.available").isEqualTo(true)
    }

    @Test
    fun `Should respond 404 NOT_FOUND if menu is not found`() {
        webTestClient
                .get()
                .uri("/group?menu_id=123a")
                .exchange()
                .expectStatus().isNotFound
                .expectBody()
                .jsonPath("$.error_messages").isArray
                .jsonPath("$.error_messages.length()").isEqualTo(1)
                .jsonPath("$.error_messages[*].description").isEqualTo("No group found. id=123a")

    }

}