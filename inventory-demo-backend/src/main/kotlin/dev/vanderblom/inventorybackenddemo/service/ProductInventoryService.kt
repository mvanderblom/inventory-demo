package dev.vanderblom.inventorybackenddemo.service

import dev.vanderblom.inventorybackenddemo.data.ProductRepository
import dev.vanderblom.inventorybackenddemo.data.ReservationRepository
import dev.vanderblom.inventorybackenddemo.data.model.Product
import dev.vanderblom.inventorybackenddemo.data.model.Reservation
import dev.vanderblom.inventorybackenddemo.service.model.ProductModel
import dev.vanderblom.inventorybackenddemo.service.model.ProductMutationModel
import dev.vanderblom.inventorybackenddemo.service.model.ReservationRequestModel
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable
import java.time.LocalDateTime

@Service
class ProductInventoryService(
    private val productRepo: ProductRepository,
    private val reservationsRepo: ReservationRepository
) {
    fun getAllProducts(): List<ProductModel> = productRepo.findAll()
        .map(ProductModel::of)
        .toList()

    fun createProduct(projectMutation: ProductMutationModel): ProductModel {
        val product = productRepo.save(Product(projectMutation.name, projectMutation.inventory))
        return ProductModel.of(product)
    }

    fun readProduct(id: Long): ProductModel = ProductModel.of(productRepo.getById(id))

    fun updateProduct(id: Long, projectMutation: ProductMutationModel): ProductModel {
        val product = productRepo.getById(id)
        product.name = projectMutation.name
        product.inventory = projectMutation.inventory
        return ProductModel.of(productRepo.save(product))
    }

    fun createReservation(id: Long, reservationRequestModel: ReservationRequestModel): ProductModel {
        val productModel = readProduct(id)
        require(reservationRequestModel.seconds <= 5 * 60) { "Max reservation time is 5 minutes" }
        require(reservationRequestModel.amount <= productModel.inventory) { "You cannot reserve more than ${productModel.inventory} products" }

        val reservation = Reservation(
            reservationRequestModel.amount,
            LocalDateTime.now().plusSeconds(reservationRequestModel.seconds)
        )
        val product = productRepo.getById(id)
        product.reservations.add(reservation)
        return ProductModel.of(productRepo.save(product))
    }

    fun deleteProduct(@PathVariable id: Long) = productRepo.deleteById(id)

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Scheduled(fixedRate = 500)
    fun cleanUpReservations() {
        reservationsRepo.deleteByExpiresBefore(LocalDateTime.now())
    }

}