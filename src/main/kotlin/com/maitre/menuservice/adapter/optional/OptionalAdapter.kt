package com.maitre.menuservice.adapter.optional

import com.maitre.menuservice.adapter.optional.out.persistence.entity.OptionalRepository
import com.maitre.menuservice.domain.optional.entity.Optional
import com.maitre.menuservice.domain.optional.port.out.persistence.GetOptionalsByMenuIdPort
import com.maitre.menuservice.domain.optional.port.out.persistence.SaveOptionalPort
import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toEntity
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Component
class OptionalAdapter(private val optionalRepository: OptionalRepository): SaveOptionalPort, GetOptionalsByMenuIdPort {

  override fun save(optional: Optional): Mono<Optional> {
    return optionalRepository.save(optional.toEntity())
      .map { it.toDomain() }
  }

  override fun getByMenuId(menuId: String): Flux<Optional> {
    return optionalRepository.findAllByMenuId(menuId)
      .map { it.toDomain() }
  }
}