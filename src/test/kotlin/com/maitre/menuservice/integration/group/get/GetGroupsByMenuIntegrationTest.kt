package com.maitre.menuservice.integration.group.get

import com.maitre.menuservice.adapter.group.out.GroupRepository
import com.maitre.menuservice.adapter.group.out.persistence.entity.GroupEntity
import com.maitre.menuservice.domain.group.entity.GroupType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.EnabledIf
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'test'}")
class GetGroupsByMenuIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var groupRepository: GroupRepository

    @Test
    fun `Should get a groups by menu_id`() {
        givenGroupSavedInDatabase().map{
            webTestClient
                .get()
                .uri("/group?menu_id=${it.menuId}")
                .exchange()
                .expectStatus().isOk
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
        webTestClient
                .get()
                .uri("/group?menu_id=123a")
                .exchange()
                .expectStatus().isNotFound
                .expectBody()
                .jsonPath("$.error_messages").isArray
                .jsonPath("$.error_messages.length()").isEqualTo(1)
                .jsonPath("$.error_messages[*].description").isEqualTo("Group not found. id=123a")
    }


    private fun givenGroupSavedInDatabase(): Mono<GroupEntity> {
        val group = GroupEntity(
            "GRUO_bc4743a8-1130-4127-a057-0aacc950a1e3",
            "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
            "Ice Cream",
            "delicious ice cream",
            GroupType.ICE_CREAM,
            true)
        return groupRepository.save(group)
    }

}