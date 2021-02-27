package com.maitre.menuservice.domain.menu.port.out.persistence

import com.maitre.menuservice.domain.menu.entity.Menu
import reactor.core.publisher.Flux

interface GetMenusByCustomerPort {
    fun getByCustomerId(customerId: String) : Flux<Menu>
}
