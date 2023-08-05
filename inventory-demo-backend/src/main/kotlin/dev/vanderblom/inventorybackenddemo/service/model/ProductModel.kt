package dev.vanderblom.inventorybackenddemo.service.model

import dev.vanderblom.inventorybackenddemo.data.model.Product
import dev.vanderblom.inventorybackenddemo.data.model.Reservation

class ProductModel(
    val id: Long,
    val name: String,
    val inventory: Long
) {

    companion object {
        fun of(product: Product): ProductModel {
            val id = product.id!!
            val name = product.name
            val inventory = product.inventory - product.reservations.sumOf(Reservation::amount)
            return ProductModel(id, name, inventory)
        }
    }
}