package dev.vanderblom.inventorybackenddemo.data

import dev.vanderblom.inventorybackenddemo.data.model.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface ProductRepository : CrudRepository<Product, Long> {
}