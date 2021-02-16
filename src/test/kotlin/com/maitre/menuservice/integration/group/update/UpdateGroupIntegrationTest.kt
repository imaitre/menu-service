package com.maitre.menuservice.integration.group.update

import com.maitre.menuservice.adapter.group.`in`.dto.GroupRequestDTO
import com.maitre.menuservice.adapter.group.out.GroupRepository
import com.maitre.menuservice.adapter.group.out.persistence.entity.GroupEntity
import com.maitre.menuservice.domain.group.entity.GroupType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.EnabledIf
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'test'}")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UpdateGroupIntegrationTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var groupRepository: GroupRepository

    @Test
    fun `Should update a group`() {
        givenGroupSavedInDatabase().map{
            val groupRequestDTO = GroupRequestDTO(
                it.menuId,
                "Ice Cream",
                "delicious ice cream",
                GroupType.ICE_CREAM,
                false)

            webTestClient
                .put()
                .uri("/group/${it.id}")
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
                .jsonPath("$.available").isEqualTo(false)
        }
    }

    @Test
    fun `Should respond 404 NOT_FOUND if group is not found`() {

        val groupRequestDTO = GroupRequestDTO(
            "MENU_1",
            "Ice Cream",
            "delicious ice cream",
            GroupType.ICE_CREAM,
            true)
        webTestClient
            .put()
            .uri("/group/1234")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(groupRequestDTO)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.error_messages").isArray
            .jsonPath("$.error_messages.length()").isEqualTo(1)
            .jsonPath("$.error_messages[*].description").isEqualTo("Group not found. id=1234")
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