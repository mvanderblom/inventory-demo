package dev.vanderblom.inventorybackenddemo.service

import dev.vanderblom.inventorybackenddemo.data.ProductRepository
import dev.vanderblom.inventorybackenddemo.data.model.Product
import dev.vanderblom.inventorybackenddemo.service.exceptions.ResourceNotFoundException
import dev.vanderblom.inventorybackenddemo.service.model.ProductUpdateModel
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class InventoryService(
    private val repo: ProductRepository
)  {
    fun list(): List<Product> = repo.findAll().toList()

    fun create(product: Product) = repo.save(product)

    fun read(id: Long): Product = repo.findById(id)
        .orElseThrow { ResourceNotFoundException("The product with id $id was not found.") }

    fun update(id: Long, productUpdate: ProductUpdateModel) {
        val data = repo.findById(id)
            .orElseThrow { ResourceNotFoundException("The product with id $id was not found.") }
        data.name = productUpdate.name
        data.inventory = productUpdate.inventory
        repo.save(data)
    }

    fun delete(@PathVariable id: Long) = repo.deleteById(id)
}