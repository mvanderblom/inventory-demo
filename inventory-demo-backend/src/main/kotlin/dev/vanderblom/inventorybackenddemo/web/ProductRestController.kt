package dev.vanderblom.inventorybackenddemo.web

import dev.vanderblom.inventorybackenddemo.data.model.Product
import dev.vanderblom.inventorybackenddemo.service.ProductInventoryService
import dev.vanderblom.inventorybackenddemo.service.model.ProductModel
import dev.vanderblom.inventorybackenddemo.service.model.ProductUpdateModel
import dev.vanderblom.inventorybackenddemo.service.model.ReservationRequestModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/product/")
class ProductRestController(
    private val service: ProductInventoryService
) {

    @GetMapping("/")
    fun list(): List<ProductModel> = service.getAllProducts()

    @PostMapping("/")
    fun create(@RequestBody product: Product) = service.createProduct(product)

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long) = service.readProduct(id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody productUdate: ProductUpdateModel) =
        service.updateProduct(id, productUdate)

    @PutMapping("/{id}/reserve")
    fun reserve(@PathVariable id: Long, @RequestBody reservationRequest: ReservationRequestModel) =
        service.createReservation(id, reservationRequest)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.deleteProduct(id)

}