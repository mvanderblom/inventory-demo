package dev.vanderblom.inventorybackenddemo.service

import dev.vanderblom.inventorybackenddemo.data.ProductRepository
import dev.vanderblom.inventorybackenddemo.data.model.Product
import dev.vanderblom.inventorybackenddemo.data.model.Reservation
import dev.vanderblom.inventorybackenddemo.service.model.ProductModel
import dev.vanderblom.inventorybackenddemo.service.model.ProductUpdateModel
import dev.vanderblom.inventorybackenddemo.service.model.ReservationRequestModel
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import java.time.LocalDateTime

@Service
class InventoryService(
    private val repo: ProductRepository
)  {
    fun list(): List<ProductModel> = repo.findAll()
        .map(::ProductModel)
        .toList()

    fun create(product: Product) = ProductModel(repo.save(product))

    fun read(id: Long): ProductModel = ProductModel(repo.getById(id))

    fun update(id: Long, productUpdate: ProductUpdateModel): ProductModel {
        val product = repo.getById(id)
        product.name = productUpdate.name
        product.inventory = productUpdate.inventory
        return ProductModel(repo.save(product))
    }

    fun reserve(id: Long, reservationRequestModel: ReservationRequestModel): ProductModel {
        if (reservationRequestModel.seconds > 5 * 60)
            throw IllegalArgumentException("Max reservation time is 5 minutes")

        val productModel = read(id)

        if(reservationRequestModel.amount > productModel.inventory)
            throw IllegalArgumentException("You cannot reserve more than ${productModel.inventory} products")

        val reservation = Reservation(
            reservationRequestModel.amount,
            LocalDateTime.now().plusSeconds(reservationRequestModel.seconds)
        )
        val product = repo.getById(id)
        product.reservations.add(reservation)
        return ProductModel(repo.save(product))
    }

    fun delete(@PathVariable id: Long) = repo.deleteById(id)

}