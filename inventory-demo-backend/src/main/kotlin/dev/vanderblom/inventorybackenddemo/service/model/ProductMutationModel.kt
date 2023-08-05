package dev.vanderblom.inventorybackenddemo.service.model

data class ProductMutationModel(
    val name: String,
    val inventory: Long
)