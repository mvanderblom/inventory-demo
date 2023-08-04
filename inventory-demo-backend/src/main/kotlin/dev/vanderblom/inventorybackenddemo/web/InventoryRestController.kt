package dev.vanderblom.inventorybackenddemo.web

import dev.vanderblom.inventorybackenddemo.data.ProductRepository
import dev.vanderblom.inventorybackenddemo.data.model.Product
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/inventory/")
class InventoryRestController(
        private val repo: ProductRepository
)  {

    @GetMapping("/")
    fun list(): List<Product> = repo.findAll().toList()

    @PostMapping("/")
    fun create(@RequestBody product: Product) = repo.save(product)

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long) = repo.findById(id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody product: Product) {
        val data = repo.findById(id).orElseThrow()
        data.name = product.name
        data.inventory = product.inventory
        repo.save(data)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = repo.deleteById(id)





}