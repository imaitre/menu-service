package com.maitre.menuservice.adapter.group.`in`.create

import com.maitre.menuservice.adapter.group.`in`.dto.GroupRequestDTO
import com.maitre.menuservice.domain.group.entity.GroupType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
 class CreateGroupHandlerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `Should create a group`() {
        val groupRequestDTO = GroupRequestDTO(
            "MENU_bc4743a8-1130-4127-a057-0aacc950a1e3",
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
                .jsonPath("$.menu_id").isEqualTo("MENU_bc4743a8-1130-4127-a057-0aacc950a1e3")
                .jsonPath("$.name").isEqualTo("Ice Cream")
                .jsonPath("$.description").isEqualTo("delicious ice cream")
                .jsonPath("$.type").isEqualTo(GroupType.ICE_CREAM.name)
                .jsonPath("$.available").isEqualTo(true)
    }
}
