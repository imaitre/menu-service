package com.maitre.menuservice.integration.group.create

import com.maitre.menuservice.adapter.group.`in`.dto.GroupRequestDTO
import com.maitre.menuservice.adapter.group.out.GroupRepository
import com.maitre.menuservice.adapter.menu.out.MenuRepository
import com.maitre.menuservice.adapter.menu.out.persistence.entity.MenuEntity
import com.maitre.menuservice.domain.group.entity.GroupType
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.EnabledIf
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'test'}")
class CreateGroupIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var menuRepository: MenuRepository
    @Mock
    lateinit var createGroupRepository: GroupRepository

    @Test
    fun `Should create a group`() {
        givenMenuSavedInDatabase().map{
            val groupRequestDTO = GroupRequestDTO(
                it.id,
                "Ice Cream",
                "delicious ice cream",
                GroupType.ICE_CREAM,
                true)

            webTestClient
                .post()
                .uri("/group")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(groupRequestDTO)
                .exchange()
                .expectStatus().isCreated
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.menu_id").isEqualTo(it.id)
                .jsonPath("$.name").isEqualTo("Ice Cream")
                .jsonPath("$.description").isEqualTo("delicious ice cream")
                .jsonPath("$.type").isEqualTo(GroupType.ICE_CREAM.name)
                .jsonPath("$.available").isEqualTo(true)
        }
    }

    @Test
    fun `Should respond 404 NOT_FOUND if menu is not found`() {

        val groupRequestDTO = GroupRequestDTO(
            "MENU_1",
            "Ice Cream",
            "delicious ice cream",
            GroupType.ICE_CREAM,
            true)
        webTestClient
            .post()
            .uri("/group")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(groupRequestDTO)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.error_messages").isArray
            .jsonPath("$.error_messages.length()").isEqualTo(1)
            .jsonPath("$.error_messages[*].description").isEqualTo("Menu not found. id=MENU_1")
    }

    private fun givenMenuSavedInDatabase(): Mono<MenuEntity> {
        val menuEntity = MenuEntity(
            "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
            "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62",
            "Monday menu",
            "some description.",
            true)
        return menuRepository.save(menuEntity)
    }
}