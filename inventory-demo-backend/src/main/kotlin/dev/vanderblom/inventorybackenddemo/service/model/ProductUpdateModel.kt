package dev.vanderblom.inventorybackenddemo.service.model

data class ProductUpdateModel(
    val name: String,
    val inventory: Long
) {
}