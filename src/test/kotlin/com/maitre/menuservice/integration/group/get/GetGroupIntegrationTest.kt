package com.maitre.menuservice.integration.group.get

import com.maitre.menuservice.adapter.group.out.GroupRepository
import com.maitre.menuservice.adapter.group.out.persistence.entity.GroupEntity
import com.maitre.menuservice.domain.group.entity.GroupType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.EnabledIf
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'test'}")
class GetGroupIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var groupRepository: GroupRepository

    @Test
    fun `Should get a group`() {
        givenGroupSavedInDatabase()

        webTestClient
            .get()
            .uri("/group/GRUO_bc4743a8-1130-4127-a057-0aacc950a1e3")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo("GRUO_bc4743a8-1130-4127-a057-0aacc950a1e3")
            .jsonPath("$.menu_id").isEqualTo("MENU_af60830b-d190-43bf-afcb-f5cc2656ea25")
            .jsonPath("$.name").isEqualTo("Ice Cream")
            .jsonPath("$.description").isEqualTo("delicious ice cream")
            .jsonPath("$.type").isEqualTo("ICE_CREAM")
            .jsonPath("$.available").isEqualTo(true)
    }

    @Test
    fun `Should respond 404 NOT_FOUND if menu is not found`() {

        givenGroupNotExistentInDatabase()

        webTestClient
            .get()
            .uri("/group/MENU_af60830b-d190-43bf-afcb-f5cc2656ea26")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.error_messages").isArray
            .jsonPath("$.error_messages.length()").isEqualTo(1)
            .jsonPath("$.error_messages[*].description")
            .isEqualTo("Group not found. id=MENU_af60830b-d190-43bf-afcb-f5cc2656ea26")

    }

    private fun givenGroupSavedInDatabase(){
        val groupEntity = GroupEntity(
            "GRUO_bc4743a8-1130-4127-a057-0aacc950a1e3",
            "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
            "Ice Cream",
            "delicious ice cream",
            GroupType.ICE_CREAM,
            true
        )
        groupRepository.save(groupEntity).block()
    }

    private fun givenGroupNotExistentInDatabase(){
        groupRepository.deleteById("GRUO_bc4743a8-1130-4127-a057-0aacc950a1e3").block()
    }

}