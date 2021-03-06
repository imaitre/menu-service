package com.maitre.menuservice.adapter.optional

import com.maitre.menuservice.adapter.optional.out.persistence.entity.OptionalRepository
import com.maitre.menuservice.domain.optional.entity.Optional
import com.maitre.menuservice.domain.optional.port.out.persistence.SaveOptionalPort
import com.maitre.menuservice.utils.toDomain
import com.maitre.menuservice.utils.toEntity
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono


@Component
class OptionalAdapter(private val optionalRepository: OptionalRepository): SaveOptionalPort {

  override fun save(optional: Optional): Mono<Optional> {
    return optionalRepository.save(optional.toEntity())
      .map { it.toDomain() }
  }
}