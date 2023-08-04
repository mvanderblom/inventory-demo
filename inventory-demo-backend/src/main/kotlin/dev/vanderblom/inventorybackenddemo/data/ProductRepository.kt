package dev.vanderblom.inventorybackenddemo.data

import dev.vanderblom.inventorybackenddemo.data.model.Product
import dev.vanderblom.inventorybackenddemo.service.exceptions.ResourceNotFoundException
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface ProductRepository : CrudRepository<Product, Long> {
    fun getById(id: Long): Product
}