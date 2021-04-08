package com.maitre.menuservice.domain.optional.usecases

import com.maitre.menuservice.domain.group.entity.Group
import com.maitre.menuservice.domain.group.port.out.persistence.GetGroupsByMenuIdPort
import com.maitre.menuservice.domain.group.usecases.GetGroupsByMenuIdUseCase
import com.maitre.menuservice.domain.optional.entity.Optional
import com.maitre.menuservice.domain.optional.port.out.persistence.DeleteOptionalPort
import com.maitre.menuservice.domain.optional.port.out.persistence.GetOptionalByIdPort
import com.maitre.menuservice.domain.product.entity.Product
import com.maitre.menuservice.domain.product.port.out.persistence.GetProductsByGroupPort
import com.maitre.menuservice.domain.product.port.out.persistence.SaveProductPort
import com.maitre.menuservice.exception.OptionalNotFoundException
import kotlinx.coroutines.reactive.collect
import org.slf4j.Logger
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.util.function.Tuple2

@Service
class DeleteOptionalUseCase(
    private val logger: Logger,
    private val getOptionalByIdPort: GetOptionalByIdPort,
    private val deleteOptionalPort: DeleteOptionalPort,
    private val getGroupsByMenuIdPort: GetGroupsByMenuIdPort,
    private val getProductsByGroupPort: GetProductsByGroupPort,
    private val saveProductPort: SaveProductPort
) {

    fun execute(id: String): Mono<Void> {
        logger.info("Delete optional use case initiated. groupId=${id}")
        val optional = getOptionalByIdPort.getById(id)
            .switchIfEmpty(Mono.error(OptionalNotFoundException(id)))

        return  removeOptionFromProducts(optional, id)
                .then(deleteOptionalPort.delete(id))
                .doOnSuccess { logger.info("Delete optional use case done. id=${id}}") }

//        return Mono.zip(
//            removeOptionFromProducts(optional, id),
//            optional
//                .then(deleteOptionalPort.delete(id))
//                .doOnSuccess { logger.info("Delete optional use case done. id=${id}}") }
//        )

//        return removeOptionFromProducts(optional, id)
//            .then(deleteOptionalPort.delete(id))
//            .doOnSuccess { logger.info("Delete optional use case done. id=${id}}") }
    }

    fun removeOptionFromProducts(optional: Mono<Optional>, optionalId: String): Mono<Optional> {
        return optional.map {
            print("1:$it")
            getGroupsByMenuIdPort.getByMenuId(it.menuId)
                .map { a -> println("1.5: $a") }
                .map { group ->
                    print("2:$it")
                    getProductsByGroupPort.getByGroup(group.id)
                        .map { product ->
                            print("3:$it")
                            removeOptional(product, optionalId) }
                }
        }
            .flatMap {
                print("4:$it")
                optional }
    }

    private fun removeOptional(product: Product?, optionalId: String): Mono<Product> {
        if (product != null) {
            product.addons?.remove(optionalId)
            return saveProductPort.save(product)
        }
        return Mono.empty()
    }


}

//fun removeOptionFromProducts(optional: Mono<Optional>, optionalId: String) : Mono<Optional>{
//    optional.map { getGroupsByMenuIdPort.getByMenuId(it.menuId) }
//        .map { groups ->
//            groups.map { group ->
//                getProductsByGroupPort.getByGroup(group.id)
//            }
//                .map { products ->
//                    products.map { product -> removeOptional(product, optionalId) } }
//        }
//
//}