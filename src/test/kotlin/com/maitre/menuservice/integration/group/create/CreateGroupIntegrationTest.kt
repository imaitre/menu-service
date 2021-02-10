package com.maitre.menuservice.integration.group.create

import com.maitre.menuservice.adapter.group.`in`.dto.GroupRequestDTO
import com.maitre.menuservice.adapter.group.out.GroupRepository
import com.maitre.menuservice.adapter.group.out.persistence.entity.GroupEntity
import com.maitre.menuservice.adapter.menu.out.MenuRepository
import com.maitre.menuservice.adapter.menu.out.persistence.entity.MenuEntity
import com.maitre.menuservice.domain.group.entity.GroupType
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.EnabledIf
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.kotlin.core.publisher.toMono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'test'}")
class CreateGroupIntegrationTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Mock
    lateinit var menuRepository: MenuRepository
    @Mock
    lateinit var createGroupRepository: GroupRepository

    @Test
    fun `Should create a group`() {

        whenever(menuRepository.findById(any<String>())).thenReturn(menuEntity.toMono())
        whenever(createGroupRepository.save(any<GroupEntity>())).thenReturn(groupEntity.toMono())

        val groupRequestDTO = GroupRequestDTO(
                "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
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
                .jsonPath("$.menu_id").isEqualTo("MENU_af60830b-d190-43bf-afcb-f5cc2656ea25")
                .jsonPath("$.name").isEqualTo("Ice Cream")
                .jsonPath("$.description").isEqualTo("delicious ice cream")
                .jsonPath("$.type").isEqualTo(GroupType.ICE_CREAM.name)
                .jsonPath("$.available").isEqualTo(true)
    }


    private val menuEntity = MenuEntity(
            "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
            "CUST_0c6e1cb0-df2e-414a-98fb-73b2b8ce6b62",
            "Monday menu",
            "some description.",
            true)
    private val groupEntity = GroupEntity(
            "GRUO_bc4743a8-1130-4127-a057-0aacc950a1e3",
            "MENU_af60830b-d190-43bf-afcb-f5cc2656ea25",
            "Ice Cream",
            "delicious ice cream",
            GroupType.ICE_CREAM,
            true)
}