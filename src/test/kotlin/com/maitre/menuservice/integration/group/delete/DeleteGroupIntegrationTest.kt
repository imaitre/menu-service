package com.maitre.menuservice.integration.group.delete

import com.maitre.menuservice.adapter.group.out.GroupRepository
import com.maitre.menuservice.adapter.group.out.persistence.entity.GroupEntity
import com.maitre.menuservice.domain.group.entity.GroupType
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.EnabledIf
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'test'}")
class DeleteGroupIntegrationTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var groupRepository: GroupRepository
    @Mock
    lateinit var createGroupRepository: GroupRepository


    @Test
    fun `Should delete a group`() {
        givenGroupSavedInDatabase().map{
            webTestClient
                .delete()
                .uri("/group/${it.id}")
                .exchange()
                .expectStatus().isNoContent
        }
    }

    @Test
    fun `Should respond 404 NOT_FOUND if group is not found`() {
        webTestClient
            .delete()
            .uri("/group/123")
            .exchange()
            .expectStatus().isNotFound
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