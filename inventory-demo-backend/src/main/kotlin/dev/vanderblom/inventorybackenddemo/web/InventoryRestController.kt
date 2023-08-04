package dev.vanderblom.inventorybackenddemo.web

import dev.vanderblom.inventorybackenddemo.data.model.Product
import dev.vanderblom.inventorybackenddemo.service.InventoryService
import dev.vanderblom.inventorybackenddemo.service.model.ProductModel
import dev.vanderblom.inventorybackenddemo.service.model.ProductUpdateModel
import dev.vanderblom.inventorybackenddemo.service.model.ReservationRequestModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/inventory/")
class InventoryRestController(
    private val service: InventoryService
) {

    @GetMapping("/")
    fun list(): List<ProductModel> = service.list()

    @PostMapping("/")
    fun create(@RequestBody product: Product) = service.create(product)

    @GetMapping("/{id}")
    fun read(@PathVariable id: Long) = service.read(id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody productUdate: ProductUpdateModel) = service.update(id, productUdate)

    @PutMapping("/{id}/reserve")
    fun reserve(@PathVariable id: Long, @RequestBody reservationRequest: ReservationRequestModel) =
        service.reserve(id, reservationRequest)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) = service.delete(id)

}