package dev.vanderblom.inventorybackenddemo.service.model

import dev.vanderblom.inventorybackenddemo.data.model.Product
import dev.vanderblom.inventorybackenddemo.data.model.Reservation

class ProductModel(private val product: Product) {
    val id get() = product.id!!
    val name get() = product.name
    val inventory get() = product.inventory - product.reservations.sumOf(Reservation::amount)
}