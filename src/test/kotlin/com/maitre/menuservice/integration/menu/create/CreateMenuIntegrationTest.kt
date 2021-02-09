package com.maitre.menuservice.integration.menu.create

import com.maitre.menuservice.adapter.menu.`in`.dto.MenuRequestDTO
import com.maitre.menuservice.adapter.menu.out.MenuRepository
import com.maitre.menuservice.adapter.menu.out.persistence.entity.MenuEntity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.kotlin.core.publisher.toMono


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CreateMenuIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Mock
    lateinit var menuRepository: MenuRepository

    @Test
    fun `Should create a menu`() {

        whenever(menuRepository.save(any<MenuEntity>())).thenReturn(menuEntity.toMono())

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

    private val menuEntity = MenuEntity(
        "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
        "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62",
        "Monday menu",
        "some description.",
        true
    )
}